package org.kuali.db;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Properties;

import org.junit.Test;
import org.springframework.util.PropertyPlaceholderHelper;

import org.kuali.rice.core.config.ConfigContext;
import org.kuali.rice.core.config.JAXBConfigImpl;

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

	@Test
	public void nestedProperties() {
		Properties properties = new Properties();
		properties.put("db.vendor", "derby");
		properties.put("datasource.url.oracle", "jdbc:oracle:thin:@localhost:1521:XE");
		properties.put("datasource.url.derby", "jdbc:derby://localhost:1527/derby/rice-db;create=true");
		properties.put("datasource.url", "${datasource.url.${db.vendor}}");
		PropertyPlaceholderHelper helper = new PropertyPlaceholderHelper("${", "}");
		String value = "${datasource.url}";
		value = helper.replacePlaceholders(value, properties);
		// System.out.println(value);
	}

	@Test
	public void configContext() throws IOException {
		System.setProperty("additional.config.locations", "C:/temp/mergelog/nomerge/ks-config.xml");
		JAXBConfigImpl config = new JAXBConfigImpl("classpath:standalone-config.xml", ConfigContext.getCurrentContextConfig());
		config.setSystemOverride(true);
		config.parseConfig();
		ConfigContext.init(config);
		Properties properties = config.getProperties();
		System.out.println(properties.getProperty("datasource.url"));
		System.out.println(properties.getProperty("datasource.username"));
		System.out.println(properties.getProperty("datasource.password"));
	}

}
