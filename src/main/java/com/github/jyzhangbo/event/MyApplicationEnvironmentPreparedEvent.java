package com.github.jyzhangbo.event;

import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;

/**
 * @author zhangbo
 *
 */
public class MyApplicationEnvironmentPreparedEvent implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {

  @Override
  public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
    ConfigurableEnvironment environment = event.getEnvironment();
    String[] activeProfiles = environment.getActiveProfiles();
    for (String string : activeProfiles) {
      System.out.println(string);
    }

    MutablePropertySources propertySources = environment.getPropertySources();
    propertySources.forEach((obj) -> {
      if (obj.containsProperty("data.url")) {
        System.out.println("datasource:url=" + obj.getProperty("data.url"));
      }
    });

  }

}
