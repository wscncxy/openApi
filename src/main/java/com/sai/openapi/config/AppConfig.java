package com.sai.openapi.config;

import com.sai.openapi.zuul.SaiPreDecorationFilter;
import com.sai.openapi.zuul.SaiRibbonRoutingFilter;
import com.sai.web.utils.SaiRestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.ribbon.support.RibbonRequestCustomizer;
import org.springframework.cloud.netflix.zuul.filters.ProxyRequestHelper;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.filters.pre.PreDecorationFilter;
import org.springframework.cloud.netflix.zuul.filters.route.RibbonCommandFactory;
import org.springframework.cloud.netflix.zuul.filters.route.RibbonRoutingFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
@Configuration
public class AppConfig {

    @Autowired
    protected ZuulProperties zuulProperties;

    @Autowired
    protected ServerProperties server;

    @SuppressWarnings("rawtypes")
    @Autowired(required = false)
    private List<RibbonRequestCustomizer> requestCustomizers = Collections.emptyList();

    @Bean
    @LoadBalanced
    public RestTemplate geRestTemplate() {
        return new SaiRestTemplate();
    }

}
