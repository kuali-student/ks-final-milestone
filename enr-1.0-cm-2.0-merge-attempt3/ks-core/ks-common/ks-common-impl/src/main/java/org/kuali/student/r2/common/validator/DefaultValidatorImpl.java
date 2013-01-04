/**
 * Copyright 2010 The Kuali Foundation Licensed under the Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing permissions and limitations under the License.
 */

package org.kuali.student.r2.common.validator;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.kuali.student.common.util.MessageUtils;
import org.kuali.student.r1.common.dictionary.dto.CaseConstraint;
import org.kuali.student.r1.common.dictionary.dto.CommonLookupParam;
import org.kuali.student.r1.common.dictionary.dto.Constraint;
import org.kuali.student.r1.common.dictionary.dto.DataType;
import org.kuali.student.r1.common.dictionary.dto.FieldDefinition;
import org.kuali.student.r1.common.dictionary.dto.LookupConstraint;
import org.kuali.student.r1.common.dictionary.dto.MustOccurConstraint;
import org.kuali.student.r1.common.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.r1.common.dictionary.dto.RequiredConstraint;
import org.kuali.student.r1.common.dictionary.dto.ValidCharsConstraint;
import org.kuali.student.r1.common.dictionary.dto.WhenConstraint;
import org.kuali.student.r2.core.search.dto.SearchParamInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.service.SearchService;
import org.kuali.student.r1.common.validator.BeanConstraintDataProvider;
import org.kuali.student.r1.common.validator.ConstraintDataProvider;
import org.kuali.student.r1.common.validator.DateParser;
import org.kuali.student.r1.common.validator.ServerDateParser;
import org.kuali.student.r1.common.validator.ValidatorUtils;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.LocaleInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.infc.ValidationResult.ErrorLevel;
import org.kuali.student.r2.common.messages.dto.MessageInfo;
import org.kuali.student.r2.common.messages.service.MessageService;
import org.springframework.beans.BeanUtils;

// This class is a special case, this class/equivelent doesn't exist in R2
// packages and is a common and has methods used in both R1 and R2 packages,
// this class was duplicated to R2 and modified to work with R2 services
// BaseAbstractValidator, BaseAbstractValidator, Validator, ValidatorFactory

public class DefaultValidatorImpl extends BaseAbstractValidator {
    final static Logger LOG = Logger.getLogger(DefaultValidatorImpl.class);

    private MessageService messageService = null;

    private SearchService searchDispatcher;

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

    public List<ValidationResultInfo> validateObject(Object data, ObjectStructureDefinition objStructure, ContextInfo contextInfo) {

        ObjectStructureHierarchy objectStructureHierarchy = new ObjectStructureHierarchy();
        objectStructureHierarchy.setObjectStructure(objStructure);
        return validateObject(data, objectStructureHierarchy, contextInfo);
    }

    /**
     * Validate Object and all its nested child objects for given type and state
     * 
     * @param data
     * @param objStructure
     * @return
     */
    public List<ValidationResultInfo> validateObject(Object data, ObjectStructureHierarchy objStructure, ContextInfo contextInfo) {

        List<ValidationResultInfo> results = new ArrayList<ValidationResultInfo>();
        Stack<String> elementStack = new Stack<String>();

        validateObject(results, data, objStructure, elementStack, data, objStructure.getObjectStructure(), true, null, contextInfo);

        return results;
    }

    private void validateObject(List<ValidationResultInfo> results, Object data, ObjectStructureHierarchy objStructure, Stack<String> elementStack, Object rootData,
                                ObjectStructureDefinition rootObjStructure, boolean isRoot, ConstraintDataProvider parentDataProvider, ContextInfo contextInfo) {

        ConstraintDataProvider dataProvider = new BeanConstraintDataProvider();
        dataProvider.initialize(data);
        if (parentDataProvider != null){
            dataProvider.setParent(parentDataProvider);
        }

        // Push object structure to the top of the stack
        StringBuilder objXPathElement = new StringBuilder(dataProvider.getPath());

        if (!isRoot && !objXPathElement.toString().isEmpty()) {
            elementStack.push(objXPathElement.toString());
        }

        /*
         * Do nothing if the object to be validated is not type/state or if the objectstructure with constraints is not
         * provided
         */
        if (null == objStructure.getObjectStructure()) {
            return;
        }

        for (FieldDefinition f : objStructure.getObjectStructure().getAttributes()) {
            validateField(results, f, objStructure, dataProvider, elementStack, rootData, rootObjStructure, contextInfo);

            // Use Custom Validators
            if (f.getCustomValidatorClass() != null || f.isServerSide() && serverSide) {
                Validator customValidator = validatorFactory.getValidator(f.getCustomValidatorClass());
                if (customValidator == null) {
                    throw new RuntimeException("Custom Validator " + f.getCustomValidatorClass() + " was not configured in this context");
                }
                List<ValidationResultInfo> l = customValidator.validateObject(f, data, objStructure.getObjectStructure(), elementStack, null);
                results.addAll(l);
            }
        }
        if (!isRoot && !objXPathElement.toString().isEmpty()) {
            elementStack.pop();
        }

        /* All Field validations are returned right now */
        // List<ValidationResultInfo> resultsBuffer = new
        // ArrayList<ValidationResultInfo>();
        // for (ValidationResultContainer vc : results) {
        // if (skipFields.contains(vc.getElement()) == false) {
        // resultsBuffer.add(vc);
        // }
        // }
        // results = resultsBuffer;
    }

