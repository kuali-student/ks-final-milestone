package org.kuali.student.lum.course.service.impl;

import org.junit.Test;
import org.kuali.student.core.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestDictionaryLoad {
	@Test
	public void testLoadDictionary() {
		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:ks-cluInfo-dictionary-context.xml");
		ObjectStructureDefinition os = (ObjectStructureDefinition) ac.getBean(CluInfo.class.getName());
		os.getAttributes();
	}
}
