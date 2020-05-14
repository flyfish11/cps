package com.cloud.gateway.dao;

import com.cloud.model.gateway.GatewayApiDefine;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Classname GatewayApiDefineDao
 * @Description 路由规则Dao层
 * @Author yulj
 * @Date: 2019/07/16 21:34
 */
@Mapper
public interface GatewayApiDefineDao {

    int deleteById(String id);

    int insert(GatewayApiDefine record);

    int insertSelective(GatewayApiDefine record);

    GatewayApiDefine selectById(String id);

    GatewayApiDefine selectByServiceId(String serviceId);

    int updateByIdSelective(GatewayApiDefine record);

    int updateById(GatewayApiDefine record);

    List<GatewayApiDefine> selectAllByEnabled(@Param("enabled") Boolean enabled);

    int count(Map<String, Object> params);

    List<GatewayApiDefine> pageList(Map<String, Object> params);

    List<GatewayApiDefine> selectAllByGatewayApiDefineInfo(Map<String, Object> params);


}