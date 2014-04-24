package com.undancer.breath.core;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by undancer on 14-4-22.
 */
@Controller
public class RootController {
    @RequestMapping("/")
    public void root() {
    }
}
