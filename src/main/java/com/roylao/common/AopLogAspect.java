package com.roylao.common;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

/**
 * @description kafka日志收集切面类
 * @author roylao
 * @create 2020-3-15 17:03:19
 */
@Aspect
@Component
@Slf4j
public class AopLogAspect {

    @Autowired
    private KafkaSender<JSONObject> kafkaSender;


    // 申明一个切点 里面是 execution表达式
//    @Pointcut("execution(* com.roylao.service.Impl.*(..))")
    @Pointcut("execution(* com.roylao.service.Impl.*.*(..))")
    private void serviceAspect() {
    }


    // 请求method前打印内容
    @Before(value = "serviceAspect()")
    public void methodBefore(JoinPoint joinPoint) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        String ip = IpUtils.getIpAddress(request);
        int localPort = request.getLocalPort();
        log.info("---localPort---:" + localPort);
        int serverPort = request.getServerPort();
        log.info("---serverPort---:" + serverPort);
        int remotePort = request.getRemotePort();
        log.info("---remotePort---:" + remotePort);
        JSONObject jsonObject = new JSONObject();

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        jsonObject.put("request_time", LocalDateTime.now().format(dateTimeFormatter));
        jsonObject.put("request_ip_port", ip);
        jsonObject.put("request_url", request.getRequestURL().toString());
        jsonObject.put("request_method", request.getMethod());
        jsonObject.put("signature", joinPoint.getSignature());
        jsonObject.put("request_args", Arrays.toString(joinPoint.getArgs()));
        JSONObject requestJsonObject = new JSONObject();
        requestJsonObject.put("request", jsonObject);
        kafkaSender.send(requestJsonObject);
    }

    // 在方法执行完结后打印返回内容
    @AfterReturning(returning = "o", pointcut = "serviceAspect()")
    public void methodAfterReturing(Object o) {
        JSONObject respJSONObject = new JSONObject();
        JSONObject jsonObject = new JSONObject();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        jsonObject.put("response_time", LocalDateTime.now().format(dateTimeFormatter));
        jsonObject.put("response_content", JSONObject.toJSONString(o));
        respJSONObject.put("response", jsonObject);
        kafkaSender.send(respJSONObject);
    }
}
