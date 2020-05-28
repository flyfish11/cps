package com.cloud.model.platformappmanager.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Classname AppAdminBO
 * @Description 设置应用管理员业务对象
 * @Author yulj
 * @Date: 2019/12/11 5:35 下午
 */
@Data
@ApiModel(description = "设置应用管理员业务对象")
public class AppAdminBO {

    /**
     * 应用id
     */
    @ApiModelProperty(value = "应用id", dataType = "int")
    @NotNull(message = "应用id不能为空")
    private Integer id;

    /**
     * 应用管理员
     */
    @ApiModelProperty(value = "应用管理员", dataType = "int")
    @NotNull(message = "用户id不能为空")
    private Integer appAdmin;
}
