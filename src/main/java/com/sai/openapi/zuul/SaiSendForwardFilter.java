package com.sai.openapi.zuul;

import com.netflix.zuul.context.RequestContext;
import org.springframework.cloud.netflix.zuul.filters.route.SendForwardFilter;
import org.springframework.util.ReflectionUtils;

import javax.servlet.RequestDispatcher;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.FORWARD_TO_KEY;

public class SaiSendForwardFilter extends SendForwardFilter {
    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        return ctx.sendZuulResponse() && ctx.containsKey(FORWARD_TO_KEY)
                && !ctx.getBoolean(SEND_FORWARD_FILTER_RAN, false);
    }

}
