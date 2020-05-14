package com.cloud.common.utils;

import cn.hutool.core.util.URLUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.cloud.model.log.SysLog;
import lombok.experimental.UtilityClass;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @Classname SysLogUtils
 * @Description <h1>系统日志工具类</h1>
 * @Author yulj
 * @Date: 2020/04/14 5:27 下午
 */
@UtilityClass
public class SysLogUtils {

    public SysLog getSysLog() {
        SysLog sysLog = new SysLog();
        HttpServletRequest request = ((ServletRequestAttributes) Objects
                .requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        sysLog.setRemoteAddr(ServletUtil.getClientIP(request));
        sysLog.setRequestUri(URLUtil.getPath(request.getRequestURI()));
        sysLog.setMethod(request.getMethod());
        sysLog.setUserAgent(request.getHeader("user-agent"));
        return sysLog;
    }

}
