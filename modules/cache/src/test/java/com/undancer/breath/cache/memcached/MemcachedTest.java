package com.undancer.breath.cache.memcached;

import com.undancer.breath.cache.Cache;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

/**
 * Created by undancer on 14-4-19.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring.xml")
public class MemcachedTest {

    @Inject
    Cache cache;

    @Test
    public void test() {
        String key = "name";
        String value = "undancer";
        cache.set(key, value);
        Assert.assertEquals(cache.get(key), value);
    }
}
