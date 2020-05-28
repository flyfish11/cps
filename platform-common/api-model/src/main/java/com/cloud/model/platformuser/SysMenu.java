package com.cloud.model.platformuser;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@ApiModel(description = "菜单数据实体")
public class SysMenu implements Serializable {
    /**
     * 菜单ID
     */
    @ApiModelProperty("菜单ID")
    private String id;

    /**
     * 菜单名称
     */
    @ApiModelProperty("菜单名称")
    private String title;

    /**
     * 父菜单ID
     */
    @ApiModelProperty("父菜单ID")
    @JsonProperty("pId")
    private String pId;

    /**
     * 显示顺序
     */
    @ApiModelProperty("显示顺序")
    private Integer orderNum;

    /**
     * 前端路由
     */
    @ApiModelProperty("前端路由")
    private String path;

    @ApiModelProperty("请求地址")
    private String url;

    /**
     * 菜单类型（M目录 C菜单 F按钮）
     */
    @ApiModelProperty("菜单类型（M目录 C菜单 F按钮）")
    private String menuType;

    /**
     * 菜单状态（0显示 1隐藏）
     */
    @ApiModelProperty("菜单状态（0显示 1隐藏）")
    private String visible;

    /**
     * 权限标识
     */
    @ApiModelProperty("权限标识")
    private String perms;

    /**
     * 菜单图标
     */
    @ApiModelProperty("菜单图标")
    private String icon;

    /**
     * 创建者
     */
    @ApiModelProperty("创建者")
    private String createBy;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 更新者
     */
    @ApiModelProperty("更新者")
    private String updateBy;

    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;

    /**
     * 应用ID
     */
    @ApiModelProperty("应用ID")
    private String applicationId;

    /**
     * 前端路由
     */
    private String index;

    /**
     * 是否内部系统(0否，1是)
     */
    private Boolean menuScope;

    private List<SysMenu> subs;

    private String pIdTitle;
}