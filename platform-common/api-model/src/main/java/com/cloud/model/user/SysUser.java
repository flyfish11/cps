package com.cloud.model.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Classname SysUser
 * @Description 系统用户表实体
 * @Author yulj
 * @Date: 2019/08/28 13:33
 */
@Data
@ApiModel(description = "系统用户表实体")
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id", dataType = "int")
    private Integer id;

    /**
     * 头像
     */
    @ApiModelProperty(value = "头像", dataType = "String")
    private String avatar;

    /**
     * 账号
     */
    @ApiModelProperty(value = "账号", dataType = "String")
    private String account;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码", dataType = "String")
    private String password;

    /**
     * 名字
     */
    @ApiModelProperty(value = "名字", dataType = "String")
    private String name;

    /**
     * 性别（1:男 2:女 3:未知）
     */
    @ApiModelProperty(value = "性别（1:男 2:女 3:未知）", dataType = "int")
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
     * 角色id
     */
    @ApiModelProperty(value = "角色id", dataType = "String")
    private String roleid;

    /**
     * 部门id
     */
    @ApiModelProperty(value = "部门id", dataType = "int")
    private Integer deptid;

    /**
     * 平台账号状态(1：启用  0：禁用）
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
     * 创建人
     */
    @ApiModelProperty(value = "创建人", dataType = "String")
    private String createBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间", dataType = "Date")
    private Date createTime;

    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人", dataType = "String")
    private String updateBy;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "更新时间", dataType = "Date")
    private Date updateTime;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注", dataType = "String")
    private String remarks;

}