package com.cloud.gateway.controller;

import com.cloud.common.constants.ServiceIdsConstants;
import com.cloud.common.utils.R;
import com.cloud.common.utils.ResultUtil;
import com.cloud.gateway.service.BlackIpService;
import com.cloud.model.common.Page;
import com.cloud.model.common.Result;
import com.cloud.model.gateway.BlackIp;
import com.cloud.model.gateway.bo.BlackIpAddBO;
import com.cloud.model.gateway.bo.BlackIpUpdateBO;
import com.cloud.model.log.LogAnnotation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.Map;

@RestController
@Api(tags = "黑名单相关接口")
@RequestMapping("/blackIP")
public class BlackIPController {

    @Autowired
    private BlackIpService blackIPService;

    /**
     * 查询黑名单
     *
     * @param params
     * @return
     */
    @ApiOperation(value = "分页查询黑白名单记录", notes = "根据查询条件分页查询黑白名单记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页码", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "limit", value = "条数", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "ip", value = "ip地址", dataType = "String", paramType = "query")
    })
    @GetMapping("/list")
    public Result list(@ApiIgnore @RequestParam Map<String, Object> params) {
        Page<BlackIp> blackIpPage = this.blackIPService.findList(params);
        return ResultUtil.success(blackIpPage.getTotal(), blackIpPage.getData());
    }

    /**
     * 查询黑名单<br>
     * 可内网匿名访问
     *
     * @return
     */
    @ApiOperation(value = "查询所有黑名单Ip地址", notes = "查询所有黑名单Ip地址")
    @GetMapping("/findAllBlackIPs")
    public R findAllBlackIPs() {
        return this.blackIPService.findAllBlackIPs();
    }

    /**
     * 添加黑名单ip
     *
     * @param blackIpAddBO
     */
    @LogAnnotation(serviceId = ServiceIdsConstants.GATEWAY_ZUUL, title = "新增黑名单")
    @ApiOperation(value = "新增黑白名单信息", notes = "根据BlackIP对象新增黑白名单信息", response = R.class)
    @PostMapping("/add")
    public R save(@Valid @RequestBody BlackIpAddBO blackIpAddBO, @ApiIgnore BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return R.failed(bindingResult.getFieldError().getDefaultMessage());
        }
        return blackIPService.save(blackIpAddBO);
    }

    /**
     * 修改黑名单ip
     *
     * @param blackIpUpdateBO
     */
    @LogAnnotation(serviceId = ServiceIdsConstants.GATEWAY_ZUUL, title = "修改黑名单")
    @ApiOperation(value = "修改黑白名单信息", notes = "根据BlackIpUpdateBO对象修改黑名单信息")
    @PostMapping("/update")
    public R update(@Valid @RequestBody BlackIpUpdateBO blackIpUpdateBO, @ApiIgnore BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return R.failed(bindingResult.getFieldError().getDefaultMessage());
        }
        return blackIPService.update(blackIpUpdateBO);
    }

    /**
     * 删除黑名单id
     *
     * @param id
     */
    @LogAnnotation(serviceId = ServiceIdsConstants.GATEWAY_ZUUL, title = "删除黑名单")
    @ApiOperation(value = "删除黑白名单信息", notes = "通过id删除黑白名单信息")
    @ApiImplicitParam(name = "id", value = "黑名单id", required = true, dataType = "String", paramType = "query")
    @GetMapping("/delete")
    public R delete(@RequestParam String id) {
        if (StringUtils.isBlank(id)) {
            return R.failed("黑名单id不能为空");
        }
        return blackIPService.delete(id);
    }

}