package com.cloud.appmanagercenter.service;

import com.cloud.common.utils.R;
import com.cloud.model.appmanagercenter.BannerConfig;
import com.cloud.model.appmanagercenter.bo.BannerConfigAddBO;
import com.cloud.model.appmanagercenter.bo.BannerConfigUpdateBO;

import java.util.List;

/**
 * @Classname BannerConfigService
 * @Description <h1>Banner配置表服务层</h1>
 * @Author yulj
 * @Date: 2020/04/23 9:14 下午
 */
public interface BannerConfigService {

    /**
     * 查询banner配置列表
     *
     * @return
     */
    List<BannerConfig> list();

    /**
     * <h2>新增banner配置表数据</h2>
     *
     * @param bannerConfigAddBO banner配置新增数据对象
     * @return
     */
    R add(BannerConfigAddBO bannerConfigAddBO);

    /**
     * <h2>修改banner配置表数据</h2>
     *
     * @param bannerConfigUpdateBO banner配置更新数据对象
     * @return
     */
    R update(BannerConfigUpdateBO bannerConfigUpdateBO);

    /**
     * <h2>删除banner配置表数据</h2>
     *
     * @param id banner配置id
     * @return
     */
    R delete(Integer id);

    /**
     * <h2>banner上移</h2>
     *
     * @param id banner配置id
     * @return
     */
    R moveUp(Integer id);

    /**
     * <h2>banner下移</h2>
     *
     * @param id banner配置id
     * @return
     */
    R moveDown(Integer id);
}
