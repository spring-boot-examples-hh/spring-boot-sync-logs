package com.roylao.common;

import javax.servlet.http.HttpServletRequest;

/**
 * @description ip类
 * @author roylao
 * @create 2020-3-15 17:02:50
 */
public class IpUtils {

    /**
     * 获取请求ip地址
     * @param request
     * @return
     */
    public static String getIpAddress(HttpServletRequest request) {
        return request.getRemoteAddr();
    }
}
