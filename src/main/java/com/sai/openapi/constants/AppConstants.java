package com.sai.openapi.constants;

import com.sai.core.constants.Constants;
import com.sai.core.utils.RedisKey;

public class AppConstants {

    public static final String REDIS_OPERATION_ROUTERCACHE = "routerCache";
    public static final String REDIS_OPERATION_ROUTERCACHE_VERSION = "version";

    public static final String REDIS_SIGN_ROUTERCACHE = "routerCacheSign";
    public static final String REDIS_SIGN_ROUTERCACHE_VERSION = "version";

    public static final String apiRouterRedisKey = RedisKey.create().setProgam(Constants.SAI_PROGRAM_OPENAPI).setOperation(AppConstants.REDIS_OPERATION_ROUTERCACHE).setSign(AppConstants.REDIS_SIGN_ROUTERCACHE).build();
    public static final String apiRouterRedisVersionKey = RedisKey.create().setProgam(Constants.SAI_PROGRAM_OPENAPI).setOperation(AppConstants.REDIS_OPERATION_ROUTERCACHE_VERSION).setSign(AppConstants.REDIS_SIGN_ROUTERCACHE_VERSION).build();

    public static final String routerInfoKey = RedisKey.create().setProgam(Constants.SAI_PROGRAM_OPENAPI).setOperation("zuulRouter").setSign("routerMap").build();
}
