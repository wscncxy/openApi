package com.sai.openapi.config;

import com.sai.openapi.service.ApiRouterService;
import com.sai.openapi.zuul.MyZuulRouteLocator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ZuulConfig {
    @Autowired
    private ZuulProperties zuulProperties;
    @Autowired
    private ServerProperties server;
    @Autowired
    private ApiRouterService apiRouterService;

    @Bean
    public MyZuulRouteLocator routeLocator() {
        return new MyZuulRouteLocator(this.server.getServletPrefix(), this.zuulProperties, apiRouterService);
    }
}
