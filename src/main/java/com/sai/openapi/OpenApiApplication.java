package com.sai.openapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.context.ConfigurableWebApplicationContext;

import java.util.LinkedList;


@SpringBootApplication(exclude={org.springframework.cloud.netflix.zuul.ZuulProxyAutoConfiguration.class,
        org.springframework.cloud.netflix.zuul.ZuulServerAutoConfiguration.class})
@EnableZuulProxy
@ComponentScan(basePackages={"com.sai.openapi","com.sai.web"})
@ServletComponentScan(basePackages = {"com.sai.openapi", "com.sai.web"})
public class OpenApiApplication extends SpringBootServletInitializer implements ApplicationContextInitializer<ConfigurableWebApplicationContext> {

    @Autowired
    private ServerProperties properties;
LinkedList
    public static void main(String[] args) {
        SpringApplication.run(OpenApiApplication.class, args);
    }

    @Override
public void initialize(ConfigurableWebApplicationContext applicationContext) {
        System.out.println(11111);
    }
}