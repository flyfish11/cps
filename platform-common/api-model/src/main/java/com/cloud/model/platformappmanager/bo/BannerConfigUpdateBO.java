package com.cloud.model.platformappmanager.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Classname BannerConfigUpdateBO
 * @Description <h1>banner配置更新业务对象</h1>
 * @Author yulj
 * @Date: 2020/04/23 9:16 下午
 */
@Data
@ApiModel(value = "banner配置更新业务对象")
public class BannerConfigUpdateBO {

    /**
     * banner配置id
     */
    @ApiModelProperty(value = "banner配置id", dataType = "int")
    @NotNull(message = "banner配置id不能为空")
    private Integer id;

    /**
     * 标题
     */
    @ApiModelProperty(value = "标题", dataType = "String")
    private String title;

    /**
     * 简介
     */
    @ApiModelProperty(value = "简介", dataType = "String")
    private String description;

    /**
     * 图片地址
     */
    @ApiModelProperty(value = "图片地址", dataType = "String")
    private String imageUrl;

    /**
     * 访问地址
     */
    @ApiModelProperty(value = "访问地址", dataType = "String")
    private String actionUrl;

}
