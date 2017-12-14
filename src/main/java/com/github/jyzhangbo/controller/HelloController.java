package com.github.jyzhangbo.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangbo
 *
 */
@RestController
@RequestMapping(value = "/hello")
public class HelloController {

  @RequestMapping(value = "/{name}", method = RequestMethod.GET)
  public String hello(@PathVariable(name = "name") String name) {
    return "Hello " + name;
  }

}
