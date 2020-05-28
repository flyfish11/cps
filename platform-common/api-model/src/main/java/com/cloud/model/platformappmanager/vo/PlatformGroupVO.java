package com.cloud.model.platformappmanager.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @Classname PlatformGroupVO
 * @Description <h1>平台类型视图对象</h1>
 * @Author yulj
 * @Date: 2020/04/22 8:02 下午
 */
@Data
@ToString
public class PlatformGroupVO {

    /**
     * 分组id
     */
    @ApiModelProperty(value = "分组id", dataType = "int")
    private Integer groupId;

    /**
     * 分组名称
     */
    @ApiModelProperty(value = "分组名称", dataType = "String")
    private String groupName;

}
