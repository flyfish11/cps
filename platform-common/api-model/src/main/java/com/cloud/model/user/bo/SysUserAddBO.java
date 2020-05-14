package com.cloud.model.user.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @Classname SysUserAddBO
 * @Description <h1>用户新增业务对象</h1>
 * @Author yulj
 * @Date: 2020/04/21 7:55 下午
 */
@Data
@ApiModel(value = "用户新增业务对象")
public class SysUserAddBO {

    /**
     * 头像
     */
    @ApiModelProperty(value = "头像", dataType = "String")
    private String avatar;

    /**
     * 账号
     */
    @ApiModelProperty(value = "账号", dataType = "String", required = true)
    @NotBlank(message = "账号不能为空")
    @Length(min = 1, max = 32, message = "账号的的长度需在1到32之间")
    @Pattern(regexp = "^[\\u4E00-\\u9FA5A-Za-z0-9\\*]*$", message = "用户昵称限制：最多32字符，包含文字、字母和数字")
    private String account;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码", dataType = "String", required = true)
    @NotBlank(message = "密码不能为空")
    private String password;

    /**
     * 真实姓名
     */
    @ApiModelProperty(value = "真实姓名", dataType = "String", required = true)
    @Length(min = 1, max = 32, message = "真实姓名的的长度需在1到32之间")
    @NotBlank(message = "真实姓名不能为空")
    private String name;

    /**
     * 性别（1:男 2:女 3:未知）
     */
    @ApiModelProperty(value = "性别（1:男 2:女 3:未知）", dataType = "int", required = true)
    @NotNull(message = "性别不能为空")
    private Integer sex;

    /**
     * 电子邮件
     */
    @ApiModelProperty(value = "电子邮件", dataType = "String", required = true)
    @NotBlank(message = "电子邮件不能为空")
    @Email(message = "邮箱不符合规则!")
    private String email;

    /**
     * 电话
     */
    @ApiModelProperty(value = "电话", dataType = "String", required = true)
    @NotBlank(message = "电话不能为空")
    @Length(min = 11, max = 11, message = "手机号的长度必须是11位")
    @Pattern(regexp = "^[1][3,4,5,6,7,8,9][0-9]{9}$", message = "手机号格式有误")
    private String phone;

    /**
     * 所属部门
     */
    @ApiModelProperty(value = "所属部门", dataType = "int", required = true)
    @NotNull(message = "所属部门不能为空")
    private Integer deptid;

    /**
     * 账号状态(1：启用  0：禁用）
     */
    @ApiModelProperty(value = "平台账号状态(1：启用  0：禁用）", dataType = "int", required = true)
    @NotNull(message = "账号状态不能为空")
    private Integer status;

    /**
     * 排序值
     */
    @ApiModelProperty(value = "排序值", dataType = "int", required = true)
    @NotNull(message = "账号状态不能为空")
    private Integer num;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注", dataType = "String")
    private String remarks;

}
