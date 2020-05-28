package com.cloud.platformappmanager.dao;

import com.cloud.model.platformappmanager.BannerConfig;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Classname BannerConfigDao
 * @Description <h1>banner配置表数据访问层</h1>
 * @Author yulj
 * @Date: 2020/04/23 9:05 下午
 */
@Mapper
public interface BannerConfigDao {

    /**
     * <h2>根据banner配置id删除banner配置记录</h2>
     *
     * @param id banner配置id
     * @return
     */
    int deleteById(Integer id);

    /**
     * <h2>新增banner配置记录</h2>
     *
     * @param record banner配置记录对象
     * @return
     */
    int insert(BannerConfig record);

    /**
     * <h2>根据动态属性新增banner配置记录</h2>
     *
     * @param record banner配置记录对象
     * @return
     */
    int insertSelective(BannerConfig record);

    /**
     * <h2>根据应用id查询banner配置记录</h2>
     *
     * @param id banner配置id
     * @return
     */
    BannerConfig selectById(Integer id);

    /**
     * <h2>根据动态属性更新banner配置记录</h2>
     *
     * @param record banner配置记录对象
     * @return
     */
    int updateByIdSelective(BannerConfig record);

    /**
     * <h2>更新banner配置记录</h2>
     *
     * @param record banner配置记录对象
     * @return
     */
    int updateById(BannerConfig record);

    /**
     * <h2>批量插入banner配置记录</h2>
     *
     * @param list banner配置记录对象集合
     * @return
     */
    int batchInsert(@Param("list") List<BannerConfig> list);

    /**
     * <h2>批量更新banner配置记录</h2>
     *
     * @param list banner配置记录对象集合
     * @return
     */
    int updateBatchSelective(List<BannerConfig> list);

    /**
     * <h2>查询所有插入banner配置记录</h2>
     *
     * @return
     */
    List<BannerConfig> selectAll();

    /**
     * <h2>查询所有插入banner配置记录条数</h2>
     *
     * @return
     */
    Integer queryCount();

    /**
     * <h2>根据标题查询banner配置记录</h2>
     *
     * @param title 标题
     * @return
     */
    BannerConfig selectOneByTitle(@Param("title") String title);

    /**
     * <h2>根据标题查询banner配置记录(不等于banner配置id)</h2>
     *
     * @param title 标题
     * @param id    banner配置id
     * @return
     */
    BannerConfig selectOneByTitleAndIdNot(@Param("title") String title, @Param("id") Integer id);

    /**
     * <h2>根据排序值查询banner配置记录</h2>
     *
     * @param orderNum 排序值
     * @return
     */
    BannerConfig selectOneByOrderNum(@Param("orderNum") Integer orderNum);

}