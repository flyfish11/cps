package com.cloud.model.platformuser.bo;

import io.github.yangyouwang.annotion.Wrapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Classname SysUserAddBO
 * @Description <h1>用户新增业务对象</h1>
 * @Author yulj
 * @Date: 2020/04/21 7:55 下午
 */
@Data
@ApiModel(value = "用户新增业务对象")
public class SysUserUpdateBO {

    @ApiModelProperty(value = "主键id", dataType = "int", required = true)
    @NotNull(message = "用户id不能为空")
    private Integer id;

    /**
     * 头像
     */
    @ApiModelProperty(value = "头像", dataType = "String")
    private String avatar;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码", dataType = "String")
    private String password;

    /**
     * 真实姓名
     */
    @ApiModelProperty(value = "真实姓名", dataType = "String")
    private String name;

    /**
     * 性别（1:男 2:女 3:未知）
     */
    @ApiModelProperty(value = "性别（1:男 2:女 3:未知）", dataType = "int")
    @Wrapper(dictData = {"1:男", "2:女", "3:未知"}, dictType = "array", name = "性别")
    private Integer sex;

    /**
     * 电子邮件
     */
    @ApiModelProperty(value = "电子邮件", dataType = "String")
    private String email;

    /**
     * 电话
     */
    @ApiModelProperty(value = "电话", dataType = "String")
    private String phone;

    /**
     * 所属部门
     */
    @ApiModelProperty(value = "所属部门", dataType = "int")
    private Integer deptid;

    /**
     * 账号状态(1：启用  0：禁用）
     */
    @ApiModelProperty(value = "平台账号状态(1：启用  0：禁用）", dataType = "int")
    private Integer status;

    /**
     * 排序值
     */
    @ApiModelProperty(value = "排序值", dataType = "int")
    private Integer num;

    /**
     * 产品注册数量
     */
    @ApiModelProperty(value = "产品注册数量", dataType = "int")
    private Integer productRegisterCount;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注", dataType = "String")
    private String remarks;

}
