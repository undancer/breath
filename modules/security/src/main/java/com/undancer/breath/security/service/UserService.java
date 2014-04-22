package com.undancer.breath.security.service;


import com.undancer.breath.security.entity.User;

import java.util.Collection;

/**
 * Created by undancer on 14-4-21.
 */
public interface UserService {

    public User loadUserByAccessToken(String accessToken);

    public User loadUserByUsername(String username);

    public void save(User user);

    public Collection<String> getRolesByUser(User user);

    public Collection<String> getPermsByUser(User user);

}
