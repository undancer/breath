package com.undancer.breath.samples.showcase.security.impl;

import com.undancer.breath.samples.showcase.security.User;
import com.undancer.breath.samples.showcase.security.UserService;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by undancer on 14-4-21.
 */
@Named("userService")
public class UserServiceImpl implements UserService {

    @Inject
    UserJpaRepository userJpaRepository;

    @Override
    public User loadUserByAccessToken(String accessToken) {

        return null;
    }
}
