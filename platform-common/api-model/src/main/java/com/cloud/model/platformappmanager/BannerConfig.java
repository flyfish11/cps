package com.cloud.model.platformappmanager;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @Classname BannerConfig
 * @Description <h1>banner配置表实体</h1>
 * @Author yulj
 * @Date: 2020/04/23 9:05 下午
 */
@ApiModel(value = "banner配置表实体")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BannerConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id", dataType = "int")
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

    /**
     * 排序值
     */
    @ApiModelProperty(value = "排序值", dataType = "int")
    private Integer orderNum;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人", dataType = "String")
    private String createBy;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", dataType = "Date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人", dataType = "String")
    private String updateBy;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间", dataType = "Date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

}