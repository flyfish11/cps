package com.cloud.model.platformuser.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Classname SysDeptAddBO
 * @Description <h1>部门新增业务对象</h1>
 * @Author yulj
 * @Date: 2020/04/21 4:40 下午
 */
@Data
@ApiModel(value = "部门新增业务对象")
public class SysDeptAddBO {

    /**
     * 父部门id
     */
    @ApiModelProperty(value = "父部门id", dataType = "int", required = true)
    @NotNull(message = "父部门id不能为空")
    private Integer pid;

    /**
     * 部门全称
     */
    @ApiModelProperty(value = "部门全称", dataType = "String", required = true)
    @NotBlank(message = "部门全称不能为空")
    private String fullname;

    /**
     * 部门简称
     *
     * @return
     */
    @ApiModelProperty(value = "部门简称", dataType = "String", required = true)
    @NotBlank(message = "部门简称不能为空")
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
