package com.undancer.breath.core.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.inject.Named;
import java.util.Collection;

import static org.apache.commons.collections.CollectionUtils.isNotEmpty;

/**
 * Created by undancer on 13-12-27.
 */
@Named
public class HttpMessageConverterConfigurer implements BeanFactoryPostProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpMessageConverterConfigurer.class);

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        Collection<HttpMessageConverter> messageConverters = beanFactory.getBeansOfType(HttpMessageConverter.class).values();
        LOGGER.debug("messageConverters : {}", messageConverters.size());
        Collection<RequestMappingHandlerAdapter> requestMappingHandlerAdapters = beanFactory.getBeansOfType(RequestMappingHandlerAdapter.class).values();
        LOGGER.debug("requestMappingHandlerAdapters : {}", requestMappingHandlerAdapters.size());
        if (isNotEmpty(messageConverters) && isNotEmpty(requestMappingHandlerAdapters)) {
            for (RequestMappingHandlerAdapter adapter : requestMappingHandlerAdapters) {
                if (isNotEmpty(adapter.getMessageConverters())) {
                    for (HttpMessageConverter messageConverter : messageConverters) {
                        LOGGER.debug("messageConverter : {}", messageConverter);
                        adapter.getMessageConverters().add(messageConverter);
                    }
                }
            }
        }
    }
}
