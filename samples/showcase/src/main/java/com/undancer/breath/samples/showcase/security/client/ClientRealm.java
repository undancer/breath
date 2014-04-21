package com.undancer.breath.samples.showcase.security.client;

import com.google.common.collect.Maps;
import com.undancer.breath.samples.showcase.security.User;
import com.undancer.breath.samples.showcase.security.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.CollectionUtils;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Map;

/**
 * Created by undancer on 14-4-17.
 */

@Named
public class ClientRealm extends AuthorizingRealm {

    @Inject
    UserService userService;

    public ClientRealm() {
        setAuthenticationTokenClass(ClientToken.class);
    }

    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.err.println("doGetAuthorizationInfo");

        AuthorizationInfo info = new SimpleAuthorizationInfo();
        return info;
//    throw new
    }

    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.err.println("doGetAuthenticationInfo");

        if (token == null || !(token instanceof ClientToken)) {
            return null;
        }
        ClientToken clientToken = (ClientToken) token;
        String accessToken = clientToken.getAccessToken();
        if (StringUtils.isBlank(accessToken)) {
            return null;
        }

        User user = userService.loadUserByAccessToken(accessToken);
        if (user != null) {
            Long userId = user.getId();
            String username = user.getUsername();
            Map<String, Object> attributes = Maps.newHashMap();

            PrincipalCollection principalCollection =
                    new SimplePrincipalCollection(
                            CollectionUtils.asList(userId, username, user, attributes),
                            getName()
                    );

            return new SimpleAuthenticationInfo(principalCollection, accessToken);
        }
        throw new AuthenticationException("token 无效");
    }
}
