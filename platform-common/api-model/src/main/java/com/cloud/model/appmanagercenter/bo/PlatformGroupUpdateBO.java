package com.cloud.model.appmanagercenter.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Classname PlatformGroupUpdateBO
 * @Description <h1>平台类型更新数据对象</h1>
 * @Author yulj
 * @Date: 2020/04/22 2:58 下午
 */
@Data
@ApiModel(value = "平台类型更新数据对象")
public class PlatformGroupUpdateBO {

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id", dataType = "int", required = true)
    @NotNull(message = "平台类型id不能为空")
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

}
