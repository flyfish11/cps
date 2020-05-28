package com.cloud.platformuser.service.impl;

import com.cloud.common.enums.YesOrNoEnum;
import com.cloud.common.utils.PageUtil;
import com.cloud.common.utils.UUIDUtils;
import com.cloud.model.common.Page;
import com.cloud.model.platformuser.SysDictItem;
import com.cloud.model.platformuser.vo.SysDictItemVO;
import com.cloud.platformuser.dao.SysDictItemDao;
import com.cloud.platformuser.service.SysDictItemService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 字典项(SysDictItem)表服务实现类
 *
 * @author makejava
 * @since 2019-09-18 16:31:39
 */
@Service("sysDictItemService")
public class SysDictItemServiceImpl implements SysDictItemService {
    @Resource
    private SysDictItemDao sysDictItemDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public SysDictItem queryById(String id) {
        return this.sysDictItemDao.queryById(id);
    }

    @Override
    public List<SysDictItem> queryByDictId(String dictId) {
        return sysDictItemDao.queryByDictId(dictId);
    }

    /**
     * 查询所有
     *
     * @param params 查询参数（包括分页<start ,length>）
     * @return 返回对象集合
     */
    @Override
    public Page<SysDictItem> queryAllByLimit(Map<String, Object> params) {

        long total = this.sysDictItemDao.count(params);
        List<SysDictItem> list = Collections.emptyList();
        if (total > 0) {
            PageUtil.pageUtil(params);
            list = this.sysDictItemDao.queryAllByLimit(params);
        }
        return new Page<>(total, list);
    }

    /**
     * 新增数据
     *
     * @param sysDictItem 实例对象
     * @return 实例对象
     */
    @Override
    @CacheEvict(value = "dictItemData", allEntries = true, beforeInvocation = true)
    public SysDictItem insert(SysDictItem sysDictItem) {
        if (StringUtils.isEmpty(sysDictItem.getId())) {
            sysDictItem.setId(UUIDUtils.getUUID());
        }
        this.sysDictItemDao.insert(sysDictItem);
        return sysDictItem;
    }

    /**
     * 修改数据
     *
     * @param sysDictItem 实例对象
     * @return 实例对象
     */
    @Override
    @CacheEvict(value = "dictItemData", allEntries = true, beforeInvocation = true)
    public SysDictItem update(SysDictItem sysDictItem) {
        this.sysDictItemDao.update(sysDictItem);
        return this.queryById(sysDictItem.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    @CacheEvict(value = "dictItemData", allEntries = true, beforeInvocation = true)
    public boolean deleteById(String id) {
        return this.sysDictItemDao.deleteById(id) > 0;
    }

    /**
     * 通过字典表 type 查询字典相
     *
     * @param dictType
     * @return
     */
    @Override
    public List<SysDictItem> queryByDictType(String dictType) {
        return this.sysDictItemDao.queryByDictType(dictType);
    }

    @Override
    @Cacheable(value = "dictItemData", key = "targetClass + methodName +#p0", unless = "#result == null")
    public Map<String, String> findDictSelectByType(String dictType) {
        List<SysDictItemVO> allByTypeAndDelFlag = this.sysDictItemDao.findAllByTypeAndDelFlag(
                dictType, YesOrNoEnum.NO.getType().toString());
        Map<String, String> dictMap = allByTypeAndDelFlag.stream()
                .collect(Collectors.toMap(SysDictItemVO::getValue, SysDictItemVO::getLabel));
        return dictMap;
    }

}