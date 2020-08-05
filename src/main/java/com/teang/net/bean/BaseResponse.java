package com.teang.net.bean;

/**
 * 统一响应
 *
 * @param <T>
 */
public class BaseResponse<T> {
    private int code;
    private String msg;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int res_code) {
        this.code = res_code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String err_msg) {
        this.msg = err_msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T demo) {
        this.data = demo;
    }
}
