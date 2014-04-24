package com.undancer.breath.security.userdetails;


import java.util.Collection;

/**
 * Created by undancer on 14-4-21.
 */
public interface UserDetailsService {

    public <U extends UserDetails> U loadUserByAccessToken(String accessToken);

    public <U extends UserDetails> U loadUserByUsername(String username);

    public <U extends UserDetails> Collection<String> getRolesByUser(U user);

    public <U extends UserDetails> Collection<String> getPermsByUser(U user);

    public <U extends UserDetails> void save(U user);

}
