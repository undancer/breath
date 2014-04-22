package com.undancer.breath.cache.memcached;

import com.undancer.breath.cache.Cache;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;

import java.net.ConnectException;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeoutException;

/**
 * Created by undancer on 14-4-19.
 */
public class MemcachedCache implements Cache {

    private MemcachedClient memcachedClient;

    public void setMemcachedClient(MemcachedClient memcachedClient) {
        this.memcachedClient = memcachedClient;
    }

    public void set(String key, Object value) {
        try {
            memcachedClient.set(key, 60 * 60 * 1, value);
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (MemcachedException e) {
            e.printStackTrace();
        }
    }

    public Object get(String key) {
        try {
            return memcachedClient.get(key);
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (MemcachedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public <T> T get(String key, Callable<T> callback) {
        T value = null;
        try {
            value = (T) get(key);
            if (value == null) {
                System.out.println("通过回调获取");
                value = callback.call();
                set(key, value);
            } else {
                System.out.println("通过缓存获取");
            }
        } catch (ConnectException ce) {
            try {
                value = callback.call();
            } catch (Exception e) {
            }

        } catch (Exception e) {

        }
        return value;
    }
}
