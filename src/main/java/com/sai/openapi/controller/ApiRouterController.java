package com.sai.openapi.controller;

import com.alibaba.fastjson.JSONObject;
import com.sai.core.dto.ResultCode;
import com.sai.openapi.domain.ApiRouter;
import com.sai.openapi.service.ApiRouterService;
import com.sai.web.controller.BaseController;
import com.sai.web.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/apiRouter")
public class ApiRouterController extends BaseController {

    @Autowired
    private ApiRouterService apiRouterService;

    @RequestMapping(value = "page", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public String page(@RequestBody JSONObject jsonObject) throws IOException {
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
    public String add(@RequestBody ApiRouter apiRouter) throws IOException {
        ResultCode resultCode = apiRouterService.insert(apiRouter);
        return getResult(resultCode);
    }

    @RequestMapping(value = "{id}", produces = "application/json;charset=UTF-8")
    public String get(@PathVariable("id") Long id) throws IOException {
        ApiRouter apiRouter = apiRouterService.selectByPrimaryKey(id);
        return getSuccessResult(apiRouter);
    }

    @RequestMapping(value = "update/{id}", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public String update(@PathVariable("id") Long id, @RequestBody ApiRouter apiRouter) throws IOException {
        apiRouter.setId(id);
        ResultCode resultCode = apiRouterService.update(apiRouter);
        return getResult(resultCode);
    }


}
