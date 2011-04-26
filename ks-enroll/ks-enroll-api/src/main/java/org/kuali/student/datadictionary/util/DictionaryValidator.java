/*
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.datadictionary.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.kuali.rice.core.api.datetime.DateTimeService;
import org.kuali.rice.kns.datadictionary.AttributeDefinition;
import org.kuali.rice.kns.datadictionary.DataObjectEntry;
import org.kuali.rice.kns.datadictionary.validation.DataType;
import org.kuali.rice.kns.datadictionary.validation.ValidationUtils;
import org.kuali.rice.kns.datadictionary.validation.constraint.CaseConstraint;
import org.kuali.rice.kns.datadictionary.validation.constraint.LookupConstraint;
import org.kuali.rice.kns.datadictionary.validation.constraint.ValidCharactersConstraint;
import org.kuali.rice.kns.datadictionary.validation.constraint.WhenConstraint;




public class DictionaryValidator {

    private DateTimeService dateTimeService;
    private DataObjectEntry ode;
    private Set<DataObjectEntry> alreadyValidated;

    public DictionaryValidator(DataObjectEntry ode,
            Set<DataObjectEntry> alreadyValidated) {
        this.ode = ode;
        this.alreadyValidated = alreadyValidated;
    }

    public DateTimeService getDateTimeService() {
        return dateTimeService;
    }

    public void setDateTimeService(DateTimeService dateTimeService) {
        this.dateTimeService = dateTimeService;
    }
    
    public List<String> validate() {
        List<String> errors = new ArrayList();
        if (ode.getFullClassName() == null) {
            errors.add("The class name cannot be be left null");
        }
        if (ode.getEntryClass() == null) {
            errors.add("The entry class should not be left null");
        }
        if ( ! ode.getEntryClass().getName ().equals(ode.getFullClassName())) {
            errors.add("The entry class should match the full class name");
        }

//  else if (this.getClass (ode.getName ()) == null)
//  {
//   errors.add ("The name does not exist on the class path");
//  }

        if (ode.getAttributes() == null) {
            errors.add("getAttribues () is null -- null for field defintion");
            return errors;
        }
        if (ode.getAttributes().size() == 0) {
            errors.add("No fields defined for complex object structure");
            return errors;
        }
        Set<String> fieldNames = new HashSet();
        for (AttributeDefinition ad : ode.getAttributes()) {
            if (ad.getName() != null) {
                if (!fieldNames.add(ad.getName())) {
                    errors.add(ad.getName() + " is defined more than once");
                }
            }
            errors.addAll(validateAttributeDefinition(ad));
        }
        return errors;
    }

// private Class getClass (String className)
// {
//  try
//  {
//   return Class.forName (className);
//  }
//  catch (ClassNotFoundException ex)
//  {
//   return null;
//  }
// }
    private List<String> validateAttributeDefinition(AttributeDefinition ad) {
        List<String> errors = new ArrayList();
        if (ad.getName() == null) {
            errors.add("name cannot be null");
        } else if (ad.getName().trim().equals("")) {
            errors.add("name cannot be blank");
        }
        if (ad.getDataType().equals(DataType.COMPLEX)) {
            errorIfNotNull(errors, ad, "exclusiveMin", ad.getExclusiveMin());
            errorIfNotNull(errors, ad, "inclusiveMax", ad.getInclusiveMax());
            errorIfNotNull(errors, ad, "max length", ad.getMaxLength());
            errorIfNotNull(errors, ad, "min length", ad.getMinLength());
            errorIfNotNull(errors, ad, "valid chars", ad.getValidCharactersConstraint());
            errorIfNotNull(errors, ad, "lookup", ad.getLookupDefinition());
        }
//        validateConversion(errors, ad.getName(), "defaultValue", ad.getDataType(), ad.getDefaultValue());
        validateConversion(errors, ad.getName(), "exclusiveMin", ad.getDataType(), ad.getExclusiveMin());
        validateConversion(errors, ad.getName(), "inclusiveMax", ad.getDataType(), ad.getInclusiveMax());
        //TODO: Cross compare to make sure min is not greater than max and that default value is valid itself

        if (ad.getLookupDefinition() != null) {
            errors.addAll(validateLookup(ad, ad.getLookupDefinition()));
        }
        if (ad.getCaseConstraint() != null) {
            errors.addAll(validateCase(ad, ad.getCaseConstraint()));
        }
        if (ad.getValidCharactersConstraint() != null) {
            errors.addAll(validateValidChars(ad, ad.getValidCharactersConstraint()));
        }
        return errors;
    }

    private void errorIfNotNull(List<String> errors, AttributeDefinition fd,
            String validation,
            Object value) {
        if (value != null) {
            errors.add("field " + fd.getName() + " has a " + validation
                    + " but it cannot be specified on a complex type");
        }
    }

    private Object validateConversion(List<String> errors, String fieldName,
            String propertyName, DataType dataType,
            Object value) {
        if (value == null) {
            return null;
        }
        switch (dataType) {
            case STRING:
                return value.toString().trim();
//    case DATE, TRUNCATED_DATE, BOOLEAN, INTEGER, FLOAT, DOUBLE, LONG, COMPLEX
            case LONG:
                try {
                    return ValidationUtils.getLong(value);
                } catch (NumberFormatException ex) {
                    errors.add(
                            "field " + fieldName
                            + " has a " + propertyName
                            + " that cannot be converted into a long integer");
                }
                return null;
            case INTEGER:
                try {
                    return ValidationUtils.getInteger(value);
                } catch (NumberFormatException ex) {
                    errors.add(
                            "field " + fieldName
                            + " has a " + propertyName + " that cannot be converted into an integer");
                }
                return null;
            case FLOAT:
                try {
                    return ValidationUtils.getFloat(value);
                } catch (NumberFormatException ex) {
                    errors.add(
                            "field " + fieldName
                            + " has a " + propertyName
                            + " that cannot be converted into a floating point value");
                }
                return null;
            case DOUBLE:
                try {
                    return ValidationUtils.getFloat(value);
                } catch (NumberFormatException ex) {
                    errors.add(
                            "field " + fieldName
                            + " has a " + propertyName
                            + " that cannot be converted into a double sized floating point value");
                }
                return null;
            case BOOLEAN:
                if (value instanceof Boolean) {
                    return ((Boolean) value).booleanValue();
                }
                if (value instanceof String) {
                    if (((String) value).trim().equalsIgnoreCase("true")) {
                        return true;
                    }
                    if (((String) value).trim().equalsIgnoreCase("false")) {
                        return true;
                    }
                }
                errors.add(
                        "field " + fieldName
                        + " has a " + propertyName
                        + " that cannot be converted into a boolean true/false");
                return null;
            case DATE:
            case TRUNCATED_DATE:
                if (value instanceof Date) {
                    return (Date) value;
                }
                try {
                    // TODO: make the date parser configurable like the validator is
                    return ValidationUtils.getDate(value, dateTimeService);
                } catch (Exception e) {
                    errors.add(
                            "field " + fieldName
                            + " has a " + propertyName
                            + " that cannot be converted into a date");
                }
                return null;
            default:
                errors.add(
                        "field " + fieldName
                        + " has a " + propertyName
                        + " that cannot be converted into an unknown/unhandled data type");
                return null;
        }
    }

    private List<String> validateValidChars(AttributeDefinition fd,
            ValidCharactersConstraint vc) {
        List<String> errors = new ArrayList();
        String validChars = vc.getValue();
        int typIdx = validChars.indexOf(":");
        String processorType = "regex";
        if (-1 == typIdx) {
            validChars = "[" + validChars + "]*";
        } else {
            processorType = validChars.substring(0, typIdx);
            validChars = validChars.substring(typIdx + 1);
        }
        if (!processorType.equalsIgnoreCase("regex")) {
            errors.add(
                    "field " + fd.getName()
                    + " has an invalid valid chars processor type: a simple list of characters or a regex: is supported");
            return errors;
        }
        try {
            Pattern pattern = Pattern.compile(validChars);
        } catch (PatternSyntaxException ex) {
            errors.add("field " + fd.getName()
                    + " has in invalid character pattern for a regular expression: "
                    + validChars);
        }
        return errors;
    }

    private List<String> validateLookup(AttributeDefinition fd, LookupConstraint lc) {
        List<String> errors = new ArrayList();
        if (lc.getParams() == null) {
            errors.add("field " + fd.getName() + " has a lookup with null parameters");
        }
        //TODO: more validation
        return errors;
    }
    public static final String GREATER_THAN_EQUAL = "greater_than_equal";
    public static final String LESS_THAN_EQUAL = "less_than_equal";
    public static final String GREATER_THAN = "greater_than";
    public static final String LESS_THAN = "less_than";
    public static final String EQUALS = "equals";
    public static final String NOT_EQUAL = "not_equal";
    private static final String[] VALID_OPERATORS = {
        NOT_EQUAL, EQUALS, GREATER_THAN_EQUAL, LESS_THAN_EQUAL, GREATER_THAN, LESS_THAN
    };

    private List<String> validateCase(AttributeDefinition fd, CaseConstraint cc) {
        List<String> errors = new ArrayList();
        if (cc.getOperator() == null) {
            errors.add("field " + fd.getName()
                    + " has a case constraint with no operator");
        } else {
            boolean found = false;
            for (int i = 0; i < VALID_OPERATORS.length; i++) {
                if (VALID_OPERATORS[i].equalsIgnoreCase(cc.getOperator())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                errors.add("field " + fd.getName()
                        + " has a case constraint with an unknown operator "
                        + cc.getOperator());
            }
        }
        if (cc.getFieldPath() == null) {
            errors.add(
                    "field " + fd.getName()
                    + " has a case constraint with a null for the field to use for the comparison");
        } else if (cc.getFieldPath().trim().equals("")) {
            errors.add(
                    "field " + fd.getName()
                    + " has a case constraint with blanks for the field to use for the comparison");
        }
        if (cc.getWhenConstraint() == null) {
            errors.add("field " + fd.getName()
                    + " has a case constraint but null when statements");
            return errors;
        }
        if (cc.getWhenConstraint().size() == 0) {
            errors.add("field " + fd.getName()
                    + " has a case constraint but has no when statements");
        }
        for (WhenConstraint wc : cc.getWhenConstraint()) {
            if (wc.getConstraint() == null) {
                errors.add(
                        "field " + fd.getName()
                        + " has a as case constraint with a when statement that has no overriding constraints specified");
            }
        }
        //TODO: more validation
        return errors;
    }
}
