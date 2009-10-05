package org.kuali.student.common.ui.client.configurable.mvc;

import java.util.List;

import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.core.validation.dto.ValidationResultInfo;

import com.google.gwt.user.client.ui.Widget;


/** 
 * This interface defines all methods an implementation of a configurable
 * section must define, so that configuration can be reused regardless of
 * section implementation used. 
 * 
 * @author Kuali Student Team
 *
 */
public interface ConfigurableLayoutSection extends HasLayoutController {

    /** 
     * Add a field to this section
     * 
     * @param fieldDescriptor
     */
    public void addField(FieldDescriptor fieldDescriptor);
        
    /** 
     * Used to add child section
     * 
     * @param fieldKey
     */
    public void addSection(Section section);
    
    /**
     * Add widget to this section
     * 
     * @param widget
     */
    public void addWidget(Widget widget);
    
    public List<FieldDescriptor> getFields();
    
    public void validate(Callback<ValidationResultInfo.ErrorLevel> callback);
    
    public SectionTitle getSectionTitle();

    public void setSectionTitle(SectionTitle sectionTitle);

    public String getInstructions();
    
    public void setInstructions(String instructions);
}
