package com.sai.openapi;

import com.sai.openapi.zuul.ZuulAuthFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;


@SpringBootApplication
@EnableZuulProxy
@ServletComponentScan(basePackages = {"com.sai.openapi", "com.sai.web"})
public class OpenApiApplication extends SpringBootServletInitializer {


    public static void main(String[] args) {
        SpringApplication.run(OpenApiApplication.class, args);
    }

}