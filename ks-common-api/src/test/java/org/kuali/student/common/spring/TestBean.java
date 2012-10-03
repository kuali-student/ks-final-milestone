/**
 * 
 */
package org.kuali.student.common.spring;

import org.kuali.student.r1.common.dictionary.service.DictionaryService;
import org.kuali.student.r2.common.datadictionary.service.DataDictionaryService;
import org.kuali.student.r2.common.messages.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Kuali Student
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
