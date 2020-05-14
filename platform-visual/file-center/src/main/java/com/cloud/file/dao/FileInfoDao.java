package com.cloud.file.dao;

import org.apache.ibatis.annotations.Param;

import com.cloud.model.file.FileInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @Classname FileInfoDao
 * @Description 文件管理Dao
 * @Author yulj
 * @Date: 2019/07/31 14:36
 */
@Mapper
public interface FileInfoDao {

    int deleteById(String id);

    int insert(FileInfo fileInfo);

    int insertSelective(FileInfo fileInfo);

    FileInfo selectById(String id);

    int updateByIdSelective(FileInfo record);

    int updateById(FileInfo record);

    int count(Map<String, Object> params);

    List<FileInfo> findList(Map<String, Object> params);

    FileInfo findOneByFileName(@Param("fileName") String fileName);
}