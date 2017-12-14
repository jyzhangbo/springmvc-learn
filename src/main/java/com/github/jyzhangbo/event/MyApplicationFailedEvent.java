package com.github.jyzhangbo.event;

import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.context.ApplicationListener;

/**
 * @author zhangbo
 *
 */
public class MyApplicationFailedEvent implements ApplicationListener<ApplicationFailedEvent> {

  @Override
  public void onApplicationEvent(ApplicationFailedEvent event) {
    Throwable exception = event.getException();
    System.out.println("启动异常");
  }

}
