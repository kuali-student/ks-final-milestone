package org.kuali.student.core.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BeanFactory {
    private static BeanFactory _instance;
    private ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-beans.xml");
    private BeanFactory() {
        
    }
    public static synchronized BeanFactory getInstance() {
        if (_instance == null) {
            _instance = new BeanFactory();
        }
        return _instance;
    }
    public Object getBean(String name) {
        return context.getBean(name);
    }
}
