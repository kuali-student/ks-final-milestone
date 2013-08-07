/*
 * Copyright 2013 The Kuali Foundation
 * 
 * Licensed under the Educational Community License, Version 1.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.opensource.org/licenses/ecl1.php
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.kuali.student.common.spring;

import java.lang.reflect.Proxy;
import java.lang.reflect.TypeVariable;

import org.kuali.rice.krad.uif.service.ViewHelperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Create a Serializable Proxy for the beanName and java interface provided.
 * <pre>{@code
 *     <bean id="serializableList" class="org.kuali.student.common.spring.SerializableProxyFactoryBean">
 *   
 *         <constructor-arg index="0"><value>list</value></constructor-arg>
 *         <constructor-arg index="1"><value>java.util.List</value></constructor-arg>
 *     </bean>
 *   
 *     <bean id="list" class="java.util.ArrayList">
 *           <constructor-arg>
 *               <list>
 *                   <value>Test</value>
 *                   <value>Proxy</value>
 *               </list>
 *           </constructor-arg>
 *     </bean>  
 *}
 * </pre>
 * 
 * When serialized only the bean name is saved and then on deserialization the real bean is retrieved from the application context.
 * 
 * 
 * @author Kuali Student Team
 * 
 */
public class SerializableProxyFactoryBean<T> implements
        FactoryBean<T>, ApplicationContextAware {

    private String beanName;

    private Class<?>beanClass;
    
    /**
     * 
     */
    public SerializableProxyFactoryBean(String beanName, Class<?>beanClass) {
        this.beanName = beanName;
        this.beanClass = beanClass;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.context.ApplicationContextAware#setApplicationContext
     * (org.springframework.context.ApplicationContext)
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        SerializableSpringBeanProxyInvocationHandler
                .setApplicationContext(applicationContext);

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.beans.factory.FactoryBean#getObject()
     */
    @Override
    public T getObject() throws Exception {

        SerializableSpringBeanProxyInvocationHandler handler = new SerializableSpringBeanProxyInvocationHandler();

        handler.setBeanName(beanName);

        @SuppressWarnings("unchecked")
        T proxy = (T) Proxy.newProxyInstance(
                getClass().getClassLoader(),
                new Class[] { beanClass }, handler);
        
        return proxy;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.beans.factory.FactoryBean#getObjectType()
     */
    @Override
    public Class<?> getObjectType() {
        
        return beanClass;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.beans.factory.FactoryBean#isSingleton()
     */
    @Override
    public boolean isSingleton() {
        return false;
    }

}
