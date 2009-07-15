package org.kuali.student.common.ui.client.configurable.mvc;

import java.util.List;

import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.core.validation.dto.ValidationResultInfo;


public interface ConfigurableLayoutSection {
	        
    public void addField(FieldDescriptor fieldDescriptor);
        
    /** 
     * Used to add child section
     * 
     * @param fieldKey
     */
    public void addSection(Section section);
    
    public List<FieldDescriptor> getFields();
    
    public void validate(Callback<ValidationResultInfo.ErrorLevel> callback);
    
    public String getSectionTitle();

    public void setSectionTitle(String sectionTitle);

    public String getInstructions();
    
    public void setInstructions(String instructions);
}
