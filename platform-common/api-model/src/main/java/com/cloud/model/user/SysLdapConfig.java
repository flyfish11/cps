package com.cloud.model.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Classname SysLdapConfig
 * @Description <h1>LDAP配置表实体</h1>
 * @Author yulj
 * @Date: 2020/04/27 1:43 下午
 */
@ApiModel(value = "LDAP配置表实体")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysLdapConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id", dataType = "int")
    private Integer id;

    /**
     * LDAP访问地址
     */
    @ApiModelProperty(value = "LDAP访问地址", dataType = "String")
    private String url;

    /**
     * 端口号
     */
    @ApiModelProperty(value = "端口号", dataType = "int")
    private Integer port;

    /**
     * Distinguished Name
     */
    @ApiModelProperty(value = "DN", dataType = "String")
    private String dn;

    /**
     * 管理员账号
     */
    @ApiModelProperty(value = "管理员账号", dataType = "String")
    private String adminAccount;

    /**
     * 管理员密码
     */
    @ApiModelProperty(value = "管理员密码", dataType = "String")
    private String adminPassword;

}