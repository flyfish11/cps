package com.cloud.model.platformuser;

import com.cloud.model.annotations.Display;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 部门数据实体
 *
 * @author yulj
 * @create: 2019/05/06 14:20
 */

@Data
@ApiModel(description = " 部门数据实体")
public class SysDept implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id", dataType = "int")
    private Integer id;

    /**
     * 父部门id
     */
    @ApiModelProperty(value = "父部门id", dataType = "int")
    private Integer pid;

    /**
     * 全称
     */
    @ApiModelProperty(value = "全称", dataType = "String")
    private String fullname;

    /**
     * 部门简称
     *
     * @return
     */
    @ApiModelProperty(value = "部门简称", dataType = "String")
    private String sdeptname;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序", dataType = "int")
    private Integer num;

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

    @Display
    protected List<SysDept> children = new ArrayList<>();

}
