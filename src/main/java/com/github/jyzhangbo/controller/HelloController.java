package com.github.jyzhangbo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author zhangbo
 *
 */
@Controller
@RequestMapping(value = "/v2")
public class HelloController {

  @RequestMapping(value = "/hello", method = RequestMethod.GET)
  public String hello() {
    return "hello";
  }

}
