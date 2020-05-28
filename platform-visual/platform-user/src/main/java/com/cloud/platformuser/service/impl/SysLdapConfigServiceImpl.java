package com.cloud.platformuser.service.impl;

import cn.hutool.core.util.StrUtil;
import com.cloud.common.constants.CommonConstants;
import com.cloud.common.enums.ResultEnum;
import com.cloud.common.utils.R;
import com.cloud.model.platformuser.SysLdapConfig;
import com.cloud.model.platformuser.bo.SysLdapConfigUpdateBO;
import com.cloud.platformuser.dao.SysLdapConfigDao;
import com.cloud.platformuser.service.SysLdapConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.ldap.AuthenticationException;
import org.springframework.ldap.CommunicationException;
import org.springframework.ldap.UncategorizedLdapException;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Classname SysLdapConfigServiceImpl
 * @Description <h1>LDAP配置表服务层实现</h1>
 * @Author yulj
 * @Date: 2020/04/27 2:03 下午
 */
@Service
@Slf4j
public class SysLdapConfigServiceImpl implements SysLdapConfigService {

    @Autowired
    private SysLdapConfigDao sysLdapConfigDao;

    /**
     * <h2>CDI上下文初始化LdapTemplate</h2>
     *
     * @return
     */
    private LdapTemplate initLdapTemplate(SysLdapConfigUpdateBO sysLdapConfigUpdateBO) {
        LdapContextSource ldapContextSource = new LdapContextSource();
        if (null != sysLdapConfigUpdateBO) {
            ldapContextSource.setUrl(StrUtil.format("{}:{}",
                    sysLdapConfigUpdateBO.getUrl().trim(), sysLdapConfigUpdateBO.getPort()));
            ldapContextSource.setBase(sysLdapConfigUpdateBO.getDn().trim());
            ldapContextSource.setUserDn(StrUtil.format("cn={},{}",
                    sysLdapConfigUpdateBO.getAdminAccount().trim(), sysLdapConfigUpdateBO.getDn().trim()));
            ldapContextSource.setPassword(sysLdapConfigUpdateBO.getAdminPassword());
        }
        // 调用 afterPropertiesSet 来完成初始化
        ldapContextSource.afterPropertiesSet();
        return new LdapTemplate(ldapContextSource);
    }

    @Override
    @Cacheable(value = "sysLdapConfigData", key = "targetClass + methodName +#p0", unless = "#result == null")
    public R getDefultLdapConfig() {
        SysLdapConfig sysLdapConfig = this.sysLdapConfigDao.selectById(CommonConstants.DEFUALT_LDAP_CONFIG_ID);
        return R.ok(sysLdapConfig);
    }

    @Override
    @Transactional
    @CacheEvict(value = "sysLdapConfigData", allEntries = true, beforeInvocation = true)
    public R updateLdapConfig(SysLdapConfigUpdateBO sysLdapConfigUpdateBO) {
        SysLdapConfig sysLdapConfig = new SysLdapConfig();
        BeanUtils.copyProperties(sysLdapConfigUpdateBO, sysLdapConfig);
        this.sysLdapConfigDao.updateByIdSelective(sysLdapConfig);

        return R.ok(ResultEnum.UPDATE_OPERATE_SUCCESS.getMessage());
    }

    @Override
    @Cacheable(value = "sysLdapConfigData", key = "targetClass + methodName +#p0", unless = "#result == null")
    public SysLdapConfig loadDefaultData() {
        return this.sysLdapConfigDao.selectById(CommonConstants.DEFUALT_LDAP_CONFIG_ID);
    }

    @Override
    public R connectTest(SysLdapConfigUpdateBO sysLdapConfigUpdateBO) {
        try {
            log.info("LDAP验证:{}", sysLdapConfigUpdateBO);
            LdapTemplate ldapTemplate = initLdapTemplate(sysLdapConfigUpdateBO);
            EqualsFilter filter = new EqualsFilter("uid", sysLdapConfigUpdateBO.getAdminAccount());
            ldapTemplate.authenticate("", filter.toString(), sysLdapConfigUpdateBO.getAdminPassword());
            return R.ok(ResultEnum.LDAP_CONNECT_SUCCESS.getMessage());
        } catch (Exception e) {
            String errMsg = "";
            log.error("验证LDAP用户失败！", sysLdapConfigUpdateBO);
            if (e instanceof CommunicationException) {
                errMsg = ",请检查IP或端口号信息是否有误.";
            } else if (e instanceof UncategorizedLdapException) {
                errMsg = ",请检查访问地址信息是否有误.";
            } else if (e instanceof AuthenticationException) {
                errMsg = ",请检查DN、用户名和密码信息是否有误.";
            }
            return R.failed(ResultEnum.LDAP_CONNECT_ERROR.getMessage() + errMsg);
        }
    }

}
