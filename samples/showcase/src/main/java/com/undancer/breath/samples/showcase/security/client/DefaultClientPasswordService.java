package com.undancer.breath.samples.showcase.security.client;

import org.apache.shiro.authc.credential.DefaultPasswordService;

import javax.inject.Named;

/**
 * Created by undancer on 14-4-17.
 */
@Named("passwordService")
public class DefaultClientPasswordService extends DefaultPasswordService {


}
