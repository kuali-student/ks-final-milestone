/**
 * Copyright 2010 The Kuali Foundation Licensed under the Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing permissions and limitations under the License.
 */

package org.kuali.student.common.validator.poc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.apache.log4j.Logger;
import org.kuali.student.common.util.MessageUtils;
import org.kuali.student.core.dictionary.poc.dto.CaseConstraint;
import org.kuali.student.core.dictionary.poc.dto.Constraint;
import org.kuali.student.core.dictionary.poc.dto.DataType;
import org.kuali.student.core.dictionary.poc.dto.FieldDefinition;
import org.kuali.student.core.dictionary.poc.dto.LookupConstraint;
import org.kuali.student.core.dictionary.poc.dto.LookupConstraintParamMapping;
import org.kuali.student.core.dictionary.poc.dto.MustOccurConstraint;
import org.kuali.student.core.dictionary.poc.dto.ObjectStructureDefinition;
import org.kuali.student.core.dictionary.poc.dto.RequiredConstraint;
import org.kuali.student.core.dictionary.poc.dto.ValidCharsConstraint;
import org.kuali.student.core.dictionary.poc.dto.WhenConstraint;
import org.kuali.student.core.messages.dto.Message;
import org.kuali.student.core.messages.service.MessageService;
import org.kuali.student.core.search.dto.SearchParam;
import org.kuali.student.core.search.dto.SearchRequest;
import org.kuali.student.core.search.dto.SearchResult;
import org.kuali.student.core.search.service.SearchService;
import org.kuali.student.core.validation.dto.ValidationResultInfo;

public class Validator {
    final static Logger LOG = Logger.getLogger(Validator.class);

    private MessageService messageService = null;

    private SearchService searchService;

    private String messageLocaleKey = "en";

    private String messageGroupKey = "validation";

    private DateParser dateParser = new ServerDateParser();

    private boolean serverSide = true;

    public MessageService getMessageService() {
        return messageService;
    }

    public void setMessageService(MessageService messageService) {
        this.messageService = messageService;
    }

    public String getMessageLocaleKey() {
        return messageLocaleKey;
    }

    public void setMessageLocaleKey(String messageLocaleKey) {
        this.messageLocaleKey = messageLocaleKey;
    }

    public String getMessageGroupKey() {
        return messageGroupKey;
    }

    public void setMessageGroupKey(String messageGroupKey) {
        this.messageGroupKey = messageGroupKey;
    }

    public void setDateParser(DateParser dateParser) {
        this.dateParser = dateParser;
    }

    /**
     * @return the serverSide
     */
    public boolean isServerSide() {
        return serverSide;
    }

    /**
     * @param serverSide
     *            the serverSide to set
     */
    public void setServerSide(boolean serverSide) {
        this.serverSide = serverSide;
    }

    /**
     * @return the dateParser
     */
    public DateParser getDateParser() {
        return dateParser;
    }

    /**
     * Validate Object and all its nested child objects for given type and state
     * 
     * @param data
     * @param objStructure
     * @return
     */
    public List<ValidationResultInfo> validateObject(Object data, ObjectStructureDefinition objStructure) {

        Stack<String> elementStack = new Stack<String>();
        return validateObject(data, objStructure, elementStack);
    }

    private List<ValidationResultInfo> validateObject(Object data, ObjectStructureDefinition objStructure, Stack<String> elementStack) {

       List<ValidationResultInfo> results = new ArrayList<ValidationResultInfo>();

        ConstraintDataProvider dataProvider = new BeanConstraintDataProvider();
        dataProvider.initialize(data);

        // Push object structure to the top of the stack
        StringBuilder objXPathElement = new StringBuilder(dataProvider.getPath());
        if (null != dataProvider.getObjectId()) {
            objXPathElement.append("[id='" + dataProvider.getObjectId() + "']");
        } else {
            objXPathElement.append("[id='null']");
        }
        elementStack.push(objXPathElement.toString());

        /*
         * Do nothing if the object to be validated is not type/state or if the objectstructure with constraints is not
         * provided
         */
        if (null == objStructure) {
            return results;
        }

        // Validate with the matching Type/State
        for (FieldDefinition f : objStructure.getAttributes()) {
            List<ValidationResultInfo> l = validateField(f, objStructure, dataProvider, elementStack);
            results.addAll(l);
        }
        elementStack.pop();

        /* All Field validations are returned right now */
        // List<ValidationResultInfo> resultsBuffer = new
        // ArrayList<ValidationResultInfo>();
        // for (ValidationResultContainer vc : results) {
        // if (skipFields.contains(vc.getElement()) == false) {
        // resultsBuffer.add(vc);
        // }
        // }
        // results = resultsBuffer;
        return results;
    }

