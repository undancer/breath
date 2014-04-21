package com.undancer.breath.samples.showcase.security;

/**
 * Created by undancer on 14-4-21.
 */
public interface UserService {

    public User loadUserByAccessToken(String accessToken);

}
