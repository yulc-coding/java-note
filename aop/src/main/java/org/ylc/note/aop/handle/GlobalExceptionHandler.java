package org.ylc.note.aop.handle;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 * <p>
 * 全局异常
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2019/11/20
 */
@Component
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 异常处理
     */
    @ExceptionHandler(Exception.class)
    public String exceptionHandler(Exception ex) {

        System.out.println(ex.getMessage());
        return ex.getMessage();
    }
}
