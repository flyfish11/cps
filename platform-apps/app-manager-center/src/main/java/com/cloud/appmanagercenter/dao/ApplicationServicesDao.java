package com.cloud.appmanagercenter.dao;

import com.cloud.model.appmanagercenter.ApplicationServices;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Classname ApplicationServicesDao
 * @Description 应用服务关联表dao
 * @Author yulj
 * @Date: 2019/07/01 16:23
 */
@Mapper
public interface ApplicationServicesDao {

    /**
     * 根据主键id删除应用服务关联数据
     *
     * @param id 主键id
     * @return
     */
    int deleteById(Integer id);

    /**
     * 插入工业应用数据
     *
     * @param record 应用服务关联数据对象
     * @return
     */
    int insert(ApplicationServices record);

    /**
     * 插入应用服务关联数据（排除空字段值）
     *
     * @param record 应用服务关联数据对象
     * @return
     */
    int insertSelective(ApplicationServices record);

    /**
     * 根据主键id查询应用服务关联记录
     *
     * @param id 主键id
     * @return
     */
    ApplicationServices selectById(Integer id);

    /**
     * 更新应用服务关联数据（排除空字段值）
     *
     * @param record 应用服务关联数据对象
     * @return
     */
    int updateByIdSelective(ApplicationServices record);

    /**
     * 查询应用服务记录
     *
     * @param params 查询条件Map
     * @return
     */
    List<ApplicationServices> findList(Map<String, Object> params);

    /**
     * 查询应用服务记录条数
     *
     * @param params 查询条件Map
     * @return
     */
    int count(Map<String, Object> params);

    /**
     * 根据应用id删除应用服务关联数据
     *
     * @param applicationId
     * @return
     */
    int deleteByApplicationId(@Param("applicationId") Integer applicationId);


}