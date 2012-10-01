/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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

package org.kuali.student.common.validator.old;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.kuali.student.common.dictionary.old.dto.CaseConstraint;
import org.kuali.student.common.dictionary.old.dto.ConstraintDescriptor;
import org.kuali.student.common.dictionary.old.dto.ConstraintSelector;
import org.kuali.student.common.dictionary.old.dto.Field;
import org.kuali.student.common.dictionary.old.dto.LookupConstraint;
import org.kuali.student.common.dictionary.old.dto.ObjectStructure;
import org.kuali.student.common.dictionary.old.dto.OccursConstraint;
import org.kuali.student.common.dictionary.old.dto.RequireConstraint;
import org.kuali.student.common.dictionary.old.dto.State;
import org.kuali.student.common.dictionary.old.dto.Type;
import org.kuali.student.common.dictionary.old.dto.TypeStateCaseConstraint;
import org.kuali.student.common.dictionary.old.dto.ValidCharsConstraint;
import org.kuali.student.common.dictionary.old.dto.WhenConstraint;
import org.kuali.student.common.messages.dto.Message;
import org.kuali.student.common.messages.service.MessageService;
import org.kuali.student.common.util.MessageUtils;
import org.kuali.student.common.validation.dto.ValidationResultInfo;

public class Validator {

	//TODO: Change this to 'default' when the change is made in xml
	private static final String DEFAULT_STATE = "*";

	private static final String UNBOUNDED_CHECK = null;

	private MessageService messageService = null;

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
	public List<ValidationResultInfo> validateTypeStateObject(Object data,
			ObjectStructure objStructure) {

		Stack<String> elementStack = new Stack<String>();
		return validateTypeStateObject(data, objStructure, elementStack);
	}

