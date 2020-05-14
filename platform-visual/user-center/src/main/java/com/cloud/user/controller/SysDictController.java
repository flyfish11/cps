package com.cloud.user.controller;

import com.cloud.common.constants.ServiceIdsConstants;
import com.cloud.common.utils.ModelUtil;
import com.cloud.common.utils.ResultUtil;
import com.cloud.model.common.Page;
import com.cloud.model.common.Result;
import com.cloud.model.log.LogAnnotation;
import com.cloud.model.user.SysDict;
import com.cloud.user.service.SysDictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 字典表(SysDict)表控制层
 *
 * @author makejava
 * @since 2019-09-18 16:31:39
 */
@Api(tags = "字典表控制器")
@RestController
@RequestMapping("/sysDict")
public class SysDictController {
    /**
     * 服务对象
     */
    @Resource
    private SysDictService sysDictService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @ApiOperation(value = "根据id查询单条数据", notes = "根据id查询单条数据", response = Result.class)
    @ApiImplicitParam(name = "id", value = "主键id", required = true, dataType = "String")
    @GetMapping("/getById")
    public Result getById(@RequestParam String id) {
        SysDict sysDict = this.sysDictService.queryById(id);
        return ResultUtil.success(sysDict);
    }

    /**
     * 添加数据
     *
     * @param sysDict 插入的数据实体
     * @return
     */
    @ApiOperation(value = "新增数据", notes = "新增数据", response = Result.class)
    @PostMapping("/add")
    @LogAnnotation(serviceId = ServiceIdsConstants.USER_CENTER, title = "新增字典类型记录")
    public Result add(@RequestBody SysDict sysDict) {
        this.sysDictService.insert(sysDict);
        return ResultUtil.success();
    }

    /**
     * 查询所有
     *
     * @return 返回数据集合
     */
    @ApiOperation(value = "分页查询所有数据", notes = "分页查询所有数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页码", required = true, dataType = "int", paramType = "query"), @ApiImplicitParam(name = "limit", value = "每页显示条数", required = true, dataType = "int", paramType = "query")
    })
    @GetMapping("/queryAll")
    public Result queryAll(@ApiIgnore @RequestParam Map<String, Object> params) {
        Page<SysDict> pages = this.sysDictService.queryAllByLimit(params);
        return ResultUtil.success(pages.getTotal(), pages.getData(), ModelUtil.getFieldAndComment(SysDict.class));
    }

    /**
     * 根据id删除数据
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id 删除数据", notes = "根据id 删除数据", response = Result.class)
    @ApiImplicitParam(name = "id", value = "主键id", required = true, dataType = "String")
    @GetMapping("/remove")
    @LogAnnotation(serviceId = ServiceIdsConstants.USER_CENTER, title = "删除字典类型记录")
    public Result remove(@RequestParam String id) {
        this.sysDictService.deleteById(id);

        return ResultUtil.success();
    }

    /**
     * 修改数据
     *
     * @param sysDict 修改实体类
     * @return
     */
    @ApiOperation(value = "修改数据", notes = "修改数据", response = Result.class)
    @ApiImplicitParam(name = "sysDict", value = " SysDict实体类", required = true, dataType = "SysDict")
    @PostMapping("/modify")
    @LogAnnotation(serviceId = ServiceIdsConstants.USER_CENTER, title = "修改字典类型记录")
    public Result modify(@RequestBody SysDict sysDict) {
        this.sysDictService.update(sysDict);
        return ResultUtil.success();
    }

}