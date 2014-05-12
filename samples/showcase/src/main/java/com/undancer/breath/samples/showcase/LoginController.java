package com.undancer.breath.samples.showcase;

import com.undancer.breath.samples.showcase.entity.User;
import com.undancer.breath.samples.showcase.security.service.impl.UserServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.credential.PasswordService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.inject.Inject;

/**
 * Created by undancer on 14-4-22.
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    @Inject
    PasswordService passwordService;

    @Inject
    UserServiceImpl userService;

    @RequestMapping(method = RequestMethod.POST)
    public void post(@RequestParam String username, @RequestParam String password) {

        User user = userService.loadUserByUsername(username);

        if (user != null) {
            String plaintextPassword = user.getPassword();
            if (StringUtils.isNoneBlank(plaintextPassword) && !StringUtils.startsWith(plaintextPassword, "$shiro1$")) {
                String encryptPassword = passwordService.encryptPassword(plaintextPassword);

                user.setPassword(encryptPassword);

                userService.save(user);

                if (passwordService.passwordsMatch(password, encryptPassword)) {
                    System.out.println("密码通过");
                } else {
                    System.out.println("密码不通过");
                }
                System.out.println("encryptPassword: " + encryptPassword);
            }
        }

        System.out.println("username: " + username);
        System.out.println("password: " + password);
    }
}
