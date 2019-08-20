package com.olaa.avatar.open.gateway.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.olaa.avatar.open.common.model.ResultModel;
import com.olaa.avatar.open.common.util.RedisUtil;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: Philip
 * @Date: 2019/8/1
 * @Description: 鉴权拦截器
 */
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

	protected RedisUtil redisUtil;
	
	protected String commonRsaPriKey ;
	
	protected MessageSource messageSource ;
	
	
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
    	return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

	}
    
    private void response2Client(HttpServletResponse response,Integer code) throws IOException {
    	response.setHeader("Content-Type", "application/json;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        writer.write(JSONObject.toJSONString(ResultModel.fail(code, getMessage(code))));
        writer.flush();
    }
    
	private String getMessage(int code) {
        try {
            return messageSource.getMessage(code + "", null, LocaleContextHolder.getLocale());
        } catch (NoSuchMessageException e) {
            log.error(e.getMessage());
            return "The system is abnormal.";
        }
    }
    
}