    public List<ValidationResultInfo> validateField(FieldDefinition field, ObjectStructureDefinition objStruct, ConstraintDataProvider dataProvider, Stack<String> elementStack) {

        Object value = dataProvider.getValue(field.getName());
        List<ValidationResultInfo> results = new ArrayList<ValidationResultInfo>();

        // Handle null values in field
        if (value == null || "".equals(value.toString().trim())) {
            processConstraint(results, field, objStruct, value, dataProvider, elementStack);
            return results;
        }

        /*
         * For complex object structures only the following constraints apply 1. TypeStateCase 2. MinOccurs 3. MaxOccurs
         */
        if (DataType.COMPLEX.equals(field.getDataType())) {
            ObjectStructureDefinition nestedObjStruct = null;

            if (null != field.getDataObjectStructure()) {
                nestedObjStruct = field.getDataObjectStructure();
            }

            elementStack.push(field.getName());

            if (value instanceof Collection) {

                String xPath = getElementXpath(elementStack) + "/";

                for (Object o : (Collection<?>) value) {
                    processNestedObjectStructure(results, o, nestedObjStruct, field, elementStack);
                }
                if (field.getMinOccurs() != null && field.getMinOccurs() > ((Collection<?>) value).size()) {
                    ValidationResultInfo valRes = new ValidationResultInfo(xPath);
                    valRes.setError(MessageUtils.interpolate(getMessage("validation.minOccurs"), toMap(field)));
                    results.add(valRes);
                }

                Integer maxOccurs = tryParse(field.getMaxOccurs());
                if (maxOccurs != null && maxOccurs < ((Collection<?>) value).size()) {
                    ValidationResultInfo valRes = new ValidationResultInfo(xPath);
                    valRes.setError(MessageUtils.interpolate(getMessage("validation.maxOccurs"), toMap(field)));
                    results.add(valRes);
                }
            } else {
                if (null != value) {
                    processNestedObjectStructure(results, value, nestedObjStruct, field, elementStack);
                } else {
                    if (field.getMinOccurs() != null && field.getMinOccurs() > 0) {
                        ValidationResultInfo val = new ValidationResultInfo(getElementXpath(elementStack) + "[value='null']/");
                        val.setError(getMessage("validation.required"));
                        results.add(val);
                    }
                }
            }

            elementStack.pop();

        } else { // If non complex data type

            if (value instanceof Collection) {
                for (Object o : (Collection<?>) value) {
                    processConstraint(results, field, objStruct, o, dataProvider, elementStack);
                }

                String xPath = getElementXpath(elementStack) + field.getName() + "/";
                if (field.getMinOccurs() != null && field.getMinOccurs() > ((Collection<?>) value).size()) {
                    ValidationResultInfo valRes = new ValidationResultInfo(xPath);
                    valRes.setError(MessageUtils.interpolate(getMessage("validation.minOccurs"), toMap(field)));
                    results.add(valRes);
                }

                Integer maxOccurs = tryParse(field.getMaxOccurs());
                if (maxOccurs != null && maxOccurs < ((Collection<?>) value).size()) {
                    ValidationResultInfo valRes = new ValidationResultInfo(xPath);
                    valRes.setError(MessageUtils.interpolate(getMessage("validation.maxOccurs"), toMap(field)));
                    results.add(valRes);
                }
            } else {
                processConstraint(results, field, objStruct, value, dataProvider, elementStack);
            }

        }
        return results;
    }

    private Integer tryParse(String s) {
        Integer result = null;
        if (s != null) {
            try {
                result = Integer.valueOf(s);
            } catch (NumberFormatException e) {
                // do nothing
            }
        }
        return result;
    }

