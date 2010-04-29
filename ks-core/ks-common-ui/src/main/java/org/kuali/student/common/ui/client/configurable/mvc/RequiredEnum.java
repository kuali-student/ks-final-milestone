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
