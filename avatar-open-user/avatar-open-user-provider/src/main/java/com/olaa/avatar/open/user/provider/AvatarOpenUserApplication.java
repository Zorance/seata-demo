package com.olaa.avatar.open.user.provider;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@MapperScan("com.olaa.avatar.open.*.dao")
@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan("com.olaa.avatar.open.*.service.impl")
public class AvatarOpenUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(AvatarOpenUserApplication.class, args);
    }

}
