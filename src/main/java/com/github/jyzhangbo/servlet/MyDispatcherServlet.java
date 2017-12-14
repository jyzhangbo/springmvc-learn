package com.github.jyzhangbo.servlet;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nutz.json.Json;

import com.github.jyzhangbo.annotation.MyAutowired;
import com.github.jyzhangbo.annotation.MyController;
import com.github.jyzhangbo.annotation.MyRequestMapping;
import com.github.jyzhangbo.annotation.MyRequestParam;
import com.github.jyzhangbo.annotation.MyService;

/**
 * @author zhangbo
 *
 */
@WebServlet(urlPatterns = "/mytest/*")
public class MyDispatcherServlet extends HttpServlet {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private Map<String, Object> iocContent = new HashMap<>();

  private Set<String> classes = new HashSet<>();

  private Map<String, HandlerModel> handlerMapping = new HashMap<>();

  @Override
  public void init(ServletConfig config) throws ServletException {
    // 加载所有满足条件的类
    doScan("com.github.jyzhangbo");

    // 初始化ioc容器
    initIocContent();

    // 初始化依赖注入
    initAutowired();

    // 初始化handlermapping
    initHandlerMapping();
  }


  /**
   * @param packageName
   */
  private void doScan(String packageName) {
    URL url = this.getClass().getClassLoader().getResource(packageName.replace(".", "/"));
    File file = new File(url.getFile());

    for (File f : file.listFiles()) {
      if (f.isDirectory()) {
        doScan(packageName + "." + f.getName());
      } else {
        if (f.getName().endsWith(".class")) {
          String className = packageName + "." + f.getName().replaceAll(".class", "");
          classes.add(className);
        }
      }
    }

  }


