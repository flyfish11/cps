package com.cloud.service.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cloud.common.constants.ServiceIdsConstants;
import com.cloud.common.enums.ResultEnum;
import com.cloud.common.utils.AppUserUtil;
import com.cloud.common.utils.ResultUtil;
import com.cloud.common.utils.ResultVOUtil;
import com.cloud.common.utils.ValidateUtil;
import com.cloud.common.vo.ResultVO;
import com.cloud.model.common.Page;
import com.cloud.model.common.Result;
import com.cloud.model.log.LogAnnotation;
import com.cloud.model.platformappmanager.ServiceInfo;
import com.cloud.model.platformuser.LoginAppUser;
import com.cloud.service.config.JenkinsConfig;
import com.cloud.service.dto.ServiceInfoDto;
import com.cloud.service.exception.ServiceCenterException;
import com.cloud.service.service.ServiceInfoService;
import com.cloud.service.util.HttpClietUtil;
import com.google.common.collect.Maps;
import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.Build;
import com.offbytwo.jenkins.model.BuildWithDetails;
import com.offbytwo.jenkins.model.Job;
import com.offbytwo.jenkins.model.JobWithDetails;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.http.conn.HttpHostConnectException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.io.IOException;
import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 服务信息控制器
 *
 * @author yulj
 * @create: 2019/05/09 13:39
 */
@Api(tags = "服务管理")
@RestController
@Slf4j
@RequestMapping("/service")
public class ServiceInfoController {

    @Value("${eureka.client.serviceUrl.defaultZone}")
    private String registryCenter;

    @Value("${web.createFileApi}")
    private String createFileApi;

    private static AtomicLong counter = new AtomicLong(0L);

    @Autowired
    private ServiceInfoService serviceInfoService;

    @Autowired
    private JenkinsConfig jenkinsConfig;

    /**
     * jenkins执行第几次构建
     */
    private int buildNumber;

