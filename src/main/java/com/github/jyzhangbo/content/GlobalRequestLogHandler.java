package com.github.jyzhangbo.content;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 
 * 接口日志处理.
 * 
 * @author zhangbo
 *
 */
@Aspect
@Component
public class GlobalRequestLogHandler {

  private static final Logger logger = Logger.getLogger("GLOBAL-REQUEST-LOG");

  @Pointcut("execution(* com.github.jyzhangbo.controller..*(..)) and @annotation(org.springframework.web.bind.annotation.RequestMapping)")
  public void pointCut() {}

  @Before("pointCut()")
  public void doBefore(JoinPoint joinPoint) {
    ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    HttpServletRequest request = attributes.getRequest();

    String message = "request=" + request.getRequestURI() + "@" + Json.toJson(joinPoint.getArgs(), JsonFormat.tidy());
    // 类方法
    logger.info(message);

  }

  @AfterReturning(returning = "obj", pointcut = "pointCut()") // 在调用上面 @Pointcut标注的方法后执行。用于获取返回值
  public void doAfterReturning(Object obj) {
    logger.info("response=" + Json.toJson(obj, JsonFormat.tidy()));
  }

}
