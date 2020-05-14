package com.cloud.txmanager.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 模块信息
 *
 * @author LCN on 2017/11/13
 */
@Setter
@Getter
@ApiModel(description = "模块信息")
public class ModelInfo {

    @ApiModelProperty(value = "模块名称", dataType = "String")
    private String model;

    @ApiModelProperty(value = "模块地址", dataType = "String")
    private String ipAddress;

    @ApiModelProperty(value = "管道名称", dataType = "String")
    private String channelName;

    @ApiModelProperty(value = "唯一标识", dataType = "String")
    private String uniqueKey;

}
