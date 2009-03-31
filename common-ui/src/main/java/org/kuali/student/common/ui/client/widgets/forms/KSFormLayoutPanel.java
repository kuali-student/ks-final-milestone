/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.common.ui.client.widgets.forms;

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
     * Use to get the text value of the form field with the given name. If the 
     * field has no text value, use getFieldWidget() to get the desired value(s) 
     * from the underlying widget.
     * 
     * @param name
     * @return
     */
    public String getFieldValue(String name){
        return form.getFieldValue(name);
    }
    
    public void setFieldValue(String name, String value){
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

}
