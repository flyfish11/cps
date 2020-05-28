package com.cloud.service.feign;

import com.cloud.model.platformuser.SysDictItem;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 用户中心FeignClient
 *
 * @author yulj
 * @create: 2019/05/22 00:06
 */
@FeignClient(value = "platform-user", name = "platform-user")
public interface UserCenterFeignClient {

    @RequestMapping(value = "/dept/queryDeptNames", method = RequestMethod.GET)
    String queryDeptNames(@RequestParam Integer... deptIds);

    @RequestMapping(value = "/sysDictItem/queryByDictType", method = RequestMethod.GET)
    List<SysDictItem> queryByDictType(@RequestParam String dictType);

}
