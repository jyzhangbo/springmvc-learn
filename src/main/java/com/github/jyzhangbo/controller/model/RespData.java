package com.github.jyzhangbo.controller.model;

import com.github.jyzhangbo.codes.ErrorCode;
import com.github.jyzhangbo.codes.ErrorCodes;
import com.github.jyzhangbo.content.CodeException;

import cn.mapway.document.annotation.ApiField;
import cn.mapway.document.annotation.Doc;

/**
 * @author zhangbo
 *
 */
@Doc(value = "返回状态")
public class RespData {


  @ApiField(value = "返回码，200代表成功，其他代码失败", example = "200")
  public Integer code;
  @ApiField(value = "返回消息", example = "成功")
  public String message;

  public RespData() {}

  public RespData ok() {
    this.code = ErrorCodes.SUCCESS.getCode();
    this.message = ErrorCodes.SUCCESS.getMessage();
    return this;
  }

  public RespData ok(String message) {
    this.code = ErrorCodes.SUCCESS.getCode();
    this.message = message;
    return this;
  }

  public RespData(Integer code, String message) {
    this.code = code;
    this.message = message;
  }

  public RespData(ErrorCode errorCode) {
    this.code = errorCode.getCode();
    this.message = errorCode.getMessage();
  }

  public RespData(CodeException codeException) {
    this.code = codeException.getCode();
    this.message = codeException.getMessage();
  }

  /**
   * @return the code
   */
  public Integer getCode() {
    return code;
  }

  /**
   * @param code the code to set
   */
  public void setCode(Integer code) {
    this.code = code;
  }

  /**
   * @return the message
   */
  public String getMessage() {
    return message;
  }

  /**
   * @param message the message to set
   */
  public void setMessage(String message) {
    this.message = message;
  }

}
