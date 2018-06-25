package com.sai.openapi.controller;

import com.alibaba.fastjson.JSONObject;
import com.sai.openapi.domain.ApiRouterParam;
import com.sai.openapi.service.ApiRouterParamService;
import com.sai.web.utils.PageUtil;
import com.sai.core.dto.ResultCode;
import com.sai.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/apiRouterParam")
public class ApiRouterParamController extends BaseController {

    @Autowired
    private ApiRouterParamService apiRouterParamService;

    @RequestMapping(value = "page", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public String page(@RequestBody JSONObject jsonObject) throws IOException {
        ApiRouterParam apiRouterParam = jsonObject.toJavaObject(ApiRouterParam.class);
        Integer pageSize = jsonObject.getInteger("pageSize");
        if (pageSize == null || pageSize < 1 || pageSize > 100) {
            pageSize = 20;
        }
        Integer pageNum = jsonObject.getInteger("pageNo");
        if (pageNum == null || pageNum < 1) {
            pageNum = 1;
        }
        PageUtil<ApiRouterParam> page = new PageUtil<ApiRouterParam>(pageNum, pageSize);
        ResultCode<PageUtil<ApiRouterParam>> result = apiRouterParamService.queryList(apiRouterParam, page);
        return this.getSuccessResult(result.getData());
    }

    @RequestMapping(value = "add", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public String add(@RequestBody ApiRouterParam apiRouterParam) throws IOException {
        ResultCode resultCode = apiRouterParamService.insert(apiRouterParam);
        return getResult(resultCode);
    }

    @RequestMapping(value = "{id}", produces = "application/json;charset=UTF-8")
    public String get(@PathVariable("id") Long id) throws IOException {
        ApiRouterParam apiRouterParam = apiRouterParamService.selectByPrimaryKey(id);
        return getSuccessResult(apiRouterParam);
    }

    @RequestMapping(value = "update/{id}", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public String update(@PathVariable("id") Long id, @RequestBody ApiRouterParam apiRouterParam) throws IOException {
        apiRouterParam.setId(id);
        ResultCode resultCode = apiRouterParamService.update(apiRouterParam);
        return getResult(resultCode);
    }


}
