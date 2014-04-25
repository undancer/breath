package com.undancer.breath.metrics;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by undancer on 14-4-25.
 */
@RestController
@RequestMapping(value = "/ping")
public class PingController {

    private static final String NO_CACHE = "must-revalidate,no-cache,no-store";

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<String> ping() {
        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl(NO_CACHE);
        return new ResponseEntity("pong", headers, HttpStatus.OK);
    }
}
