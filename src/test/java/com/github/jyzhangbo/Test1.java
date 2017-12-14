package com.github.jyzhangbo;

/**
 * @author zhangbo
 *
 */
public class Test1 {

  public static void main(String[] args) {
    String s = "TestService";
    StringBuilder builder = new StringBuilder();
    builder.append(s.substring(0, 1).toLowerCase()).append(s.substring(1));
    System.out.println(builder.toString());

  }

}
