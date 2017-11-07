package com.abin.lee.redis.common.util.context;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.SingletonBeanRegistry;
import org.springframework.beans.factory.support.DefaultSingletonBeanRegistry;

/**
 * Description:
 * Author: abin
 * Update: (2015-09-04 22:35)
 */
public class ClassPathXmlApplicationContext extends org.springframework.context.support.ClassPathXmlApplicationContext {

    public void setBean(String beanName, Object obj) {
        ConfigurableListableBeanFactory configurableListableBeanFactory = getBeanFactory();

        configurableListableBeanFactory.registerSingleton(beanName, obj);

        if (((obj instanceof DisposableBean)) && ((configurableListableBeanFactory instanceof SingletonBeanRegistry)))
            ((DefaultSingletonBeanRegistry) configurableListableBeanFactory).registerDisposableBean(beanName,
                    (DisposableBean) obj);
    }
}