    private void processNestedObjectStructure(List<ValidationResultInfo> results, Object value, ObjectStructureDefinition nestedObjStruct, FieldDefinition field, Stack<String> elementStack) {

        results.addAll(validateObject(value, nestedObjStruct, elementStack));

    }

    private void processConstraint(List<ValidationResultInfo> valResults, FieldDefinition field, ObjectStructureDefinition objStructure, Object value, ConstraintDataProvider dataProvider, Stack<String> elementStack) {

        // If constraint is only to be processed on server side
        // TODO: Wire in custom Validator implementation here
        if (field.getCustomValidatorClass() != null || field.isServerSide() && !serverSide) {
            return;
        }

        // Process Case Constraint
        // Case Constraint are only evaluated on the field. Nested case constraints are currently ignored
        Constraint caseConstraint = processCaseConstraint(valResults, field, objStructure, value, dataProvider, elementStack);

        Constraint constraint = (null != caseConstraint) ? caseConstraint : field; 
        
        processBaseConstraints(valResults, constraint, field.getDataType(), field.getName(), value, elementStack);

        // Stop other checks if value is null
        if (value == null || "".equals(value.toString().trim())) {
            return;
        }

        String elementPath = getElementXpath(elementStack) + field.getName() + "[value='" + value.toString() + "']/";

        // Process Valid Chars
        if (null != constraint.getValidChars()) {
            ValidationResultInfo val = processValidCharConstraint(elementPath, constraint.getValidChars(), dataProvider, value);
            if (null != val) {
                valResults.add(val);
            }
        }

        // Process Require Constraints (only if this field has value)
        if (value != null && !"".equals(value.toString().trim())) {
            if (null != constraint.getRequireConstraint() && constraint.getRequireConstraint().size() > 0) {
                for (RequiredConstraint rc : constraint.getRequireConstraint()) {
                    ValidationResultInfo val = processRequireConstraint(elementPath, rc, field, objStructure, dataProvider);
                    if (null != val) {
                        valResults.add(val);
                    }
                }
            }
        }

        // Process Occurs Constraint
        if (null != constraint.getOccursConstraint() && constraint.getOccursConstraint().size() > 0) {
            for (MustOccurConstraint oc : constraint.getOccursConstraint()) {
                ValidationResultInfo val = processOccursConstraint(elementPath, oc, field, objStructure, dataProvider);
                if (null != val) {
                    valResults.add(val);
                }
            }
        }

        // Process lookup Constraint
        if (null != constraint.getLookupDefinition()) {
            processLookupConstraint(valResults, constraint.getLookupDefinition(), field, elementStack, dataProvider);
        }
    }

    private ValidationResultInfo processRequireConstraint(String element, RequiredConstraint constraint, FieldDefinition field, ObjectStructureDefinition objStructure, ConstraintDataProvider dataProvider) {

        ValidationResultInfo val = null;

        String fieldName = constraint.getFieldPath();// TODO parse fieldname from here
        Object fieldValue = dataProvider.getValue(fieldName);

        boolean result = true;

        if (fieldValue instanceof java.lang.String) {
            result = hasText((String) fieldValue);
        } else if (fieldValue instanceof Collection) {
            result = (((Collection<?>) fieldValue).size() > 0);
        } else {
            result = (null != fieldValue) ? true : false;
        }

        if (!result) {
            Map<String, Object> rMap = new HashMap<String, Object>();
            rMap.put("field1", field.getName());
            rMap.put("field2", fieldName);
            val = new ValidationResultInfo(element);
            val.setError(MessageUtils.interpolate(getMessage("validation.requiresField"), rMap));
        }

        return val;
    }

