package com.cloud.model.appmanagercenter.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Classname ApplicationUserSyncBO
 * @Description <h1>应用用户同步业务对象</h1>
 * @Author yulj
 * @Date: 2020/04/25 11:20 上午
 */
@Data
@ApiModel(description = "应用用户同步业务对象")
public class ApplicationUserSyncBO {

    @ApiModelProperty(value = "用户id", dataType = "int", required = true)
    private Integer userId;

    @ApiModelProperty(value = "应用id", dataType = "int", required = true)
    private Integer appId;

    /**
     * 0已关闭,1已开放
     */
    @ApiModelProperty(value = "0已关闭,1已开放", dataType = "int", required = true)
    private Boolean syncSttaus;

}
