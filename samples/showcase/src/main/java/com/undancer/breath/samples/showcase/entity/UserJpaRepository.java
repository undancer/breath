package com.undancer.breath.samples.showcase.entity;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by undancer on 14-4-21.
 */
public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {

    public UserEntity findOneByUsername(String username);

    public UserEntity findOneByAccessToken(String accessToken);

}
