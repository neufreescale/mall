package org.diwayou.core.util;

import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.ServletRequestDataBinder;

import javax.servlet.http.HttpServletRequest;

/**
 * @author gaopeng 2021/1/15
 */
public class WebRequestUtil {

    /**
     * 从request中解析参数到对象中
     *
     * @param clazz 解析对象class
     * @param <T>   对象实例
     * @return 解析出的对象
     */
    public static <T> T parse(HttpServletRequest request, Class<T> clazz) {
        T d = BeanUtils.instantiateClass(clazz);

        new ServletRequestDataBinder(d).bind(request);

        return d;
    }

    public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if (ip != null) {
            return ip;
        }

        ip = request.getHeader("X-Forwarded-For");
        if (ip != null) {
            return ip;
        }

        return request.getRemoteAddr();
    }
}
