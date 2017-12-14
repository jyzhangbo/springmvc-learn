package com.github.jyzhangbo.controller.model;

import cn.mapway.document.annotation.ApiField;
import cn.mapway.document.annotation.Doc;

/**
 * @author zhangbo
 *
 */
@Doc(value = "查询用户请求")
public class QueryUserReq {

  @ApiField(value = "用户名", example = "zhangbo")
  public String username;

}
