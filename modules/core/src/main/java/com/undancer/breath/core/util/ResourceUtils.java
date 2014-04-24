package com.undancer.breath.core.util;

import com.google.common.collect.Lists;
import org.springframework.core.OrderComparator;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.List;

import static org.springframework.util.ResourceUtils.isJarURL;

/**
 * Created by undancer on 14-4-21.
 */
public class ResourceUtils {

    public static boolean exists(String name) {
        return getClassPathResource(name) != null;
    }

    public static <T> List<T> getResources(Class<T> clazz) {
        String[] names = BeanUtils.getBeanNames(clazz);
        if (names != null) {
            List<T> resources = Lists.newArrayList();
            for (String name : names) {
                T bean = BeanUtils.getBean(name, clazz);
                if (!resources.contains(bean)) {
                    resources.add(bean);
                }
            }
            OrderComparator.sort(resources);
            return resources;
        }
        return null;
    }

    public static URL getClassPathResource(String name) {
        return getClassPathResource(name, true);
    }

    public static URL getClassPathResource(String name, boolean isJar) {
        List<URL> resources = getClassPathResources(name, isJar);
        if (resources.size() != 0) {
            return resources.get(0);
        }
        return null;
    }

    public static List<URL> getClassPathResources(String name) {
        return getClassPathResources(name, true);
    }


    public static List<URL> getClassPathResources(String name, boolean isJar) {
        List<URL> list = Lists.newArrayList();
        try {
            Enumeration<URL> resources = ResourceUtils.class.getClassLoader().getResources(name);
            while (resources.hasMoreElements()) {
                URL resource = resources.nextElement();
                if (isJar || isJar == isJarURL(resource)) {
                    list.add(resource);
                }
            }
        } catch (IOException e) {
        }

        Collections.sort(list, new Comparator<URL>() {
            private static final String PREFIX = "/breath-";

            public int compare(URL o1, URL o2) {
                return o1.getPath().indexOf(PREFIX) - o2.getPath().indexOf(PREFIX);
            }
        });

        return list;
    }
}
