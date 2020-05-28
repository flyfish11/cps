package com.cloud.platformappmanager.controller;

import com.cloud.platformappmanager.service.ApplicationServicesService;
import com.cloud.common.constants.ServiceIdsConstants;
import com.cloud.common.utils.R;
import com.cloud.common.utils.ResultUtil;
import com.cloud.model.platformappmanager.ApplicationServices;
import com.cloud.model.common.Page;
import com.cloud.model.common.Result;
import com.cloud.model.log.LogAnnotation;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

/**
 * @Classname ApplicationServicesController
 * @Description 应用服务关联控制器
 * @Author yulj
 * @Date: 2019/07/04 09:08
 */
@RestController
@RequestMapping("/applicationServices")
@Api(tags = "应用服务关联相关接口")
public class ApplicationServicesController {

    @Autowired
    private ApplicationServicesService applicationServicesService;

    @GetMapping("/load/{id}")
    @ApiOperation(value = "查询应用服务关联明细", notes = "根据url的id来获取应用服务关联详细信息", response = R.class)
    @ApiImplicitParam(name = "id", value = "应用id", required = true, dataType = "int", paramType = "path")
    public R load(@PathVariable(value = "id") Integer id) {
        return R.ok(this.applicationServicesService.findById(id));
    }

    @GetMapping("/list")
    @ApiOperation(value = "获取分页应用服务关联列表", notes = "获取分页应用服务关联列表", response = Result.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页码", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "limit", value = "条数", required = true, dataType = "int", paramType = "query")
    })
    public Result list(@RequestParam Map<String, Object> params) {
        Page<ApplicationServices> applicationServicesPage = this.applicationServicesService.listBySelective(params);
        return ResultUtil.success(applicationServicesPage.getTotal(), applicationServicesPage.getData());
    }

    @PostMapping("/add")
    @ApiOperation(value = "新增应用服务关联数据", notes = "根据ApplicationServices对象创建应用服务关联数据", response = R.class)
    @ApiImplicitParam(name = "applicationServices", value = "应用详细实体applicationServices", required = true, dataType = "ApplicationServices")
    @ApiOperationSupport(ignoreParameters = {"application.id", "application.createdBy", "application.createdTime"})
    @LogAnnotation(serviceId = ServiceIdsConstants.APP_MANAGER_CENTER, title = "新增应用服务关联")
    public R add(@RequestBody ApplicationServices applicationServices) {
        return this.applicationServicesService.save(applicationServices);
    }

    @PostMapping("/update")
    @ApiOperation(value = "更新应用服务关联数据", notes = "更新应用服务关联数据", response = R.class)
    @ApiImplicitParam(name = "id", value = "应用id", required = true, dataType = "string", paramType = "query")
    @ApiOperationSupport(ignoreParameters = {"application.id", "application.createdBy", "application.createdTime"})
    @LogAnnotation(serviceId = ServiceIdsConstants.APP_MANAGER_CENTER, title = "修改应用服务关联")
    public R update(@RequestBody ApplicationServices applicationServices, @ApiIgnore BindingResult result) {
        if (result.hasErrors()) {
            R.failed("请求参数有错误！");
        }
        return this.applicationServicesService.update(applicationServices);
    }

    @GetMapping("/delete/{id}")
    @ApiOperation(value = "删除应用服务关联数据", notes = "根据url的id来删除应用服务关联数据", response = R.class)
    @ApiImplicitParam(name = "id", value = "主键id", required = true, dataType = "int", paramType = "path")
    @LogAnnotation(serviceId = ServiceIdsConstants.APP_MANAGER_CENTER, title = "删除应用服务关联")
    public R delete(@PathVariable(value = "id") Integer id) {
        return this.applicationServicesService.delete(id);
    }

}
