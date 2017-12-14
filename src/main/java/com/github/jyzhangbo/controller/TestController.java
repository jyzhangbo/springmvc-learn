package com.github.jyzhangbo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.jyzhangbo.content.CodeException;
import com.github.jyzhangbo.controller.model.QueryUserReq;
import com.github.jyzhangbo.controller.model.QueryUserResp;
import com.github.jyzhangbo.service.TestService;

import cn.mapway.document.annotation.DevelopmentState;
import cn.mapway.document.annotation.Doc;

/**
 * @author zhangbo
 *
 */
@RestController
@Doc(value = "测试接口", group = "测试", order = 1)
@RequestMapping(value = "v1")
public class TestController {

  @Autowired
  TestService testService;

  @RequestMapping(value = "/queryUser", method = RequestMethod.POST)
  @Doc(value = "查询用户信息", author = "jyzhangbo@gmail.com", state = DevelopmentState.FINISH)
  public QueryUserResp index(@RequestBody QueryUserReq req) throws CodeException {
    return testService.index(req);
  }

}
