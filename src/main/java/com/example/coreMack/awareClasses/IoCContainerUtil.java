package com.example.coreMack.awareClasses;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class IoCContainerUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;
    /**
     * After All beans has been created this method will be called
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext=applicationContext;
    }
    public static <T> T getBean(Class<T> tClass){
        return applicationContext.getBean(tClass);
    }
    public static <T> T getBean(Class<T> tClass,String beanName){
        return applicationContext.getBean(beanName,tClass);
    }
}