    /**
     * Process caseConstraint tag and sets any of the base constraint items if any of the when condition matches
     * 
     * @param constraint
     * @param caseConstraint
     * @param field
     */
    private Constraint processCaseConstraint(List<ValidationResultInfo> valResults, FieldDefinition field, ObjectStructureDefinition objStructure, Object value, ConstraintDataProvider dataProvider, Stack<String> elementStack) {

        CaseConstraint constraint = field.getCaseConstraint();

        if (null == constraint) { 
            return null;
        }

        String operator = (hasText(constraint.getOperator())) ? constraint.getOperator() : "EQUALS";
        FieldDefinition caseField = (hasText(constraint.getFieldPath())) ? ValidatorUtils.getField(constraint.getFieldPath(), objStructure) : null;

        // TODO: What happens when the field is not in the dataProvider?
        Object fieldValue = (null != caseField) ? dataProvider.getValue(caseField.getName()) : value;

        // If fieldValue is null then skip Case check
        if(null == fieldValue) {
            return null;
        }
        
        // Extract value for field Key
        for (WhenConstraint wc : constraint.getWhenConstraint()) {
            Object whenValue = wc.getValue();

            if (ValidatorUtils.compareValues(fieldValue, whenValue, caseField.getDataType(), operator, constraint.isCaseSensitive(), dateParser) && null != wc.getConstraint()) {
                return wc.getConstraint();
            }
        }
        
        return null;
    }

    private ValidationResultInfo processValidCharConstraint(String element, ValidCharsConstraint vcConstraint, ConstraintDataProvider dataProvider, Object value) {

        ValidationResultInfo val = null;

        StringBuilder fieldValue = new StringBuilder();
        String validChars = vcConstraint.getValue();

        fieldValue.append(ValidatorUtils.getString(value));

        int typIdx = validChars.indexOf(":");
        String processorType = "regex";
        if (-1 == typIdx) {
            validChars = "[" + validChars + "]*";
        } else {
            processorType = validChars.substring(0, typIdx);
            validChars = validChars.substring(typIdx + 1);
        }

        // TODO: Allow different processing based on the label
        if ("regex".equalsIgnoreCase(processorType)) {
            // if (!Pattern.matches(validChars, fieldValue.toString())) {
            if (fieldValue == null) {
                val = new ValidationResultInfo(element);
                val.setError(getMessage("validation.validCharsFailed"));
            } else if (fieldValue != null && !fieldValue.toString().matches(validChars)) {
                val = new ValidationResultInfo(element);
                val.setError(getMessage("validation.validCharsFailed"));
            }
        }

        return val;
    }

    /**
     * Computes if all the filed required in the occurs clause are between the min and max
     * 
     * @param valResults
     * @param constraint
     * @param field
     * @param type
     * @param state
     * @param objStructure
     * @param dataProvider
     * @return
     */
    private ValidationResultInfo processOccursConstraint(String element, MustOccurConstraint constraint, FieldDefinition field, ObjectStructureDefinition objStructure, ConstraintDataProvider dataProvider) {

        boolean result = false;
        int trueCount = 0;

        ValidationResultInfo val = null;

        for (RequiredConstraint rc : constraint.getRequiredFields()) {
            trueCount += (processRequireConstraint("", rc, field, objStructure, dataProvider) != null) ? 1 : 0;
        }

        for (MustOccurConstraint oc : constraint.getOccurs()) {
            trueCount += (processOccursConstraint("", oc, field, objStructure, dataProvider) != null) ? 1 : 0;
        }

        result = (trueCount >= constraint.getMin() && trueCount <= constraint.getMax()) ? true : false;

        if (!result) {
            val = new ValidationResultInfo(element);
            val.setError(getMessage("validation.occurs"));
        }

        return val;
    }

