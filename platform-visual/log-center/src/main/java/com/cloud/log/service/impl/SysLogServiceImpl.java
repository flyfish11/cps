package com.cloud.log.service.impl;

import com.cloud.common.utils.PageUtil;
import com.cloud.common.utils.R;
import com.cloud.log.dao.SysLogDao;
import com.cloud.log.service.SysLogService;
import com.cloud.model.common.Page;
import com.cloud.model.log.SysLog;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class SysLogServiceImpl implements SysLogService {

    @Resource
    private SysLogDao sysLogDao;

    @Async
    @Override
    public R save(SysLog sysLog) {
        if (sysLog.getCreateTime() == null) {
            sysLog.setCreateTime(new Date());
        }
        if (sysLog.getType() == null) {
            sysLog.setType(Boolean.TRUE);
        }
        this.sysLogDao.insertSelective(sysLog);
        return R.ok();
    }

    @Override
    public Page<SysLog> list(Map<String, Object> params) {
        int total = this.sysLogDao.count(params);
        PageUtil.pageUtil(params);
        List<SysLog> list = Collections.emptyList();
        if (total > 0) {
            list = this.sysLogDao.findList(params);
        }
        return new Page<>(total, list);
    }

}
