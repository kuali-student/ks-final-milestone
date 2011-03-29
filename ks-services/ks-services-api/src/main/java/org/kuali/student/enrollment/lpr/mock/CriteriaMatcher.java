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
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.kuali.student.common.dto.ContextInfo;
import org.kuali.student.common.dto.CriteriaInfo;
import org.kuali.student.common.exceptions.DoesNotExistException;
import org.kuali.student.common.exceptions.MissingParameterException;
import org.kuali.student.common.exceptions.PermissionDeniedException;
import org.kuali.student.common.exceptions.InvalidParameterException;
import org.kuali.student.common.exceptions.OperationFailedException;
import org.kuali.student.common.infc.HoldsDataDictionaryService;
import org.kuali.student.datadictionary.dto.AttributeDefinitionInfo;
import org.kuali.student.datadictionary.dto.DictionaryEntryInfo;
import org.kuali.student.datadictionary.service.DataDictionaryService;

/**
 * A helper class for the Mock implementation to match criteria to values on the object
 * @author nwright
 */
public class CriteriaMatcher implements HoldsDataDictionaryService {

    private DataDictionaryService dataDictionaryService;

    public DataDictionaryService getDataDictionaryService() {
        return dataDictionaryService;
    }

    public void setDataDictionaryService(DataDictionaryService dataDictionaryService) {
        this.dataDictionaryService = dataDictionaryService;
    }
    private List<CriteriaInfo> criterias;
    private Class<?> infoClass;
    private String dictEntryKey;
    private ContextInfo context;

    public CriteriaMatcher(List<CriteriaInfo> criterias, Class<?> infoClass, ContextInfo context)
            throws InvalidParameterException,
            OperationFailedException {
        this.criterias = criterias;
        this.infoClass = infoClass;
        this.context = context;
        this.dictEntryKey = this.calcDictEntryKey();
        this.validate();
    }

    private String calcDictEntryKey(Class<?> clazz) {
        return initLower(clazz.getSimpleName());
    }

    private String calcDictEntryKey() {
        return calcDictEntryKey(this.infoClass);
    }

    private void validate()
            throws InvalidParameterException,
            OperationFailedException {
        int i = 0;
        for (CriteriaInfo criteria : criterias) {
            this.validate(i, criteria);
            i++;
        }
    }

    private void validate(int i, CriteriaInfo criteria)
            throws InvalidParameterException,
            OperationFailedException {
        String fk = criteria.getFieldKey();
        String op = criteria.getOperator();
        String fv = criteria.getValue();
        AttributeDefinitionInfo ad = this.getAttributeDefinition(fk);
        if (ad == null) {
            throw new InvalidParameterException("The " + i + "th criteria's field key " + fk + " is invalid");
        }
        if (op == null) {
            throw new InvalidParameterException("The " + i + "th criteria's operator is null");
        }
        if (op.equals("=")) {
            return;
        }
        if (op.equals("<")) {
            return;
        }
        if (op.equals(">")) {
            return;
        }
        if (op.equals("!=")) {
            return;
        }
        if (op.equals("<=")) {
            return;
        }
        if (op.equals(">=")) {
            return;
        }
        if (fv == null) {
            if (!op.equals("=") && !op.equals("<>")) {
                throw new InvalidParameterException("The " + i + "th criteria's value is null but the operator " + op + " is a comparison operator that does not apply");
            }
            return;
        }
        boolean isList = calcIsList(ad);
        switch (ad.getDataType()) {
            case STRING:
                break;
            case DATE:
            case TRUNCATED_DATE:
                break;
            case BOOLEAN:
                if (op.equals(">") || op.equals("<") || op.equals(">=") || op.equals("<=")) {
                    throw new InvalidParameterException("The " + i + "th criteria's operator " + op + " is a comparison operator that does not apply to the field's boolean data type");
                }
            case INTEGER:
            case FLOAT:
            case DOUBLE:
            case LONG:
                break;
            case COMPLEX:
                if (fv != null) {
                    throw new InvalidParameterException("The " + i + "th criteria's value is not null but attribute type is complex. Complex can only be checked to see if it is null or not null");
                }
        }
        parseValue (i, criteria, ad);
    }

