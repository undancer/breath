package com.undancer.breath.security.util;

import com.undancer.breath.security.userdetails.UserDetails;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;

/**
 * Created by undancer on 14-4-23.
 */
public class SecurityUtils {

    public static Subject getSubject() {
        return org.apache.shiro.SecurityUtils.getSubject();
    }

    public static PrincipalCollection getPrincipals() {
        return getSubject().getPrincipals();
    }

    public static Long currentUserId() {
        return getPrincipals().oneByType(Long.class);
    }

    public static String currentUsername() {
        return getPrincipals().oneByType(String.class);
    }

    public static UserDetails currentUser() {
        return getPrincipals().oneByType(UserDetails.class);
    }
}
