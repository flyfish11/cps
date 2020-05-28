package com.cloud.model.platformappmanager.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Classname ApplicationAddBO
 * @Description <h1>应用新增业务对象</h1>
 * @Author yulj
 * @Date: 2020/04/22 8:37 下午
 */
@Data
@ApiModel(value = "应用新增业务对象")
public class ApplicationAddBO {

    /**
     * logo地址
     */
    @ApiModelProperty(value = "logo地址", dataType = "String", required = true)
    @NotBlank(message = "应用logo不能为空")
    private String logoUrl;

    /**
     * 应用名称
     */
    @ApiModelProperty(value = "应用名称", dataType = "String", required = true)
    @NotBlank(message = "应用名称不能为空")
    private String name;

    /**
     * 应用简称
     */
    @ApiModelProperty(value = "应用简称", dataType = "String", required = true)
    @NotBlank(message = "应用简称不能为空")
    private String shortName;

    /**
     * 应用版本号
     */
    @ApiModelProperty(value = "应用版本号", dataType = "String", required = true)
    @NotBlank(message = "应用版本号不能为空")
    private String version;

    /**
     * 应用分类
     */
    @ApiModelProperty(value = "应用分类", dataType = "int", required = true)
    @NotNull(message = "应用分类不能为空")
    private Integer appClassification;

    /**
     * 应用分组
     */
    @ApiModelProperty(value = "应用分组", dataType = "int", required = true)
    @NotNull(message = "应用分组不能为空")
    private Integer platformGroup;

    /**
     * 应用类型
     */
    @ApiModelProperty(value = "应用类型", dataType = "int", required = true)
    @NotNull(message = "应用类型不能为空")
    private Integer appType;

    /**
     * 应用截图
     */
    @ApiModelProperty(value = "应用截图", dataType = "String")
    private String screenshots;

    /**
     * 应用简介
     */
    @ApiModelProperty(value = "应用简介", dataType = "String")
    private String description;

    /**
     * 访问地址
     */
    @ApiModelProperty(value = "访问地址", dataType = "String", required = true)
    @NotBlank(message = "访问地址不能为空")
    private String indexUrl;

    /**
     * 排序值
     */
    @ApiModelProperty(value = "排序值", dataType = "int")
    private Integer orderNum;

    /**
     * 运行状态(0未运行,1运行中)
     */
    @ApiModelProperty(value = "运行状态(0未运行,1运行中)", dataType = "int")
    private Integer runStatus;

    /**
     * 注册用户地址
     */
    @ApiModelProperty(value = "注册用户地址", dataType = "String")
    private String userRegisterUrl;

    /**
     * 注销用户地址
     */
    @ApiModelProperty(value = "注销用户地址", dataType = "String")
    private String userLogoutUrl;

    /**
     * 验证用户地址
     */
    @ApiModelProperty(value = "验证用户地址", dataType = "String")
    private String userCheckUrl;


    /**
     * 用户测试地址
     */
    @ApiModelProperty(value = "用户测试地址", dataType = "String")
    private String userTestUrl;

    /**
     * 应用token
     */
    @ApiModelProperty(value = "应用token", dataType = "String")
    private String appToken;

    /**
     * 应用管理员账号
     */
    @ApiModelProperty(value = "应用管理员账号", dataType = "String")
    private String adminAccount;

    /**
     * 应用管理员密码
     */
    @ApiModelProperty(value = "应用管理员密码", dataType = "String")
    private String adminPassword;

    /**
     * 应用所属部门
     */
    @ApiModelProperty(value = "应用所属部门", dataType = "String")
    private Integer responsibleDept;

    /**
     * 应用使用范围(部门)
     */
    @ApiModelProperty(value = "应用使用范围(部门)", dataType = "String")
    private String appUseScope;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注", dataType = "String")
    private String remarks;

}
