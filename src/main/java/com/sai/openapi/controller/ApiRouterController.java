package com.sai.openapi.controller;

import com.alibaba.fastjson.JSONObject;
import com.sai.core.dto.ResultCode;
import com.sai.openapi.domain.ApiRouter;
import com.sai.openapi.service.ApiRouterService;
import com.sai.openapi.zuul.MyZuulRouteLocator;
import com.sai.web.controller.BaseController;
import com.sai.web.dto.ResponseCode;
import com.sai.web.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.RoutesRefreshedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/apiRouter")
public class ApiRouterController extends BaseController {

    @Autowired
    private ApiRouterService apiRouterService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private MyZuulRouteLocator myZuulRouteLocator;

    @RequestMapping(value = "page", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public ResponseCode page(@RequestBody JSONObject jsonObject) throws IOException {
        ApiRouter apiRouter = jsonObject.toJavaObject(ApiRouter.class);
        Integer pageSize = jsonObject.getInteger("pageSize");
        if (pageSize == null || pageSize < 1 || pageSize > 100) {
            pageSize = 20;
        }
        Integer pageNum = jsonObject.getInteger("pageNo");
        if (pageNum == null || pageNum < 1) {
            pageNum = 1;
        }
        PageUtil<ApiRouter> page = new PageUtil<ApiRouter>(pageNum, pageSize);
        ResultCode<PageUtil<ApiRouter>> result = apiRouterService.queryList(apiRouter, page);
        return this.getSuccessResult(result.getData());
    }

    @RequestMapping(value = "add", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public ResponseCode add(@RequestBody ApiRouter apiRouter) throws IOException {
        ResultCode resultCode = apiRouterService.insert(apiRouter);
        return getResult(resultCode);
    }

    @RequestMapping(value = "{id}", produces = "application/json;charset=UTF-8")
    public ResponseCode get(@PathVariable("id") Long id) throws IOException {
        ApiRouter apiRouter = apiRouterService.selectByPrimaryKey(id);
        return getSuccessResult(apiRouter);
    }

    @RequestMapping(value = "update/{id}", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public ResponseCode update(@PathVariable("id") Long id, @RequestBody ApiRouter apiRouter) throws IOException {
        apiRouter.setId(id);
        ResultCode resultCode = apiRouterService.update(apiRouter);
        return getResult(resultCode);
    }

    @RequestMapping(value = "refreshRouter", method = RequestMethod.GET)
    public ResponseCode refreshRouter() throws IOException {
        RoutesRefreshedEvent routesRefreshedEvent = new RoutesRefreshedEvent(myZuulRouteLocator);
        publisher.publishEvent(routesRefreshedEvent);
        return getSuccessResult("刷新成功");
    }

}
