package com.cloud.model.gateway;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @Classname BlackIp
 * @Description <h1>黑名单表实体</h1>
 * @Author yulj
 * @Date: 2020/04/17 2:39 下午
 */
@ApiModel(value = "黑名单表实体")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlackIp implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id", dataType = "String")
    private String id;

    /**
     * IP地址
     */
    @ApiModelProperty(value = "IP地址", dataType = "String")
    private String ip;

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

}