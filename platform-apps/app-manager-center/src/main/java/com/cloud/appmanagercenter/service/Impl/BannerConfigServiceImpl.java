package com.cloud.appmanagercenter.service.Impl;

import com.cloud.appmanagercenter.dao.BannerConfigDao;
import com.cloud.appmanagercenter.service.BannerConfigService;
import com.cloud.common.constants.CommonConstants;
import com.cloud.common.enums.ResultEnum;
import com.cloud.common.utils.AppUserUtil;
import com.cloud.common.utils.R;
import com.cloud.model.appmanagercenter.BannerConfig;
import com.cloud.model.appmanagercenter.bo.BannerConfigAddBO;
import com.cloud.model.appmanagercenter.bo.BannerConfigUpdateBO;
import com.cloud.model.user.LoginAppUser;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @Classname BannerConfigServiceImpl
 * @Description <h1>Banner配置表服务层实现</h1>
 * @Author yulj
 * @Date: 2020/04/23 9:39 下午
 */
@Service
public class BannerConfigServiceImpl implements BannerConfigService {

    @Autowired
    private BannerConfigDao bannerConfigDao;

    @Override
    @Cacheable(value = "bannerConfigData", key = "targetClass + methodName +#p0", unless = "#result == null")
    public List<BannerConfig> list() {
        return this.bannerConfigDao.selectAll();
    }

    @Override
    @Transactional
    @CacheEvict(value = "bannerConfigData", allEntries = true, beforeInvocation = true)
    public R add(BannerConfigAddBO bannerConfigAddBO) {
        //属性校验
        BannerConfig bannerConfigByTitle = this.bannerConfigDao.selectOneByTitle(bannerConfigAddBO.getTitle());
        if (bannerConfigByTitle != null) {
            return R.failed("标题已存在！");
        }

        BannerConfig bannerConfig = bannerConfigAddBOToBannerConfig(bannerConfigAddBO);
        this.bannerConfigDao.insertSelective(bannerConfig);

        return R.ok(ResultEnum.ADD_OPERATE_SUCCESS.getMessage());
    }

    private BannerConfig bannerConfigAddBOToBannerConfig(BannerConfigAddBO bannerConfigAddBO) {
        BannerConfig bannerConfig = new BannerConfig();
        BeanUtils.copyProperties(bannerConfigAddBO, bannerConfig);
        LoginAppUser user = AppUserUtil.getLoginAppUser();
        if (user != null) {
            bannerConfig.setCreateBy(user.getUsername());
            bannerConfig.setUpdateBy(user.getUsername());
        }
        bannerConfig.setCreateTime(new Date());
        bannerConfig.setUpdateTime(bannerConfig.getCreateTime());
        // 设置排序值为 banner 配置条数 + 1
        Integer count = this.bannerConfigDao.queryCount();
        bannerConfig.setOrderNum(count + 1);
        return bannerConfig;
    }

    @Override
    @Transactional
    @CacheEvict(value = "bannerConfigData", allEntries = true, beforeInvocation = true)
    public R update(BannerConfigUpdateBO bannerConfigUpdateBO) {
        //属性校验
        if (StringUtils.isNotBlank(bannerConfigUpdateBO.getTitle())) {
            BannerConfig bannerConfigByTitle = this.bannerConfigDao.selectOneByTitleAndIdNot(bannerConfigUpdateBO.getTitle(),
                    bannerConfigUpdateBO.getId());
            if (bannerConfigByTitle != null) {
                return R.failed("标题已存在！");
            }
        }

        BannerConfig bannerConfig = bannerConfigUpdateBOToBannerConfig(bannerConfigUpdateBO);
        this.bannerConfigDao.updateByIdSelective(bannerConfig);

        return R.ok(ResultEnum.UPDATE_OPERATE_SUCCESS.getMessage());
    }

    private BannerConfig bannerConfigUpdateBOToBannerConfig(BannerConfigUpdateBO bannerConfigUpdateBO) {
        BannerConfig bannerConfig = new BannerConfig();
        BeanUtils.copyProperties(bannerConfigUpdateBO, bannerConfig);
        LoginAppUser user = AppUserUtil.getLoginAppUser();
        if (user != null) {
            bannerConfig.setUpdateBy(user.getUsername());
        }
        bannerConfig.setUpdateTime(new Date());
        return bannerConfig;
    }

    @Override
    @Transactional
    @CacheEvict(value = "bannerConfigData", allEntries = true, beforeInvocation = true)
    public R delete(Integer id) {
        BannerConfig bannerConfig = this.bannerConfigDao.selectById(id);
        if (bannerConfig == null) {
            return R.failed(ResultEnum.CAN_NOT_FIND_RECORD.getMessage());
        }

        this.bannerConfigDao.deleteById(id);

        return R.ok(ResultEnum.DELETE_OPERATE_SUCCESS.getMessage());
    }

    @Override
    @Transactional
    @CacheEvict(value = "bannerConfigData", allEntries = true, beforeInvocation = true)
    public synchronized R moveUp(Integer id) {
        BannerConfig bannerConfig = this.bannerConfigDao.selectById(id);
        Integer orderNum = bannerConfig.getOrderNum();
        if (orderNum.equals(CommonConstants.TOP_BANAER_SORT_NUM)) {
            return R.failed("此banner已不可上移！");
        }

        // 查询上一条 banner 记录，若存在，则与当前操作记录互换排序值
        Integer lastOneOrderNum = orderNum - 1;
        BannerConfig lastOnebannerConfig = this.bannerConfigDao.selectOneByOrderNum(lastOneOrderNum);
        if (lastOnebannerConfig != null) {
            bannerConfig.setOrderNum(lastOneOrderNum);
            lastOnebannerConfig.setOrderNum(orderNum);
            // 批量插入banner 配置记录
            List<BannerConfig> bannerConfigList = Lists.newArrayList();
            bannerConfigList.add(bannerConfig);
            bannerConfigList.add(lastOnebannerConfig);
            this.bannerConfigDao.updateBatchSelective(bannerConfigList);
            return R.ok(ResultEnum.BANEER_MOVE_SUCCESS.getMessage());
        }

        return R.failed(ResultEnum.BANEER_MOVE_UP_FAIL.getMessage());
    }

    @Override
    @Transactional
    @CacheEvict(value = "bannerConfigData", allEntries = true, beforeInvocation = true)
    public synchronized R moveDown(Integer id) {
        BannerConfig bannerConfig = this.bannerConfigDao.selectById(id);
        Integer orderNum = bannerConfig.getOrderNum();
        Integer count = this.bannerConfigDao.queryCount();
        if (orderNum.equals(count)) {
            return R.failed("此banner已不可下移！");
        }

        // 查询下一条 banner 记录，若存在，则与当前操作记录互换排序值
        Integer nextOneOrderNum = orderNum + 1;
        BannerConfig nextOnebannerConfig = this.bannerConfigDao.selectOneByOrderNum(nextOneOrderNum);
        if (nextOnebannerConfig != null) {
            bannerConfig.setOrderNum(nextOneOrderNum);
            nextOnebannerConfig.setOrderNum(orderNum);
            // 批量插入banner 配置记录
            List<BannerConfig> bannerConfigList = Lists.newArrayList();
            bannerConfigList.add(bannerConfig);
            bannerConfigList.add(nextOnebannerConfig);
            this.bannerConfigDao.updateBatchSelective(bannerConfigList);
            return R.ok(ResultEnum.BANEER_MOVE_SUCCESS.getMessage());
        }

        return R.failed(ResultEnum.BANEER_MOVE_DOWN_FAIL.getMessage());
    }

}
