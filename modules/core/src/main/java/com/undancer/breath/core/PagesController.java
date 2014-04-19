package com.undancer.breath.core;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by undancer on 14-4-18.
 */
@Controller
public class PagesController {
    @RequestMapping("/**/*")
    public void pages() {
    }
}
