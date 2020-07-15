package com.cloud.platformappmanager.service.Impl;

import com.cloud.common.constants.CommonConstants;
import com.cloud.common.enums.AppStatusEnum;
import com.cloud.common.enums.ResultEnum;
import com.cloud.common.enums.YesOrNoEnum;
import com.cloud.common.utils.AppUserUtil;
import com.cloud.common.utils.PageUtil;
import com.cloud.common.utils.R;
import com.cloud.model.common.Page;
import com.cloud.model.platformappmanager.Application;
import com.cloud.model.platformappmanager.UserApplication;
import com.cloud.model.platformappmanager.bo.AppAdminBO;
import com.cloud.model.platformappmanager.bo.AppUsePersonBO;
import com.cloud.model.platformappmanager.bo.ApplicationAddBO;
import com.cloud.model.platformappmanager.bo.ApplicationUpdateBO;
import com.cloud.model.platformappmanager.vo.*;
import com.cloud.model.user.LoginAppUser;
import com.cloud.platformappmanager.dao.ApplicationDao;
import com.cloud.platformappmanager.dao.PlatformGroupDao;
import com.cloud.platformappmanager.dao.UserApplicationDao;
import com.cloud.platformappmanager.feign.UserCenterFeignClient;
import com.cloud.platformappmanager.service.ApplicationService;
import com.cloud.platformappmanager.service.PlatformGroupService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Classname ApplicationServiceImpl
 * @Description <h1>应用表服务层实现</h1>
 * @Author yulj
 * @Date: 2020/04/22 8:48 下午
 */
@Service
public class ApplicationServiceImpl implements ApplicationService {

    @Autowired
    private ApplicationDao applicationDao;

    @Autowired
    private UserApplicationDao userApplicationDao;

    @Autowired
    private PlatformGroupDao platformGroupDao;

    @Autowired
    private PlatformGroupService platformGroupService;

    @Autowired
    private UserCenterFeignClient userCenterFeignClient;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Page<ApplicationDetailVO> list(Map<String, Object> params) {
        params.put("delFlag", YesOrNoEnum.NO.getType());
        return commonSelectPage(params);
    }

    public Page<ApplicationDetailVO> commonSelectPage(Map<String, Object> params) {
        long total = this.applicationDao.count(params);
        PageUtil.pageUtil(params);
        List<Application> list = Lists.newArrayList();
        List<ApplicationDetailVO> applicationDetailVOS = Lists.newArrayList();
        if (total > 0) {
            list = this.applicationDao.findList(params);
            applicationDetailVOS = wrapperApplicationListData(list);
        }
        return new Page<>(total, applicationDetailVOS);
    }

    private List<ApplicationDetailVO> wrapperApplicationListData(List<Application> list) {
        List<ApplicationDetailVO> applicationDetailVOS = Lists.newArrayList();
        Map<String, String> platformGroupMap = this.platformGroupService.queryAllPlatformGroup();
        Map<String, String> appTypeMap = userCenterFeignClient.findDictSelectByType("app_type");
        List<AppClassificationVO> appClassificationList = getAppClassification();
        Map<Integer, String> appClassificationMap = appClassificationList.stream()
                .collect(Collectors.toMap(AppClassificationVO::getValue, AppClassificationVO::getName));
        list.forEach(application -> {
            ApplicationDetailVO applicationDetailVO = new ApplicationDetailVO();
            BeanUtils.copyProperties(application, applicationDetailVO);
            doWrapper(platformGroupMap, appTypeMap, appClassificationMap, applicationDetailVO);
            applicationDetailVOS.add(applicationDetailVO);
        });
        return applicationDetailVOS;
    }

