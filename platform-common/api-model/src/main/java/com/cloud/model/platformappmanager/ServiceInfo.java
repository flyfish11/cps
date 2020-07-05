package com.cloud.model.platformappmanager;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @Classname ServiceInfo
 * @Description <h1>服务数据表实体</h1>
 * @Author yulj
 * @Date: 2020/5/26 11:51 下午
 */
@ApiModel(value = "服务数据表实体")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceInfo implements Serializable {


    private static final long serialVersionUID = 1L;

    /**
     * 服务表主键
     */
    @ApiModelProperty(value = "服务表主键")
    private Integer id;

    /**
     * 服务名
     */
    @ApiModelProperty(value = "服务名")
    private String name;

    /**
     * 服务简称
     */
    @ApiModelProperty(value = "服务简称")
    private String shortName;

    /**
     * 服务描述
     */
    @ApiModelProperty(value = "服务描述")
    private String description;

    /**
     * 服务识别id
     */
    @ApiModelProperty(value = "服务识别id")
    private String serviceUnicId;

    /**
     * 服务所属应用外键id
     */
    @ApiModelProperty(value = "服务所属应用外键id")
    private Integer belongApplication;

    /**
     * 服务分组外键id
     */
    @ApiModelProperty(value = "服务分组外键id")
    private String serviceGroup;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private String createBy;

    /**
     * 创建角色
     */
    @ApiModelProperty("创建角色")
    private String roleId;


    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 修改人
     */
    @ApiModelProperty(value = "修改人")
    private String updateBy;

    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTimre;

    /**
     * 删除标记
     */
    @ApiModelProperty(value = "删除标记")
    private Integer isDelete;

    /**
     * 构建日志
     */
    @ApiModelProperty(value = "构建日志")
    private byte[] jenkinslog;

    /**
     * 服务jar包地址
     */
    @ApiModelProperty(value = "服务jar包地址")
    private String jarAddr;

    /**
     * 服务类型（jar ：j上传jar包的项目；gitlab ：从gitlab拉取源码的项目）
     */
    @ApiModelProperty(value = "服务类型（jar ：j上传jar包的项目；gitlab ：从gitlab拉取源码的项目）")
    private String type;

    /**
     * 服务端口
     */
    @ApiModelProperty(value = "服务端口")
    private String servicePort;

    /**
     * 服务端口
     */
    @ApiModelProperty("服务端口")
    private String serviceStatus;

}