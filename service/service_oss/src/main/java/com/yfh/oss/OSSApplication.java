package com.yfh.oss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class) // 配置springboot不去加载数据库
@ComponentScan(basePackages = {"com.yfh"})
public class OSSApplication {
    public static void main(String[] args) {
        SpringApplication.run(OSSApplication.class);
    }
}
