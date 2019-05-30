package cc.shallow.sbebean.aop;

import cc.shallow.sbebean.utils.json.Json;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Aspect
@Component
@Slf4j
public class WebAcpect {
    @Pointcut("execution(* cc.shallow.sbebean.controller.**.*(..))")
    public void pointcut(){}

    /**
     * 请求参数记录
     * @param point
     */
    @Before("pointcut()")
    public void requestLog(JoinPoint point){
        ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String token = request.getHeader("Authorization");
        String queryString = request.getQueryString();
        String requestURI = request.getRequestURI();
        String ip = request.getRemoteAddr();
        String requestParam = Json.encode(point.getArgs());
        log.info("requestParam:"+requestParam);
        log.info("token:"+token);
        log.info(queryString);
        log.info("requestUrl:"+requestURI);
        log.info("request IP:"+ip);
        log.info("request time:"+ LocalDateTime.now());
    }

    /**
     * 响应日志记录
     * @param point
     * @param result
     */
    @AfterReturning(value = "pointcut()",returning = "result")
    public void responseLog(JoinPoint point,Object result){
        log.info("response time:"+LocalDateTime.now());
        log.info("response result:"+Json.encode(result));
    }
}
