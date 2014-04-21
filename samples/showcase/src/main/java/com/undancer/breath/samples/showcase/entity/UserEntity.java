package com.undancer.breath.samples.showcase.entity;

import com.undancer.breath.samples.showcase.security.token.User;

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
}
