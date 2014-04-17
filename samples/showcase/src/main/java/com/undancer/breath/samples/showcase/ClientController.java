package com.undancer.breath.samples.showcase;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by undancer on 14-4-17.
 */
@RestController
@RequestMapping("/client/home")
public class ClientController {

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public void get() {
        System.out.println(this);
    }
}
