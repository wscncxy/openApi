package com.sai.openapi.domain;

import java.util.Date;

public class ApiRouter {
    private Long id;
    private Long projectId;
    private String routerName;
    private String serviceId;
    private String routerPath;
    private int routerType;
    private Date createTime;
    private Date updateTime;
    private String requestMethod;
    private String requestContentType;

    public void setId(Long id){
        this.id=id;
    }

    public Long getId(){
        return this.id;
    }

    public void setProjectId(Long projectId){
        this.projectId=projectId;
    }

    public Long getProjectId(){
        return this.projectId;
    }

    public void setRouterName(String routerName){
        this.routerName=routerName;
    }

    public String getRouterName(){
        return this.routerName;
    }

    public void setServiceId(String serviceId){
        this.serviceId=serviceId;
    }

    public String getServiceId(){
        return this.serviceId;
    }

    public void setRouterPath(String routerPath){
        this.routerPath=routerPath;
    }

    public String getRouterPath(){
        return this.routerPath;
    }

    public void setRouterType(int routerType){
        this.routerType=routerType;
    }

    public int getRouterType(){
        return this.routerType;
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

    public void setRequestMethod(String requestMethod){
        this.requestMethod=requestMethod;
    }

    public String getRequestMethod(){
        return this.requestMethod;
    }

    public void setRequestContentType(String requestContentType){
        this.requestContentType=requestContentType;
    }

    public String getRequestContentType(){
        return this.requestContentType;
    }
}