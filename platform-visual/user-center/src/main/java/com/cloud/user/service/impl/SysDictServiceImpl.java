package com.cloud.user.service.impl;

import com.cloud.model.user.SysDict;
import com.cloud.user.dao.SysDictDao;
import com.cloud.user.service.SysDictService;
import com.cloud.common.utils.PageUtil;
import com.cloud.common.utils.UUIDUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import com.cloud.model.common.Page;
import java.util.Map;


/**
 * 字典表(SysDict)表服务实现类
 *
 * @author makejava
 * @since 2019-09-18 16:31:39
 */
@Service("sysDictService")
public class SysDictServiceImpl implements SysDictService {
    @Resource
    private SysDictDao sysDictDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public SysDict queryById(String id) {
        return this.sysDictDao.queryById(id);
    }

    /**
     * 查询所有
     * @param params 查询参数（包括分页<start ,length>）
     * @return 返回对象集合
     */
    @Override
    public Page<SysDict> queryAllByLimit(Map<String, Object> params) {
    
      long total = this.sysDictDao.count(params);
        List<SysDict> list = Collections.emptyList();
        if (total > 0) {
           PageUtil.pageUtil(params);
           list=this.sysDictDao.queryAllByLimit(params);
        }
        return new Page<>(total,list);
    }

    /**
     * 新增数据
     *
     * @param sysDict 实例对象
     * @return 实例对象
     */
    @Override
    public SysDict insert(SysDict sysDict) {
      if (StringUtils.isEmpty(sysDict.getId())){
            sysDict.setId(UUIDUtils.getUUID());
        }
        this.sysDictDao.insert(sysDict);
        return sysDict;
    }

    /**
     * 修改数据
     *
     * @param sysDict 实例对象
     * @return 实例对象
     */
    @Override
    public SysDict update(SysDict sysDict) {
        this.sysDictDao.update(sysDict);
        return this.queryById(sysDict.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String id) {
        return this.sysDictDao.deleteById(id) > 0;
    }
}