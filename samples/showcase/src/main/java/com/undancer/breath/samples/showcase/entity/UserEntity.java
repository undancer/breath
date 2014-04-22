package com.undancer.breath.samples.showcase.entity;

import com.undancer.breath.security.entity.User;

import javax.persistence.*;

/**
 * Created by undancer on 14-4-21.
 */
@Entity
@Table(name = "user")
public class UserEntity implements User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private String username;

    private String password;

    @Column(name = "access_token", nullable = true, unique = true)
    private String accessToken;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
