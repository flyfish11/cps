package com.cloud.platformappmanager.service.Impl;

import com.cloud.platformappmanager.dao.PlatformGroupDao;
import com.cloud.platformappmanager.service.PlatformGroupService;
import com.cloud.common.enums.ResultEnum;
import com.cloud.common.enums.YesOrNoEnum;
import com.cloud.common.utils.AppUserUtil;
import com.cloud.common.utils.PageUtil;
import com.cloud.common.utils.R;
import com.cloud.model.platformappmanager.PlatformGroup;
import com.cloud.model.platformappmanager.bo.PlatformGroupAddBO;
import com.cloud.model.platformappmanager.bo.PlatformGroupUpdateBO;
import com.cloud.model.platformappmanager.vo.PlatformGroupVO;
import com.cloud.model.common.Page;
import com.cloud.model.platformuser.LoginAppUser;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Classname PlatformGroupServiceImpl
 * @Description <h1>平台类型表服务层实现</h1>
 * @Author yulj
 * @Date: 2020/04/22 3:05 下午
 */
@Service
public class PlatformGroupServiceImpl implements PlatformGroupService {

    @Autowired
    private PlatformGroupDao platformGroupDao;

    @Override
    public Page<PlatformGroup> list(Map<String, Object> params) {
        params.put("delFlag", YesOrNoEnum.NO.getType());
        long total = this.platformGroupDao.count(params);
        PageUtil.pageUtil(params);
        List<PlatformGroup> list = Lists.newArrayList();
        if (total > 0) {
            list = this.platformGroupDao.findList(params);
        }
        return new Page<>(total, list);
    }

    @Override
    @Transactional
    @CacheEvict(value = "platformGroupData", allEntries = true, beforeInvocation = true)
    public R add(PlatformGroupAddBO platformGroupAddBO) {
        //属性校验
        PlatformGroup platformGroupByName = this.platformGroupDao
                .selectOneByGroupNameAndDelFlag(platformGroupAddBO.getGroupName(), YesOrNoEnum.NO.getType());
        if (platformGroupByName != null) {
            return R.failed("平台类型名称已存在！");
        }

        PlatformGroup platformGroup = platformGroupAddBOToPlatformGroup(platformGroupAddBO);
        this.platformGroupDao.insertSelective(platformGroup);

        return R.ok(ResultEnum.ADD_OPERATE_SUCCESS.getMessage());
    }

    private PlatformGroup platformGroupAddBOToPlatformGroup(PlatformGroupAddBO platformGroupAddBO) {
        PlatformGroup platformGroup = new PlatformGroup();
        BeanUtils.copyProperties(platformGroupAddBO, platformGroup);
        LoginAppUser user = AppUserUtil.getLoginAppUser();
        if (user != null) {
            platformGroup.setCreateBy(user.getUsername());
            platformGroup.setUpdateBy(user.getUsername());
        }
        platformGroup.setCreateTime(new Date());
        platformGroup.setUpdateTime(platformGroup.getCreateTime());
        platformGroup.setDelFlag(YesOrNoEnum.NO.getType());
        return platformGroup;
    }

    @Override
    @Transactional
    @CacheEvict(value = "platformGroupData", allEntries = true, beforeInvocation = true)
    public R update(PlatformGroupUpdateBO platformGroupUpdateBO) {
        if (StringUtils.isNotBlank(platformGroupUpdateBO.getGroupName())) {
            //属性校验
            PlatformGroup platformGroupByName = this.platformGroupDao.
                    selectOneByGroupNameAndDelFlagAndIdNot(platformGroupUpdateBO.getGroupName(),
                            YesOrNoEnum.NO.getType(), platformGroupUpdateBO.getId());
            if (platformGroupByName != null) {
                return R.failed("平台类型名称已存在！");
            }
        }

        PlatformGroup platformGroup = platformGroupUpdateBOToPlatformGroup(platformGroupUpdateBO);
        this.platformGroupDao.updateByIdSelective(platformGroup);

        return R.ok(ResultEnum.UPDATE_OPERATE_SUCCESS.getMessage());
    }

    private PlatformGroup platformGroupUpdateBOToPlatformGroup(PlatformGroupUpdateBO platformGroupUpdateBO) {
        PlatformGroup platformGroup = new PlatformGroup();
        BeanUtils.copyProperties(platformGroupUpdateBO, platformGroup);
        LoginAppUser user = AppUserUtil.getLoginAppUser();
        if (user != null) {
            platformGroup.setUpdateBy(user.getUsername());
        }
        platformGroup.setUpdateTime(new Date());
        return platformGroup;
    }

    @Override
    @CacheEvict(value = "platformGroupData", allEntries = true, beforeInvocation = true)
    public R delete(Integer id) {
        PlatformGroup platformGroup = this.platformGroupDao.selectById(id);
        if (platformGroup == null) {
            return R.failed(ResultEnum.CAN_NOT_FIND_RECORD.getMessage());
        }
        // 校验是否在使用
        if (platformGroup.getEnable().equals(YesOrNoEnum.YES.getType())) {
            return R.failed("该平台类型在使用中，不能进行删除操作！");
        }

        platformGroup.setDelFlag(YesOrNoEnum.YES.getType());
        LoginAppUser user = AppUserUtil.getLoginAppUser();
        if (user != null) {
            platformGroup.setUpdateBy(user.getUsername());
        }
        platformGroup.setUpdateTime(new Date());
        this.platformGroupDao.updateByIdSelective(platformGroup);

        return R.ok(ResultEnum.DELETE_OPERATE_SUCCESS.getMessage());
    }

    @Override
    @Cacheable(value = "platformGroupData", key = "targetClass + methodName +#p0", unless = "#result == null")
    public R queryPlatformSelect() {
        List<PlatformGroupVO> platformGroupVOS = this.platformGroupDao.selectIdAndGroupName(YesOrNoEnum.NO.getType(), YesOrNoEnum.YES.getType());
        return R.ok(platformGroupVOS);
    }

    @Override
    public R load(Integer id) {
        PlatformGroup platformGroup = this.platformGroupDao.selectById(id);
        return R.ok(platformGroup);
    }

    @Override
    @Cacheable(value = "platformGroupData", key = "targetClass + methodName +#p0", unless = "#result == null")
    public Map<String, String> queryAllPlatformGroup() {
        List<PlatformGroupVO> platformGroupVOS = this.platformGroupDao.selectIdAndGroupName(YesOrNoEnum.NO.getType(), YesOrNoEnum.YES.getType());
        Map<String, String> map = Maps.newHashMap();
        platformGroupVOS.forEach(platformGroupVO -> {
            map.put(platformGroupVO.getGroupId().toString(), platformGroupVO.getGroupName());
        });
        return map;
    }

}
