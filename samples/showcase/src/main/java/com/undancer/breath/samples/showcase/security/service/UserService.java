package com.undancer.breath.samples.showcase.security.service;

import com.undancer.breath.samples.showcase.security.token.User;

/**
 * Created by undancer on 14-4-21.
 */
public interface UserService {

    public User loadUserByAccessToken(String accessToken);

}
