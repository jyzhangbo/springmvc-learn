package com.github.jyzhangbo.codes;

import org.nutz.lang.Strings;

/**
 * 错误实体.
 * 
 * @author zhangbo
 *
 */
public class ErrorCode {

  private Integer code;

  private String message;

  public ErrorCode(Integer code, String message) {
    this.code = code;
    this.message = message;
  }

  public ErrorCode bind(Object... objs) {

    if (Strings.isBlank(message)) {
      return this;
    }
    message = String.format(message, objs);

    return this;
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
