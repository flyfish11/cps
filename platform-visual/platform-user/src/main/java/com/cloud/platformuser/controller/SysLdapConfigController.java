package com.cloud.platformuser.controller;

import com.cloud.common.constants.ServiceIdsConstants;
import com.cloud.common.utils.BindingResultUtil;
import com.cloud.common.utils.R;
import com.cloud.model.log.LogAnnotation;
import com.cloud.model.platformuser.bo.SysLdapConfigUpdateBO;
import com.cloud.platformuser.service.SysLdapConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.Map;

/**
 * @Classname SysLdapConfigController
 * @Description <h1>LDAP配置相关接口</h1>
 * @Author yulj
 * @Date: 2020/04/27 2:08 下午
 */
@RestController
@RequestMapping("/ldapConfig")
@Api(tags = "LDAP配置相关接口")
public class SysLdapConfigController {

    @Autowired
    private SysLdapConfigService sysLdapConfigService;

    @ApiOperation(value = "查询LDAP配置", notes = "查询LDAP配置", response = R.class)
    @GetMapping("/getDefault")
    public R getDefault() {
        return sysLdapConfigService.getDefultLdapConfig();
    }

    @LogAnnotation(serviceId = ServiceIdsConstants.USER_CENTER, title = "更新LDAP配置信息")
    @ApiOperation(value = "更新LDAP配置", notes = "更新LDAP配置", response = R.class)
    @PostMapping("/update")
    public R update(@Valid @RequestBody SysLdapConfigUpdateBO sysLdapConfigUpdateBO, @ApiIgnore BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = BindingResultUtil.getErrors(bindingResult);
            return R.errorMap(errorMap);
        }
        return sysLdapConfigService.updateLdapConfig(sysLdapConfigUpdateBO);
    }

    @ApiOperation(value = "连接测试", notes = "连接测试LDAP配置", response = R.class)
    @PostMapping("/connectTest")
    public R connectTest(@Valid @RequestBody SysLdapConfigUpdateBO sysLdapConfigUpdateBO, @ApiIgnore BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = BindingResultUtil.getErrors(bindingResult);
            return R.errorMap(errorMap);
        }
        return sysLdapConfigService.connectTest(sysLdapConfigUpdateBO);
    }

}
