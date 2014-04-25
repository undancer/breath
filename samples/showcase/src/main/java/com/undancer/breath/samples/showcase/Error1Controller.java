package com.undancer.breath.samples.showcase;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by undancer on 14-4-16.
 */

@Controller
@RequestMapping("/error1")
public class Error1Controller {

    @RequestMapping(method = RequestMethod.GET)
    public void error() throws Exception {
        throw new Exception("error");
    }

    @ExceptionHandler(Throwable.class)
    public void handler(Throwable throwable) {
        System.out.println(throwable.getMessage());
    }
}
