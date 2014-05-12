package com.undancer.breath.samples.showcase;

import com.google.common.collect.Lists;
import com.undancer.breath.samples.showcase.entity.User;
import com.undancer.breath.samples.showcase.service.UserService;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.message.Message;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.apache.ws.security.WSConstants;
import org.apache.ws.security.WSPasswordCallback;
import org.apache.ws.security.handler.WSHandlerConstants;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by undancer on 14-5-2.
 */
public class Main implements CallbackHandler {
    public static void main(String[] args) {
        JaxWsProxyFactoryBean proxyFactory = new JaxWsProxyFactoryBean();
        Map<String, Object> properties = new HashMap<>();
        properties.put(WSHandlerConstants.ACTION, WSHandlerConstants.USERNAME_TOKEN);
        properties.put(WSHandlerConstants.PASSWORD_TYPE, WSConstants.PW_TEXT);
        properties.put(WSHandlerConstants.USER, "admin");
        properties.put(WSHandlerConstants.PW_CALLBACK_CLASS, Main.class.getName());
        proxyFactory.setOutInterceptors(Lists.<Interceptor<? extends Message>>newArrayList(new LoggingOutInterceptor(), new WSS4JOutInterceptor(properties)));
//        proxyFactory.setAddress("http://localhost:8080/service/hello");
        proxyFactory.setInInterceptors(Lists.<Interceptor<? extends Message>>newArrayList(new LoggingOutInterceptor()));
        proxyFactory.setAddress("http://localhost:3200/service/userService");
        proxyFactory.setServiceClass(UserService.class);
        UserService service = (UserService) proxyFactory.create();
        User user = service.loadUserByAccessToken("admin");
        System.out.println(ToStringBuilder.reflectionToString(user).toString());
    }

    @Override
    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
        for (Callback callback : callbacks) {
            if (callback instanceof WSPasswordCallback) {
                ((WSPasswordCallback) callback).setIdentifier("bebase");
                ((WSPasswordCallback) callback).setPassword("boxfish");
            }
        }
    }
}
