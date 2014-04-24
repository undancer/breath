package com.undancer.breath.samples.showcase;

import org.apache.shiro.authz.UnauthenticatedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Created by undancer on 14-4-16.
 */
@ControllerAdvice
public class DefaultErrorControllerAdvice {

    @ExceptionHandler(Throwable.class)
    public void error(Throwable throwable) {

        System.out.println("throwable:" + throwable);

    }

    @ExceptionHandler(UnauthenticatedException.class)
    public void Unauthenticated(UnauthenticatedException exception) {
        System.out.println("未授权的账户");
    }

}