    /**
     * 查询服务列表数据
     *
     * @param params
     * @return
     */
    @ApiOperation(value = "分页查询服务列表", notes = "根据查询条件分页查询服务列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页码", required = true, dataType = "int", paramType = "query"), @ApiImplicitParam(name = "limit", value = "条数", required = true, dataType = "int", paramType = "query"), @ApiImplicitParam(name = "name", value = "服务名称", required = false, dataType = "String", paramType = "query")
    })
    @GetMapping("/list")
    public Result list(@ApiIgnore @RequestParam Map<String, Object> params) {
        Page<ServiceInfoDto> serviceInfoPage = this.serviceInfoService.list(params);
        return ResultUtil.success(serviceInfoPage.getTotal(), serviceInfoPage.getData());
    }

    /**
     * 服务创建
     *
     * @param serviceInfo
     * @param bindingResult
     * @return
     */
    @LogAnnotation(serviceId = ServiceIdsConstants.SERVICE_CENTER, title = "创建服务记录")
    @ApiOperation(value = "新增服务信息", notes = "根据ServiceInfo对象信息新增服务信息")
    @PostMapping("/add")
    public Result add(@RequestBody ServiceInfo serviceInfo, @ApiIgnore BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("【服务创建】参数不正确, serviceInfo={}", serviceInfo);
            throw new ServiceCenterException(ResultEnum.PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        serviceInfo.setCreateBy(loginAppUser.getAccount());
        serviceInfo.setCreateTime(new Date());
        serviceInfo.setIsDelete(0);
        return this.serviceInfoService.add(serviceInfo);
    }

    /**
     * 服务修改
     *
     * @param serviceInfo
     * @param bindingResult
     * @return
     */
    @ApiOperation(value = "修改服务信息", notes = "根据ServiceInfo对象信息修改服务信息")
    @PostMapping("/update")
    @LogAnnotation(serviceId = ServiceIdsConstants.SERVICE_CENTER, title = "修改服务记录")
    public Result update(@RequestBody ServiceInfo serviceInfo, @ApiIgnore BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("【服务创建】参数不正确, serviceInfo={}", serviceInfo);
            throw new ServiceCenterException(ResultEnum.PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }
        return serviceInfoService.update(serviceInfo);
    }

    /**
     * 服务修改
     *
     * @param serviceInfo
     * @param bindingResult
     * @return
     */
    @PostMapping("/edit")
    @LogAnnotation(serviceId = ServiceIdsConstants.SERVICE_CENTER, title = "修改服务记录")
    public ResultVO<Map<String, String>> edit(ServiceInfo serviceInfo, @ApiIgnore BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("【服务修改】参数不正确, serviceInfo={}", serviceInfo);
            throw new ServiceCenterException(ResultEnum.PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        serviceInfo.setUpdateBy(loginAppUser.getAccount());
        serviceInfo.setUpdateTimre(new Date());
        this.serviceInfoService.updateById(serviceInfo);
        Map<String, Object> map = Maps.newHashMap();
        map.put("serviceId", serviceInfo.getId());
        return ResultVOUtil.success(map);
    }

    /**
     * 查询单条服务信息
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "查询单条服务信息", notes = "根据服务id查询服务信息")
    @ApiImplicitParam(name = "id", value = "服务id", required = true, dataType = "int", paramType = "query")
    @GetMapping("/load")
    public Result load(@RequestParam Integer id) {
        ServiceInfo serviceInfo = this.serviceInfoService.findById(id);
        if (serviceInfo == null) {
            return ResultUtil.error(1, "未查询到服务信息！");
        }
        String serviceStatus = this.getServiceStatus(serviceInfo.getName());
        if (serviceStatus == null) {
            serviceStatus = "服务还未部署";
        }

        serviceInfo.setServiceStatus(serviceStatus);
        return ResultUtil.success(serviceInfo);
    }

    /**
     * 删除服务信息
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "删除服务信息", notes = "根据服务id删除服务信息")
    @ApiImplicitParam(name = "id", value = "服务id", required = true, dataType = "int", paramType = "query")
    @LogAnnotation(serviceId = ServiceIdsConstants.SERVICE_CENTER, title = "删除服务记录")
    @GetMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        if (ValidateUtil.isEmpty(id)) {
            return ResultUtil.error(ResultEnum.PARAM_ERROR.getMessage());
        }
        try {
            return this.serviceInfoService.delete(id);
        } catch (HttpHostConnectException e) {
            log.error("HttpClient 拒绝连接 :{}", e.getMessage());
            return ResultUtil.error("HttpClient 拒绝连接");
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResultUtil.error(e.getMessage());
        }
    }

    /**
     * 服务构建
     *
     * @param id
     * @return
     */
    @LogAnnotation(serviceId = ServiceIdsConstants.SERVICE_CENTER, title = "构建服务")
    @ApiOperation(value = "构建服务", notes = "根据服务id构建服务")
    @ApiImplicitParam(name = "id", value = "服务构建id", required = true, dataType = "String", paramType = "path")
    @GetMapping(value = "/buildJenkins")
    public Result buildJenkins(@RequestParam Integer id) throws Exception {
        ServiceInfo serviceInfo = serviceInfoService.findById(id);
        String jobName = serviceInfo.getShortName();
        String result = "";
        JenkinsServer jenkins = new JenkinsServer(new URI(jenkinsConfig.getJenUrl()), jenkinsConfig.getUsername(), jenkinsConfig.getPasswordorToken());
        if (jenkins.isRunning()) {
            JobWithDetails job = jenkins.getJob(jobName);
            if (job != null) {
                job.build();
                buildNumber = job.getNextBuildNumber();
                result = "任务正在构建中，请稍等";
            } else {
                throw new ServiceCenterException(ResultEnum.JENKINS_NOJOB_ERROR);
            }
        } else {
            throw new ServiceCenterException(ResultEnum.JENKINS_RUN_ERROR);
        }
        return ResultUtil.success(result);
    }

    /**
     * 查询实时构建日志
     *
     * @return
     */
    @ApiOperation(value = "查看服务构建信息", notes = "根据服务id查看服务构建信息")
    @ApiImplicitParam(name = "id", value = "日志id", required = true, dataType = "String", paramType = "path")
    @GetMapping(value = "/buildLod")
    public Result showLog(@RequestParam Integer id) {
        ServiceInfo serviceInfo = serviceInfoService.findById(id);
        boolean finished = isFinished(buildNumber, serviceInfo);
        if (finished == true) {
            return ResultUtil.success(serviceInfo.getJenkinslog());
        }
        return ResultUtil.error(1, "任务还在构建中");
    }

    /**
     * 判断job是否执行完
     *
     * @param number
     * @param serviceInfo
     * @return
     */
    public boolean isFinished(int number, ServiceInfo serviceInfo) {
        String jobName = serviceInfo.getShortName();
        boolean isBuilding = false;
        if (number <= 0) {
            throw new IllegalArgumentException("jodId must greater than 0!");
        }
        try {
            JenkinsServer jenkins = new JenkinsServer(new URI(jenkinsConfig.getJenUrl()), jenkinsConfig.getUsername(), jenkinsConfig.getPasswordorToken());
            ;
            Map<String, Job> jobs = jenkins.getJobs();
            JobWithDetails job = jobs.get(jobName).details();
            Build buildByNumber = job.getBuildByNumber(number);
            if (null != buildByNumber) {
                BuildWithDetails details = buildByNumber.details();
                if (null != details) {
                    isBuilding = details.isBuilding();
                    serviceInfo.setJenkinslog(details.getConsoleOutputText().getBytes());
                    serviceInfoService.update(serviceInfo);
                } else {
                    isBuilding = true;
                }
            } else {
                isBuilding = true;
            }
            return !isBuilding;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
        return false;
    }

    /**
     * 创建服务构建文件
     *
     * @param serviceInfo
     */
    @PostMapping(value = "/createFile")
    public void createFile(@RequestBody ServiceInfo serviceInfo) {
        serviceInfo = serviceInfoService.findById(serviceInfo.getId());
        Map<String, String> map = new HashMap<>();
        map.put("service_name", serviceInfo.getName());
        map.put("port", serviceInfo.getServicePort());
        map.put("addr", serviceInfo.getJarAddr());
        map.put("type", serviceInfo.getType());
        String send = null;
        try {
            send = HttpClietUtil.send(createFileApi, map, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getServiceStatus(String serviceName) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(registryCenter + "apps/" + serviceName).addHeader("Content-Type", "application/json").addHeader("Accept", "application/json").build();

        String status = null;
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                okhttp3.ResponseBody body = response.body();

                JSONObject json = JSONObject.parseObject(response.body().string());
                JSONObject application = json.getJSONObject("application");
                JSONArray instance = application.getJSONArray("instance");
                if (instance.size() > 0) {
                    for (int i = 0; i < json.size(); i++) {
                        JSONObject job = instance.getJSONObject(i);
                        status = job.getString("status");
                        // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                        // 得到 每个对象中的属性值
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return status;
    }

}