package com.undancer.breath.samples.showcase;

import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by undancer on 14-4-22.
 */
@Controller
@RequestMapping("/shiro")
public class ShiroController {
    @RequestMapping(method = RequestMethod.GET)
    @RequiresUser
    public void get() {

    }

}
