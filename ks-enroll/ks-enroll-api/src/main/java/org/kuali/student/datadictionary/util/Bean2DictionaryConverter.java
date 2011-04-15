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
package org.kuali.student.datadictionary.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Date;
import java.util.List;
import java.util.Stack;
import org.kuali.rice.kns.datadictionary.AttributeDefinition;
import org.kuali.rice.kns.datadictionary.DataObjectEntry;
import org.kuali.rice.kns.datadictionary.validation.DataType;

public class Bean2DictionaryConverter {

    private Class<?> clazz;
    private Stack<AttributeDefinition> parents;

    public Bean2DictionaryConverter(Class<?> clazz, Stack<AttributeDefinition> parents) {
        this.clazz = clazz;
        this.parents = parents;
    }

    public DataObjectEntry convert() {
        DataObjectEntry ode = new DataObjectEntry();
        ode.setObjectClass(clazz);
        addAttributeDefinitions(ode);
        return ode;
    }

    public void addAttributeDefinitions(DataObjectEntry ode) {
        BeanInfo beanInfo;
        try {
            beanInfo = Introspector.getBeanInfo(clazz);
        } catch (IntrospectionException ex) {
            throw new RuntimeException(ex);
        }
        for (PropertyDescriptor pd : beanInfo.getPropertyDescriptors()) {
            if (Class.class.equals(pd.getPropertyType())) {
                continue;
            }
            Class<?> actualClass = calcActualClass(clazz, pd);
            AttributeDefinition ad = calcAttributeDefinition(clazz, pd, actualClass);
            ode.getAttributes().add(ad);
            if (ad.getDataType().equals(DataType.COMPLEX)) {
                parents.push(ad);
                Bean2DictionaryConverter subConverter = new Bean2DictionaryConverter(actualClass, parents);
                subConverter.addAttributeDefinitions(ode);
                parents.pop();
            }
        }
    }

    private AttributeDefinition calcAttributeDefinition(Class<?> clazz, PropertyDescriptor pd, Class<?> actualClass) {
        AttributeDefinition ad = new AttributeDefinition();
        ad.setName(calcName(pd.getName()));
        Class<?> pt = pd.getPropertyType();
        if (List.class.equals(pt)) {
//            TODO: fix this to use a CollectionDefinition
//            ad.setMaxOccurs(DictionaryConstants.UNBOUNDED);
            ad.setDataType(calcDataType(actualClass));
        } else {
//            ad.setMaxOccurs(DictionaryConstants.SINGLE);
            ad.setDataType(calcDataType(actualClass));
        }
        return ad;
    }

    private String calcName(String leafName) {
        StringBuilder bldr = new StringBuilder();
        for (AttributeDefinition parent : parents) {
            bldr.append(parent.getName());
            bldr.append(".");
        }
        bldr.append(initLower(leafName));
        return bldr.toString();
    }

    private String initLower(String name) {
        return name.substring(0, 1).toLowerCase() + name.substring(1);
    }

    public static Class<?> calcActualClass(Class<?> clazz, PropertyDescriptor pd) {
        Class<?> pt = pd.getPropertyType();
        if (List.class.equals(pt)) {
            pt = ComplexSubstructuresHelper.getActualClassFromList(clazz, pd.getName());
        }
        return pt;
    }

    private DataType calcDataType(Class<?> pt) {
        return calcDataType(clazz, pt);
    }

    public static DataType calcDataType(Class<?> clazz, Class<?> pt) {
        if (int.class.equals(pt) || Integer.class.equals(pt)) {
            return DataType.INTEGER;
        } else if (long.class.equals(pt) || Long.class.equals(pt)) {
            return DataType.LONG;
        } else if (double.class.equals(pt) || Double.class.equals(pt)) {
            return DataType.DOUBLE;
        } else if (float.class.equals(pt) || Float.class.equals(pt)) {
            return DataType.FLOAT;
        } else if (boolean.class.equals(pt) || Boolean.class.equals(pt)) {
            return DataType.BOOLEAN;
        } else if (Date.class.equals(pt)) {
            return DataType.DATE;
        } else if (String.class.equals(pt)) {
            return DataType.STRING;
        } else if (List.class.equals(pt)) {
            throw new RuntimeException("Can't have a list of lists, List<List<?>>");
        } else if (Enum.class.isAssignableFrom(pt)) {
            return DataType.STRING;
        } else if (Object.class.equals(pt)) {
            return DataType.STRING;
        } else if (pt.getName().startsWith("org.kuali.student.")) {
            return DataType.COMPLEX;
        } else {
            throw new RuntimeException("unknown/unhandled type of object in bean " + clazz.getSimpleName() + "." + pt.getName());
        }
    }
}