    public void validateField(List<ValidationResultInfo> results, FieldDefinition field, ObjectStructureHierarchy objStruct, ConstraintDataProvider dataProvider, Stack<String> elementStack, Object rootData, ObjectStructureDefinition rootObjectStructure, ContextInfo contextInfo) {

        Object value = dataProvider.getValue(field.getName());

        // Handle null values in field
        if (value == null || "".equals(value.toString().trim())) {
            processConstraint(results, field, objStruct, value, dataProvider, elementStack, rootData, rootObjectStructure, contextInfo);
            return; // no need to do further processing
        }

        /*
         * For complex object structures only the following constraints apply 1. TypeStateCase 2. MinOccurs 3. MaxOccurs
         */
        if (DataType.COMPLEX.equals(field.getDataType())) {
            ObjectStructureHierarchy nestedObjStruct = new ObjectStructureHierarchy();
            nestedObjStruct.setParentObjectStructureHierarchy(objStruct);

            if (null != field.getDataObjectStructure()) {
                nestedObjStruct.setObjectStructure(field.getDataObjectStructure());
            }

            elementStack.push(field.getName());
            // beanPathStack.push(field.isDynamic()?"attributes("+field.getName()+")":field.getName());

            if (value instanceof Collection) {

                String xPathForCollection = getElementXpath(elementStack) + "/*";

                int i = 0;
                for (Object o : (Collection<?>) value) {
                    elementStack.push(Integer.toString(i));
                    // beanPathStack.push(!beanPathStack.isEmpty()?beanPathStack.pop():""+"["+i+"]");
                    processNestedObjectStructure(results, o, nestedObjStruct, field, elementStack, rootData, rootObjectStructure, dataProvider, contextInfo);
                    // beanPathStack.pop();
                    // beanPathStack.push(field.isDynamic()?"attributes("+field.getName()+")":field.getName());
                    elementStack.pop();
                    i++;
                }
                if (field.getMinOccurs() != null && field.getMinOccurs() > ((Collection<?>) value).size()) {
                    ValidationResultInfo valRes = new ValidationResultInfo(xPathForCollection, value);
                    valRes.setError(MessageUtils.interpolate(getMessage("validation.minOccurs", contextInfo), toMap(field)));
                    results.add(valRes);
                }

                Integer maxOccurs = tryParse(field.getMaxOccurs());
                if (maxOccurs != null && maxOccurs < ((Collection<?>) value).size()) {
                    ValidationResultInfo valRes = new ValidationResultInfo(xPathForCollection, value);
                    valRes.setError(MessageUtils.interpolate(getMessage("validation.maxOccurs", contextInfo), toMap(field)));
                    results.add(valRes);
                }
            } else {
                if (null != value) {
                    processNestedObjectStructure(results, value, nestedObjStruct, field, elementStack, rootData, rootObjectStructure, dataProvider, contextInfo);
                } else {
                    if (field.getMinOccurs() != null && field.getMinOccurs() > 0) {
                        ValidationResultInfo val = new ValidationResultInfo(getElementXpath(elementStack), value);
                        if (field.getLabelKey() != null) {
                            val.setError(getMessage(field.getLabelKey(), contextInfo));
                        } else {
                            val.setError(getMessage("validation.required", contextInfo));
                        }
                        results.add(val);
                    }
                }
            }

            // beanPathStack.pop();
            elementStack.pop();

        } else { // If non complex data type

            if (value instanceof Collection) {

                if (((Collection<?>) value).isEmpty()) {
                    processConstraint(results, field, objStruct, "", dataProvider, elementStack, rootData, rootObjectStructure, contextInfo);
                }

                int i = 0;
                for (Object o : (Collection<?>) value) {
                    // This is tricky, change the field name to the index in the elementStack(this is for lists of non
                    // complex types)
                    elementStack.push(field.getName());
                    FieldDefinition tempField = new FieldDefinition();
                    BeanUtils.copyProperties(field, tempField);
                    tempField.setName(Integer.toBinaryString(i));
                    processConstraint(results, tempField, objStruct, o, dataProvider, elementStack, rootData, rootObjectStructure, contextInfo);
                    elementStack.pop();
                    i++;
                }

                String xPath = getElementXpath(elementStack) + "/" + field.getName() + "/*";
                if (field.getMinOccurs() != null && field.getMinOccurs() > ((Collection<?>) value).size()) {
                    ValidationResultInfo valRes = new ValidationResultInfo(xPath, value);
                    valRes.setError(MessageUtils.interpolate(getMessage("validation.minOccurs", contextInfo), toMap(field)));
                    results.add(valRes);
                }

                Integer maxOccurs = tryParse(field.getMaxOccurs());
                if (maxOccurs != null && maxOccurs < ((Collection<?>) value).size()) {
                    ValidationResultInfo valRes = new ValidationResultInfo(xPath, value);
                    valRes.setError(MessageUtils.interpolate(getMessage("validation.maxOccurs", contextInfo), toMap(field)));
                    results.add(valRes);
                }
            } else {
                processConstraint(results, field, objStruct, value, dataProvider, elementStack, rootData, rootObjectStructure, contextInfo);
            }

        }
    }

