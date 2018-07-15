package com.sai.openapi.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.sai.core.constants.Constants;
import com.sai.core.dto.ResultCode;
import com.sai.core.utils.RedisKey;
import com.sai.openapi.constants.AppConstants;
import com.sai.openapi.domain.ApiRouter;
import com.sai.openapi.domain.ApiRouterParam;
import com.sai.openapi.mapper.ApiRouterMapper;
import com.sai.openapi.mapper.ApiRouterParamMapper;
import com.sai.openapi.service.ApiRouterService;
import com.sai.web.service.RedisTemplateService;
import com.sai.web.utils.PageUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ApiRouterServiceImpl implements ApiRouterService {

    @Autowired
    private ApiRouterMapper apiRouterMapper;

    @Autowired
    private ApiRouterParamMapper apiRouterParamMapper;

    @Autowired
    private RedisTemplateService redisTemplateService;

    @Override
    public ResultCode<String> insert(ApiRouter record) {
        ResultCode<String> checkResult = checkApiRouter(record);
        if (!checkResult.isSuccess()) {
            return checkResult;
        }
        apiRouterMapper.insert(record);
        return ResultCode.success();
    }

    @Override
    public ResultCode<String> update(ApiRouter record) {
        ResultCode<String> checkResult = checkApiRouter(record);
        if (!checkResult.isSuccess()) {
            return checkResult;
        }

        Long id = record.getId();
        if (id == null || id < 1) {
            return ResultCode.fail("ID错误");
        }

        ApiRouter oldRecord = apiRouterMapper.selectByPrimaryKey(id);
        if (oldRecord == null) {
            return ResultCode.fail("信息不存在");
        }
        oldRecord.setId(record.getId());
        oldRecord.setProjectId(record.getProjectId());
        oldRecord.setRouterName(record.getRouterName());
        oldRecord.setServiceId(record.getServiceId());
        oldRecord.setRouterPath(record.getRouterPath());
        oldRecord.setRouterType(record.getRouterType());
        oldRecord.setCreateTime(record.getCreateTime());
        oldRecord.setUpdateTime(record.getUpdateTime());
        oldRecord.setRequestMethod(record.getRequestMethod());
        oldRecord.setRequestContentType(record.getRequestContentType());

        apiRouterMapper.updateByPrimaryKey(oldRecord);
        return ResultCode.success();
    }

    private ResultCode<String> checkApiRouter(ApiRouter record) {
        if (record == null) {
            return ResultCode.fail("信息不存在");
        }
        Long id = record.getId();
        if (id == null) {
            return ResultCode.fail(id + "不能为空且不能小于0");
        }
        Long projectId = record.getProjectId();
        if (projectId == null) {
            return ResultCode.fail(projectId + "不能为空且不能小于0");
        }
        String routerName = record.getRouterName();
        if (StringUtils.isBlank(routerName)) {
            return ResultCode.fail(routerName + "不能为空");
        }
        String serviceId = record.getServiceId();
        if (StringUtils.isBlank(serviceId)) {
            return ResultCode.fail(serviceId + "不能为空");
        }
        String routerPath = record.getRouterPath();
        if (StringUtils.isBlank(routerPath)) {
            return ResultCode.fail(routerPath + "不能为空");
        }

        Integer routerType = record.getRouterType();
        if (routerType == null) {
            return ResultCode.fail(routerType + "不能为空且不能小于0");
        }
        Date createTime = record.getCreateTime();
        if (createTime == null) {
            return ResultCode.fail(createTime + "不能为空且不能小于0");
        }
        Date updateTime = record.getUpdateTime();
        if (updateTime == null) {
            return ResultCode.fail(updateTime + "不能为空且不能小于0");
        }
        String requestMethod = record.getRequestMethod();
        if (StringUtils.isBlank(requestMethod)) {
            return ResultCode.fail(requestMethod + "不能为空");
        }
        String requestContentType = record.getRequestContentType();
        if (StringUtils.isBlank(requestContentType)) {
            return ResultCode.fail(requestContentType + "不能为空");
        }

        return ResultCode.success();
    }

    @Override
    public ApiRouter selectByPrimaryKey(Long id) {
        if (id == null || id < 1) {
            return null;
        }
        return apiRouterMapper.selectByPrimaryKey(id);
    }

    @Override
    public ResultCode<String> changeStatus(String merchant, Long id, Integer status) {
        return null;
    }

    @Override
    public ResultCode<PageUtil<ApiRouter>> queryList(ApiRouter param, PageUtil<ApiRouter> pageInfo) {
        return null;
    }

    @Override
    public List<ApiRouter> refreshRouter() {
        List<ApiRouter> apiRouterList = apiRouterMapper.selectAll();
        if (apiRouterList != null || apiRouterList.size() > 0) {
            List<ApiRouterParam> apiRouterParamList = apiRouterParamMapper.selectAll();
            if (apiRouterParamList != null || apiRouterParamList.size() > 0) {
                Map<Long, List<ApiRouterParam>> ApiRouterParamListMap = new HashMap<>();
                for (ApiRouterParam apiRouterParam : apiRouterParamList) {
                    Long routerId = apiRouterParam.getRouterId();
                    List<ApiRouterParam> routerParamList = ApiRouterParamListMap.get(routerId);
                    if (routerParamList == null) {
                        routerParamList = new ArrayList<>();
                    }
                    routerParamList.add(apiRouterParam);
                    ApiRouterParamListMap.remove(routerId);
                    ApiRouterParamListMap.put(routerId, routerParamList);
                }

                for (ApiRouter apiRouter : apiRouterList) {
                    apiRouter.setParamList(ApiRouterParamListMap.get(apiRouter.getId()));
                    redisTemplateService.mput(AppConstants.routerInfoKey, apiRouter.getRouterName(), JSONObject.toJSONString(apiRouter), -1);
                }

            }
        }
        return apiRouterList;
    }
}
