package com.olaa.avatar.open.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author: Philip
 * @Date: 2019/7/31
 * @Description:
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableCaching
public class AvatarOpenGatewayApplication {
	
    public static void main(String[] args) {
        SpringApplication.run(AvatarOpenGatewayApplication.class, args);
    }
    
}
