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

package org.kuali.student.core.dictionary.service.impl;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Test;
import org.kuali.student.common.search.dto.SearchTypeInfo;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SearchParseTest {
private ApplicationContext context = new ClassPathXmlApplicationContext("test-cross-search.xml");
	
	@Test
	public void test1(){
		Map<?, ?> map = context.getBeansOfType(SearchTypeInfo.class);
		//SearchTypeInfo search = (SearchTypeInfo) context.getBean("org.search.advanced");
		assertEquals(3,map.size());
	}

}
