package com.undancer.breath.samples.showcase;

import com.google.common.collect.Maps;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by undancer on 14-4-17.
 */
@RestController
@RequestMapping("/json")
public class JsonController {

    @RequestMapping(method = RequestMethod.GET)
    public Object json() {

        Map<String, String> body = Maps.newHashMap();
        body.put("name", "sign");

        return body;
    }
}
