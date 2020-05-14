package com.cloud.appmanagercenter.service.Impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.cloud.appmanagercenter.dao.ApplicationDao;
import com.cloud.appmanagercenter.dao.ApplicationUserSyncDao;
import com.cloud.appmanagercenter.feign.UserCenterFeignClient;
import com.cloud.appmanagercenter.service.ApplicationUserSyncService;
import com.cloud.common.constants.CommonConstants;
import com.cloud.common.enums.YesOrNoEnum;
import com.cloud.common.utils.AppUserUtil;
import com.cloud.common.utils.HttpRequestUtil;
import com.cloud.common.utils.PageUtil;
import com.cloud.common.utils.R;
import com.cloud.model.appmanagercenter.Application;
import com.cloud.model.appmanagercenter.ApplicationUserSync;
import com.cloud.model.appmanagercenter.bo.ApplicationUserSyncBO;
import com.cloud.model.appmanagercenter.bo.UserSyncBO;
import com.cloud.model.appmanagercenter.vo.ApplicationUserSyncVO;
import com.cloud.model.appmanagercenter.vo.UserSyncResultVO;
import com.cloud.model.common.Page;
import com.cloud.model.user.LoginAppUser;
import com.cloud.model.user.bo.SysUserUpdateBO;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Classname ApplicationUserSyncServiceImpl
 * @Description <h1>应用用户同步服务层实现</h1>
 * @Author yulj
 * @Date: 2020/04/24 8:17 下午
 */
@Service
@Slf4j
public class ApplicationUserSyncServiceImpl implements ApplicationUserSyncService {

    public static final String USER_SYNC_ACTION_REGISTER = "注册用户";

    public static final String USER_SYNC_ACTION_LOGOUT = "注销用户";

    public static final String USER_SYNC_STATUS_SUCCESS = "成功";

    public static final String USER_SYNC_STATUS_FAIL = "失败";

    public static final String USER_SYNC_OPER_FAIL_MESSAGE = "操作失败，请联系系统管理员";

    public static final String REMOTE_SYSTEM_ERROR = "操作失败，目标系统发生错误";

    public static final String REMOTE_SYSTEM_NOT_PROVIDER_REGISTER_URL = "操作失败，目标系统未提供注册用户地址";

    public static final String REMOTE_SYSTEM_NOT_PROVIDER_LOGOUT_URL = "操作失败，目标系统未提供注销用户地址";

    @Autowired
    private ApplicationUserSyncDao applicationUserSyncDao;

    @Autowired
    private ApplicationDao applicationDao;

    @Autowired
    private UserCenterFeignClient userCenterFeignClient;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    @Transactional
    public R removeApplicationUserSync(Integer userId) {
        this.applicationUserSyncDao.deleteByUserId(userId);
        return R.ok();
    }

    @Override
    public Page<ApplicationUserSyncVO> pageList(Map<String, Object> params) {
        params.put("delFlag", YesOrNoEnum.NO.getType());
        long total = this.applicationDao.count(params);
        if (params.get("showAll").equals(Boolean.TRUE.toString())) {
            params.put("start", MapUtils.getInteger(params, "limit"));
            params.put("length", total);
        } else {
            PageUtil.pageUtil(params);
        }
        List<ApplicationUserSyncVO> list = Lists.newArrayList();
        if (total > 0) {
            list = this.applicationDao.findAppIdAndName(params);
            Integer userId = Integer.parseInt(params.get("userId").toString());
            List<Integer> appIdList = this.applicationUserSyncDao.findAppIdByUserId(userId);
            if (appIdList.size() > 0) {
                list.forEach(applicationUserSyncVO -> {
                    if (appIdList.contains(applicationUserSyncVO.getAppId())) {
                        applicationUserSyncVO.setSyncSttaus(Boolean.TRUE);
                    }
                });
            }
        }
        return new Page<>(total, list);
    }

