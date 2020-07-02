package com.fuy.blog.common.exceptionHandler;

import com.fuy.blog.common.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/*
 *全局异常处理,用来返回统一异常结果
 *@return
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    //指定出现什么异常可以执行这个方法(执行所有Exception类的方法时)
    @ExceptionHandler(Exception.class)
    @ResponseBody   //为了可以返回json数据
    public Result error(Exception e){
        e.printStackTrace();
        return Result.fail().message("服务器繁忙,请稍后尝试");
    }

    //特殊异常处理方法
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody   //为了可以返回json数据
    public Result errorSpecial(ArithmeticException e){
        e.printStackTrace();
        return Result.fail().message("进行了ArithmeticException异常处理");
    }

    //实体类异常处理方法
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody   //为了可以返回json数据
    public Result errorSpecial(MethodArgumentNotValidException e){
        log.error("实体校验异常：----------------{}",e);
        BindingResult bindingResult = e.getBindingResult();
        ObjectError objectError = bindingResult.getAllErrors().stream().findFirst().get();
        return Result.fail().message(objectError.getDefaultMessage());
    }

    //自定义异常处理方法
    @ExceptionHandler(MyException.class)
    @ResponseBody   //为了可以返回json数据
    public Result error(MyException e){
        log.error(e.getMsg());
        e.printStackTrace();
        return Result.fail().code(e.getCode()).message(e.getMsg());
    }


}
