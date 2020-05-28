package com.cloud.platformuser.service.impl;

import com.cloud.common.utils.PageUtil;
import com.cloud.common.utils.R;
import com.cloud.model.common.Page;
import com.cloud.model.platformuser.SysRole;
import com.cloud.model.platformuser.constants.UserCenterMq;
import com.cloud.platformuser.dao.SysRoleDao;
import com.cloud.platformuser.dao.UserRoleDao;
import com.cloud.platformuser.dto.SysRoleDto;
import com.cloud.platformuser.service.SysRoleService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;


@Slf4j
@Service
public class SysRoleServiceImpl implements SysRoleService {


    @Resource
    private SysRoleDao sysRoleDao;

    @Resource
    private UserRoleDao userRoleDao;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Transactional
    @Override
    public R save(SysRole sysRole) {
        SysRole role = sysRoleDao.findByCode(sysRole.getCode());
        if (role != null) {
            throw new IllegalArgumentException("角色code已存在");
        }

        sysRole.setId(UUID.randomUUID().toString().replace("-", ""));
        sysRole.setCreateTime(new Date());
        sysRole.setUpdateTime(sysRole.getCreateTime());

        log.info("保存角色：{}", sysRole);
        sysRoleDao.save(sysRole);
        return R.ok();
    }

    @Transactional
    @Override
    public void update(SysRole sysRole) {
        sysRole.setUpdateTime(new Date());

        sysRoleDao.update(sysRole);
        log.info("修改角色：{}", sysRole);
    }

    @Transactional
    @Override
    public void deleteRole(String id) {
        SysRole sysRole = sysRoleDao.findById(id);

        sysRoleDao.delete(id);
        userRoleDao.deleteUserRole(null, id);

        log.info("删除角色：{}", sysRole);

        // 发布role删除的消息
        amqpTemplate.convertAndSend(UserCenterMq.MQ_EXCHANGE_USER, UserCenterMq.ROUTING_KEY_ROLE_DELETE, id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void setPermissionToRole(String roleId, Set<String> permissionIds) {
        SysRole sysRole = sysRoleDao.findById(roleId);
        if (sysRole == null) {
            throw new IllegalArgumentException("角色不存在");
        }

    }

    @Override
    public SysRole findById(String id) {
        return sysRoleDao.findById(id);
    }

    @Override
    public Page<SysRoleDto> findRoles(Map<String, Object> params) {
        long total = sysRoleDao.count(params);

        List<SysRoleDto> roleDtoList = Lists.newArrayList();
        if (total > 0) {
            PageUtil.pageParamConver(params, false);
            List<SysRole> list = sysRoleDao.findData(params);

            if (!list.isEmpty()) {
                for (SysRole sysRole : list) {
                    SysRoleDto sysRoleDto = new SysRoleDto();
                    BeanUtils.copyProperties(sysRole, sysRoleDto);
                    roleDtoList.add(sysRoleDto);
                }
            }
        }
        return new Page<>(total, roleDtoList);
    }

}