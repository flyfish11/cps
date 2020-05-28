package com.cloud.model.platformappmanager.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Classname AppAuthorityOwnerBO
 * @Description 应用使用人员配置业务对象
 * @Author yulj
 * @Date: 2019/12/26 2:43 下午
 */
@Data
@ApiModel(description = "应用使用人员业务对象")
public class AppUsePersonBO {

    /**
     * 应用id
     */
    @ApiModelProperty(value = "应用id", dataType = "int")
    @NotNull(message = "应用id不能为空")
    private Integer id;

    /**
     * 应用使用范围(部门及人员)
     */
    @ApiModelProperty(value = "应用使用范围(部门及人员),以逗号分隔", dataType = "String")
    @NotBlank(message = "应用使用范围不能为空")
    private String appUsePerson;

}
