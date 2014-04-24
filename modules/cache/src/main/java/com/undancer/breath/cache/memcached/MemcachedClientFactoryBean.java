package com.undancer.breath.cache.memcached;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import org.springframework.beans.factory.config.AbstractFactoryBean;

/**
 * Created by undancer on 14-4-22.
 */
public class MemcachedClientFactoryBean extends AbstractFactoryBean<MemcachedClient> {

    private String servers;

    public Class<?> getObjectType() {
        return MemcachedClient.class;
    }

    protected MemcachedClient createInstance() throws Exception {
        MemcachedClientBuilder memcachedClientBuilder = new XMemcachedClientBuilder(servers);
        return memcachedClientBuilder.build();
    }

    public void setServers(String servers) {
        this.servers = servers;
    }
}
