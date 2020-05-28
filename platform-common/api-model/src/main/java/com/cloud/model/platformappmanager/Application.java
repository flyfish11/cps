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
 * @Classname Application
 * @Description <h1>应用表实体</h1>
 * @Author yulj
 * @Date: 2020/04/22 8:22 下午
 */
@ApiModel(value = "应用表实体")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Application implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id", dataType = "int")
    private Integer id;

    /**
     * logo地址
     */
    @ApiModelProperty(value = "logo地址", dataType = "String")
    private String logoUrl;

    /**
     * 应用名称
     */
    @ApiModelProperty(value = "应用名称", dataType = "String")
    private String name;

    /**
     * 应用简称
     */
    @ApiModelProperty(value = "应用简称", dataType = "String")
    private String shortName;

    /**
     * 应用版本号
     */
    @ApiModelProperty(value = "应用版本号", dataType = "String")
    private String version;

    /**
     * 应用分类(1平台 2应用)
     */
    @ApiModelProperty(value = "应用分类(1平台 2应用)", dataType = "int")
    private Integer appClassification;

    /**
     * 应用分组
     */
    @ApiModelProperty(value = "应用分组", dataType = "int")
    private Integer platformGroup;

    /**
     * 应用类型
     */
    @ApiModelProperty(value = "应用类型", dataType = "int")
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
    @ApiModelProperty(value = "访问地址", dataType = "String")
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
     * 校验用户地址
     */
    @ApiModelProperty(value = "校验用户地址", dataType = "String")
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
     * 应用管理员
     */
    @ApiModelProperty(value = "应用管理员", dataType = "int")
    private Integer appAdmin;

    /**
     * 应用使用范围(部门)
     */
    @ApiModelProperty(value = "应用使用范围(部门)", dataType = "String")
    private String appUseScope;

    /**
     * 应用使用人员
     */
    @ApiModelProperty(value = "应用使用人员", dataType = "String")
    private String appUsePerson;

    /**
     * 删除状态(0未删除,1已删除)
     */
    @ApiModelProperty(value = "删除状态(0未删除,1已删除)", dataType = "int")
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

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注", dataType = "String")
    private String remarks;

}