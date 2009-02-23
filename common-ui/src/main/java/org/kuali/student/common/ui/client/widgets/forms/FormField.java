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

import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasName;
import com.google.gwt.user.client.ui.Widget;

/**
 * FormField can be used to add fields to a FormLayoutPanel. FormField just wraps
 * a widget implementing HasName interface with a field label and description
 * to be displayed in the  FormLayoutPanel.
 * 
 * @author Kuali Student Team
 *
 */
public class FormField {
    Widget formField;
    private String name;
    private String label;
    private String desc;
      
    public String getLabelText() {
        return label;
    }

    public void setLabelText(String label) {
        this.label = label;
    }

    public String getDescription() {
        return desc;
    }
    
    public void setDescription(String desc) {
        this.desc = desc;
    }

    /**
     * This method returns the widget for this form field.
     * 
     * @param name
     * @return
     */
    public Widget getWidget() {
        return formField;
    }

    /**
     * Set the widget for this form field. The widget to set should implement
     * the HasName interface. The name of this form field will be the same name 
     * same name as the underlying widget. 
     */
    public void setWidget(Widget formField) {
        /* This test will fail when using abstract KS widget.
        try {
            name = ((HasName)formField).getName();
        } catch (Exception e) {
            throw new IllegalArgumentException("formField widget doesn't implement HasName interface");
        }
        */
        this.formField = formField;        
    }
    
    /**
     * Use to get text value from the underlying widget for this field. 
     * 
     * @return text for this form field if widget has text, null if the
     * widget does not have text (e.g. SelectListWidget, KSDatePicker,
     * composite field widgets)
     */
    public String getText() {
        try {
            HasText text = (HasText)formField;
            return text.getText();
        } catch (Exception e){
            return null;
        }
    }
   
    public String getName(){
        return name;
    }
    
    public void setName(String name){
        this.name = name;
    }
}
