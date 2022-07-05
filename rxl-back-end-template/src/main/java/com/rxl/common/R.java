package com.rxl.common;


import lombok.Data;

import java.io.Serializable;

/**
 * @author ren.xiaolong
 * @date 2022/4/17
 * @Description 统一定义接口返回值
 */
@Data
public class R<T> implements Serializable {

    private String msg;

    private String  code;

    private T data;


    public static <T> R<T> OK(T data) {
        return restResult(data, "200", null);
    }

    public static <T> R<T> OK(T data, String msg) {
        return restResult(data, "200", msg);
    }


    public static <T> R<T> OK(T data, String code, String msg) {
        return restResult(data, code, msg);
    }

    public static <T> R<T> error(T data) {
        return restResult(data, "500", null);
    }

    public static <T> R<T> error(T data, String msg) {
        return restResult(data, "500", msg);
    }

    public static <T> R<T> error(T data, String code, String msg) {
        return restResult(data, code, msg);
    }


    public static <T> R<T> error(String code, String msg) {
        return restResult(code, msg);
    }

    /**
     * 成功
     * @param data
     * @param code
     * @param msg
     * @param <T>
     * @return
     */
    private static <T> R<T> restResult(T data, String code, String msg) {
        R<T> apiResult = new R();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
    }


    /**
     * 失败
     * @param code
     * @param msg
     * @param <T>
     * @return
     */
    private static <T> R<T> restResult(String code, String msg) {
        R<T> apiResult = new R();
        apiResult.setCode(code);
        apiResult.setMsg(msg);
        return apiResult;
    }



}