    private Object parseValue(int i, CriteriaInfo criteria, AttributeDefinitionInfo ad) throws InvalidParameterException {
        String fk = criteria.getFieldKey();
        String op = criteria.getOperator();
        String fv = criteria.getValue();
        if (fv == null) {
            return null;
        }
        switch (ad.getDataType()) {
            case STRING:
                return parseString(i, fv);
            case DATE:
                return parseDateTime(i, fv);
            case TRUNCATED_DATE:
                return parseDate(i, fv);
            case BOOLEAN:
                return parseBoolean(i, fv);
            case INTEGER:
                return parseInteger(i, fv);
            case FLOAT:
                return parseFloat(i, fv);
            case DOUBLE:
                return parseDouble(i, fv);
            case LONG:
                return parseLong(i, fv);
            case COMPLEX:
                throw new InvalidParameterException("The " + i + "th criteria's value is not null but attribute type is complex. Complex can only be checked to see if it is null or not null");
            default:
                throw new IllegalArgumentException("Unknown/unhandled datatype " + ad.getDataType());
        }
    }

    private String parseString(int i, String fv) throws InvalidParameterException {
        return fv;
    }

    private Timestamp parseDateTime(int i, String fv) throws InvalidParameterException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd-hh.mm.ss");
        try {
            return new Timestamp(df.parse(fv).getTime());
        } catch (ParseException ex) {
            throw new InvalidParameterException("The " + i + "th criteria's value " + fv + " cannot be parsed as a dateTime");
        }
    }

    private Date parseDate(int i, String fv) throws InvalidParameterException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return df.parse(fv);
        } catch (ParseException ex) {
            throw new InvalidParameterException("The " + i + "th criteria's value " + fv + " cannot be parsed as a date");
        }
    }

    private Integer parseInteger(int i, String fv) throws InvalidParameterException {
        try {
            return Integer.parseInt(fv);
        } catch (NumberFormatException ex) {
            throw new InvalidParameterException("The " + i + "th criteria's value " + fv + " cannot be parsed as an integer");
        }
    }

    private Long parseLong(int i, String fv) throws InvalidParameterException {
        try {
            return Long.parseLong(fv);
        } catch (NumberFormatException ex) {
            throw new InvalidParameterException("The " + i + "th criteria's value " + fv + " cannot be parsed as a Long");
        }
    }

    private Boolean parseBoolean(int i, String fv) throws InvalidParameterException {
        if (fv.equalsIgnoreCase("true")) {
            return Boolean.TRUE;
        }
        if (fv.equalsIgnoreCase("false")) {
            return Boolean.FALSE;
        }
        throw new InvalidParameterException("The " + i + "th criteria's value " + fv + " cannot be parsed as a Boolean");

    }

    private Float parseFloat(int i, String fv) throws InvalidParameterException {
        try {
            return Float.parseFloat(fv);
        } catch (NumberFormatException ex) {
            throw new InvalidParameterException("The " + i + "th criteria's value " + fv + " cannot be parsed as an float");
        }
    }

    private Double parseDouble(int i, String fv) throws InvalidParameterException {
        try {
            return Double.parseDouble(fv);
        } catch (NumberFormatException ex) {
            throw new InvalidParameterException("The " + i + "th criteria's value " + fv + " cannot be parsed as an double");
        }
    }

    private String initLower(String str) {
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

    private boolean calcIsList(AttributeDefinitionInfo ad) {
        if (ad.getMaxOccurs() == null) {
            return false;
        }
        if (ad.getMaxOccurs() <= 1) {
            return false;
        }
        return true;

    }

    private DictionaryEntryInfo getDictionaryEntry()
            throws InvalidParameterException,
            OperationFailedException {
        DictionaryEntryInfo entry = null;
        try {
            entry = dataDictionaryService.getDataDictionaryEntry(dictEntryKey, context);
        } catch (MissingParameterException ex) {
            throw new OperationFailedException("failed checking dictionary", ex);
        } catch (PermissionDeniedException ex) {
            throw new OperationFailedException("failed checking dictionary", ex);
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("failed checking dictionary", ex);
        }
        return entry;
    }

    private AttributeDefinitionInfo getAttributeDefinition(String fk)
            throws InvalidParameterException,
            OperationFailedException {
        return this.getAttributeDefinition(this.getDictionaryEntry(), fk);
    }

    private AttributeDefinitionInfo getAttributeDefinition(DictionaryEntryInfo entry, String fk) {
        return this.getAttributeDefinition(entry, fk);
    }

    private AttributeDefinitionInfo getAttributeDefinition(List<AttributeDefinitionInfo> attributes, String fk) {
        for (AttributeDefinitionInfo ad : attributes) {
            if (ad.getName().equals(fk)) {
                return ad;
            }
        }
        return null;
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

