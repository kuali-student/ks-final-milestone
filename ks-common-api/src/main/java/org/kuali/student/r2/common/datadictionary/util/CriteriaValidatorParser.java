 /*
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may	obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.r2.common.datadictionary.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.kuali.rice.core.api.uif.DataType;

import org.kuali.student.r2.common.datadictionary.infc.AttributeDefinitionInfc;
import org.kuali.student.r2.common.datadictionary.infc.DictionaryEntry;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.infc.Comparison;
import org.kuali.student.r2.common.infc.Criteria;

/**
 * A class that validates the criteria against the dictionary
 *
 * It also sets the parsedValues and parsedOperators so they may be used in the implementation
 * The call sequence is sometehing like this:
 * <ol>
 * <li>construct this validator
 * <li>Configure this validator with criteria to be valiated
 * <li>Get the dictionary entry of the object to be validated by calling DataDictionaryServiceInfc.getDataDictionaryEntry (the ref object's URI)
 * <li>Configure this validator with the dictionary entry that you just got from the dictionary
 * <li>Call the validate () method which will throw exceptions if problems
 * <li>get the parsed values (@see getParsedValues ()) and use them in the implementation
 * <li>Get the parsed operators and use in the implementation, if you want
 * </ol>
 * @author nwright
 */
public class CriteriaValidatorParser {

    public enum Operator {

        EQ, IN, GT, LT, NEQ, GTE, LTE, LIKE, BETWEEN;
    }
    private Criteria criteria;
    private DictionaryEntry dictionaryEntry;
    private transient List<Object> parsedValues;
    private transient List<Operator> parsedOperators;

    public CriteriaValidatorParser() {
    }

    public Criteria getCriteria() {
        return criteria;
    }

    public void setCriteria(Criteria criteria) {
        this.criteria = criteria;
    }

    public DictionaryEntry getDictionaryEntry() {
        return dictionaryEntry;
    }

    public void setDictionaryEntry(DictionaryEntry dictionaryEntry) {
        this.dictionaryEntry = dictionaryEntry;
    }

    /**
     * Get the string operators translated as an Operator enum
     * @return list of operators in the same ordinal position as the supplied operators in the list of ComparisonInfc objects
     */
    public List<Operator> getParsedOperators() {
        return parsedOperators;
    }

    /**
     * The values parsed as their respective data types, String, Date, Integer, etc.
     *
     * Special considerations:
     * IN operators return a List of one or more parsed values who's order is the samne as the order in the ComparisonInfc
     * BETWEEN operators return a list of two parsed values who's values coorespond to the from to values of a between operation.
     *
     * @return a list of parsed values in the same ordinal positions as the unparsed values in the list of ComparisonInfc objects
     */
    public List<Object> getParsedValues() {
        return parsedValues;
    }



    /**
     * Validate the criteria thowing an InvalidParameterException if there are problems
     *
     * TODO: Consider getting all the errors and throw them as a group instead of as we find them
     *
     * @throws InvalidParameterException
     * @throws OperationFailedException
     */
    public void validate()
            throws InvalidParameterException,
            OperationFailedException {
        parsedValues = new ArrayList<Object>();
        parsedOperators = new ArrayList <Operator> ();
        if (this.criteria.getComparisons() == null) {
            throw new InvalidParameterException ("Comparisons list is null -- to select all specify an empty list");
        }
        int i = 0;
        for (Comparison comparison : this.criteria.getComparisons()) {
            this.validate(i, comparison);
            i++;
        }
    }

