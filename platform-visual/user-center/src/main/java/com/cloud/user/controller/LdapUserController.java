package com.cloud.user.controller;

import com.cloud.common.constants.ServiceIdsConstants;
import com.cloud.common.utils.BindingResultUtil;
import com.cloud.common.utils.R;
import com.cloud.model.log.LogAnnotation;
import com.cloud.model.user.bo.LdapUserBO;
import com.cloud.user.service.LdapUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.Map;

/**
 * @Classname LdapUserController
 * @Description <h1>LDAP用户相关接口</h1>
 * @Author yulj
 * @Date: 2020/04/22 10:50 上午
 */
@RestController
@RequestMapping("/ldap")
@Api(tags = "LDAP用户相关接口")
public class LdapUserController {

    @Autowired
    private LdapUserService ldapUserService;

    @LogAnnotation(serviceId = ServiceIdsConstants.USER_CENTER, title = "LDAP中认证账号信息")
    @ApiOperation(value = "LDAP用户认证", notes = "LDAP中认证账号信息", response = R.class)
    @PostMapping("/authenticate")
    public R authenticate(@Valid @RequestBody LdapUserBO ldapUserBO, @ApiIgnore BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = BindingResultUtil.getErrors(bindingResult);
            return R.errorMap(errorMap);
        }
        return ldapUserService.authenticate(ldapUserBO);
    }

}
