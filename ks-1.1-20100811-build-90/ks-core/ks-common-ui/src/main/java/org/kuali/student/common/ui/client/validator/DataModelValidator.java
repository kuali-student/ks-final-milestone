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

package org.kuali.student.common.ui.client.validator;

import static org.kuali.student.common.ui.client.validator.ValidationMessageKeys.BOOLEAN;
import static org.kuali.student.common.ui.client.validator.ValidationMessageKeys.DATE;
import static org.kuali.student.common.ui.client.validator.ValidationMessageKeys.DOUBLE;
import static org.kuali.student.common.ui.client.validator.ValidationMessageKeys.FLOAT;
import static org.kuali.student.common.ui.client.validator.ValidationMessageKeys.INTEGER;
import static org.kuali.student.common.ui.client.validator.ValidationMessageKeys.LENGTH_OUT_OF_RANGE;
import static org.kuali.student.common.ui.client.validator.ValidationMessageKeys.LONG;
import static org.kuali.student.common.ui.client.validator.ValidationMessageKeys.MAX_LENGTH;
import static org.kuali.student.common.ui.client.validator.ValidationMessageKeys.MAX_OCCURS;
import static org.kuali.student.common.ui.client.validator.ValidationMessageKeys.MAX_VALUE;
import static org.kuali.student.common.ui.client.validator.ValidationMessageKeys.MIN_LENGTH;
import static org.kuali.student.common.ui.client.validator.ValidationMessageKeys.MIN_OCCURS;
import static org.kuali.student.common.ui.client.validator.ValidationMessageKeys.MIN_VALUE;
import static org.kuali.student.common.ui.client.validator.ValidationMessageKeys.OCCURS;
import static org.kuali.student.common.ui.client.validator.ValidationMessageKeys.OUT_OF_RANGE;
import static org.kuali.student.common.ui.client.validator.ValidationMessageKeys.REQUIRED;
import static org.kuali.student.common.ui.client.validator.ValidationMessageKeys.VALID_CHARS;
import static org.kuali.student.core.assembly.data.MetadataInterrogator.getLargestMinLength;
import static org.kuali.student.core.assembly.data.MetadataInterrogator.getLargestMinOccurs;
import static org.kuali.student.core.assembly.data.MetadataInterrogator.getLargestMinValue;
import static org.kuali.student.core.assembly.data.MetadataInterrogator.getLargestMinValueDate;
import static org.kuali.student.core.assembly.data.MetadataInterrogator.getLargestMinValueDouble;
import static org.kuali.student.core.assembly.data.MetadataInterrogator.getSmallestMaxLength;
import static org.kuali.student.core.assembly.data.MetadataInterrogator.getSmallestMaxOccurs;
import static org.kuali.student.core.assembly.data.MetadataInterrogator.getSmallestMaxValue;
import static org.kuali.student.core.assembly.data.MetadataInterrogator.getSmallestMaxValueDate;
import static org.kuali.student.core.assembly.data.MetadataInterrogator.getSmallestMaxValueDouble;
import static org.kuali.student.core.assembly.data.MetadataInterrogator.isRequired;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.DataModelDefinition;
import org.kuali.student.common.util.MessageUtils;
import org.kuali.student.common.validator.old.DateParser;
import org.kuali.student.core.assembly.data.ConstraintMetadata;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.data.QueryPath;
import org.kuali.student.core.assembly.data.Data.DataType;
import org.kuali.student.core.assembly.data.Data.StringKey;
import org.kuali.student.core.validation.dto.ValidationResultInfo;

import com.google.gwt.i18n.client.DateTimeFormat;

public class DataModelValidator {
	
	private static final String RUNTIME_DELETED_KEY = "_runtimeData/deleted";
	
	private DateParser dateParser = null;


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

	
	/**
	 * Use to validate the entire DataModel structure against constraints defined in the metadata. 
	 * @param model
	 * @return
	 */
	public List<ValidationResultInfo> validate(final DataModel model) {
		List<ValidationResultInfo> results = new ArrayList<ValidationResultInfo>();
		DataModelDefinition def = (DataModelDefinition) model.getDefinition();
		doValidate(model, def.getMetadata(), new QueryPath(), results);
		return results;
	}
	
	/**
	 * Use to validated a single field within the data model against constraints defined in the metadata
	 * @param fd
	 * @param model
	 * @return
	 */
	public List<ValidationResultInfo> validate(FieldDescriptor fd,
			DataModel model) {
		List<ValidationResultInfo> results = new ArrayList<ValidationResultInfo>();
		if(fd != null && fd.getMetadata() != null && fd.getFieldKey() != null){
			doValidate(model, fd.getMetadata(), QueryPath.parse(fd.getFieldKey()), results);	
		}else{
			return validate(model);
		}
				
		return results;
	}
	
