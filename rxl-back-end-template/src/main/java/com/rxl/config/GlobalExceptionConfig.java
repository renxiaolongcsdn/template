/*
package com.rxl.config;

import com.rxl.common.R;
import com.rxl.exception.GlobalException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

*/
/**
 * @author ren.xiaolong
 * @date 2022/7/3
 * @Description 全局异常处理器
 *
 * @RestControllerAdvice 是Controller控制器的增强，其实际就是相当于一个拦截器。
 * 作用于controller下的所有RequestMapping，配合着@ExceptionHandler注解全局的指定异常处理
 *//*


@RestControllerAdvice
public class GlobalExceptionConfig {

    */
/**
     * 传入枚举类型
     * @ExceptionHandler 用于处理特定处理程序类和或处理程序方法中的异常的注解。
     * 处理指定异常的抛出
     *//*

    @ExceptionHandler(GlobalException.class)
    public R GlobalException(GlobalException e){
        e.printStackTrace();
        return R.error(e.getMessage());
    }


}
*/
