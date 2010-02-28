package org.kuali.student.core.dictionary.service.impl;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Test;
import org.kuali.student.core.search.dto.SearchTypeInfo;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SearchParseTest {
private ApplicationContext context = new ClassPathXmlApplicationContext("test-cross-search.xml");
	
	@Test
	public void test1(){
		Map<?, ?> map = context.getBeansOfType(SearchTypeInfo.class);
		//SearchTypeInfo search = (SearchTypeInfo) context.getBean("org.search.advanced");
		assertEquals(14,map.size());
	}

}
