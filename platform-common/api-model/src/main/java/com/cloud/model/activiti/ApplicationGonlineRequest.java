package com.cloud.model.activiti;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.io.Serializable;

/**
 * 应用上线申请(ApplicationGonlineRequest)实体类
 *
 * @author makejava
 * @since 2019-08-23 13:57:59
 */
@Data
@ToString
public class ApplicationGonlineRequest implements Serializable {
    private static final long serialVersionUID = 853085115828441467L;
    
    /**
     * 应用上线申请主键
     */
    @ApiModelProperty(value = "应用上线申请主键", dataType = "String")    
    private String id;
    
    /**
     * 申请部门
     */
    @ApiModelProperty(value = "申请部门", dataType = "String")    
    private String deptid;
    
    /**
     * 申请时间
     */
    @ApiModelProperty(value = "申请时间", dataType = "Date")    
    private Date requesttime;
    
    /**
     * 上线应用名称
     */
    @ApiModelProperty(value = "上线应用名称", dataType = "String")    
    private String agentname;
    
    /**
     * 上线运行时间
     */
    @ApiModelProperty(value = "上线运行时间", dataType = "Date")    
    private Date runningtime;
    
    /**
     * 试运行结论
     */
    @ApiModelProperty(value = "试运行结论", dataType = "String")    
    private String testrunconclusion;
    
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", dataType = "Date")    
    private Date createtime;
    
    /**
     * 应用上线申请附件url
     */
    @ApiModelProperty(value = "应用上线申请附件url", dataType = "String")    
    private String agentGonlineRequestFileurl;
    



}