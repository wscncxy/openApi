package com.sai.openapi.zuul;

import org.springframework.cloud.netflix.zuul.ZuulServerAutoConfiguration;
import org.springframework.cloud.netflix.zuul.filters.post.SendResponseFilter;
import org.springframework.cloud.netflix.zuul.filters.route.SendForwardFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SaiZuulServerAutoConfiguration extends ZuulServerAutoConfiguration {
    @Bean
    @Override
    public SendResponseFilter sendResponseFilter() {
        return new SaiSendResponseFilter();
    }

    @Bean
    @Override
    public SendForwardFilter sendForwardFilter() {
        return new SaiSendForwardFilter();
    }
}
