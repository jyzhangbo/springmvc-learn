package com.github.jyzhangbo.event;

import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author zhangbo
 *
 */
public class MyApplicationPreparedEvent implements ApplicationListener<ApplicationPreparedEvent> {

  @Override
  public void onApplicationEvent(ApplicationPreparedEvent event) {
    ConfigurableApplicationContext context = event.getApplicationContext();
    System.out.println("spring boot上下文context创建完成");
  }

}
