package com.cloud.model.appmanagercenter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Classname ApplicationUserSync
 * @Description <h1>应用用户同步表实体</h1>
 * @Author yulj
 * @Date: 2020/04/24 8:05 下午
 */
@ApiModel(value = "应用用户同步表实体")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationUserSync implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id", dataType = "int")
    private Integer id;

    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id", dataType = "int")
    private Integer userId;

    /**
     * 应用id
     */
    @ApiModelProperty(value = "应用id", dataType = "int")
    private Integer appId;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人", dataType = "String")
    private String createBy;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", dataType = "Date")
    private Date createTime;

}