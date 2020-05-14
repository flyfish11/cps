package com.cloud.txmanager.api.controller;

import com.cloud.txmanager.api.service.ApiAdminService;
import com.cloud.txmanager.api.service.ApiModelService;
import com.cloud.txmanager.compensate.model.TxModel;
import com.cloud.txmanager.model.ModelInfo;
import com.cloud.txmanager.model.ModelName;
import com.cloud.txmanager.model.TxState;
import com.cloud.txmanager.model.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author LCN on 2017/7/1.
 * @author LCN
 */
@RestController
@AllArgsConstructor
@RequestMapping("/admin")
@Api(tags = "分布式事务管理相关接口")
public class AdminController {

    private final ApiAdminService apiAdminService;

    private final ApiModelService apiModelService;

    @RequestMapping(value = "/onlines", method = RequestMethod.GET)
    public List<ModelInfo> onlines() {
        return apiModelService.onlines();
    }

    @RequestMapping(value = "/txOnlines", method = RequestMethod.GET)
    @ApiOperation(value = "在线事务查询", notes = "在线事务查询", response = Result.class)
    public Result txOnlines() {
        List<ModelInfo> modelInfoList = apiModelService.onlines();
        return Result.ok(modelInfoList);
    }

    @RequestMapping(value = "/setting", method = RequestMethod.GET)
    public TxState setting() {
        return apiAdminService.getState();
    }

    @RequestMapping(value = "/txMonitor", method = RequestMethod.GET)
    @ApiOperation(value = "事务监控", notes = "事务监控", response = Result.class)
    public Result txMonitor() {
        List<Map<String, Object>> mapList = apiAdminService.getMapState();
        return Result.ok(mapList);
    }

    @RequestMapping(value = "/json", method = RequestMethod.GET)
    public String json() {
        return apiAdminService.loadNotifyJson();
    }

    @RequestMapping(value = "/modelList", method = RequestMethod.GET)
    public List<ModelName> modelList() {
        return apiAdminService.modelList();
    }

    @RequestMapping(value = "/modelTimes", method = RequestMethod.GET)
    public List<String> modelTimes(@RequestParam("model") String model) {
        return apiAdminService.modelTimes(model);
    }


    @RequestMapping(value = "/modelInfos", method = RequestMethod.GET)
    public List<TxModel> modelInfos(@RequestParam("path") String path) {
        return apiAdminService.modelInfos(path);
    }

    @SneakyThrows
    @RequestMapping(value = "/compensate", method = RequestMethod.GET)
    public boolean compensate(@RequestParam("path") String path) {
        return apiAdminService.compensate(path);
    }

    @SneakyThrows
    @RequestMapping(value = "/delCompensate", method = RequestMethod.GET)
    public boolean delCompensate(@RequestParam("path") String path) {
        return apiAdminService.delCompensate(path);
    }

    @SneakyThrows
    @RequestMapping(value = "/hasCompensate", method = RequestMethod.GET)
    public boolean hasCompensate() {
        return apiAdminService.hasCompensate();
    }

}
