package com.undancer.breath.samples.showcase.security.impl;

import javax.persistence.*;

/**
 * Created by undancer on 14-4-21.
 */

@Entity
@Table(name = "user")
public class User implements com.undancer.breath.samples.showcase.security.User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;

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
}
