package com.cloud.common.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @Classname MenuTree
 * @Description <h1>菜单树</h1>
 * @Author yulj
 * @Date: 2019/07/04 17:13
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MenuTree extends MenuTreeNode {

    private String applicationId;

    private String createBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    private String icon;

    private String index;

    private String menuType;

    private Integer orderNum;

    private String perms;

    private String remark;

    private String title;

    private String updateBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    private String url;

    private String visible;

    private String label;

    private String path;

    private String pIdTitle;
}