    private void validate(int i, Comparison comparison)
            throws InvalidParameterException,
            OperationFailedException {
        String fieldKey = comparison.getFieldKey();
        String operator = comparison.getOperator();
        List<String> values = comparison.getValues();
        AttributeDefinitionInfc ad = this.getAttributeDefinition(fieldKey);
        if (ad == null) {
            throw new InvalidParameterException("The " + i + "th comparison's field key " + fieldKey + " is not defined in the dictionary");
        }
        if (operator == null) {
            throw new InvalidParameterException("The " + i + "th comparison's operator is null");
        }
        if (operator.equals("=")) {
            this.parsedOperators.add(Operator.EQ);
        } else if (operator.equals("<")) {
            this.parsedOperators.add(Operator.LT);
        } else if (operator.equals(">")) {
            this.parsedOperators.add(Operator.GT);
        } else if (operator.equals("!=")) {
            this.parsedOperators.add(Operator.NEQ);
        } else if (operator.equals("<=")) {
            this.parsedOperators.add(Operator.LTE);
        } else if (operator.equals(">=")) {
            this.parsedOperators.add(Operator.GTE);
        } else if (operator.equals("in")) {
            this.parsedOperators.add(Operator.IN);
        } else if (operator.equals("between")) {
            this.parsedOperators.add(Operator.BETWEEN);
        } else if (operator.equals("like")) {
            this.parsedOperators.add(Operator.LIKE);
            if (ad.getDataType().equals (DataType.STRING)) {
            throw new InvalidParameterException("The " + i + "th comparison's operator is LIKE which can only be applied to strings, " + ad.getDataType() + " is invalid.");
            }
        } else {
            throw new InvalidParameterException("The " + i + "th comparison's operator, " + operator + ", is invalid.");
        }
        if (values == null) {
            throw new InvalidParameterException("The " + i + "th comparison's values list is required and cannot be null");
        }
        if (values.isEmpty()) {
            throw new InvalidParameterException("The " + i + "th comparison's values list is required and cannot be an empty list");
        }
        if (values.get(0) == null) {
            if (!operator.equals("=") && !operator.equals("!=")) {
                throw new InvalidParameterException("The " + i + "th comparison's value is null but the operator " + operator + " is a comparison operator that does not apply");
            }
            return;
        }
        if (operator.equals("between")) {
            if (values.size() != 2) {
                throw new InvalidParameterException("The " + i + "th comparison is a between operator which requires two values, found " + values.size());
            }
            if (values.get(0) == null) {
                throw new InvalidParameterException("The " + i + "th comparison is a between operator but the first value is null");
            }
            if (values.get(1) == null) {
                throw new InvalidParameterException("The " + i + "th comparison is a between operator but the second value is null");
            }
        } else if (values.size() > 1) {
            if (!operator.equals("in")) {
                throw new InvalidParameterException("The " + i + "th comparison's value is a list but the operator " + operator + " is a comparison operator that does not apply");
            }
        }
        switch (ad.getDataType()) {
            case STRING:
                break;
            case DATE:
            case TRUNCATED_DATE:
                break;
            case BOOLEAN:
                if (! operator.equals("=") && !operator.equals("!=")) {
                    throw new InvalidParameterException("The " + i + "th comparison's operator " + operator + " is a comparison operator that does not apply to the field's boolean data type");
                }
            case INTEGER:
            case FLOAT:
            case DOUBLE:
            case LONG:
                break;
//            case COMPLEX:
//                if (! operator.equals("=") && !operator.equals("!=")) {
//                    throw new InvalidParameterException("The " + i + "th comparison's operator " + operator + " is a comparison operator that does not apply to the field's complex data type");
//                }
//                if (values.get(0) == null) {
//                    throw new InvalidParameterException("The " + i + "th comparison's value is not null but attribute type is complex. Complex can only be checked to see if it is null or not null");
//                }
        }
        parsedValues.add(parseValues(i, ad.getDataType(), comparison.getValues(), comparison.getIsIgnoreCase()));
    }

    private Object parseValues(int i, DataType dataType, List<String> values, boolean ignoreCase)
            throws InvalidParameterException {
        if (values.size() == 1) {
            return parseValue(i, dataType, values.get(0), ignoreCase);
        }
        List<Object> list = new ArrayList<Object>();
        for (String value : values) {
            list.add(parseValue(i, dataType, value, ignoreCase));
        }
        return list;
    }

