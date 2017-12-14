package com.github.jyzhangbo.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.github.jyzhangbo.annotation.MyAutowired;
import com.github.jyzhangbo.annotation.MyController;
import com.github.jyzhangbo.annotation.MyRequestMapping;
import com.github.jyzhangbo.annotation.MyRequestParam;
import com.github.jyzhangbo.service.MyTestService;

/**
 * @author zhangbo
 *
 */
@MyController
@MyRequestMapping(value = "/mytest")
public class MyTestController {

  @MyAutowired
  MyTestService myTestService;

  @MyRequestMapping(value = "/hello")
  public void test(@MyRequestParam("name") String name, HttpServletResponse resp) throws IOException {
    String test = myTestService.test(name);
    resp.getWriter().write(test);
  }

}
