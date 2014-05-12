package com.undancer.breath.samples.showcase.service;

import com.undancer.breath.samples.showcase.entity.User;

import javax.jws.WebService;
import java.util.Collection;

/**
 * Created by undancer on 14-5-2.
 */

@WebService(targetNamespace = "http://api.boxfish.cn/")
public interface UserService {

    User loadUserByAccessToken(String accessToken);

    User loadUserByUsername(String username);

    Collection<String> getRolesByUser(User user);

    Collection<String> getPermsByUser(User user);
}
