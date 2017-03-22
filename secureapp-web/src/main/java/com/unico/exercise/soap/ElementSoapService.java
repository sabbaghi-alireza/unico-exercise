package com.unico.exercise.soap;

import org.apache.cxf.annotations.EndpointProperties;
import org.apache.cxf.annotations.EndpointProperty;
import org.apache.cxf.annotations.Policy;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import java.util.List;

/**
 * Created by Alireza on 3/18/2017.
 */
@WebService(targetNamespace = "http://soap.exercise.unico.com/ws-extensions/wssecuritypolicy")
@Policy(placement = Policy.Placement.BINDING, uri = "SecurityPolicy.xml")
@EndpointProperties(value = {
        @EndpointProperty(key = "ws-security.ut.validator", value = "com.unico.exercise.soap.security.config.CustomUTValidator")
        //@EndpointProperty(key = "ws-security.callback-handler", value = "com.unico.exercise.soap.security.config.ServerUsernamePasswordCallback")
}
)
public interface ElementSoapService {

    @WebMethod
    public
    @WebResult(name = "gcd-Result")
    Integer gcd();

    @WebMethod
    public
    @WebResult(name = "gcd-list-Result")
    List<Integer> gcdList();

    @WebMethod
    public
    @WebResult(name = "gcd-sum-Result")
    Integer gcdSum();
}
