package com.olaa.avatar.open.gateway.config;

import java.util.Locale;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

/**
 * @Author: Philip
 * @Date: 2019/7/31
 * @Description: 国际化配置
 */
//@Configuration
public class I18nConfig {
	
	@Bean(name = "messageSource")
    public ResourceBundleMessageSource getMessageSource() throws Exception {
        ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
        //属性文件读取时的编码格式
        resourceBundleMessageSource.setDefaultEncoding("UTF-8");
        //属性文件路径
        resourceBundleMessageSource.setBasenames("i18n/messages");
        return resourceBundleMessageSource;
    }
 
    @Bean
    public LocaleResolver localeResolver() {
    	MyAcceptHeaderLocaleResolver localeResolver = new I18nConfig.MyAcceptHeaderLocaleResolver();
        localeResolver.setDefaultLocale(Locale.SIMPLIFIED_CHINESE);
        return localeResolver;
    }
    
    public static class MyAcceptHeaderLocaleResolver extends AcceptHeaderLocaleResolver{
    	@Override
    	public Locale resolveLocale(HttpServletRequest request) {
    		Locale defaultLocale = getDefaultLocale();
    		String language = request.getHeader("Accept-Language") ;
    		if (  Optional.ofNullable(language).filter(l->l.startsWith("en")).isPresent() ) {
    			return Locale.US ;
    		}
    		return defaultLocale;
    	}	
    }
    
}
