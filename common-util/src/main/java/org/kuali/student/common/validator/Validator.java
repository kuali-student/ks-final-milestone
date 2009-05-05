package org.kuali.student.common.validator;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.util.MessageUtils;
import org.kuali.student.core.dictionary.dto.Field;
import org.kuali.student.core.dictionary.dto.FieldDescriptor;
import org.kuali.student.core.dictionary.dto.ObjectStructure;
import org.kuali.student.core.dictionary.dto.State;
import org.kuali.student.core.dictionary.dto.Type;
import org.kuali.student.core.dto.HasAttributes;
import org.kuali.student.core.dto.HasTypeState;
import org.kuali.student.core.messages.dto.Message;
import org.kuali.student.core.validation.dto.ValidationResult;

public class Validator {
    private Map<String, String> messages = new HashMap<String, String>();
    private DateParser dateParser = null;

    public DateParser getDateParser() {
        return dateParser;
    }

    public void setDateParser(DateParser dateParser) {
        this.dateParser = dateParser;
    }

    public void addMessages(List<Message> list) {
        for (Message m : list) {
            messages.put(m.getId(), m.getValue());
        }
    }

    public ValidationResult validate(String key, Object value, Map<String, Object> fieldDesc) {
        ValidationResult result = new ValidationResult();
        result.setKey(key);
        String dataType = fieldDesc.get("dataType").toString().trim();
        if (dataType.equalsIgnoreCase("integer")) {
            validateInteger(value, fieldDesc, result);
        } else if (dataType.equalsIgnoreCase("string")) {
            validateString(value, fieldDesc, result);
        } else if (dataType.equalsIgnoreCase("Date")) {
            validateDate(value, fieldDesc, result);
        } else if (dataType.equalsIgnoreCase("long")) {
            validateLong(value, fieldDesc, result);
        } else if (dataType.equalsIgnoreCase("float")) {
            validateFloat(value, fieldDesc, result);
        } else if (dataType.equalsIgnoreCase("double")) {
            validateDouble(value, fieldDesc, result);
        } else if (dataType.equalsIgnoreCase("boolean")) {
            validateBoolean(value, fieldDesc, result);
        } else {
            // TODO any other types?
        }

        return result;
    }

