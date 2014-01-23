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

package org.kuali.student.r1.core.dictionary.service.impl;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Test;
import org.kuali.student.r1.common.dictionary.old.dto.ObjectStructure;
import org.kuali.student.r1.common.dictionary.old.dto.Type;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DictionaryParseTest {
	private static final ConfigurableApplicationContext luContext = new ClassPathXmlApplicationContext("lu-dict-original.xml");
    private static final ConfigurableApplicationContext baseContext = new ClassPathXmlApplicationContext("base-org-dict-config.xml");
    private static final ConfigurableApplicationContext customContext = new ClassPathXmlApplicationContext("custom-org-dict-config.xml");

    static {
        luContext.registerShutdownHook();
        baseContext.registerShutdownHook();
        customContext.registerShutdownHook();
    }
	
	@Test
	public void test1(){
		Map<?, ?> map = luContext.getBeansOfType(ObjectStructure.class);
		assertEquals(133,map.size());
	}

	
	@Test
	public void test2(){
		//Test that the context does not break
		Map<?, ?> baseCtxMap = baseContext.getBeansOfType(ObjectStructure.class);
		Map<?, ?> customCtxMap = customContext.getBeansOfType(ObjectStructure.class);
		
		assertEquals(11,baseCtxMap.size());
		assertEquals(11,customCtxMap.size());
		
		Type doubleInheritanceType = (Type)customContext.getBean("DoubleInheritance");
		assertEquals("NEW",doubleInheritanceType.getName());
		assertEquals("Default Description",doubleInheritanceType.getDesc());
		
		Type attrType = (Type)customContext.getBean("OrgCodeInfo.type.default");
		assertEquals("value1",attrType.getAttributes().get("attr1"));
		assertEquals("value2",attrType.getAttributes().get("attr2"));
	}

}
