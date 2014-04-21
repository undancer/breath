package com.undancer.breath.samples.showcase.security.service.impl;

import com.undancer.breath.cache.Cache;
import com.undancer.breath.samples.showcase.entity.UserJpaRepository;
import com.undancer.breath.samples.showcase.security.service.UserService;
import com.undancer.breath.samples.showcase.security.token.User;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.Callable;

/**
 * Created by undancer on 14-4-21.
 */
@Named("userService")
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    @Inject
    UserJpaRepository userJpaRepository;

    @Inject
    Cache cache;

    public User loadUserByAccessToken(final String accessToken) {
        return cache.get("user_by_access_token_" + accessToken, new Callable<User>() {
            public User call() throws Exception {
                return userJpaRepository.findOneByAccessToken(accessToken);
            }
        });
    }
}
