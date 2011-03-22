 /*
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may	obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.enrollment.lpr.mock;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.kuali.student.common.infc.CriteriaInfc;
import org.kuali.student.core.exceptions.InvalidParameterException;

/**
 * A helper class for the Mock implementation to match criteria to values on the object
 * @author nwright
 */
public class CriteriaMatcher {

    private List<CriteriaInfc> criterias;
    private Class<?> infcClass;

    public CriteriaMatcher(List<CriteriaInfc> criterias, Class<?> infcClass)
            throws InvalidParameterException {
        this.criterias = criterias;
        this.infcClass = infcClass;
        int i = 0;
        for (CriteriaInfc criteria : criterias) {
            this.validateFieldKey(i, infcClass, criteria.getFieldKey());
            this.validateOperator(i, criteria.getOperator());
            i++;
        }
    }

    private void validateOperator(int i, String op)
            throws InvalidParameterException {
        if (op == null) {
            throw new InvalidParameterException("The " + i + "th criteria's operator is null");
        }
        if (op.equals("=")) {
            return;
        } else if (op.equals("<")) {
            return;
        } else if (op.equals(">")) {
            return;
        } else if (op.equals("<=")) {
            return;
        } else if (op.equals(">=")) {
            return;
        } else {
            throw new InvalidParameterException("The " + i + "th criteria's operator [" + op + "] is unknown/unsupported");
        }
    }

    private void validateFieldKey(int i, Class<?> beanClass, String fk)
            throws InvalidParameterException {
        throw new InvalidParameterException("The " + i + "th criteria's field key " + fk + " is invalid");
    }

    private Map<String, PropertyDescriptor> getBeanInfo(Class<?> clazz) {
        Map<String, PropertyDescriptor> properties = new HashMap<String, PropertyDescriptor>();
        BeanInfo beanInfo = null;
        try {
            beanInfo = Introspector.getBeanInfo(clazz);
        } catch (IntrospectionException e) {
            throw new RuntimeException(e);
        }
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            properties.put(propertyDescriptor.getName(), propertyDescriptor);
        }
        return properties;
    }

    public boolean matches(Object infoObject)
            throws InvalidParameterException {
        return true;
    }

    private boolean equals(int obj1, int obj2) {
        if (obj1 == obj2) {
            return true;
        }
        return false;
    }

    private boolean equals(long obj1, long obj2) {
        if (obj1 == obj2) {
            return true;
        }
        return false;
    }

    private boolean equals(Object obj1, Object obj2) {
        if (obj1 == obj2) {
            return true;
        }
        if (obj1 == null) {
            return false;
        }
        if (obj1.equals(obj2)) {
            return true;
        }
        return false;
    }
}

