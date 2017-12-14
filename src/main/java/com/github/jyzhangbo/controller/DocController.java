package com.github.jyzhangbo.controller;

import org.springframework.stereotype.Controller;

import cn.mapway.document.annotation.Doc;

/**
 * 使用手册.
 * 
 * @author zhangjianshe
 *
 */
@Doc(value = "文档使用说明", order = 0, group = "/文档使用说明", refs = {"docref/manual.html"})
@Controller
public class DocController {

}
