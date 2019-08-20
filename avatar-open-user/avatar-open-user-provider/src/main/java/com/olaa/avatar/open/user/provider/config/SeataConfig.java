package com.olaa.avatar.open.user.provider.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.olaa.avatar.open.common.constant.AvatorOpenConstant;
import io.seata.spring.annotation.GlobalTransactionScanner;

@Configuration
public class SeataConfig {

	@Value("${spring.application.name}")
    private String applicationName;
    
    @Bean
    public GlobalTransactionScanner globalTransactionScanner() {
    	return new GlobalTransactionScanner(applicationName,AvatorOpenConstant.SEATA_TX_GROUP);
    }
    
}
