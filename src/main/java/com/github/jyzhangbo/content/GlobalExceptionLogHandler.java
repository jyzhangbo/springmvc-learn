package com.github.jyzhangbo.content;

import java.util.logging.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.springframework.stereotype.Component;

/**
 * 
 * 接口日志处理.
 * 
 * @author zhangbo
 *
 */
@Aspect
@Component
public class GlobalExceptionLogHandler {

  private static final Logger logger = Logger.getLogger("GLOBAL-EXCEPTION-LOG");

  @Pointcut("execution(* com.github.jyzhangbo.content.GlobalHandlerAdvice.*(..)) and @annotation(org.springframework.web.bind.annotation.ExceptionHandler)")
  public void pointCut() {}

  @Before("pointCut()")
  public void doBefore(JoinPoint joinPoint) {

  }

  @AfterReturning(returning = "obj", pointcut = "pointCut()") // 在调用上面 @Pointcut标注的方法后执行。用于获取返回值
  public void doAfterReturning(Object obj) {
    logger.info("response=" + Json.toJson(obj, JsonFormat.tidy()));
  }

}
