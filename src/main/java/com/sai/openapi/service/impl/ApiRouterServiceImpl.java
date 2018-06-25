package com.sai.openapi.service.impl;

import com.sai.openapi.domain.ApiRouter;
import com.sai.openapi.mapper.ApiRouterMapper;
import com.sai.openapi.service.ApiRouterService;
import com.sai.openapi.utils.BeanUtils;
import com.sai.openapi.utils.Pager;
import com.sai.openapi.utils.ResultCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ApiRouterServiceImpl implements ApiRouterService {

    @Autowired
    private ApiRouterMapper apiRouterMapper;


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

        ApiRouter oldRecord = ApiRouterMapper.selectByPrimaryKey(id);
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

        ApiRouterMapper.updateByPrimaryKey(oldRecord);
        return ResultCode.success();
    }

    private ResultCode<String> checkApiRouter(ApiRouter record) {
        if (record == null) {
            return ResultCode.fail("信息不存在");
        }
                Long id = record.getId();
                if (id == null) {
                    return ResultCode.fail(id+"不能为空且不能小于0");
                }
                Long projectId = record.getProjectId();
                if (projectId == null) {
                    return ResultCode.fail(projectId+"不能为空且不能小于0");
                }
                String routerName = record.getRouterName();
                if (StringUtils.isBlank(routerName)) {
                    return ResultCode.fail(routerName+"不能为空");
                }
                String serviceId = record.getServiceId();
                if (StringUtils.isBlank(serviceId)) {
                    return ResultCode.fail(serviceId+"不能为空");
                }
                String routerPath = record.getRouterPath();
                if (StringUtils.isBlank(routerPath)) {
                    return ResultCode.fail(routerPath+"不能为空");
                }
                int routerType = record.getRouterType();
                if (routerType == null) {
                    return ResultCode.fail(routerType+"不能为空且不能小于0");
                }
                Date createTime = record.getCreateTime();
                if (createTime == null) {
                    return ResultCode.fail(createTime+"不能为空且不能小于0");
                }
                Date updateTime = record.getUpdateTime();
                if (updateTime == null) {
                    return ResultCode.fail(updateTime+"不能为空且不能小于0");
                }
                String requestMethod = record.getRequestMethod();
                if (StringUtils.isBlank(requestMethod)) {
                    return ResultCode.fail(requestMethod+"不能为空");
                }
                String requestContentType = record.getRequestContentType();
                if (StringUtils.isBlank(requestContentType)) {
                    return ResultCode.fail(requestContentType+"不能为空");
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
    public List<ApiRouter> selectList(ApiRouter example, Integer size) {
        if (size == null || size < 1 || size > 1000) {
            size = 10;
        }
        Map<String, Object> param = BeanUtils.toMap(example);
        param.put("pageSize", size);
        return ApiRouterMapper.selectList(param);
    }

    @Override
    public ResultCode<Pager<ApiRouter>> queryList(ApiRouter bean, Pager<ApiRouter> pageInfo) {
        if (bean == null) {
            return ResultCode.fail("参数不能为空");
        }
        Map<String, Object> param = BeanUtils.toMap(bean);
        param.put("pageSize", pageInfo.getPageSize());
        param.put("start", pageInfo.getStart());
        List<ApiRouter> list = apiRouterMapper.selectPage(param);
        pageInfo.setList(list);
        Long count = apiRouterMapper.selectCount(param);
        pageInfo.setTotalCount(count.intValue());
        return ResultCode.success(pageInfo);
    }

    public ResultCode<String> changeStatus(Long id, Integer status) {
        ApiRouter oldRecord = apiRouterMapper.selectByPrimaryKey(id);
        if (oldRecord == null) {
            return ResultCode.fail("信息不存在");
        }

        oldRecord.setStatus(status);
        apiRouterMapper.updateByPrimaryKey(oldRecord);
        return ResultCode.success();
    }

}