    private List<ApplicationDetailVO> doSimpleWrapper(List<Application> applicationList) {
        Map<String, String> platformGroupMap = this.platformGroupService.queryAllPlatformGroup();
        List<ApplicationDetailVO> applicationDetailVOList = Lists.newArrayList();
        List<AppClassificationVO> appClassificationList = getAppClassification();
        Map<Integer, String> appClassificationMap = appClassificationList.stream()
                .collect(Collectors.toMap(AppClassificationVO::getValue, AppClassificationVO::getName));
        applicationList.forEach(application -> {
            ApplicationDetailVO applicationDetailVO = new ApplicationDetailVO();
            BeanUtils.copyProperties(application, applicationDetailVO);
            applicationDetailVO.setPlatformGroupName(platformGroupMap.get(application.getPlatformGroup().toString()));
            applicationDetailVO.setAppClassificationName(appClassificationMap.get(application.getAppClassification()));
            applicationDetailVOList.add(applicationDetailVO);
        });
        return applicationDetailVOList;
    }

    public void doWrapper(Map<String, String> platformGroupMap, Map<String, String> appTypeMap, Map<Integer, String> appClassificationMap, ApplicationDetailVO applicationDetailVO) {
        if (Objects.nonNull(applicationDetailVO.getPlatformGroup())) {
            applicationDetailVO.setAppClassificationName(appClassificationMap.get(applicationDetailVO.getAppClassification()));
        }

        if (Objects.nonNull(applicationDetailVO.getPlatformGroup())) {
            applicationDetailVO.setPlatformGroupName(platformGroupMap.get(applicationDetailVO.getPlatformGroup().toString()));
        }

        if (Objects.nonNull(applicationDetailVO.getAppType())) {
            String appTypeName = appTypeMap.get(applicationDetailVO.getAppType().toString());
            if (StringUtils.isNotBlank(appTypeName)) {
                applicationDetailVO.setAppTypeName(appTypeName);
            }
        }

        if (Objects.nonNull(applicationDetailVO.getResponsibleDept())) {
            // 先从缓存获取数据
            String responsibleDeptName = (String) redisTemplate.opsForHash().get(CommonConstants.CACHE_DEPT_KEY,
                    applicationDetailVO.getResponsibleDept().toString());
            // 若缓存中取不到，则从数据库查询
            if (StringUtils.isBlank(responsibleDeptName)) {
                responsibleDeptName = this.userCenterFeignClient.queryDeptNames(applicationDetailVO.getResponsibleDept());
            }
            if (StringUtils.isNotBlank(responsibleDeptName)) {
                applicationDetailVO.setResponsibleDeptName(responsibleDeptName);
            }
        }

        if (Objects.nonNull(applicationDetailVO.getAppAdmin())) {
            String appAdminName = (String) redisTemplate.opsForHash().get(CommonConstants.CACHE_USER_KEY,
                    applicationDetailVO.getAppAdmin().toString());
            if (StringUtils.isBlank(appAdminName)) {
                appAdminName = this.userCenterFeignClient.getNameInUserIds(applicationDetailVO.getAppAdmin());
            }
            applicationDetailVO.setAppAdmin(applicationDetailVO.getAppAdmin() + CommonConstants.USER_ID_ACCUMULATE);
            if (StringUtils.isNotBlank(appAdminName)) {
                applicationDetailVO.setAppAdminName(appAdminName);
            }
        }

        String appUsePerson = applicationDetailVO.getAppUsePerson();
        if (StringUtils.isNotBlank(appUsePerson)) {
            String[] ids = appUsePerson.split(",");
            Set<String> deptIds = Sets.newHashSet();
            Set<String> userIds = Sets.newHashSet();
            for (int i = 0; i < ids.length; i++) {
                Integer id = Integer.valueOf(ids[i]);
                if (id > CommonConstants.USER_ID_ACCUMULATE) {
                    userIds.add(String.valueOf(id - CommonConstants.USER_ID_ACCUMULATE));
                } else {
                    deptIds.add(String.valueOf(id));
                }
            }
            List<String> stringList = Lists.newArrayList();
            if (!deptIds.isEmpty()) {
                List<String> deptNameList = redisTemplate.opsForHash().multiGet(CommonConstants.CACHE_DEPT_KEY, deptIds);
                // 若缓存中为空，则从数据库中查询
                if (deptNameList.isEmpty() || deptNameList.contains(null)) {
                    String deptNames = this.userCenterFeignClient.queryDeptNames(setToIntArray(deptIds));
                    deptNameList = Arrays.asList(deptNames.split(","));
                }
                stringList.addAll(deptNameList);
            }
            if (!userIds.isEmpty()) {
                List<String> userNameList = redisTemplate.opsForHash().multiGet(CommonConstants.CACHE_USER_KEY, userIds);
                // 若缓存中为空，则从数据库中查询
                if (userNameList.isEmpty() || userNameList.contains(null)) {
                    String userNames = this.userCenterFeignClient.getNameInUserIds(setToIntArray(userIds));
                    userNameList = Arrays.asList(userNames.split(","));
                }
                stringList.addAll(userNameList);
            }
            if (!stringList.isEmpty()) {
                String appUsePersonName = stringList.stream().collect(Collectors.joining(","));
                applicationDetailVO.setAppUsePersonName(appUsePersonName);
            }
        }
    }

