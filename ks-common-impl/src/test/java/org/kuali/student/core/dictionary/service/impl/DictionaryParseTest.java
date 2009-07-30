package org.kuali.student.core.dictionary.service.impl;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Test;
import org.kuali.student.core.dictionary.dto.ObjectStructure;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DictionaryParseTest {
	private ApplicationContext context = new ClassPathXmlApplicationContext("lu-dictionary-config-generated-excel.xml");
	
	@Test
	public void test1(){
		Map<?, ?> map = context.getBeansOfType(ObjectStructure.class);
		assertEquals(17,map.size());
	}
}
