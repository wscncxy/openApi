package com.sai.openapi.zuul;

import com.alibaba.fastjson.JSONObject;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.http.HttpServletRequestWrapper;
import com.sai.core.dto.ResultCode;
import com.sai.openapi.constants.AppConstants;
import com.sai.openapi.domain.ApiRouter;
import com.sai.web.service.RedisTemplateService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

@Component
public class ZuulAuthFilter extends ZuulFilter {

    @Autowired
    private RedisTemplateService redisTemplateService;

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
        String method = requestContext.getRequest().getParameter("method");
        boolean sendZuulResponse = false;
        boolean zuulAuthOk = false;
        if (StringUtils.isNotBlank(method)) {
            String routerInfoStr = redisTemplateService.mget(AppConstants.routerInfoKey, method);
            if (StringUtils.isNotBlank(routerInfoStr)) {
                ApiRouter apiRouter = JSONObject.toJavaObject(JSONObject.parseObject(routerInfoStr), ApiRouter.class);
                sendZuulResponse = true;
                zuulAuthOk = false;
                requestContext.setRequest(new HttpServletRequestWrapper(requestContext.getRequest()) {
                    @Override
                    public String getRequestURI() {
                        return "/testChange";
                    }
                });
            }
        }
        requestContext.setSendZuulResponse(sendZuulResponse);
        requestContext.set("zuulAuth", zuulAuthOk);
        if (!sendZuulResponse) {
            requestContext.setResponseBody(JSONObject.toJSONString(ResultCode.fail("404")));
            requestContext.getResponse().setContentType("application/json;charset=UTF-8");
        }
        System.out.println(requestContext.getRequest().getRequestURI());
        return null;
    }
}
