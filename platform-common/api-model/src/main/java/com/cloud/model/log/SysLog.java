package com.cloud.model.log;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @Classname SysLog
 * @Description <h1>系统日志表实体</h1>
 * @Author yulj
 * @Date: 2020/04/14 4:54 下午
 */
@ApiModel(value = "系统日志表实体")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SysLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @ApiModelProperty(value = "主键ID", dataType = "String")
    private Long id;

    /**
     * 是否成功(0成功,1失败)
     */
    @ApiModelProperty(value = "是否成功(0成功,1失败)", dataType = "Boolean")
    private Boolean type;

    /**
     * 日志主题
     */
    @ApiModelProperty(value = "日志主题", dataType = "String")
    private String title;

    /**
     * 服务ID
     */
    @ApiModelProperty(value = "服务ID", dataType = "String")
    private String serviceId;

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
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "更新时间", dataType = "Date")
    private Date updateTime;

    /**
     * 客户端IP
     */
    @ApiModelProperty(value = "客户端IP", dataType = "String")
    private String remoteAddr;

    /**
     * 客户端类型
     */
    @ApiModelProperty(value = "客户端类型", dataType = "String")
    private String userAgent;

    /**
     * 请求地址
     */
    @ApiModelProperty(value = "请求地址", dataType = "String")
    private String requestUri;

    /**
     * 请求方式
     */
    @ApiModelProperty(value = "请求方式", dataType = "String")
    private String method;

    /**
     * 请求参数
     */
    @ApiModelProperty(value = "请求参数", dataType = "String")
    private String params;

    /**
     * 执行时间
     */
    @ApiModelProperty(value = "执行时间", dataType = "String")
    private String executeTime;

    /**
     * 删除标记(0未删除,1已删除)
     */
    @ApiModelProperty(value = "删除标记(0未删除,1已删除)", dataType = "Boolean")
    private Boolean deleteFlag;

    /**
     * 异常信息
     */
    @ApiModelProperty(value = "异常信息", dataType = "String")
    private String exception;

}