    private void doValidate(final DataModel model, final Metadata meta, final QueryPath path, List<ValidationResultInfo> results) {
    	switch (meta.getDataType()) {
		case DATA:
			// intentional fallthrough case
		case LIST:
			doValidateComplex(model, meta, path, results);
			break;
    	}
    	
    	if (meta.getConstraints() != null){
	    	switch (meta.getDataType()){
	    	
			case BOOLEAN:
				doValidateBoolean(model, meta, path, results);
				break;
			
			case DATE:
				// intentional fallthrough case
			case TRUNCATED_DATE:
				doValidateDate(model, meta, path, results);
				break;
			
			case DOUBLE:
				doValidateDouble(model, meta, path, results);
				break;
			
			case FLOAT:
				doValidateFloat(model, meta, path, results);
				break;
	
			case INTEGER:
				doValidateInteger(model, meta, path, results);
				break;
			
			case LONG:
				doValidateLong(model, meta, path, results);
				break;
			
			case STRING:
				doValidateString(model, meta, path, results);
				break;
			
			default:
				// do nothing
			}
    	}
	}
	
    private void addError(List<ValidationResultInfo> list, QueryPath element, ValidationMessageKeys msgKey, Map<String, Object> constraintInfo) {
        ValidationResultInfo v = new ValidationResultInfo();    	
        String rawMsg = Application.getApplicationContext().getMessage(msgKey.getKey());
        v.setElement(element.toString());
        v.setError(MessageUtils.interpolate(rawMsg, constraintInfo));       
        list.add(v);
    }
    
    private void addError(List<ValidationResultInfo> list, QueryPath element, ValidationMessageKeys msgKey, Object value ){
        ValidationResultInfo v = new ValidationResultInfo();
        String rawMsg = Application.getApplicationContext().getMessage(msgKey.getKey());
        v.setElement(element.toString());
        v.setError(MessageUtils.interpolate(rawMsg, msgKey.getProperty(), value));       
        list.add(v);
    }
    
    
    private void addError(List<ValidationResultInfo> list, QueryPath element, ValidationMessageKeys msgKey){
    	addError(list, element, msgKey.getKey());
    }
    
    private void addError(List<ValidationResultInfo> list, QueryPath element, String msgKey){
        ValidationResultInfo v = new ValidationResultInfo();
        v.setElement(element.toString());
        v.setError(Application.getApplicationContext().getMessage(msgKey));       
        list.add(v);
    }
    
    private void addRangeError(List<ValidationResultInfo> list, QueryPath element, ValidationMessageKeys msgKey, Object minValue, Object maxValue){
    	Map<String, Object> constraintInfo = new HashMap<String, Object>();
    	
    	put(constraintInfo, MIN_VALUE.getProperty(), minValue);
    	put(constraintInfo, MAX_VALUE.getProperty(), maxValue);
    	
    	addError(list, element, msgKey, constraintInfo);
    }
        
