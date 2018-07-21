package com.sai.openapi.zuul;

import com.alibaba.fastjson.JSONObject;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.http.HttpServletRequestWrapper;
import com.sai.core.constants.Constants;
import com.sai.core.dto.ResultCode;
import com.sai.core.utils.RedisKey;
import com.sai.core.utils.StringUtil;
import com.sai.openapi.constants.AppConstants;
import com.sai.openapi.domain.ApiRouter;
import com.sai.web.service.RedisTemplateService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

@Component
public class ZuulAuthFilter extends ZuulFilter {

    @Autowired
    private RedisTemplateService redisTemplateService;

    @Override
    public String filterType() {
        return PRE_TYPE;
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
        HttpServletRequest requestContextRequest = requestContext.getRequest();
        boolean sendZuulResponse = false;
        boolean zuulAuthOk = false;
        String uri = requestContextRequest.getRequestURI();
        if (uri.equals("/api")) {
            String method = requestContextRequest.getParameter("method");
            String routerInfoStr = redisTemplateService.mget(AppConstants.routerInfoKey, method);
            if (StringUtils.isNotBlank(routerInfoStr)) {
                final ApiRouter apiRouter = JSONObject.toJavaObject(JSONObject.parseObject(routerInfoStr), ApiRouter.class);
                if (apiRouter.isNeedLogin()) {
                    String loginToken = requestContextRequest.getHeader("loginToken");
                    String loginUserId = requestContextRequest.getHeader("loginUserId");
                    if (StringUtils.isNotBlank(loginToken) && StringUtils.isNotBlank(loginUserId)) {
                        //这里要做配置化
                        String userLoginTokenKey = RedisKey.create().setProgam(Constants.SAI_PROGRAM_OPENAPI).setOperation("userLoginToken").setSign("user-" + loginUserId).build();
                        String userLoginToken = redisTemplateService.get(userLoginTokenKey);
                        if (StringUtils.equals(loginToken, userLoginToken)) {
                            zuulAuthOk = true;
                        }
                    }
                }
                final String routerName;
                if (zuulAuthOk) {
                    routerName = apiRouter.getRouterName();
                } else {
                    routerName = "sai.login";
                }
                requestContext.set("SAI_ROUTER_TYPE", apiRouter.getRouterType());
                sendZuulResponse = true;
                requestContext.setRequest(new HttpServletRequestWrapper(requestContext.getRequest()) {
                    @Override
                    public String getRequestURI() {
                        return "/router/" + routerName;
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
