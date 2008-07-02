package org.kuali.student.commons.ui.validators.server;

import java.util.HashMap;
import java.util.Map;

import org.kuali.student.commons.ui.messages.client.Messages;
import org.kuali.student.commons.ui.validators.client.ValidationResult;
import org.kuali.student.commons.ui.validators.client.Validator;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

/**
 * Server implementation of the Validator interface. Uses the Rhino JS engine to execute the validator javascript.
 */
public class ServerValidator implements Validator {
    /**
     * Used to construct the adapter required to write javascript that can be executed both by Rhino and GWT
     */
    public static final String SERVER_VALIDATOR_ADAPTER_HEADER = "function validate(value, result, attributes, messages) {\n" + "	validateImpl(\n" + "		function getMessage(message) {\n" + "			return messages.get(message, attributes);\n" + "		},\n" + "		function addWarning(message) {\n" + "			result.addWarning(message);\n" + "		},\n" + "		function addError(message) {\n" + "			result.addError(message);\n" + "		},\n" + "		function isOk() {\n" + "			return result.isOk();\n" + "		},\n" + "		function isWarn() {\n" + "			return result.isWarn();\n" + "		},\n" + "		function isError() {\n" + "			return result.isError();\n" + "		},\n" + "		function getAttributeString(key, defaultValue) {\n" + "			if (typeof(defaultValue) == 'undefined') {\n" + "				defaultValue = null;\n" + "			}\n" + "			var result = attributes.get(key);\n" + "			if (typeof(result) == 'undefined' || result == null) {\n" + "				result = defaultValue;\n" + "			}\n" + "			return result;\n" + "		},\n"
            + "		function getAttributeNumber(key, defaultValue) {\n" + "			if (typeof(defaultValue) == 'undefined') {\n" + "				defaultValue = null;\n" + "			}\n" + "			var result = parseFloat(attributes.get(key));\n" + "			if (typeof(result) == 'undefined' || result == null || isNaN(result)) {\n" + "				result = defaultValue;\n" + "			}\n" + "			return result;\n" + "		},\n" + "		function getAttributeBoolean(key, defaultValue) {\n" + "			if (typeof(defaultValue) == 'undefined') {\n" + "				defaultValue = null;\n" + "			}\n" + "			var v = attributes.get(key);\n" + "			if (typeof(v) == 'undefined' || v == null) {\n" + "				result = defaultValue;\n" + "			} else {\n" + "				result = (v.toLowerCase == 'true');\n" + "			}\n" + "			return result;\n" + "		},\n" + "		function getAttributeDate(key, defaultValue) {\n" + "			// TODO implement getAttributeDate\n" + "			return null;\n" + "		}, \n" + "		function getValueString() {\n" + "			if (typeof(value) == 'undefined' || value == null) {\n" + "				return null;\n"
            + "			} else {\n" + "				return value.toString();\n" + "			}\n" + "		},\n" + "		function getValueNumber() {\n" + "			if (typeof(value) == 'undefined' || value == null) {\n" + "				return null;\n" + "			} else if (typeof(value) == 'number' ) {\n" + "				return value;\n" + "			} else {\n" + "				return parseFloat(value);\n" + "			}\n" + "		},\n" + "		function getValueBoolean() {\n" + "			if (typeof(value) == 'undefined' || value == null) {\n" + "				return false;\n" + "			} else if (typeof(value) == 'boolean' ) {\n" + "				return value;\n" + "			} else {\n" + "				return value.toString().toLowerCase == 'true';\n" + "			}\n" + "		},\n" + "		function getValueDate() {\n" + "			// TODO implement getValueDate\n" + "			return null;\n" + "		},\n" + "		function trim(str) {\n" + "			str = str.replace(/^\\s+/, ''); \n" + "			for (var i = str.length - 1; i >= 0; i--) { \n" + "				if (/\\S/.test(str.charAt(i))) { \n" + "					str = str.substring(0, i + 1); \n" + "					break; \n" + "				} \n" + "			} \n"
            + "			return str; \n" + "		}\n" + "	);\n" + "}\n" + "function validateImpl(getMessage, addWarning, addError, isOk, isWarn, isError, \n" + "	getAttributeString, getAttributeNumber, getAttributeBoolean, getAttributeDate,\n" + "	getValueString, getValueNumber, getValueBoolean, getValueDate, trim) {\n";

    /**
     * Used to construct the adapter required to write javascript that can be executed both by Rhino and GWT
     */
    public static final String SERVER_VALIDATOR_ADAPTER_FOOTER = "}";

    String script;
    Map<String, String> attributes;
    Messages messages;
    ScriptableObject topScope;
    Function function;

    /**
     * Creates a new validator with the specified script and validator attributes
     * 
     * @param script
     *            the validator script
     * @param attributes
     *            the validator attributes
     */
    public ServerValidator(String script, Map<String, String> attributes) {
        this.script = script;
        this.attributes = attributes;
    }

    /**
     * @see org.kuali.student.commons.ui.validators.client.Validator#setMessages(org.kuali.student.commons.ui.messages.client.Messages)
     */
    @Override
    public void setMessages(Messages messages) {
        this.messages = messages;
    }

    /**
     * @see org.kuali.student.commons.ui.validators.client.Validator#validate(java.lang.Object)
     */
    @Override
    public ValidationResult validate(Object value) {
        return validate(value, (Map<String, String>) null);
    }

    /**
     * @see org.kuali.student.commons.ui.validators.client.Validator#validate(java.lang.Object, java.lang.String)
     */
    @Override
    public ValidationResult validate(Object value, String valueName) {
        Map<String, String> m = new HashMap<String, String>();
        m.put("valueName", valueName);
        return validate(value, m);
    }

    /**
     * @see org.kuali.student.commons.ui.validators.client.Validator#validate(java.lang.Object, java.lang.String,
     *      java.util.Map)
     */
    @Override
    public ValidationResult validate(Object value, String valueName, Map<String, String> attributes) {
        Map<String, String> m = new HashMap<String, String>();
        m.put("valueName", valueName);
        m.putAll(attributes);
        return validate(value, m);
    }

    /**
     * @see org.kuali.student.commons.ui.validators.client.Validator#validate(java.lang.Object, java.util.Map)
     */
    @Override
    public ValidationResult validate(Object value, Map<String, String> additionalAttributes) {
        ValidationResult result = new ValidationResult();
        Map<String, String> m = attributes;
        if ((additionalAttributes != null) && (additionalAttributes.size() > 0)) {
            m = new HashMap<String, String>();
            m.putAll(attributes);
            m.putAll(additionalAttributes);
        }
        Context c = ContextFactory.getGlobal().enterContext();
        try {
            Scriptable scope = c.initStandardObjects(topScope);
            getFunction().call(c, scope, scope, new Object[]{value, result, m, messages});
        } finally {
            Context.exit();
        }
        return result;
    }

    private Function getFunction() {
        if (function == null) {
            String source = SERVER_VALIDATOR_ADAPTER_HEADER + script + SERVER_VALIDATOR_ADAPTER_FOOTER;
            // System.out.println(source);
            Context c = ContextFactory.getGlobal().enterContext();
            try {
                topScope = c.initStandardObjects();
                c.evaluateString(topScope, source, "anonymous_validator", 1, null);
                function = (Function) ScriptableObject.getProperty(topScope, "validate");
            } finally {
                Context.exit();
            }
        }
        return function;
    }
}
