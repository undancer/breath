package com.undancer.breath.security.userdetails;

/**
 * Created by undancer on 14-4-23.
 */
public interface UserDetails {

    public Long getId();

    public String getUsername();

    public String getPassword();

    public void setPassword(String password);

}
