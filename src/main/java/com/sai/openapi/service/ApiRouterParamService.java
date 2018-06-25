package com.sai.openapi.service;

import com.sai.core.dto.ResultCode;
import com.sai.openapi.domain.ApiRouterParam;
import com.sai.web.utils.PageUtil;

import java.util.List;

public interface ApiRouterParamService {

    ResultCode<String> insert(ApiRouterParam record);

    ApiRouterParam selectByPrimaryKey(Long id);

    List<ApiRouterParam> selectList(ApiRouterParam example, Integer size);

    ResultCode<String> update(ApiRouterParam record);

    ResultCode<String> changeStatus(String merchant, Long id, Integer status);

    ResultCode<PageUtil<ApiRouterParam>> queryList(ApiRouterParam param, PageUtil<ApiRouterParam> pageInfo);
}