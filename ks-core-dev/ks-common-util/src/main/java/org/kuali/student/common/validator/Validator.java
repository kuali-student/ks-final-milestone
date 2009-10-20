/*
 * Copyright 2009 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 * 
 * http://www.osedu.org/licenses/ECL-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.kuali.student.common.validator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.kuali.student.common.util.MessageUtils;
import org.kuali.student.core.dictionary.dto.CaseConstraint;
import org.kuali.student.core.dictionary.dto.ConstraintDescriptor;
import org.kuali.student.core.dictionary.dto.ConstraintSelector;
import org.kuali.student.core.dictionary.dto.Field;
import org.kuali.student.core.dictionary.dto.LookupConstraint;
import org.kuali.student.core.dictionary.dto.ObjectStructure;
import org.kuali.student.core.dictionary.dto.OccursConstraint;
import org.kuali.student.core.dictionary.dto.RequireConstraint;
import org.kuali.student.core.dictionary.dto.State;
import org.kuali.student.core.dictionary.dto.Type;
import org.kuali.student.core.dictionary.dto.TypeStateCaseConstraint;
import org.kuali.student.core.dictionary.dto.ValidCharsConstraint;
import org.kuali.student.core.dictionary.dto.WhenConstraint;
import org.kuali.student.core.messages.dto.Message;
import org.kuali.student.core.validation.dto.ValidationResultContainer;

public class Validator {

	private static final String UNBOUNDED_CHECK = null;

	private Map<String, String> messages = new HashMap<String, String>();

	private Stack<String> elementStack = new Stack<String>();
    private List<String> skipFields = new ArrayList<String>();
	private DateParser dateParser = null;

	private ConstraintSetupFactory setupFactory = null;

	private boolean serverSide = true;

	public Validator(ConstraintSetupFactory setupFactory, boolean serverSide) {
		this.setupFactory = setupFactory;
		this.serverSide = serverSide;
		
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
	 * @param dateParser
	 *            the dateParser to set
	 */
	public void setDateParser(DateParser dateParser) {
		this.dateParser = dateParser;
	}

	public void addMessages(List<Message> list) {
		for (Message m : list) {
			messages.put(m.getId(), m.getValue());
		}
	}
	private String getMessage(String id){
	    if(messages.get(id) == null){
	        return id;
	    }
	    return messages.get(id);
	}

	/**
	 * Validate Object and all its nested child objects for given type and state
	 * 
	 * @param data
	 * @param objStructure
	 * @return
	 */
	public List<ValidationResultContainer> validateTypeStateObject(
			Object data, ObjectStructure objStructure) {
		List<ValidationResultContainer> results = new ArrayList<ValidationResultContainer>();

		ConstraintDataProvider dataProvider = setupFactory
				.getDataProvider(data);

		boolean isTypeStateObject = (dataProvider.hasField("type") && dataProvider
				.hasField("state"));

		// Push object structure to the top of the stack
		//StringBuilder objXPathElement = new StringBuilder(objStructure.getKey());
		StringBuilder objXPathElement = new StringBuilder(dataProvider.getPath());
		if (null != dataProvider.getObjectId()) {
			objXPathElement.append("[id='" + dataProvider.getObjectId() + "']");
		}
		elementStack.push(objXPathElement.toString());

		// We are making the assumption that all objects being validated has
		// Type and State
		if (!isTypeStateObject) {
			throw new IllegalArgumentException(
					"Non TypeState object being validated:"
							+ dataProvider.getObjectId());
		}

		// Validate with the matching Type/State
		List<Type> types = objStructure.getType();
		for (Type t : types) {
			if (t.getKey().equalsIgnoreCase(
					(String) dataProvider.getValue("type"))) {
				for (State s : t.getState()) {
					if (s.getKey().equalsIgnoreCase(
							(String) dataProvider.getValue("state"))) {
						for (Field f : s.getField()) {

						    List<ValidationResultContainer> l = validateField(f, t, s, objStructure,dataProvider);
							results.addAll(l);
						}
						break;
					}
				}
				break;
			}
		}
		  elementStack.pop();
		  
		   // Joe start
		  List<ValidationResultContainer> resultsBuffer = new ArrayList<ValidationResultContainer>();
	            for(ValidationResultContainer vc: results){
	                if(skipFields.contains(vc.getElement()) == false){
                        resultsBuffer.add(vc);
                    }
	            }
	            results = resultsBuffer;
          //Joe end
		return results;
	}
	public void setSkipFields(List<String> list){
	    skipFields = list;	    
	}
    private boolean isNullable(Field field){
        ConstraintDescriptor cd = field.getConstraintDescriptor();
        if (null != cd) {
	        List<ConstraintSelector> constraintList = cd.getConstraint();
	        for(ConstraintSelector cs: constraintList){
	            if(cs.getMinOccurs() != null && cs.getMinOccurs() > 0){
	                return false;
	            }
	        }
        }
        return true;
    }
	public List<ValidationResultContainer> validateField(Field field,
			Type type, State state, ObjectStructure objStruct,
			ConstraintDataProvider dataProvider) {

		Object value = dataProvider.getValue(field.getKey());
		List<ValidationResultContainer> results = new ArrayList<ValidationResultContainer>();
        System.out.println("validator:"+field.getKey());
        System.out.println("value:"+value);
        System.out.println("Complex:"+field.getFieldDescriptor().getDataType());
        
		// added by joe
        if (value == null || "".equals(value.toString().trim())) {
            if(isNullable(field) == false){
               ValidationResultContainer valResults = new ValidationResultContainer(
                            getElementXpath() + field.getKey());
              valResults.addError(getMessage("validation.required"));
              results.add(valResults);

            }
            return results;
        }   
        // added finished

		// Check to see if the Field is not a complex type
		if ("complex"
				.equalsIgnoreCase(field.getFieldDescriptor().getDataType())) {
			ObjectStructure nestedObjStruct = null;
			if (hasText(field.getFieldDescriptor()
					.getObjectStructureRef())) {
				nestedObjStruct = setupFactory.getObjectStructure(field
						.getFieldDescriptor().getObjectStructureRef());
			} else {
				nestedObjStruct = field.getFieldDescriptor()
						.getObjectStructure();
			}

			if (value instanceof Collection) {
				for (Object o : (Collection<?>) value) {
					processNestedObjectStructure(results, o, nestedObjStruct,
							field);
				}
// added by Joe
	//		} else if(value == null){
	  //             ValidationResultContainer valResults = new ValidationResultContainer(
        //                   getElementXpath() + field.getKey() + "/");
          //   valResults.addError("cannot be null");
            // results.add(valResults);
             // return results;
// added by Joe finished			    
			}else{
				processNestedObjectStructure(results, value, nestedObjStruct,
						field);
			}
		} else { // If non complex data type
			ConstraintDescriptor cd = field.getConstraintDescriptor();
			if (null != cd) {

				List<ConstraintSelector> constraints = cd.getConstraint();

				if (value instanceof Collection) {

					// TODO: Right now bcb is computed for each object. Change
					// this so that it is only computed
					// for the first object
					BaseConstraintBean bcb = new BaseConstraintBean();
					for (Object o : (Collection<?>) value) {
						String xPath = getElementXpath() + field.getKey()
								+ "[value='" + o.toString() + "']/";
						ValidationResultContainer valResults = new ValidationResultContainer(
								xPath);

						for (ConstraintSelector constraint : constraints) {
							processConstraint(valResults, constraint, field,
									type, state, objStruct, o, dataProvider,
									bcb);
						}
						processBaseConstraints(valResults, bcb, field, o);

						if (bcb.minOccurs > ((Collection<?>) value).size()) {
							valResults.addError(getMessage("validation.minOccurs"));
						}

						Integer maxOccurs = tryParse(bcb.maxOccurs);
						if (maxOccurs != null && maxOccurs < ((Collection<?>) value).size()) {
							valResults.addError(getMessage("validation.maxOccurs"));
						}

						results.add(valResults);
					}
				} else {
					ValidationResultContainer valResults = new ValidationResultContainer(
							getElementXpath() + field.getKey());

					BaseConstraintBean bcb = new BaseConstraintBean();
					for (ConstraintSelector constraint : constraints) {
						processConstraint(valResults, constraint, field, type,
								state, objStruct, value, dataProvider, bcb);
					}
					processBaseConstraints(valResults, bcb, field, value);

					results.add(valResults);
				}
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

	private void processNestedObjectStructure(
			List<ValidationResultContainer> results, Object value,
			ObjectStructure nestedObjStruct, Field field) {
	    
	    results.addAll(validateTypeStateObject(value, nestedObjStruct));
		
		// CD should have only one type state case constraint
		ConstraintDescriptor cd = field.getConstraintDescriptor();
		if (null != cd) {
			ConstraintSelector cs = cd.getConstraint().get(0);
			TypeStateCaseConstraint tscs = cs.getTypeStateCaseConstraint();
			if (null != tscs) {
			//    ValidationResultContainer vc = new ValidationResultContainer();
				//processTypeStateCaseConstraint(vc);
				//results.add(vc);
			}
		}
	}

	private void processConstraint(ValidationResultContainer valResults,
			ConstraintSelector constraint, Field field, Type type, State state,
			ObjectStructure objStructure, Object value,
			ConstraintDataProvider dataProvider, BaseConstraintBean bcb) {

		// If constraint is only to be processed on server side
		if (hasText(constraint.getClassName())
				|| constraint.isServerSide() && !serverSide) {
			return;
		}

		if (null != constraint.getMinLength()) {
			bcb.minLength = (bcb.minLength > constraint.getMinLength()) ? bcb.minLength
					: constraint.getMinLength();
		}

		if (null != constraint.getMinOccurs()) {
			bcb.minOccurs = (bcb.minOccurs > constraint.getMinOccurs()) ? bcb.minOccurs
					: constraint.getMinOccurs();
		}

		if (null != constraint.getMinValue()) {
			bcb.minValue = (null == bcb.minValue || ValidatorUtils
					.compareValues(bcb.minValue, constraint.getMinValue(),
							field.getFieldDescriptor().getDataType(),
							"GREATER_THAN", dateParser)) ? constraint
					.getMinValue() : bcb.minValue;
		}

		if (null != constraint.getMaxValue()) {
			bcb.maxValue = (null == bcb.maxValue || ValidatorUtils
					.compareValues(bcb.maxValue, constraint.getMaxValue(),
							field.getFieldDescriptor().getDataType(),
							"LESS_THAN", dateParser)) ? constraint
					.getMaxValue() : bcb.maxValue;
		}

		if (hasText(constraint.getMaxLength())) {
			Integer maxLength = tryParse(bcb.maxLength);
			Integer constraintMaxLength = tryParse(constraint.getMaxLength());
			if (maxLength == null) {
				bcb.maxLength = constraint.getMaxLength();
			} else if (constraintMaxLength != null) {
				if (constraintMaxLength > maxLength) {
					bcb.maxLength = constraint.getMaxLength();
				}
			}
		}

		if (hasText(constraint.getMaxOccurs())) {
			Integer maxOccurs = tryParse(bcb.maxOccurs);
			Integer constraintMaxOccurs = tryParse(constraint.getMaxOccurs());
			if (maxOccurs == null) {
				bcb.maxOccurs = constraint.getMaxOccurs();
			} else if (constraintMaxOccurs != null) {
				if (constraintMaxOccurs > maxOccurs) {
					bcb.maxOccurs = constraint.getMaxOccurs();
				}
			}
		}

		// Process Valid Chars
		if (null != constraint.getValidChars()) {
			processValidCharConstraint(valResults, constraint.getValidChars(),
					dataProvider, value);
		}

		// Process Require Constraints (only if this field has value)
		if (value != null && !"".equals(value.toString().trim())) {
			if (null != constraint.getRequireConstraint()
					&& constraint.getRequireConstraint().size() > 0) {
				for (RequireConstraint rc : constraint.getRequireConstraint()) {
					processRequireConstraint(valResults, rc, field, objStructure,
							dataProvider);
				}
			}
		}

		// Process Occurs Constraint
		if (null != constraint.getOccursConstraint()
				&& constraint.getOccursConstraint().size() > 0) {
			for (OccursConstraint oc : constraint.getOccursConstraint()) {
				processOccursConstraint(valResults, oc, field, type, state,
						objStructure, dataProvider);
			}
		}

		// Process lookup Constraint
		// TODO: Implement lookup constraint
		if (null != constraint.getLookupConstraint()
				&& constraint.getLookupConstraint().size() > 0) {
			for (LookupConstraint lc : constraint.getLookupConstraint()) {
				processLookupConstraint(valResults);
			}
		}

		// Process Case Constraint
		if (null != constraint.getCaseConstraint()
				&& constraint.getCaseConstraint().size() > 0) {
			for (CaseConstraint cc : constraint.getCaseConstraint()) {
				processCaseConstraint(valResults, cc, field, type, state,
						objStructure, value, dataProvider, bcb);
			}
		}
	}

	private boolean processRequireConstraint(
			ValidationResultContainer valResults,
			RequireConstraint constraint, Field field, ObjectStructure objStructure,
			ConstraintDataProvider dataProvider) {

		boolean result = false;

		String fieldName = constraint.getField();
		Object fieldValue = dataProvider.getValue(fieldName);

		if (fieldValue instanceof java.lang.String) {
			result = hasText((String) fieldValue);
		} else if (fieldValue instanceof java.util.Collection) {
			result = (((Collection<?>) fieldValue).size() > 0);
		} else {
			result = (null != fieldValue) ? true : false;
		}

		if (!result) {
			Map<String, Object> rMap = new HashMap<String, Object>();
			rMap.put("field1", field.getKey());
			rMap.put("field2", fieldName);
			valResults.addError(MessageUtils.interpolate(getMessage("validation.requiresField"), rMap));
		}

		return result;
	}

	/**
	 * Process caseConstraint tag and sets any of the base constraint items if
	 * any of the when condition matches
	 * 
	 * @param bcb
	 * @param caseConstraint
	 * @param field
	 */
	private void processCaseConstraint(
			ValidationResultContainer valResults,
			CaseConstraint constraint, Field field, Type type, State state,
			ObjectStructure objStructure, Object value,
			ConstraintDataProvider dataProvider, BaseConstraintBean bcb) {

		String operator = (hasText(constraint.getOperator())) ? constraint
				.getOperator()
				: "EQUALS";
		Field caseField = (hasText(constraint.getField())) ? ValidatorUtils
				.getField(constraint.getField(), objStructure, type.getKey(),
						state.getKey())
				: null;

		// TODO: What happens when the field is not in the dataProvider?
		Object fieldValue = (null != caseField) ? dataProvider
				.getValue(caseField.getKey()) : value;

		// Extract value for field Key
		for (WhenConstraint wc : constraint.getWhenConstraint()) {
			String whenValue = wc.getValue();

			if (ValidatorUtils.compareValues(fieldValue, whenValue,
					caseField.getFieldDescriptor().getDataType(), operator,
					dateParser)) {
				processConstraint(valResults, wc.getConstraint(), field, type,
						state, objStructure, value, dataProvider, bcb);
			}
		}
	}

	private void processValidCharConstraint(
			ValidationResultContainer valResults,
			ValidCharsConstraint vcConstraint,
			ConstraintDataProvider dataProvider, Object value) {

		StringBuilder fieldValue = new StringBuilder();
		String validChars = vcConstraint.getValue();
		String fields = vcConstraint.getFields();

		if (hasText(fields)) {
			String separator = vcConstraint.getSeparator();
			String[] fieldNameList = fields.split(",");

			int sz = fieldNameList.length;

			for (String fieldName : fieldNameList) {
				Object v = dataProvider.getValue(fieldName);
				fieldValue.append(ValidatorUtils.getString(v));

				if (--sz > 0) {
					fieldValue.append(separator);
				}
			}
		} else {
			fieldValue.append(ValidatorUtils.getString(value));
		}

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
			//if (!Pattern.matches(validChars, fieldValue.toString())) {
		    if(fieldValue == null){
                valResults
                .addError(getMessage("validation.validCharsFailed"));
		    }else if (fieldValue != null && !fieldValue.toString().matches(validChars)) {
				valResults
						.addError(getMessage("validation.validCharsFailed"));
			}
		}
	}

	/**
	 * Computes if all the filed required in the occurs clause are between the
	 * min and max
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
	private boolean processOccursConstraint(
			ValidationResultContainer valResults,
			OccursConstraint constraint, Field field, Type type, State state,
			ObjectStructure objStructure, ConstraintDataProvider dataProvider) {

		boolean result = false;
		int trueCount = 0;

		ValidationResultContainer tempC = new ValidationResultContainer(
				null);

		for (RequireConstraint rc : constraint.getRequire()) {
			trueCount += (processRequireConstraint(tempC, rc, field, objStructure,
					dataProvider)) ? 1 : 0;
		}

		for (OccursConstraint oc : constraint.getOccurs()) {
			trueCount += (processOccursConstraint(tempC, oc, field, type,
					state, objStructure, dataProvider)) ? 1 : 0;
		}

		result = (trueCount >= constraint.getMin() && trueCount <= constraint
				.getMax()) ? true : false;

		if (!result) {
			valResults.addError(getMessage("validation.occurs"));
		}

		return result;
	}

	private void processLookupConstraint(
			ValidationResultContainer valResults) {
	}

	private void processTypeStateCaseConstraint(
			ValidationResultContainer valResults) {
	}

	private void processBaseConstraints(
			ValidationResultContainer valResults, BaseConstraintBean bcb,
			Field field, Object value) {

		String dataType = field.getFieldDescriptor().getDataType();

		valResults.setDataType(dataType);
		valResults.setDerivedMinLength(bcb.minLength);
		valResults.setDerivedMaxLength(bcb.maxLength);
		valResults.setDerivedMinOccurs(bcb.minOccurs);
		valResults.setDerivedMaxOccurs(bcb.maxOccurs);

		if (value == null || "".equals(value.toString().trim())) {
			if (bcb.minOccurs != null && bcb.minOccurs > 0) {
				valResults.addError(getMessage("validation.required"));
				return;
			}
		}

		if ("string".equalsIgnoreCase(dataType)) {
			validateString(value, bcb, valResults);
		} else if ("integer".equalsIgnoreCase(dataType)) {
			validateInteger(value, bcb, valResults);
		} else if ("long".equalsIgnoreCase(dataType)) {
			validateLong(value, bcb, valResults);
		} else if ("double".equalsIgnoreCase(dataType)) {
			validateDouble(value, bcb, valResults);
		} else if ("float".equalsIgnoreCase(dataType)) {
			validateFloat(value, bcb, valResults);
		} else if ("boolean".equalsIgnoreCase(dataType)) {
			validateBoolean(value, bcb, valResults);
		} else if ("date".equalsIgnoreCase(dataType)) {
			validateDate(value, bcb, valResults, dateParser);
		}
	}

	private void validateBoolean(Object value, BaseConstraintBean bcb,
			ValidationResultContainer result) {
		if (!(value instanceof Boolean)) {
			try {
				Boolean.valueOf(value.toString());
			} catch (Exception e) {
				result.addError(getMessage("validation.mustBeBoolean"));
			}
		}
	}

	private void validateDouble(Object value, BaseConstraintBean bcb,
			ValidationResultContainer result) {
		Double v = null;
		if (value instanceof Number) {
			v = ((Number) value).doubleValue();
		} else {
			try {
				v = Double.valueOf(value.toString());
			} catch (Exception e) {
				result.addError(getMessage("validation.mustBeDouble"));
			}
		}

		if (result.isOk()) {
			Double maxValue = ValidatorUtils.getDouble(bcb.maxValue);
			Double minValue = ValidatorUtils.getDouble(bcb.minValue);

			if (maxValue != null && minValue != null) {
				// validate range
				if (v > maxValue || v < minValue) {
					result.addError(MessageUtils.interpolate(getMessage("validation.outOfRange"), bcb.toMap()));
				}
			} else if (maxValue != null) {
				if (v > maxValue) {
					result.addError(MessageUtils.interpolate(getMessage("validation.maxValueFailed"), bcb.toMap()));
				}
			} else if (minValue != null) {
				if (v < minValue) {
					result.addError(MessageUtils.interpolate(getMessage("validation.minValueFailed"), bcb.toMap()));
				}
			}
		}
	}

	private void validateFloat(Object value, BaseConstraintBean bcb,
			ValidationResultContainer result) {
		Float v = null;

		if (value instanceof Number) {
			v = ((Number) value).floatValue();
		} else {
			try {
				v = Float.valueOf(value.toString());
			} catch (Exception e) {
				result.addError(getMessage("validation.mustBeFloat"));
			}
		}

		if (result.isOk()) {
			Float maxValue = ValidatorUtils.getFloat(bcb.maxValue);
			Float minValue = ValidatorUtils.getFloat(bcb.minValue);

			if (maxValue != null && minValue != null) {
				// validate range
				if (v > maxValue || v < minValue) {
					result.addError(MessageUtils.interpolate(getMessage("validation.outOfRange"), bcb.toMap()));
				}
			} else if (maxValue != null) {
				if (v > maxValue) {
					result.addError(MessageUtils.interpolate(getMessage("validation.maxValueFailed"), bcb.toMap()));
				}
			} else if (minValue != null) {
				if (v < minValue) {
					result.addError(MessageUtils.interpolate(getMessage("validation.minValueFailed"), bcb.toMap()));
				}
			}
		}
	}

	private void validateLong(Object value, BaseConstraintBean bcb,
			ValidationResultContainer result) {
		Long v = null;

		if (value instanceof Number) {
			v = ((Number) value).longValue();
		} else {
			try {
				v = Long.valueOf(value.toString());
			} catch (Exception e) {
				result.addError(getMessage("validation.mustBeLong"));
			}
		}

		if (result.isOk()) {
			Long maxValue = ValidatorUtils.getLong(bcb.maxValue);
			Long minValue = ValidatorUtils.getLong(bcb.minValue);

			if (maxValue != null && minValue != null) {
				// validate range
				if (v > maxValue || v < minValue) {
					result.addError(MessageUtils.interpolate(getMessage("validation.outOfRange"), bcb.toMap()));
				}
			} else if (maxValue != null) {
				if (v > maxValue) {
					result.addError(MessageUtils.interpolate(getMessage("validation.maxValueFailed"), bcb.toMap()));
				}
			} else if (minValue != null) {
				if (v < minValue) {
					result.addError(MessageUtils.interpolate(getMessage("validation.minValueFailed"), bcb.toMap()));
				}
			}
		}

	}

	private void validateInteger(Object value, BaseConstraintBean bcb,
			ValidationResultContainer result) {
		Integer v = null;

		if (value instanceof Number) {
			v = ((Number) value).intValue();
		} else {
			try {
				v = Integer.valueOf(value.toString());
			} catch (Exception e) {
				result.addError(getMessage("validation.mustBeInteger"));
			}
		}

		if (result.isOk()) {
			Integer maxValue = ValidatorUtils.getInteger(bcb.maxValue);
			Integer minValue = ValidatorUtils.getInteger(bcb.minValue);

			if (maxValue != null && minValue != null) {
				// validate range
				if (v > maxValue || v < minValue) {
					result.addError(MessageUtils.interpolate(getMessage("validation.outOfRange"), bcb.toMap()));
				}
			} else if (maxValue != null) {
				if (v > maxValue) {
					result.addError(MessageUtils.interpolate(getMessage("validation.maxValueFailed"), bcb.toMap()));
				}
			} else if (minValue != null) {
				if (v < minValue) {
					result.addError(MessageUtils.interpolate(getMessage("validation.minValueFailed"), bcb.toMap()));
				}
			}
		}

	}

	private void validateDate(Object value, BaseConstraintBean bcb,
			ValidationResultContainer result, DateParser dateParser) {
		Date v = null;

		if (value instanceof Date) {
			v = (Date) value;
		} else {
			try {
				v = dateParser.parseDate(value.toString());
			} catch (Exception e) {
				result.addError(getMessage("validation.mustBeDate"));
			}
		}

		if (result.isOk()) {
			Date maxValue = ValidatorUtils
					.getDate(bcb.maxValue, dateParser);
			Date minValue = ValidatorUtils
					.getDate(bcb.minValue, dateParser);

			if (maxValue != null && minValue != null) {
				// validate range
				if (v.getTime() > maxValue.getTime()
						|| v.getTime() < minValue.getTime()) {
					result.addError(MessageUtils.interpolate(getMessage("validation.outOfRange"), bcb.toMap()));
				}
			} else if (maxValue != null) {
				if (v.getTime() > maxValue.getTime()) {
					result.addError(MessageUtils.interpolate(getMessage("validation.maxValueFailed"), bcb.toMap()));
				}
			} else if (minValue != null) {
				if (v.getTime() < minValue.getTime()) {
					result.addError(MessageUtils.interpolate(getMessage("validation.minValueFailed"), bcb.toMap()));
				}
			}
		}
	}

	private void validateString(Object value, BaseConstraintBean bcb,
			ValidationResultContainer result) {

	    if(value == null){
	        value = "";
//            result.addError(MessageUtils.interpolate(messages
  //                  .get("Empty string")));
	//	    return ;
		}
	    String s = value.toString().trim();

	    Integer maxLength = tryParse(bcb.maxLength);
		if (maxLength != null && bcb.minLength > 0) {
			if (s.length() > maxLength || s.length() < bcb.minLength) {
				result.addError(MessageUtils.interpolate(getMessage("validation.lengthOutOfRange"), bcb.toMap()));
			}
		} else if (maxLength != null) {
			if (s.length() > Integer.parseInt(bcb.maxLength)) {
				result.addError(MessageUtils.interpolate(getMessage("validation.maxLengthFailed"), bcb.toMap()));
			}
		} else if (bcb.minLength > 0) {
			if (s.length() < bcb.minLength) {
				result.addError(MessageUtils.interpolate(getMessage("validation.minLengthFailed"), bcb.toMap()));
			}
		}
	}

	private String getElementXpath() {
		StringBuilder xPath = new StringBuilder();

		Iterator<String> itr = elementStack.iterator();
		while (itr.hasNext()) {
			xPath.append(itr.next() + "/");
		}

		return xPath.toString();
	}

	private class BaseConstraintBean {
		public Integer minOccurs = 0;
		public String maxOccurs = UNBOUNDED_CHECK;
		public Integer minLength = 0;
		public String maxLength = UNBOUNDED_CHECK;
		public String dataType = null;
		public String minValue = null;
		public String maxValue = null;

		public Map<String, Object> toMap() {
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("minOccurs", minOccurs);
			result.put("maxOccurs", maxOccurs);
			result.put("minLength", minLength);
			result.put("maxLength", maxLength);
			result.put("minValue", minValue);
			result.put("maxValue", maxValue);
			result.put("dataType", dataType);

			return result;
		}
	}
	
	/*
	 * Homemade has text so we dont need outside libs.
	 */
	private boolean hasText(String string){

		if(string==null||string.length()<1){
			return false;
		}
		int stringLength = string.length();
				
		for(int i=0;i<stringLength;i++){
			char currentChar=string.charAt(i);
			if(' '!=currentChar||
			   '\t'!=currentChar||
			   '\n'!=currentChar){
				return true;
			}
		}
		
		return false;
	}
}