    protected Integer tryParse(String s) {
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

    protected void processNestedObjectStructure(List<ValidationResultInfo> results, Object value, ObjectStructureHierarchy nestedObjStruct, FieldDefinition field,
                                                Stack<String> elementStack, Object rootData, ObjectStructureDefinition rootObjStructure, ConstraintDataProvider parentDataProvider, ContextInfo contextInfo) {
        validateObject(results, value, nestedObjStruct, elementStack, rootData, rootObjStructure, false, parentDataProvider, contextInfo);
    }

    protected void processConstraint(List<ValidationResultInfo> valResults, FieldDefinition field, ObjectStructureHierarchy objStructure, Object value, ConstraintDataProvider dataProvider, Stack<String> elementStack, Object rootData, ObjectStructureDefinition rootObjStructure, ContextInfo contextInfo) {

        // Process Case Constraint
        // Case Constraint are only evaluated on the field. Nested case constraints are currently ignored
        Constraint caseConstraint = processCaseConstraint(valResults, field.getCaseConstraint(), objStructure, value, dataProvider, elementStack, rootData, rootObjStructure, contextInfo);

        Constraint constraint = (null != caseConstraint) ? caseConstraint : field;

        processBaseConstraints(valResults, constraint, field, value, elementStack, contextInfo);

        // Stop other checks if value is null
        if (value == null || "".equals(value.toString().trim())) {
            return;
        }

        String elementPath = getElementXpath(elementStack) + "/" + field.getName();

        // Process Valid Chars
        if (null != constraint.getValidChars()) {
            ValidationResultInfo val = processValidCharConstraint(elementPath, constraint.getValidChars(), dataProvider, value, contextInfo);
            if (null != val) {
                valResults.add(val);
            }
        }

        // Process Require Constraints (only if this field has value)
        if (value != null && !"".equals(value.toString().trim())) {
            if (null != constraint.getRequireConstraint() && constraint.getRequireConstraint().size() > 0) {
                for (RequiredConstraint rc : constraint.getRequireConstraint()) {
                    ValidationResultInfo val = processRequireConstraint(elementPath, rc, field, objStructure.getObjectStructure(), dataProvider, contextInfo);
                    if (null != val) {
                        valResults.add(val);
                        // FIXME: For clarity, might be better to handle this in the processRequireConstraint method instead.
                        processCrossFieldWarning(valResults, rc, val.getErrorLevel(), field.getName(), contextInfo);
                    }
                }
            }
        }

        // Process Occurs Constraint
        if (null != constraint.getOccursConstraint() && constraint.getOccursConstraint().size() > 0) {
            for (MustOccurConstraint oc : constraint.getOccursConstraint()) {
                ValidationResultInfo val = processOccursConstraint(elementPath, oc, field, objStructure.getObjectStructure(), dataProvider, contextInfo);
                if (null != val) {
                    valResults.add(val);
                }
            }
        }

        // Process lookup Constraint
        if (null != constraint.getLookupDefinition()) {
            processLookupConstraint(valResults, constraint.getLookupDefinition(), field, elementStack, dataProvider, objStructure.getObjectStructure(), rootData, rootObjStructure, value, contextInfo);
        }
    }

    protected ValidationResultInfo processRequireConstraint(String element, RequiredConstraint constraint, FieldDefinition field, ObjectStructureDefinition objStructure, ConstraintDataProvider dataProvider, ContextInfo contextInfo) {

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
            val = new ValidationResultInfo(element, fieldValue);
            val.setMessage(MessageUtils.interpolate(getMessage("validation.requiresField", contextInfo), rMap));
            val.setLevel(constraint.getErrorLevel());
        }

        return val;
    }

