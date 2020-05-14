package com.cloud.appmanagercenter.controller;

import com.cloud.appmanagercenter.service.BannerConfigService;
import com.cloud.common.constants.ServiceIdsConstants;
import com.cloud.common.utils.BindingResultUtil;
import com.cloud.common.utils.R;
import com.cloud.common.utils.ResultUtil;
import com.cloud.model.appmanagercenter.BannerConfig;
import com.cloud.model.appmanagercenter.bo.BannerConfigAddBO;
import com.cloud.model.appmanagercenter.bo.BannerConfigUpdateBO;
import com.cloud.model.common.Result;
import com.cloud.model.log.LogAnnotation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @Classname BannerConfigController
 * @Description <h1>banner配置相关接口</h1>
 * @Author yulj
 * @Date: 2020/04/23 9:59 下午
 */
@RestController
@RequestMapping("/bannerConfig")
@Api(tags = "banner配置相关接口")
public class BannerConfigController {

    @Autowired
    private BannerConfigService bannerConfigService;

    @GetMapping("/list")
    @ApiOperation(value = "banner配置列表查询", notes = "banner配置列表查询")
    public Result list() {
        List<BannerConfig> list = bannerConfigService.list();
        return ResultUtil.success(list.size(), list);
    }

    /**
     * <h2>新增banner配置表记录</h2>
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增banner配置", notes = "根据BannerConfigAddBO对象新增banner配置表数据", response = R.class)
    @LogAnnotation(serviceId = ServiceIdsConstants.APP_MANAGER_CENTER, title = "新增banner配置")
    public R add(@Valid @RequestBody BannerConfigAddBO bannerConfigAddBO, @ApiIgnore BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = BindingResultUtil.getErrors(bindingResult);
            return R.errorMap(errorMap);
        }
        return this.bannerConfigService.add(bannerConfigAddBO);
    }

    /**
     * <h2>修改banner配置表记录</h2>
     */
    @PostMapping("/update")
    @ApiOperation(value = "修改banner配置", notes = "根据BannerConfigUpdateBO对象修改banner配置表数据", response = R.class)
    @LogAnnotation(serviceId = ServiceIdsConstants.APP_MANAGER_CENTER, title = "修改banner配置")
    public R update(@Valid @RequestBody BannerConfigUpdateBO bannerConfigUpdateBO, @ApiIgnore BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = BindingResultUtil.getErrors(bindingResult);
            return R.errorMap(errorMap);
        }
        return this.bannerConfigService.update(bannerConfigUpdateBO);
    }

    /**
     * <h2>删除banner配置表记录</h2>
     */
    @PostMapping("/delete")
    @ApiOperation(value = "删除banner配置", notes = "删除banner配置表记录", response = R.class)
    @ApiImplicitParam(name = "id", value = "banner配置id", required = true, dataType = "int", paramType = "query")
    @LogAnnotation(serviceId = ServiceIdsConstants.APP_MANAGER_CENTER, title = "删除banner配置")
    public R delete(@RequestParam Integer id) {
        return this.bannerConfigService.delete(id);
    }

    /**
     * <h2>banner上移</h2>
     */
    @GetMapping("/moveUp")
    @ApiOperation(value = "banner上移", notes = "banner上移", response = R.class)
    @ApiImplicitParam(name = "id", value = "banner配置id", required = true, dataType = "int", paramType = "query")
    public R moveUp(@RequestParam Integer id) {
        return this.bannerConfigService.moveUp(id);
    }

    /**
     * <h2>banner下移</h2>
     */
    @GetMapping("/moveDown")
    @ApiOperation(value = "banner下移", notes = "banner下移", response = R.class)
    @ApiImplicitParam(name = "id", value = "banner配置id", required = true, dataType = "int", paramType = "query")
    public R moveDown(@RequestParam Integer id) {
        return this.bannerConfigService.moveDown(id);
    }

}
