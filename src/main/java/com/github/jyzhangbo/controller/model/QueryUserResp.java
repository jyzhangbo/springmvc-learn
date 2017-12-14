package com.github.jyzhangbo.controller.model;

import cn.mapway.document.annotation.ApiField;
import cn.mapway.document.annotation.Doc;

/**
 * @author zhangbo
 *
 */
@Doc(value = "查询用户返回")
public class QueryUserResp extends RespData {

  @ApiField(value = "用户名", example = "zhangbo")
  public String userName;

  @ApiField(value = "真实姓名", example = "张博")
  public String realName;

  @ApiField(value = "邮箱", example = "jyzhangbo.gmail.cn")
  public String email;

  @ApiField(value = "手机号", example = "13888888888")
  public String mobile;

}
