package com.undancer.breath.samples.showcase.security.service.impl;

import com.google.common.collect.Lists;
import com.undancer.breath.samples.showcase.entity.User;
import com.undancer.breath.samples.showcase.entity.jpa.UserJpaRepository;
import com.undancer.breath.security.userdetails.UserDetails;
import com.undancer.breath.security.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;

/**
 * Created by undancer on 14-4-21.
 */
@Named("userService")
@Transactional(readOnly = true)
public class UserServiceImpl implements UserDetailsService {

    @Inject
    UserJpaRepository userJpaRepository;

    public User loadUserByAccessToken(final String accessToken) {
        return userJpaRepository.findOneByAccessToken(accessToken);
    }

    public User loadUserByUsername(String username) {
        return userJpaRepository.findOneByUsername(username);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public <U extends UserDetails> void save(U user) {
        if (user instanceof User) {
            userJpaRepository.save((User) user);
        }
    }

    public <U extends UserDetails> Collection<String> getRolesByUser(U user) {
        return Lists.newArrayList();
    }

    public <U extends UserDetails> Collection<String> getPermsByUser(U user) {
        return Lists.newArrayList();
    }
}
