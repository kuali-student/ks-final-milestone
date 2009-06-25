package org.kuali.student.common.ui.client.configurable.mvc;


public interface ConfigurableLayoutSection {
	        
    public void addField(FieldDescriptor fieldDescriptor);
        
    /** 
     * Used to add child section
     * 
     * @param fieldKey
     */
    public void addSection(String fieldKey, ConfigurableLayoutSection section);
}
