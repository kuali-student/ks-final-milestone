package org.kuali.student.common.validator;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.util.MessageUtils;
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

    public ValidationResult validate(String key, Object value, Map<String, String> fieldDesc) {
        ValidationResult result = new ValidationResult();
        result.setKey(key);
        String dataType = fieldDesc.get("dataType").trim();
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

    private void validateBoolean(Object value, Map<String, String> fieldDesc, ValidationResult result) {
        // TODO implement this
    }

    private void validateDouble(Object value, Map<String, String> fieldDesc, ValidationResult result) {
        // TODO implement this
    }

    private void validateFloat(Object value, Map<String, String> fieldDesc, ValidationResult result) {
        // TODO implement this
    }

    private void validateLong(Object value, Map<String, String> fieldDesc, ValidationResult result) {
        // TODO implement this
    }

    private void validateInteger(Object value, Map<String, String> fieldDesc, ValidationResult result) {
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
            Integer minValue = getInteger("maxValue", fieldDesc);

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

    private void validateDate(Object value, Map<String, String> fieldDesc, ValidationResult result) {
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
            Date minValue = getDate("maxValue", fieldDesc);

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

    private void validateString(Object value, Map<String, String> fieldDesc, ValidationResult result) {
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
        String validChars = ((String) fieldDesc.get("validChars")).trim();
        if (validChars != null && !validChars.equals("")) {
            char[] valueChars = s.toCharArray();
            for (char c : valueChars) {
                if (validChars.indexOf(c) == -1) {
                    result.addError(MessageUtils.interpolate(messages.get("validation.validCharsFailed"), fieldDesc));
                }
            }

        }
        // invalidChars, a non-set invalid chars field means no chars are specified as invalid
        String invalidChars = ((String) fieldDesc.get("invalidChars")).trim();
        if (invalidChars != null && !invalidChars.equals("")) {
            char[] valueChars = s.toCharArray();
            for (char c : valueChars) {
                if (invalidChars.indexOf(c) >= 0) {
                    result.addError(MessageUtils.interpolate(messages.get("validation.invalidCharsFailed"), fieldDesc));
                }
            }
        }

    }

    private Integer getInteger(String key, Map<String, String> m) {
        Integer result = null;
        String s = m.get(key);
        if (s != null && s.trim().length() > 0) {
            result = Integer.valueOf(s.trim());
        }
        return result;
    }

    private Date getDate(String key, Map<String, String> m) {
        Date result = null;
        String s = m.get(key);
        if (s != null && s.trim().length() > 0) {
            result = dateParser.parseDate(s.trim());
        }
        return result;
    }
}
