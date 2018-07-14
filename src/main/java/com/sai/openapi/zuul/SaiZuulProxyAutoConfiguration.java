package com.sai.openapi.zuul;

import org.springframework.cloud.netflix.zuul.ZuulProxyAutoConfiguration;
import org.springframework.cloud.netflix.zuul.filters.ProxyRequestHelper;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.cloud.netflix.zuul.filters.pre.PreDecorationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SaiZuulProxyAutoConfiguration extends ZuulProxyAutoConfiguration {

    @Bean
    @Override
    public PreDecorationFilter preDecorationFilter(RouteLocator routeLocator,
                                                   ProxyRequestHelper proxyRequestHelper) {
        return new SaiPreDecorationFilter(routeLocator, this.server.getServletPrefix(),
                this.zuulProperties, proxyRequestHelper);
    }


}
