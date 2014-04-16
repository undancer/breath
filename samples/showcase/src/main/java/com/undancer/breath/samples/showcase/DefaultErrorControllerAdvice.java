package com.undancer.breath.samples.showcase;

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

}