    private Integer[] setToIntArray(Set<String> stringSet) {
        Object[] objArray = stringSet.toArray();
        Integer[] intArray = new Integer[objArray.length];
        for (int i = 0; i < objArray.length; i++) {
            intArray[i] = Integer.parseInt(objArray[i].toString());
        }
        return intArray;
    }

    @Override
    @Transactional
    @CacheEvict(value = "applicationData", allEntries = true, beforeInvocation = true)
    public R add(ApplicationAddBO applicationAddBO) {
        //属性校验
        Application applicationByName = this.applicationDao
                .selectOneByNameAndDelFlag(applicationAddBO.getName(), YesOrNoEnum.NO.getType());
        if (applicationByName != null) {
            return R.failed("应用名称已存在！");
        }
        Application applicationByShortName = this.applicationDao
                .selectOneByShortNameAndDelFlag(applicationAddBO.getShortName(), YesOrNoEnum.NO.getType());
        if (applicationByShortName != null) {
            return R.failed("应用简称已存在！");
        }

        Application application = applicationAddBOToApplication(applicationAddBO);
        this.applicationDao.insertSelective(application);

        return R.ok(ResultEnum.ADD_OPERATE_SUCCESS.getMessage());
    }

    private Application applicationAddBOToApplication(ApplicationAddBO applicationAddBO) {
        Application application = new Application();
        BeanUtils.copyProperties(applicationAddBO, application);
        LoginAppUser user = AppUserUtil.getLoginAppUser();
        if (user != null) {
            application.setCreateBy(user.getUsername());
            application.setUpdateBy(user.getUsername());
        }
        application.setCreateTime(new Date());
        application.setUpdateTime(application.getCreateTime());
        application.setRunStatus(AppStatusEnum.NOT_RUNNING.getCode());
        application.setDelFlag(YesOrNoEnum.NO.getType());
        return application;
    }

    @Override
    @Transactional
    @CacheEvict(value = "applicationData", allEntries = true, beforeInvocation = true)
    public R update(ApplicationUpdateBO applicationUpdateBO) {
        //属性校验
        if (StringUtils.isNotBlank(applicationUpdateBO.getName())) {
            Application applicationByName = this.applicationDao.
                    selectOneByNameAndDelFlagAndIdNot(applicationUpdateBO.getName(),
                            YesOrNoEnum.NO.getType(), applicationUpdateBO.getId());
            if (applicationByName != null) {
                return R.failed("应用名称已存在！");
            }
        }
        if (StringUtils.isNotBlank(applicationUpdateBO.getShortName())) {
            Application applicationByShortName = this.applicationDao.
                    selectOneByShortNameAndDelFlagAndIdNot(applicationUpdateBO.getShortName(),
                            YesOrNoEnum.NO.getType(), applicationUpdateBO.getId());
            if (applicationByShortName != null) {
                return R.failed("应用简称已存在！");
            }
        }

        Application application = applicationUpdateBOToApplication(applicationUpdateBO);
        this.applicationDao.updateByIdSelective(application);

        // 同步更新用户应用关联数据
        this.userApplicationDao.updateAppInfoByAppId(application.getName(), application.getLogoUrl(),
                application.getIndexUrl(), application.getId());

        return R.ok(ResultEnum.UPDATE_OPERATE_SUCCESS.getMessage());
    }

