package com.cloud.user.controller;

import com.cloud.common.constants.ServiceIdsConstants;
import com.cloud.common.utils.ModelUtil;
import com.cloud.common.utils.ResultUtil;
import com.cloud.model.common.Page;
import com.cloud.model.common.Result;
import com.cloud.model.log.LogAnnotation;
import com.cloud.model.user.SysDictItem;
import com.cloud.user.service.SysDictItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 字典项(SysDictItem)表控制层
 *
 * @author makejava
 * @since 2019-09-18 16:31:39
 */
@Api(tags = "字典项控制器")
@RestController
@RequestMapping("/sysDictItem")
public class SysDictItemController {
    /**
     * 服务对象
     */
    @Resource
    private SysDictItemService sysDictItemService;

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
        SysDictItem sysDictItem = this.sysDictItemService.queryById(id);
        return ResultUtil.success(sysDictItem);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param dictId 主键
     * @return 单条数据
     */
    @ApiOperation(value = "根据id查询单条数据", notes = "根据id查询单条数据", response = Result.class)
    @ApiImplicitParam(name = "id", value = "主键id", required = true, dataType = "String")
    @GetMapping("/queryByDictId")
    public Result queryByDictId(@RequestParam String dictId) {
        List<SysDictItem> sysDictItems = this.sysDictItemService.queryByDictId(dictId);
        return ResultUtil.success(sysDictItems);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param dictType 主键
     * @return
     */
    @ApiOperation(value = "根据字典类型查询字典数据", notes = "根据字典类型查询字典数据", response = Result.class)
    @ApiImplicitParam(name = "dictType", value = "字典类别", required = true, dataType = "String")
    @GetMapping("/queryByDictType")
    public List<SysDictItem> queryByDictType(@RequestParam String dictType) {
        return this.sysDictItemService.queryByDictType(dictType);
    }

    /**
     * 添加数据
     *
     * @param sysDictItem 插入的数据实体
     * @return
     */
    @ApiOperation(value = "新增数据", notes = "新增数据", response = Result.class)
    @ApiImplicitParam(name = "sysDictItem", value = " SysDictItem实体类", required = true, dataType = "SysDictItem")
    @PostMapping("/add")
    @LogAnnotation(serviceId = ServiceIdsConstants.USER_CENTER, title = "新增字典项记录")
    public Result add(@RequestBody SysDictItem sysDictItem) {
        this.sysDictItemService.insert(sysDictItem);
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
        Page<SysDictItem> pages = this.sysDictItemService.queryAllByLimit(params);
        return ResultUtil.success(pages.getTotal(), pages.getData(), ModelUtil.getFieldAndComment(SysDictItem.class));
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
    @LogAnnotation(serviceId = ServiceIdsConstants.USER_CENTER, title = "删除字典项记录")
    public Result remove(@RequestParam String id) {
        this.sysDictItemService.deleteById(id);

        return ResultUtil.success();
    }

    /**
     * 修改数据
     *
     * @param sysDictItem 修改实体类
     * @return
     */
    @ApiOperation(value = "修改数据", notes = "修改数据", response = Result.class)
    @ApiImplicitParam(name = "sysDictItem", value = " SysDictItem实体类", required = true, dataType = "SysDictItem")
    @PostMapping("/modify")
    @LogAnnotation(serviceId = ServiceIdsConstants.USER_CENTER, title = "修改字典项记录")
    public Result modify(@RequestBody SysDictItem sysDictItem) {
        this.sysDictItemService.update(sysDictItem);
        return ResultUtil.success();
    }

    @ApiIgnore
    @GetMapping("/findDictSelect")
    public Map<String, String> findDictSelectByType(@RequestParam String dictType) {
        return this.sysDictItemService.findDictSelectByType(dictType);
    }

}