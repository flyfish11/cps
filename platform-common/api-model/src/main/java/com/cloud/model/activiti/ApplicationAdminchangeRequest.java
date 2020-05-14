package com.cloud.model.activiti;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.io.Serializable;

/**
 * 应用网络管理员变更(ApplicationAdminchangeRequest)实体类
 *
 * @author makejava
 * @since 2019-08-23 13:57:38
 */
@Data
@ToString
public class ApplicationAdminchangeRequest implements Serializable {
    private static final long serialVersionUID = -83616300034608145L;
    
    /**
     * 应用管理员变更主键
     */
    @ApiModelProperty(value = "应用管理员变更主键", dataType = "String")    
    private String id;
    
    /**
     * 申请时间
     */
    @ApiModelProperty(value = "申请时间", dataType = "Date")    
    private Date requesttime;
    
    /**
     * 申请部门
     */
    @ApiModelProperty(value = "申请部门", dataType = "String")    
    private String deptid;
    
    /**
     * 变更应用名称
     */
    @ApiModelProperty(value = "变更应用名称", dataType = "String")    
    private String agentname;
    
    /**
     * 变更时间
     */
    @ApiModelProperty(value = "变更时间", dataType = "Date")    
    private Date changetime;
    
    /**
     * 应用管理员所属部门
     */
    @ApiModelProperty(value = "应用管理员所属部门", dataType = "String")    
    private String admindeptid;
    
    /**
     * 应用管理员名称
     */
    @ApiModelProperty(value = "应用管理员名称", dataType = "String")    
    private String agentAdmin;
    
    /**
     * 应用管理员电话
     */
    @ApiModelProperty(value = "应用管理员电话", dataType = "String")    
    private String agentAdminMobile;
    
    /**
     * 应用管理员邮箱
     */
    @ApiModelProperty(value = "应用管理员邮箱", dataType = "String")    
    private String agentAdminEmail;
    
    /**
     * 应用管理员OA账号
     */
    @ApiModelProperty(value = "应用管理员OA账号", dataType = "String")    
    private String agentAdminOaacount;
    
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", dataType = "Date")    
    private Date createtime;
    
    /**
     * 新应用管理员所属部门
     */
    @ApiModelProperty(value = "新应用管理员所属部门", dataType = "String")    
    private String newAdmindeptid;
    
    /**
     * 新应用管理员id
     */
    @ApiModelProperty(value = "新应用管理员id", dataType = "String")    
    private String newAgentAdmin;
    
    /**
     * 新应用管理员电话
     */
    @ApiModelProperty(value = "新应用管理员电话", dataType = "String")    
    private String newAgentAdminMobile;
    
    /**
     * 新应用管理员邮箱
     */
    @ApiModelProperty(value = "新应用管理员邮箱", dataType = "String")    
    private String newAgentAdminEmail;
    
    /**
     * 新应用管理员OA账号
     */
    @ApiModelProperty(value = "新应用管理员OA账号", dataType = "String")    
    private String newAgentAdminOaacount;
    
    /**
     * 应用管理员变更申请附件url
     */
    @ApiModelProperty(value = "应用管理员变更申请附件url", dataType = "String")    
    private String agentadminChangeRequestFileurl;
    



}