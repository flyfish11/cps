package com.cloud.model.platformuser;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(description = "角色数据实体")
public class SysRole implements Serializable {

    private static final long serialVersionUID = -2054359538140713354L;
    @ApiModelProperty("角色id")
    private String id;
    @ApiModelProperty("角色code")
    private String code;
    @ApiModelProperty("角色名称")
    private String name;
    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    @ApiModelProperty("修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
}
