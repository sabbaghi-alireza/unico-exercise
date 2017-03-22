package com.unico.exercise.rest;

import com.unico.exercise.model.Element;
import com.unico.exercise.service.ElementService;
import org.apache.shiro.authz.annotation.RequiresRoles;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;
import java.util.logging.Logger;

/**
 * Created by Alireza on 3/16/2017.
 */
@Path("/elements")
@RequestScoped
public class ElementRestService {

    @Inject
    private Logger log;

    @Inject
    private Validator validator;

    @Inject
    private ElementService service;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RequiresRoles("ADMIN")
    public String push(Element element) {
        Response.ResponseBuilder builder = null;

        try {
            // Validates element using bean validation
            validateElement(element);

            service.process(element);

            // Create an "ok" response
            builder = Response.ok();
        } catch (ConstraintViolationException ce) {
            // Handle bean validation issues
            builder = createViolationResponse(ce.getConstraintViolations());
        } catch (Exception e) {
            // Handle generic exceptions
            Map<String, String> responseObj = new HashMap<>();
            responseObj.put("error", e.getMessage());
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
        }

        return builder.build().getStatusInfo().toString();
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RequiresRoles("ADMIN")
    public List<Integer> list() {
        List<Element> list = service.findAll();
        List<Integer> result = new ArrayList<>();
        for (Element n : list) {
            result.add(n.getFirst());
            result.add(n.getSecond());
        }
        return result;
    }



    private void validateElement(Element element) throws ConstraintViolationException, ValidationException {
        // Create a bean validator and check for issues.
        Set<ConstraintViolation<Element>> violations = validator.validate(element);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
        }
    }

    private Response.ResponseBuilder createViolationResponse(Set<ConstraintViolation<?>> violations) {
        log.fine("Validation completed. violations found: " + violations.size());

        Map<String, String> responseObj = new HashMap<>();

        for (ConstraintViolation<?> violation : violations) {
            responseObj.put(violation.getPropertyPath().toString(), violation.getMessage());
        }

        return Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
    }
}
