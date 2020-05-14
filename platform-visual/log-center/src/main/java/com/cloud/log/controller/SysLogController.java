package com.cloud.log.controller;

import com.cloud.common.utils.R;
import com.cloud.common.utils.ResultUtil;
import com.cloud.log.service.SysLogService;
import com.cloud.model.common.Page;
import com.cloud.model.common.Result;
import com.cloud.model.log.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;

@RestController
@Api(tags = "日志管理")
@RequestMapping("/logs")
public class SysLogController {

    @Autowired
    private SysLogService sysLogService;

    @Autowired
    private DiscoveryClient discoveryClient;

    @ApiOperation(value = "新增日志", notes = "根据Log对象新增日志")
    @PostMapping("/add")
    public R save(@RequestBody SysLog sysLog) {
        return sysLogService.save(sysLog);
    }

    /**
     * 日志查询
     *
     * @param params
     * @return
     */
    @ApiOperation(value = "分页查询日志记录", notes = "根据查询条件分页查询日志记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页码", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "limit", value = "条数", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "serviceId", value = "服务名", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "createBy", value = "用户名", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "是否成功", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "title", value = "主题", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "beginTime", value = "开始时间", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "endTime", value = "截止时间", dataType = "String", paramType = "query")
    })
    @GetMapping
    public Result findLogs(@ApiIgnore @RequestParam Map<String, Object> params) {
        Page<SysLog> sysLogPage = this.sysLogService.list(params);
        return ResultUtil.success(sysLogPage.getTotal(), sysLogPage.getData());
    }

    /**
     * 获取全部服务名称
     *
     * @return
     */
    @GetMapping("/getAllServices")
    @ApiOperation(value = "获取全部服务名称", notes = "获取全部服务名称下拉选")
    public R getAllServices() {
        List<String> serviceNames = discoveryClient.getServices();
        return R.ok(serviceNames);
    }

}
