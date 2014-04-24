package com.undancer.breath.samples.showcase.entity.jpa;

import com.undancer.breath.samples.showcase.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by undancer on 14-4-21.
 */
public interface UserJpaRepository extends JpaRepository<User, Long> {

    public User findOneByUsername(String username);

    public User findOneByAccessToken(String accessToken);

}