    // TODO: Implement lookup constraint
    private void processLookupConstraint(List<ValidationResultInfo> valResults, LookupConstraint lookupConstraint, FieldDefinition field, Stack<String> elementStack, ConstraintDataProvider dataProvider) {
        if (lookupConstraint == null) {
            return;
        }

        // Create search params based on the param mapping
        List<SearchParam> params = new ArrayList<SearchParam>();
        for (LookupConstraintParamMapping paramMapping : lookupConstraint.getLookupParams()) {
            SearchParam param = new SearchParam();

            param.setKey(paramMapping.getParamKey());

            // If the value of the search param comes form another field then get it
            if (paramMapping.getFieldPath() != null && !paramMapping.getFieldPath().isEmpty()) {
                Object fieldValue = dataProvider.getValue(paramMapping.getFieldPath());
                if (fieldValue instanceof String) {
                    param.setValue((String) fieldValue);
                } else if (fieldValue instanceof List<?>) {
                    param.setValue((List<String>) fieldValue);
                }
            } else if (paramMapping.getDefaultValueString() != null) {
                param.setValue(paramMapping.getDefaultValueString());
            } else {
                param.setValue(paramMapping.getDefaultValueList());
            }
            params.add(param);
        }

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setMaxResults(1);
        searchRequest.setStartAt(0);
        searchRequest.setNeededTotalResults(false);
        searchRequest.setSearchKey(lookupConstraint.getSearchTypeId());
        searchRequest.setParams(params);

        SearchResult searchResult = null;
        try {
            searchResult = searchService.search(searchRequest);
        } catch (Exception e) {
            LOG.info("Error calling Search", e);
        }
        if (searchResult == null || searchResult.getRows() == null || searchResult.getRows().isEmpty()) {
            ValidationResultInfo val = new ValidationResultInfo(getElementXpath(elementStack) + field.getName());
            val.setError(getMessage("validation.lookup"));
            valResults.add(val);
        }
    }

    private void processBaseConstraints(List<ValidationResultInfo> valResults, Constraint constraint, DataType dataType, String name, Object value, Stack<String> elementStack) {

        if (value == null || "".equals(value.toString().trim())) {
            if (constraint.getMinOccurs() != null && constraint.getMinOccurs() > 0) {
                ValidationResultInfo val = new ValidationResultInfo(getElementXpath(elementStack) + name + "[value='null']/");
                val.setError(getMessage("validation.required"));
                valResults.add(val);
            }
            return;
        }

        String elementPath = getElementXpath(elementStack) + name + "[value='" + value.toString() + "']/";

        if (DataType.STRING.equals(dataType)) {
            validateString(value, constraint, elementPath, valResults);
        } else if (DataType.INTEGER.equals(dataType)) {
            validateInteger(value, constraint, elementPath, valResults);
        } else if (DataType.LONG.equals(dataType)) {
            validateLong(value, constraint, elementPath, valResults);
        } else if (DataType.DOUBLE.equals(dataType)) {
            validateDouble(value, constraint, elementPath, valResults);
        } else if (DataType.FLOAT.equals(dataType)) {
            validateFloat(value, constraint, elementPath, valResults);
        } else if (DataType.BOOLEAN.equals(dataType)) {
            validateBoolean(value, constraint, elementPath, valResults);
        } else if (DataType.DATE.equals(dataType)) {
            validateDate(value, constraint, elementPath, valResults, dateParser);
        }
    }

    private void validateBoolean(Object value, Constraint constraint, String element, List<ValidationResultInfo> results) {
        if (!(value instanceof Boolean)) {
            try {
                Boolean.valueOf(value.toString());
            } catch (Exception e) {
                ValidationResultInfo val = new ValidationResultInfo(element);
                val.setError(getMessage("validation.mustBeBoolean"));
                results.add(val);
            }
        }
    }

    private void validateDouble(Object value, Constraint constraint, String element, List<ValidationResultInfo> results) {
        Double v = null;

        ValidationResultInfo val = new ValidationResultInfo(element);

        if (value instanceof Number) {
            v = ((Number) value).doubleValue();
        } else {
            try {
                v = Double.valueOf(value.toString());
            } catch (Exception e) {
                val.setError(getMessage("validation.mustBeDouble"));
            }
        }

        if (val.isOk()) {
            Double maxValue = ValidatorUtils.getDouble(constraint.getInclusiveMax());
            Double minValue = ValidatorUtils.getDouble(constraint.getExclusiveMin());

            if (maxValue != null && minValue != null) {
                // validate range
                if (v > maxValue || v < minValue) {
                    val.setError(MessageUtils.interpolate(getMessage("validation.outOfRange"), toMap(constraint)));
                }
            } else if (maxValue != null) {
                if (v > maxValue) {
                    val.setError(MessageUtils.interpolate(getMessage("validation.maxValueFailed"), toMap(constraint)));
                }
            } else if (minValue != null) {
                if (v < minValue) {
                    val.setError(MessageUtils.interpolate(getMessage("validation.minValueFailed"), toMap(constraint)));
                }
            }
        }

        if (!val.isOk()) {
            results.add(val);
        }
    }

