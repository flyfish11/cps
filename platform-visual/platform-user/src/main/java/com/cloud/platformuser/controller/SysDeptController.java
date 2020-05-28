package com.cloud.platformuser.controller;

import com.cloud.common.constants.CommonConstants;
import com.cloud.common.constants.ServiceIdsConstants;
import com.cloud.common.utils.BindingResultUtil;
import com.cloud.common.utils.R;
import com.cloud.common.utils.ResultUtil;
import com.cloud.model.common.Result;
import com.cloud.model.log.LogAnnotation;
import com.cloud.model.platformuser.SysDept;
import com.cloud.model.platformuser.bo.SysDeptAddBO;
import com.cloud.model.platformuser.bo.SysDeptUpdateBO;
import com.cloud.platformuser.service.DeptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 部门控制器
 *
 * @author yulj
 * @create: 2019/05/06 14:39
 */

@RestController
@RequestMapping("/dept")
@Api(tags = "部门管理")
@Slf4j
public class SysDeptController {

    @Autowired
    private DeptService deptService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 获取部门数据树形表格
     */
    @ApiOperation(value = "查询部门树形grid", notes = "查询部门树形grid")
    @GetMapping(value = "/treeTable")
    public Result treeTable(@RequestParam Map<String, Object> params) {
        List<SysDept> depts = deptService.treeTable(params);
        return ResultUtil.success(depts);
    }

    /**
     * 部门详情
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "部门详情", notes = "部门详情")
    @ApiImplicitParam(name = "id", value = "部门id", required = true, dataType = "int", paramType = "query")
    @GetMapping(value = "/findById")
    public SysDept findById(@RequestParam Integer id) {
        return this.deptService.load(id);
    }

    /**
     * 根据部门ids查询部门名称
     *
     * @param deptIds
     * @return
     */
    @ApiOperation(value = "部门id数组查询部门名称", notes = "根据部门id数组查询部门名称")
    @ApiImplicitParam(name = "deptIds", value = "部门id数组", required = true, dataType = "int[]", paramType = "query", example = "1,2,3")
    @GetMapping(value = "/queryDeptNames")
    public String queryDeptNames(@RequestParam Integer... deptIds) {
        if (!redisTemplate.hasKey(CommonConstants.CACHE_DEPT_KEY)) {
            deptService.cacheAllDeptData();
        }
        return this.deptService.getDeptNames(deptIds);
    }

    /**
     * 获取部门树(ElementUI)
     *
     * @return
     */
    @GetMapping(value = "/elementTree")
    @ApiImplicitParam(name = "deptName", value = "部门名称", dataType = "String", paramType = "query")
    @ApiOperation(value = "获取部门树(ElementUI)", notes = "获取部门树(ElementUI)")
    public R elementTree(@RequestParam String deptName) {
        return R.ok(this.deptService.elementTree(deptName));
    }

    /**
     * 返回部门人员树形
     *
     * @return 树形菜单
     */
    @GetMapping(value = "/elementUserTree")
    @ApiOperation(value = "获取部门及人员树", notes = "获取部门及人员树")
    public R getUserTree() {
        return R.ok(this.deptService.selectUserTree());
    }

    /**
     * 返回部门人员树形(在应用可见范围部门内)
     *
     * @return 树形菜单
     */
    @GetMapping(value = "/elementUserTreeInRange/{applicationId}")
    @ApiOperation(value = "获取部门及人员树(在应用可见范围部门内)", notes = "获取部门及人员树(在应用可见范围部门内)")
    @ApiImplicitParam(name = "applicationId", value = "应用id", required = true, dataType = "String", paramType = "path")
    public R getUserTreeInRange(@PathVariable String applicationId) {
        return this.deptService.selectUserTreeInRange(applicationId);
    }

    /**
     * 添加部门数据
     *
     * @param sysDeptAddBO 部门新增业务对象
     * @return
     */
    @ApiOperation(value = "新增部门", notes = "新增部门", response = R.class)
    @LogAnnotation(serviceId = ServiceIdsConstants.USER_CENTER, title = "新增部门记录")
    @PostMapping("/add")
    public R add(@Valid @RequestBody SysDeptAddBO sysDeptAddBO, @ApiIgnore BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = BindingResultUtil.getErrors(bindingResult);
            return R.errorMap(errorMap);
        }
        return this.deptService.insert(sysDeptAddBO);
    }

    /**
     * 修改部门数据
     *
     * @param sysDeptUpdateBO 部门新增业务对象
     * @return
     */
    @PostMapping("/modify")
    @ApiOperation(value = "修改部门", notes = "修改部门", response = R.class)
    @LogAnnotation(serviceId = ServiceIdsConstants.USER_CENTER, title = "修改部门记录")
    public R modify(@Valid @RequestBody SysDeptUpdateBO sysDeptUpdateBO, @ApiIgnore BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = BindingResultUtil.getErrors(bindingResult);
            return R.errorMap(errorMap);
        }
        return this.deptService.update(sysDeptUpdateBO);
    }

    /**
     * 根据id删除数据
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "删除部门", notes = "删除部门", response = Result.class)
    @ApiImplicitParam(name = "id", value = "主键id", required = true, dataType = "Integer")
    @LogAnnotation(serviceId = ServiceIdsConstants.USER_CENTER, title = "删除部门记录")
    @PostMapping("/remove")
    public R remove(@RequestParam Integer id) {
        return this.deptService.deleteById(id);
    }

}
