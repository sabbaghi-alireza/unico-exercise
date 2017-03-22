package com.unico.exercise.soap;

import com.unico.exercise.service.ElementService;
import com.unico.exercise.soap.security.interceptor.CheckUserRole;
import org.apache.cxf.annotations.EndpointProperties;
import org.apache.cxf.annotations.EndpointProperty;
import org.apache.cxf.annotations.Policy;

import javax.inject.Inject;
import javax.jws.WebService;
import java.util.List;

/**
 * Created by Alireza on 3/18/2017.
 */

@WebService
        (
                portName = "ElementSoapServicePort",
                serviceName = "ElementSoapService",
                targetNamespace = "http://soap.exercise.unico.com/ws-extensions/wssecuritypolicy",
                endpointInterface = "com.unico.exercise.soap.ElementSoapService"
        )
@Policy(placement = Policy.Placement.BINDING, uri = "SecurityPolicy.xml")
@EndpointProperties(value = {
        @EndpointProperty(key = "ws-security.ut.validator", value = "com.unico.exercise.soap.security.config.CustomUTValidator")
        //@EndpointProperty(key = "ws-security.callback-handler", value = "com.unico.exercise.soap.security.config.ServerUsernamePasswordCallback")
}
)
public class ElementSoapServiceImpl implements ElementSoapService {

    @Inject
    private ElementService service;



    @Override
    public Integer gcd() {
        return service.computeGCD();
    }

    @Override
    public List<Integer> gcdList() {
        return service.getGCDList();
    }

    @Override
    @CheckUserRole("ADMIN")
    public Integer gcdSum() {
        return service.getGCDSum();
    }
}
