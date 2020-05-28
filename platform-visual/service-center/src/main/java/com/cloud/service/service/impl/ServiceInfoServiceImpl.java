package com.cloud.service.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cloud.common.enums.ResultEnum;
import com.cloud.common.utils.AppUserUtil;
import com.cloud.common.utils.PageUtil;
import com.cloud.common.utils.R;
import com.cloud.common.utils.ResultUtil;
import com.cloud.model.platformappmanager.ApplicationServices;
import com.cloud.model.platformappmanager.ServiceInfo;
import com.cloud.model.common.Page;
import com.cloud.model.common.Result;
import com.cloud.model.platformuser.SysDictItem;
import com.cloud.service.config.BaseServicesProperties;
import com.cloud.service.dao.ServiceInfoDao;
import com.cloud.service.dto.ServiceInfoDto;
import com.cloud.service.feign.ApplicationFeignClient;
import com.cloud.service.feign.UserCenterFeignClient;
import com.cloud.service.service.ServiceInfoService;
import com.cloud.service.util.HttpClietUtil;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 服务数据接口
 *
 * @author yulj
 * @create: 2019/05/09 13:35
 */

@Service
@Slf4j
public class ServiceInfoServiceImpl implements ServiceInfoService {

    @Autowired
    private BaseServicesProperties baseServicesProperties;

    @Resource
    private ServiceInfoDao serviceInfoDao;

    @Autowired
    private ApplicationFeignClient applicationFeignClient;

    @Autowired
    private UserCenterFeignClient userCenterFeignClient;

    @Value("${web.deleteServiceApi}")
    private String deleteServiceApi;

    @Override
    public Page<ServiceInfoDto> list(Map<String, Object> params) {
        params.put("isDelete", 0);
        long total = serviceInfoDao.count(params);
        PageUtil.pageUtil(params);
        List<ServiceInfo> serviceInfoList = Lists.newArrayList();
        List<ServiceInfoDto> serviceInfoDtoList = Lists.newArrayList();
        if (total > 0) {
            PageUtil.pageParamConver(params, true);
            serviceInfoList = serviceInfoDao.findList(params);
            List<SysDictItem> sysDicts = this.userCenterFeignClient.queryByDictType("serviceGroup");
            Map<String, String> dictMap = sysDicts.stream().collect(Collectors.toMap(SysDictItem::getId, (k) -> k.getValue()));
            for (ServiceInfo serviceInfo : serviceInfoList) {
                ServiceInfoDto serviceInfoDto = new ServiceInfoDto();
                BeanUtils.copyProperties(serviceInfo, serviceInfoDto);
                serviceInfoDto.setServiceGroupName(dictMap.get(serviceInfo.getServiceGroup()));
                serviceInfoDtoList.add(serviceInfoDto);
            }
        }
        return new Page<>(total, serviceInfoDtoList);
    }

    @Override
    public Result add(ServiceInfo serviceInfo) {
        //判断服务名称是否重复
        ServiceInfo serviceInfo1 = this.serviceInfoDao.selectOneByNameAndIsDelete(serviceInfo.getName(), 0);
        if (Objects.nonNull(serviceInfo1)) {
            return ResultUtil.error(ResultEnum.PROJECT_ALREDY_EXISTS.getMessage());
        }

        //判断服务端口是否重复
        if (serviceInfo.getServicePort() != null && StringUtils.isNotBlank(serviceInfo.getServicePort())) {
            // 判断端口是否已被基础服务占用
            List<Integer> integers = baseServicesPort();
            if (integers.contains(Integer.valueOf(serviceInfo.getServicePort()))) {
                return ResultUtil.error(ResultEnum.SERVICEPORT_ALREDY_EXISTS.getMessage());
            }
            ServiceInfo serviceInfo2 = this.serviceInfoDao.selectOneByServicePortAndIsDelete(serviceInfo.getServicePort(), 0);
            if (Objects.nonNull(serviceInfo2)) {
                return ResultUtil.error(ResultEnum.SERVICEPORT_ALREDY_EXISTS.getMessage());
            }
        }

        serviceInfo.setType("jar");
        this.serviceInfoDao.insertSelective(serviceInfo);

        // 设置应用类别（jar/zip）
        R r = this.applicationFeignClient.load(serviceInfo.getBelongApplication());
        if (r.getData() == "") {
            return ResultUtil.error(ResultEnum.APPLICATION_NOT_EXISTS.getMessage());
        }
        Map<String, Object> applicationMap = (Map<String, Object>) r.getData();
        //新增应用服务关联关系
        ApplicationServices applicationServices = new ApplicationServices();
        applicationServices.setApplicationId(Integer.valueOf(applicationMap.get("id").toString()));
        applicationServices.setApplicationName(applicationMap.get("name").toString());
        applicationServices.setApplicationAliasName(applicationMap.get("shortName").toString());
        applicationServices.setApplicationVersion(applicationMap.get("version").toString());
        applicationServices.setServiceVersion(applicationMap.get("version").toString());
        applicationServices.setServiceId(serviceInfo.getId());
        applicationServices.setServiceName(serviceInfo.getName());
        applicationServices.setServiceAliasName(serviceInfo.getShortName());
        applicationServices.setCreatedBy(AppUserUtil.getLoginAppUser().getUsername());
        applicationServices.setCreatedTime(new Date());
        this.applicationFeignClient.add(applicationServices);

        return ResultUtil.success("新增成功！");
    }

    @Cacheable(value = "serviceInfo", key = "targetClass + methodName +#p0")
    public List<Integer> baseServicesPort() {
        List<com.cloud.service.config.ServiceInfo> baseServices = this.baseServicesProperties.getBaseServices();
        List<Integer> ports = Lists.newArrayList();
        baseServices.forEach((e) -> ports.add(e.getPort()));
        return ports;
    }

    @Override
    public Result update(ServiceInfo serviceInfo) {
        serviceInfoDao.updateByPrimaryKeySelective(serviceInfo);
        return ResultUtil.success("更新成功");
    }


    @Override
    public ServiceInfo findById(Integer id) {
        ServiceInfo serviceInfo = this.serviceInfoDao.selectByPrimaryKey(id);
        return serviceInfo;
    }

    @Override
    public int updateById(ServiceInfo serviceInfo) {
        int index = this.serviceInfoDao.updateByPrimaryKeySelective(serviceInfo);
        return index;
    }

    @Override
    @Transactional
    public Result delete(Integer id) throws Exception {
        ServiceInfo serviceInfo = null;

        serviceInfo = this.findById(id);
        //逻辑删除
        if (serviceInfo == null) {
            return ResultUtil.error("该服务不存在！！");
        }
        Map<String, String> map = new HashMap<>();
        String jarAddr = serviceInfo.getJarAddr();

        if (StringUtils.isNotEmpty(jarAddr)) {
            map.put("addr", jarAddr);
            map.put("type", serviceInfo.getType());
            String post = HttpClietUtil.post(deleteServiceApi, map, "UTF-8");
            Map result = JSONObject.parseObject(post);
            Object status = result.get("status");
            if (status != null && "0".equals(status.toString())) {
                serviceInfo.setIsDelete(1);
                this.updateById(serviceInfo);
                return ResultUtil.success("服务删除成功！");
            } else {
                String msg = result.get("msg") != null ? result.get("msg").toString() : "服务删除失败！";
                return ResultUtil.error(msg);
            }
        } else {
            serviceInfo.setIsDelete(1);
            this.updateById(serviceInfo);
            return ResultUtil.success("服务删除成功！");
        }
    }

}
