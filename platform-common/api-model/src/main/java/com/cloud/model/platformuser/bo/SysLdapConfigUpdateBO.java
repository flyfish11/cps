package com.cloud.model.platformuser.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Classname SysLdapConfigUpdateBO
 * @Description <h1>LDAP配置更新业务实体</h1>
 * @Author yulj
 * @Date: 2020/04/27 1:43 下午
 */
@ApiModel(value = "LDAP配置更新业务实体")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysLdapConfigUpdateBO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id", dataType = "int", required = true)
    @NotNull(message = "LDAP配置id不能为空")
    private Integer id;

    /**
     * LDAP访问地址
     */
    @ApiModelProperty(value = "LDAP访问地址", dataType = "String", required = true)
    @NotBlank(message = "LDAP访问地址不能为空")
    private String url;

    /**
     * 端口号
     */
    @ApiModelProperty(value = "端口号", dataType = "int", required = true)
    @NotNull(message = "端口号不能为空")
    private Integer port;

    /**
     * Distinguished Name
     */
    @ApiModelProperty(value = "DN", dataType = "String", required = true)
    @NotBlank(message = "DN不能为空")
    private String dn;

    /**
     * 管理员账号
     */
    @ApiModelProperty(value = "管理员账号", dataType = "String", required = true)
    @NotBlank(message = "管理员账号不能为空")
    private String adminAccount;

    /**
     * 管理员密码
     */
    @ApiModelProperty(value = "管理员密码", dataType = "String", required = true)
    @NotBlank(message = "管理员密码不能为空")
    private String adminPassword;

}