package com.undancer.breath.core.util;

import com.google.common.collect.Lists;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.OrderComparator;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Map;

/**
 * Created by undancer on 14-4-21.
 */
@Named
public class BeanUtils implements BeanFactoryPostProcessor {

    @Inject
    private static ConfigurableListableBeanFactory factory;

    public static Object getBean(String name) {
        return getBean(factory, name);
    }

    public static Object getBean(ConfigurableListableBeanFactory factory, String name) {
        return factory.getBean(name);
    }

    public static <T> T getBean(String name, Class<T> type) {
        return getBean(factory, name, type);
    }

    public static <T> T getBean(ConfigurableListableBeanFactory factory, String name, Class<T> type) {
        return factory.getBean(name, type);
    }


    public static <T> String[] getBeanNames(Class<T> clazz) {
        return getBeanNames(factory, clazz);
    }

    public static <T> String[] getBeanNames(ConfigurableListableBeanFactory factory, Class<T> clazz) {
        return factory.getBeanNamesForType(clazz);
    }

    public static <T> T getBeanOfType(Class<T> type) {
        return getBeanOfType(factory, type);
    }

    public static <T> T getBeanOfType(ConfigurableListableBeanFactory factory, Class<T> type) {
        Map<String, T> beans = getBeansOfType(factory, type);
        List<T> values = Lists.newArrayList(beans.values());
        if (values.size() != 0) {
            OrderComparator.sort(values);
            return values.get(0);
        }
        return null;
    }

    public static <T> Map<String, T> getBeansOfType(Class<T> type) {
        return getBeansOfType(factory, type);
    }

    public static <T> Map<String, T> getBeansOfType(ConfigurableListableBeanFactory factory, Class<T> type) {
        return factory.getBeansOfType(type);
    }

    public void postProcessBeanFactory(ConfigurableListableBeanFactory factory) throws BeansException {
        BeanUtils.factory = factory;
    }
}