    @Override
    @Transactional
    public R updateApplicationUserSync(ApplicationUserSyncBO... appAuthoritys) {
        if (appAuthoritys.length == 0) {
            return R.failed("未执行任何操作！");
        }

        final Integer userId = appAuthoritys[0].getUserId();

        // 遍历数组，取出所有应用id
        Set<Integer> appIdSet = Sets.newHashSet();
        for (ApplicationUserSyncBO applicationUserSyncBO : appAuthoritys) {
            appIdSet.add(applicationUserSyncBO.getAppId());
        }
        // 根据 id 集合查询应用数据，用于获取注册、注销地址
        List<Application> applicationList = this.applicationDao.findAllByidInAndDelFlag(appIdSet, YesOrNoEnum.NO.getType());
        Map<Integer, Application> applicationMap = applicationList.stream()
                .collect(Collectors.toMap(Application::getId, Application -> Application));

        // 根据 用户id 查询应用用户已同步数据
        Map<String, Object> queryMap = Maps.newHashMap();
        queryMap.put("userId", userId);
        List<ApplicationUserSync> applicationUserSyncList = this.applicationUserSyncDao.findList(queryMap);
        Map<String, Integer> tempMap = Maps.newHashMap();
        applicationUserSyncList.forEach(applicationUserSync -> {
            tempMap.put(StrUtil.format(
                    "userId_{}:appId_{}", applicationUserSync.getUserId(), applicationUserSync.getAppId()),
                    applicationUserSync.getId());
        });

        // 构建用户同步数据对象
        UserSyncBO userSyncBO = buildUserSyncBO(userId);

        List<UserSyncResultVO> userSyncResultVOList = Lists.newArrayList();
        List<ApplicationUserSync> needToInsert = Lists.newArrayList();
        List<Integer> needToDelete = Lists.newArrayList();
        // 记录用户注册产品数量
        Integer updateCounts = 0;
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        for (ApplicationUserSyncBO applicationUserSyncBO : appAuthoritys) {
            String tempStr = StrUtil.format("userId_{}:appId_{}", applicationUserSyncBO.getUserId(), applicationUserSyncBO.getAppId());
            // 若应用用户同步数据中未包含，并且状态为开放，则为注册逻辑
            String action = "";
            String status = "";
            String message = "";
            if (!tempMap.containsKey(tempStr) && applicationUserSyncBO.getSyncSttaus().equals(Boolean.TRUE)) {
                Application application = applicationMap.get(applicationUserSyncBO.getAppId());
                UserSyncResultVO userSyncResultVO = new UserSyncResultVO();
                userSyncResultVO.setAppName(application.getName());
                action = USER_SYNC_ACTION_REGISTER;
                if (StringUtils.isNotBlank(application.getUserRegisterUrl())) {
                    try {
                        HttpEntity<String> requestEntity = HttpRequestUtil.getRequestEntity(JSONUtil.toJsonStr(userSyncBO));
                        //发送 POST 请求注册用户
                        ResponseEntity<String> response = restTemplate.exchange(application.getUserRegisterUrl().trim(),
                                HttpMethod.POST, requestEntity, String.class);
                        JSONObject jsonObject = new JSONObject(response.getBody());
                        Object code = jsonObject.get("code");
                        if (null != code) {
                            if (response.getStatusCode().equals(HttpStatus.OK) &&
                                    code.equals(CommonConstants.OTHER_PLATFORM_RESPONSE_CODE_SUCCESS)) {
                                updateCounts++;
                                status = USER_SYNC_STATUS_SUCCESS;
                                ApplicationUserSync applicationUserSync = new ApplicationUserSync();
                                applicationUserSync.setAppId(applicationUserSyncBO.getAppId());
                                applicationUserSync.setUserId(userId);
                                if (null != loginAppUser) {
                                    applicationUserSync.setCreateBy(loginAppUser.getUsername());
                                    applicationUserSync.setCreateTime(new Date());
                                }
                                needToInsert.add(applicationUserSync);
                            } else {
                                status = USER_SYNC_STATUS_FAIL;
                                message = REMOTE_SYSTEM_ERROR;
                            }
                        } else {
                            // python 建模平台返回 JSON
                            Object result = jsonObject.get("result");
                            if (null != result) {
                                JSONObject json = JSONUtil.parseObj(result.toString());
                                Object codeObj = json.get("code");
                                if (response.getStatusCode().equals(HttpStatus.OK) &&
                                        codeObj.equals(CommonConstants.OTHER_PLATFORM_RESPONSE_CODE_SUCCESS)) {
                                    updateCounts++;
                                    status = USER_SYNC_STATUS_SUCCESS;
                                    ApplicationUserSync applicationUserSync = new ApplicationUserSync();
                                    applicationUserSync.setAppId(applicationUserSyncBO.getAppId());
                                    applicationUserSync.setUserId(userId);
                                    if (null != loginAppUser) {
                                        applicationUserSync.setCreateBy(loginAppUser.getUsername());
                                        applicationUserSync.setCreateTime(new Date());
                                    }
                                    needToInsert.add(applicationUserSync);
                                } else {
                                    status = USER_SYNC_STATUS_FAIL;
                                    message = REMOTE_SYSTEM_ERROR;
                                }
                            } else {
                                status = USER_SYNC_STATUS_FAIL;
                                message = USER_SYNC_OPER_FAIL_MESSAGE;
                            }
                        }
                    } catch (Exception e) {
                        log.error("【同步用户失败】请求地址={}，错误信息为{}", application.getUserRegisterUrl().trim(), e.getMessage());
                        status = USER_SYNC_STATUS_FAIL;
                        message = USER_SYNC_OPER_FAIL_MESSAGE;
                    }
                } else {
                    status = USER_SYNC_STATUS_FAIL;
                    message = REMOTE_SYSTEM_NOT_PROVIDER_REGISTER_URL;
                }
                userSyncResultVO.setAction(action);
                userSyncResultVO.setStatus(status);
                userSyncResultVO.setMessage(message);
                userSyncResultVO.setOperateTime(new Date());
                userSyncResultVOList.add(userSyncResultVO);
            } else if (tempMap.containsKey(tempStr) && applicationUserSyncBO.getSyncSttaus().equals(Boolean.FALSE)) {
                // 执行注销操作
                Application application = applicationMap.get(applicationUserSyncBO.getAppId());
                UserSyncResultVO userSyncResultVO = new UserSyncResultVO();
                userSyncResultVO.setAppName(application.getName());
                action = USER_SYNC_ACTION_LOGOUT;
                if (StringUtils.isNotBlank(application.getUserLogoutUrl())) {
                    //发送 DELETE 请求注销用户
                    String logoutUrl = application.getUserLogoutUrl().trim();
                    try {
                        // 若为移动应用平台，则为 RequestParam
                        if (logoutUrl.contains(CommonConstants.MOBILE_PLATFORM_API_STR)) {
                            logoutUrl = StrUtil.format("{}&account={}", logoutUrl, userSyncBO.getAccount());
                        } else if (logoutUrl.contains(CommonConstants.DATA_INTEGRATION_PLATFORM_API_STR)) {
                            logoutUrl = StrUtil.format("{}?account={}", logoutUrl, userSyncBO.getAccount());
                        } else {
                            logoutUrl = StrUtil.format("{}/{}", logoutUrl, userSyncBO.getAccount());
                        }
                        ResponseEntity<String> response = null;
                        // 若为 SpringMVC请求，则请求方式为POST
                        if (logoutUrl.contains(CommonConstants.DATA_INTEGRATION_PLATFORM_API_STR)) {
                            response = restTemplate.exchange(logoutUrl, HttpMethod.POST, null, String.class);
                        } else {
                            response = restTemplate.exchange(logoutUrl, HttpMethod.DELETE, null, String.class);
                        }
                        JSONObject jsonObject = new JSONObject(response.getBody());
                        Object code = jsonObject.get("code");
                        if (response.getStatusCode().equals(HttpStatus.OK) &&
                                code.equals(CommonConstants.OTHER_PLATFORM_RESPONSE_CODE_SUCCESS)) {
                            updateCounts--;
                            status = USER_SYNC_STATUS_SUCCESS;
                            needToDelete.add(tempMap.get(tempStr));
                        } else {
                            status = USER_SYNC_STATUS_FAIL;
                            message = USER_SYNC_OPER_FAIL_MESSAGE;
                        }
                    } catch (Exception e) {
                        log.error("【同步用户失败】请求地址={}，错误信息为{}", application.getUserRegisterUrl().trim(), e.getMessage());
                        status = USER_SYNC_STATUS_FAIL;
                        message = USER_SYNC_OPER_FAIL_MESSAGE;
                    }
                } else {
                    status = USER_SYNC_STATUS_FAIL;
                    message = REMOTE_SYSTEM_NOT_PROVIDER_LOGOUT_URL;
                }
                userSyncResultVO.setAction(action);
                userSyncResultVO.setStatus(status);
                userSyncResultVO.setMessage(message);
                userSyncResultVO.setOperateTime(new Date());
                userSyncResultVOList.add(userSyncResultVO);
            }
        }

        // 插入应用用户同步状态数据
        if (needToInsert.size() > 0) {
            this.applicationUserSyncDao.batchInsert(needToInsert);
        }
        // 删除应用用户同步状态数据
        if (needToDelete.size() > 0) {
            this.applicationUserSyncDao.deleteByIdIn(needToDelete);
        }
        // 更新用户应用注册数
        SysUserUpdateBO sysUserUpdateBO = new SysUserUpdateBO();
        sysUserUpdateBO.setId(userId);
        sysUserUpdateBO.setProductRegisterCount(updateCounts);
        this.userCenterFeignClient.updateProductRegisterCount(sysUserUpdateBO);

        return R.ok(userSyncResultVOList);
    }

    private UserSyncBO buildUserSyncBO(Integer userId) {
        R result = userCenterFeignClient.findUserById(userId);
        Map<String, Object> userMap = (Map<String, Object>) result.getData();
        // 构建用户同步数据对象
        UserSyncBO userSyncBO = UserSyncBO.builder()
                .account(userMap.get("account").toString())
                .name(userMap.get("name").toString())
                .phone(userMap.get("phone").toString()).build();
        return userSyncBO;
    }

}
