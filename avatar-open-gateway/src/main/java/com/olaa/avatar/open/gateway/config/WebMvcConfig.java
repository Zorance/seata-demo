package com.olaa.avatar.open.gateway.config;

import com.olaa.avatar.open.common.constant.BaseConstant;
import com.olaa.avatar.open.common.util.RedisUtil;
import com.olaa.avatar.open.gateway.interceptor.AuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

/**
 * @Author: Philip
 * @Date: 2019/8/1
 * @Description:
 */
//@Configuration
//@RefreshScope
public class WebMvcConfig implements WebMvcConfigurer {

	@Autowired
	private RedisUtil redisUtil ;
	
	@Autowired
	private MessageSource messageSource ;
	
	@Value("${common.rsa.priKey}")
	private String commonRsaPriKey ;
	
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    	
    	//权限认证拦截器
        registry.addInterceptor(new AuthInterceptor(redisUtil,commonRsaPriKey,messageSource))
        		.addPathPatterns("/**")
        		.excludePathPatterns(BaseConstant.PUB_API_PREFIX_V1+"/**");
        
        //国际化拦截器
        LocaleChangeInterceptor localeInterceptor = new LocaleChangeInterceptor();
        registry.addInterceptor(localeInterceptor)
        		.addPathPatterns("/**");
        
    }
    
}
