/*
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.r2.common.datadictionary.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * This workaround logic is used in Bean2DictionaryConverter
 * 
 * I want to keep this test here because when it fails that means the bug is finally
 * fixed and we can remove the workaround from that code.
 * 
 * @author nwright
 */
public class TestBeanInfoBugAndWorkaround {

    private static interface Sub {

        public String getFoo();
    }

    private static class SubInfo implements Sub {

        private String foo;

        @Override
        public String getFoo() {
            return foo;
        }

        public void setFoo(String foo) {
            this.foo = foo;
        }
    }

    private static interface Main {

        public Sub getSub();
    }

    private static class MainInfo implements Main {

        private SubInfo sub;

        @Override
        public SubInfo getSub() {
            return sub;
        }

        public void setSub(SubInfo sub) {
            this.sub = sub;
        }
    }

    private static class MainBean {

        private SubInfo sub;

        public SubInfo getSub() {
            return sub;
        }

        public void setSub(SubInfo sub) {
            this.sub = sub;
        }
    }

    @Test
    public void testBeanInfo() {
        System.out.println("testing beanInfo bug and workaround introspection");
        BeanInfo beanInfo;
        // gets the Sub interface for the property type!
        try {
            beanInfo = Introspector.getBeanInfo(MainInfo.class);
        } catch (IntrospectionException ex) {
            throw new RuntimeException(ex);
        }
        for (PropertyDescriptor pd : beanInfo.getPropertyDescriptors()) {
                if (pd.getReadMethod().getName().equals("getSub")) {
//                System.out.println(pd.getName() + " " + pd.getPropertyType().getName());
                assertEquals(Sub.class.getName(), pd.getPropertyType().getName());
            }           
        }
        // works for a regular bean
        try {
            beanInfo = Introspector.getBeanInfo(MainBean.class);
        } catch (IntrospectionException ex) {
            throw new RuntimeException(ex);
        }
        for (PropertyDescriptor pd : beanInfo.getPropertyDescriptors()) {
            if (pd.getReadMethod().getName().equals("getSub")) {
//                System.out.println(pd.getName() + " " + pd.getPropertyType().getName());
                assertEquals(SubInfo.class.getName(), pd.getPropertyType().getName());
            }
        }
        // try work around
                try {
            beanInfo = Introspector.getBeanInfo(MainInfo.class);
        } catch (IntrospectionException ex) {
            throw new RuntimeException(ex);
        }
        for (PropertyDescriptor pd : beanInfo.getPropertyDescriptors()) {
            if (pd.getReadMethod().getName().equals("getSub")) {
//                System.out.println(pd.getName() + " " + pd.getPropertyType().getName());
                assertEquals(Sub.class.getName(), pd.getPropertyType().getName());
                assertTrue (pd.getPropertyType().isInterface());
                Class<?> clazz = workAround (MainInfo.class, pd.getReadMethod().getName());
//                System.out.println(clazz.getName());
                assertEquals(SubInfo.class, clazz);               
            }
        }
        
    }

    private Class<?> workAround (Class <?> currentTargetClass, String methodName) {
        Method method = findMethodImplFirst (currentTargetClass, methodName);
        return method.getReturnType();
    }
    
    /**
     * from http://raulraja.com/2009/09/12/java-beans-introspector-odd-behavio/
     * workaround for introspector odd behavior with javabeans that implement interfaces with comaptible return types
     * but instrospection is unable to find the right accessors
     *
     * @param currentTargetClass the class being evaluated
     * @param methodName		 the method name we are looking for
     * @param argTypes		   the arg types for the method name
     * @return a method if found
     */
    private Method findMethodImplFirst(Class<?> currentTargetClass, String methodName, Class<?>... argTypes) {
        Method method = null;
        if (currentTargetClass != null && methodName != null) {
            try {
                method = currentTargetClass.getMethod(methodName, argTypes);
            } catch (Throwable t) {
                // nothing we can do but continue
            }
            //Is the method in one of our parent classes
            if (method == null) {
                Class<?> superclass = currentTargetClass.getSuperclass();
                if (!superclass.equals(Object.class)) {
                    method = findMethodImplFirst(superclass, methodName, argTypes);
                }
            }
        }
        return method;
    }
}
