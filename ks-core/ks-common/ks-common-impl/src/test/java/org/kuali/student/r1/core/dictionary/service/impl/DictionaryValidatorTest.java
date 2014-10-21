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

import java.util.List;

import org.junit.Test;
import org.kuali.student.r1.common.dictionary.old.dto.ObjectStructure;
import org.kuali.student.r1.common.dictionary.service.impl.old.DictionaryServiceSpringImpl;
import org.kuali.student.r1.common.dictionary.service.old.DictionaryService;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r1.common.validator.old.ServerDateParser;
import org.kuali.student.r1.common.validator.old.Validator;

// TODO KSCM-428 
public class DictionaryValidatorTest {

	private static final DictionaryService SAMPLE_DICT = new DictionaryServiceSpringImpl("classpath:mockaddr-dictionary-config.xml");

	Validator val = null;
	
	public DictionaryValidatorTest() {
		val = new Validator();
		val.setDateParser(new ServerDateParser());
		val.setMessageService(null);
	}
	
    @Test
    public void testRequiredValidation() {
    	MockDictAddress addr1 = buildAddress1();

    	ObjectStructure o = SAMPLE_DICT.getObjectStructure("MockAddrInfo");
    	    	
    	List<ValidationResultInfo> results = val.validateTypeStateObject(addr1, o);    
    	assertEquals(results.size(), 4);

    	assertEquals(results.get(2).getErrorLevel(), ValidationResultInfo.ErrorLevel.ERROR);
    	assertEquals(results.get(2).getMessage(), "validation.required");
    }
    
    
    private MockDictAddress buildAddress1() {
    	MockDictAddress addr = new MockDictAddress();
    
    	addr.setId("Addr1");
    	addr.setType("homeaddr");
    	addr.setState("submitted");    	
    	addr.setPostalCode("wrong");
    	return addr;
    }
}
