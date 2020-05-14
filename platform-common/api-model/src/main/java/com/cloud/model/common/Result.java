package com.cloud.model.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: flyfish
 * @create: 2018-06-19 11:27
 * @description:
 */
@Data
@ApiModel(value = "分页查询响应信息主体")
public class Result implements Serializable {

    @ApiModelProperty(value = "返回标记：成功标记=0，失败标记=1")
    private Integer code;

    @ApiModelProperty(value = "是否成功：成功=true，失败=false")
    private boolean success;

    @ApiModelProperty(value = "返回信息")
    private String msg;

    @ApiModelProperty(value = "数据条数")
    private long total;

    @ApiModelProperty(value = "数据")
    private Object data;

    private long count;

    private Object rows;
}