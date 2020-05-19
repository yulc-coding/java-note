package org.ylc.note.security.common;

import org.ylc.note.security.constant.ConfigConstants;

import java.io.Serializable;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 *
 * 统一返回
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2020/5/19
 */
public class HttpResult<T> implements Serializable {


    private static final long serialVersionUID = 1L;

    private int code;

    private String msg;

    private T data;

    public HttpResult(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public HttpResult() {
        this.code = ConfigConstants.Return.SUCCESS;
        this.msg = "SUCCESS";
    }

    public HttpResult(T data) {
        this();
        this.data = data;
    }

    /**
     * 成功返回
     *
     * @param msg  成功信息
     * @param body 返回数据
     * @return Result
     */
    public static <T> HttpResult<T> success(String msg, T body) {
        return new HttpResult<>(ConfigConstants.Return.SUCCESS, msg, body);
    }

    /**
     * 成功返回
     *
     * @param body 返回数据
     * @return Result
     */
    public static <T> HttpResult<T> success(T body) {
        return success("success", body);
    }

    /**
     * 成功返回
     *
     * @return Result
     */
    public static <T> HttpResult<T> success() {
        return success(null);
    }

    /**
     * 失败返回
     *
     * @param code 错误编码
     * @param msg  错误信息
     * @return Result
     */
    public static <T> HttpResult<T> fail(int code, String msg) {
        return new HttpResult<>(code, msg, null);
    }

    /**
     * 普通失败返回
     *
     * @param msg 错误信息
     * @return Result
     */
    public static <T> HttpResult<T> fail(String msg) {
        return fail(ConfigConstants.Return.OPERATION_FAILED, msg);
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

}
