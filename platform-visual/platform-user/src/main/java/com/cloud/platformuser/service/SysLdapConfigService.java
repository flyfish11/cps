package com.cloud.platformuser.service;

import com.cloud.common.utils.R;
import com.cloud.model.platformuser.SysLdapConfig;
import com.cloud.model.platformuser.bo.SysLdapConfigUpdateBO;

/**
 * @Classname SysLdapConfigService
 * @Description <h1>LDAP配置表服务层</h1>
 * @Author yulj
 * @Date: 2020/04/27 1:56 下午
 */
public interface SysLdapConfigService {

    /**
     * <h2>获取默认LDAP配置</h2>
     *
     * @return
     */
    R getDefultLdapConfig();

    /**
     * <h2>更新LDAP配置</h2>
     *
     * @param sysLdapConfigUpdateBO LDAP配置更新业务对象
     * @return
     */
    R updateLdapConfig(SysLdapConfigUpdateBO sysLdapConfigUpdateBO);

    /**
     * <h2>查询默认LDAP配置</h2>
     *
     * @return
     */
    SysLdapConfig loadDefaultData();

    /**
     * <h1>连接测试</h1>
     *
     * @param sysLdapConfigUpdateBO LDAP配置更新业务对象
     * @return
     */
    R connectTest(SysLdapConfigUpdateBO sysLdapConfigUpdateBO);

}