    /**
     * Process caseConstraint tag and sets any of the base constraint items if any of the when condition matches
     * 
     * @param valResults
     * @param caseConstraint
     * @param objStructure
     */
    protected Constraint processCaseConstraint(List<ValidationResultInfo> valResults, CaseConstraint caseConstraint, ObjectStructureHierarchy objStructure, Object value, ConstraintDataProvider dataProvider, Stack<String> elementStack, Object rootData, ObjectStructureDefinition rootObjStructure, ContextInfo contextInfo) {

        if (null == caseConstraint) {
            return null;
        }

        String operator = (hasText(caseConstraint.getOperator())) ? caseConstraint.getOperator() : "EQUALS";
        FieldDefinition caseField = null;
        boolean absolutePath = false;
        if (hasText(caseConstraint.getFieldPath())) {
            if (caseConstraint.getFieldPath().startsWith("/")) {
                absolutePath = true;
                caseField = ValidatorUtils.getField(caseConstraint.getFieldPath().substring(1), rootObjStructure);
            } else {
                caseField = ValidatorUtils.getField(caseConstraint.getFieldPath(), objStructure);
            }
        }

        // TODO: What happens when the field is not in the dataProvider?
        Object fieldValue = value;
        if (caseField != null) {
            if (absolutePath) {
                try {
                    if (caseField.isDynamic()) {
                        // Pull the value from the dynamic attribute map
                        // TODO There needs to be some mapping from PropertyUtils to the KS path
                        // Until then, this will only work for root level properties
                        Map<String, String> attributes = null;
                        Object atts = PropertyUtils.getNestedProperty(rootData, "attributes");
                        if (atts instanceof Map<?, ?>) {
                            attributes = (Map<String, String>) atts;
                        } else {
                            List<AttributeInfo> attToMap = (List<AttributeInfo>) atts;
                            if (attToMap != null) {
                                for (AttributeInfo atin : attToMap) {

                                    try {
                                        attributes.put(atin.getKey(), atin.getValue());
                                    } catch (Exception e) {
                                        System.out.print("Failed at " + rootData.getClass().getName() + " for object attributes");

                                    }
                                }
                            }
                        }

                        if (attributes != null) {
                            fieldValue = attributes.get(caseConstraint.getFieldPath().substring(1));
                        }
                    } else {
                        fieldValue = PropertyUtils.getNestedProperty(rootData, caseConstraint.getFieldPath().substring(1));
                    }
                } catch (IllegalAccessException e) {} catch (InvocationTargetException e) {} catch (NoSuchMethodException e) {}
            } else {
                fieldValue = ValidatorUtils.getFieldValue(caseConstraint.getFieldPath(), dataProvider);
            }
        }
        DataType fieldDataType = (null != caseField ? caseField.getDataType() : null);

        // If fieldValue is null then skip Case check
        if (null == fieldValue) {
            return null;
        }

        // Extract value for field Key
        for (WhenConstraint wc : caseConstraint.getWhenConstraint()) {

            if (hasText(wc.getValuePath())) {
                Object whenValue = null;
                if (wc.getValuePath().startsWith("/")) {
                    try {
                        whenValue = PropertyUtils.getNestedProperty(rootData, wc.getValuePath().substring(1));
                    } catch (IllegalAccessException e) {} catch (InvocationTargetException e) {} catch (NoSuchMethodException e) {}
                } else {
                    whenValue = dataProvider.getValue(wc.getValuePath());
                }
                if (ValidatorUtils.compareValues(fieldValue, whenValue, fieldDataType, operator, caseConstraint.isCaseSensitive(), dateParser) && null != wc.getConstraint()) {
                    Constraint constraint = wc.getConstraint();
                    if (constraint.getCaseConstraint() != null) {
                        return processCaseConstraint(valResults, constraint.getCaseConstraint(), objStructure, value, dataProvider, elementStack, rootData, rootObjStructure, contextInfo);
                    } else {
                        processCrossFieldWarning(valResults, caseConstraint, constraint, value, constraint.getErrorLevel(), contextInfo);
                        return constraint;
                    }
                }
            } else {
                List<Object> whenValueList = wc.getValues();

                for (Object whenValue : whenValueList) {
                    if (ValidatorUtils.compareValues(fieldValue, whenValue, fieldDataType, operator, caseConstraint.isCaseSensitive(), dateParser) && null != wc.getConstraint()) {
                        Constraint constraint = wc.getConstraint();
                        if (constraint.getCaseConstraint() != null) {
                            return processCaseConstraint(valResults, constraint.getCaseConstraint(), objStructure, value, dataProvider, elementStack, rootData, rootObjStructure, contextInfo);
                        } else {
                            processCrossFieldWarning(valResults, caseConstraint, constraint, value, constraint.getErrorLevel(), contextInfo);
                            return constraint;
                        }
                    }
                }
            }
        }

        return null;
    }

    public ValidationResultInfo processValidCharConstraint(String element, ValidCharsConstraint vcConstraint, ConstraintDataProvider dataProvider, Object value, ContextInfo contextInfo) {

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

        if ("regex".equalsIgnoreCase(processorType)) {
            if (fieldValue == null || !fieldValue.toString().matches(validChars)) {
                val = new ValidationResultInfo(element, fieldValue);
                if (vcConstraint.getLabelKey() != null) {
                    val.setError(getMessage(vcConstraint.getLabelKey(), contextInfo));
                } else {
                    val.setError(getMessage("validation.validCharsFailed", contextInfo));
                }
            }
        }

        return val;
    }

    /**
     * Computes if all the filed required in the occurs clause are between the min and max
     * 
     * @param element
     * @param constraint
     * @param field
     * @param objStructure
     * @param dataProvider
     * @return
     */
    protected ValidationResultInfo processOccursConstraint(String element, MustOccurConstraint constraint, FieldDefinition field, ObjectStructureDefinition objStructure, ConstraintDataProvider dataProvider, ContextInfo contextInfo) {

        boolean result = false;
        int trueCount = 0;

        ValidationResultInfo val = null;

        for (RequiredConstraint rc : constraint.getRequiredFields()) {
            trueCount += (processRequireConstraint("", rc, field, objStructure, dataProvider, contextInfo) != null) ? 1 : 0;
        }

        for (MustOccurConstraint oc : constraint.getOccurs()) {
            trueCount += (processOccursConstraint("", oc, field, objStructure, dataProvider, contextInfo) != null) ? 1 : 0;
        }

        result = (trueCount >= constraint.getMin() && trueCount <= constraint.getMax()) ? true : false;

        if (!result) {
            // TODO: figure out what data should go here instead of null
            val = new ValidationResultInfo(element, null);
            val.setMessage(getMessage("validation.occurs", contextInfo));
            val.setLevel(constraint.getErrorLevel());
        }

        return val;
    }

