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
 * @Classname PlatformGroup
 * @Description <h1>平台类型表实体</h1>
 * @Author yulj
 * @Date: 2020/04/22 2:45 下午
 */
@ApiModel(value = "平台类型表实体")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlatformGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id", dataType = "int")
    private Integer id;

    /**
     * 分组名称
     */
    @ApiModelProperty(value = "分组名称", dataType = "String")
    private String groupName;

    /**
     * 排序值
     */
    @ApiModelProperty(value = "排序值", dataType = "int")
    private Integer num;

    /**
     * 组简介
     */
    @ApiModelProperty(value = "组简介", dataType = "description")
    private String description;

    /**
     * 状态(0停用,1启用)
     */
    @ApiModelProperty(value = "状态(0停用,1启用)", dataType = "int")
    private Integer enable;

    /**
     * 删除标记(0未删除,1已删除)
     */
    @ApiModelProperty(value = "删除标记(0未删除,1已删除)", dataType = "int")
    private Integer delFlag;

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