package com.sai.openapi.service;

import com.sai.core.dto.ResultCode;
import com.sai.openapi.domain.ApiRouter;
import com.sai.web.utils.PageUtil;

import java.util.List;

public interface ApiRouterService {

    ResultCode<String> insert(ApiRouter record);

    ApiRouter selectByPrimaryKey(Long id);

    ResultCode<String> update(ApiRouter record);

    ResultCode<String> changeStatus(String merchant, Long id, Integer status);

    ResultCode<PageUtil<ApiRouter>> queryList(ApiRouter param, PageUtil<ApiRouter> pageInfo);

    List<ApiRouter> refreshRouterInfo();

    void refreshZuulRouter();
}