package com.undancer.breath.cache;

/**
 * Created by undancer on 14-4-19.
 */
public interface Cache {

    public void set(String key, Object value);

    public Object get(String key);
}
