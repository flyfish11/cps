package com.cloud.model.platformuser.bo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Classname SysMenuAddBO
 * @Description <h1>菜单表更新业务对象</h1>
 * @Author yulj
 * @Date: 2020/06/24 4:24 下午
 */
@Data
@ApiModel(value = "菜单表更新业务对象")
public class SysMenuUpdateBO implements Serializable {

    /**
     * 菜单ID
     */
    @ApiModelProperty("菜单ID")
    @NotBlank(message = "菜单ID不能为空")
    private String id;

    /**
     * 菜单类型（M目录 C菜单 F按钮）
     */
    @ApiModelProperty(value = "菜单类型（C菜单 F按钮）", dataType = "String", required = true)
    private String menuType;

    /**
     * 菜单名称
     */
    @ApiModelProperty(value = "菜单名称", dataType = "String", required = true)
    private String title;

    /**
     * 父菜单ID
     */
    @ApiModelProperty(value = "父菜单ID", dataType = "String")
    @JsonProperty("pId")
    private String pId;

    /**
     * 排序值
     */
    @ApiModelProperty(value = "排序值", dataType = "int", required = true)
    private Integer orderNum;

    /**
     * 权限标识
     */
    @ApiModelProperty(value = "权限标识", dataType = "String")
    private String perms;

    /**
     * 请求地址(ifream)
     */
    @ApiModelProperty(value = "请求地址(ifream)", dataType = "String")
    private String url;

    /**
     * 前端路由
     */
    @ApiModelProperty(value = "前端路由", dataType = "String")
    private String path;

    /**
     * 图标
     */
    @ApiModelProperty(value = "图标", dataType = "String")
    private String icon;

}
