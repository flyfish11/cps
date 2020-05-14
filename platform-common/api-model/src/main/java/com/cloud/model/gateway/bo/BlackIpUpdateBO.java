package com.cloud.model.gateway.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Classname BlackIpAddBO
 * @Description <h1>黑名单新增业务对象</h1>
 * @Author yulj
 * @Date: 2020/04/17 2:47 下午
 */
@Data
@ApiModel(value = "黑名单新增业务对象")
public class BlackIpUpdateBO {

    @ApiModelProperty(value = "黑名单id", dataType = "String", required = true)
    @NotBlank(message = "黑名单id不能为空")
    private String id;

    @ApiModelProperty(value = "ip地址", dataType = "String", required = true)
    @NotBlank(message = "ip地址不能为空")
    private String ip;

}
