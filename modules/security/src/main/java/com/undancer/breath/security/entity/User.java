package com.undancer.breath.security.entity;

/**
 * Created by undancer on 14-4-22.
 */
public interface User {

    public Long getId();

    public String getUsername();

    public String getPassword();

    public void setPassword(String password);

}
