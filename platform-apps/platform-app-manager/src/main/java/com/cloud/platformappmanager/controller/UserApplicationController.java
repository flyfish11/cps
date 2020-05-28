package com.cloud.platformappmanager.controller;

import com.cloud.platformappmanager.service.UserApplicationService;
import com.cloud.common.constants.ServiceIdsConstants;
import com.cloud.common.utils.R;
import com.cloud.model.log.LogAnnotation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

/**
 * @Classname UserApplicationController
 * @Description <h1>用户应用相关接口</h1>
 * @Author yulj
 * @Date: 2020/04/24 9:21 上午
 */
@RestController
@RequestMapping("/userApplication")
@Api(tags = "用户应用相关接口")
public class UserApplicationController {

    @Autowired
    private UserApplicationService userApplicationService;

    @GetMapping("/myApps")
    @ApiOperation(value = "我的应用", notes = "查询我的应用")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appName", value = "应用名称", dataType = "String", paramType = "query")
    })
    public R myApps(@ApiIgnore @RequestParam Map<String, Object> params) {
        return this.userApplicationService.myApps(params);
    }

    @GetMapping("/check")
    @ApiOperation(value = "应用校验", notes = "校验应用是否存在该账户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appId", value = "应用id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "account", value = "账户名", dataType = "String", paramType = "query")
    })
    public R check(@RequestParam Integer appId, @RequestParam String account) {
        return this.userApplicationService.checkAppUser(appId, account);
    }

    /**
     * <h2>访问应用</h2>
     */
    @PostMapping("/visitApp")
    @ApiOperation(value = "访问应用", notes = "用户访问应用", response = R.class)
    @ApiImplicitParam(name = "id", value = "应用id", required = true, dataType = "int", paramType = "query")
    @LogAnnotation(serviceId = ServiceIdsConstants.APP_MANAGER_CENTER, title = "用户访问应用")
    public R visitApp(@RequestParam Integer id) {
        return this.userApplicationService.visitApp(id);
    }

    @GetMapping("/recentVisit")
    @ApiOperation(value = "最近访问应用", notes = "查询最近访问应用")
    @ApiImplicitParam(name = "appCounts", value = "最近访问应用数，默认为15", dataType = "int", paramType = "query", defaultValue = "15")
    public R findRecentVisit(@RequestParam(value = "appCounts", defaultValue = "15") Integer appCounts) {
        return this.userApplicationService.findRecentVisit(appCounts);
    }

    @PostMapping("/clearRecentVisit")
    @ApiOperation(value = "清空最近访问应用", notes = "清空用户的最近访问应用记录")
    public R clearRecentVisit() {
        return this.userApplicationService.clearRecentVisit();
    }

    @ApiIgnore
    @PostMapping("/deleteByUserId")
    R deleteByUserId(@RequestParam Integer userId) {
        return this.userApplicationService.deleteByUserId(userId);
    }

}
