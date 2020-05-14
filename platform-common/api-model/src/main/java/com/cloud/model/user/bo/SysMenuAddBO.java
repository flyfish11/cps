package com.cloud.model.user.bo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Classname SysMenuAddBO
 * @Description <h1>菜单表新增业务对象</h1>
 * @Author yulj
 * @Date: 2020/04/17 5:01 下午
 */
@Data
@ApiModel(value = "菜单表新增业务对象")
public class SysMenuAddBO implements Serializable {

    /**
     * 菜单类型（M目录 C菜单 F按钮）
     */
    @ApiModelProperty(value = "菜单类型（C菜单 F按钮）", dataType = "String", required = true)
    @NotBlank(message = "菜单类型不能为空")
    private String menuType;

    /**
     * 菜单名称
     */
    @ApiModelProperty(value = "菜单名称", dataType = "String", required = true)
    @NotBlank(message = "菜单名称不能为空")
    private String title;

    /**
     * 是否内部系统(0否，1是)
     */
    @ApiModelProperty(value = "是否内部系统(0否，1是)", dataType = "Boolean", required = true)
    @NotNull(message = "是否内部系统不能为空")
    private Boolean menuScope;

    /**
     * 排序值
     */
    @ApiModelProperty(value = "排序值", dataType = "int", required = true)
    @NotNull(message = "排序值不能为空")
    private Integer orderNum;

    /**
     * 父菜单ID
     */
    @ApiModelProperty(value = "父菜单ID", dataType = "String")
    @JsonProperty("pId")
    private String pId;

    /**
     * 前端路由
     */
    @ApiModelProperty(value = "前端路由", dataType = "String")
    private String path;

    /**
     * 请求地址(ifream)
     */
    @ApiModelProperty(value = "请求地址(ifream)", dataType = "String")
    private String url;

    /**
     * 权限标识
     */
    @ApiModelProperty(value = "权限标识", dataType = "String")
    private String perms;

    /**
     * 图标
     */
    @ApiModelProperty(value = "图标", dataType = "String")
    private String icon;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注", dataType = "String")
    private String remark;

}
