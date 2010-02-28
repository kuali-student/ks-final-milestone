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
package org.kuali.student.common.ui.client.widgets.forms;

import org.kuali.student.common.ui.client.widgets.forms.EditModeChangeEvent.EditMode;
import org.kuali.student.common.ui.client.widgets.forms.impl.KSFormLayoutPanelImpl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;

/**
 * FormLayoutPanel can be used to display fields in a three column form format
 * containing field label, field widget, and field description. 
 * 
 * @author Kuali Student Team
 *
 */
public class KSFormLayoutPanel extends KSFormLayoutPanelAbstract {
    
    KSFormLayoutPanelAbstract form = GWT.create(KSFormLayoutPanelImpl.class);
    
    public KSFormLayoutPanel(){
        form.init(null);
        this.initWidget(form);        
    }
    
    public KSFormLayoutPanel(String title){
        form.init(title);
        form.setTitle(title);
        this.initWidget(form);
    }
    
    /**
     * Add a field widget to this form. The form field must not contain a 
     * widget having same name as widget in an existing field in this form.
     * 
     * @param field
     */
    public void addFormField(KSFormField field){
        form.addFormField(field);
    }    
    
    /**
     * Use to get names for all field widgets contained in this form.
     */
    public String[] getFieldNames(){
        return form.getFieldNames();
    }
    
    /**
     * Use to get the 
     * 
     * @param name
     * @return
     */
    public Object getFieldValue(String name){
        return form.getFieldValue(name);
    }
    
    /**
     * Get text value of the form field. This may not necessarily be the string
     * representation of getFieldValue(). (eg. For drop down list fields, this
     * would be the visible text, rather than it's associated value)  
     */
    public String getFieldText(String name){
        return form.getFieldText(name);
    }


    public void setFieldValue(String name, Object value){
        form.setFieldValue(name, value);
    }
    
    /**
     * Use to get the underlying widget with given name in form field.
     * 
     * @param name
     * @return
     */
    public Widget getFieldWidget(String name){
        return form.getFieldWidget(name);
    }
    
    public KSFormField getFormRow(int row){
        return form.getFormRow(row);        
    }
   
    public int getRowCount(){
        return form.getRowCount();
    }

    /**
     * Use to set the edit mode of form fields. The possible edit modes are
     * 
     * EDITABLE:    Enable all form field input elements.
     * UNEDITABLE:  Disable all form field input elements.
     * VIEW_ONLY:   Do not display form field input elements, only display it's text value.
     * 
     * Setting the edit mode will only affect the form in its current state. If you add new form fields they will not reflect the 
     * edit mode previously set.
     * 
     * @see org.kuali.student.common.ui.client.widgets.forms.KSFormLayoutPanelAbstract#setEditMode(org.kuali.student.common.ui.client.widgets.forms.EditModeChangeEvent.EditMode)
     */
    public void setEditMode(EditMode mode){
        form.setEditMode(mode);
    }
        
    /**
     * Use to set the dirty state of this form. All containing form fields are also set
     * to the same dirty state.
     *
     */
    public void setDirtyState(boolean isDirty){
        form.setDirtyState(isDirty);
    }
    
    /**
     * Use to determine the dirty state of this form.
     *  
     * @return true if any of one of the form fields is in a dirty state.
     */
    public boolean isDirty(){
        return form.isDirty();
    }
}
