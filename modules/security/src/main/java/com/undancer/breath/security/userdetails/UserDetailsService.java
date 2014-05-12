package com.undancer.breath.security.userdetails;


import java.util.Collection;

/**
 * Created by undancer on 14-4-21.
 */
public interface UserDetailsService<U extends UserDetails> {

    public U loadUserByAccessToken(String accessToken);

    public U loadUserByUsername(String username);

    public Collection<String> getRolesByUser(U user);

    public Collection<String> getPermsByUser(U user);
}
