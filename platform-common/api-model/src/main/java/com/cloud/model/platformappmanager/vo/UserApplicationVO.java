package com.cloud.model.platformappmanager.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @Classname UserApplicationVO
 * @Description <h1>用户应用视图对象</h1>
 * @Author yulj
 * @Date: 2020/04/24 4:32 下午
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "用户应用视图对象")
public class UserApplicationVO implements Serializable {

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
     * 应用名称
     */
    @ApiModelProperty(value = "应用名称", dataType = "String")
    private String appName;

    /**
     * logo地址
     */
    @ApiModelProperty(value = "logo地址", dataType = "String")
    private String logoUrl;

    /**
     * 访问地址
     */
    @ApiModelProperty(value = "访问地址", dataType = "String")
    private String indexUrl;

    /**
     * 最近访问时间
     */
    @ApiModelProperty(value = "最近访问时间", dataType = "Date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date recentVisitTime;

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

    @ApiModelProperty(value = "分组名称", dataType = "String")
    private String groupName;

}
