package com.sai.openapi.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: zhouxiang
 * Date: 2017-10-26
 * Time: 下午 3:20
 */
@Configuration
@MapperScan(basePackages = "com.sai.openapi.mapper", sqlSessionFactoryRef = "saiShoppingSqlSessionFactory")
public class OpenApiDataSourceConfig {
    @Bean(name = "saiShoppingDataSource")
    @ConfigurationProperties(prefix="spring.saiShoppingDataSource.druid")
    public DataSource dataSource(){
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "saiShoppingSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());

        // 添加插件
        return sessionFactory.getObject();
    }
}
