package com.cloud.gateway.service.impl;

import com.cloud.common.constants.CommonConstants;
import com.cloud.common.utils.AppUserUtil;
import com.cloud.common.utils.PageUtil;
import com.cloud.common.utils.R;
import com.cloud.common.utils.UUIDUtils;
import com.cloud.gateway.dao.GatewayApiDefineDao;
import com.cloud.gateway.service.GatewayApiDefineService;
import com.cloud.model.common.Page;
import com.cloud.model.gateway.GatewayApiDefine;
import com.cloud.model.gateway.bo.GatewayApiDefineAddBO;
import com.cloud.model.gateway.bo.GatewayApiDefineUpdateBO;
import com.cloud.model.platformuser.SysRole;
import com.cloud.model.platformuser.LoginAppUser;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.RoutesRefreshedEvent;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Classname GatewayApiDefineServiceImpl
 * @Description 路由规则service层实现
 * @Author yulj
 * @Date: 2019/07/16 21:43
 */
@Service
public class GatewayApiDefineServiceImpl implements GatewayApiDefineService {

    @Autowired
    ApplicationEventPublisher publisher;

    @Autowired
    RouteLocator routeLocator;

    @Autowired
    private GatewayApiDefineDao gatewayApiDefineDao;

    @Override
    public void refreshRoute() {
        RoutesRefreshedEvent routesRefreshedEvent = new RoutesRefreshedEvent(routeLocator);
        publisher.publishEvent(routesRefreshedEvent);
    }

    @Override
    public Page<GatewayApiDefine> pageList(Map<String, Object> params) {

        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        Set<SysRole> sysRoles = loginAppUser.getSysRoles();
        Iterator<SysRole> iterator = sysRoles.iterator();
        if(iterator.hasNext()){
            params.put("roleId",iterator.next().getId());
        }
        long total = this.gatewayApiDefineDao.count(params);
        PageUtil.pageUtil(params);

        List<GatewayApiDefine> list = Collections.emptyList();
        if (total > 0) {
            list = this.gatewayApiDefineDao.pageList(params);
        }
        return new Page<>(total, list);
    }

    @Override
    public R add(GatewayApiDefineAddBO gatewayApiDefineAddBO) {
        R result = judgeGatewayApiDefine(gatewayApiDefineAddBO);
        if (result.getCode() == CommonConstants.FAIL) {
            return result;
        }
        GatewayApiDefine gatewayApiDefine = gatewayApiDefineAddBOToGatewayApiDefine(gatewayApiDefineAddBO);
        int index = this.gatewayApiDefineDao.insertSelective(gatewayApiDefine);
        if (index == 1) {
            // 创建路由规则后，刷新缓存
            refreshRoute();
        }
        return result;
    }

    /**
     * <h2>网关路由规则新增业务对象转为网关路由规则实体</h2>
     *
     * @param gatewayApiDefineAddBO
     * @return
     */
    private GatewayApiDefine gatewayApiDefineAddBOToGatewayApiDefine(GatewayApiDefineAddBO gatewayApiDefineAddBO) {
        GatewayApiDefine gatewayApiDefine = new GatewayApiDefine();
        BeanUtils.copyProperties(gatewayApiDefineAddBO, gatewayApiDefine);
        if (StringUtils.isBlank(gatewayApiDefineAddBO.getApiName())) {
            gatewayApiDefine.setId(UUIDUtils.getUUID());
        } else {
            gatewayApiDefine.setId(gatewayApiDefineAddBO.getApiName());
        }
        gatewayApiDefine.setRetryable(Boolean.FALSE);
        gatewayApiDefine.setEnabled(Boolean.TRUE);
        gatewayApiDefine.setStripPrefix(1);
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        Set<SysRole> sysRoles = loginAppUser.getSysRoles();
        Iterator<SysRole> iterator = sysRoles.iterator();
        if(iterator.hasNext()){
            gatewayApiDefine.setRoleId(iterator.next().getId());
        }
        return gatewayApiDefine;
    }

    /**
     * 路由规则校验
     *
     * @param gatewayApiDefineAddBO
     * @return
     */
    public R judgeGatewayApiDefine(GatewayApiDefineAddBO gatewayApiDefineAddBO) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("apiName", gatewayApiDefineAddBO.getApiName());
        List<GatewayApiDefine> gatewayApiDefines = this.gatewayApiDefineDao.selectAllByGatewayApiDefineInfo(params);
        if (!gatewayApiDefines.isEmpty()) {
            return R.failed("服务名称已存在！");
        }

        params.clear();
        params.put("serviceId", gatewayApiDefineAddBO.getServiceId());
        gatewayApiDefines = this.gatewayApiDefineDao.selectAllByGatewayApiDefineInfo(params);
        if (!gatewayApiDefines.isEmpty()) {
            return R.failed("已存在该服务id对应的路由规则！");
        }

        params.clear();
        params.put("path", gatewayApiDefineAddBO.getPath());
        gatewayApiDefines = this.gatewayApiDefineDao.selectAllByGatewayApiDefineInfo(params);
        if (!gatewayApiDefines.isEmpty()) {
            return R.failed("路由规则已存在！");
        }

        return R.ok();
    }

    @Override
    public R findById(String id) {
        return R.ok(this.gatewayApiDefineDao.selectById(id));
    }

    @Override
    public R findByServiceId(String serviceId) {
        return R.ok(this.gatewayApiDefineDao.selectByServiceId(serviceId));
    }

    @Override
    public R update(GatewayApiDefineUpdateBO gatewayApiDefineUpdateBO) {
        GatewayApiDefine gatewayApiDefine = gatewayApiDefineUpdateBOToGatewayApiDefine(gatewayApiDefineUpdateBO);
        int index = this.gatewayApiDefineDao.updateByIdSelective(gatewayApiDefine);
        if (index == 1) {
            // 修改路由规则后，刷新缓存
            refreshRoute();
        }
        return R.ok("修改路由规则成功！");
    }

    /**
     * <h2>网关路由规则更新业务对象转为网关路由规则实体</h2>
     *
     * @param gatewayApiDefineUpdateBO
     * @return
     */
    private GatewayApiDefine gatewayApiDefineUpdateBOToGatewayApiDefine(GatewayApiDefineUpdateBO gatewayApiDefineUpdateBO) {
        GatewayApiDefine gatewayApiDefine = new GatewayApiDefine();
        BeanUtils.copyProperties(gatewayApiDefineUpdateBO, gatewayApiDefine);
        return gatewayApiDefine;
    }

    @Override
    public R delete(String id) {
        int index = this.gatewayApiDefineDao.deleteById(id);
        if (index == 1) {
            // 删除路由规则后，刷新缓存
            refreshRoute();
        }
        return R.ok("删除路由规则成功！");
    }

}