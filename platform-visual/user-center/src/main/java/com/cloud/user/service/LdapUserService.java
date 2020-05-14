package com.cloud.user.service;

import com.cloud.common.utils.R;
import com.cloud.model.user.bo.LdapUserBO;
import com.cloud.model.user.bo.LdapUserUpdateBO;

/**
 * @Classname LdapUserService
 * @Description <h1>LDAP 用户相关服务层</h1>
 * @Author yulj
 * @Date: 2020/04/22 10:18 上午
 */
public interface LdapUserService {

    /**
     * <h2>LDAP用户认证</h2>
     *
     * @param ldapUserBO LDAP用户业务对象
     * @return
     */
    R authenticate(LdapUserBO ldapUserBO);

    /**
     * <h2>注册用户到 LDAP</h2>
     *
     * @param ldapUserBO LDAP用户业务对象
     * @return
     */
    R register(LdapUserBO ldapUserBO);

    /**
     * <h2>注销用户到 LDAP</h2>
     *
     * @param account 账号
     * @return
     */
    R logout(String account);

    /**
     * <h2>更新 LDAP 用户信息</h2>
     *
     * @param ldapUserUpdateBO LDAP用户注册业务对象
     * @return
     */
    R update(LdapUserUpdateBO ldapUserUpdateBO);

    /**
     * <h2>用户重置密码</h2>
     *
     * @param ldapUserUpdateBO LDAP用户注册业务对象
     * @return
     */
    R resetPassword(LdapUserUpdateBO ldapUserUpdateBO);

}
