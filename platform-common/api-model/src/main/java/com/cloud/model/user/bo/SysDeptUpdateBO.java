package com.cloud.model.user.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Classname SysDeptAddBO
 * @Description <h1>部门修改业务对象</h1>
 * @Author yulj
 * @Date: 2020/04/21 4:44 下午
 */
@Data
@ApiModel(value = "部门修改业务对象")
public class SysDeptUpdateBO {

    /**
     * 主键id
     */
    @ApiModelProperty(value = "部门id", dataType = "int", required = true)
    @NotNull(message = "部门id不能为空")
    private Integer id;

    /**
     * 父部门id
     */
    @ApiModelProperty(value = "父部门id", dataType = "int")
    private Integer pid;

    /**
     * 部门全称
     */
    @ApiModelProperty(value = "部门全称", dataType = "String")
    private String fullname;

    /**
     * 部门简称
     *
     * @return
     */
    @ApiModelProperty(value = "部门简称", dataType = "String")
    private String sdeptname;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序", dataType = "int")
    private Integer num;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注", dataType = "String")
    private String remarks;

}
