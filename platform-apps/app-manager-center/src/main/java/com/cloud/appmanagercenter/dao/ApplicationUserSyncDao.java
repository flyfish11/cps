package com.cloud.appmanagercenter.dao;

import com.cloud.model.appmanagercenter.ApplicationUserSync;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @Classname ApplicationUserSyncDao
 * @Description <h1>应用用户同步表数据处理层</h1>
 * @Author yulj
 * @Date: 2020/04/26 12:26 上午
 */
@Mapper
public interface ApplicationUserSyncDao {

    /**
     * <h2>根据应用用户同步id删除应用用户同步记录</h2>
     *
     * @param id 应用用户同步id
     * @return
     */
    int deleteById(Integer id);

    /**
     * <h2>新增应用用户同步记录</h2>
     *
     * @param record 应用用户同步记录对象
     * @return
     */
    int insert(ApplicationUserSync record);

    /**
     * <h2>根据动态属性新增应用用户同步记录</h2>
     *
     * @param record 应用用户同步记录对象
     * @return
     */
    int insertSelective(ApplicationUserSync record);

    /**
     * <h2>根据应用id查询应用用户同步记录</h2>
     *
     * @param id 应用用户同步id
     * @return
     */
    ApplicationUserSync selectById(Integer id);

    /**
     * <h2>根据动态属性更新应用用户同步记录</h2>
     *
     * @param record 应用用户同步记录对象
     * @return
     */
    int updateByIdSelective(ApplicationUserSync record);

    /**
     * <h2>更新应用用户同步记录</h2>
     *
     * @param record 应用用户同步记录对象
     * @return
     */
    int updateById(ApplicationUserSync record);

    /**
     * <h2>批量插入应用用户同步记录</h2>
     *
     * @param list 应用用户同步记录对象集合
     * @return
     */
    int batchInsert(@Param("list") List<ApplicationUserSync> list);

    /**
     * <h2>批量更新应用用户同步记录</h2>
     *
     * @param list 应用用户同步记录对象集合
     * @return
     */
    int updateBatchSelective(List<ApplicationUserSync> list);

    /**
     * 根据用户id删除应用用户同步数据
     *
     * @param userId 用户id
     * @return
     */
    int deleteByUserId(@Param("userId") Integer userId);

    /**
     * <h2>根据条件查询应用用户同步记录条数</h2>
     *
     * @param params 查询参数
     * @return
     */
    int count(Map<String, Object> params);

    /**
     * <h2>根据条件查询应用用户同步记录</h2>
     *
     * @param params 查询参数
     * @return
     */
    List<ApplicationUserSync> findList(Map<String, Object> params);

    List<ApplicationUserSync> selectAllByIdIn(@Param("idCollection") Collection<Integer> idCollection);

    List<Integer> findAppIdByUserId(@Param("userId") Integer userId);

    int deleteByIdIn(@Param("idCollection") Collection<Integer> idCollection);

}