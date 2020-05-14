package com.cloud.model.user.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Classname LdapRegisterUserInfo
 * @Description <h1>LDAP用户业务对象</h1>
 * @Author yulj
 * @Date: 2020/04/21 8:58 下午
 */
@Data
@Builder
@ToString
@ApiModel(value = "LDAP用户业务对象")
public class LdapUserBO implements Serializable {

    /**
     * 账号
     */
    @ApiModelProperty(value = "账号", dataType = "String", required = true)
    @NotBlank(message = "账号不能为空")
    private String account;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码", dataType = "String", required = true)
    @NotBlank(message = "密码不能为空")
    private String password;

}
