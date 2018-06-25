package com.sai.openapi.zuul;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.http.HttpServletRequestWrapper;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

@Component
public class ZuulAuthFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        System.out.println("test cloud zuul");
        RequestContext requestContext = RequestContext.getCurrentContext();
        System.out.println(requestContext.getRequest().getRequestURI());
        requestContext.setRequest(new HttpServletRequestWrapper(requestContext.getRequest()) {
            @Override
            public String getRequestURI() {
                return "/testApi";
            }
        });
        System.out.println(requestContext.getRequest().getRequestURI());
        return null;
    }
}
