package com.cloud.appmanagercenter.service.Impl;

import com.cloud.appmanagercenter.dao.ApplicationServicesDao;
import com.cloud.appmanagercenter.service.ApplicationServicesService;
import com.cloud.common.enums.YesOrNoEnum;
import com.cloud.common.utils.PageUtil;
import com.cloud.common.utils.R;
import com.cloud.model.appmanagercenter.ApplicationServices;
import com.cloud.model.common.Page;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Classname ApplicationServicesServiceImpl
 * @Description 应用服务接口实现
 * @Author yulj
 * @Date: 2019/07/04 09:35
 */
@Service
@Slf4j
public class ApplicationServicesServiceImpl implements ApplicationServicesService {

    @Autowired
    private ApplicationServicesDao applicationServicesDao;

    @Override
    public R save(ApplicationServices applicationServices) {
        applicationServices.setDeleteFlag(YesOrNoEnum.NO.getType());
        int insertRow = this.applicationServicesDao.insertSelective(applicationServices);
        if (insertRow == 1) {
            return R.ok();
        } else {
            log.error("【创建应用服务关联】应用服务关联记录创建错误，应用服务关联信息={}", applicationServices.toString());
            return R.failed("新增操作异常");
        }
    }

    @Override
    public R update(ApplicationServices applicationServices) {
        int insertRow = this.applicationServicesDao.updateByIdSelective(applicationServices);
        if (insertRow == 1) {
            return R.ok();
        } else {
            log.error("【更新应用服务关联】应用服务关联记录更新错误，应用服务关联信息={}", applicationServices.toString());
            return R.failed("更新操作异常");
        }
    }

    @Override
    public ApplicationServices findById(Integer id) {
        return this.applicationServicesDao.selectById(id);
    }

    @Override
    public R delete(Integer id) {
        ApplicationServices applicationServices = new ApplicationServices();
        applicationServices.setId(id);
        applicationServices.setDeleteFlag(YesOrNoEnum.YES.getType());

        int insertRow = this.applicationServicesDao.updateByIdSelective(applicationServices);
        if (insertRow == 1) {
            return R.ok();
        } else {
            log.error("【删除应用服务关联】应用服务关联记录删除错误，应用服务关联信息id={}", id);
            return R.failed("删除操作异常");
        }
    }

    @Override
    public Page<ApplicationServices> listBySelective(Map<String, Object> params) {
        long total = this.applicationServicesDao.count(params);
        PageUtil.pageUtil(params);
        List<ApplicationServices> list = Lists.newArrayList();
        if (total > 0) {
            PageUtil.pageParamConver(params, true);
            list = this.applicationServicesDao.findList(params);
        }
        return new Page<>(total, list);
    }
}
