package com.undancer.breath.security.client;

import com.google.common.collect.Maps;
import com.undancer.breath.security.entity.User;
import com.undancer.breath.security.service.UserService;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Map;

/**
 * Created by undancer on 14-4-17.
 */

@Named
public class ClientRealm extends AuthorizingRealm {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientRealm.class);

    @Inject
    UserService userService;

    public ClientRealm() {
        setAuthenticationTokenClass(ClientToken.class);
    }

    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        LOGGER.debug("获取用户[{}]的权限", principals.oneByType(String.class));
        User user = principals.oneByType(User.class);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addRoles(userService.getRolesByUser(user));
        info.addStringPermissions(userService.getRolesByUser(user));
        info.addStringPermissions(userService.getPermsByUser(user));
        return info;
    }

    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        LOGGER.debug("获取token对应的用户信息");
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
