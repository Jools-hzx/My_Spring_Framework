package com.hzx.myspring.application;

import com.hzx.myspring.annotation.Autowired;
import com.hzx.myspring.annotation.Component;
import com.hzx.myspring.annotation.ComponentScan;
import com.hzx.myspring.annotation.Scope;
import com.hzx.myspring.entity.BeanDefinition;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Jools He
 * @version 1.0
 * @date 2024/4/11 21:18
 * @description: TODO
 */
public class HzxSpringApplicationContext {

    //容器配置类的 Class 类对象
    Class<?> configClass;

    private final ConcurrentHashMap<String, BeanDefinition> beanDefinitions = new ConcurrentHashMap<>();

    private final ConcurrentHashMap<String, Object> singletonObjects = new ConcurrentHashMap<>();

    public HzxSpringApplicationContext(Class<?> configClass) {
        this.configClass = configClass;
        scanPackageAndEncapsulateBeanDefinition(this.configClass);
        injectSingleObjects();

        System.out.println("容器初始化完成!");
    }

    //根据 beanId 返回 Bean 对象
    public Object getBean(String beanId) {
        if (beanId.isEmpty()) return null;
        //判断如果是单例，从单例集合中返回
        BeanDefinition beanDefinition = beanDefinitions.get(beanId);
        if ("singleton".equals(beanDefinition.getScope())
                && singletonObjects.containsKey(beanId)) {
            return singletonObjects.get(beanId);
        }
        //如果不是单例，则反射生成
        if ("prototype".equals(beanDefinition.getScope())) {
            return createBean(beanDefinition.getCls());
        }
        throw new RuntimeException("Can not find bean which beanId:" + beanId);
    }

    //根据 beanId 返回指定类型的 Bean 对象
    @SuppressWarnings("all")
    public <T> T getBean(String beanId, Class<T> clazz) {
        Object bean = getBean(beanId);
        if (bean.getClass().isAssignableFrom(clazz)) {
            return (T) bean;
        }
        throw new RuntimeException("Can not find bean which beanId:" + beanId);
    }

    private void injectSingleObjects() {
        if (beanDefinitions.isEmpty()) return;  //检查集合情况
        Enumeration<String> keys = beanDefinitions.keys();  //遍历集合
        while (keys.hasMoreElements()) {
            String beanId = keys.nextElement();
            BeanDefinition beanDefinition = beanDefinitions.get(beanId);

            //如果定义为 singleton 类型，则实例化并存放进集合内
            if ("singleton".equals(beanDefinition.getScope())) {
                Object instance = createBean(beanDefinition.getCls());
                singletonObjects.put(beanId, instance);
            }
        }
    }

    // 该方法基于 Bean 类的 Class 类对象反射生成实例。
    private Object createBean(Class<?> cls) {
        Object instance;
        try {
            instance = cls.newInstance();

            //If AutoWired Annotation exists, try to auto wired field by specifics bean
            Field[] fields = cls.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(Autowired.class)) {

                    Autowired autowired = field.getAnnotation(Autowired.class);
                    String fieldName = field.getName();
                    Object autoWiredInstance = null;
                    field.setAccessible(true);

                    //If bean definition is defined and scope is "singleton"
                    if (beanDefinitions.containsKey(fieldName) &&
                            "singleton".equals(beanDefinitions.get(fieldName).getScope())) {
                        if (singletonObjects.containsKey(fieldName)) {
                            autoWiredInstance = singletonObjects.get(fieldName);
                        } else {
                            autoWiredInstance = createBean(beanDefinitions.get(fieldName).getCls());
                            singletonObjects.put(fieldName, autoWiredInstance);
                        }
                    }

                    // Throw Exception if autoWired is required!!!
                    if (null == autoWiredInstance && autowired.required()) {
                        throw new RuntimeException("Not assemblable bean instance, " +
                                "please check field name:" + fieldName);
                    }

                    //set the field
                    field.set(instance, autoWiredInstance);
                    field.setAccessible(false);
                }
            }
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return instance;
    }

    @SuppressWarnings("all")
    private void scanPackageAndEncapsulateBeanDefinition(Class<?> configClass) {

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
                String classFullPath = packagePath + "." + className;
                System.out.println("类的全路径:" + classFullPath);

                try {
                    Class<?> cls = Class.forName(classFullPath);
                    String scope = "singleton"; //默认为 单例类型返回
                    String beanId = StringUtils.uncapitalize(className);    //默认beanId为类名首字母小写

                    //获取自定义的 beanId
                    if (cls.isAnnotationPresent(Component.class)) {
                        Component component = cls.getAnnotation(Component.class);
                        if (isComponentAnnotationContainValue(component)) {
                            beanId = component.value();
                        }
                    } else {
                        //如果没有被注解 Component 标记，则不作为 bean 处理
                        continue;
                    }

                    //获取自定义的 Scope
                    if (cls.isAnnotationPresent(Scope.class)) {
                        Scope scopeAnnotation = cls.getAnnotation(Scope.class);
                        if (isScopeAnnotationContainValue(scopeAnnotation)) {
                            scope = scopeAnnotation.value();
                        }
                    }

                    //封装成 BeanDefinition 对象
                    BeanDefinition beanDefinition = new BeanDefinition();
                    beanDefinition.setBeanId(beanId);
                    beanDefinition.setCls(cls);
                    beanDefinition.setScope(scope);

                    beanDefinitions.put(beanId, beanDefinition);

                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        } else {
            throw new RuntimeException("Can not find configuration class:" + configClass);
        }
    }

    //判断 Component 注解value() 是否包含有值，并且值是否符合要求
    private boolean isComponentAnnotationContainValue(Component component) {
        return !component.value().isEmpty();
    }

    //判断 Scope 注解是否含有值并且符合输入要求
    private boolean isScopeAnnotationContainValue(Scope scope) {
        return !scope.value().isEmpty() &&
                ("singleton".equals(scope.value()) ||
                        "prototype".equals(scope.value()));
    }

}
