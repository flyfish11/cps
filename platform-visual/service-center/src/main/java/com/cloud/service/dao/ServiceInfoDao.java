package com.cloud.service.dao;
import org.apache.ibatis.annotations.Param;

import com.cloud.model.platformappmanager.ServiceInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @Classname ServiceInfoDao
 * @Description TODO
 * @Author yulj
 * @Date: 2020/5/26 11:51 下午
 */
@Mapper
public interface ServiceInfoDao {

    int deleteByPrimaryKey(Integer id);

    int insert(ServiceInfo record);

    int insertSelective(ServiceInfo record);

    ServiceInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ServiceInfo record);

    int updateByPrimaryKey(ServiceInfo record);

    List<ServiceInfo> findList(Map<String, Object> params);

    int count(Map<String, Object> params);

    ServiceInfo selectOneByNameAndIsDelete(@Param("name")String name,@Param("isDelete")Integer isDelete);

    ServiceInfo selectOneByServicePortAndIsDelete(@Param("servicePort")String servicePort,@Param("isDelete")Integer isDelete);

}