  /**
   * 
   */
  private void initIocContent() {
    try {
      if (classes.size() == 0) {
        return;
      }
      for (String clazz : classes) {
        Class<?> cls = Class.forName(clazz);
        // 如果不是MyController和MyService注解标注的类就跳过
        if (!cls.isAnnotationPresent(MyController.class) && !cls.isAnnotationPresent(MyService.class)) {
          continue;
        }
        // 将类名首字母转成小写
        String className = toLowerCaseFirstWord(cls.getSimpleName());
        // 将实例化后的对象放进ioc容器中
        iocContent.put(className, cls.newInstance());
      }
      System.out.println("Ioc容器初始化完成" + Json.toJson(iocContent));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  /**
   * 
   */
  private void initAutowired() {

    if (iocContent.isEmpty()) {
      return;
    }

    for (Entry<String, Object> entry : iocContent.entrySet()) {

      Field[] fields = entry.getValue().getClass().getDeclaredFields();
      for (Field field : fields) {
        if (!field.isAnnotationPresent(MyAutowired.class)) {
          continue;
        }
        String name = field.getName();
        field.setAccessible(true);
        try {
          field.set(entry.getValue(), iocContent.get(name));
        } catch (IllegalArgumentException | IllegalAccessException e) {
          e.printStackTrace();
          continue;
        }
      }
    }

  }


  /**
   * 
   */
  private void initHandlerMapping() {
    if (classes.isEmpty()) {
      return;
    }

    for (Entry<String, Object> entry : iocContent.entrySet()) {
      try {
        Class<?> clazz = entry.getValue().getClass();
        String url = "";

        if (clazz.isAnnotationPresent(MyRequestMapping.class)) {
          MyRequestMapping annotation = clazz.getAnnotation(MyRequestMapping.class);
          String value = annotation.value();
          if (!"".equals(value)) {
            url = url + value;
          }
        }
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
          if (method.isAnnotationPresent(MyRequestMapping.class)) {
            MyRequestMapping annotation = method.getAnnotation(MyRequestMapping.class);
            String value = annotation.value();
            url = url + value;
            HandlerModel model = new HandlerModel(method, toLowerCaseFirstWord(clazz.getSimpleName()));

            handlerMapping.put(url, model);
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }


  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doDispatcher(request, response);
  }

  /**
   * @param request
   * @param response
   * @throws IOException
   */
  private void doDispatcher(HttpServletRequest request, HttpServletResponse response) throws IOException {
    if (handlerMapping.isEmpty()) {
      response.getWriter().write("404 NOT FOUND");
      return;
    }

    String path = request.getRequestURI();
    System.out.println(path);

    HandlerModel model = handlerMapping.get(path);
    if (model == null) {
      response.getWriter().write("404 NOT FOUND");
      return;
    }

    Class<?>[] parameterTypes = model.method.getParameterTypes();

    Object[] paramValues = new Object[parameterTypes.length];

    Map<String, String[]> parameterMap = request.getParameterMap();
    for (Entry<String, String[]> param : parameterMap.entrySet()) {
      String value = param.getValue()[0];
      if (!model.paramIndexMapping.containsKey(param.getKey())) {
        continue;
      }
      Integer integer = model.paramIndexMapping.get(param.getKey());
      paramValues[integer] = value;
    }

    Integer reqIndex = model.paramIndexMapping.get(HttpServletRequest.class.getName());
    if (reqIndex != null) {
      paramValues[reqIndex] = request;
    }
    Integer respIndex = model.paramIndexMapping.get(HttpServletResponse.class.getName());
    if (respIndex != null) {
      paramValues[respIndex] = response;
    }

    Object object = iocContent.get(model.clazz);
    try {
      model.method.invoke(object, paramValues);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  class HandlerModel {
    public Method method;
    public String clazz;
    public Map<String, Integer> paramIndexMapping;


    public HandlerModel() {}

    public HandlerModel(Method method, String clazz) {
      this.method = method;
      this.clazz = clazz;
      paramIndexMapping = new HashMap<>();

      putParamIndexMapping(method);
    }

    /**
     * @param method
     */
    private void putParamIndexMapping(Method method) {
      // 获取方法中参数的注解
      Annotation[][] pa = method.getParameterAnnotations();
      for (int i = 0; i < pa.length; i++) {// 循环参数
        for (Annotation a : pa[i]) {// 循环参数上的注解
          if (a instanceof MyRequestParam) {// 如果是自己定义的参数注解
            String paramName = ((MyRequestParam) a).value();// 获取注解的value值
            if (!"".equals(paramName)) {
              paramIndexMapping.put(paramName, i);
            }
          }
        }
      }

      // 获取所有参数列表
      Class<?>[] parameterTypes = method.getParameterTypes();
      for (int i = 0; i < parameterTypes.length; i++) {
        Class<?> type = parameterTypes[i];
        if (type == HttpServletRequest.class || type == HttpServletResponse.class) {
          paramIndexMapping.put(type.getName(), i);
        }
      }

    }
  }


  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doPost(request, response);
  }



  /**
   * @param name
   */
  private String toLowerCaseFirstWord(String name) {
    StringBuilder builder = new StringBuilder();
    builder.append(name.substring(0, 1).toLowerCase()).append(name.substring(1));
    return builder.toString();
  }

  /**
   * 从包package中获取所有的Class
   * 
   * @param pack
   * @return
   */
  public static Set<Class<?>> getClasses(String pack) {

    // 第一个class类的集合
    Set<Class<?>> classes = new LinkedHashSet<Class<?>>();
    // 是否循环迭代
    boolean recursive = true;
    // 获取包的名字 并进行替换
    String packageName = pack;
    String packageDirName = packageName.replace('.', '/');
    // 定义一个枚举的集合 并进行循环来处理这个目录下的things
    Enumeration<URL> dirs;
    try {
      dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
      // 循环迭代下去
      while (dirs.hasMoreElements()) {
        // 获取下一个元素
        URL url = dirs.nextElement();
        // 得到协议的名称
        String protocol = url.getProtocol();
        // 如果是以文件的形式保存在服务器上
        if ("file".equals(protocol)) {
          System.err.println("file类型的扫描");
          // 获取包的物理路径
          String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
          // 以文件的方式扫描整个包下的文件 并添加到集合中
          findAndAddClassesInPackageByFile(packageName, filePath, recursive, classes);
        } else if ("jar".equals(protocol)) {
          // 如果是jar包文件
          // 定义一个JarFile
          System.err.println("jar类型的扫描");
          JarFile jar;
          try {
            // 获取jar
            jar = ((JarURLConnection) url.openConnection()).getJarFile();
            // 从此jar包 得到一个枚举类
            Enumeration<JarEntry> entries = jar.entries();
            // 同样的进行循环迭代
            while (entries.hasMoreElements()) {
              // 获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件
              JarEntry entry = entries.nextElement();
              String name = entry.getName();
              // 如果是以/开头的
              if (name.charAt(0) == '/') {
                // 获取后面的字符串
                name = name.substring(1);
              }
              // 如果前半部分和定义的包名相同
              if (name.startsWith(packageDirName)) {
                int idx = name.lastIndexOf('/');
                // 如果以"/"结尾 是一个包
                if (idx != -1) {
                  // 获取包名 把"/"替换成"."
                  packageName = name.substring(0, idx).replace('/', '.');
                }
                // 如果可以迭代下去 并且是一个包
                if ((idx != -1) || recursive) {
                  // 如果是一个.class文件 而且不是目录
                  if (name.endsWith(".class") && !entry.isDirectory()) {
                    // 去掉后面的".class" 获取真正的类名
                    String className = name.substring(packageName.length() + 1, name.length() - 6);
                    try {
                      // 添加到classes
                      classes.add(Class.forName(packageName + '.' + className));
                    } catch (ClassNotFoundException e) {
                      // log
                      // .error("添加用户自定义视图类错误 找不到此类的.class文件");
                      e.printStackTrace();
                    }
                  }
                }
              }
            }
          } catch (IOException e) {
            // log.error("在扫描用户定义视图时从jar包获取文件出错");
            e.printStackTrace();
          }
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    return classes;
  }

  /**
   * 以文件的形式来获取包下的所有Class
   * 
   * @param packageName
   * @param packagePath
   * @param recursive
   * @param classes
   */
  public static void findAndAddClassesInPackageByFile(String packageName, String packagePath, final boolean recursive,
      Set<Class<?>> classes) {
    // 获取此包的目录 建立一个File
    File dir = new File(packagePath);
    // 如果不存在或者 也不是目录就直接返回
    if (!dir.exists() || !dir.isDirectory()) {
      // log.warn("用户定义包名 " + packageName + " 下没有任何文件");
      return;
    }
    // 如果存在 就获取包下的所有文件 包括目录
    File[] dirfiles = dir.listFiles(new FileFilter() {
      // 自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件)
      public boolean accept(File file) {
        return (recursive && file.isDirectory()) || (file.getName().endsWith(".class"));
      }
    });
    // 循环所有文件
    for (File file : dirfiles) {
      // 如果是目录 则继续扫描
      if (file.isDirectory()) {
        findAndAddClassesInPackageByFile(packageName + "." + file.getName(), file.getAbsolutePath(), recursive,
            classes);
      } else {
        // 如果是java类文件 去掉后面的.class 只留下类名
        String className = file.getName().substring(0, file.getName().length() - 6);
        try {
          // 添加到集合中去
          // classes.add(Class.forName(packageName + '.' + className));
          // 经过回复同学的提醒，这里用forName有一些不好，会触发static方法，没有使用classLoader的load干净
          classes.add(Thread.currentThread().getContextClassLoader().loadClass(packageName + '.' + className));
        } catch (ClassNotFoundException e) {
          // log.error("添加用户自定义视图类错误 找不到此类的.class文件");
          e.printStackTrace();
        }
      }
    }
  }

}