    private Application applicationUpdateBOToApplication(ApplicationUpdateBO applicationUpdateBO) {
        Application application = new Application();
        BeanUtils.copyProperties(applicationUpdateBO, application);
        LoginAppUser user = AppUserUtil.getLoginAppUser();
        if (user != null) {
            application.setUpdateBy(user.getUsername());
        }
        application.setUpdateTime(new Date());
        return application;
    }

    @Override
    @Transactional
    @CacheEvict(value = "applicationData", allEntries = true, beforeInvocation = true)
    public R delete(Integer id) {
        Application application = this.applicationDao.selectById(id);
        if (application == null) {
            return R.failed(ResultEnum.CAN_NOT_FIND_RECORD.getMessage());
        }
        // 校验是否在运行
        if (application.getRunStatus().equals(AppStatusEnum.RUNNING.getCode())) {
            return R.failed("该应用运行中，不能进行删除操作！");
        }

        application.setDelFlag(YesOrNoEnum.YES.getType());
        LoginAppUser user = AppUserUtil.getLoginAppUser();
        if (user != null) {
            application.setUpdateBy(user.getUsername());
        }
        application.setUpdateTime(new Date());
        this.applicationDao.updateByIdSelective(application);

        // 删除用户应用关联数据
        this.userApplicationDao.deleteByAppId(id);

        return R.ok(ResultEnum.DELETE_OPERATE_SUCCESS.getMessage());
    }

    @Override
    @Cacheable(value = "applicationData", key = "targetClass + methodName +#p0", unless = "#result == null")
    public R load(Integer id) {
        Application application = this.applicationDao.selectById(id);
        Map<String, String> platformGroupMap = this.platformGroupService.queryAllPlatformGroup();
        Map<String, String> appTypeMap = userCenterFeignClient.findDictSelectByType("app_type");
        List<AppClassificationVO> appClassificationList = getAppClassification();
        Map<Integer, String> appClassificationMap = appClassificationList.stream()
                .collect(Collectors.toMap(AppClassificationVO::getValue, AppClassificationVO::getName));
        ApplicationDetailVO applicationDetailVO = new ApplicationDetailVO();
        BeanUtils.copyProperties(application, applicationDetailVO);
        doWrapper(platformGroupMap, appTypeMap, appClassificationMap, applicationDetailVO);
        applicationDetailVO.setHasUseAuthority(Boolean.FALSE);
        LoginAppUser user = AppUserUtil.getLoginAppUser();
        if (null != user) {
            UserApplication userApplication = this.userApplicationDao.findOneByAppIdAndUserId(id, user.getId());
            if (null != userApplication) {
                applicationDetailVO.setHasUseAuthority(Boolean.TRUE);
            }
        }
        return R.ok(applicationDetailVO);
    }

    @Override
    public Page<ApplicationDetailVO> gradeAuthorizeList(Map<String, Object> params) {
        params.put("delFlag", YesOrNoEnum.NO.getType());
        LoginAppUser user = AppUserUtil.getLoginAppUser();
        if (user != null) {
            params.put("appAdmin", user.getId());
            // 若为超级管理员角色，则查询所有应用
            if (StringUtils.isNotBlank(user.getRoleid())) {
                if (user.getRoleid().equals(CommonConstants.SUPER_ADMIN_ROLE_ID)) {
                    params.remove("appAdmin");
                }
            }
        }
        return commonSelectPage(params);
    }

    @Override
    @Transactional
    public R updateAppAdmin(AppAdminBO appAdminBO) {
        if (appAdminBO.getAppAdmin() < CommonConstants.USER_ID_ACCUMULATE) {
            return R.failed("应用管理员只能为人员！");
        }

        appAdminBO.setAppAdmin(appAdminBO.getAppAdmin() - CommonConstants.USER_ID_ACCUMULATE);
        Application application = new Application();
        BeanUtils.copyProperties(appAdminBO, application);
        LoginAppUser user = AppUserUtil.getLoginAppUser();
        if (user != null) {
            application.setUpdateBy(user.getUsername());
        }
        application.setUpdateTime(new Date());
        applicationDao.updateByIdSelective(application);

        return R.ok(ResultEnum.UPDATE_OPERATE_SUCCESS.getMessage());
    }

