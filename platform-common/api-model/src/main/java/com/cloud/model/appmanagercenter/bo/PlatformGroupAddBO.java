package com.cloud.model.appmanagercenter.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @Classname PlatformGroupAddBO
 * @Description <h1>平台类型新增数据对象</h1>
 * @Author yulj
 * @Date: 2020/04/22 2:58 下午
 */
@Data
@ApiModel(value = "平台类型新增数据对象")
public class PlatformGroupAddBO {

    /**
     * 分组名称
     */
    @ApiModelProperty(value = "分组名称", dataType = "String", required = true)
    @NotBlank(message = "分组名称不能为空")
    private String groupName;

    /**
     * 排序值
     */
    @ApiModelProperty(value = "排序值", dataType = "int", required = true)
    @NotNull(message = "排序值不能为空")
    private Integer num;

    /**
     * 状态(0停用,1启用)
     */
    @ApiModelProperty(value = "状态(0停用,1启用)", dataType = "int", required = true)
    @NotNull(message = "状态不能为空")
    private Integer enable;

    /**
     * 组简介
     */
    @ApiModelProperty(value = "组简介", dataType = "description")
    private String description;

}
