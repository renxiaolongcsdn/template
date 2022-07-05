package com.rxl.exception;

import com.rxl.enums.ExceptionEnum;
import lombok.Data;

/**
 * @author ren.xiaolong
 * @date 2022/7/3
 * @Description 自定义异常
 */
@Data
public class GlobalException extends RuntimeException{

    private ExceptionEnum exceptionEnum;
    private String code;
    private String errorMsg;


    public GlobalException(ExceptionEnum exceptionEnum) {
        super("{code:" + exceptionEnum.getCode() + ",errorMsg:" + exceptionEnum.getMsg() + "}");
        this.exceptionEnum = exceptionEnum;
        this.code = exceptionEnum.getCode();
        this.errorMsg = exceptionEnum.getMsg();
    }

    public GlobalException(String code, String errorMsg) {
        super("{code:" + code + ",errorMsg:" + errorMsg + "}");
        this.code = code;
        this.errorMsg = errorMsg;
    }

    public GlobalException(String code, String errorMsg, Object... args) {
        super("{code:" + code + ",errorMsg:" + String.format(errorMsg, args) + "}");
        this.code = code;
        this.errorMsg = String.format(errorMsg, args);
    }

    public GlobalException(String errorMsg) {
        super("{errorMsg:" + errorMsg + "}");
        this.errorMsg = errorMsg;
    }
}