    @Override
    @Transactional
    public R setAppUsePerson(AppUsePersonBO appUsePersonBO) {
        Application application = new Application();
        BeanUtils.copyProperties(appUsePersonBO, application);
        Integer applicationId = appUsePersonBO.getId();
        application.setAppUsePerson(appUsePersonBO.getAppUsePerson());

        // 先删除该应用关联的所有用户应用关联数据
        this.userApplicationDao.deleteByAppId(applicationId);

        // 批量插入用户应用关联数据
        Set<Integer> userIdSet = dealUserIds(appUsePersonBO.getAppUsePerson());
        // 查询超级管理员对应的用户id
        List<Integer> userIdInRoleIds = userCenterFeignClient.getUserIdInRoleIds(CommonConstants.SUPER_ADMIN_ROLE_ID);
        userIdSet.addAll(userIdInRoleIds);
        Application sourceAppData = this.applicationDao.selectById(applicationId);
        String appName = sourceAppData.getName();
        String logoUrl = sourceAppData.getLogoUrl();
        String indexUrl = sourceAppData.getIndexUrl();
        String createBy = "";
        LoginAppUser user = AppUserUtil.getLoginAppUser();
        if (user != null) {
            createBy = user.getUsername();
        }
        String finalCreateBy = createBy;
        Date createTime = new Date();
        List<UserApplication> userApplicationList = Lists.newArrayList();
        userIdSet.forEach(userId -> userApplicationList.add(
                UserApplication.builder().userId(userId).appId(applicationId).appName(appName)
                        .logoUrl(logoUrl).indexUrl(indexUrl).createBy(finalCreateBy).createTime(createTime)
                        .build()
        ));
        this.userApplicationDao.batchInsert(userApplicationList);

        // 更新工业App应用使用配置
        this.applicationDao.updateByIdSelective(application);
        return R.ok(ResultEnum.UPDATE_OPERATE_SUCCESS.getMessage());
    }

    @Override
    @Cacheable(value = "applicationData", key = "targetClass + methodName +#p0", unless = "#result == null")
    public Application loadById(Integer id) {
        return this.applicationDao.selectById(id);
    }

    @Override
    public R navigationBarApps(Integer appClassification) {
        List<PlatformGroupVO> platformGroupVOS = platformGroupDao.
                selectIdAndGroupName(YesOrNoEnum.NO.getType(), YesOrNoEnum.YES.getType());
        Set<String> groupNameSet = platformGroupVOS.stream()
                .map(PlatformGroupVO::getGroupName)
                .collect(Collectors.toSet());

        Map<String, Object> queryMap = Maps.newHashMap();
        queryMap.put("appClassification", appClassification);
        List<Application> applicationList = this.applicationDao.findList(queryMap);
        List<ApplicationDetailVO> applicationDetailVOS = Lists.newArrayList();
        if (!applicationList.isEmpty()) {
            applicationDetailVOS = doSimpleWrapper(applicationList);
        }

        List<MyAppVO> myAppVOList = Lists.newArrayList();
        for (String groupName : groupNameSet) {
            MyAppVO myAppVO = new MyAppVO();
            myAppVO.setGroupName(groupName);
            List<UserApplicationVO> userApplicationVOList = Lists.newArrayList();
            applicationDetailVOS.forEach(applicationDetailVO -> {
                if (applicationDetailVO.getPlatformGroupName().equals(groupName)) {
                    UserApplicationVO userApplicationVO = new UserApplicationVO();
                    userApplicationVO.setAppId(applicationDetailVO.getId());
                    userApplicationVO.setAppName(applicationDetailVO.getName());
                    userApplicationVO.setGroupName(groupName);
                    userApplicationVOList.add(userApplicationVO);
                }
            });
            myAppVO.setChildren(userApplicationVOList);
            myAppVOList.add(myAppVO);
        }

        return R.ok(myAppVOList);
    }

