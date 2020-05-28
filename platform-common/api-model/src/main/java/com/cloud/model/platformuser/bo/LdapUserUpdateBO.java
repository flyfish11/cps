package com.cloud.model.platformuser.bo;

import lombok.Builder;
import lombok.Data;

/**
 * @Classname LdapRegisterUserInfo
 * @Description <h1>LDAP用户注册业务对象</h1>
 * @Author yulj
 * @Date: 2020/04/21 8:58 下午
 */
@Data
@Builder
public class LdapUserUpdateBO {

    /**
     * 账号
     */
    private String account;

    /**
     * 原密码
     */
    private String sourcePassword;

    /**
     * 新密码
     */
    private String newPassword;

}
