package indi.shine.web.exception.handler;

import indi.shine.web.bean.response.ReturnT;
import indi.shine.web.exception.BizException;
import org.apache.logging.log4j.util.Strings;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xiezhenxiang 2021/9/10
 */
@RestControllerAdvice
public class BizExceptionHandler {

    /**
     * 自定义业务异常
     **/
    @ExceptionHandler(value = BizException.class)
    public ReturnT<Object> errorHandler(BizException ex) {
        ReturnT<Object> fail = ReturnT.fail(ex.getCode(), ex.getMessage());
        fail.setData(ex.getClass());
        ex.printStackTrace();
        return fail;
    }

    /**
     * 参数校验未通过异常 @RequestBody参数校验失败
     **/
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ReturnT<Object> errorHandler(MethodArgumentNotValidException ex) {

        List<ObjectError> errors = ex.getBindingResult().getAllErrors();
        List<String> errorArr = new ArrayList<>();
        for (ObjectError error : errors) {
            if (error instanceof FieldError) {
                FieldError fieldError = (FieldError)error;
                errorArr.add(fieldError.getField() + fieldError.getDefaultMessage());
            } else {
                errorArr.add(error.getObjectName() + error.getDefaultMessage());
            }
        }
        String errMsg= Strings.join(errorArr.iterator(), ';');
        ReturnT<Object> fail = ReturnT.fail();
        fail.setMsg(errMsg);
        fail.setData(MethodArgumentNotValidException.class);
        return fail;
    }

    /**
     * 参数校验未通过异常 @RequestParam 参数校验失败
     **/
    @ExceptionHandler(value = ConstraintViolationException.class)
    public ReturnT<Object> errorHandler(ConstraintViolationException ex) {

        List<String> errorArr = new ArrayList<>();
        for (ConstraintViolation<?> constraint : ex.getConstraintViolations()) {
            errorArr.add("字段:" + constraint.getPropertyPath() + ", 非法值:" + constraint.getInvalidValue() + "," + constraint.getMessage());
        }
        String errMsg= Strings.join(errorArr.iterator(), ';');
        ReturnT<Object> fail = ReturnT.fail();
        fail.setMsg(errMsg);
        fail.setData(ConstraintViolationException.class);
        return fail;
    }

    /**
     * 其他异常
     **/
    @ExceptionHandler(value = Exception.class)
    public ReturnT<Object> errorHandler(Exception ex) {
        ReturnT<Object> fail = ReturnT.fail();
        fail.setMsg(ex.getMessage());
        fail.setData(ex.getClass());
        ex.printStackTrace();
        return fail;
    }
}
