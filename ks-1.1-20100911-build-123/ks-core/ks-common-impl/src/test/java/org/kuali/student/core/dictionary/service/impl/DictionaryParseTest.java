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
import org.kuali.student.core.dictionary.old.dto.ObjectStructure;
import org.kuali.student.core.dictionary.old.dto.Type;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DictionaryParseTest {
	private ApplicationContext context = new ClassPathXmlApplicationContext("lu-dict-original.xml");
	
	@Test
	public void test1(){
		Map<?, ?> map = context.getBeansOfType(ObjectStructure.class);
		assertEquals(133,map.size());
	}

	
	@Test
	public void test2(){
		//Test that the context does not break
		ApplicationContext baseCtx = new ClassPathXmlApplicationContext("base-org-dict-config.xml");
		ApplicationContext customCtx = new ClassPathXmlApplicationContext("custom-org-dict-config.xml");
		
		Map<?, ?> baseCtxMap = baseCtx.getBeansOfType(ObjectStructure.class);
		Map<?, ?> customCtxMap = customCtx.getBeansOfType(ObjectStructure.class);
		
		assertEquals(11,baseCtxMap.size());
		assertEquals(11,customCtxMap.size());
		
		Type doubleInheritanceType = (Type)customCtx.getBean("DoubleInheritance");
		assertEquals("NEW",doubleInheritanceType.getName());
		assertEquals("Default Description",doubleInheritanceType.getDesc());
		
		Type attrType = (Type)customCtx.getBean("OrgCodeInfo.type.default");
		assertEquals("value1",attrType.getAttributes().get("attr1"));
		assertEquals("value2",attrType.getAttributes().get("attr2"));
	}

}
