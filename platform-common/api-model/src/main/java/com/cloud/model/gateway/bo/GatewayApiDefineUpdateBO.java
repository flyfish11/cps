package com.cloud.model.gateway.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Classname GatewayApiDefineAddBO
 * @Description <h1>网关路由规则表新增业务对象</h1>
 * @Author yulj
 * @Date: 2020/03/03 11:26 上午
 */
@Data
@ApiModel(value = "网关路由规则更新业务对象")
public class GatewayApiDefineUpdateBO {

    @ApiModelProperty(value = "应用名称", dataType = "String", required = true)
    @NotBlank(message = "路由规则id不能为空")
    private String id;

    @ApiModelProperty(value = "服务名称", dataType = "String")
    private String apiName;

    @ApiModelProperty(value = "路由规则", dataType = "String")
    private String path;

    @ApiModelProperty(value = "服务id", dataType = "String")
    private String serviceId;

    @ApiModelProperty(value = "是否启用", dataType = "Boolean")
    private Boolean enabled;

}