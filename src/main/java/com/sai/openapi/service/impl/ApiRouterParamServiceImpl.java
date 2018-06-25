package com.sai.openapi.service.impl;

import com.sai.openapi.domain.ApiRouterParam;
import com.sai.openapi.mapper.ApiRouterParamMapper;
import com.sai.openapi.service.ApiRouterParamService;
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
public class ApiRouterParamServiceImpl implements ApiRouterParamService {

    @Autowired
    private ApiRouterParamMapper apiRouterParamMapper;


    @Override
    public ResultCode<String> insert(ApiRouterParam record) {
        ResultCode<String> checkResult = checkApiRouterParam(record);
        if (!checkResult.isSuccess()) {
            return checkResult;
        }
        apiRouterParamMapper.insert(record);
        return ResultCode.success();
    }

    @Override
    public ResultCode<String> update(ApiRouterParam record) {
        ResultCode<String> checkResult = checkApiRouterParam(record);
        if (!checkResult.isSuccess()) {
            return checkResult;
        }

        Long id = record.getId();
        if (id == null || id < 1) {
            return ResultCode.fail("ID错误");
        }

        ApiRouterParam oldRecord = ApiRouterParamMapper.selectByPrimaryKey(id);
        if (oldRecord == null) {
            return ResultCode.fail("信息不存在");
        }
            oldRecord.setId(record.getId());
            oldRecord.setRouterId(record.getRouterId());
            oldRecord.setParamKey(record.getParamKey());
            oldRecord.setIsNeed(record.getIsNeed());
            oldRecord.setDataType(record.getDataType());
            oldRecord.setCreateTime(record.getCreateTime());
            oldRecord.setUpdateTime(record.getUpdateTime());

        ApiRouterParamMapper.updateByPrimaryKey(oldRecord);
        return ResultCode.success();
    }

    private ResultCode<String> checkApiRouterParam(ApiRouterParam record) {
        if (record == null) {
            return ResultCode.fail("信息不存在");
        }
                Long id = record.getId();
                if (id == null) {
                    return ResultCode.fail(id+"不能为空且不能小于0");
                }
                Long routerId = record.getRouterId();
                if (routerId == null) {
                    return ResultCode.fail(routerId+"不能为空且不能小于0");
                }
                String paramKey = record.getParamKey();
                if (StringUtils.isBlank(paramKey)) {
                    return ResultCode.fail(paramKey+"不能为空");
                }
                Byte isNeed = record.getIsNeed();
                if (isNeed == null) {
                    return ResultCode.fail(isNeed+"不能为空且不能小于0");
                }
                String dataType = record.getDataType();
                if (StringUtils.isBlank(dataType)) {
                    return ResultCode.fail(dataType+"不能为空");
                }
                Date createTime = record.getCreateTime();
                if (createTime == null) {
                    return ResultCode.fail(createTime+"不能为空且不能小于0");
                }
                Date updateTime = record.getUpdateTime();
                if (updateTime == null) {
                    return ResultCode.fail(updateTime+"不能为空且不能小于0");
                }

        return ResultCode.success();
    }

    @Override
    public ApiRouterParam selectByPrimaryKey(Long id) {
        if (id == null || id < 1) {
            return null;
        }
        return apiRouterParamMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<ApiRouterParam> selectList(ApiRouterParam example, Integer size) {
        if (size == null || size < 1 || size > 1000) {
            size = 10;
        }
        Map<String, Object> param = BeanUtils.toMap(example);
        param.put("pageSize", size);
        return ApiRouterParamMapper.selectList(param);
    }

    @Override
    public ResultCode<Pager<ApiRouterParam>> queryList(ApiRouterParam bean, Pager<ApiRouterParam> pageInfo) {
        if (bean == null) {
            return ResultCode.fail("参数不能为空");
        }
        Map<String, Object> param = BeanUtils.toMap(bean);
        param.put("pageSize", pageInfo.getPageSize());
        param.put("start", pageInfo.getStart());
        List<ApiRouterParam> list = apiRouterParamMapper.selectPage(param);
        pageInfo.setList(list);
        Long count = apiRouterParamMapper.selectCount(param);
        pageInfo.setTotalCount(count.intValue());
        return ResultCode.success(pageInfo);
    }

    public ResultCode<String> changeStatus(Long id, Integer status) {
        ApiRouterParam oldRecord = apiRouterParamMapper.selectByPrimaryKey(id);
        if (oldRecord == null) {
            return ResultCode.fail("信息不存在");
        }

        oldRecord.setStatus(status);
        apiRouterParamMapper.updateByPrimaryKey(oldRecord);
        return ResultCode.success();
    }

}
