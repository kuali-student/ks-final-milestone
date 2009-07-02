package org.kuali.student.common.ui.client.configurable.mvc;

import java.util.List;

import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.core.validation.dto.ValidationResult.ErrorLevel;

import com.google.gwt.user.client.ui.Widget;


public interface ConfigurableLayoutSection {
	        
    public void addField(FieldDescriptor fieldDescriptor);
        
    /** 
     * Used to add child section
     * 
     * @param fieldKey
     */
    public void addSection(NestedSection section);
    
    public List<FieldDescriptor> getFields();
    
    public void validate(Callback<ErrorLevel> callback);
    
    public String getSectionTitle();

    public void setSectionTitle(String sectionTitle);

    public String getInstructions();
    
    public void setInstructions(String instructions);
}
