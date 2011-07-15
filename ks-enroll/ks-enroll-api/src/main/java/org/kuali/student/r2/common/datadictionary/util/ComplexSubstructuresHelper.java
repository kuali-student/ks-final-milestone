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
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class ComplexSubstructuresHelper {

    public Set<String> getComplexStructures(String className) {
        Set<String> complexStructures = new LinkedHashSet<String>();
        loadComplexStructures(className, complexStructures);
        return complexStructures;
    }

    private void loadComplexStructures(String className,
            Set<String> complexStructures) {
        if (!complexStructures.add(className)) {
            return;
        }
        BeanInfo beanInfo;
        Class<?> clazz;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException ex) {
            System.out.println("ComplexSubstructuresHelper: Could not process because the class must be a freestanding object: " + className);
            return;
        }
        try {
            beanInfo = Introspector.getBeanInfo(clazz);
        } catch (IntrospectionException ex) {
            throw new RuntimeException(ex);
        }
        for (PropertyDescriptor pd : beanInfo.getPropertyDescriptors()) {
            Class<?> subClass = pd.getPropertyType();
            if (List.class.equals(subClass)) {
                // recursively check super classes for field if not declared on this class
                subClass = getActualClassFromList(clazz, pd.getName());
            }
            if (!Class.class.equals(subClass)
                    && !String.class.equals(subClass)
                    && !Integer.class.equals(subClass)
                    && !Long.class.equals(subClass)
                    && !Boolean.class.equals(subClass)
                    && !boolean.class.equals(subClass)
                    && !int.class.equals(subClass)
                    && !long.class.equals(subClass)
                    && !Double.class.equals(subClass)
                    && !Float.class.equals(subClass)
                    && !Date.class.equals(subClass)
                    && !Enum.class.isAssignableFrom(subClass)
                    && !Object.class.equals(subClass)) {
                loadComplexStructures(subClass.getName(), complexStructures);
            }
        }
    }

    public static Class<?> getActualClassFromList(Class<?> originalClass, String fieldName) {
        // recursively check super classes for field if not declared on this class
        Class<?> classToCheck = originalClass;
        while (true) {
            try {
                Field field = classToCheck.getDeclaredField(fieldName);
                Type type = field.getGenericType();
                ParameterizedType pt = (ParameterizedType) type;
                Type actualType = pt.getActualTypeArguments()[0];
                return (Class<?>) actualType;
            } catch (NoSuchFieldException ex) {
                classToCheck = classToCheck.getSuperclass();
                if (classToCheck == null) {
                    throw new RuntimeException(originalClass.getName(), ex);
                }
                if (classToCheck.equals(Object.class)) {
                    throw new RuntimeException(ex);
                }
            } catch (SecurityException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
