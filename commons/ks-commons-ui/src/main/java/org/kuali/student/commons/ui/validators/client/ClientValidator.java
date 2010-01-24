package org.kuali.student.commons.ui.validators.client;

import java.util.HashMap;
import java.util.Map;

import org.kuali.student.commons.ui.messages.client.Messages;

/**
 * Client-side implementation of the Validator interface. Uses JSNI to evaluate the validator script locally.
 */
public class ClientValidator implements Validator {
    String script;
    Map<String, String> attributes;
    Messages messages;

    /**
     * Creates a new validator with the specified javascript and validator attributes
     * 
     * @param script
     *            the script to used when performing the validation
     * @param attributes
     *            the attributes to be used when performing the validation
     */
    public ClientValidator(String script, Map<String, String> attributes) {
        this.script = script;
        this.attributes = attributes;
    }

    /**
     * Sets the internationalization messages to be used when performing the validation
     */
    public void setMessages(Messages messages) {
        this.messages = messages;
    }

    /**
     * @see org.kuali.student.commons.ui.validators.client.Validator#validate(java.lang.Object)
     */
    public ValidationResult validate(Object value) {
        return validate(value, (Map<String, String>) null);
    }

    /**
     * @see org.kuali.student.commons.ui.validators.client.Validator#validate(java.lang.Object, java.lang.String)
     */
    public ValidationResult validate(Object value, String valueName) {
        Map<String, String> m = new HashMap<String, String>();
        m.put("valueName", valueName);
        return validate(value, m);
    }

    /**
     * @see org.kuali.student.commons.ui.validators.client.Validator#validate(java.lang.Object, java.lang.String,
     *      java.util.Map)
     */
    public ValidationResult validate(Object value, String valueName, Map<String, String> attributes) {
        Map<String, String> m = new HashMap<String, String>();
        m.put("valueName", valueName);
        m.putAll(attributes);
        return validate(value, m);
    }

    /**
     * @see org.kuali.student.commons.ui.validators.client.Validator#validate(java.lang.Object, java.util.Map)
     */
    public ValidationResult validate(Object value, Map<String, String> additionalAttributes) {

        ValidationResult result = new ValidationResult();

        Map<String, String> m = attributes;
        if ((additionalAttributes != null) && (additionalAttributes.size() > 0)) {
            m = new HashMap<String, String>();
            m.putAll(attributes);
            m.putAll(additionalAttributes);
        }

        System.out.println("DUMPING MESSAGES:\n" + messages.toString());
        doValidate(script, value, result, m, messages);

        return result;
    }

    private final native void doValidate(String script, Object value, ValidationResult validationResult, Map<String, String> attributes, Messages messages) /*-{
       	var adapter = new Array();
       	
       	// getMessage
       	adapter[0] = function(message) {
       		return messages.@org.kuali.student.commons.ui.messages.client.Messages::get(Ljava/lang/String;Ljava/util/Map;)(message, attributes);
       	};
       	
       	// addWarning
       	adapter[1] = function(message) {
       	    validationResult.@org.kuali.student.commons.ui.validators.client.ValidationResult::addWarning(Ljava/lang/String;)(message);
       	};
       	
       	// addError
       	adapter[2] = function(message) {
       	    //$wnd.document.write(typeof(validationResult));
            validationResult.@org.kuali.student.commons.ui.validators.client.ValidationResult::addError(Ljava/lang/String;)(message);
       	};
       	
       	// isOk
       	adapter[3] = function() {
       		return validationResult.@org.kuali.student.commons.ui.validators.client.ValidationResult::isOk()();
       	};
       	
       	// isWarn
       	adapter[4] = function() {
       		return validationResult.@org.kuali.student.commons.ui.validators.client.ValidationResult::isWarn()();
       	};
       	
       	// isError
       	adapter[5] = function() {
       		return validationResult.@org.kuali.student.commons.ui.validators.client.ValidationResult::isError()();
       	};
       	
       	// getAttributeString
       	adapter[6] = function(key, defaultValue) {
       		if (typeof(defaultValue) == 'undefined') {
       			defaultValue = null;
       		}
       		var result = attributes.@java.util.Map::get(Ljava/lang/Object;)(key);
       		if (typeof(result) == 'undefined' || result == null) {
       			result = defaultValue;
       		}
       		return result;
       	};
       	
       	// getAttributeNumber
       	adapter[7] = function(key, defaultValue) {
       		if (typeof(defaultValue) == 'undefined') {
       			defaultValue = null;
       		}
       		var result;
       		var v = attributes.@java.util.Map::get(Ljava/lang/Object;)(key);
       		if (typeof(v) == 'undefined' || v == null) {
       			result = defaultValue;
       		} else {
       			result = parseFloat(v.toString());
       			if (isNaN(result)) {
       				result = defaultValue;
       			}
       		}
       		return result;
       	};
       	
       	// getAttributeBoolean
       	adapter[8] = function(key, defaultValue) {
       		if (typeof(defaultValue) == 'undefined') {
       			defaultValue = null;
       		}
       		var result;
       		var v = attributes.@java.util.Map::get(Ljava/lang/Object;)(key);
       		if (typeof(v) == 'undefined' || v == null) {
       			result = defaultValue;
       		} else {
       			result = (v.toLowerCase == 'true');
       		}
       		return result;
       	};
       	
       	// getAttributeDate
       	adapter[9] = function(key, defaultValue) {
       		// TODO implement getAttributeDate
       		return null;
       	}; 
       	
       	// getValueString
       	adapter[10] = function() {
       		if (typeof(value) == 'undefined' || value == null) {
       			return null;
       		} else {
       			return value.toString();
       		}
       	};
       	
       	// getValueNumber
       	adapter[11] = function() {
       		if (typeof(value) == 'undefined' || value == null) {
       			return null;
       		} else if (typeof(value) == 'number' ) {
       			return value;
       		} else {
       			return parseFloat(value.toString());
       		}
       	};
       	
       	// getValueBoolean
       	adapter[12] = function() {
       		if (typeof(value) == 'undefined' || value == null) {
       			return false;
       		} else if (typeof(value) == 'boolean' ) {
       			return value;
       		} else {
       			return value.toString().toLowerCase == 'true';
       		}
       	};
       	
       	// getValueDate
       	adapter[13] = function() {
       		// TODO implement getValueDate
       	}
       	
       	// misc utility functions for validation
       	// trim
       	adapter[14] = function(str) {
       		str = str.replace(/^\s+/, '');
       		for (var i = str.length - 1; i >= 0; i--) {
       			if (/\S/.test(str.charAt(i))) {
       				str = str.substring(0, i + 1);
       				break;
       			}
       		}
       		return str;
       	};
       	
       	var impl = function(adapter) {
       		var header = "var args=arguments[0];var getMessage=args[0];var addWarning=args[1];var addError=args[2];var isOk=args[3];var isWarn=args[4];var isError=args[5];var getAttributeString=args[6];var getAttributeNumber=args[7];var getAttributeBoolean=args[8];var getAttributeDate=args[9];var getValueString=args[10];var getValueNumber=args[11];var getValueBoolean=args[12];var getValueDate=args[13];var trim=args[14];";
       		eval(header + script);
       	}.call(impl,adapter);
       	
       }-*/;
}
