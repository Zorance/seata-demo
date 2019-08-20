package com.olaa.avatar.open.gateway.exception;

import com.alibaba.fastjson.JSON;
import com.olaa.avatar.open.common.constant.CodeConstant;
import com.olaa.avatar.open.common.exception.AvatarOpenException;
import com.olaa.avatar.open.common.model.ResultModel;
import com.olaa.avatar.open.gateway.util.MyEnvUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Philip
 * @Date: 2019/7/31
 * @Description: 统一异常处理
 */
@Slf4j
@RestControllerAdvice
public class AvatarOpenExceptionHandler {

    @Autowired
    private MessageSource messageSource;


    /**
     * 业务异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ResultModel handleException(Exception e) {
    	ResultModel result = null ;
        if (e instanceof AvatarOpenException) {
            AvatarOpenException exception = (AvatarOpenException) e;
            result = getResultByCode(exception.getCode());
        } else {
            log.error("===> 未知异常：{}", e.getMessage(), e);
            result = getResultByCode(CodeConstant.Common.UNKNOWN_ERROR);
            setMsg2Log(result, e);
        }
        return result;
    }

    /**
     * Servlet异常处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler({
            NoHandlerFoundException.class,
            HttpRequestMethodNotSupportedException.class,
            HttpMediaTypeNotSupportedException.class,
            MissingPathVariableException.class,
            MissingServletRequestParameterException.class,
            TypeMismatchException.class,
            HttpMessageNotReadableException.class,
            HttpMessageNotWritableException.class,
            HttpMediaTypeNotAcceptableException.class,
            ServletRequestBindingException.class,
            ConversionNotSupportedException.class,
            MissingServletRequestPartException.class,
            AsyncRequestTimeoutException.class
    })
    public ResultModel handleServletException(Exception e) {
        log.error("===> 系统异常：{}", e.getMessage(), e);
        ResultModel result = getResultByCode(CodeConstant.Common.SYSTEM_ERROR);
        setMsg2Log(result, e);
        return result;
    }
    

    /**
     * 参数校验异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(value = {
        BindException.class,
        MethodArgumentNotValidException.class
    })
    public ResultModel handleBindException(Exception e) {
        Map<String, String> map = new HashMap<>();
        if (e instanceof BindException) {
            BindException bindException = (BindException) e;
            map = wrapperBindingResult(bindException);
        } else if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException methodArgumentNotValidException = (MethodArgumentNotValidException) e;
            map = wrapperBindingResult(methodArgumentNotValidException.getBindingResult());
        }
        log.error("===> 参数异常：{}", JSON.toJSONString(map, true), e);
        ResultModel result = getResultByCode(CodeConstant.Common.PARAM_ERROR);
        setMsg2Log(result, e);
        return result;
    }

    @ExceptionHandler(NoSuchMessageException.class)
    public ResultModel handleNoSuchMessageException(NoSuchMessageException e) {
        ResultModel result = getResultByCode(CodeConstant.Common.SYSTEM_ERROR);
        setMsg2Log(result, e);
        return result;
    }

    private ResultModel getResultByCode(Integer code) {
        String codeStr = null == code ? String.valueOf(CodeConstant.Common.UNKNOWN_ERROR) : code.toString();
        String message = getMessage(codeStr, null);
        return ResultModel.fail(code, message);
    }

    private Map<String, String> wrapperBindingResult(BindingResult bindingResult) {
        Map<String, String> map = new HashMap<>();
        for (ObjectError error : bindingResult.getAllErrors()) {
            if (error instanceof FieldError) {
                map.put(((FieldError) error).getField(), getMessage(error.getDefaultMessage(), null));
            }
        }
        return map;
    }
    
    private void setMsg2Log(ResultModel result , Exception e){
    	if ( !MyEnvUtil.isProdEnv() ) {
            result.setData(e.getMessage());
        }
    }

	private String getMessage(String code, Object[] objects) {
        try {
            return messageSource.getMessage(code + "", objects, LocaleContextHolder.getLocale());
        } catch (NoSuchMessageException e) {
            log.error(e.getMessage());
            return e.getMessage();
        }
    }
    
}
