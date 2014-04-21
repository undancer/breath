package com.undancer.breath.samples.showcase.entity;

import com.undancer.breath.samples.showcase.security.token.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by undancer on 14-4-21.
 */
public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {

    public User findOneByAccessToken(String accessToken);

}
