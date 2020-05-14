package com.cloud.model.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Classname SysDictItemVO
 * @Description <h1>字典项视图对象</h1>
 * @Author yulj
 * @Date: 2020/04/26 2:11 下午
 */
@Data
@ApiModel(value = "字典项视图对象")
public class SysDictItemVO {

    /**
     * 数据值
     */
    @ApiModelProperty(value = "数据值", dataType = "String")
    private String value;

    /**
     * 标签名
     */
    @ApiModelProperty(value = "标签名", dataType = "String")
    private String label;

}
