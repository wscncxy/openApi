package com.sai.openapi.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

public class StartInitializing implements InitializingBean {

    public void afterPropertiesSet() throws Exception {
        System.out.println("tttttttttttttttttttt");
    }

}
