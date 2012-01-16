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

package org.kuali.student.common.util;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.junit.Test;

public class TestPropertiesFactory {
	final Logger LOG = Logger.getLogger(TestPropertiesFactory.class);
	@Test
	public void testResolvePlaceholders() throws Exception{
//		String orig="1111${asd}2222${23}";
//		String newS = orig.replaceAll(".*\\$\\{([^}]+)\\}.*", "$1");
//		System.out.println(newS);
		PropertiesFilterFactoryBean pfb = new PropertiesFilterFactoryBean();
		pfb.setPrefix("prefix");
		pfb.setPropertyFile("classpath:testProps.properties");
		Properties props = (Properties) pfb.getObject();
		LOG.warn(props.toString());
	}
}
