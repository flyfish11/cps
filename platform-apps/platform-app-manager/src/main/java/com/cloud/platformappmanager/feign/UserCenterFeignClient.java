package com.cloud.platformappmanager.feign;

import com.cloud.common.utils.R;
import com.cloud.model.platformuser.bo.SysUserUpdateBO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @Classname UserCenterFeignClient
 * @Description <h1>用户中心FeignClient</h1>
 * @Author yulj
 * @Date: 2020/04/23 4:58 下午
 */
@FeignClient("platform-user")
public interface UserCenterFeignClient {

    @RequestMapping(value = "/user/getUserIdInRoleIds", method = RequestMethod.GET)
    List<Integer> getUserIdInRoleIds(@RequestParam String... roleIds);

    @RequestMapping(value = "/user/load", method = RequestMethod.GET)
    R findUserById(@RequestParam Integer userId);

    @RequestMapping(value = "/sysDictItem/findDictSelect", method = RequestMethod.GET)
    Map<String, String> findDictSelectByType(@RequestParam String dictType);

    @RequestMapping(value = "/user/updateProductRegisterCount", method = RequestMethod.POST)
    R updateProductRegisterCount(@RequestBody SysUserUpdateBO sysUserUpdateBO);

    @RequestMapping(value = "/dept/queryDeptNames", method = RequestMethod.GET)
    String queryDeptNames(@RequestParam Integer... deptIds);

    @RequestMapping(value = "/user/getNameInUserIds", method = RequestMethod.GET)
    String getNameInUserIds(@RequestParam Integer... userIds);

}
