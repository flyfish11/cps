package com.cloud.model.appmanagercenter.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Classname ApplicationUserSyncVO
 * @Description <h1>应用用户同步业务对象</h1>
 * @Author yulj
 * @Date: 2020/04/25 10:26 下午
 */
@Data
@ApiModel(value = "应用用户同步业务对象")
public class ApplicationUserSyncVO {

    /**
     * 应用id
     */
    @ApiModelProperty(value = "应用id", dataType = "int")
    private Integer appId;

    /**
     * 应用名称
     */
    @ApiModelProperty(value = "应用名称", dataType = "String")
    private String appName;

    /**
     * 0已关闭,1已开放
     */
    @ApiModelProperty(value = "0已关闭,1已开放", dataType = "int")
    private Boolean syncSttaus = Boolean.FALSE;

}
