package com.github.jyzhangbo.service;

import com.github.jyzhangbo.annotation.MyService;

/**
 * @author zhangbo
 *
 */
@MyService
public class MyTestService {

  /**
   * 
   */
  public String test(String name) {
    System.out.println("hello" + name);
    return "-------" + name;
  }

}
