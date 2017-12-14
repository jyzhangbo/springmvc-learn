package com.github.jyzhangbo.codes;

/**
 * 错误代码集.
 * 
 * @author zhangbo
 *
 */
public class ErrorCodes {

  public static final ErrorCode SUCCESS = new ErrorCode(200, "操作成功");

  public static final ErrorCode ERROR_SYSTEM = new ErrorCode(500, "服务器异常：%s");
  public static final ErrorCode ERROR_RESOURCE_NOT_FIND = new ErrorCode(404, "访问的资源不存在");
  public static final ErrorCode ERROR_REQUEST_METHOD_NOT_SUPPORT = new ErrorCode(405, "调用方法不被支持");
  public static final ErrorCode ERROR_REQUEST_BODY = new ErrorCode(400, "接口请求JSON数据格式错误:%s");
  public static final ErrorCode ERROR_PARAMETER = new ErrorCode(415, "检查输入参数:%s");

  public static final ErrorCode ERROR_QUERY_USER = new ErrorCode(1001, "查找的用户%s不存在");

}
