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
