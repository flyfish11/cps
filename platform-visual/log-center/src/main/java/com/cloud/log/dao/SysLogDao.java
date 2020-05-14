package com.cloud.log.dao;

import com.cloud.model.log.SysLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Classname SysLogDao
 * @Description <h1>系统日志表数据处理层</h1>
 * @Author yulj
 * @Date: 2020/04/14 4:54 下午
 */
@Mapper
public interface SysLogDao {

    /**
     * <h2>根据系统日志id删除系统日志记录</h2>
     *
     * @param id
     * @return
     */
    int deleteById(Long id);

    /**
     * <h2>新增系统日志记录</h2>
     *
     * @param record 系统日志记录对象
     * @return
     */
    int insert(SysLog record);

    /**
     * <h2>根据动态属性新增系统日志记录</h2>
     *
     * @param record 系统日志记录对象
     * @return
     */
    int insertSelective(SysLog record);

    /**
     * <h2>根据系统日志id查询系统日志记录</h2>
     *
     * @param id 系统日志id
     * @return
     */
    SysLog selectById(Long id);

    /**
     * <h2>根据动态属性更新系统日志记录</h2>
     *
     * @param record 系统日志记录对象
     * @return
     */
    int updateByIdSelective(SysLog record);

    /**
     * <h2>更新系统日志记录</h2>
     *
     * @param record 系统日志记录对象
     * @return
     */
    int updateById(SysLog record);

    /**
     * <h2>批量插入系统日志记录</h2>
     *
     * @param list 系统日志记录对象集合
     * @return
     */
    int batchInsert(@Param("list") List<SysLog> list);

    /**
     * <h2>根据条件查询系统日志记录条数</h2>
     *
     * @param params 查询参数
     * @return
     */
    int count(Map<String, Object> params);

    /**
     * <h2>根据条件查询系统日志记录</h2>
     *
     * @param params 查询参数
     * @return
     */
    List<SysLog> findList(Map<String, Object> params);

}