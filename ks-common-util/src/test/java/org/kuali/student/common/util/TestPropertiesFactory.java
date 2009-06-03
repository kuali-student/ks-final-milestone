package org.kuali.student.common.util;

import java.util.Properties;

import org.junit.Test;
import org.kuali.student.common.util.PropertiesFilterFactoryBean;

public class TestPropertiesFactory {

	@Test
	public void testResolvePlaceholders() throws Exception{
//		String orig="1111${asd}2222${23}";
//		String newS = orig.replaceAll(".*\\$\\{([^}]+)\\}.*", "$1");
//		System.out.println(newS);
		PropertiesFilterFactoryBean pfb = new PropertiesFilterFactoryBean();
		pfb.setPrefix("prefix");
		pfb.setPropertyFile("classpath:testProps.properties");
		Properties props = (Properties) pfb.getObject();
		System.out.println(props.toString());
	}
}
