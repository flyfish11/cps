package com.cloud.gateway.controller;

import com.cloud.common.utils.R;
import com.cloud.common.utils.ResultUtil;
import com.cloud.gateway.service.GatewayApiDefineService;
import com.cloud.model.common.Page;
import com.cloud.model.common.Result;
import com.cloud.model.gateway.GatewayApiDefine;
import com.cloud.model.gateway.bo.GatewayApiDefineAddBO;
import com.cloud.model.gateway.bo.GatewayApiDefineUpdateBO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.Map;

/**
 * @Classname GatewayApiDefineController
 * @Description 路由规则控制器
 * @Author yulj
 * @Date: 2019/07/16 20:18
 */
@RestController
@RequestMapping("/route")
@Slf4j
@Api(tags = "路由管理")
public class GatewayApiDefineController {

    @Autowired
    private GatewayApiDefineService gatewayApiDefineService;

    /**
     * 分页查询路由规则记录
     *
     * @param params
     * @return
     */
    @GetMapping("/list")
    @ApiOperation(value = "分页查询路由规则记录", notes = "根据条件分页查询路由规则记录", response = Result.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页码", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "limit", value = "条数", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "serviceId", value = "服务名称", required = false, dataType = "String", paramType = "query")
    })
    public Result pageList(@RequestParam Map<String, Object> params) {
        Page<GatewayApiDefine> gatewayApiDefinePage = this.gatewayApiDefineService.pageList(params);
        return ResultUtil.success(gatewayApiDefinePage.getTotal(), gatewayApiDefinePage.getData());
    }

    /**
     * 添加路由规则
     *
     * @param gatewayApiDefine 路由规则数据对象
     * @return
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增路由规则", notes = "根据GatewayApiDefine对象信息新增路由规则信息")
    public R add(@Valid @RequestBody GatewayApiDefineAddBO gatewayApiDefine, @ApiIgnore BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("【路由规则创建】参数不正确, gatewayApiDefine={}", gatewayApiDefine);
            return R.failed(bindingResult.getFieldError().getDefaultMessage());
        }
        return this.gatewayApiDefineService.add(gatewayApiDefine);
    }

    @GetMapping("/load/{id}")
    public R findById(@PathVariable String id) {
        return R.ok(this.gatewayApiDefineService.findById(id));
    }

    @GetMapping("/findByServiceId")
    @ApiOperation(value = "根据serviceId查询路由规则", notes = "根据serviceId查询路由规则")
    public R findByServiceId(@RequestParam String serviceId) {
        if (StringUtils.isBlank(serviceId)) {
            return R.failed("服务id不能为空！");
        }
        return this.gatewayApiDefineService.findByServiceId(serviceId);
    }

    /**
     * 修改路由规则
     *
     * @param gatewayApiDefineUpdateBO 路由规则数据对象
     * @return
     */
    @PostMapping("/update")
    @ApiOperation(value = "修改路由规则", notes = "根据GatewayApiDefineUpdateBO对象信息修改路由规则信息")
    public R update(@Valid @RequestBody GatewayApiDefineUpdateBO gatewayApiDefineUpdateBO, @ApiIgnore BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("【路由规则修改】参数不正确, gatewayApiDefineUpdateBO={}", gatewayApiDefineUpdateBO);
            return R.failed(bindingResult.getFieldError().getDefaultMessage());
        }
        return this.gatewayApiDefineService.update(gatewayApiDefineUpdateBO);
    }

    /**
     * 删除路由规则信息
     *
     * @param id
     * @return
     */
    @GetMapping("/delete")
    @ApiOperation(value = "删除路由规则", notes = "根据路由规则id删除路由规则信息")
    @ApiImplicitParam(name = "id", value = "路由规则id", required = true, dataType = "String", paramType = "query")
    public R deleteRole(@RequestParam String id) {
        if (StringUtils.isBlank(id)) {
            return R.failed("路由规则id不能为空！");
        }
        return this.gatewayApiDefineService.delete(id);
    }

}