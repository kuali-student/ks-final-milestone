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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.kuali.rice.kns.datadictionary.DataObjectEntry;

import org.kuali.rice.kns.datadictionary.validation.DataType;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;

public class DictionaryCreator {

    private static String initLower(String str) {
        if (str == null) {
            return null;
        }
        if (str.length() == 0) {
            return str;
        }
        if (str.length() == 1) {
            return str.toLowerCase();
        }
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }

    public void execute(Class<?> clazz) throws InvalidParameterException, MissingParameterException, ReadOnlyException {
        String outputFileName = "target/ks-" + clazz.getSimpleName() + "-dictionary.xml";
        execute(clazz, outputFileName);
    }

    public void execute(Class<?> clazz, String outputFileName) throws InvalidParameterException, MissingParameterException, ReadOnlyException {
        // Create base dictionary object structure for DTOs that map to entities
        File file = new File(outputFileName);
        OutputStream os;
        try {
            os = new FileOutputStream(file);
        } catch (FileNotFoundException ex) {
            throw new IllegalArgumentException(ex);
        }
        StringBuffer s = new StringBuffer();
        addSpringHeaderOpen(s);

        addObjectStructure(clazz, s, new HashSet<Class<?>>());

        addSpringHeaderClose(s);
        try {
            os.write(s.toString().getBytes());
            os.close();
        } catch (IOException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    private void addObjectStructure(Class<?> clazz, StringBuffer s,
            Set<Class<?>> processed) throws InvalidParameterException, MissingParameterException, ReadOnlyException {
        //Don't process if processed
        if (processed.contains(clazz)) {
            return;
        }
        processed.add(clazz);

        BeanInfo beanInfo;
        try {
            beanInfo = Introspector.getBeanInfo(clazz);
        } catch (IntrospectionException ex) {
            throw new IllegalArgumentException(ex);
        }


        //Step 1, create the abstract structure
        s.append("\n\n<!-- " + clazz.getSimpleName() + "-->");
        s.append("\n<bean id=\"" + initLower(clazz.getSimpleName())
                + "-parent\" abstract=\"true\" parent=\"" + initLower(DataObjectEntry.class.getSimpleName())
                + "\">");
        addProperty("name", initLower(clazz.getSimpleName()), s);
        addProperty("objectClass", clazz.getName(), s);
        addProperty("objectLabel", "", s);
        addProperty("objectDescription", "", s);
        String titleAttribute = calcTitleAttribute(clazz, beanInfo);
        if (titleAttribute != null) {
            addProperty("titleAttribute", titleAttribute, s);
        }
        s.append("\n<property name=\"primaryKeys\">");
        List<String> pks = calcPrimaryKeys(clazz, beanInfo);
        if (pks != null && !pks.isEmpty()) {
            s.append("\n<list>");
            for (String pk : pks) {
                addValue(pk, s);
            }
            s.append("\n</list>");
        }
        s.append("\n</property>");
        s.append("\n<property name=\"attributes\">");
        s.append("\n<list>");

        for (PropertyDescriptor pd : getFilteredSortedProperties(beanInfo)) {
            String fieldName = initLower(clazz.getSimpleName() + "." + initLower(pd.getName()));
            s.append("\n<ref bean=\"" + fieldName + "\"/>");
        }
        s.append("\n</list>");
        s.append("\n</property>");
        s.append("\n</bean>");

        //Create the instance
        s.append("\n<bean id=\"" + initLower(clazz.getSimpleName()) + "\" parent=\""
                + initLower(clazz.getSimpleName()) + "-parent\"/>");

        //Step 2, loop through attributes
        Set<Class<?>> dependantStructures = new HashSet<Class<?>>();
        for (PropertyDescriptor pd : getFilteredSortedProperties(beanInfo)) {
            dependantStructures.addAll(addAttributeDefinition(clazz, pd, s, processed));
        }
        //Step 3, process all dependant object structures
        for (Class<?> dependantClass : dependantStructures) {
            addObjectStructure(dependantClass, s, processed);
        }
    }

    private List<PropertyDescriptor> getFilteredSortedProperties(BeanInfo beanInfo) {
        List<PropertyDescriptor> list = new ArrayList(beanInfo.getPropertyDescriptors().length);
        for (PropertyDescriptor pd : beanInfo.getPropertyDescriptors()) {
            if (pd.getPropertyType().equals(Class.class)) {
                continue;
            }
            list.add(pd);
        }
        Collections.sort(list, new PropertyDescriptorComparator());
        return list;
    }

    private static class PropertyDescriptorComparator implements Comparator<PropertyDescriptor> {

        @Override
        public int compare(PropertyDescriptor o1, PropertyDescriptor o2) {
            return calcRank(o1).compareTo(calcRank(o2));
        }

        private String calcRank(PropertyDescriptor pd) {

            String name = pd.getName();
            String lowerName = pd.getName().toLowerCase();
            if (lowerName.equals("id")) {
                return "00" + name;
            }
            if (lowerName.equals("key")) {
                return "00" + name;
            }
            if (lowerName.equals("typekey")) {
                return "01" + name;
            }
            if (lowerName.equals("statekey")) {
                return "02" + name;
            }
            // to handle r1 services
            if (lowerName.equals("type")) {
                return "01" + name;
            }
            if (lowerName.equals("state")) {
                return "02" + name;
            }
            if (lowerName.equals("name")) {
                return "03" + name;
            }
            if (lowerName.equals("descr")) {
                return "04" + name;
            }
            if (lowerName.equals("effectivedate")) {
                return "10" + name;
            }
            if (lowerName.equals("expirationdate")) {
                return "11" + name;
            }
            if (lowerName.equals("attributes")) {
                return "80" + name;
            }
            if (lowerName.equals("meta")) {
                return "90" + name;
            }
            if (lowerName.equals("startdate")) {
                return "48" + name;
            }
            if (lowerName.equals("enddate")) {
                return "49" + name;
            }
            return "50" + name;
        }
    }

    private String calcTitleAttribute(Class<?> clazz, BeanInfo beanInfo) {
        for (PropertyDescriptor pd : beanInfo.getPropertyDescriptors()) {
            if (pd.getName().equalsIgnoreCase("name")) {
                return initLower(pd.getName());
            }
        }
        for (PropertyDescriptor pd : beanInfo.getPropertyDescriptors()) {
            if (pd.getName().equalsIgnoreCase("title")) {
                return initLower(pd.getName());
            }
        }
        for (PropertyDescriptor pd : beanInfo.getPropertyDescriptors()) {
            if (pd.getName().toLowerCase().endsWith("name")) {
                return initLower(pd.getName());
            }
        }
        for (PropertyDescriptor pd : beanInfo.getPropertyDescriptors()) {
            if (pd.getName().toLowerCase().endsWith("title")) {
                return initLower(pd.getName());
            }
        }
        return null;
    }

    private List<String> calcPrimaryKeys(Class<?> clazz, BeanInfo beanInfo) {
        for (PropertyDescriptor pd : beanInfo.getPropertyDescriptors()) {
            if (pd.getName().equalsIgnoreCase("id")) {
                return Arrays.asList(initLower(pd.getName()));
            }
        }
        for (PropertyDescriptor pd : beanInfo.getPropertyDescriptors()) {
            if (pd.getName().equalsIgnoreCase("key")) {
                return Arrays.asList(initLower(pd.getName()));
            }
        }
        for (PropertyDescriptor pd : beanInfo.getPropertyDescriptors()) {
            if (pd.getName().endsWith("Key")) {
                return Arrays.asList(initLower(pd.getName()));
            }
        }
        return null;
    }

    private Set<Class<?>> addAttributeDefinition(Class<?> clazz, PropertyDescriptor pd,
            StringBuffer s, Set<Class<?>> processed) throws InvalidParameterException, MissingParameterException, ReadOnlyException {
        Set<Class<?>> dependantStructures = new HashSet<Class<?>>();

        //Create the abstract field
        String name = clazz.getSimpleName().substring(0, 1).toLowerCase() + clazz.getSimpleName().substring(
                1) + "." + pd.getName();
        Class<?> actualClass = Bean2DictionaryConverter.calcActualClass(clazz, pd);
        DataType dt = calcDataType(clazz, pd);
        String parentField = calcBaseKualiType(actualClass, pd, dt);

        s.append("\n\n<bean id=\"" + name
                + "-parent\" abstract=\"true\" parent=\"" + parentField + "\">");
        addProperty("name", initLower(pd.getName()), s);
        if (isList(pd)) {
            addProperty("maxOccurs", "" + DictionaryConstants.UNBOUNDED, s);
        }
        s.append("\n</bean>");

        //Create the instance
        s.append("\n<bean id=\"" + name + "\" parent=\"" + name
                + "-parent\"/>");
        return dependantStructures;
    }

    private DataType calcDataType(Class<?> clazz, PropertyDescriptor pd) throws InvalidParameterException, MissingParameterException, ReadOnlyException {
        Class<?> actualClass = Bean2DictionaryConverter.calcActualClass(clazz, pd);
        DataType dataType = Bean2DictionaryConverter.calcDataType(actualClass);
        return dataType;
    }

    private boolean isList(PropertyDescriptor pd) {
        if (pd.getPropertyType().equals(List.class)) {
            return true;
        }
        return false;
    }

    private boolean isComplex(Class<?> clazz, PropertyDescriptor pd) throws InvalidParameterException, MissingParameterException, ReadOnlyException {
        return isComplex(calcDataType(clazz, pd));
    }

    private boolean isComplex(DataType dt) {
        if (dt.equals(DataType.COMPLEX)) {
            return true;
        }
        return false;
    }

    private String calcBaseKualiType(Class<?> clazz, PropertyDescriptor pd, DataType dt) {

        String name = pd.getName();
        String lowerName = pd.getName().toLowerCase();
        if (lowerName.equals("id")) {
            return "baseKualiId";
        }
        if (lowerName.equals("key")) {
            return "baseKualiKey";
        }
//        if (lowerName.equals("descr")) {
//            return "baseKualiTypeDescr";
//        }
        if (lowerName.equals("name")) {
            return "baseKualiName";
        }
        if (lowerName.equals("typekey")) {
            return "baseKualiTypeKey";
        }
        if (lowerName.equals("statekey")) {
            return "baseKualiStateKey";
        }
        // to handle r1 services
        if (lowerName.equals("type")) {
            return "baseKualiTypeKey";
        }
        if (lowerName.equals("state")) {
            return "baseKualiStateKey";
        }
        if (lowerName.equals("effectivedate")) {
            return "baseKualiEffectiveDate";
        }
        if (lowerName.equals("expirationdate")) {
            return "baseKualiExpirationDate";
        }
        if (lowerName.endsWith("orgid")) {
            return "baseKualiOrgId";
        }
        if (lowerName.endsWith("personid")) {
            return "baseKualiPersonId";
        }
        if (lowerName.endsWith("principalid")) {
            return "baseKualiPrincipalId";
        }
        if (lowerName.endsWith("cluid")) {
            return "baseKualiCluId";
        }
        if (lowerName.endsWith("luiid")) {
            return "baseKualiLuiId";
        }
        if (lowerName.endsWith("code")) {
            return "baseKualiCode";
        }
        switch (dt) {
            case STRING:
                return "baseKualiString";
            case DATE:
                return "baseKualiDateTime";
            case TRUNCATED_DATE:
                return "baseKualiDate";
            case BOOLEAN:
                return "baseKualiBoolean";
            case INTEGER:
            case LONG:
                return "baseKualiInteger";
            case FLOAT:
            case DOUBLE:
                return "baseKualiCurrency";
            case COMPLEX:
                return "baseKualiComplex";
            default:
                return "baseKualiString";
        }
    }

    private void addValue(String value, StringBuffer s) {
        s.append("\n<value>" + value + "</value>");
    }

    private void addProperty(String propertyName, String propertyValue,
            StringBuffer s) {
        s.append("\n<property name=\"" + propertyName + "\" value=\"" + propertyValue
                + "\"/>");
    }

    private static void addPropertyRef(String propertyName, String propertyValue,
            StringBuffer s) {
        s.append("\n<property name=\"" + propertyName + "\" ref=\"" + propertyValue
                + "\"/>");
    }

    private void addSpringHeaderClose(StringBuffer s) {
        s.append("\n</beans>").append("\n");
    }

    private void addSpringHeaderOpen(StringBuffer s) {
        s.append("<!--").append("\n");
        s.append(" Copyright 2011 The Kuali Foundation").append("\n");
        s.append("").append("\n");
        s.append(" Licensed under the Educational Community License, Version 2.0 (the \"License\");").append("\n");
        s.append(" you may not use this file except in compliance with the License.").append("\n");
        s.append(" You may obtain a copy of the License at").append("\n");
        s.append("").append("\n");
        s.append(" http://www.opensource.org/licenses/ecl2.php").append("\n");
        s.append("").append("\n");
        s.append(" Unless required by applicable law or agreed to in writing, software").append("\n");
        s.append(" distributed under the License is distributed on an \"AS IS\" BASIS,").append("\n");
        s.append(" WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.").append("\n");
        s.append(" See the License for the specific language governing permissions and").append("\n");
        s.append(" limitations under the License.").append("\n");
        s.append("-->").append("\n");
        s.append("<beans xmlns=\"http://www.springframework.org/schema/beans\"").append("\n");
        s.append("xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"").append("\n");
        s.append("xsi:schemaLocation=\"").append("\n");
        s.append("http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-2.5.xsd").append("\n");
        s.append("\">").append("\n");
        s.append("\n<import resource=\"classpath:ks-base-dictionary.xml\"/>");
    }
}

