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

package org.kuali.student.brms.translators.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class Constants {
	//public final static String DEF_CRITERIA_KEY = "kuali.student.criteria.key";

	private final static int size = Constants.class.getFields().length;
	private final static Map<String,String> map = new HashMap<String,String>(size);;
	
	static {
		try {
			Field[] fields = Constants.class.getFields();
			for(Field field: fields) {
				map.put(field.getName(), field.get(null).toString());
			}
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
	
	private Constants() {}
	
	public static String[] getConstantValues() {
		return (String[]) map.values().toArray(new String[]{});
	}

	public static String[] getConstants() {
		return (String[]) map.keySet().toArray(new String[]{});
	}
	
	public static boolean containsConstant(String key) {
		return map.containsKey(key);
	}

	public static boolean containsConstantValue(String value) {
		return map.containsValue(value);
	}

	public int size() {
		return size;
	}
}
