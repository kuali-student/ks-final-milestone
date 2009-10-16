/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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
