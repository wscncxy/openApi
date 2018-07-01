package com.sai.openapi.mapper;

import com.sai.openapi.domain.ApiRouter;

import java.util.List;
import java.util.Map;

public interface ApiRouterMapper {
    int insert(ApiRouter record);

    ApiRouter selectByPrimaryKey(Long id);

    int updateByPrimaryKey(ApiRouter record);

    List<ApiRouter> selectPage(Map<String, Object> param);

    List<ApiRouter> selectAll();
}