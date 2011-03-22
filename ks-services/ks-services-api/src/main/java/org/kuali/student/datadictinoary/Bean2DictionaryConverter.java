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
package org.kuali.student.datadictinoary;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.List;
import org.kuali.rice.kns.datadictionary.AttributeDefinition;
import org.kuali.rice.kns.datadictionary.validation.DataType;



public class Bean2DictionaryConverter {

    private Class<?> clazz;

    public Bean2DictionaryConverter(Class<?> clazz) {
        this.clazz = clazz;
    }

    public DataDictionaryObjectStructure convert() {
        DataDictionaryObjectStructure os = new DataDictionaryObjectStructure();
        os.setFullClassName(clazz.getName());
        BeanInfo beanInfo;
        try {
            beanInfo = Introspector.getBeanInfo(clazz);
        } catch (IntrospectionException ex) {
            throw new RuntimeException(ex);
        }
         for (PropertyDescriptor pd : beanInfo.getPropertyDescriptors()) {
            if (!Class.class.equals(pd.getPropertyType())) {
                os.getAttributes().add(calcField(clazz, pd));
            }
        }
        return os;
    }



    private AttributeDefinition calcField(Class<?> clazz, PropertyDescriptor pd) {
        AttributeDefinition ad = new AttributeDefinition();
        ad.setName(pd.getName());
        Class<?> pt = pd.getPropertyType();
        if (List.class.equals(pt)) {
            ad.setMaxOccurs(DictionaryConstants.UNBOUNDED);
            try {
                pt =
                        (Class<?>) ((ParameterizedType) clazz.getDeclaredField(pd.getName()).getGenericType()).getActualTypeArguments()[0];
            } catch (NoSuchFieldException ex) {
                throw new RuntimeException(ex);
            } catch (SecurityException ex) {
                throw new RuntimeException(ex);
            }
        } else {
            ad.setMaxOccurs(DictionaryConstants.SINGLE);
        }
        ad.setDataType(calcDataType(pt));
        return ad;
    }

    private DataType calcDataType(Class<?> pt) {
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
            throw new RuntimeException("unknown/unhandled type of object in bean " + pt.getName());
        }
    }
}

