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
package org.kuali.student.enrollment.lpr.mock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import org.kuali.rice.kns.datadictionary.AttributeDefinition;
import org.kuali.rice.kns.datadictionary.DataObjectEntry;
import org.kuali.rice.kns.datadictionary.validation.DictionaryObjectAttributeValueReader;
import org.kuali.student.common.infc.Comparison;
import org.kuali.student.common.infc.Criteria;
import org.kuali.student.datadictionary.Student2RiceDictionaryEntryConverter;
import org.kuali.student.datadictionary.infc.DictionaryEntry;
import org.kuali.student.datadictionary.util.CriteriaValidatorParser;
import org.kuali.student.datadictionary.util.CriteriaValidatorParser.Operator;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;

/**
 * A helper class for the Mock implementation to match criteria to values on the object
 * @author nwright
 */
public class CriteriaMatcherInMemory<T> {

    public CriteriaMatcherInMemory() {
        super();
    }
    private transient DataObjectEntry riceDictionaryEntry;
    private DictionaryEntry dictionaryEntry;
        private Criteria criteria;
    private transient List<Object> parsedValues;
    private transient List<CriteriaValidatorParser.Operator> parsedOperators;

    public void setDictionaryEntry(DictionaryEntry dictionaryEntry) {
        this.dictionaryEntry = dictionaryEntry;
        if (dictionaryEntry == null) {
            this.riceDictionaryEntry = null;
            return;
        }
        this.riceDictionaryEntry = new Student2RiceDictionaryEntryConverter().convert(dictionaryEntry);
    }

    public DictionaryEntry getDictionaryEntry() {
        return dictionaryEntry;
    }

    public Criteria getCriteria() {
        return criteria;
    }

    public void setCriteria(Criteria criteria) {
        this.criteria = criteria;
    }

    public List<Operator> getParsedOperators() {
        return parsedOperators;
    }

    public void setParsedOperators(List<Operator> parsedOperators) {
        this.parsedOperators = parsedOperators;
    }

    public List<Object> getParsedValues() {
        return parsedValues;
    }

    public void setParsedValues(List<Object> parsedValues) {
        this.parsedValues = parsedValues;
    }

    

    private boolean calcIsList(AttributeDefinition ad) {
        // TODO: Deal with lists
//        if (ad.getMaximumNumberOfElements() == null) {
//            return false;
//        }
//        if (ad.getMaximumNumberOfElements() <= 1) {
//            return false;
//        }
        return true;

    }

    private AttributeDefinition getAttributeDefinition(String fk)
            throws InvalidParameterException,
            OperationFailedException {
        return this.riceDictionaryEntry.getAttributeDefinition(fk);
    }

    /**
     * finds all of the supplied objects that match the specified criteria
     * 
     * @param all
     * @return filtered list
     */
    public List<T> findMatching(Collection<T> all) {
        List<T> selected = new ArrayList<T>();
        for (T obj : all) {
            if (matches(obj)) {
                selected.add(obj);
                if (this.criteria.getMaxResults() != null) {
                    if (selected.size() >= this.criteria.getMaxResults().intValue()) {
                        break;
                    }
                }
            }
        }
        return selected;
    }

    /**
     * Checks if an object matches the criteria
     * @param infoObject
     * @return
     */
    public boolean matches(Object infoObject) {
        // logically and them together
        int i = 0;
        for (Comparison comparison : this.getCriteria().getComparisons()) {
            if (!matches(i, infoObject, comparison)) {
                return false;
            }
            i++;
        }
        return true;
    }

    private boolean matches(int i, Object infoObject, Comparison comparison) {
        AttributeDefinition ad = null;
        try {
            ad = this.getAttributeDefinition(comparison.getFieldKey());
        } catch (InvalidParameterException ex) {
            throw new IllegalArgumentException(ex);
        } catch (OperationFailedException ex) {
            throw new IllegalArgumentException(ex);
        }
        Object parsedValue = this.getParsedValues().get(i);
        CriteriaValidatorParser.Operator op = this.getParsedOperators().get(i);
        Object dataValue = this.extractValue(i, comparison.getFieldKey(), infoObject);
        if (comparison.isIgnoreCase()) {
            if (dataValue instanceof String) {
                dataValue = ((String) dataValue).toLowerCase();
            }
        }
        if (matches(op, parsedValue, dataValue)) {
            return true;
        }
        return false;
    }

    private Object extractValue(int i, String fk, Object infoObject) {
        // TODO: make sure the key is the same as the name
        String dictionaryEntryKey = this.riceDictionaryEntry.getName();
        DictionaryObjectAttributeValueReader reader =
                new DictionaryObjectAttributeValueReader(infoObject, dictionaryEntryKey, this.riceDictionaryEntry);
        Object value = reader.getValue(fk);
        return value;
    }

