package com.cloud.appmanagercenter.dao;

import com.cloud.model.appmanagercenter.PlatformGroup;
import com.cloud.model.appmanagercenter.vo.PlatformGroupVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Classname PlatformGroupDao
 * @Description <h1>平台类型表数据访问层</h1>
 * @Author yulj
 * @Date: 2020/04/22 2:45 下午
 */
@Mapper
public interface PlatformGroupDao {

    /**
     * <h2>根据平台类型id删除平台类型记录</h2>
     *
     * @param id 平台类型id
     * @return
     */
    int deleteById(Integer id);

    /**
     * <h2>新增平台类型记录</h2>
     *
     * @param record 平台类型记录对象
     * @return
     */
    int insert(PlatformGroup record);

    /**
     * <h2>根据动态属性新增平台类型记录</h2>
     *
     * @param record 平台类型记录对象
     * @return
     */
    int insertSelective(PlatformGroup record);

    /**
     * <h2>根据平台类型id查询平台类型记录</h2>
     *
     * @param id 平台类型id
     * @return
     */
    PlatformGroup selectById(Integer id);

    /**
     * <h2>根据动态属性更新平台类型记录</h2>
     *
     * @param record 平台类型记录对象
     * @return
     */
    int updateByIdSelective(PlatformGroup record);

    /**
     * <h2>更新平台类型记录</h2>
     *
     * @param record 平台类型记录对象
     * @return
     */
    int updateById(PlatformGroup record);

    /**
     * <h2>批量插入平台类型记录</h2>
     *
     * @param list 平台类型记录对象集合
     * @return
     */
    int batchInsert(@Param("list") List<PlatformGroup> list);

    /**
     * <h2>根据条件查询平台类型记录条数</h2>
     *
     * @param params 查询参数
     * @return
     */
    int count(Map<String, Object> params);

    /**
     * <h2>根据条件查询平台类型记录</h2>
     *
     * @param params 查询参数
     * @return
     */
    List<PlatformGroup> findList(Map<String, Object> params);

    /**
     * <h2>根据分组名称查询平台类型记录</h2>
     *
     * @param groupName 分组名称
     * @param delFlag   删除标记
     * @return
     */
    PlatformGroup selectOneByGroupNameAndDelFlag(@Param("groupName") String groupName,
                                                 @Param("delFlag") Integer delFlag);

    /**
     * <h2>根据分组名称查询平台类型记录(不等于平台类型id)</h2>
     *
     * @param groupName 分组名称
     * @param delFlag   删除标记
     * @param id        平台类型id
     * @return
     */
    PlatformGroup selectOneByGroupNameAndDelFlagAndIdNot(@Param("groupName") String groupName,
                                                         @Param("delFlag") Integer delFlag,
                                                         @Param("id") Integer id);

    /**
     * 查询所有平台类型id及名称
     *
     * @param delFlag 删除标记
     * @return
     */
    List<PlatformGroupVO> selectIdAndGroupName(@Param("delFlag") Integer delFlag,
                                               @Param("enable") Integer enable);

}