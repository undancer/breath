package com.undancer.breath.samples.showcase.service;

import javax.inject.Named;
import javax.jws.WebService;

/**
 * Created by undancer on 14-5-2.
 */
@Named("helloService")
@WebService(serviceName = "helloService")
//@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class HelloServiceImpl implements HelloService {
    public String sayHi(String username) {
        return "Hi," + username;
    }
}
