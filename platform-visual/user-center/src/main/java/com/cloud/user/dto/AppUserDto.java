package com.cloud.user.dto;

import com.cloud.model.user.SysUser;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * @Classname AppUserDto
 * @Description 用户数据传输对象
 * @Author yulj
 * @Date: 2019/07/30 09:19
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AppUserDto extends SysUser implements Serializable {

    /**
     * 性别值
     */
    private String sexValue;

    /**
     * 部门名称
     */
    private String deptName;

}
