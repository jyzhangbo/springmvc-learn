package com.github.jyzhangbo.event;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationListener;

/**
 * @author zhangbo
 *
 */
public class MyApplicationStartedEvent implements ApplicationListener<ApplicationStartingEvent> {

  @Override
  public void onApplicationEvent(ApplicationStartingEvent event) {
    SpringApplication application = event.getSpringApplication();
    application.setBannerMode(Banner.Mode.CONSOLE);
    System.out.println("spring boot启动开始");

  }

}
