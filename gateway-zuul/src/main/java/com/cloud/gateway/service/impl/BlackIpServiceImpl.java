package com.cloud.gateway.service.impl;


import com.cloud.common.utils.AppUserUtil;
import com.cloud.common.utils.PageUtil;
import com.cloud.common.utils.R;
import com.cloud.common.utils.UUIDUtils;
import com.cloud.gateway.dao.BlackIpDao;
import com.cloud.gateway.service.BlackIpService;
import com.cloud.model.common.Page;
import com.cloud.model.gateway.BlackIp;
import com.cloud.model.gateway.bo.BlackIpAddBO;
import com.cloud.model.gateway.bo.BlackIpUpdateBO;
import com.cloud.model.platformuser.LoginAppUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class BlackIpServiceImpl implements BlackIpService {

    @Resource
    private BlackIpDao blackIpDao;

    @Override
    public Page<BlackIp> findList(Map<String, Object> params) {
        int total = blackIpDao.count(params);
        PageUtil.pageUtil(params);
        List<BlackIp> list = Collections.emptyList();
        if (total > 0) {
            list = blackIpDao.pageList(params);
        }
        return new Page<>(total, list);
    }

    @Override
    @Transactional
    public R save(BlackIpAddBO blackIpAddBO) {
        BlackIp blackIp = new BlackIp();
        BeanUtils.copyProperties(blackIpAddBO, blackIp);
        blackIp.setId(UUIDUtils.getUUID().trim());
        LoginAppUser user = AppUserUtil.getLoginAppUser();
        if (user != null) {
            blackIp.setCreateBy(user.getUsername());
        }
        blackIp.setCreateTime(new Date());

        blackIpDao.insertSelective(blackIp);
        log.info("添加黑名单ip:{}", blackIp);
        return R.ok();
    }

    @Override
    @Transactional
    public R update(BlackIpUpdateBO blackIpUpdateBO) {
        BlackIp blackIp = new BlackIp();
        BeanUtils.copyProperties(blackIpUpdateBO, blackIp);
        LoginAppUser user = AppUserUtil.getLoginAppUser();
        if (user != null) {
            blackIp.setUpdateBy(user.getUsername());
        }
        blackIp.setUpdateTime(new Date());

        blackIpDao.updateByIdSelective(blackIp);
        log.info("添加黑名单ip:{}", blackIp);
        return R.ok();
    }

    @Override
    public R delete(String id) {
        blackIpDao.deleteById(id);
        return R.ok();
    }

    @Override
    public R findAllBlackIPs() {
        List<String> allBlackIPs = this.blackIpDao.selectDistinctIp();
        return R.ok(allBlackIPs);
    }

}
