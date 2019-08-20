package com.olaa.avatar.open.gateway.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import com.olaa.avatar.open.common.util.EnvUtil;
import javax.annotation.PostConstruct;

@Configuration
public class MyEnvUtil extends EnvUtil {
	
	@Value("${spring.profiles.active}")
	private String envStr;
	
	@PostConstruct
	public void init() {
		EnvUtil.curEnv = envStr ;
	}
	
}
