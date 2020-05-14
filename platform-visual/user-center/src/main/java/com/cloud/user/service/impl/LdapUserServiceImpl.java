package com.cloud.user.service.impl;

import cn.hutool.core.util.StrUtil;
import com.cloud.common.constants.CommonConstants;
import com.cloud.common.enums.ResultEnum;
import com.cloud.common.utils.JupiterPublicMethods;
import com.cloud.common.utils.R;
import com.cloud.model.user.SysLdapConfig;
import com.cloud.model.user.bo.LdapUserBO;
import com.cloud.model.user.bo.LdapUserUpdateBO;
import com.cloud.user.entity.Person;
import com.cloud.user.service.LdapUserService;
import com.cloud.user.service.SysLdapConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.NameAlreadyBoundException;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.stereotype.Service;

/**
 * @Classname LdapUserServiceImpl
 * @Description <h1>LDAP 用户相关服务层实现</h1>
 * @Author yulj
 * @Date: 2020/04/22 10:29 上午
 */
@Service
@Slf4j
public class LdapUserServiceImpl implements LdapUserService {

    @Autowired
    private SysLdapConfigService sysLdapConfigService;

    /**
     * <h2>CDI上下文初始化LdapTemplate</h2>
     *
     * @return
     */
    private LdapTemplate initLdapTemplate() {
        SysLdapConfig sysLdapConfig = this.sysLdapConfigService.loadDefaultData();
        LdapContextSource ldapContextSource = new LdapContextSource();
        if (null != sysLdapConfig) {
            ldapContextSource.setUrl(StrUtil.format("{}:{}",
                    sysLdapConfig.getUrl().trim(), sysLdapConfig.getPort()));
            ldapContextSource.setBase(sysLdapConfig.getDn().trim());
            ldapContextSource.setUserDn(StrUtil.format("cn={},{}",
                    sysLdapConfig.getAdminAccount().trim(), sysLdapConfig.getDn().trim()));
            ldapContextSource.setPassword(sysLdapConfig.getAdminPassword());
        }
        // 调用 afterPropertiesSet 来完成初始化
        ldapContextSource.afterPropertiesSet();
        return new LdapTemplate(ldapContextSource);
    }

    @Override
    public R authenticate(LdapUserBO ldapUserBO) {
        boolean result;
        try {
            EqualsFilter filter = new EqualsFilter("uid", ldapUserBO.getAccount());
            LdapTemplate ldapTemplate = initLdapTemplate();
            result = ldapTemplate.authenticate("", filter.toString(), ldapUserBO.getPassword());
            log.info("LDAP中验证用户:{}", ldapUserBO);
        } catch (Exception e) {
            log.error("验证LDAP用户失败！", e);
            return R.failed(ResultEnum.LOGIN_FAIL.getMessage());
        }
        if (result == Boolean.FALSE) {
            return R.failed(ResultEnum.BAD_CREDENTIALS.getMessage());
        }
        return R.ok(null, ResultEnum.LDAP_AUTHENTICATE_SUCCESS.getMessage());
    }

    @Override
    public R register(LdapUserBO ldapUserBO) {
        String username = ldapUserBO.getAccount();
        Person person = new Person(username);
        person.setUserPassword(ldapUserBO.getPassword());
        person.setCn("cn-" + username);
        person.setSn("sn-" + username);
        person.setGidNumber(Integer.valueOf(JupiterPublicMethods.createCode(6)));
        person.setUidNumber(Integer.valueOf(JupiterPublicMethods.createCode(6)));
        person.setHomeDirectory("/home/user");
        try {
            LdapTemplate ldapTemplate = initLdapTemplate();
            ldapTemplate.create(person);
            log.info("LDAP中创建用户:{}", person);
        } catch (Exception e) {
            if (e instanceof NameAlreadyBoundException) {
                return R.failed("LDAP中已存在该账号");
            }
            throw new RuntimeException(e);
        }
        return R.ok("注册成功");
    }

    @Override
    public R logout(String account) {
        LdapTemplate ldapTemplate = initLdapTemplate();
        ldapTemplate.delete(new Person(account));
        return R.ok();
    }

    @Override
    public R update(LdapUserUpdateBO ldapUserUpdateBO) {
        // LDAP中验证用户
        LdapUserBO ldapUserBO = LdapUserBO.builder()
                .account(ldapUserUpdateBO.getAccount())
                .password(ldapUserUpdateBO.getSourcePassword())
                .build();
        R authResult = authenticate(ldapUserBO);
        if (authResult.getCode() != CommonConstants.SUCCESS) {
            return R.failed("LDAP中不存在该账号信息");
        }

        return updateLdap(ldapUserUpdateBO);
    }

    @Override
    public R resetPassword(LdapUserUpdateBO ldapUserUpdateBO) {
        return updateLdap(ldapUserUpdateBO);
    }

    public R updateLdap(LdapUserUpdateBO ldapUserUpdateBO) {
        String username = ldapUserUpdateBO.getAccount();
        Person person = new Person(username);
        person.setUserPassword(ldapUserUpdateBO.getNewPassword());
        person.setCn("cn-" + username);
        person.setSn("sn-" + username);
        person.setGidNumber(Integer.valueOf(JupiterPublicMethods.createCode(6)));
        person.setUidNumber(Integer.valueOf(JupiterPublicMethods.createCode(6)));
        person.setHomeDirectory("/home/user");
        LdapTemplate ldapTemplate = initLdapTemplate();
        ldapTemplate.update(person);
        return R.ok();
    }

}
