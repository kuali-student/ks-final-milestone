package org.kuali.db;

import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import org.springframework.util.PropertyPlaceholderHelper;

public class PropertyPlaceholderHelperTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void replacePlaceholders() {
		Properties properties = new Properties();
		properties.put("schema", "KSEMBEDDED");
		String value = "DROP USER ${schema} CASCADE;";
		PropertyPlaceholderHelper helper = new PropertyPlaceholderHelper("${", "}");
		value = helper.replacePlaceholders(value, properties);
		System.out.println(value);
	}
}
