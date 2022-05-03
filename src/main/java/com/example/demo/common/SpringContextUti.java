package com.example.demo.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.core.env.Environment;

import java.util.Map;

public class SpringContextUti {

    private static ApplicationContext applicationContext;
    //0:开发，1:测试，2:生产环境
    private static byte env = 0;

    private static Logger logger = LoggerFactory.getLogger(SpringContextUti.class);

    /**
     * 取的存储在静态变量中的ApplicationContext
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static void setApplicationContext(ApplicationContext applicationContext) {
        SpringContextUti.applicationContext = applicationContext;
        //获取当前的系统环境
        Environment evn = applicationContext.getEnvironment();
        String[] activeProfiles = evn.getActiveProfiles();
        for (String profile : activeProfiles) {
            if ("dev".equals(profile)){
                break;
            } else if ("test".equals(profile)){
                env = 1;
                break;
            } else if ("prod".equals(profile)){
                env = 2;
                break;
            }
        }
    }

    /**
     * 是否dev环境
     * @return
     */
    public static boolean isDev() {
        return env == 0;
    }
    public static boolean isTest(){
        return env == 1;
    }
    public static boolean isProd(){
        return env == 2;
    }

    public static byte getEnviron(){
        return env;
    }

    /**
     * 从静态变量applicationContext中去的Bean，自动转型为所复制对象的类型
     */
    public static <T> T getBean(String name) {
        return (T) applicationContext.getBean(name);
    }

    public static <T> T getBean(String name, Class<T> clazz) {
        return applicationContext.getBean(name, clazz);
    }

    public static <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        return getApplicationContext().getBeansOfType(type);
    }

    /**
     * 发布一个事件
     */
    public static void publish(ApplicationEvent event) {
        getApplicationContext().publishEvent(event);
    }
}
