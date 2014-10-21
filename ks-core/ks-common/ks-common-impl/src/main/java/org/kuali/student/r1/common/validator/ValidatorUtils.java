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

package org.kuali.student.r1.common.validator;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.kuali.student.r1.common.dictionary.dto.DataType;
import org.kuali.student.r1.common.dictionary.dto.FieldDefinition;
import org.kuali.student.r1.common.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.infc.ValidationResult.ErrorLevel;
import org.kuali.student.r2.common.validator.ObjectStructureHierarchy;

public class ValidatorUtils {

	public static boolean compareValues(Object value1, Object value2,
			DataType dataType, String operator, boolean isCaseSensitive, DateParser dateParser) {

		boolean result = false;
		Integer compareResult = null;
		if("has_value".equalsIgnoreCase(operator)){
			if(value1==null){
				return "false".equals(value2.toString().toLowerCase());
			}
			if(value1 instanceof Collection && ((Collection<?>) value1).isEmpty()){
				return "false".equals(value2.toString().toLowerCase());
			}
			return "true".equals(value2.toString().toLowerCase());
		}
		// Convert objects into appropriate data types
		if (null != dataType) {
			if (DataType.STRING.equals(dataType)) {
			    String v1 = getString(value1);
				String v2 = getString(value2);

				if(!isCaseSensitive) {
				    v1 = v1.toUpperCase();
				    v2 = v2.toUpperCase();
				}
				
				compareResult = v1.compareTo(v2);
			} else if (DataType.INTEGER.equals(dataType)) {
				Integer v1 = getInteger(value1);
				Integer v2 = getInteger(value2);
				compareResult = v1.compareTo(v2);
			} else if (DataType.LONG.equals(dataType)) {
				Long v1 = getLong(value1);
				Long v2 = getLong(value2);
				compareResult = v1.compareTo(v2);
			} else if (DataType.DOUBLE.equals(dataType)) {
				Double v1 = getDouble(value1);
				Double v2 = getDouble(value2);
				compareResult = v1.compareTo(v2);
			} else if (DataType.FLOAT.equals(dataType)) {
				Float v1 = getFloat(value1);
				Float v2 = getFloat(value2);
				compareResult = v1.compareTo(v2);
			} else if (DataType.BOOLEAN.equals(dataType)) {
				Boolean v1 = getBoolean(value1);
				Boolean v2 = getBoolean(value2);
				compareResult = v1.compareTo(v2);
			} else if (DataType.DATE.equals(dataType)) {
				Date v1 = getDate(value1, dateParser);
				Date v2 = getDate(value2, dateParser);
				compareResult = v1.compareTo(v2);
			}
		}

		if (null != compareResult) {
			if (("equals".equalsIgnoreCase(operator)
					|| "greater_than_equal".equalsIgnoreCase(operator) || "less_than_equal"
					.equalsIgnoreCase(operator))
					&& 0 == compareResult) {
				result = true;
			}

			if (("not_equal".equalsIgnoreCase (operator)
     || "greater_than".equalsIgnoreCase(operator) || "greater_than_equal".equalsIgnoreCase(operator)) && compareResult >= 1) {
				result = true;
			}

			if (("not_equal".equalsIgnoreCase (operator)
     || "less_than".equalsIgnoreCase(operator)|| "less_than_equal".equalsIgnoreCase(operator)) && compareResult <= -1) {
				result = true;
			}
		}

		return result;
	}

	public static Integer getInteger(Object o) {
		Integer result = null;
		if (o instanceof Integer)
			return (Integer) o;
		if (o == null)
			return null;
		if (o instanceof Number)
			return ((Number) o).intValue();
		String s = o.toString();
		if (s != null && s.trim().length() > 0) {
			result = Integer.valueOf(s.trim());
		}
		return result;
	}

	public static Long getLong(Object o) {
		Long result = null;
		if (o instanceof Long)
			return (Long) o;
		if (o == null)
			return null;
		if (o instanceof Number)
			return ((Number) o).longValue();
		String s = o.toString();
		if (s != null && s.trim().length() > 0) {
			result = Long.valueOf(s.trim());
		}
		return result;
	}

	public static Float getFloat(Object o) {
		Float result = null;
		if (o instanceof Float)
			return (Float) o;
		if (o == null)
			return null;
		if (o instanceof Number)
			return ((Number) o).floatValue();
		String s = o.toString();
		if (s != null && s.trim().length() > 0) {
			result = Float.valueOf(s.trim());
		}
		return result;
	}

	public static Double getDouble(Object o) {
		Double result = null;
		if (o instanceof Double)
			return (Double) o;
		if (o == null)
			return null;
		if (o instanceof Number)
			return ((Number) o).doubleValue();
		String s = o.toString();
		if (s != null && s.trim().length() > 0) {
			result = Double.valueOf(s.trim());
		}
		return result;
	}

	public static Date getDate(Object o, DateParser dateParser) {
		Date result = null;
		if (o instanceof Date)
			return (Date) o;
		if (o == null)
			return null;
		String s = o.toString();
		if (s != null && s.trim().length() > 0) {
			result = dateParser.parseDate(s.trim());
		}
		return result;
	}

