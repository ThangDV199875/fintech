package com.example.fintech.configs;

import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class DozerMapperConfig {
    @Bean
    DozerBeanMapper mapper() {
        DozerBeanMapper beanMapper = new DozerBeanMapper();
        return beanMapper;
    }
}