	private void doValidateString(DataModel model, Metadata meta,
			QueryPath path, List<ValidationResultInfo> results) {
		
	    Map<QueryPath, Object> values = model.query(path);
		
		validateOccurs(path, values, meta, results);
		
		Object[] keys = values.keySet().toArray();
		for (int keyIndex = 0; keyIndex < keys.length; keyIndex++) {
			QueryPath element = (QueryPath)keys[keyIndex];

			String s = (values.get(element) == null) ? "" : values.get(element).toString();
			
			if (s.isEmpty() && isRequired(meta)) {
				addError(results, element, REQUIRED);
			} else if(!s.isEmpty()) {
				int len = s.length();
				Integer minLength = getLargestMinLength(meta);
				Integer maxLength = getSmallestMaxLength(meta);
				
				if (minLength != null && maxLength != null) {
					if (len < minLength || len > maxLength) {
						addRangeError(results, element, LENGTH_OUT_OF_RANGE, minLength, maxLength);
					}
				} else if (minLength != null && len < minLength) {
					addError(results, element, MIN_LENGTH, minLength);
				} else if (maxLength != null && len > maxLength) {
					addError(results, element, MAX_LENGTH, maxLength);
				}
				
				// process valid chars constraint
				if (meta.getConstraints() != null) {
					boolean failed = false;
					List<ConstraintMetadata> constraints = meta.getConstraints();
					
					for (int consIdx=0; consIdx <constraints.size(); consIdx++) {
						ConstraintMetadata cons = constraints.get(consIdx);
						if (failed) {
							break;
						}
						String validChars = cons.getValidChars();
						validChars = (validChars == null) ? "" : validChars.trim();
						if (!validChars.isEmpty()) {
							if (validChars.startsWith("regex:")) {
								validChars = validChars.substring(6);
								if (!s.matches(validChars)) {
									if(cons.getValidCharsMessageId() != null ){
										addError(results, element, cons.getValidCharsMessageId());
									}else{
										addError(results, element, VALID_CHARS);	
									}
									failed = true;
									break;
								}
							} else {
								for (char c : s.toCharArray()) {
									if (!validChars.contains(String.valueOf(c))) {
										if(cons.getValidCharsMessageId() != null ){
											addError(results, element, cons.getValidCharsMessageId());
										}else{
											addError(results, element, VALID_CHARS);	
										}
										failed = true;
										break;
									}
								}
							}
						}
					}
				}
			}					
		}
	}
	
	
	private void doValidateInteger(DataModel model, Metadata meta,
			QueryPath path, List<ValidationResultInfo> results) {
		
	    Map<QueryPath, Object> values = model.query(path);
	    
	    validateOccurs(path, values, meta, results);
        
		Object[] keys = values.keySet().toArray();
		for (int keyIndex = 0; keyIndex < keys.length; keyIndex++) {
			QueryPath element = (QueryPath)keys[keyIndex];

			Object o = values.get(element);
			
			if (o == null) {
				if (isRequired(meta)) {
					addError(results, element, REQUIRED);
				}
			} else {
				Integer i = null;
				try {
					i = (o instanceof Integer) ? (Integer) o : Integer.valueOf(o.toString());
				} catch (Exception ex) {
					addError(results, element, INTEGER);
				}
				
				if (i != null) {
    				Long min = getLargestMinValue(meta);
    				Long max = getSmallestMaxValue(meta);
    				
    				if (min != null && max != null) {
    					if (i < min || i > max) {
    						addRangeError(results, element, OUT_OF_RANGE, min, max);
    					}
    				} else if (min != null && i < min) {
    					addError(results, element, MIN_VALUE, min);
    				} else if (max != null && i > max) {
    					addError(results, element, MAX_VALUE, max);
    				}
				}
			}
		}
	}

	private void doValidateLong(DataModel model, Metadata meta,
			QueryPath path, List<ValidationResultInfo> results) {
		
	    Map<QueryPath, Object> values = model.query(path);

        validateOccurs(path, values, meta, results);
        
		Object[] keys = values.keySet().toArray();
		for (int keyIndex = 0; keyIndex < keys.length; keyIndex++) {
			QueryPath element = (QueryPath)keys[keyIndex];

			Object o = values.get(element);
			
			if (o == null) {
				if (isRequired(meta)) {
					addError(results, element, REQUIRED);
				}
			} else {
				Long i = null;
				try {
					i = (o instanceof Long) ? (Long) o : Long.valueOf(o.toString());
				} catch (Exception ex) {
					addError(results, element, LONG);
				}
				
				
				if (i != null) {
    				Long min = getLargestMinValue(meta);
    				Long max = getSmallestMaxValue(meta);
    				
    				if (min != null && max != null) {
    					if (i < min || i > max) {
    						addRangeError(results, element, OUT_OF_RANGE, min, max);
    					}
    				} else if (min != null && i < min) {
    					addError(results, element, MIN_VALUE, min);
    				} else if (max != null && i > max) {
    					addError(results, element, MAX_VALUE, max);
    				}
				}
			}
		}
	}
	
	private void doValidateDouble(DataModel model, Metadata meta,
			QueryPath path, List<ValidationResultInfo> results) {
		
	    Map<QueryPath, Object> values = model.query(path);
		
        validateOccurs(path, values, meta, results);
        
		Object[] keys = values.keySet().toArray();
		for (int keyIndex = 0; keyIndex < keys.length; keyIndex++) {
			QueryPath element = (QueryPath)keys[keyIndex];

			Object o = values.get(element);
			
			if (o == null) {
				if (isRequired(meta)) {
					addError(results, element, REQUIRED);
				}
			} else {
				Double d = null;
				try {
					d = (o instanceof Double) ? (Double) o : Double.valueOf(o.toString());
				} catch (Exception ex) {
					addError(results, element, DOUBLE);
				}
				
				
				if (d != null) {
    				Double min = getLargestMinValueDouble(meta);
    				Double max = getSmallestMaxValueDouble(meta);
    				    				
    				if (min != null && max != null) {
    					if (d < min || d > max) {
    						addRangeError(results, element, OUT_OF_RANGE,  min, max);
    					}
    				} else if (min != null && d < min) {
    					addError(results, element, MIN_VALUE, min);
    				} else if (max != null && d > max) {
    					addError(results, element, MAX_VALUE, max);
    				}
				}
			}
		}
	}
	
