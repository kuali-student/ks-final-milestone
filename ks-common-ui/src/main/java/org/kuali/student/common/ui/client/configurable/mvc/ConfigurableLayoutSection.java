package org.kuali.student.common.ui.client.configurable.mvc;


public interface ConfigurableLayoutSection {
	
    //FIXME: Should the field type be configurable or be based on dictionary?
    public enum FieldType {TEXTAREA, RICHTEXT, RADIO, CHECKBOX, TEXTBOX, DROPDOWN}
        
    public void addField(String fieldKey, String fieldLabel);
    
    public void addField(String fieldKey, String fieldLabel, FieldType fieldType);
    
    /** 
     * Used to add child section
     * 
     * @param fieldKey
     */
    public void addSection(String fieldKey, ConfigurableLayoutSection section);
}
