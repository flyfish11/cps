package com.cloud.model.appmanagercenter.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Classname BannerConfigAddBO
 * @Description <h1>banner配置新增业务对象</h1>
 * @Author yulj
 * @Date: 2020/04/23 9:15 下午
 */
@Data
@ApiModel(value = "banner配置新增业务对象")
public class BannerConfigAddBO {

    /**
     * 标题
     */
    @ApiModelProperty(value = "标题", dataType = "String", required = true)
    @NotBlank(message = "标题不能为空")
    private String title;

    /**
     * 简介
     */
    @ApiModelProperty(value = "简介", dataType = "String", required = true)
    @NotBlank(message = "简介不能为空")
    private String description;

    /**
     * 图片地址
     */
    @ApiModelProperty(value = "图片地址", dataType = "String", required = true)
    @NotBlank(message = "图片地址不能为空")
    private String imageUrl;

    /**
     * 访问地址
     */
    @ApiModelProperty(value = "访问地址", dataType = "String")
    private String actionUrl;

}