    @Override
    public R indexList(Integer appClassification) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("appClassification", appClassification);
        params.put("delFlag", YesOrNoEnum.NO.getType());
        List<Application> applicationList = this.applicationDao.findList(params);
        List<ApplicationDetailVO> applicationDetailVOS = Lists.newArrayList();
        if (!applicationList.isEmpty()) {
            applicationDetailVOS = doSimpleWrapper(applicationList);
        }

        return R.ok(applicationDetailVOS);
    }

    @Override
    public R indexLoad(Integer appId) {
        Application application = this.applicationDao.selectById(appId);
        Map<String, String> platformGroupMap = this.platformGroupService.queryAllPlatformGroup();
        List<AppClassificationVO> appClassificationList = getAppClassification();
        Map<Integer, String> appClassificationMap = appClassificationList.stream()
                .collect(Collectors.toMap(AppClassificationVO::getValue, AppClassificationVO::getName));
        ApplicationDetailVO applicationDetailVO = new ApplicationDetailVO();
        BeanUtils.copyProperties(application, applicationDetailVO);
        applicationDetailVO.setPlatformGroupName(platformGroupMap.get(application.getPlatformGroup().toString()));
        applicationDetailVO.setHasUseAuthority(Boolean.FALSE);
        applicationDetailVO.setAppClassificationName(appClassificationMap.get(applicationDetailVO.getAppClassification()));
        LoginAppUser user = AppUserUtil.getLoginAppUser();
        if (null != user) {
            UserApplication userApplication = this.userApplicationDao.findOneByAppIdAndUserId(appId, user.getId());
            if (null != userApplication) {
                applicationDetailVO.setHasUseAuthority(Boolean.TRUE);
            }
        }
        return R.ok(applicationDetailVO);
    }

    @Override
    public R statistics() {
        Map<String, Object> map = Maps.newHashMap();
        map.put("delFlag", YesOrNoEnum.NO.getType());
        List<Application> list = this.applicationDao.findList(map);
        Integer platformCount = 0;
        Integer appCount = 0;
        for (Application application : list) {
            if (application.getAppClassification().equals(CommonConstants.APP_CLASSIFICATION_OF_PLATFORM)) {
                platformCount++;
            } else if (application.getAppClassification().equals(CommonConstants.APP_CLASSIFICATION_OF_APP)) {
                appCount++;
            }
        }
        map.clear();
        map.put("platformCount", platformCount);
        map.put("appCount", appCount);

        return R.ok(map);
    }

    @Override
    public List<AppClassificationVO> getAppClassification() {
        List<AppClassificationVO> appClassificationVOList = Lists.newArrayList();
        AppClassificationVO appClassificationVO = new AppClassificationVO();
        appClassificationVO.setValue(CommonConstants.APP_CLASSIFICATION_OF_PLATFORM);
        appClassificationVO.setName(CommonConstants.APP_CLASSIFICATION_OF_PLATFORM_VALUE);
        appClassificationVOList.add(appClassificationVO);

        AppClassificationVO classificationVO = new AppClassificationVO();
        classificationVO.setValue(CommonConstants.APP_CLASSIFICATION_OF_APP);
        classificationVO.setName(CommonConstants.APP_CLASSIFICATION_OF_APP_VALUE);
        appClassificationVOList.add(classificationVO);

        return appClassificationVOList;
    }

    private Set<Integer> dealUserIds(String appUsePerson) {
        String[] userIds = appUsePerson.split(",");
        Set<Integer> userIdSet = Sets.newHashSet();
        for (int i = 0; i < userIds.length; i++) {
            Integer value = Integer.valueOf(userIds[i]);
            if (value > CommonConstants.USER_ID_ACCUMULATE) {
                userIdSet.add(value - CommonConstants.USER_ID_ACCUMULATE);
            }
        }
        return userIdSet;
    }

    @Override
    public R selectAllApp() {
        List<Application> applications = this.applicationDao.selectAllByDelFlag(YesOrNoEnum.NO.getType());
        return R.ok(applications);
    }
}
