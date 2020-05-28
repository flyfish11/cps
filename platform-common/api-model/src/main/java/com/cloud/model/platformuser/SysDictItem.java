package com.cloud.model.platformuser;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.io.Serializable;

/**
 * 字典项(SysDictItem)实体类
 *
 * @author makejava
 * @since 2019-09-18 18:06:11
 */
@Data
@ToString
public class SysDictItem implements Serializable {
    private static final long serialVersionUID = -21553716551026046L;
    
    /**
     * 编号
     */
    @ApiModelProperty(value = "编号", dataType = "String")    
    private String id;
    
        
    private String dictId;
    
    /**
     * 数据值
     */
    @ApiModelProperty(value = "数据值", dataType = "String")    
    private String value;
    
    /**
     * 标签名
     */
    @ApiModelProperty(value = "标签名", dataType = "String")    
    private String label;
    
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
     * 排序（升序）
     */
    @ApiModelProperty(value = "排序（升序）", dataType = "Integer")    
    private Integer sort;
    
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", dataType = "Date")    
    private Date createTime;
    
    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间", dataType = "Date")    
    private Date updateTime;
    
    /**
     * 备注信息
     */
    @ApiModelProperty(value = "备注信息", dataType = "String")    
    private String remarks;
    
    /**
     * 删除标记
     */
    @ApiModelProperty(value = "删除标记", dataType = "String")    
    private String delFlag;
    

}