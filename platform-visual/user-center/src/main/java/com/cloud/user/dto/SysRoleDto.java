package com.cloud.user.dto;

import com.cloud.model.user.SysRole;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * @Classname SysRoleDto
 * @Description 角色数据传输对象
 * @Author yulj
 * @Date: 2019/07/30 10:23
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class SysRoleDto extends SysRole implements Serializable {

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
}
