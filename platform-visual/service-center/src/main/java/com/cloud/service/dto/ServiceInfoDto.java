package com.cloud.service.dto;

import com.cloud.model.platformappmanager.ServiceInfo;
import lombok.Data;

/**
 * @Classname ServiceInfoDto
 * @Description 服务数据传输对象
 * @Author yulj
 * @Date: 2019/07/25 10:51
 */

@Data
public class ServiceInfoDto extends ServiceInfo {

    /**
     * 服务分组名称
     */
    private String serviceGroupName;
}
