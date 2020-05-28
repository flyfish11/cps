package com.cloud.service.feign;

import com.cloud.common.utils.R;
import com.cloud.model.platformappmanager.ApplicationServices;
import com.cloud.model.platformappmanager.bo.ApplicationUpdateBO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @Classname ApplicationFeignClient
 * @Description App管理中心FeignClient
 * @Author yulj
 * @Date: 2019/07/12 10:39
 */
@FeignClient(value = "platform-app-manager", name = "platform-app-manager")
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
