package com.cloud.model.user.vo;

import com.cloud.model.user.SysUser;
import lombok.Data;

/**
 * @Classname SysUserVo
 * @Description <h1>用户视图对象</h1>
 * @Author yulj
 * @Date: 2020/5/26 2:22 下午
 */
@Data
public class SysUserVo extends SysUser {

    /**
     * 是否提醒更新密码
     */
    private Boolean remindUpdatePassword = Boolean.FALSE;

    /**
     * 距离上次修改密码天数
     */
    private Long lastUpdatedPasswordDays = 0L;

}
