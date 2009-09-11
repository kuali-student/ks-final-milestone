package org.kuali.student.common.ui.client.application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.security.SecurityContext;
import org.kuali.student.core.dictionary.dto.ObjectStructure;
import org.kuali.student.core.messages.dto.Message;

// TODO find out what we'll really be storing here, and where to get it
// for now this is just a mockup
public class ApplicationContext {
	private boolean loggedIn = true;
	private String userId = "testuser";
	private List<String> roles = new ArrayList<String>();
	
	private Map<String, Map<String, String>> messages = new HashMap<String, Map<String,String>>();
	private Map<String, String> flatMessages = new HashMap<String, String>();
	Map<String, ObjectStructure> dictionaryData = new HashMap<String, ObjectStructure>();
	
	private SecurityContext securityContext;
	
	public ApplicationContext() {
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
	public void addDictionaryData(String name, ObjectStructure objStructure){
	    dictionaryData.put(name, objStructure);
	}

    public ObjectStructure getDictionaryData(String name){
       return dictionaryData.get(name);
    }
	public void addMessages(List<Message> messages) {
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
     * 
     * @param type
     * @param state
     * @param fieldId
     * @return
     */public String getUILabel(String type, String state, String fieldId) {

        String label = getMessage(type + ":" + state + ":" + fieldId);
        
        if (label == null)
            label = getMessage(fieldId);
        
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
