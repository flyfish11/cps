package com.cloud.model.platformappmanager.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @Classname UserSyncResultVO
 * @Description <h1>用户同步结果集视图对象</h1>
 * @Author yulj
 * @Date: 2020/04/25 3:25 下午
 */
@Data
public class UserSyncResultVO {

    /**
     * 应用名称
     */
    private String appName;

    /**
     * 操作类型
     */
    private String action;

    /**
     * 状态
     */
    private String status;

    /**
     * 描述
     */
    private String message;

    /**
     * 操作时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date operateTime;

}
