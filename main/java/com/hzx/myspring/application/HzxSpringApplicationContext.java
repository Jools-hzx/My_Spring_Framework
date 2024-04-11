package com.hzx.myspring.application;

import com.hzx.myspring.annotation.ComponentScan;

import java.io.File;
import java.net.URL;

/**
 * @author Jools He
 * @version 1.0
 * @date 2024/4/11 21:18
 * @description: TODO
 */
public class HzxSpringApplicationContext {

    //容器配置类的 Class 类对象
    Class<?> configClass;

    public HzxSpringApplicationContext(Class<?> configClass) {
        this.configClass = configClass;

        if (configClass.isAnnotationPresent(ComponentScan.class)) {

            ComponentScan componentScan = configClass.getAnnotation(ComponentScan.class);
            String packagePath = componentScan.value();
            System.out.println("待扫描的包路径为:" + packagePath);
            //拼接字符串
            StringBuilder sb = new StringBuilder();

            //将包路径的 . 转换为 /
            String packageURI = packagePath.replaceAll("\\.", "/");

            //得到待扫描包的资源路径
            URL url = Thread.currentThread().getContextClassLoader().getResource(packageURI);
            assert url != null;
            //获取该 URL 的字符串
            String urlStr = url.getPath();
            sb.append(urlStr);
//            System.out.println("待扫描包的 Url 为:" + sb.toString());

            //扫描该包下的所有文件
            File dir = new File(sb.toString());
            if (!dir.isDirectory()) {
                throw new RuntimeException("待扫描的包下为空!" + sb);
            }

            File[] files = dir.listFiles();
            for (File file : files) {
                //获取到Bean类名和类的全路径
                String absolutePath = file.getAbsolutePath();
                String className = absolutePath.substring(
                        absolutePath.lastIndexOf("\\") + 1,
                        absolutePath.indexOf("."));
                System.out.println("类名:" + className);
                String classFullPath = packagePath + className;
                System.out.println("类的全路径:" + classFullPath);
            }

        } else {
            throw new RuntimeException("Can not find configuration class:" + configClass);
        }
    }
}
