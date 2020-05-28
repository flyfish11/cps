package com.cloud.model.platformappmanager.vo;

import lombok.Data;

import java.util.List;

/**
 * @Classname MyAppVO
 * @Description <h1>我的应用视图对象</h1>
 * @Author yulj
 * @Date: 2020/04/24 8:29 下午
 */
@Data
public class MyAppVO {

    /**
     * 分组名称
     */
    private String groupName;

    private List<UserApplicationVO> children;

}
