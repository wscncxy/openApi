package com.sai.openapi.zuul;

import com.netflix.zuul.ZuulFilter;
import org.springframework.stereotype.Component;


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
        System.out.println("test cloud zuul1");
        return null;
    }
}
