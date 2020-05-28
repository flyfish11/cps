package com.cloud.platformappmanager.controller;

import com.cloud.platformappmanager.service.ApplicationService;
import com.cloud.common.constants.ServiceIdsConstants;
import com.cloud.common.utils.BindingResultUtil;
import com.cloud.common.utils.R;
import com.cloud.common.utils.ResultUtil;
import com.cloud.model.platformappmanager.bo.AppAdminBO;
import com.cloud.model.platformappmanager.bo.AppUsePersonBO;
import com.cloud.model.platformappmanager.bo.ApplicationAddBO;
import com.cloud.model.platformappmanager.bo.ApplicationUpdateBO;
import com.cloud.model.platformappmanager.vo.AppClassificationVO;
import com.cloud.model.platformappmanager.vo.ApplicationDetailVO;
import com.cloud.model.common.Page;
import com.cloud.model.common.Result;
import com.cloud.model.log.LogAnnotation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @Classname ApplicationController
 * @Description <h1>平台应用相关接口</h1>
 * @Author yulj
 * @Date: 2020/04/22 9:11 下午
 */
@RestController
@RequestMapping("/application")
@Api(tags = "平台应用相关接口")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @GetMapping("/list")
    @ApiOperation(value = "平台/应用分页查询", notes = "平台/应用分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页码", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "limit", value = "条数", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "应用名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "appType", value = "应用类型", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "runStatus", value = "运行状态", dataType = "int", paramType = "query")
    })
    public Result list(@ApiIgnore @RequestParam Map<String, Object> params) {
        Page<ApplicationDetailVO> applicationDetailVOPage = this.applicationService.list(params);
        return ResultUtil.success(applicationDetailVOPage.getTotal(), applicationDetailVOPage.getData());
    }

    /**
     * <h2>新增应用表记录</h2>
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增应用", notes = "根据ApplicationAddBO对象新增应用表数据", response = R.class)
    @LogAnnotation(serviceId = ServiceIdsConstants.APP_MANAGER_CENTER, title = "新增应用")
    public R add(@Valid @RequestBody ApplicationAddBO applicationAddBO, @ApiIgnore BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = BindingResultUtil.getErrors(bindingResult);
            return R.errorMap(errorMap);
        }
        return this.applicationService.add(applicationAddBO);
    }

    /**
     * <h2>修改应用表记录</h2>
     */
    @PostMapping("/update")
    @ApiOperation(value = "修改应用", notes = "根据ApplicationUpdateBO对象修改应用表数据", response = R.class)
    @LogAnnotation(serviceId = ServiceIdsConstants.APP_MANAGER_CENTER, title = "修改应用")
    public R update(@Valid @RequestBody ApplicationUpdateBO applicationUpdateBO, @ApiIgnore BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = BindingResultUtil.getErrors(bindingResult);
            return R.errorMap(errorMap);
        }
        return this.applicationService.update(applicationUpdateBO);
    }

    /**
     * <h2>删除应用表记录</h2>
     */
    @PostMapping("/delete")
    @ApiOperation(value = "删除应用", notes = "删除应用表记录", response = R.class)
    @ApiImplicitParam(name = "id", value = "应用id", required = true, dataType = "int", paramType = "query")
    @LogAnnotation(serviceId = ServiceIdsConstants.APP_MANAGER_CENTER, title = "删除应用")
    public R delete(@RequestParam Integer id) {
        return this.applicationService.delete(id);
    }

    @GetMapping("/load")
    @ApiOperation(value = "查询应用明细", notes = "根据url的id来获取应用详细信息", response = R.class)
    @ApiImplicitParam(name = "id", value = "应用id", required = true, dataType = "int", paramType = "query")
    public R load(@RequestParam Integer id) {
        return applicationService.load(id);
    }

    @GetMapping("/gradeAuthorizeList")
    @ApiOperation(value = "授权管理分页查询", notes = "授权管理分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页码", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "limit", value = "条数", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "应用名称", dataType = "String", paramType = "query")
    })
    public Result gradeAuthorizeList(@ApiIgnore @RequestParam Map<String, Object> params) {
        Page<ApplicationDetailVO> applicationDetailVOPage = this.applicationService.gradeAuthorizeList(params);
        return ResultUtil.success(applicationDetailVOPage.getTotal(), applicationDetailVOPage.getData());
    }

    @PostMapping("/setAppAdmin")
    @ApiOperation(value = "更新应用管理员", notes = "更新应用管理员", response = R.class)
    @LogAnnotation(serviceId = ServiceIdsConstants.APP_MANAGER_CENTER, title = "设置应用管理员")
    public R update(@Valid @RequestBody AppAdminBO appAdminBO, @ApiIgnore BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = BindingResultUtil.getErrors(bindingResult);
            return R.errorMap(errorMap);
        }
        return applicationService.updateAppAdmin(appAdminBO);
    }

    @PostMapping("/setAppUsePerson")
    @ApiOperation(value = "更新应用使用范围", notes = "更新应用使用范围", response = R.class)
    @LogAnnotation(serviceId = ServiceIdsConstants.APP_MANAGER_CENTER, title = "更新应用使用范围")
    public R setAppUsePerson(@RequestBody AppUsePersonBO appUsePersonBO, @ApiIgnore BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = BindingResultUtil.getErrors(bindingResult);
            return R.errorMap(errorMap);
        }
        return applicationService.setAppUsePerson(appUsePersonBO);
    }

    @GetMapping("/getAppClassification")
    @ApiOperation(value = "获取应用分类", notes = "获取应用分类")
    public List<AppClassificationVO> getAppClassification() {
        return this.applicationService.getAppClassification();
    }

    @GetMapping("/listByType/{appType}")
    @ApiOperation(value = "查询应用列表", notes = "根据应用类别查询应用列表", response = R.class)
    public R listByType(@PathVariable(value = "appType") String appType) {
        return this.applicationService.listByType(appType);
    }

    @GetMapping("/index/navigationBarApps")
    @ApiImplicitParam(name = "appClassification", value = "应用分类", required = true, dataType = "int", paramType = "query")
    @ApiOperation(value = "首页导航栏应用列表", notes = "首页导航栏应用列表")
    public R navigationBarApps(@RequestParam Integer appClassification) {
        return this.applicationService.navigationBarApps(appClassification);
    }

    @GetMapping("/index/list")
    @ApiOperation(value = "首页平台/应用列表", notes = "首页平台/应用列表")
    public R list() {
        return this.applicationService.indexList();
    }

    @GetMapping("/index/load")
    @ApiOperation(value = "首页应用详情", notes = "首页应用详情")
    @ApiImplicitParam(name = "appId", value = "应用id", required = true, dataType = "int", paramType = "query")
    public R indexLoad(@RequestParam Integer appId) {
        return this.applicationService.indexLoad(appId);
    }

    @GetMapping("/index/statistics")
    @ApiOperation(value = "首页平台/应用统计", notes = "首页平台/应用统计")
    public R statistics() {
        return this.applicationService.statistics();
    }

}
