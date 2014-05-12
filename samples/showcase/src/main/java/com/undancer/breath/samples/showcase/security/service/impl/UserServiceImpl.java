package com.undancer.breath.samples.showcase.security.service.impl;

import com.google.common.collect.Lists;
import com.undancer.breath.samples.showcase.entity.User;
import com.undancer.breath.samples.showcase.entity.jpa.UserJpaRepository;
import com.undancer.breath.security.userdetails.UserDetailsService;
import org.apache.cxf.interceptor.security.JAASLoginInterceptor;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import java.io.IOException;
import java.util.Collection;

/**
 * Created by undancer on 14-4-21.
 */
@Named("userService")
@Transactional(readOnly = true)
public class UserServiceImpl implements UserDetailsService<User>, CallbackHandler {

    @Inject
    UserJpaRepository userJpaRepository;

    public User loadUserByAccessToken(final String accessToken) {
        return userJpaRepository.findOneByAccessToken(accessToken);
    }

    public User loadUserByUsername(String username) {
        return userJpaRepository.findOneByUsername(username);
    }

    public Collection<String> getRolesByUser(User user) {
        return Lists.newArrayList();
    }

    public Collection<String> getPermsByUser(User user) {
        return Lists.newArrayList();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void save(User user) {
        userJpaRepository.save(user);
    }

    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
        for (Callback callback : callbacks) {
            System.out.println(callback);
        }
//        WSS4JInInterceptor;
//        Interceptor
        JAASLoginInterceptor interceptor;
    }
}