    // TODO: Implement lookup constraint
    protected void processLookupConstraint(List<ValidationResultInfo> valResults, LookupConstraint lookupConstraint, FieldDefinition field, Stack<String> elementStack, ConstraintDataProvider dataProvider, ObjectStructureDefinition objStructure, Object rootData, ObjectStructureDefinition rootObjStructure, Object value, ContextInfo contextInfo) {
        if (lookupConstraint == null) {
            return;
        }

        // Create search params based on the param mapping
        List<SearchParamInfo> params = new ArrayList<SearchParamInfo>();

        for (CommonLookupParam paramMapping : lookupConstraint.getParams()) {
            // Skip params that are the search param id key
            if (lookupConstraint.getSearchParamIdKey() != null && lookupConstraint.getSearchParamIdKey().equals(paramMapping.getKey())) {
                continue;
            }

            SearchParamInfo param = new SearchParamInfo();

            param.setKey(paramMapping.getKey());

            // If the value of the search param comes form another field then get it
            if (paramMapping.getFieldPath() != null && !paramMapping.getFieldPath().isEmpty()) {
                FieldDefinition lookupField = null;
                boolean absolutePath = false;
                if (hasText(paramMapping.getFieldPath())) {
                    if (paramMapping.getFieldPath().startsWith("/")) {
                        absolutePath = true;
                        lookupField = ValidatorUtils.getField(paramMapping.getFieldPath().substring(1), rootObjStructure);
                    } else {
                        lookupField = ValidatorUtils.getField(paramMapping.getFieldPath(), objStructure);
                    }
                }
                Object fieldValue = null;
                if (lookupField != null) {
                    if (absolutePath) {
                        try {
                            if (lookupField.isDynamic()) {
                                // Pull the value from the dynamic attribute map
                                // Until then, this will only work for root level properties
                                Map<String, String> attributes = (Map<String, String>) PropertyUtils.getNestedProperty(rootData, "attributes");
                                if (attributes != null) {
                                    fieldValue = attributes.get(paramMapping.getFieldPath().substring(1));
                                }
                            } else {
                                fieldValue = PropertyUtils.getNestedProperty(rootData, paramMapping.getFieldPath().substring(1));
                            }
                        } catch (IllegalAccessException e) {} catch (InvocationTargetException e) {} catch (NoSuchMethodException e) {}
                    } else {
                        fieldValue = dataProvider.getValue(lookupField.getName());
                    }
                } else {
                    fieldValue = dataProvider.getValue(paramMapping.getFieldPath());
                }

                if (fieldValue instanceof String) {
                    param.getValues().add((String) fieldValue);
                } else if (fieldValue instanceof List<?>) {
                    param.setValues((List<String>) fieldValue);
                }
            } else if (paramMapping.getDefaultValueString() != null) {
                param.getValues().add(paramMapping.getDefaultValueString());
            } else {
                param.setValues(paramMapping.getDefaultValueList());
            }
            params.add(param);
        }

        if (lookupConstraint.getSearchParamIdKey() != null) {
            SearchParamInfo param = new SearchParamInfo();
            param.setKey(lookupConstraint.getSearchParamIdKey());
            if (value instanceof String) {
                param.getValues().add((String) value);
            } else if (value instanceof List<?>) {
                param.setValues((List<String>) value);
            }
            params.add(param);
        }

        SearchRequestInfo searchRequest = new SearchRequestInfo();
        searchRequest.setMaxResults(1);
        searchRequest.setStartAt(0);
        searchRequest.setNeededTotalResults(false);
        searchRequest.setSearchKey(lookupConstraint.getSearchTypeId());
        searchRequest.setParams(params);

        SearchResultInfo searchResult = null;
        try {
            searchResult = searchDispatcher.search(searchRequest, contextInfo);
        } catch (Exception e) {
            LOG.info("Error calling Search", e);
        }
        // If there are no search results then make a validation result
        if (searchResult == null || searchResult.getRows() == null || searchResult.getRows().isEmpty()) {
            ValidationResultInfo val = new ValidationResultInfo(getElementXpath(elementStack) + "/" + field.getName(), value);
            val.setLevel(lookupConstraint.getErrorLevel());
            val.setMessage(getMessage("validation.lookup", contextInfo));
            valResults.add(val);
            processCrossFieldWarning(valResults, lookupConstraint, lookupConstraint.getErrorLevel(), contextInfo);
        }
    }

