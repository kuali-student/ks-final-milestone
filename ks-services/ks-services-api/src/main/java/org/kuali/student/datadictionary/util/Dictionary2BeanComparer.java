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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import org.kuali.rice.kns.datadictionary.AttributeDefinition;
import org.kuali.rice.kns.datadictionary.DataObjectEntry;


public class Dictionary2BeanComparer {

    private String className;
    private DataObjectEntry osDict;

    public Dictionary2BeanComparer(String className, DataObjectEntry osDict) {
        this.className = className;
        this.osDict = osDict;
    }

    public List<String> compare() {
        if (className == null) {
            return Arrays.asList(osDict.getFullClassName() + " does not have a corresponding java class");
        }
        Class<?> clazz = null;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException ex) {
            return Arrays.asList(className + " does not have a corresponding java class");
        }
        Stack<AttributeDefinition> parents = new Stack<AttributeDefinition>();
        DataObjectEntry osBean = new Bean2DictionaryConverter(clazz, parents).convert();
        return compare(osDict, osBean);

    }

    private List<String> compare(DataObjectEntry osDict,
            DataObjectEntry osBean) {
        List<String> discrepancies = new ArrayList();
        compareAddDiscrepancy(discrepancies, "Java class name", osDict.getFullClassName(), osBean.getFullClassName());
        compareAddDiscrepancy(discrepancies, "Entry class", osDict.getEntryClass(), osBean.getEntryClass());
        for (AttributeDefinition adDict : osDict.getAttributes()) {
            AttributeDefinition adBean = findField(adDict.getName(), osBean);
            if (adBean == null) {
//                if (!adDict.isDynamic()) {
                discrepancies.add("Field " + adDict.getName() + " does not exist in the corresponding java class");
//                }
                continue;
            }
            compareAddDiscrepancy(discrepancies, adDict.getName() + " dataType", adDict.getDataType(), adBean.getDataType());
            compareAddDiscrepancy(discrepancies, adDict.getName() + " maxOccurs", adDict.getMaximumNumberOfElements(), adBean.getMaximumNumberOfElements());
        }
        for (AttributeDefinition fdBean : osBean.getAttributes()) {
            AttributeDefinition fdDict = findField(fdBean.getName(), osDict);
            if (fdDict == null) {
                discrepancies.add("Field " + fdBean.getName() + " missing from the dictictionary");
                continue;
            }
        }
        return discrepancies;
    }

    private AttributeDefinition findField(String name, DataObjectEntry ode) {
        for (AttributeDefinition fd : ode.getAttributes()) {
            if (name.equals(fd.getName())) {
                return fd;
            }
        }
        return null;
    }

    private void compareAddDiscrepancy(List<String> discrepancies, String field, boolean value1,
            boolean value2) {
        String discrep = compare(field, value1, value2);
        if (discrep != null) {
            discrepancies.add(discrep);
        }
    }

    private void compareAddDiscrepancy(List<String> discrepancies, String field, Object value1,
            Object value2) {
        String discrep = compare(field, value1, value2);
        if (discrep != null) {
            discrepancies.add(discrep);
        }
    }

    private String compare(String field, boolean value1, boolean value2) {
        if (value1) {
            if (value2) {
                return null;
            }
        }
        if (!value1) {
            if (!value2) {
                return null;
            }
        }
        return field + " inconsistent: dictionary='" + value1 + "', java class='" + value2 + "'";
    }

    private String compare(String field, Object value1, Object value2) {
        if (value1 == null) {
            if (value2 == null) {
                return null;
            }
        } else {
            if (value1.equals(value2)) {
                return null;
            }
        }
        return field + " inconsistent: dictionary='" + value1 + "'], java class='" + value2 + "'";
    }
}

