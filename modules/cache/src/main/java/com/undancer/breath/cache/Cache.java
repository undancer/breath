package com.undancer.breath.cache;

import java.util.concurrent.Callable;

/**
 * Created by undancer on 14-4-19.
 */
public interface Cache {

    public void set(String key, Object value);

    public Object get(String key);

    public <T> T get(String key, Callable<T> callback);
}
