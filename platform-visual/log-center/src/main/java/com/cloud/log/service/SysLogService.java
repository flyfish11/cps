package com.cloud.log.service;

import com.cloud.common.utils.R;
import com.cloud.model.common.Page;
import com.cloud.model.log.SysLog;

import java.util.Map;

public interface SysLogService {

    /**
     * 新增日志
     *
     * @param sysLog
     */
    R save(SysLog sysLog);

    /**
     * 分页查询日志
     *
     * @param params
     * @return
     */
    Page<SysLog> list(Map<String, Object> params);

}
