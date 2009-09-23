package org.kuali.student.common.util;
import java.util.Map;

public class SystemPropertyConfigurer {

	public void setProperties(Map<String, String> properties) {
		for (Map.Entry<String, String> e : properties.entrySet()) {
			System.getProperties().put(e.getKey(), e.getValue());
		}
	}
}