	private void doValidateFloat(DataModel model, Metadata meta,
			QueryPath path, List<ValidationResultInfo> results) {
		
	    Map<QueryPath, Object> values = model.query(path);

        validateOccurs(path, values, meta, results);
        
		Object[] keys = values.keySet().toArray();		
		for (int keyIndex = 0; keyIndex < keys.length; keyIndex++) {
			QueryPath element = (QueryPath)keys[keyIndex];

			Object o = values.get(element);
			
			if (o == null) {
				if (isRequired(meta)) {
					addError(results, element, REQUIRED);
				}
			} else {
				Float d = null;
				try {
					d = (o instanceof Float) ? (Float) o : Float.valueOf(o.toString());
				} catch (Exception ex) {
					addError(results, element, FLOAT);
				}
				

				if (d != null) {
    				Double min = getLargestMinValueDouble(meta);
    				Double max = getSmallestMaxValueDouble(meta);
    				
    				if (min != null && max != null) {
    					if (d < min || d > max) {
    						addRangeError(results, element, OUT_OF_RANGE,  min, max);
    					}
    				} else if (min != null && d < min) {
    					addError(results, element, MIN_VALUE, min);
    				} else if (max != null && d > max) {
    					addError(results, element, MAX_VALUE, max);
    				}
				}
			}
		}
	}
	
	private void doValidateDate(DataModel model, Metadata meta,
			QueryPath path, List<ValidationResultInfo> results) {
		
	    Map<QueryPath, Object> values = model.query(path);

        validateOccurs(path, values, meta, results);
        
		Object[] keys = values.keySet().toArray();
		for (int keyIndex = 0; keyIndex < keys.length; keyIndex++) {
			QueryPath element = (QueryPath)keys[keyIndex];
			Object o = values.get(element);
			
			if (o == null) {
				if (isRequired(meta)) {
					addError(results, element, REQUIRED);
				}
			} else {
				Date d = null;
				try {
					d = (o instanceof Date) ? (Date) o : dateParser.parseDate(o.toString());
				} catch (Exception ex) {
					addError(results, element, DATE);
				}
			
				
				if (d != null) {
					//Get defined min/max value constraint
    				Date min = getLargestMinValueDate(meta, dateParser, getCrossFieldMinValue(model, element, meta));
    				Date max = getSmallestMaxValueDate(meta, dateParser, getCrossFieldMaxValue(model, element, meta));
    				    				
    				if (min != null && max != null) {
    					if (d.getTime() < min.getTime() || d.getTime() > max.getTime()) {
    						addRangeError(results, element, OUT_OF_RANGE,  asDateString(min), asDateString(max));
    					}
    				} else if (min != null && d.getTime() < min.getTime()) {
    					addError(results, element, MIN_VALUE, asDateString(min));
    				} else if (max != null && d.getTime() > max.getTime()) {
    					addError(results, element, MAX_VALUE, asDateString(max));
    				}
				}
			}
		}
	}
	
	
	private void doValidateBoolean(DataModel model, Metadata meta,
			QueryPath path, List<ValidationResultInfo> results) {
		
	    Map<QueryPath, Object> values = model.query(path);

	    validateOccurs(path, values, meta, results);
        
		Object[] keys = values.keySet().toArray();
		for (int keyIndex = 0; keyIndex < keys.length; keyIndex++) {
			QueryPath element = (QueryPath)keys[keyIndex];

			Object o = values.get(element);
			
			if (o == null) {
				if (isRequired(meta)) {
					addError(results, element, REQUIRED);
				}
			} else {
				if (o instanceof Boolean == false) {
					addError(results, element, BOOLEAN);
				}
			}
		}
	}

