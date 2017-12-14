package com.github.jyzhangbo.content;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.github.jyzhangbo.codes.ErrorCodes;
import com.github.jyzhangbo.controller.model.RespData;

/**
 * 全局异常处理.
 * 
 * @author zhangbo
 *
 */
@ControllerAdvice
@ResponseBody
public class GlobalHandlerAdvice {

  /**
   * Request handling no handler found.
   *
   * @param req the req
   * @param ex the ex
   * @return the model and view
   */
  @ExceptionHandler(value = {NoClassDefFoundError.class, NoHandlerFoundException.class})
  @ResponseStatus(value = HttpStatus.NOT_FOUND)
  public RespData requestHandlingNoHandlerFound(HttpServletRequest req, NoHandlerFoundException ex) {
    return new RespData(ErrorCodes.ERROR_RESOURCE_NOT_FIND);
  }


  /**
   * 调用方法不支持时 返回的视图.
   *
   * @param request the request
   * @param exception the exception
   * @return the model and view
   */
  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public RespData handlerMethodNotSupportException(HttpServletRequest request, Exception exception) {
    return new RespData(ErrorCodes.ERROR_REQUEST_METHOD_NOT_SUPPORT);
  }

  /**
   * Http message not readable exception handler.
   *
   * @param request the request
   * @param exception the exception
   * @return the model and view
   */
  @ExceptionHandler(HttpMessageNotReadableException.class)
  public RespData httpMessageNotReadableExceptionHandler(HttpServletRequest request,
      HttpMessageNotReadableException exception) {

    return new RespData(ErrorCodes.ERROR_REQUEST_BODY.bind(exception.getMessage()));
  }



  /**
   * Code exception error handler.
   *
   * @param request the request
   * @param exception the exception
   * @return the model and view
   */
  @ExceptionHandler(value = {CodeException.class})
  public RespData codeExceptionErrorHandler(HttpServletRequest request, CodeException exception) {
    return new RespData(exception);
  }

  /**
   * 处理参数绑定验证错误.
   *
   * @param request the request
   * @param exception the exception
   * @return the model and view
   */

  @ExceptionHandler(value = {MethodArgumentNotValidException.class})
  public RespData handleBindError(HttpServletRequest request, MethodArgumentNotValidException exception) {
    BindingResult br = exception.getBindingResult();

    StringBuilder sb = new StringBuilder();
    for (ObjectError ex : br.getAllErrors()) {
      sb.append(ex.getDefaultMessage());
      sb.append(";");
    }
    return new RespData(ErrorCodes.ERROR_PARAMETER.bind(sb.toString()));
  }



  /**
   * Default error handler.
   *
   * @param request the request
   * @param exception the exception
   * @return the model and view
   */
  @ExceptionHandler(value = {Exception.class})
  public RespData defaultErrorHandler(HttpServletRequest request, Exception exception) {

    String msg = exception.toString();
    if (msg.contains("NullPointerException")) {
      msg = "空指针错误";
    }
    StackTraceElement[] stackTrace = exception.getStackTrace();
    if (stackTrace.length > 0) {
      StackTraceElement ste = stackTrace[0];
      msg += "\r\n错误代码在" + ste.getClassName() + "类:" + ste.getMethodName() + "方法的" + ste.getLineNumber() + "行";
    }
    return new RespData(ErrorCodes.ERROR_SYSTEM.bind(msg));
  }

}
