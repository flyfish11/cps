package com.cloud.txmanager.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Classname R
 * @Description <h1>响应信息主体(仅用于演示 Feign)</h1>
 * @Author yulj
 * @Date: 2020/02/22 3:10 下午
 */
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(value = "响应信息主体")
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 成功标记
     */
    public static final Integer SUCCESS = 0;

    /**
     * 失败标记
     */
    public static final Integer FAIL = 1;

    @Getter
    @Setter
    @ApiModelProperty(value = "返回标记：成功标记=0，失败标记=1")
    private int code;

    @Getter
    @Setter
    @ApiModelProperty(value = "返回信息")
    private String msg;


    @Getter
    @Setter
    @ApiModelProperty(value = "数据")
    private T data;

    @Getter
    @Setter
    @ApiModelProperty(value = "附加数据")
    private T other;

    public static <T> Result<T> ok() {
        return restResult(null, SUCCESS, "");
    }

    public static <T> Result<T> ok(T data) {
        return restResult(data, SUCCESS, "");
    }

    public static <T> Result<T> ok(T data, T other) {
        return restResult(data, SUCCESS, "", other);
    }

    public static <T> Result<T> ok(T data, String msg) {
        return restResult(data, SUCCESS, msg);
    }

    public static <T> Result<T> ok(T data, String msg, T other) {
        return restResult(data, SUCCESS, msg, other);
    }

    public static <T> Result<T> failed() {
        return restResult(null, FAIL, "");
    }

    public static <T> Result<T> failed(String msg) {
        return restResult(null, FAIL, msg);
    }

    public static <T> Result<T> failed(T data) {
        return restResult(data, FAIL, "");
    }

    public static <T> Result<T> failed(T data, String msg) {
        return restResult(data, FAIL, msg);
    }

    private static <T> Result<T> restResult(T data, int code, String msg) {
        Result<T> apiResult = new Result<>();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
    }

    private static <T> Result<T> restResult(T data, int code, String msg, T other) {
        Result<T> apiResult = new Result<>();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        apiResult.setOther(other);
        return apiResult;
    }

}