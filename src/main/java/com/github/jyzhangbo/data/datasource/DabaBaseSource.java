package com.github.jyzhangbo.data.datasource;

import javax.sql.DataSource;

import org.nutz.dao.Dao;
import org.nutz.dao.impl.NutDao;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * @author zhangbo
 *
 */
@Configuration
public class DabaBaseSource {

  @ConfigurationProperties(prefix = "data")
  @Bean
  public DruidDataSource dataSource() {
    DruidDataSource druidDataSource = new DruidDataSource();
    return druidDataSource;
  }

  @Bean
  public Dao getDao(DataSource dataSource) {
    Dao dao = new NutDao(dataSource);
    return dao;
  }

}
