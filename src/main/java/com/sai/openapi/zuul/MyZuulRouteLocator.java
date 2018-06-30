package com.sai.openapi.zuul;

import com.sai.core.constants.Constants;
import com.sai.openapi.domain.ApiRouter;
import com.sai.openapi.service.ApiRouterService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.netflix.zuul.filters.RefreshableRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.SimpleRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MyZuulRouteLocator extends SimpleRouteLocator implements RefreshableRouteLocator {

    private ApiRouterService apiRouterService;
    private static final String ROUTER_ID_PRE = "ROUTER";

    public MyZuulRouteLocator(String servletPath, ZuulProperties properties, ApiRouterService apiRouterService) {
        super(servletPath, properties);
        this.apiRouterService = apiRouterService;
    }

    @Override
    public void refresh() {
        doRefresh();
    }

    @Override
    protected Map<String, ZuulProperties.ZuulRoute> locateRoutes() {
        LinkedHashMap<String, ZuulProperties.ZuulRoute> routesMap = new LinkedHashMap();
        routesMap.putAll(super.locateRoutes());
        List<ApiRouter> apiRouterList = apiRouterService.refreshRouter();
        if (apiRouterList != null || apiRouterList.size() > 0) {
            for (ApiRouter apiRouter : apiRouterList) {
                String path = apiRouter.getRouterName();
                if (StringUtils.isNotBlank(path)) {
                    ZuulProperties.ZuulRoute zuulRoute = new ZuulProperties.ZuulRoute();
                    zuulRoute.setId(ROUTER_ID_PRE + apiRouter.getId());
                    zuulRoute.setPath(Constants.SYMBOL_LEFT_SLASH + path + Constants.SYMBOL_LEFT_SLASH + "**");
                    zuulRoute.setServiceId(apiRouter.getServiceId());
                    zuulRoute.setUrl(apiRouter.getRouterPath());
                    zuulRoute.setStripPrefix(true);
                    zuulRoute.setRetryable(true);
                    routesMap.put(path, zuulRoute);
                }
            }
        }
        return routesMap;
    }
}
