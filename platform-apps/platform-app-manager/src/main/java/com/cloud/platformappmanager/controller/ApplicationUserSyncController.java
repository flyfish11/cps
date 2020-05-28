package com.cloud.platformappmanager.controller;

import com.cloud.platformappmanager.service.ApplicationUserSyncService;
import com.cloud.common.constants.ServiceIdsConstants;
import com.cloud.common.utils.R;
import com.cloud.common.utils.ResultUtil;
import com.cloud.model.platformappmanager.bo.ApplicationUserSyncBO;
import com.cloud.model.platformappmanager.vo.ApplicationUserSyncVO;
import com.cloud.model.common.Page;
import com.cloud.model.common.Result;
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
 * @Classname ApplicationUserSyncController
 * @Description <h1>应用用户同步相关接口</h1>
 * @Author yulj
 * @Date: 2020/04/24 9:07 下午
 */
@RestController
@RequestMapping("/applicationUserSync")
@Api(tags = "应用用户同步相关接口")
public class ApplicationUserSyncController {

    @Autowired
    private ApplicationUserSyncService applicationUserSyncService;

    /**
     * 删除应用用户同步数据
     *
     * @param userId 用户id
     * @return
     */
    @PostMapping("/removeByUserId")
    @ApiIgnore
    public R removeByUserId(@RequestParam Integer userId) {
        return this.applicationUserSyncService.removeApplicationUserSync(userId);
    }

    @GetMapping("/list")
    @ApiOperation(value = "应用权限列表查询", notes = "应用权限列表查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页码", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "limit", value = "查询条数", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "showAll", value = "是否查询全部", required = true, dataType = "boolean", paramType = "query", defaultValue = "false")
    })
    public Result list(@ApiIgnore @RequestParam Map<String, Object> params) {
        Page<ApplicationUserSyncVO> applicationUserSyncVOPage = this.applicationUserSyncService.pageList(params);
        return ResultUtil.success(applicationUserSyncVOPage.getTotal(), applicationUserSyncVOPage.getData());
    }

    @PostMapping("/update")
    @ApiOperation(value = "应用权限更新", notes = "应用权限更新", response = R.class)
    @LogAnnotation(serviceId = ServiceIdsConstants.APP_MANAGER_CENTER, title = "更新应用权限")
    public R update(@RequestBody ApplicationUserSyncBO... appAuthoritys) {
        return this.applicationUserSyncService.updateApplicationUserSync(appAuthoritys);
    }

}
