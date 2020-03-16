package com.seckill.exception;

import com.seckill.result.CodeMsg;
import com.seckill.result.Result;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Albert on 2020/3/9.
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public Result<String> exceptionHandler(HttpServletRequest request, Exception e) {
        e.printStackTrace();
        if (e instanceof GlobalException) {
            GlobalException exception = (GlobalException)e;
            return Result.error(exception.getCodeMsg());
        } else if (e instanceof BindException) {
            BindException exception = (BindException) e;
            List<ObjectError> list = exception.getAllErrors();
            ObjectError error = list.get(0);
            Object[] args = error.getArguments();
            return Result.error(CodeMsg.BIND_EXCEPTION.fillArgs(args));
        } else {
            return Result.error(CodeMsg.ERROR_MSG);
        }
    }
}