    protected void processBaseConstraints(List<ValidationResultInfo> valResults, Constraint constraint, FieldDefinition field, Object value, Stack<String> elementStack, ContextInfo contextInfo) {
        DataType dataType = field.getDataType();
        String name = field.getName();

        if (value == null || "".equals(value.toString().trim())) {
            if (constraint.getMinOccurs() != null && constraint.getMinOccurs() > 0) {
                ValidationResultInfo val = new ValidationResultInfo(getElementXpath(elementStack) + "/" + name, value);
                if (constraint.getLabelKey() != null) {
                    val.setError(getMessage(constraint.getLabelKey(), contextInfo));
                } else {
                    val.setMessage(getMessage("validation.required", contextInfo));
                }
                val.setLevel(constraint.getErrorLevel());
                valResults.add(val);
            }
            return;
        }

        String elementPath = getElementXpath(elementStack) + "/" + name;

        if (DataType.STRING.equals(dataType)) {
            validateString(value, constraint, elementPath, valResults, contextInfo);
        } else if (DataType.INTEGER.equals(dataType)) {
            validateInteger(value, constraint, elementPath, valResults, contextInfo);
        } else if (DataType.LONG.equals(dataType)) {
            validateLong(value, constraint, elementPath, valResults, contextInfo);
        } else if (DataType.DOUBLE.equals(dataType)) {
            validateDouble(value, constraint, elementPath, valResults, contextInfo);
        } else if (DataType.FLOAT.equals(dataType)) {
            validateFloat(value, constraint, elementPath, valResults, contextInfo);
        } else if (DataType.BOOLEAN.equals(dataType)) {
            validateBoolean(value, constraint, elementPath, valResults, contextInfo);
        } else if (DataType.DATE.equals(dataType)) {
            validateDate(value, constraint, elementPath, valResults, dateParser, contextInfo);
        }
    }

    /**
     * This adds a warning on the related field when a processed case-constraint results in a warning
     * 
     * @param valResults
     * @param crossConstraint
     * @param constraint
     */
    protected void processCrossFieldWarning(List<ValidationResultInfo> valResults, CaseConstraint crossConstraint, Constraint constraint, Object value, ErrorLevel errorLevel, ContextInfo contextInfo) {
        if ((ErrorLevel.WARN == errorLevel || ErrorLevel.ERROR == errorLevel) && (value == null || "".equals(value.toString().trim()))) {
            if (constraint.getMinOccurs() != null && constraint.getMinOccurs() > 0) {

                String crossFieldPath = crossConstraint.getFieldPath();
                String crossFieldMessageId = crossConstraint.getFieldPathMessageId() == null ? "validation.required" : crossConstraint.getFieldPathMessageId();
                addCrossFieldWarning(valResults, crossFieldPath, getMessage(crossFieldMessageId, contextInfo), errorLevel);
            }
        }
    }

    /**
     * This adds a warning on the related field when a processed case-constraint results in a warning
     * 
     * @param valResults
     * @param requiredConstraint
     * @param field
     */
    protected void processCrossFieldWarning(List<ValidationResultInfo> valResults, RequiredConstraint requiredConstraint, ErrorLevel errorLevel, String field, ContextInfo contextInfo) {
        if ((ErrorLevel.WARN == errorLevel || ErrorLevel.ERROR == errorLevel) && requiredConstraint != null) {
            String crossFieldPath = requiredConstraint.getFieldPath();
            String crossFieldMessageId = requiredConstraint.getFieldPathMessageId() == null ? "validation.required" : requiredConstraint.getFieldPathMessageId();
            addCrossFieldWarning(valResults, crossFieldPath, getMessage(crossFieldMessageId, contextInfo), errorLevel);
        }
    }

    /**
     * This adds a warning on the related field when a processed lookup-constraint results in a warning
     * 
     * @param valResults
     * @param lookupConstraint
     */
    protected void processCrossFieldWarning(List<ValidationResultInfo> valResults, LookupConstraint lookupConstraint, ErrorLevel errorLevel, ContextInfo contextInfo) {
        if ((ErrorLevel.WARN == errorLevel || ErrorLevel.ERROR == errorLevel) && lookupConstraint != null) {
            for (CommonLookupParam param : lookupConstraint.getParams()) {
                if (param.getFieldPath() != null && !param.getFieldPath().isEmpty()) {
                    String crossFieldPath = param.getFieldPath();
                    String crossFieldMessageId = param.getFieldPathMessageId() == null ? "validation.lookup.cause" : param.getFieldPathMessageId();
                    addCrossFieldWarning(valResults, crossFieldPath, getMessage(crossFieldMessageId, contextInfo), errorLevel);
                }
            }
        }
    }

    protected void addCrossFieldWarning(List<ValidationResultInfo> valResults, String crossFieldPath, String message, ErrorLevel errorLevel) {
        // Check to see if the exact same validation message already exists on referenced field
        boolean warnAlreadyExists = false;
        for (ValidationResultInfo vr : valResults) {
            if (vr.getElement().equals(crossFieldPath) && vr.getMessage().equals(message)) {
                warnAlreadyExists = true;
            }
        }

        // Only add this warning, if it hasn't been already added
        if (!warnAlreadyExists) {
            ValidationResultInfo val = new ValidationResultInfo(crossFieldPath, null);
            val.setMessage(message);
            val.setLevel(errorLevel);
            valResults.add(val);
        }
    }

