package com.github.jyzhangbo.content;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.nutz.lang.Times;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.stereotype.Component;

import cn.mapway.document.servlet.MapwayDocServlet;

/**
 * 生成接口文档的servlet注册.
 * 
 * @author zhangbo
 *
 */
@Component
public class DocumentServer extends ServletRegistrationBean {

  /**
   * 构造函数.
   */
  public DocumentServer() {

    // servlet
    MapwayDocServlet mapwayServlet = new MapwayDocServlet();
    // servlet parameters
    Map<String, String> params = getParameters();

    setServlet(mapwayServlet);
    addUrlMappings("/doc/*");
    setInitParameters(params);
    setLoadOnStartup(1);
  }

  /**
   * 生成文档的配置信息.
   * 
   * @return 配置信息
   */
  private Map<String, String> getParameters() {
    Map<String, String> params = new HashMap<String, String>();
    params.put(MapwayDocServlet.PARAM_ANT_HOME, org.nutz.lang.Strings.sBlank(System.getenv("ANT_HOME")));
    params.put(MapwayDocServlet.PARAM_AUTHOR, "jyzhangbo@gmail.com");
    params.put(MapwayDocServlet.PARAM_BASE_PATH, "/");
    params.put(MapwayDocServlet.PARAM_CONNECTOR_CLASS_NAME, "ApiConnector");
    params.put(MapwayDocServlet.PARAM_CONNECTOR_PACKAGE_NAME, "com.github.jyzhangbo");
    params.put(MapwayDocServlet.PARAM_DOMAIN, "www.jyzhangbo.cn");
    params.put(MapwayDocServlet.PARAM_SCAN_PACKAGES, "com.github.jyzhangbo");
    params.put(MapwayDocServlet.PARAM_SUB_TITLE, "文档生成时间:" + Times.format("yyyy年MM月dd日 HH:mm:ss", new Date()));
    params.put(MapwayDocServlet.PARAM_TITLE, "springboot学习");
    params.put(MapwayDocServlet.PARAM_COPY_RIGHT, "jyzhangbo.cn");
    return params;
  }
}
