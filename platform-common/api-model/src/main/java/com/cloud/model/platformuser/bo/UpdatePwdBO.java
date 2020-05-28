package com.cloud.model.platformuser.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

/**
 * @Classname UpdatePwdBO
 * @Description <h1>修改用户密码业务对象</h1>
 * @Author yulj
 * @Date: 2020/04/23 8:58 上午
 */
@Data
@Builder
@ToString
@ApiModel(value = "修改用户密码业务对象")
public class UpdatePwdBO {

    /**
     * 原账号密码
     */
    @ApiModelProperty(value = "原密码", dataType = "String", required = true)
    @NotBlank(message = "原密码不能为空")
    private String sourcePassword;

    /**
     * 新密码
     */
    @ApiModelProperty(value = "新密码", dataType = "String", required = true)
    @NotBlank(message = "新密码不能为空")
    private String newPassword;

}
