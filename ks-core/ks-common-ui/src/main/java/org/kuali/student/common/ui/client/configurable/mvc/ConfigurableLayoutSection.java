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
