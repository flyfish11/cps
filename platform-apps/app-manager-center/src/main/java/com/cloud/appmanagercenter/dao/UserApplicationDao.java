package com.cloud.appmanagercenter.dao;

import com.cloud.model.appmanagercenter.UserApplication;
import com.cloud.model.appmanagercenter.vo.UserApplicationVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Classname UserApplicationDao
 * @Description <h1>用户应用关联表数据访问层</h1>
 * @Author yulj
 * @Date: 2020/04/23 4:37 下午
 */
@Mapper
public interface UserApplicationDao {

    /**
     * <h2>根据用户应用关联id删除用户应用关联记录</h2>
     *
     * @param id 用户应用关联id
     * @return
     */
    int deleteById(Integer id);

    /**
     * <h2>新增用户应用关联记录</h2>
     *
     * @param record 用户应用关联记录对象
     * @return
     */
    int insert(UserApplication record);

    /**
     * <h2>根据动态属性新增用户应用关联记录</h2>
     *
     * @param record 用户应用关联记录对象
     * @return
     */
    int insertSelective(UserApplication record);

    /**
     * <h2>根据应用id查询用户应用关联记录</h2>
     *
     * @param id 用户应用关联id
     * @return
     */
    UserApplication selectById(Integer id);

    /**
     * <h2>根据动态属性更新用户应用关联记录</h2>
     *
     * @param record 用户应用关联记录对象
     * @return
     */
    int updateByIdSelective(UserApplication record);

    /**
     * <h2>更新用户应用关联记录</h2>
     *
     * @param record 用户应用关联记录对象
     * @return
     */
    int updateById(UserApplication record);

    /**
     * <h2>批量插入用户应用关联记录</h2>
     *
     * @param list 用户应用关联记录对象集合
     * @return
     */
    int batchInsert(@Param("list") List<UserApplication> list);

    /**
     * <h1>根据应用id删除用户应用关联数据</h1>
     *
     * @param appId 应用id
     * @return
     */
    int deleteByAppId(@Param("appId") Integer appId);

    /**
     * <h2>查询所有用户应用</h2>
     *
     * @param params 查询条件
     * @return
     */
    List<UserApplicationVO> findAllUserApplication(Map<String, Object> params);

    /**
     * <h2>更新用户应用信息</h2>
     *
     * @param userApplication 用户应用对象
     * @return
     */
    int updateUserApplication(@Param("userApplication") UserApplication userApplication);

    /**
     * <h2>查询用户最近访问应用</h2>
     *
     * @param userId    用户id
     * @param appCounts 最近访问应用数
     * @return
     */
    List<UserApplication> findRecentVisit(@Param("userId") Integer userId, @Param("appCounts") Integer appCounts);

    int updateRecentVisitTimeByUserId(@Param("recentVisitTime") Date recentVisitTime, @Param("userId") Integer userId);

    int updateAppInfoByAppId(@Param("appName") String appName, @Param("logoUrl") String logoUrl,
                             @Param("indexUrl") String indexUrl, @Param("appId") Integer appId);

    int deleteByUserId(@Param("userId")Integer userId);

    UserApplication findOneByAppIdAndUserId(@Param("appId")Integer appId,@Param("userId")Integer userId);

}