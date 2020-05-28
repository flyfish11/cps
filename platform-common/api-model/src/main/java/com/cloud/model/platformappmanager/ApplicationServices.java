package com.cloud.model.platformappmanager;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @Classname ApplicationServices
 * @Description 工业应用服务关联表实体
 * @Author yulj
 * @Date: 2019/07/01 16:23
 */
@Data
@ToString
@ApiModel(description = "工业应用服务关联表实体")
public class ApplicationServices {

    /**
     * 主键ID
     */
    @ApiModelProperty(value = "主键id", dataType = "int")
    private Integer id;

    /**
     * 应用ID
     */
    @ApiModelProperty(value = "应用id", dataType = "int")
    private Integer applicationId;

    /**
     * 应用名称
     */
    @ApiModelProperty(value = "应用名称", dataType = "String")
    private String applicationName;

    /**
     * 应用别名
     */
    @ApiModelProperty(value = "应用别名（英文名称）", dataType = "String")
    private String applicationAliasName;

    /**
     * 应用版本号
     */
    @ApiModelProperty(value = "应用版本号", dataType = "String")
    private String applicationVersion;

    /**
     * 服务ID
     */
    @ApiModelProperty(value = "服务id", dataType = "int")
    private Integer serviceId;

    /**
     * 服务名称
     */
    @ApiModelProperty(value = "服务名称", dataType = "String")
    private String serviceName;

    /**
     * 服务别名 英文名称
     */
    @ApiModelProperty(value = "服务别名", dataType = "String")
    private String serviceAliasName;

    /**
     * 服务版本号
     */
    @ApiModelProperty(value = "服务版本号", dataType = "String")
    private String serviceVersion;

    /**
     * 删除标记 0未删除，1已删除
     */
    @ApiModelProperty(value = "删除标记", dataType = "int")
    private Integer deleteFlag;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人", dataType = "String")
    private String createdBy;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", dataType = "Date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createdTime;

}