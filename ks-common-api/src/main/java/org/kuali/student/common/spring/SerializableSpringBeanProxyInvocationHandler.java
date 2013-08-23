/*
 * Copyright 2012 The Kuali Foundation
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

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.ReflectionUtils;

/**
 * A serializable proxy for a spring bean.
 * 
 * It serialized the name of the bean when serialized.
 * 
 * This assumes a single application context will be used to find all beans being proxied.
 * 
 * @author Kuali Student Team 
 *
 */
public class SerializableSpringBeanProxyInvocationHandler implements
        InvocationHandler, Externalizable {
    
    private static final Logger log = LoggerFactory
            .getLogger(SerializableSpringBeanProxyInvocationHandler.class);

    private String beanName;
    
    private static BeanFactory BEAN_FACTORY;
    
    private transient Object beanDelegate;
   

    /**
     * 
     */
    public SerializableSpringBeanProxyInvocationHandler() {
        super();
    }
    
    

    /**
     * @param applicationContext the applicationContext to set
     */
    public static void setApplicationContext(BeanFactory applicationContext) {
        SerializableSpringBeanProxyInvocationHandler.BEAN_FACTORY = applicationContext;
    }



    /**
     * @param beanName the beanName to set
     */
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }



    /* (non-Javadoc)
     * @see java.io.Externalizable#writeExternal(java.io.ObjectOutput)
     */
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {

        out.writeObject(beanName);
        
    }

    /* (non-Javadoc)
     * @see java.io.Externalizable#readExternal(java.io.ObjectInput)
     */
    @Override
    public void readExternal(ObjectInput in) throws IOException,
            ClassNotFoundException {

        this.beanName = (String) in.readObject();
    }

    /* (non-Javadoc)
     * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object, java.lang.reflect.Method, java.lang.Object[])
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {
        
        if (this.beanDelegate == null) {
            
            this.beanDelegate = BEAN_FACTORY.getBean(beanName);
        }
        
        // delegate the call to the actual service to fulfill.
        return ReflectionUtils.invokeMethod(method, beanDelegate, args);
    
    }
    
    
}
