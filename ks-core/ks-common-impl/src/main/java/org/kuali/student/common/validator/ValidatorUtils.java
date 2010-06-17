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

package org.kuali.student.common.validator;

import java.util.Date;
import java.util.List;

import org.kuali.student.core.dictionary.dto.Field;
import org.kuali.student.core.dictionary.dto.ObjectStructure;
import org.kuali.student.core.dictionary.dto.State;
import org.kuali.student.core.dictionary.dto.Type;

public class ValidatorUtils {

	public static boolean compareValues(Object value1, Object value2,
			String dataType, String operator, DateParser dateParser) {

		boolean result = false;
		Integer compareResult = null;

		// Convert objects into appropriate data types
		if (null != dataType) {
			if ("string".equalsIgnoreCase(dataType)) {
				String v1 = getString(value1);
				String v2 = getString(value2);
				compareResult = v1.compareTo(v2);
			} else if ("integer".equalsIgnoreCase(dataType)) {
				Integer v1 = getInteger(value1);
				Integer v2 = getInteger(value2);
				compareResult = v1.compareTo(v2);
			} else if ("long".equalsIgnoreCase(dataType)) {
				Long v1 = getLong(value1);
				Long v2 = getLong(value2);
				compareResult = v1.compareTo(v2);
			} else if ("double".equalsIgnoreCase(dataType)) {
				Double v1 = getDouble(value1);
				Double v2 = getDouble(value2);
				compareResult = v1.compareTo(v2);
			} else if ("float".equalsIgnoreCase(dataType)) {
				Float v1 = getFloat(value1);
				Float v2 = getFloat(value2);
				compareResult = v1.compareTo(v2);
			} else if ("boolean".equalsIgnoreCase(dataType)) {
				Boolean v1 = getBoolean(value1);
				Boolean v2 = getBoolean(value2);
				compareResult = v1.compareTo(v2);
			} else if ("date".equalsIgnoreCase(dataType)) {
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

			if ("greater_than".equalsIgnoreCase(operator) && 1 == compareResult) {
				result = true;
			}

			if ("less_than".equalsIgnoreCase(operator) && -1 == compareResult) {
				result = true;
			}
		}

		return result;
	}

	protected static Integer getInteger(Object o) {
		if (o == null)
			return null;

		Integer result = null;
		if (o instanceof Integer)
			return (Integer) o;
		if (o instanceof Number)
			return ((Number) o).intValue();
		String s = o.toString();
		if (s != null && s.trim().length() > 0) {
			result = Integer.valueOf(s.trim());
		}
		return result;
	}

	protected static Long getLong(Object o) {
		if (o == null)
			return null;

		Long result = null;
		if (o instanceof Long)
			return (Long) o;
		if (o instanceof Number)
			return ((Number) o).longValue();
		String s = o.toString();
		if (s != null && s.trim().length() > 0) {
			result = Long.valueOf(s.trim());
		}
		return result;
	}

	protected static Float getFloat(Object o) {
		if (o == null)
			return null;

		Float result = null;
		if (o instanceof Float)
			return (Float) o;
		if (o instanceof Number)
			return ((Number) o).floatValue();
		String s = o.toString();
		if (s != null && s.trim().length() > 0) {
			result = Float.valueOf(s.trim());
		}
		return result;
	}

	protected static Double getDouble(Object o) {
		if (o == null)
			return null;

		Double result = null;
		if (o instanceof Double)
			return (Double) o;
		if (o instanceof Number)
			return ((Number) o).doubleValue();
		String s = o.toString();
		if (s != null && s.trim().length() > 0) {
			result = Double.valueOf(s.trim());
		}
		return result;
	}

	protected static Date getDate(Object o, DateParser dateParser) {
		if (o == null)
			return null;

		Date result = null;
		if (o instanceof Date)
			return (Date) o;
		String s = o.toString();
		if (s != null && s.trim().length() > 0) {
			result = dateParser.parseDate(s.trim());
		}
		return result;
	}

	protected static String getString(Object o) {
		if (o == null)
			return null;

		if (o instanceof String)
			return (String) o;
		return o.toString();
	}

	private static Boolean getBoolean(Object o) {
		if (o == null)
			return null;

		Boolean result = null;
		if (o instanceof Boolean)
			return (Boolean) o;
		String s = o.toString();
		if (s != null && s.trim().length() > 0) {
			result = Boolean.parseBoolean(s.trim());
		}
		return result;
	}

	/**
	 * Traverses the dictionary ObjectStructure to find the field with the match
	 * key, type and state
	 *
	 * @param key
	 * @param type
	 * @param state
	 * @param objStructure
	 * @return
	 */
	protected static Field getField(String key, ObjectStructure objStructure,
			String type, String state) {
		List<Type> typeList = objStructure.getType();

		for (Type t : typeList) {
			if (t.getKey().equalsIgnoreCase(type)) {
				for (State s : t.getState()) {
					if (s.getKey().equalsIgnoreCase(state)) {
						for (Field f : s.getField()) {
							if (f.getKey().equals(key)) {
								return f;
							}
						}
					}
				}
			}
		}

		return null;
	}

}

