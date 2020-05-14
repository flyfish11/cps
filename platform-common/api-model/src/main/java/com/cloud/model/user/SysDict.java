package com.cloud.model.user;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.io.Serializable;

/**
 * 字典表(SysDict)实体类
 *
 * @author makejava
 * @since 2019-09-18 18:06:09
 */
@Data
@ToString
public class SysDict implements Serializable {
    private static final long serialVersionUID = -42110077228685739L;
    
    /**
     * 编号
     */
    @ApiModelProperty(value = "编号", dataType = "String")    
    private String id;
    
    /**
     * 类型
     */
    @ApiModelProperty(value = "类型", dataType = "String")    
    private String type;
    
    /**
     * 描述
     */
    @ApiModelProperty(value = "描述", dataType = "String")    
    private String description;
    
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", dataType = "Date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    
    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间", dataType = "Date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
    
    /**
     * 备注信息
     */
    @ApiModelProperty(value = "备注信息", dataType = "String")    
    private String remarks;
    
    /**
     * 0-否|1-是
     */
    @ApiModelProperty(value = "0-否|1-是", dataType = "String")    
    private String system;
    
    /**
     * 删除标记
     */
    @ApiModelProperty(value = "删除标记", dataType = "String")    
    private String delFlag;
    

}