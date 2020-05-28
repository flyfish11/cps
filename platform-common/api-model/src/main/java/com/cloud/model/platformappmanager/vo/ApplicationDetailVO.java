package com.cloud.model.platformappmanager.vo;

import com.cloud.model.platformappmanager.Application;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Classname ApplicationDetailVO
 * @Description <h1>应用详情视图对象</h1>
 * @Author yulj
 * @Date: 2020/04/26 12:58 下午
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "应用详情视图对象")
public class ApplicationDetailVO extends Application {

    /**
     * 应用分组名称
     */
    @ApiModelProperty(value = "应用分组名称", dataType = "String")
    private String platformGroupName;

    /**
     * 应用类型名称
     */
    @ApiModelProperty(value = "应用类型名称", dataType = "String")
    private String appTypeName;

    /**
     * 应用所属部门名称
     */
    @ApiModelProperty(value = "应用所属部门名称", dataType = "String")
    private String responsibleDeptName;

    /**
     * 应用管理员名称
     */
    @ApiModelProperty(value = "应用管理员名称", dataType = "String")
    private String appAdminName;

    /**
     * 应用使用人员名称
     */
    @ApiModelProperty(value = "应用使用人员名称", dataType = "String")
    private String appUsePersonName;

    @ApiModelProperty(value = "使用应用权限", dataType = "Boolean")
    private Boolean hasUseAuthority;

    @ApiModelProperty(value = "应用分类名称", dataType = "String")
    private String appClassificationName;

}
