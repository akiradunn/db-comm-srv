package com.akiradunn.dbcommsrv.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @description:
 * @author: akiradunn
 * @date: 2020/6/14 2:54
 */
@Configuration
public class MyConfiguration {
    @Bean
    public RestTemplate restTemplate(){
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new DBMappingJackson2HttpMessageConverter());
        return restTemplate;
    }
}
