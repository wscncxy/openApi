package com.sai.openapi.zuul;

import org.springframework.cloud.netflix.ribbon.support.RibbonRequestCustomizer;
import org.springframework.cloud.netflix.zuul.filters.ProxyRequestHelper;
import org.springframework.cloud.netflix.zuul.filters.route.RibbonCommandFactory;
import org.springframework.cloud.netflix.zuul.filters.route.RibbonRoutingFilter;

import java.util.List;

public class SaiRibbonRoutingFilter extends RibbonRoutingFilter {
    public SaiRibbonRoutingFilter(ProxyRequestHelper helper,
                                  RibbonCommandFactory<?> ribbonCommandFactory,
                                  List<RibbonRequestCustomizer> requestCustomizers) {
        super(helper, ribbonCommandFactory, requestCustomizers);
    }

    public SaiRibbonRoutingFilter(RibbonCommandFactory<?> ribbonCommandFactory) {
        this(new ProxyRequestHelper(), ribbonCommandFactory, null);
    }

    @Override
    public boolean shouldFilter() {
       return false;
    }
}
