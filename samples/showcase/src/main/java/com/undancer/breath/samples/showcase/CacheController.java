package com.undancer.breath.samples.showcase;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.undancer.breath.cache.Cache;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

/**
 * Created by undancer on 14-4-19.
 */
@RestController
@RequestMapping("/cache")
public class CacheController {

    @Inject
    Cache cache;

    @RequestMapping(method = RequestMethod.GET)
    public Object get() throws ExecutionException {
        ObjectNode json = JsonNodeFactory.instance.objectNode();
        json.put("name", cache.get("name", new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "undancer";
            }
        }));
        return json;
    }

}
