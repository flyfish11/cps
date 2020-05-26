package com.cloud.service.feign;

import com.cloud.common.utils.R;
import com.cloud.model.appmanagercenter.Application;
import com.cloud.model.appmanagercenter.ApplicationServices;
import com.cloud.model.appmanagercenter.bo.ApplicationUpdateBO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

/**
 * @Classname ApplicationFeignClient
 * @Description App管理中心FeignClient
 * @Author yulj
 * @Date: 2019/07/12 10:39
 */
@FeignClient(value = "app-manager-center", name = "app-manager-center")
public interface ApplicationFeignClient {

    @GetMapping("/application/load")
    R load(@RequestParam Integer id);

    @PostMapping("/application/update")
    R update(@RequestBody ApplicationUpdateBO applicationUpdateBO);

    /**
     * 新增应用服务
     *
     * @param applicationServices
     * @return
     */
    @PostMapping(value = "/applicationServices/add")
    R add(@RequestBody ApplicationServices applicationServices);

}
