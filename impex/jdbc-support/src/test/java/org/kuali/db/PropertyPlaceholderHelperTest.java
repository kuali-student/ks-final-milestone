package org.kuali.db;

import static org.junit.Assert.*;

import java.util.Properties;

import org.junit.Test;
import org.springframework.util.PropertyPlaceholderHelper;

public class PropertyPlaceholderHelperTest {

	@Test
	public void replacePlaceholders() {
		Properties properties = new Properties();
		properties.put("schema", "KSEMBEDDED");
		String value = "DROP USER ${schema} CASCADE;";
		PropertyPlaceholderHelper helper = new PropertyPlaceholderHelper("${", "}");
		value = helper.replacePlaceholders(value, properties);
		assertEquals(value, "DROP USER KSEMBEDDED CASCADE;");
	}
}
