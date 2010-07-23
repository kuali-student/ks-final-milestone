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

package org.kuali.student.common.ui.client.application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.security.SecurityContext;
import org.kuali.student.core.messages.dto.Message;

public class ApplicationContext {
	private boolean loggedIn = true;
	private String userId = "testuser";
	private List<String> roles = new ArrayList<String>();
	
	private Map<String, Map<String, String>> messages = new HashMap<String, Map<String,String>>();
	private Map<String, String> flatMessages = new HashMap<String, String>();
	private List<Message> messagesList = new ArrayList<Message>();
	
	private SecurityContext securityContext;
	
	/**
	 * This constructor should only be visible to the common application package. If ApplicationContext is 
	 * required outside this package do Application.getApplicationContext();
	 */
	protected ApplicationContext() {
		roles.add("role1");
		roles.add("role2");
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public String getUserId() {
		return userId;
	}

	public List<String> getRoles() {
		return roles;
	}

    public void addMessages(List<Message> messages) {
		messagesList.addAll(messages);
	    for (Message m : messages) {
	        String groupName = m.getGroupName();
	        Map<String, String> group = this.messages.get(groupName);
	        if (group == null) {
	            group = new HashMap<String, String>();
	            this.messages.put(groupName, group);
	        }
	        group.put(m.getId(), m.getValue());
	        flatMessages.put(m.getId(), m.getValue());
	    }
	}
	
	public String getMessage(String messageId) {
	    return flatMessages.get(messageId);
    }
    
	public List<Message> getMessages() {
	    return messagesList;
    }
    
	
	public String getMessage(String groupName, String messageId) {
			
	    String result = null;
	    
	    Map<String, String> group = this.messages.get(groupName);
	    if (group != null) {
	        result = group.get(messageId);
	    }
	    
	    return result;
	}
	
    /**
     * 
     * This method looks up a UI Label in the messages cache.  
     * First looks for a label specific to the type and state of the field.
     * If none found try for a generalized label.
     * Otherwise return the supplied fieldId
     * Groups provide namespace for same label ids within different LUs
     * 
     * @param groupName - for example 'course' or 'program'
     * @param type
     * @param state
     * @param fieldId
     * @return
     */public String getUILabel(String groupName, String type, String state, String fieldId) {

        String label = getMessage(groupName, type + ":" + state + ":" + fieldId);
        
        if (label == null)
            label = getMessage(groupName, fieldId);
        
        if (label == null)
            label =  fieldId;
        
        return label;
        
    }

    public SecurityContext getSecurityContext() {
        return securityContext;
    }

    public void setSecurityContext(SecurityContext securityContext) {
        this.securityContext = securityContext;
    }
	
	
}
