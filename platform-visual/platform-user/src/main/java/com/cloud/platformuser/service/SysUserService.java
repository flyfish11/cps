package com.cloud.platformuser.service;

import com.cloud.common.utils.R;
import com.cloud.model.common.Page;
import com.cloud.model.user.LoginAppUser;
import com.cloud.model.platformuser.SysRole;
import com.cloud.model.platformuser.SysUser;
import com.cloud.model.platformuser.bo.SysUserAddBO;
import com.cloud.model.platformuser.bo.SysUserUpdateBO;
import com.cloud.model.platformuser.bo.UpdatePwdBO;
import com.cloud.platformuser.dto.AppUserDto;
import com.cloud.platformuser.exception.UserCenterException;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface SysUserService {

    /**
     * 添加用户
     *
     * @param userAddBO
     */
    R addSysUser(SysUserAddBO userAddBO) throws UserCenterException;

    /**
     * 修改用户
     *
     * @param sysUserUpdateBO
     * @return
     */
    R updateSysUser(SysUserUpdateBO sysUserUpdateBO);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    R deleteById(Integer id);

    /**
     * 通过账户查询用户
     *
     * @param account
     * @return
     */
    LoginAppUser findByAccount(String account);

    /**
     * 根据id查询用户
     *
     * @param id
     * @return
     */
    R findById(Integer id);

    /**
     * 为用户分配角色
     *
     * @param id
     * @param roleIds
     */
    void setRoleToUser(Integer id, Set<String> roleIds);

    /**
     * 修改用户密码
     *
     * @param updatePwdBO 修改用户密码业务对象
     * @return
     */
    R updatePassword(UpdatePwdBO updatePwdBO);

    /**
     * 用户重置密码
     *
     * @param id 用户id
     * @return
     */
    R resetPassword(Integer id);

    /**
     * 查询所有用户
     *
     * @param params
     * @return
     */
    Page<AppUserDto> findUsers(Map<String, Object> params);

    /**
     * 通过用户id查询用户角色
     *
     * @param userId
     * @return
     */
    Set<SysRole> findRolesByUserId(Integer userId);

    /**
     * getUserIdInRoleIds
     *
     * @param roleIds
     * @return
     */
    List<Integer> getUserIdInRoleIds(String... roleIds);

    /**
     * 获取用户信息（指定应用）
     *
     * @param applicationId
     * @return
     */
    LoginAppUser getUserInfoWithApp(String applicationId);

    /**
     * @param userIds
     * @return
     */
    String findNamesInIdRange(Integer... userIds);

    /**
     * <h2>根据用户id数组获取用户信息</h2>
     *
     * @param userIds
     * @returnr
     */
    List<SysUser> getUserInfoInUserIds(Integer... userIds);

    /**
     * 缓存所有用户数据
     *
     * @return
     */
    int cacheAllUserData();

    /**
     * 更新用户应用注册数
     *
     * @param sysUserUpdateBO
     * @return
     */
    R updateProductRegisterCount(SysUserUpdateBO sysUserUpdateBO);

    /**
     * 根据用户id查询用户数据
     *
     * @param userId 用户id
     * @return
     */
    SysUser queryByUserId(Integer userId);

}