    private Map<String, PropertyDescriptor> getBeanInfo(Class<?> clazz) {
        Map<String,PropertyDescriptor> properties = new HashMap<String, PropertyDescriptor>();
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
    
    public List<ValidationResult> validateTypeStateObject(Object object, ObjectStructure objStruct) {
        List<ValidationResult> results = new ArrayList<ValidationResult>();

        Map<String, PropertyDescriptor> beanInfo = getBeanInfo(object.getClass());
        
        List<Type> types = objStruct.getType();
        for(Type t: types){        	
            if((object instanceof HasTypeState && t.getKey().equalsIgnoreCase(((HasTypeState)object).getType())) || !(object instanceof HasTypeState)){
                for(State s: t.getState()){
                    if((object instanceof HasTypeState && s.getKey().equalsIgnoreCase(((HasTypeState)object).getState())) || !(object instanceof HasTypeState)){
                        for(Field f: s.getField()){
                            PropertyDescriptor propertyDescriptor = beanInfo.get(f.getKey());
                           	results.addAll(validateField(f,propertyDescriptor,object));
                        }
                        break;
                    }
                }
                break;
            }
        }
        return results;
    }
    private List<ValidationResult> validateField(Field f, PropertyDescriptor propertyDescriptor, Object object){
    	
    	List<ValidationResult> results = new ArrayList<ValidationResult>();
        Object value = null;
    	if(f.getFieldDescriptor() instanceof FieldDescriptor){
            Map<String, Object> map = ((FieldDescriptor)f.getFieldDescriptor()).toMap();

            if(propertyDescriptor != null && propertyDescriptor.getReadMethod() != null) {
                try {
                    value = propertyDescriptor.getReadMethod().invoke(object);
                } catch (Exception e) {
                }
            } else if(object instanceof HasAttributes) {
                value = ((HasAttributes)object).getAttributes().get(f.getKey());
            }
            if(value instanceof Collection){
            	if(((FieldDescriptor)f.getFieldDescriptor()).getMinOccurs()>((Collection<?>)value).size()){
                    ValidationResult result = new ValidationResult();
                    result.setKey(f.getKey());
                    result.addError(messages.get("validation.minOccurs"));
                    results.add(result);
            	}
            	if(((FieldDescriptor)f.getFieldDescriptor()).getMaxOccurs()<((Collection<?>)value).size()){
                    ValidationResult result = new ValidationResult();
                    result.setKey(f.getKey());
                    result.addError(messages.get("validation.maxOccurs"));
                    results.add(result);
            	}
            	for(Object o:(Collection<?>)value){
            		results.add(validate(f.getKey(), o, map));
            	}
            }else{
            	results.add(validate(f.getKey(), value, map));
            }
        }else if(f.getFieldDescriptor() instanceof ObjectStructure){
            if(propertyDescriptor != null && propertyDescriptor.getReadMethod() != null) {
                try {
                    value = propertyDescriptor.getReadMethod().invoke(object);
                } catch (Exception e) {
                }
            }
            if(value instanceof Collection){
            	for(Object o:(Collection<?>)value){
            		results.addAll(validateTypeStateObject(o,(ObjectStructure)f.getFieldDescriptor()));
            	}
            }else{
            	results.addAll(validateTypeStateObject(value,(ObjectStructure)f.getFieldDescriptor()));
            }
        }
    	return results;
    }
    
    private void validateBoolean(Object value, Map<String, Object> fieldDesc, ValidationResult result) {
        Integer minOccurs = getInteger("minOccurs", fieldDesc);

        if (value == null) {
            if (minOccurs != null && minOccurs > 0) {
                result.addError(messages.get("validation.required"));
            }
            return;
        }

        if (!(value instanceof Boolean)) {
            String s = value.toString().trim();
            if (s.equals("")) {
                if (minOccurs != null && minOccurs > 0) {
                    result.addError(messages.get("validation.required"));
                }
                return;
            } else {
                try {
                    Boolean.valueOf(value.toString());
                } catch (Exception e) {
                    result.addError(messages.get("validation.mustBeBoolean"));
                }
            }
        }

    }

    private void validateDouble(Object value, Map<String, Object> fieldDesc, ValidationResult result) {
        Double v = null;
        Integer minOccurs = getInteger("minOccurs", fieldDesc);

        if (value == null) {
            if (minOccurs != null && minOccurs > 0) {
                result.addError(messages.get("validation.required"));
            }
            return;
        }

        if (value instanceof Number) {
            v = ((Number) value).doubleValue();
        } else {
            String s = value.toString().trim();
            if (s.equals("")) {
                if (minOccurs != null && minOccurs > 0) {
                    result.addError(messages.get("validation.required"));
                }
                return;
            } else {
                try {
                    v = Double.valueOf(value.toString());
                } catch (Exception e) {
                    result.addError(messages.get("validation.mustBeDouble"));
                }
            }
        }

        if (result.isOk()) {
            Double maxValue = getDouble("maxValue", fieldDesc);
            Double minValue = getDouble("minValue", fieldDesc);

            if (maxValue != null && minValue != null) {
                // validate range
                if (v > maxValue || v < minValue) {
                    result.addError(MessageUtils.interpolate(messages.get("validation.outOfRange"), fieldDesc));
                }
            } else if (maxValue != null) {
                if (v > maxValue) {
                    result.addError(MessageUtils.interpolate(messages.get("validation.maxValueFailed"), fieldDesc));
                }
            } else if (minValue != null) {
                if (v < minValue) {
                    result.addError(MessageUtils.interpolate(messages.get("validation.minValueFailed"), fieldDesc));
                }
            }
        }
    }

    private void validateFloat(Object value, Map<String, Object> fieldDesc, ValidationResult result) {
        Float v = null;
        Integer minOccurs = getInteger("minOccurs", fieldDesc);

        if (value == null) {
            if (minOccurs != null && minOccurs > 0) {
                result.addError(messages.get("validation.required"));
            }
            return;
        }

        if (value instanceof Number) {
            v = ((Number) value).floatValue();
        } else {
            String s = value.toString().trim();
            if (s.equals("")) {
                if (minOccurs != null && minOccurs > 0) {
                    result.addError(messages.get("validation.required"));
                }
                return;
            } else {
                try {
                    v = Float.valueOf(value.toString());
                } catch (Exception e) {
                    result.addError(messages.get("validation.mustBeFloat"));
                }
            }
        }

        if (result.isOk()) {
            Float maxValue = getFloat("maxValue", fieldDesc);
            Float minValue = getFloat("minValue", fieldDesc);

            if (maxValue != null && minValue != null) {
                // validate range
                if (v > maxValue || v < minValue) {
                    result.addError(MessageUtils.interpolate(messages.get("validation.outOfRange"), fieldDesc));
                }
            } else if (maxValue != null) {
                if (v > maxValue) {
                    result.addError(MessageUtils.interpolate(messages.get("validation.maxValueFailed"), fieldDesc));
                }
            } else if (minValue != null) {
                if (v < minValue) {
                    result.addError(MessageUtils.interpolate(messages.get("validation.minValueFailed"), fieldDesc));
                }
            }
        }
    }

    private void validateLong(Object value, Map<String, Object> fieldDesc, ValidationResult result) {
        Long v = null;
        Integer minOccurs = getInteger("minOccurs", fieldDesc);

        if (value == null) {
            if (minOccurs != null && minOccurs > 0) {
                result.addError(messages.get("validation.required"));
            }
            return;
        }

        if (value instanceof Number) {
            v = ((Number) value).longValue();
        } else {
            String s = value.toString().trim();
            if (s.equals("")) {
                if (minOccurs != null && minOccurs > 0) {
                    result.addError(messages.get("validation.required"));
                }
                return;
            } else {
                try {
                    v = Long.valueOf(value.toString());
                } catch (Exception e) {
                    result.addError(messages.get("validation.mustBeLong"));
                }
            }
        }

        if (result.isOk()) {
            Long maxValue = getLong("maxValue", fieldDesc);
            Long minValue = getLong("minValue", fieldDesc);

            if (maxValue != null && minValue != null) {
                // validate range
                if (v > maxValue || v < minValue) {
                    result.addError(MessageUtils.interpolate(messages.get("validation.outOfRange"), fieldDesc));
                }
            } else if (maxValue != null) {
                if (v > maxValue) {
                    result.addError(MessageUtils.interpolate(messages.get("validation.maxValueFailed"), fieldDesc));
                }
            } else if (minValue != null) {
                if (v < minValue) {
                    result.addError(MessageUtils.interpolate(messages.get("validation.minValueFailed"), fieldDesc));
                }
            }
        }

    }

    private void validateInteger(Object value, Map<String, Object> fieldDesc, ValidationResult result) {
        Integer v = null;
        Integer minOccurs = getInteger("minOccurs", fieldDesc);

        if (value == null) {
            if (minOccurs != null && minOccurs > 0) {
                result.addError(messages.get("validation.required"));
            }
            return;
        }

        if (value instanceof Number) {
            v = ((Number) value).intValue();
        } else {
            String s = value.toString().trim();
            if (s.equals("")) {
                if (minOccurs != null && minOccurs > 0) {
                    result.addError(messages.get("validation.required"));
                }
                return;
            } else {
                try {
                    v = Integer.valueOf(value.toString());
                } catch (Exception e) {
                    result.addError(messages.get("validation.mustBeInteger"));
                }
            }
        }

        if (result.isOk()) {
            Integer maxValue = getInteger("maxValue", fieldDesc);
            Integer minValue = getInteger("minValue", fieldDesc);

            if (maxValue != null && minValue != null) {
                // validate range
                if (v > maxValue || v < minValue) {
                    result.addError(MessageUtils.interpolate(messages.get("validation.outOfRange"), fieldDesc));
                }
            } else if (maxValue != null) {
                if (v > maxValue) {
                    result.addError(MessageUtils.interpolate(messages.get("validation.maxValueFailed"), fieldDesc));
                }
            } else if (minValue != null) {
                if (v < minValue) {
                    result.addError(MessageUtils.interpolate(messages.get("validation.minValueFailed"), fieldDesc));
                }
            }
        }

    }

    private void validateDate(Object value, Map<String, Object> fieldDesc, ValidationResult result) {
        Date v = null;
        Integer minOccurs = getInteger("minOccurs", fieldDesc);

        if (value == null) {
            if (minOccurs != null && minOccurs > 0) {
                result.addError(messages.get("validation.required"));
            }
            return;
        }

        if (value instanceof Date) {
            v = (Date) value;
        } else {
            String s = value.toString().trim();
            if (s.equals("")) {
                if (minOccurs != null && minOccurs > 0) {
                    result.addError(messages.get("validation.required"));
                }
                return;
            } else {
                try {
                    v = dateParser.parseDate(value.toString());
                } catch (Exception e) {
                    result.addError(messages.get("validation.mustBeDate"));
                }
            }
        }

        if (result.isOk()) {
            Date maxValue = getDate("maxValue", fieldDesc);
            Date minValue = getDate("minValue", fieldDesc);

            if (maxValue != null && minValue != null) {
                // validate range
                if (v.getTime() > maxValue.getTime() || v.getTime() < minValue.getTime()) {
                    result.addError(MessageUtils.interpolate(messages.get("validation.outOfRange"), fieldDesc));
                }
            } else if (maxValue != null) {
                if (v.getTime() > maxValue.getTime()) {
                    result.addError(MessageUtils.interpolate(messages.get("validation.maxValueFailed"), fieldDesc));
                }
            } else if (minValue != null) {
                if (v.getTime() < minValue.getTime()) {
                    result.addError(MessageUtils.interpolate(messages.get("validation.minValueFailed"), fieldDesc));
                }
            }
        }
    }

    private void validateString(Object value, Map<String, Object> fieldDesc, ValidationResult result) {
        Integer maxLength = getInteger("maxLength", fieldDesc);
        Integer minLength = getInteger("minLength", fieldDesc);
        Integer minOccurs = getInteger("minOccurs", fieldDesc);

        String s = (value == null) ? "" : value.toString().trim();

        if (s.equals("")) {
            if (minOccurs != null && minOccurs > 0) {
                result.addError(messages.get("validation.required"));
                return;
            }
        }

        if (maxLength != null && minLength != null) {
            if (s.length() > maxLength || s.length() < minLength) {
                result.addError(MessageUtils.interpolate(messages.get("validation.lengthOutOfRange"), fieldDesc));
            }
        } else if (maxLength != null) {
            if (s.length() > maxLength) {
                result.addError(MessageUtils.interpolate(messages.get("validation.maxLengthFailed"), fieldDesc));
            }
        } else if (minLength != null) {
            if (s.length() < minLength) {
                result.addError(MessageUtils.interpolate(messages.get("validation.minLengthFailed"), fieldDesc));
            }
        }

        // validChars, a non-set valid chars field means all characters valid (other than those in invalid)
        if(fieldDesc.get("validChars") != null) {
            String validChars = ((String) fieldDesc.get("validChars")).trim();
            if (!validChars.equals("")) {
                char[] valueChars = s.toCharArray();
                for (char c : valueChars) {
                    if (validChars.indexOf(c) == -1) {
                        result.addError(MessageUtils.interpolate(messages.get("validation.validCharsFailed"), fieldDesc));
                    }
                }
    
            }
        }
        // invalidChars, a non-set invalid chars field means no chars are specified as invalid
        if(fieldDesc.get("invalidChars") != null) {
            String invalidChars = ((String) fieldDesc.get("invalidChars")).trim();
            if (!invalidChars.equals("")) {
                char[] valueChars = s.toCharArray();
                for (char c : valueChars) {
                    if (invalidChars.indexOf(c) >= 0) {
                        result.addError(MessageUtils.interpolate(messages.get("validation.invalidCharsFailed"), fieldDesc));
                    }
                }
            }
        }

    }

    private Integer getInteger(String key, Map<String, Object> m) {
        Integer result = null;
        Object o = m.get(key);
        if(o instanceof Integer)
            return (Integer)o;
        if(o == null)
            return null;
        if(o instanceof Number)
            return ((Number)o).intValue();
        String s = o.toString();
        if (s != null && s.trim().length() > 0) {
            result = Integer.valueOf(s.trim());
        }
        return result;
    }

    private Long getLong(String key, Map<String, Object> m) {
        Long result = null;
        Object o = m.get(key);
        if(o instanceof Long)
            return (Long)o;
        if(o == null)
            return null;
        if(o instanceof Number)
            return ((Number)o).longValue();
        String s = o.toString();
        if (s != null && s.trim().length() > 0) {
            result = Long.valueOf(s.trim());
        }
        return result;
    }

    private Float getFloat(String key, Map<String, Object> m) {
        Float result = null;
        Object o = m.get(key);
        if(o instanceof Float)
            return (Float)o;
        if(o == null)
            return null;
        if(o instanceof Number)
            return ((Number)o).floatValue();
        String s = o.toString();
        if (s != null && s.trim().length() > 0) {
            result = Float.valueOf(s.trim());
        }
        return result;
    }

    private Double getDouble(String key, Map<String, Object> m) {
        Double result = null;
        Object o = m.get(key);
        if(o instanceof Double)
            return (Double)o;
        if(o == null)
            return null;
        if(o instanceof Number)
            return ((Number)o).doubleValue();
        String s = o.toString();
        if (s != null && s.trim().length() > 0) {
            result = Double.valueOf(s.trim());
        }
        return result;
    }

    private Date getDate(String key, Map<String, Object> m) {
        Date result = null;
        Object o = m.get(key);
        if(o instanceof Date)
            return (Date)o;
        if(o == null)
            return null;
        String s = o.toString();
        if (s != null && s.trim().length() > 0) {
            result = dateParser.parseDate(s.trim());
        }
        return result;
    }
}
