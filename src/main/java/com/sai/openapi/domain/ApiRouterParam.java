package com.sai.openapi.domain;

import java.util.Date;

public class ApiRouterParam {
    private Long id;
    private Long routerId;
    private String paramKey;
    private Byte isNeed;
    private String dataType;
    private Date createTime;
    private Date updateTime;

    public void setId(Long id){
        this.id=id;
    }

    public Long getId(){
        return this.id;
    }

    public void setRouterId(Long routerId){
        this.routerId=routerId;
    }

    public Long getRouterId(){
        return this.routerId;
    }

    public void setParamKey(String paramKey){
        this.paramKey=paramKey;
    }

    public String getParamKey(){
        return this.paramKey;
    }

    public void setIsNeed(Byte isNeed){
        this.isNeed=isNeed;
    }

    public Byte getIsNeed(){
        return this.isNeed;
    }

    public void setDataType(String dataType){
        this.dataType=dataType;
    }

    public String getDataType(){
        return this.dataType;
    }

    public void setCreateTime(Date createTime){
        this.createTime=createTime;
    }

    public Date getCreateTime(){
        return this.createTime;
    }

    public void setUpdateTime(Date updateTime){
        this.updateTime=updateTime;
    }

    public Date getUpdateTime(){
        return this.updateTime;
    }
}