    private void validateFloat(Object value, Constraint constraint, String element, List<ValidationResultInfo> results) {
        Float v = null;

        ValidationResultInfo val = new ValidationResultInfo(element);
        if (value instanceof Number) {
            v = ((Number) value).floatValue();
        } else {
            try {
                v = Float.valueOf(value.toString());
            } catch (Exception e) {
                val.setError(getMessage("validation.mustBeFloat"));
            }
        }

        if (val.isOk()) {
            Float maxValue = ValidatorUtils.getFloat(constraint.getInclusiveMax());
            Float minValue = ValidatorUtils.getFloat(constraint.getExclusiveMin());

            if (maxValue != null && minValue != null) {
                // validate range
                if (v > maxValue || v < minValue) {
                    val.setError(MessageUtils.interpolate(getMessage("validation.outOfRange"), toMap(constraint)));
                }
            } else if (maxValue != null) {
                if (v > maxValue) {
                    val.setError(MessageUtils.interpolate(getMessage("validation.maxValueFailed"), toMap(constraint)));
                }
            } else if (minValue != null) {
                if (v < minValue) {
                    val.setError(MessageUtils.interpolate(getMessage("validation.minValueFailed"), toMap(constraint)));
                }
            }
        }

        if (!val.isOk()) {
            results.add(val);
        }
    }

    private void validateLong(Object value, Constraint constraint, String element, List<ValidationResultInfo> results) {
        Long v = null;

        ValidationResultInfo val = new ValidationResultInfo(element);
        if (value instanceof Number) {
            v = ((Number) value).longValue();
        } else {
            try {
                v = Long.valueOf(value.toString());
            } catch (Exception e) {
                val.setError(getMessage("validation.mustBeLong"));
            }
        }

        if (val.isOk()) {
            Long maxValue = ValidatorUtils.getLong(constraint.getInclusiveMax());
            Long minValue = ValidatorUtils.getLong(constraint.getExclusiveMin());

            if (maxValue != null && minValue != null) {
                // validate range
                if (v > maxValue || v < minValue) {
                    val.setError(MessageUtils.interpolate(getMessage("validation.outOfRange"), toMap(constraint)));
                }
            } else if (maxValue != null) {
                if (v > maxValue) {
                    val.setError(MessageUtils.interpolate(getMessage("validation.maxValueFailed"), toMap(constraint)));
                }
            } else if (minValue != null) {
                if (v < minValue) {
                    val.setError(MessageUtils.interpolate(getMessage("validation.minValueFailed"), toMap(constraint)));
                }
            }
        }

        if (!val.isOk()) {
            results.add(val);
        }

    }

    private void validateInteger(Object value, Constraint constraint, String element, List<ValidationResultInfo> results) {
        Integer v = null;

        ValidationResultInfo val = new ValidationResultInfo(element);

        if (value instanceof Number) {
            v = ((Number) value).intValue();
        } else {
            try {
                v = Integer.valueOf(value.toString());
            } catch (Exception e) {
                val.setError(getMessage("validation.mustBeInteger"));
            }
        }

        if (val.isOk()) {
            Integer maxValue = ValidatorUtils.getInteger(constraint.getInclusiveMax());
            Integer minValue = ValidatorUtils.getInteger(constraint.getExclusiveMin());

            if (maxValue != null && minValue != null) {
                // validate range
                if (v > maxValue || v < minValue) {
                    val.setError(MessageUtils.interpolate(getMessage("validation.outOfRange"), toMap(constraint)));
                }
            } else if (maxValue != null) {
                if (v > maxValue) {
                    val.setError(MessageUtils.interpolate(getMessage("validation.maxValueFailed"), toMap(constraint)));
                }
            } else if (minValue != null) {
                if (v < minValue) {
                    val.setError(MessageUtils.interpolate(getMessage("validation.minValueFailed"), toMap(constraint)));
                }
            }
        }

        if (!val.isOk()) {
            results.add(val);
        }
    }

