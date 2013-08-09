/*
 * Copyright 2012 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.common.spring;

import org.kuali.student.r1.common.dictionary.service.DictionaryService;
import org.kuali.student.r2.common.messages.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Kuali Student Team
 *
 */
public class TestBean {

	@Autowired
	private DictionaryService dictionaryService;
	
	@Autowired
	private MessageService messageService;
	
	/**
	 * 
	 */
	public TestBean() {
	}

	
	

	public DictionaryService getDictionaryService() {
		return dictionaryService;
	}




	public MessageService getMessageService() {
		return messageService;
	}




	public void setDictionaryService(DictionaryService dictionaryService) {
		this.dictionaryService = dictionaryService;
	}




	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}

	
	
	
	

}
