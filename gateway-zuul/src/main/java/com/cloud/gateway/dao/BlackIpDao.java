package com.cloud.gateway.dao;

import com.cloud.model.gateway.BlackIp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Classname BlackIpDao
 * @Description <h1>黑名单表数据处理层</h1>
 * @Author yulj
 * @Date: 2020/04/17 2:39 下午
 */
@Mapper
public interface BlackIpDao {

    int deleteById(String id);

    int insert(BlackIp record);

    int insertSelective(BlackIp record);

    BlackIp selectById(String id);

    int updateByIdSelective(BlackIp record);

    int updateById(BlackIp record);

    int batchInsert(@Param("list") List<BlackIp> list);

    int count(Map<String, Object> params);

    List<BlackIp> pageList(Map<String, Object> params);

    List<String> selectDistinctIp();

    List<BlackIp> findAll();

}