    private Object parseValue(int i, DataType dataType, String value, boolean ignoreCase)
            throws InvalidParameterException {
        if (value == null) {
            return null;
        }
        switch (dataType) {
            case STRING:
                return parseString(i, value, ignoreCase);
            case DATE:
                return parseDateTime(i, value);
            case TRUNCATED_DATE:
                return parseDate(i, value);
            case BOOLEAN:
                return parseBoolean(i, value);
            case INTEGER:
                return parseInteger(i, value);
            case FLOAT:
                return parseFloat(i, value);
            case DOUBLE:
                return parseDouble(i, value);
            case LONG:
                return parseLong(i, value);
//            case COMPLEX:
//                throw new InvalidParameterException("The " + i + "th comparison's value is not null but attribute type is complex. Complex can only be checked to see if it is null or not null");
            default:
                throw new IllegalArgumentException("Unknown/unhandled datatype " + dataType);
        }
    }

    private String parseString(int i, String cv, boolean ignoreCase) throws InvalidParameterException {
        if (cv == null) {
            return null;
        }
        if (ignoreCase) {
            return cv.toLowerCase();
        }
        return cv;
    }

    private Timestamp parseDateTime(int i, String cv) throws InvalidParameterException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd-hh.mm.ss");
        try {
            return new Timestamp(df.parse(cv).getTime());
        } catch (ParseException ex) {
            throw new InvalidParameterException("The " + i + "th comparison's value " + cv + " cannot be parsed as a dateTime");
        }
    }

    private Date parseDate(int i, String cv) throws InvalidParameterException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return df.parse(cv);
        } catch (ParseException ex) {
            throw new InvalidParameterException("The " + i + "th comparison's value " + cv + " cannot be parsed as a date");
        }
    }

    private Integer parseInteger(int i, String cv) throws InvalidParameterException {
        try {
            return Integer.parseInt(cv);
        } catch (NumberFormatException ex) {
            throw new InvalidParameterException("The " + i + "th comparison's value " + cv + " cannot be parsed as an integer");
        }
    }

    private Long parseLong(int i, String cv) throws InvalidParameterException {
        try {
            return Long.parseLong(cv);
        } catch (NumberFormatException ex) {
            throw new InvalidParameterException("The " + i + "th comparison's value " + cv + " cannot be parsed as a Long");
        }
    }

    private Boolean parseBoolean(int i, String cv) throws InvalidParameterException {
        if (cv.equalsIgnoreCase("true")) {
            return Boolean.TRUE;
        }
        if (cv.equalsIgnoreCase("false")) {
            return Boolean.FALSE;
        }
        throw new InvalidParameterException("The " + i + "th comparison's value " + cv + " cannot be parsed as a Boolean");

    }

    private Float parseFloat(int i, String cv) throws InvalidParameterException {
        try {
            return Float.parseFloat(cv);
        } catch (NumberFormatException ex) {
            throw new InvalidParameterException("The " + i + "th comparison's value " + cv + " cannot be parsed as an float");
        }
    }

    private Double parseDouble(int i, String cv) throws InvalidParameterException {
        try {
            return Double.parseDouble(cv);
        } catch (NumberFormatException ex) {
            throw new InvalidParameterException("The " + i + "th comparison's value " + cv + " cannot be parsed as an double");
        }
    }

    private String initLower(String str) {
        if (str == null) {
            return null;
        }
        if (str.length() == 0) {
            return str;
        }
        if (str.length() == 1) {
            return str.toLowerCase();
        }
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }

    private boolean calcIsList(AttributeDefinitionInfc ad) {
        if (ad.getMaxOccurs() == null) {
            return false;
        }
        if (ad.getMaxOccurs() <= 1) {
            return false;
        }
        return true;

    }

    private AttributeDefinitionInfc getAttributeDefinition(String fk)
            throws InvalidParameterException,
            OperationFailedException {
        for (AttributeDefinitionInfc ad : this.dictionaryEntry.getAttributes()) {
            if (ad.getName().equals(fk)) {
                return ad;
            }
        }
       return null;
    }

}

