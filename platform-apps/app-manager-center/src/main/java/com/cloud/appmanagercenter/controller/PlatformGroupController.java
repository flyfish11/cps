package com.cloud.appmanagercenter.controller;

import com.cloud.appmanagercenter.service.PlatformGroupService;
import com.cloud.common.constants.ServiceIdsConstants;
import com.cloud.common.utils.BindingResultUtil;
import com.cloud.common.utils.R;
import com.cloud.common.utils.ResultUtil;
import com.cloud.model.appmanagercenter.PlatformGroup;
import com.cloud.model.appmanagercenter.bo.PlatformGroupAddBO;
import com.cloud.model.appmanagercenter.bo.PlatformGroupUpdateBO;
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
import java.util.Map;

/**
 * @Classname PlatformGroupController
 * @Description <h1>应用分组表控制器</h1>
 * @Author yulj
 * @Date: 2020/04/22 3:33 下午
 */
@RestController
@RequestMapping("/platformGroup")
@Api(tags = "应用分组相关接口")
public class PlatformGroupController {

    @Autowired
    private PlatformGroupService platformGroupService;

    @GetMapping("/list")
    @ApiOperation(value = "分页查询应用分组记录", notes = "分页查询应用分组记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页码", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "limit", value = "条数", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "groupName", value = "应用分组名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "enable", value = "状态", dataType = "int", paramType = "query")
    })
    public Result list(@ApiIgnore @RequestParam Map<String, Object> params) {
        Page<PlatformGroup> platformGroupPage = this.platformGroupService.list(params);
        return ResultUtil.success(platformGroupPage.getTotal(), platformGroupPage.getData());
    }

    /**
     * <h2>新增应用分组表记录</h2>
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增应用分组", notes = "根据PlatformGroupAddBO对象新增应用分组表数据", response = R.class)
    @LogAnnotation(serviceId = ServiceIdsConstants.APP_MANAGER_CENTER, title = "新增应用分组")
    public R add(@Valid @RequestBody PlatformGroupAddBO platformGroupAddBO, @ApiIgnore BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = BindingResultUtil.getErrors(bindingResult);
            return R.errorMap(errorMap);
        }
        return this.platformGroupService.add(platformGroupAddBO);
    }

    /**
     * <h2>修改应用分组表记录</h2>
     */
    @PostMapping("/update")
    @ApiOperation(value = "修改应用分组", notes = "根据IndexUpdateBO对象修改应用分组表数据", response = R.class)
    @LogAnnotation(serviceId = ServiceIdsConstants.APP_MANAGER_CENTER, title = "修改应用分组")
    public R update(@Valid @RequestBody PlatformGroupUpdateBO platformGroupUpdateBO, @ApiIgnore BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = BindingResultUtil.getErrors(bindingResult);
            return R.errorMap(errorMap);
        }
        return this.platformGroupService.update(platformGroupUpdateBO);
    }

    /**
     * <h2>删除应用分组表记录</h2>
     */
    @PostMapping("/delete")
    @ApiOperation(value = "删除应用分组", notes = "删除应用分组表记录", response = R.class)
    @ApiImplicitParam(name = "id", value = "应用分组id", required = true, dataType = "int", paramType = "query")
    @LogAnnotation(serviceId = ServiceIdsConstants.APP_MANAGER_CENTER, title = "删除应用分组")
    public R delete(@RequestParam Integer id) {
        return this.platformGroupService.delete(id);
    }

    @GetMapping("/queryPlatformSelect")
    @ApiOperation(value = "查询应用分组下拉选", notes = "查询应用分组下拉选")
    public R queryPlatformSelect() {
        return this.platformGroupService.queryPlatformSelect();
    }

    /**
     * <h2>查询应用分组详情</h2>
     */
    @GetMapping("/load")
    @ApiOperation(value = "应用分组详情", notes = "应用分组详情", response = R.class)
    @ApiImplicitParam(name = "id", value = "应用分组id", required = true, dataType = "int", paramType = "query")
    public R load(@RequestParam Integer id) {
        return this.platformGroupService.load(id);
    }

}
