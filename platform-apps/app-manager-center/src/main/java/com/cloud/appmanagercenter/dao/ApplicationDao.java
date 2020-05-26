package com.cloud.appmanagercenter.dao;
import java.util.Collection;

import com.cloud.model.appmanagercenter.Application;
import com.cloud.model.appmanagercenter.vo.ApplicationUserSyncVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Classname ApplicationDao
 * @Description <h1>应用表数据访问层</h1>
 * @Author yulj
 * @Date: 2020/04/22 8:22 下午
 */
@Mapper
public interface ApplicationDao {

    /**
     * <h2>根据应用id删除应用记录</h2>
     *
     * @param id 应用id
     * @return
     */
    int deleteById(Integer id);

    /**
     * <h2>新增应用记录</h2>
     *
     * @param record 应用记录对象
     * @return
     */
    int insert(Application record);

    /**
     * <h2>根据动态属性新增应用记录</h2>
     *
     * @param record 应用记录对象
     * @return
     */
    int insertSelective(Application record);

    /**
     * <h2>根据应用id查询应用记录</h2>
     *
     * @param id 应用id
     * @return
     */
    Application selectById(Integer id);

    /**
     * <h2>根据动态属性更新应用记录</h2>
     *
     * @param record 应用记录对象
     * @return
     */
    int updateByIdSelective(Application record);

    /**
     * <h2>更新应用记录</h2>
     *
     * @param record 应用记录对象
     * @return
     */
    int updateById(Application record);

    /**
     * <h2>批量插入应用记录</h2>
     *
     * @param list 应用记录对象集合
     * @return
     */
    int batchInsert(@Param("list") List<Application> list);

    /**
     * <h2>根据条件查询应用记录条数</h2>
     *
     * @param params 查询参数
     * @return
     */
    int count(Map<String, Object> params);

    /**
     * <h2>根据条件查询应用记录</h2>
     *
     * @param params 查询参数
     * @return
     */
    List<Application> findList(Map<String, Object> params);

    /**
     * <h2>根据应用名称查询应用记录</h2>
     *
     * @param name    应用名称
     * @param delFlag 删除标记
     * @return
     */
    Application selectOneByNameAndDelFlag(@Param("name") String name, @Param("delFlag") Integer delFlag);

    /**
     * <h2>根据应用名称查询平台类型记录(不等于应用id)</h2>
     *
     * @param name    应用名称
     * @param delFlag 删除标记
     * @param id      应用id
     * @return
     */
    Application selectOneByNameAndDelFlagAndIdNot(@Param("name") String name,
                                                  @Param("delFlag") Integer delFlag,
                                                  @Param("id") Integer id);

    /**
     * <h2>根据应用简称查询应用记录</h2>
     *
     * @param shortName 应用简称
     * @param delFlag   删除标记
     * @return
     */
    Application selectOneByShortNameAndDelFlag(@Param("shortName") String shortName, @Param("delFlag") Integer delFlag);

    /**
     * <h2>根据应用简称查询平台类型记录(不等于应用id)</h2>
     *
     * @param shortName 应用简称
     * @param delFlag   删除标记
     * @param id        应用id
     * @return
     */
    Application selectOneByShortNameAndDelFlagAndIdNot(@Param("shortName") String shortName,
                                                       @Param("delFlag") Integer delFlag,
                                                       @Param("id") Integer id);

    /**
     * <h2>分页查询所有应用id和应用名称</h2>
     *
     * @param params 查询条件
     * @return
     */
    List<ApplicationUserSyncVO> findAppIdAndName(Map<String, Object> params);

    List<Application> findAllByidInAndDelFlag(@Param("idCollection")Collection<Integer> idCollection,@Param("delFlag")Integer delFlag);

    List<Application> selectAllByAppTypeInAndDelFlag(@Param("appTypeCollection")Collection<Integer> appTypeCollection,@Param("delFlag")Integer delFlag);


}