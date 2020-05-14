package com.cloud.model.activiti;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.io.Serializable;

/**
 * 应用网络映射申请(ApplicationNetworkMappedRequest)实体类
 *
 * @author makejava
 * @since 2019-08-23 13:58:10
 */
@Data
@ToString
public class ApplicationNetworkMappedRequest implements Serializable {
    private static final long serialVersionUID = 712812160259799644L;
    
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键", dataType = "String")    
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
     * 移动应用名称
     */
    @ApiModelProperty(value = "移动应用名称", dataType = "String")    
    private String agentname;
    
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", dataType = "Date")    
    private Date createtime;
    
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注", dataType = "String")    
    private String remark;
    
    /**
     * 应用网络接口申请附件url
     */
    @ApiModelProperty(value = "应用网络接口申请附件url", dataType = "String")    
    private String agentMappedRequestFileurl;
    



}