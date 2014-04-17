package com.undancer.breath.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.AbstractFactoryBean;

/**
 * Created by undancer on 14-4-17.
 */
public class ObjectMapperFactoryBean extends AbstractFactoryBean<ObjectMapper> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ObjectMapperFactoryBean.class);

    public Class<?> getObjectType() {
        return ObjectMapper.class;
    }

    protected ObjectMapper createInstance() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        LOGGER.debug("objectMapper : {}", ToStringBuilder.reflectionToString(objectMapper));
        return objectMapper;
    }
}
