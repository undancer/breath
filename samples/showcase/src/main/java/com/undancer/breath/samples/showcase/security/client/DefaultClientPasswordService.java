package com.undancer.breath.samples.showcase.security.client;

import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.crypto.hash.Hash;

import javax.inject.Named;

/**
 * Created by undancer on 14-4-17.
 */
@Named("passwordService")
public class DefaultClientPasswordService extends DefaultPasswordService {

    @Override
    public boolean passwordsMatch(Object plaintext, Hash saved) {
        System.err.println("passwordsMatch");
        return super.passwordsMatch(plaintext, saved);
    }

    @Override
    public boolean passwordsMatch(Object submittedPlaintext, String saved) {
        System.err.println("passwordsMatch");
        return super.passwordsMatch(submittedPlaintext, saved);
    }
}
