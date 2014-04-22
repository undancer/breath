package com.undancer.breath.samples.showcase.security.service.impl;

import com.google.common.collect.Lists;
import com.undancer.breath.samples.showcase.entity.UserEntity;
import com.undancer.breath.samples.showcase.entity.UserJpaRepository;
import com.undancer.breath.security.entity.User;
import com.undancer.breath.security.service.UserService;
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
public class UserServiceImpl implements UserService {

    @Inject
    UserJpaRepository userJpaRepository;

    public User loadUserByAccessToken(final String accessToken) {
        return userJpaRepository.findOneByAccessToken(accessToken);
    }

    public User loadUserByUsername(String username) {
        return userJpaRepository.findOneByUsername(username);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void save(User user) {
        if (user instanceof UserEntity) {
            userJpaRepository.save((UserEntity) user);
        }
    }

    public Collection<String> getRolesByUser(User user) {
        return Lists.newArrayList();
    }

    public Collection<String> getPermsByUser(User user) {
        return Lists.newArrayList();
    }
}