    protected void validateBoolean(Object value, Constraint constraint, String element, List<ValidationResultInfo> results, ContextInfo contextInfo) {
        if (!(value instanceof Boolean)) {
            try {
                Boolean.valueOf(value.toString());
            } catch (Exception e) {
                ValidationResultInfo val = new ValidationResultInfo(element, value);
                val.setError(getMessage("validation.mustBeBoolean", contextInfo));
                results.add(val);
            }
        }
    }

    protected void validateDouble(Object value, Constraint constraint, String element, List<ValidationResultInfo> results, ContextInfo contextInfo) {
        Double v = null;

        ValidationResultInfo val = new ValidationResultInfo(element, value);

        if (value instanceof Number) {
            v = ((Number) value).doubleValue();
        } else {
            try {
                v = Double.valueOf(value.toString());
            } catch (Exception e) {
                val.setError(getMessage("validation.mustBeDouble", contextInfo));
            }
        }

        if (val.isOk()) {
            Double maxValue = ValidatorUtils.getDouble(constraint.getInclusiveMax());
            Double minValue = ValidatorUtils.getDouble(constraint.getExclusiveMin());

            if (maxValue != null && minValue != null) {
                // validate range
                if (v > maxValue || v < minValue) {
                    val.setError(MessageUtils.interpolate(getMessage("validation.outOfRange", contextInfo), toMap(constraint)));
                }
            } else if (maxValue != null) {
                if (v > maxValue) {
                    val.setError(MessageUtils.interpolate(getMessage("validation.maxValueFailed", contextInfo), toMap(constraint)));
                }
            } else if (minValue != null) {
                if (v < minValue) {
                    val.setError(MessageUtils.interpolate(getMessage("validation.minValueFailed", contextInfo), toMap(constraint)));
                }
            }
        }

        if (!val.isOk()) {
            results.add(val);
        }
    }

    protected void validateFloat(Object value, Constraint constraint, String element, List<ValidationResultInfo> results, ContextInfo contextInfo) {
        Float v = null;

        ValidationResultInfo val = new ValidationResultInfo(element, value);
        if (value instanceof Number) {
            v = ((Number) value).floatValue();
        } else {
            try {
                v = Float.valueOf(value.toString());
            } catch (Exception e) {
                val.setError(getMessage("validation.mustBeFloat", contextInfo));
            }
        }

        if (val.isOk()) {
            Float maxValue = ValidatorUtils.getFloat(constraint.getInclusiveMax());
            Float minValue = ValidatorUtils.getFloat(constraint.getExclusiveMin());

            if (maxValue != null && minValue != null) {
                // validate range
                if (v > maxValue || v < minValue) {
                    val.setError(MessageUtils.interpolate(getMessage("validation.outOfRange", contextInfo), toMap(constraint)));
                }
            } else if (maxValue != null) {
                if (v > maxValue) {
                    val.setError(MessageUtils.interpolate(getMessage("validation.maxValueFailed", contextInfo), toMap(constraint)));
                }
            } else if (minValue != null) {
                if (v < minValue) {
                    val.setError(MessageUtils.interpolate(getMessage("validation.minValueFailed", contextInfo), toMap(constraint)));
                }
            }
        }

        if (!val.isOk()) {
            results.add(val);
        }
    }

    protected void validateLong(Object value, Constraint constraint, String element, List<ValidationResultInfo> results, ContextInfo contextInfo) {
        Long v = null;

        ValidationResultInfo val = new ValidationResultInfo(element, value);
        if (value instanceof Number) {
            v = ((Number) value).longValue();
        } else {
            try {
                v = Long.valueOf(value.toString());
            } catch (Exception e) {
                val.setError(getMessage("validation.mustBeLong", contextInfo));
            }
        }

        if (val.isOk()) {
            Long maxValue = ValidatorUtils.getLong(constraint.getInclusiveMax());
            Long minValue = ValidatorUtils.getLong(constraint.getExclusiveMin());

            if (maxValue != null && minValue != null) {
                // validate range
                if (v > maxValue || v < minValue) {
                    val.setError(MessageUtils.interpolate(getMessage("validation.outOfRange", contextInfo), toMap(constraint)));
                }
            } else if (maxValue != null) {
                if (v > maxValue) {
                    val.setError(MessageUtils.interpolate(getMessage("validation.maxValueFailed", contextInfo), toMap(constraint)));
                }
            } else if (minValue != null) {
                if (v < minValue) {
                    val.setError(MessageUtils.interpolate(getMessage("validation.minValueFailed", contextInfo), toMap(constraint)));
                }
            }
        }

        if (!val.isOk()) {
            results.add(val);
        }

    }

