package com.jdicity.ucuc.schemas.common;

import com.jdicity.ucuc.constant.enums.ResponseCodeEnum;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 统一返回数据的格式.
 *
 * @author liyingda
 * @date 2020-11-13 16:30
 * @param <T> Object
 */
@Data
public class UniversalResponse<T> {

    /**
     * 返回code码
     */
    @ApiModelProperty(value = "返回code码")
    private int code;

    /**
     * 返回msg信息
     */
    @ApiModelProperty(value = "返回msg信息")
    private String msg;

    /**
     * 返回数据
     */
    @ApiModelProperty(value = "返回数据,没有为null")
    private T data;

    /**
     * 构造函数
     *
     * @param code 返回code码
     * @param msg 返回message信息
     */
    public UniversalResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 定义返回的结果.
     *
     * @param codeMsg 枚举的返回码信息.
     * @param data 返回的数据.
     */
    public UniversalResponse(ResponseCodeEnum codeMsg, T data) {
        this.code = codeMsg.getCode();
        this.msg = codeMsg.getMsg();
        this.data = data;
    }

    /**
     * 定义返回的结果.
     *
     * @param codeMsg 枚举的返回码信息.
     */
    public UniversalResponse(ResponseCodeEnum codeMsg) {
        this.code = codeMsg.getCode();
        this.msg = codeMsg.getMsg();
    }

    /**
     * 创建UniversalResponse对象
     *
     * @param data 返回的data数据
     * @return UniversalResponse对象
     */
    public static UniversalResponse<Object> createSuccess(Object data) {
        return new UniversalResponse<>(ResponseCodeEnum.SUCCESS, data);
    }
}
