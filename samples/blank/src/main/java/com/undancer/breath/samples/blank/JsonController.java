package com.undancer.breath.samples.blank;

import com.google.common.collect.Maps;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by undancer on 14-4-22.
 */
@RestController
@RequestMapping("/json")
public class JsonController {
    @RequestMapping(method = RequestMethod.GET)
    public Object get() {
        Map<String, Object> json = Maps.newHashMap();
        return json;
    }
}