    private boolean matches(CriteriaValidatorParser.Operator op, Object dataValue, Object criteriaValue) {
        switch (op) {
            case EQ:
                return matchesEquals(dataValue, criteriaValue);
            case LT:
                if (matchesEquals(dataValue, criteriaValue)) {
                    return false;
                }
                return matchesLessThan(dataValue, criteriaValue);
            case GT:
                if (matchesEquals(dataValue, criteriaValue)) {
                    return false;
                }
                return !matchesLessThan(dataValue, criteriaValue);

            case LTE:
                if (matchesEquals(dataValue, criteriaValue)) {
                    return true;
                }
                return matchesLessThan(dataValue, criteriaValue);

            case GTE:
                if (matchesEquals(dataValue, criteriaValue)) {
                    return true;
                }
                return !matchesLessThan(dataValue, criteriaValue);
            case NEQ:
                return !matchesEquals(dataValue, criteriaValue);
            case BETWEEN: {
                List<Object> values = (List<Object>) criteriaValue;
                Object fromValue = values.get(0);
                Object thruValue = values.get(1);
                if (matchesEquals(dataValue, fromValue)) {
                    return true;
                }
                if (matchesEquals(dataValue, thruValue)) {
                    return true;
                }
                if (matchesLessThan(dataValue, thruValue)) {
                    if (!matchesLessThan(dataValue, fromValue)) {
                        return true;
                    }
                }
                return false;
            }
            case IN: {
                List<Object> values = (List<Object>) criteriaValue;
                for (Object value : values) {
                    if (matchesEquals(dataValue, value)) {
                        return true;
                    }
                }
                return false;
            }
            case LIKE:
                return matchesLike((String) dataValue, (String) criteriaValue);
            default:
                throw new IllegalArgumentException("unknown/unhandled operation");
        }
    }

    public static boolean matchesEquals(Object dataValue, Object criteriaValue) {
        if (dataValue == criteriaValue) {
            return true;
        }
        if (dataValue == null && criteriaValue == null) {
            return true;
        }
        if (dataValue == null) {
            return false;
        }
        return dataValue.equals(criteriaValue);
    }

    public static boolean matchesLessThan(Object dataValue, Object criteriaValue) {
        if (dataValue == criteriaValue) {
            return false;
        }
        if (dataValue == null && criteriaValue == null) {
            return false;
        }
        if (dataValue == null) {
            return false;
        }
        if (criteriaValue instanceof Comparable) {
            Comparable comp1 = (Comparable) dataValue;
            Comparable comp2 = (Comparable) criteriaValue;
            if (comp1.compareTo(comp2) < 0) {
                return true;
            }
            return false;
        }
        throw new IllegalArgumentException("The values are not comparable " + criteriaValue);
    }
    // cache
    private transient Map<String, Pattern> patternCache = new HashMap<String, Pattern>();

    private Pattern getPattern(String expr) {
        Pattern p = patternCache.get(expr);
        if (p == null) {
            p = compilePattern(expr);
            patternCache.put(expr, p);
        }
        return p;
    }

    /**
     * this was taken from
     * http://stackoverflow.com/questions/898405/how-to-implement-a-sql-like-like-operator-in-java
     */
    public boolean matchesLikeCachingPattern(final String str, final String expr) {
        return matchesLike(str, getPattern(expr));
    }

    private static Pattern compilePattern(final String expr) {
        String regex = quotemeta(expr);
        regex = regex.replace("_", ".").replace("%", ".*?");
        Pattern p = Pattern.compile(regex, Pattern.DOTALL);
        return p;
    }

    /**
     * This was taken from
     * 
     * http://stackoverflow.com/questions/898405/how-to-implement-a-sql-like-like-operator-in-java
     */
    public static boolean matchesLike(final String str, final String expr) {
        Pattern p = compilePattern(expr);
        return matchesLike(str, p);
    }

    private static boolean matchesLike(final String str, final Pattern p) {
        return p.matcher(str).matches();
    }

    private static String quotemeta(String s) {
        if (s == null) {
            throw new IllegalArgumentException("String cannot be null");
        }

        int len = s.length();
        if (len == 0) {
            return "";
        }

        StringBuilder sb = new StringBuilder(len * 2);
        for (int i = 0; i < len; i++) {
            char c = s.charAt(i);
            if ("[](){}.*+?$^|#\\".indexOf(c) != -1) {
                sb.append("\\");
            }
            sb.append(c);
        }
        return sb.toString();
    }
}

