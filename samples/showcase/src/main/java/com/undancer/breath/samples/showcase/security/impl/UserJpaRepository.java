package com.undancer.breath.samples.showcase.security.impl;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by undancer on 14-4-21.
 */
public interface UserJpaRepository extends JpaRepository<User, Long> {

}
