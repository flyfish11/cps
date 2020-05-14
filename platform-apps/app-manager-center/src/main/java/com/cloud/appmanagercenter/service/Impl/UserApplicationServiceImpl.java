package com.cloud.appmanagercenter.service.Impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.cloud.appmanagercenter.dao.PlatformGroupDao;
import com.cloud.appmanagercenter.dao.UserApplicationDao;
import com.cloud.appmanagercenter.service.ApplicationService;
import com.cloud.appmanagercenter.service.UserApplicationService;
import com.cloud.common.constants.CommonConstants;
import com.cloud.common.enums.ResultEnum;
import com.cloud.common.enums.YesOrNoEnum;
import com.cloud.common.utils.AppUserUtil;
import com.cloud.common.utils.R;
import com.cloud.model.appmanagercenter.Application;
import com.cloud.model.appmanagercenter.UserApplication;
import com.cloud.model.appmanagercenter.vo.MyAppVO;
import com.cloud.model.appmanagercenter.vo.PlatformGroupVO;
import com.cloud.model.appmanagercenter.vo.UserApplicationVO;
import com.cloud.model.user.LoginAppUser;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @Classname UserApplicationServiceImpl
 * @Description <h1>用户应用表服务层实现</h1>
 * @Author yulj
 * @Date: 2020/04/24 9:25 上午
 */
@Service
@Slf4j
public class UserApplicationServiceImpl implements UserApplicationService {

    @Autowired
    private UserApplicationDao userApplicationDao;

    @Autowired
    private PlatformGroupDao platformGroupDao;

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public R myApps(Map<String, Object> params) {
        LoginAppUser user = AppUserUtil.getLoginAppUser();
        if (user == null) {
            return R.failed(ResultEnum.GET_CURRENT_USER_INFO_ERROR.getMessage());
        }

        params.put("userId", user.getId());
        List<UserApplicationVO> userApplicationVOList = this.userApplicationDao.findAllUserApplication(params);
        List<PlatformGroupVO> platformGroupVOS = platformGroupDao.
                selectIdAndGroupName(YesOrNoEnum.NO.getType(), YesOrNoEnum.YES.getType());
        Set<String> groupNameSet = platformGroupVOS.stream()
                .map(PlatformGroupVO::getGroupName)
                .collect(Collectors.toSet());
        List<MyAppVO> myAppVOList = Lists.newArrayList();
        for (String groupName : groupNameSet) {
            MyAppVO myAppVO = new MyAppVO();
            myAppVO.setGroupName(groupName);
            List<UserApplicationVO> list = userApplicationVOList.stream()
                    .filter(userApplicationVO -> userApplicationVO.getGroupName().equals(groupName))
                    .collect(Collectors.toList());
            myAppVO.setChildren(list);
            myAppVOList.add(myAppVO);
        }

        return R.ok(myAppVOList);
    }

    @Override
    public R checkAppUser(Integer appId, String account) {
        Application application = this.applicationService.loadById(appId);
        if (null != application) {
            String userCheckUrl = application.getUserCheckUrl();
            if (StringUtils.isNotBlank(userCheckUrl)) {
                // 若为移动应用平台，参数为 RequestParam
                if (userCheckUrl.contains(CommonConstants.MOBILE_PLATFORM_API_STR)) {
                    userCheckUrl = StrUtil.format("{}&account={}", userCheckUrl, account);
                } else if (userCheckUrl.contains(CommonConstants.DATA_INTEGRATION_PLATFORM_API_STR)) {
                    userCheckUrl = StrUtil.format("{}?account={}", userCheckUrl, account);
                } else {
                    userCheckUrl = StrUtil.format("{}/{}", userCheckUrl, account);
                }
                try {
                    ResponseEntity<String> response = restTemplate.exchange(userCheckUrl, HttpMethod.GET, null, String.class);
                    JSONObject jsonObject = new JSONObject(response.getBody());
                    Object code = jsonObject.get("code");
                    Object data = jsonObject.get("data");
                    if (response.getStatusCode().equals(HttpStatus.OK) &&
                            code.equals(CommonConstants.OTHER_PLATFORM_RESPONSE_CODE_SUCCESS)) {
                        if (data.equals(Boolean.TRUE)) {
                            return R.ok(ResultEnum.APP_USER_CHECK_SUCCESS.getMessage());
                        } else if (data.equals(Boolean.FALSE)) {
                            return R.failed(ResultEnum.NEED_TO_AUTHORIZE_ACCOUNT.getMessage());
                        }
                    }
                } catch (Exception e) {
                    log.error("【应用用户校验失败】请求地址={}，错误信息为{}", application.getUserRegisterUrl().trim(), e.getMessage());
                    return R.failed(ResultEnum.SERVICE_PROVISIONAL_UNUSABLE.getMessage());
                }
            }
        }
        return R.failed(ResultEnum.SERVICE_PROVISIONAL_UNUSABLE.getMessage());
    }

    @Override
    @Transactional
    public R visitApp(Integer id) {
        LoginAppUser user = AppUserUtil.getLoginAppUser();
        if (user == null) {
            return R.failed(ResultEnum.GET_CURRENT_USER_INFO_ERROR.getMessage());
        }

        UserApplication userApplication = UserApplication.builder()
                .userId(user.getId())
                .appId(id)
                .recentVisitTime(new Date())
                .build();
        this.userApplicationDao.updateUserApplication(userApplication);

        return R.ok(ResultEnum.UPDATE_OPERATE_SUCCESS.getMessage());
    }

    @Override
    public R findRecentVisit(Integer appCounts) {
        LoginAppUser user = AppUserUtil.getLoginAppUser();
        if (user == null) {
            return R.failed(ResultEnum.GET_CURRENT_USER_INFO_ERROR.getMessage());
        }

        List<UserApplication> userApplications = this.userApplicationDao.
                findRecentVisit(user.getId(), appCounts);
        return R.ok(userApplications);
    }

    @Override
    @Transactional
    public R clearRecentVisit() {
        LoginAppUser user = AppUserUtil.getLoginAppUser();
        if (user == null) {
            return R.failed(ResultEnum.GET_CURRENT_USER_INFO_ERROR.getMessage());
        }

        this.userApplicationDao.updateRecentVisitTimeByUserId(null, user.getId());

        return R.ok(ResultEnum.UPDATE_OPERATE_SUCCESS.getMessage());
    }

    @Override
    @Transactional
    public R deleteByUserId(Integer userId) {
        this.userApplicationDao.deleteByUserId(userId);
        return R.ok(ResultEnum.DELETE_OPERATE_SUCCESS);
    }

}