	private List<ValidationResultInfo> validateTypeStateObject(Object data,
			ObjectStructure objStructure, Stack<String> elementStack) {

		List<ValidationResultInfo> results = new ArrayList<ValidationResultInfo>();

		ConstraintDataProvider dataProvider = new BeanConstraintDataProvider();
		dataProvider.initialize(data);

		boolean isTypeStateObject = (dataProvider.hasField("type") && dataProvider
				.hasField("state"));

		// Push object structure to the top of the stack
		StringBuilder objXPathElement = new StringBuilder(dataProvider
				.getPath());
		if (null != dataProvider.getObjectId()) {
			objXPathElement.append("[id='" + dataProvider.getObjectId() + "']");
		} else {
			objXPathElement.append("[id='null']");
		}
		elementStack.push(objXPathElement.toString());

		/*
		 * Do nothing if the object to be validated is not type/state or if the
		 * objectstructure with constraints is not provided
		 */
		if (!isTypeStateObject || null == objStructure) {
			return results;
		}

		// Validate with the matching Type/State
		List<Type> types = objStructure.getType();
		for (Type t : types) {
			if (t.getKey().equalsIgnoreCase(
					(String) dataProvider.getValue("type"))) {
				for (State s : t.getState()) {
					if (s.getKey().equalsIgnoreCase(
							(String) dataProvider.getValue("state"))
							|| s.getKey().equalsIgnoreCase(DEFAULT_STATE)) {
						for (Field f : s.getField()) {
							List<ValidationResultInfo> l = validateField(f, t,
									s, objStructure, dataProvider, elementStack);
							results.addAll(l);
						}
						break;
					}
				}
				break;
			}
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

	public List<ValidationResultInfo> validateField(Field field, Type type,
			State state, ObjectStructure objStruct,
			ConstraintDataProvider dataProvider, Stack<String> elementStack) {

		Object value = dataProvider.getValue(field.getKey());
		List<ValidationResultInfo> results = new ArrayList<ValidationResultInfo>();

		ConstraintDescriptor cd = field.getConstraintDescriptor();

		// Handle null values in field
		if (value == null || "".equals(value.toString().trim())) {
			if (isNullable(field) == false) {
				ValidationResultInfo valInfo = new ValidationResultInfo(
						getElementXpath(elementStack) + field.getKey());
				valInfo.setError(getMessage("validation.required"));
				results.add(valInfo);
			}
			return results;
		}

		/*
		 * For complex object structures only the following constraints apply
		 * 1. TypeStateCase
		 * 2. MinOccurs
		 * 3. MaxOccurs
		 */
		if ("complex"
				.equalsIgnoreCase(field.getFieldDescriptor().getDataType())) {
			ObjectStructure nestedObjStruct = null;

			if(null != field.getFieldDescriptor().getObjectStructure()) {
				nestedObjStruct = field.getFieldDescriptor()
				.getObjectStructure();
			}
			else if (hasText(field.getFieldDescriptor().getObjectStructureRef())) {
				//TODO: Setup a mechanism to retrive referenced object structures
//				nestedObjStruct = setupFactory.getObjectStructure(field
//						.getFieldDescriptor().getObjectStructureRef());
			}

			BaseConstraintBean bcb = new BaseConstraintBean();
			if(null != cd) {
				for(ConstraintSelector constraint: cd.getConstraint()) {
					computeBaseConstraints(constraint, bcb, field);
				}
			}

			elementStack.push(field.getKey());

			if (value instanceof Collection<?>) {

				String xPath = getElementXpath(elementStack) + "/";

				for (Object o : (Collection<?>) value) {
					processNestedObjectStructure(results, o, nestedObjStruct,
							field, elementStack);
				}
				if (bcb.minOccurs > ((Collection<?>) value).size()) {
					ValidationResultInfo valRes = new ValidationResultInfo(
							xPath);
					valRes.setError(MessageUtils.interpolate(getMessage("validation.minOccurs"), bcb.toMap()));
					results.add(valRes);
				}

				Integer maxOccurs = tryParse(bcb.maxOccurs);
				if (maxOccurs != null
						&& maxOccurs < ((Collection<?>) value).size()) {
					ValidationResultInfo valRes = new ValidationResultInfo(
							xPath);
					valRes.setError(MessageUtils.interpolate(getMessage("validation.maxOccurs"), bcb.toMap()));
					results.add(valRes);
				}
			} else {
				if(null != value) {
					processNestedObjectStructure(results, value, nestedObjStruct,
							field, elementStack);
				} else {
					if (bcb.minOccurs != null && bcb.minOccurs > 0) {
						ValidationResultInfo val = new ValidationResultInfo(
								getElementXpath(elementStack) + "[value='null']/");
						val.setError(getMessage("validation.required"));
						results.add(val);
					}
				}
			}

			elementStack.pop();

		} else { // If non complex data type
			if (null != cd) {
				List<ConstraintSelector> constraints = cd.getConstraint();

				if (value instanceof Collection<?>) {
					BaseConstraintBean bcb = new BaseConstraintBean();
					for (Object o : (Collection<?>) value) {
						for (ConstraintSelector constraint : constraints) {
							processConstraint(results, constraint, field, type,
									state, objStruct, o, dataProvider, bcb, elementStack);
						}
						processBaseConstraints(results, bcb, field, o, elementStack);
						if(!bcb.initialized) {
							bcb.initialized = true;
						}
					}

					String xPath = getElementXpath(elementStack) + field.getKey() + "/";
					if (bcb.minOccurs > ((Collection<?>) value).size()) {
						ValidationResultInfo valRes = new ValidationResultInfo(
								xPath);
						valRes.setError(MessageUtils.interpolate(getMessage("validation.minOccurs"), bcb.toMap()));
						results.add(valRes);
					}

					Integer maxOccurs = tryParse(bcb.maxOccurs);
					if (maxOccurs != null
							&& maxOccurs < ((Collection<?>) value).size()) {
						ValidationResultInfo valRes = new ValidationResultInfo(
								xPath);
						valRes.setError(MessageUtils.interpolate(getMessage("validation.maxOccurs"), bcb.toMap()));
						results.add(valRes);
					}
				} else {
					BaseConstraintBean bcb = new BaseConstraintBean();
					for (ConstraintSelector constraint : constraints) {
						processConstraint(results, constraint, field, type,
								state, objStruct, value, dataProvider, bcb, elementStack);
					}
					processBaseConstraints(results, bcb, field, value, elementStack);
				}
			}
		}
		return results;
	}

	private boolean isNullable(Field field) {
		ConstraintDescriptor cd = field.getConstraintDescriptor();
		if (null != cd) {
			List<ConstraintSelector> constraintList = cd.getConstraint();
			for (ConstraintSelector cs : constraintList) {
				if (cs.getMinOccurs() != null && cs.getMinOccurs() > 0) {
					return false;
				}
			}
		}
		return true;
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
			List<ValidationResultInfo> results, Object value,
			ObjectStructure nestedObjStruct, Field field, Stack<String> elementStack) {

		results.addAll(validateTypeStateObject(value, nestedObjStruct, elementStack));

		ConstraintDescriptor cd = field.getConstraintDescriptor();
		if (null != cd) {
			ConstraintSelector cs = cd.getConstraint().get(0);
			TypeStateCaseConstraint tscs = cs.getTypeStateCaseConstraint();
			if (null != tscs) {
				// ValidationResultContainer vc = new
				// ValidationResultContainer();
				// processTypeStateCaseConstraint(vc);
				// results.add(vc);k
			}
		}
	}

	private void computeBaseConstraints(ConstraintSelector constraint,
			BaseConstraintBean bcb, Field field) {
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
	}

	private void processConstraint(List<ValidationResultInfo> valResults,
			ConstraintSelector constraint, Field field, Type type, State state,
			ObjectStructure objStructure, Object value,
			ConstraintDataProvider dataProvider, BaseConstraintBean bcb,
			Stack<String> elementStack) {

		// If constraint is only to be processed on server side
		if (hasText(constraint.getClassName()) || constraint.isServerSide()
				&& !serverSide) {
			return;
		}

		String elementPath = getElementXpath(elementStack) + field.getKey()
				+ "[value='" + value.toString() + "']/";

		if(!bcb.initialized) {
			computeBaseConstraints(constraint, bcb, field);
		}

		// Process Valid Chars
		if (null != constraint.getValidChars()) {
			ValidationResultInfo val = processValidCharConstraint(elementPath,
					constraint.getValidChars(), dataProvider, value);
			if (null != val) {
				valResults.add(val);
			}
		}

		// Process Require Constraints (only if this field has value)
		if (value != null && !"".equals(value.toString().trim())) {
			if (null != constraint.getRequireConstraint()
					&& constraint.getRequireConstraint().size() > 0) {
				for (RequireConstraint rc : constraint.getRequireConstraint()) {
					ValidationResultInfo val = processRequireConstraint(
							elementPath, rc, field, objStructure, dataProvider);
					if (null != val) {
						valResults.add(val);
					}
				}
			}
		}

		// Process Occurs Constraint
		if (null != constraint.getOccursConstraint()
				&& constraint.getOccursConstraint().size() > 0) {
			for (OccursConstraint oc : constraint.getOccursConstraint()) {
				ValidationResultInfo val = processOccursConstraint(elementPath,
						oc, field, type, state, objStructure, dataProvider);
				if (null != val) {
					valResults.add(val);
				}
			}
		}

		// Process lookup Constraint
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
						objStructure, value, dataProvider, bcb, elementStack);
			}
		}
	}

	private ValidationResultInfo processRequireConstraint(String element,
			RequireConstraint constraint, Field field,
			ObjectStructure objStructure, ConstraintDataProvider dataProvider) {

		ValidationResultInfo val = null;

		String fieldName = constraint.getField();
		Object fieldValue = dataProvider.getValue(fieldName);

		boolean result = true;

		if (fieldValue instanceof java.lang.String) {
			result = hasText((String) fieldValue);
		} else if (fieldValue instanceof Collection<?>) {
			result = (((Collection<?>) fieldValue).size() > 0);
		} else {
			result = (null != fieldValue) ? true : false;
		}

		if (!result) {
			Map<String, Object> rMap = new HashMap<String, Object>();
			rMap.put("field1", field.getKey());
			rMap.put("field2", fieldName);
			val = new ValidationResultInfo(element);
			val.setError(MessageUtils.interpolate(
					getMessage("validation.requiresField"), rMap));
		}

		return val;
	}

	/**
	 * Process caseConstraint tag and sets any of the base constraint items if
	 * any of the when condition matches
	 *
	 * @param bcb
	 * @param caseConstraint
	 * @param field
	 */
	private void processCaseConstraint(List<ValidationResultInfo> valResults,
			CaseConstraint constraint, Field field, Type type, State state,
			ObjectStructure objStructure, Object value,
			ConstraintDataProvider dataProvider, BaseConstraintBean bcb,
			Stack<String> elementStack) {

		String operator = (hasText(constraint.getOperator())) ? constraint
				.getOperator() : "EQUALS";
		Field caseField = (hasText(constraint.getField())) ? ValidatorUtils
				.getField(constraint.getField(), objStructure, type.getKey(),
						state.getKey()) : null;

		// TODO: What happens when the field is not in the dataProvider?
		Object fieldValue = (null != caseField) ? dataProvider
				.getValue(caseField.getKey()) : value;

		// Extract value for field Key
		for (WhenConstraint wc : constraint.getWhenConstraint()) {
			String whenValue = wc.getValue();

			if (ValidatorUtils.compareValues(fieldValue, whenValue, caseField
					.getFieldDescriptor().getDataType(), operator, dateParser)) {
				processConstraint(valResults, wc.getConstraint(), field, type,
						state, objStructure, value, dataProvider, bcb,
						elementStack);
			}
		}
	}

	private ValidationResultInfo processValidCharConstraint(String element,
			ValidCharsConstraint vcConstraint,
			ConstraintDataProvider dataProvider, Object value) {

		ValidationResultInfo val = null;

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
			// if (!Pattern.matches(validChars, fieldValue.toString())) {
			if (fieldValue == null) {
				val = new ValidationResultInfo(element);
				val.setError(getMessage("validation.validCharsFailed"));
			} else if (fieldValue != null
					&& !fieldValue.toString().matches(validChars)) {
				val = new ValidationResultInfo(element);
				val.setError(getMessage("validation.validCharsFailed"));
			}
		}

		return val;
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
	private ValidationResultInfo processOccursConstraint(String element,
			OccursConstraint constraint, Field field, Type type, State state,
			ObjectStructure objStructure, ConstraintDataProvider dataProvider) {

		boolean result = false;
		int trueCount = 0;

		ValidationResultInfo val = null;

		for (RequireConstraint rc : constraint.getRequire()) {
			trueCount += (processRequireConstraint("", rc, field, objStructure,
					dataProvider) != null) ? 1 : 0;
		}

		for (OccursConstraint oc : constraint.getOccurs()) {
			trueCount += (processOccursConstraint("", oc, field, type, state,
					objStructure, dataProvider) != null) ? 1 : 0;
		}

		result = (trueCount >= constraint.getMin() && trueCount <= constraint
				.getMax()) ? true : false;

		if (!result) {
			val = new ValidationResultInfo(element);
			val.setError(getMessage("validation.occurs"));
		}

		return val;
	}

	// TODO: Implement lookup constraint
	private void processLookupConstraint(List<ValidationResultInfo> valResults) {
	}

	// TODO: Implement TypeStateCase constraint
	private void processTypeStateCaseConstraint(
			List<ValidationResultInfo> valResults) {
	}

	private void processBaseConstraints(List<ValidationResultInfo> valResults,
			BaseConstraintBean bcb, Field field, Object value,
			Stack<String> elementStack) {

		String dataType = field.getFieldDescriptor().getDataType();

		if (value == null || "".equals(value.toString().trim())) {
			if (bcb.minOccurs != null && bcb.minOccurs > 0) {
				ValidationResultInfo val = new ValidationResultInfo(
						getElementXpath(elementStack) + field.getKey()
								+ "[value='null']/");
				val.setError(getMessage("validation.required"));
				valResults.add(val);
				return;
			}
		}

		String elementPath = getElementXpath(elementStack) + field.getKey()
				+ "[value='" + value.toString() + "']/";

		if ("string".equalsIgnoreCase(dataType)) {
			validateString(value, bcb, elementPath, valResults);
		} else if ("integer".equalsIgnoreCase(dataType)) {
			validateInteger(value, bcb, elementPath, valResults);
		} else if ("long".equalsIgnoreCase(dataType)) {
			validateLong(value, bcb, elementPath, valResults);
		} else if ("double".equalsIgnoreCase(dataType)) {
			validateDouble(value, bcb, elementPath, valResults);
		} else if ("float".equalsIgnoreCase(dataType)) {
			validateFloat(value, bcb, elementPath, valResults);
		} else if ("boolean".equalsIgnoreCase(dataType)) {
			validateBoolean(value, bcb, elementPath, valResults);
		} else if ("date".equalsIgnoreCase(dataType)) {
			validateDate(value, bcb, elementPath, valResults, dateParser);
		}
	}

	private void validateBoolean(Object value, BaseConstraintBean bcb,
			String element, List<ValidationResultInfo> results) {
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

	private void validateDouble(Object value, BaseConstraintBean bcb,
			String element, List<ValidationResultInfo> results) {
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
			Double maxValue = ValidatorUtils.getDouble(bcb.maxValue);
			Double minValue = ValidatorUtils.getDouble(bcb.minValue);

			if (maxValue != null && minValue != null) {
				// validate range
				if (v > maxValue || v < minValue) {
					val.setError(MessageUtils.interpolate(
							getMessage("validation.outOfRange"), bcb.toMap()));
				}
			} else if (maxValue != null) {
				if (v > maxValue) {
					val.setError(MessageUtils.interpolate(
							getMessage("validation.maxValueFailed"), bcb
									.toMap()));
				}
			} else if (minValue != null) {
				if (v < minValue) {
					val.setError(MessageUtils.interpolate(
							getMessage("validation.minValueFailed"), bcb
									.toMap()));
				}
			}
		}

		if (!val.isOk()) {
			results.add(val);
		}
	}

	private void validateFloat(Object value, BaseConstraintBean bcb,
			String element, List<ValidationResultInfo> results) {
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
			Float maxValue = ValidatorUtils.getFloat(bcb.maxValue);
			Float minValue = ValidatorUtils.getFloat(bcb.minValue);

			if (maxValue != null && minValue != null) {
				// validate range
				if (v > maxValue || v < minValue) {
					val.setError(MessageUtils.interpolate(
							getMessage("validation.outOfRange"), bcb.toMap()));
				}
			} else if (maxValue != null) {
				if (v > maxValue) {
					val.setError(MessageUtils.interpolate(
							getMessage("validation.maxValueFailed"), bcb
									.toMap()));
				}
			} else if (minValue != null) {
				if (v < minValue) {
					val.setError(MessageUtils.interpolate(
							getMessage("validation.minValueFailed"), bcb
									.toMap()));
				}
			}
		}

		if (!val.isOk()) {
			results.add(val);
		}
	}

	private void validateLong(Object value, BaseConstraintBean bcb,
			String element, List<ValidationResultInfo> results) {
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
			Long maxValue = ValidatorUtils.getLong(bcb.maxValue);
			Long minValue = ValidatorUtils.getLong(bcb.minValue);

			if (maxValue != null && minValue != null) {
				// validate range
				if (v > maxValue || v < minValue) {
					val.setError(MessageUtils.interpolate(
							getMessage("validation.outOfRange"), bcb.toMap()));
				}
			} else if (maxValue != null) {
				if (v > maxValue) {
					val.setError(MessageUtils.interpolate(
							getMessage("validation.maxValueFailed"), bcb
									.toMap()));
				}
			} else if (minValue != null) {
				if (v < minValue) {
					val.setError(MessageUtils.interpolate(
							getMessage("validation.minValueFailed"), bcb
									.toMap()));
				}
			}
		}

		if (!val.isOk()) {
			results.add(val);
		}

	}

	private void validateInteger(Object value, BaseConstraintBean bcb,
			String element, List<ValidationResultInfo> results) {
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
			Integer maxValue = ValidatorUtils.getInteger(bcb.maxValue);
			Integer minValue = ValidatorUtils.getInteger(bcb.minValue);

			if (maxValue != null && minValue != null) {
				// validate range
				if (v > maxValue || v < minValue) {
					val.setError(MessageUtils.interpolate(
							getMessage("validation.outOfRange"), bcb.toMap()));
				}
			} else if (maxValue != null) {
				if (v > maxValue) {
					val.setError(MessageUtils.interpolate(
							getMessage("validation.maxValueFailed"), bcb
									.toMap()));
				}
			} else if (minValue != null) {
				if (v < minValue) {
					val.setError(MessageUtils.interpolate(
							getMessage("validation.minValueFailed"), bcb
									.toMap()));
				}
			}
		}

		if (!val.isOk()) {
			results.add(val);
		}
	}

	private void validateDate(Object value, BaseConstraintBean bcb,
			String element, List<ValidationResultInfo> results,
			DateParser dateParser) {
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
			Date maxValue = ValidatorUtils.getDate(bcb.maxValue, dateParser);
			Date minValue = ValidatorUtils.getDate(bcb.minValue, dateParser);

			if (maxValue != null && minValue != null) {
				// validate range
				if (v.getTime() > maxValue.getTime()
						|| v.getTime() < minValue.getTime()) {
					val.setError(MessageUtils.interpolate(
							getMessage("validation.outOfRange"), bcb.toMap()));
				}
			} else if (maxValue != null) {
				if (v.getTime() > maxValue.getTime()) {
					val.setError(MessageUtils.interpolate(
							getMessage("validation.maxValueFailed"), bcb
									.toMap()));
				}
			} else if (minValue != null) {
				if (v.getTime() < minValue.getTime()) {
					val.setError(MessageUtils.interpolate(
							getMessage("validation.minValueFailed"), bcb
									.toMap()));
				}
			}
		}

		if (!val.isOk()) {
			results.add(val);
		}
	}

	private void validateString(Object value, BaseConstraintBean bcb,
			String element, List<ValidationResultInfo> results) {

		if (value == null) {
			value = "";
		}
		String s = value.toString().trim();

		ValidationResultInfo val = new ValidationResultInfo(element);

		Integer maxLength = tryParse(bcb.maxLength);
		if (maxLength != null && bcb.minLength > 0) {
			if (s.length() > maxLength || s.length() < bcb.minLength) {
				val
						.setError(MessageUtils.interpolate(
								getMessage("validation.lengthOutOfRange"), bcb
										.toMap()));
			}
		} else if (maxLength != null) {
			if (s.length() > Integer.parseInt(bcb.maxLength)) {
				val.setError(MessageUtils.interpolate(
						getMessage("validation.maxLengthFailed"), bcb.toMap()));
			}
		} else if (bcb.minLength > 0) {
			if (s.length() < bcb.minLength) {
				val.setError(MessageUtils.interpolate(
						getMessage("validation.minLengthFailed"), bcb.toMap()));
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

		Message msg = messageService.getMessage(messageLocaleKey,
				messageGroupKey, messageId);

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
			if (' ' != currentChar || '\t' != currentChar
					|| '\n' != currentChar) {
				return true;
			}
		}

		return false;
	}

	private static class BaseConstraintBean {
		public boolean initialized = false;
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
}
