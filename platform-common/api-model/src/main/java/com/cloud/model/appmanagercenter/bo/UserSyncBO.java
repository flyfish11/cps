package com.cloud.model.appmanagercenter.bo;

import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Data;

/**
 * @Classname UserSyncBO
 * @Description <h1>用户同步业务对象</h1>
 * @Author yulj
 * @Date: 2020/04/25 2:26 下午
 */
@Data
@Builder
@ApiModel(value = "用户同步业务对象")
public class UserSyncBO {

    private String account;

    private String name;

    private String phone;

}
