package com.github.jyzhangbo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

import com.github.jyzhangbo.event.MyApplicationEnvironmentPreparedEvent;
import com.github.jyzhangbo.event.MyApplicationFailedEvent;
import com.github.jyzhangbo.event.MyApplicationPreparedEvent;
import com.github.jyzhangbo.event.MyApplicationStartedEvent;

/**
 * @author zhangbo
 *
 */
@SpringBootApplication
@ServletComponentScan
public class Application {

  public static void main(String[] args) {
    SpringApplication application = new SpringApplication(Application.class);
    application.addListeners(new MyApplicationStartedEvent(), new MyApplicationEnvironmentPreparedEvent(),
        new MyApplicationPreparedEvent(), new MyApplicationFailedEvent());
    application.run(args);

  }

}
