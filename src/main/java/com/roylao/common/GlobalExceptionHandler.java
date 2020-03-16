package com.roylao.common;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @description: 全局捕获异常
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @Autowired
    private KafkaSender<JSONObject> kafkaSender;


    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public JSONObject exceptionHandler(Exception e) {
        log.info("<<<<<<<<<<<<<<<全局捕获异常>>>>>>>>>>>>>>>>>,error:{}", e);
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();


        // 1.封装异常日志信息
        JSONObject errorJson = new JSONObject();
        JSONObject logJson = new JSONObject();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        logJson.put("request_time", LocalDateTime.now().format(dateTimeFormatter));
        logJson.put("error_info", e);
        errorJson.put("szhuangl_request_error", logJson);
        kafkaSender.send(errorJson);
        // 2. 返回错误信息
        JSONObject result = new JSONObject();
        result.put("code", 500);
        result.put("msg", "系统错误");
        return result;
    }
}
