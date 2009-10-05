package org.kuali.student.lum.nlt.naturallanguage.util;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class TemplateTranslatorTest {

	@Test
	public void testTranslate() throws Exception {
		TemplateTranslator translator = new TemplateTranslator();
		Map<String, Object> contextMap = new HashMap<String, Object>();
		contextMap.put("person", "Kuali");
		String template = "Hey Mr. $person! Any students there?";

		String translation = translator.translate(contextMap, template);
		Assert.assertEquals("Hey Mr. Kuali! Any students there?", translation);
	}
}