	private void doValidateComplex(final DataModel model, final Metadata meta, final QueryPath path, List<ValidationResultInfo> results) {
		Map<QueryPath, Object> values = model.query(path);
		if (values.isEmpty() && isRequired(meta)) {
			addError(results, path, REQUIRED);
		} else if (meta.getDataType().equals(DataType.LIST)){
			for (Map.Entry<QueryPath, Object> e:values.entrySet()){
				QueryPath listPath = QueryPath.parse(e.getKey().toString());
				listPath.add(Data.WILDCARD_KEY);
				values = model.query(listPath);
	
				// do min/max occurs checks
				validateOccurs(e.getKey(), values, meta, results);
			}
		}
			
		// validate children
		String basePath = path.toString();
		if (meta.getProperties() != null) {
			Object[] keys = meta.getProperties().keySet().toArray();
			for (int keyIndex = 0; keyIndex < keys.length; keyIndex++) {
				String element = (String)keys[keyIndex];
				if (!element.contains("runtimeData")){
					QueryPath childPath = QueryPath.concat(basePath, element);
					//System.out.println(childPath.toString());
					doValidate(model, meta.getProperties().get(element), childPath, results);
				}
			}
		}
	
	}
	
	private boolean validateOccurs(QueryPath path, Map<QueryPath, Object> values, Metadata meta, List<ValidationResultInfo> results) {
	    
	    int size = getListSize(values, meta);
		
	    Integer min = getLargestMinOccurs(meta);
	    boolean minValid = min == null || min <= size;
	 
	    Integer max = getSmallestMaxOccurs(meta);
        boolean maxValid = (max == null || max == -1 || max >= size);
	    
        
		if (!minValid || !maxValid) {
            if (!minValid && !maxValid) {
            	addRangeError(results, path, OCCURS, min, max);
            } else if (!minValid) {
                addError(results, path, MIN_OCCURS, min);
            } else {
            	addError(results, path, MAX_OCCURS, max);
            }
        }
        
	    return minValid && maxValid;
	}
	
	private int getListSize(Map<QueryPath, Object> values, Metadata meta){
	    int size = 0;

	    //Check to see if a complex data element in list has been deleted
	    Map<String, Metadata> properties = meta.getProperties();		
		if (properties.containsKey(Data.WILDCARD_KEY.toString())){
			Metadata listMeta = properties.get(Data.WILDCARD_KEY.toString());
			if (listMeta != null && listMeta.getDataType().equals(DataType.DATA)){
				Object[] valueList = values.values().toArray();
				for (int i=0; i < valueList.length; i++){
					Object value = valueList[i];
					Data d = (Data)value;
					Boolean deleted = d.query(RUNTIME_DELETED_KEY);
					if (deleted == null || !deleted){
						size++;
					}
				}
			} else {
				size = values.size();
			}
		} else {
			size = values.size();
		}
		
		return size;
	}
		    
	//FIXME: This is a temp solution for getting cross field min value
    private Object getCrossFieldMinValue(DataModel model, QueryPath path, Metadata meta){
    	Object v = null;
    	List<ConstraintMetadata> constraints = meta.getConstraints();
    	for (int i=0; i < constraints.size(); i++) {
    		ConstraintMetadata cons = constraints.get(i);
			if (cons.getMinValue() != null && cons.getMinValue().contains("../")){
				QueryPath crossFieldPath = QueryPath.parse(path.toString());
				String crossFieldKey = cons.getMinValue().substring(3);
				crossFieldPath.remove(crossFieldPath.size()-1);
				crossFieldPath.add(new StringKey(crossFieldKey));
				v = model.get(crossFieldPath);
			}
		}
    	
    	return v;
    }
    
	//FIXME: This is a temp solution for getting cross field max value
    private Object getCrossFieldMaxValue(DataModel model, QueryPath path, Metadata meta){
    	Object v = null;
    	List<ConstraintMetadata> constraints = meta.getConstraints();
    	for (int i=0; i < constraints.size(); i++) {
    		ConstraintMetadata cons = constraints.get(i);
			if (cons.getMaxValue() != null && cons.getMaxValue().contains("../")){
				QueryPath crossFieldPath = QueryPath.parse(path.toString());
				String crossFieldKey = cons.getMinValue().substring(3);
				crossFieldPath.remove(crossFieldPath.size()-1);
				crossFieldPath.add(new StringKey(crossFieldKey));
				v = model.get(crossFieldPath);
			}
		}
    	
    	return v;
    }

    private void put(Map<String, Object> m, String key, Object value) {
        if (value != null) {
            m.put(key, value);
        }
    }
    
    private String asDateString(Date date){
    	DateTimeFormat dateTimeFormat = DateTimeFormat.getFormat("MM/dd/yyyy");
    	return dateTimeFormat.format(date);
    }


	
}
