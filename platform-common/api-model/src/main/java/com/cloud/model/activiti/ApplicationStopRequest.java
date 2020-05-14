package com.cloud.model.activiti;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.io.Serializable;

/**
 * 应用停用申请表(ApplicationStopRequest)实体类
 *
 * @author makejava
 * @since 2019-08-23 13:58:19
 */
@Data
@ToString
public class ApplicationStopRequest implements Serializable {
    private static final long serialVersionUID = 355228199099926072L;
    
    /**
     * 应用停用主键
     */
    @ApiModelProperty(value = "应用停用主键", dataType = "String")    
    private String id;
    
    /**
     * 申请单位/部门
     */
    @ApiModelProperty(value = "申请单位/部门", dataType = "String")    
    private String deptid;
    
    /**
     * 申请时间
     */
    @ApiModelProperty(value = "申请时间", dataType = "Date")    
    private Date requesttime;
    
    /**
     * 停用应用名称
     */
    @ApiModelProperty(value = "停用应用名称", dataType = "String")    
    private String agentname;
    
    /**
     * 应用停用期限(0短期，1长期停用)
     */
    @ApiModelProperty(value = "应用停用期限(0短期，1长期停用)", dataType = "int")
    private Integer agentStopPeriod;
    
    /**
     * 停用开始时间
     */
    @ApiModelProperty(value = "停用开始时间", dataType = "Date")    
    private Date stopbegintime;
    
    /**
     * 停用结束时间
     */
    @ApiModelProperty(value = "停用结束时间", dataType = "Date")    
    private Date stopendtime;
    
    /**
     * 应用停用原因
     */
    @ApiModelProperty(value = "应用停用原因", dataType = "String")    
    private String agentstopreason;
    
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注", dataType = "String")    
    private String remark;
    
    /**
     * 应用停用申请附件url
     */
    @ApiModelProperty(value = "应用停用申请附件url", dataType = "String")    
    private String agentStopRequestFileurl;
    
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", dataType = "Date")    
    private Date createtime;
    



}