    protected void validateInteger(Object value, Constraint constraint, String element, List<ValidationResultInfo> results, ContextInfo contextInfo) {
        Integer v = null;

        ValidationResultInfo val = new ValidationResultInfo(element, value);

        if (value instanceof Number) {
            v = ((Number) value).intValue();
        } else {
            try {
                v = Integer.valueOf(value.toString());
            } catch (Exception e) {
                val.setError(getMessage("validation.mustBeInteger", contextInfo));
            }
        }

        if (val.isOk()) {
            Integer maxValue = ValidatorUtils.getInteger(constraint.getInclusiveMax());
            Integer minValue = ValidatorUtils.getInteger(constraint.getExclusiveMin());

            if (maxValue != null && minValue != null) {
                // validate range
                if (v > maxValue || v < minValue) {
                    val.setError(MessageUtils.interpolate(getMessage("validation.outOfRange", contextInfo), toMap(constraint)));
                }
            } else if (maxValue != null) {
                if (v > maxValue) {
                    val.setError(MessageUtils.interpolate(getMessage("validation.maxValueFailed", contextInfo), toMap(constraint)));
                }
            } else if (minValue != null) {
                if (v < minValue) {
                    val.setError(MessageUtils.interpolate(getMessage("validation.minValueFailed", contextInfo), toMap(constraint)));
                }
            }
        }

        if (!val.isOk()) {
            results.add(val);
        }
    }

    protected void validateDate(Object value, Constraint constraint, String element, List<ValidationResultInfo> results, DateParser dateParser, ContextInfo contextInfo) {
        ValidationResultInfo val = new ValidationResultInfo(element, value);

        Date v = null;

        if (value instanceof Date) {
            v = (Date) value;
        } else {
            try {
                v = dateParser.parseDate(value.toString());
            } catch (Exception e) {
                val.setError(getMessage("validation.mustBeDate", contextInfo));
            }
        }

        if (val.isOk()) {
            Date maxValue = ValidatorUtils.getDate(constraint.getInclusiveMax(), dateParser);
            Date minValue = ValidatorUtils.getDate(constraint.getExclusiveMin(), dateParser);

            if (maxValue != null && minValue != null) {
                // validate range
                if (v.getTime() > maxValue.getTime() || v.getTime() < minValue.getTime()) {
                    val.setError(MessageUtils.interpolate(getMessage("validation.outOfRange", contextInfo), toMap(constraint)));
                }
            } else if (maxValue != null) {
                if (v.getTime() > maxValue.getTime()) {
                    val.setError(MessageUtils.interpolate(getMessage("validation.maxValueFailed", contextInfo), toMap(constraint)));
                }
            } else if (minValue != null) {
                if (v.getTime() < minValue.getTime()) {
                    val.setError(MessageUtils.interpolate(getMessage("validation.minValueFailed", contextInfo), toMap(constraint)));
                }
            }
        }

        if (!val.isOk()) {
            results.add(val);
        }
    }

    protected void validateString(Object value, Constraint constraint, String element, List<ValidationResultInfo> results, ContextInfo contextInfo) {

        if (value == null) {
            value = "";
        }
        String s = value.toString().trim();

        ValidationResultInfo val = new ValidationResultInfo(element, value);

        Integer maxLength = tryParse(constraint.getMaxLength());
        if (maxLength != null && constraint.getMinLength() != null && constraint.getMinLength() > 0) {
            if (s.length() > maxLength || s.length() < constraint.getMinLength()) {
                val.setError(MessageUtils.interpolate(getMessage("validation.lengthOutOfRange", contextInfo), toMap(constraint)));
            }
        } else if (maxLength != null) {
            if (s.length() > Integer.parseInt(constraint.getMaxLength())) {
                val.setError(MessageUtils.interpolate(getMessage("validation.maxLengthFailed", contextInfo), toMap(constraint)));
            }
        } else if (constraint.getMinLength() != null && constraint.getMinLength() > 0) {
            if (s.length() < constraint.getMinLength()) {
                val.setError(MessageUtils.interpolate(getMessage("validation.minLengthFailed", contextInfo), toMap(constraint)));
            }
        }

        if (!val.isOk()) {
            results.add(val);
        }
    }

    protected String getMessage(String messageId, ContextInfo contextInfo) {
        if (null == messageService) {
            return messageId;
        }

        // TODO: this need to be properly implemented.
        LocaleInfo locale = new LocaleInfo();
        locale.setLocaleLanguage(messageLocaleKey);
        MessageInfo msg = null;
        try {
            msg = messageService.getMessage(locale, messageGroupKey, messageId, contextInfo);
        } catch (DoesNotExistException e) {
            return "";
        } catch (InvalidParameterException e) {
            return "";
        } catch (MissingParameterException e) {
            return "";
        } catch (OperationFailedException e) {
            return "";
        } catch (PermissionDeniedException e) {
            return "";
        }

        return msg.getValue();
    }

    protected String getElementXpath(Stack<String> elementStack) {
        StringBuilder xPath = new StringBuilder();
        Iterator<String> itr = elementStack.iterator();
        while (itr.hasNext()) {
            xPath.append(itr.next());
            if (itr.hasNext()) {
                xPath.append("/");
            }
        }

        return xPath.toString();
    }

    /*
     * Homemade has text so we dont need outside libs.
     */
    protected boolean hasText(String string) {

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

    protected Map<String, Object> toMap(Constraint c) {
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

    public SearchService getSearchDispatcher() {
        return searchDispatcher;
    }

    public void setSearchDispatcher(SearchService searchDispatcher) {
        this.searchDispatcher = searchDispatcher;
    }

    @Override
    public List<ValidationResultInfo> validateObject(FieldDefinition field, Object o, ObjectStructureDefinition objStructure, Stack<String> elementStack, ContextInfo contextInfo) {
        return null;
    }
}
