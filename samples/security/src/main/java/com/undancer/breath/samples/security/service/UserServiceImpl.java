package com.undancer.breath.samples.security.service;

import com.undancer.breath.security.userdetails.UserDetails;
import com.undancer.breath.security.userdetails.UserDetailsService;

import javax.inject.Named;
import java.util.Collection;

/**
 * Created by undancer on 14-4-22.
 */

@Named
public class UserServiceImpl implements UserDetailsService {

    public UserDetails loadUserByAccessToken(String accessToken) {
        System.out.println("loadUserByAccessToken");

        return null;
    }

    public UserDetails loadUserByUsername(String username) {
        System.out.println("loadUserByAccessToken");
        return null;
    }

    public void save(UserDetails user) {
        System.out.println("loadUserByAccessToken");
    }

    public Collection<String> getRolesByUser(UserDetails user) {
        System.out.println("loadUserByAccessToken");
        return null;
    }

    public Collection<String> getPermsByUser(UserDetails user) {
        System.out.println("loadUserByAccessToken");
        return null;
    }
}
