package org.kuali.student.common.ui.client.configurable.mvc;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.application.ApplicationContext;

public enum RequiredEnum {
	
	REQUIRED("required"), 
	OPTIONAL("optional"), 
	NOT_MARKED(null);
	
	final static ApplicationContext context = Application.getApplicationContext();
    private String messageId;
    private RequiredEnum(String messageId) {
        this.messageId = messageId;
    }
    public String toString() {
    	if(messageId != null){
    		return context.getMessage(this.messageId);
    	}
    	else{
    		return "";
    	}
    }
}
