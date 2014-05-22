package com.undancer.breath.samples.showcase;

import org.apache.shiro.authz.UnauthenticatedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Created by undancer on 14-4-16.
 */
@ControllerAdvice
public class DefaultErrorControllerAdvice {

//    @ExceptionHandler(Throwable.class)
//    public void error(Throwable throwable) {
//        try {
//            BotUtils.setUrl("https://boxfish.slack.com/services/hooks/incoming-webhook?token=gWL5P45NRwVXyrozkosr2urj");
//            String username = String.format("%s@%s", "粗线鸟", Inet4Address.getLocalHost().getHostAddress());
//            BotUtils.say("#bebase", ":ghost:", username, throwable);
//        } catch (UnknownHostException e) {
//
//        }
//        throwable.printStackTrace();
//    }

    @ExceptionHandler(UnauthenticatedException.class)
    public void Unauthenticated(UnauthenticatedException exception) {
        System.out.println("未授权的账户");
    }

}
