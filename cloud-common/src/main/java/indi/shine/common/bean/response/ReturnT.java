package indi.shine.common.bean.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * api return model
 * @author xiezhenxiang 2020/1/7
 **/
@Getter
@Setter
@JsonInclude(Include.NON_NULL)
public class ReturnT<T> implements Serializable  {

    private static final long serialVersionUID = 112L;
    private static final int SUCCESS_CODE = 200;
    private static final String SUCCESS_MSG = "ok";
    private static final int DEFAULT_ERROR_CODE = 500;
    private static final String DEFAULT_ERROR_MSG = "undefined error";

    private int code;
    private String msg;
    private T data;

    private ReturnT(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private ReturnT(T data) {
        this.code = SUCCESS_CODE;
        this.msg = SUCCESS_MSG;
        this.data = data;
    }

    public static <T> ReturnT<T> success() {
        return new ReturnT<>(SUCCESS_CODE, SUCCESS_MSG);
    }

    public static <T> ReturnT<T> success(T content) {
        return new ReturnT<>(content);
    }

    public static <T> ReturnT<T> fail(int code, String msg) {
        return new ReturnT<>(code, msg);
    }

    public static <T> ReturnT<T> fail() {
        return new ReturnT<>(DEFAULT_ERROR_CODE, DEFAULT_ERROR_MSG);
    }
}
