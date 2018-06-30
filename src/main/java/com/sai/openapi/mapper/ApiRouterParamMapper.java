package com.sai.openapi.mapper;

import com.sai.openapi.domain.ApiRouterParam;

import java.util.List;
import java.util.Map;

public interface ApiRouterParamMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ApiRouterParam record);

    ApiRouterParam selectByPrimaryKey(Long id);

    int updateByPrimaryKey(ApiRouterParam record);

    List<ApiRouterParam> selectPage(Map<String, Object> param);

    Long selectCount(Map<String, Object> param);

    List<ApiRouterParam> selectList(Map<String, Object> param);

    List<ApiRouterParam> selectAll();
}