package com.cloud.user.dao;

import com.cloud.model.user.SysLdapConfig;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Classname SysLdapConfigDao
 * @Description <h1>LDAP配置表数据访问层</h1>
 * @Author yulj
 * @Date: 2020/04/27 1:51 下午
 */
@Mapper
public interface SysLdapConfigDao {

    /**
     * <h2>根据LDAP配置id删除LDAP配置记录</h2>
     *
     * @param id LDAP配置id
     * @return
     */
    int deleteById(Integer id);

    /**
     * <h2>新增LDAP配置记录</h2>
     *
     * @param record LDAP配置记录对象
     * @return
     */
    int insert(SysLdapConfig record);

    /**
     * <h2>根据动态属性新增LDAP配置记录</h2>
     *
     * @param record LDAP配置记录对象
     * @return
     */
    int insertSelective(SysLdapConfig record);

    /**
     * <h2>根据LDAP配置id查询LDAP配置记录</h2>
     *
     * @param id LDAP配置id
     * @return
     */
    SysLdapConfig selectById(Integer id);

    /**
     * <h2>根据动态属性更新LDAP配置记录</h2>
     *
     * @param record LDAP配置记录对象
     * @return
     */
    int updateByIdSelective(SysLdapConfig record);

    /**
     * <h2>更新LDAP配置记录</h2>
     *
     * @param record LDAP配置记录对象
     * @return
     */
    int updateById(SysLdapConfig record);

}