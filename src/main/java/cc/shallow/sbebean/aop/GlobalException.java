package cc.shallow.sbebean.aop;

import cc.shallow.sbebean.domain.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * 全局异常处理
 */
@RestControllerAdvice
@Slf4j
public class GlobalException {

    @ExceptionHandler(RuntimeException.class)
    public ResponseResult handleRuntimeException(Exception exception){
        if (exception instanceof HttpMessageConversionException){
            log.error("请求参数异常："+exception.getMessage(),exception);
            return ResponseResult.fail("参数异常");
        }
        log.error(exception.getMessage(),exception);
        return ResponseResult.fail(exception.getMessage());
    }

}
