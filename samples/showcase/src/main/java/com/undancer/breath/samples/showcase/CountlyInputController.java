package com.undancer.breath.samples.showcase;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * Created by undancer on 14-4-21.
 */
@RestController
@RequestMapping("/i")
public class CountlyInputController {

    @RequestMapping(method = RequestMethod.GET)
    public void info(HttpServletRequest request) {
        StringBuffer url = request.getRequestURL();
        String query = request.getQueryString();
        if (StringUtils.isNoneBlank(query)) {
            url.append('?').append(query);
        }
        for (Enumeration<String> enums = request.getParameterNames(); enums.hasMoreElements(); ) {
            String key = enums.nextElement();
            String value = request.getParameter(key);
            System.err.println("key: " + key + " -> value: " + value);
        }

        System.err.println(url.toString());
    }

    @RequestMapping(params = "events", method = RequestMethod.GET)
    public void events(@RequestParam String events) {
        System.out.println(events);
    }

    @RequestMapping(params = "begin_session", method = RequestMethod.GET)
    public void beginSession() {
        System.err.println("beginSession");
    }

    @RequestMapping(params = "session_duration", method = RequestMethod.GET)
    public void durationSession() {
        System.err.println("durationSession");
    }
}
