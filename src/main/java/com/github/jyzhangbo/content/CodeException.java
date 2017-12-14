package com.github.jyzhangbo.content;

import com.github.jyzhangbo.codes.ErrorCode;

/**
 * 自定义异常.
 * 
 * @author zhangbo
 *
 */
public class CodeException extends Exception {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private Integer code;

  public CodeException(ErrorCode errorCode) {
    super(errorCode.getMessage());
    this.code = errorCode.getCode();
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

}
