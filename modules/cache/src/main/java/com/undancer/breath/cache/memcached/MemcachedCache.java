package com.undancer.breath.cache.memcached;

import com.undancer.breath.cache.Cache;
import net.spy.memcached.MemcachedClient;

/**
 * Created by undancer on 14-4-19.
 */
public class MemcachedCache implements Cache {

    private MemcachedClient memcachedClient;

    public void setMemcachedClient(MemcachedClient memcachedClient) {
        this.memcachedClient = memcachedClient;
    }

    @Override
    public void set(String key, Object value) {
        memcachedClient.set(key, 60 * 60 * 1, value);
    }

    @Override
    public Object get(String key) {
        return memcachedClient.get(key);
    }
}