	public static String getString(Object o) {
		if (o instanceof String)
			return (String) o;
		if (o == null)
			return null;
		return o.toString();
	}

	public static Boolean getBoolean(Object o) {
		Boolean result = null;
		if (o instanceof Boolean)
			return (Boolean) o;
		if (o == null)
			return null;
		String s = o.toString();
		if (s != null && s.trim().length() > 0) {
			result = Boolean.parseBoolean(s.trim());
		}
		return result;
	}	
	
	/**
	 * Traverses the dictionary ObjectStructure to find the field with the match
	 * key, type and state
	 * The key has to relative to the current object structure that is being traversed.
	 * example: current object structure is ActivityInfo and if we want to lookup 
	 * the academicSubjectorgId, then <property name="fieldPath" value="academicSubjectOrgs.orgId"/>
	 * The current object structure starts from the field on which the constraint is applied on.
	 * If we want to address fields outside of this object structure we ll need to pass in the
	 * dictionary context.
	 * @param key
	 * @param objStructure
	 * @return
	 */
	public static FieldDefinition getField(String key, ObjectStructureDefinition objStructure) {
		String[] lookupPathTokens = getPathTokens(key);
		for(int i = 0; i < lookupPathTokens.length; i++) {
			for (FieldDefinition f : objStructure.getAttributes()) {
				if (f.getName().equals(lookupPathTokens[i])) {
					if(i==lookupPathTokens.length-1){
						return f;
					}
					else{
						objStructure = f.getDataObjectStructure();
						break;
					}
					
				}
			}
		 }
		return null;
	}

    /**
     * Traverses the dictionary ObjectStructure to find the field with the match
     * key, type and state
     * The key has to relative to the current object structure that is being traversed.
     * example: current object structure is ActivityInfo and if we want to lookup
     * the academicSubjectorgId, then <property name="fieldPath" value="academicSubjectOrgs.orgId"/>
     * The current object structure starts from the field on which the constraint is applied on.
     * If we want to address fields outside of this object structure we ll need to pass in the
     * dictionary context.
     * @param key
     * @param objStructureHierarchy
     * @return
     */
    public static FieldDefinition getField(String key, ObjectStructureHierarchy objStructureHierarchy) {
        String[] lookupPathTokens = getPathTokens(key);

        ObjectStructureDefinition objStructure = objStructureHierarchy.getObjectStructure();
        for(int i = 0; i < lookupPathTokens.length; i++) {
            if ("parent".equals(lookupPathTokens[i])) {
                objStructure = objStructureHierarchy.getParentObjectStructure();
                objStructureHierarchy = objStructureHierarchy.getParentObjectStructureHierarchy();
                continue;
            }

            for (FieldDefinition f : objStructure.getAttributes()) {
                if (f.getName().equals(lookupPathTokens[i])) {
                    if(i==lookupPathTokens.length-1){
                        return f;
                    }
                    else{
                        objStructure = f.getDataObjectStructure();
                        break;
                    }

                }
            }
        }
        return null;
    }

    /**
     * Traverses the parent data for the field value.
     * @param key
     * @param dataProvider
     * @return
     */
    public static Object getFieldValue(String key, ConstraintDataProvider dataProvider) {
        String[] lookupPathTokens = getPathTokens(key);

        Object returnObject = null;
        ConstraintDataProvider currentDataProvider = dataProvider;
        for(int i = 0; i < lookupPathTokens.length; i++) {
            returnObject = currentDataProvider.getValue(lookupPathTokens[i]);

            // Returns a null if any parent object is null.
            if (returnObject == null){
                return null;
            }

            // Set the current dataprovider to the parent data provider
            if (returnObject instanceof ConstraintDataProvider){
                currentDataProvider = (ConstraintDataProvider) returnObject;
            }

            // TODO: Create new dataProviders for the sub objects.
        }
        return returnObject;
    }
	
    private static String[] getPathTokens(String fieldPath) {
        return (fieldPath != null && fieldPath.contains(".") ? fieldPath.split("\\.") : new String[]{fieldPath});
    }

	/**
	 * Inspect the validation result to determine if there are any errors.
	 * 
	 * @param validationResults
	 * @return true if at least one validation result is an error. 
	 */
	public static boolean hasErrors(List<ValidationResultInfo> validationResults){
		if (validationResults !=null){
			for (ValidationResultInfo vr:validationResults){
				if (vr.getErrorLevel() == ErrorLevel.ERROR){
					return true;
				}
			}
		}
		
		return false;
	}

	/**
	 * Inspect the validation result to determine if there are any errors.
	 * 
	 * @param validationResults
	 * @return true if at least one validation result is an error. 
	 */
	public static boolean hasWarnings(List<ValidationResultInfo> validationResults){
		if (validationResults !=null){
			for (ValidationResultInfo vr:validationResults){
				if (vr.getErrorLevel() == ErrorLevel.WARN){
					return true;
				}
			}
		}
		
		return false;
	}

}

