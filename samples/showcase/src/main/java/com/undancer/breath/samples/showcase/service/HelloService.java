package com.undancer.breath.samples.showcase.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
 * Created by undancer on 14-5-2.
 */
@WebService(name = "helloService")
//@InInterceptors(classes = WSS4JInInterceptor.class)
//@EndpointProperties()
public interface HelloService {
    @WebMethod
    public
    @WebResult
    String sayHi(@WebParam String username);
}