    private void validateDate(Object value, Constraint constraint, String element, List<ValidationResultInfo> results, DateParser dateParser) {
        ValidationResultInfo val = new ValidationResultInfo(element);

        Date v = null;

        if (value instanceof Date) {
            v = (Date) value;
        } else {
            try {
                v = dateParser.parseDate(value.toString());
            } catch (Exception e) {
                val.setError(getMessage("validation.mustBeDate"));
            }
        }

        if (val.isOk()) {
            Date maxValue = ValidatorUtils.getDate(constraint.getInclusiveMax(), dateParser);
            Date minValue = ValidatorUtils.getDate(constraint.getExclusiveMin(), dateParser);

            if (maxValue != null && minValue != null) {
                // validate range
                if (v.getTime() > maxValue.getTime() || v.getTime() < minValue.getTime()) {
                    val.setError(MessageUtils.interpolate(getMessage("validation.outOfRange"), toMap(constraint)));
                }
            } else if (maxValue != null) {
                if (v.getTime() > maxValue.getTime()) {
                    val.setError(MessageUtils.interpolate(getMessage("validation.maxValueFailed"), toMap(constraint)));
                }
            } else if (minValue != null) {
                if (v.getTime() < minValue.getTime()) {
                    val.setError(MessageUtils.interpolate(getMessage("validation.minValueFailed"), toMap(constraint)));
                }
            }
        }

        if (!val.isOk()) {
            results.add(val);
        }
    }

    private void validateString(Object value, Constraint constraint, String element, List<ValidationResultInfo> results) {

        if (value == null) {
            value = "";
        }
        String s = value.toString().trim();

        ValidationResultInfo val = new ValidationResultInfo(element);

        Integer maxLength = tryParse(constraint.getMaxLength());
        if (maxLength != null && constraint.getMinLength() != null && constraint.getMinLength() > 0) {
            if (s.length() > maxLength || s.length() < constraint.getMinLength()) {
                val.setError(MessageUtils.interpolate(getMessage("validation.lengthOutOfRange"), toMap(constraint)));
            }
        } else if (maxLength != null) {
            if (s.length() > Integer.parseInt(constraint.getMaxLength())) {
                val.setError(MessageUtils.interpolate(getMessage("validation.maxLengthFailed"), toMap(constraint)));
            }
        } else if (constraint.getMinLength() != null && constraint.getMinLength() > 0) {
            if (s.length() < constraint.getMinLength()) {
                val.setError(MessageUtils.interpolate(getMessage("validation.minLengthFailed"), toMap(constraint)));
            }
        }

        if (!val.isOk()) {
            results.add(val);
        }
    }

    private String getMessage(String messageId) {
        if (null == messageService) {
            return messageId;
        }

        Message msg = messageService.getMessage(messageLocaleKey, messageGroupKey, messageId);

        return msg.getValue();
    }

    private String getElementXpath(Stack<String> elementStack) {
        StringBuilder xPath = new StringBuilder();
        xPath.append("/");
        Iterator<String> itr = elementStack.iterator();
        while (itr.hasNext()) {
            xPath.append(itr.next() + "/");
        }

        return xPath.toString();
    }

    /*
     * Homemade has text so we dont need outside libs.
     */
    private boolean hasText(String string) {

        if (string == null || string.length() < 1) {
            return false;
        }
        int stringLength = string.length();

        for (int i = 0; i < stringLength; i++) {
            char currentChar = string.charAt(i);
            if (' ' != currentChar || '\t' != currentChar || '\n' != currentChar) {
                return true;
            }
        }

        return false;
    }

    private Map<String, Object> toMap(Constraint c) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("minOccurs", c.getMinOccurs());
        result.put("maxOccurs", c.getMaxOccurs());
        result.put("minLength", c.getMinLength());
        result.put("maxLength", c.getMaxLength());
        result.put("minValue", c.getExclusiveMin());
        result.put("maxValue", c.getInclusiveMax());
        // result.put("dataType", c.getDataType());

        return result;
    }

    public SearchService getSearchService() {
        return searchService;
    }

    public void setSearchService(SearchService searchService) {
        this.searchService = searchService;
    }
}
