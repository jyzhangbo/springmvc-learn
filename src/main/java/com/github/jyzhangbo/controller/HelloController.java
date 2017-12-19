package com.github.jyzhangbo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author zhangbo
 *
 */
@Controller
public class HelloController {

  @RequestMapping(value = "/login", method = RequestMethod.GET)
  public ModelAndView login(@RequestParam(value = "error", required = false) String error,
      @RequestParam(value = "logout", required = false) String logout) {
    ModelAndView model = new ModelAndView();
    if (error != null) {
      model.addObject("error", "Invalid username and password!");
    }

    if (logout != null) {
      model.addObject("msg", "You've been logged out successfully.");
    }
    model.setViewName("login");
    return model;
  }

  @RequestMapping(value = "/hello", method = RequestMethod.GET)
  public String hello(ModelMap model) {
    model.addAttribute("name", "zhangbo");
    return "hello";
  }

  @RequestMapping(value = "/admin", method = RequestMethod.GET)
  public String admin(ModelMap model) {
    model.addAttribute("name", "admin");
    return "admin";
  }

}
