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

import org.kuali.student.common.ui.client.dto.HelpInfo;

import com.google.gwt.user.client.ui.HasName;
import com.google.gwt.user.client.ui.HasText;
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
    private HelpInfo helpInfo;
      
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
     * Set the widget for this form field. If the widget implements the HasName
     * interface, this form field can be accessed in a FormLayoutPanel using  
     * the same name as the underlying widget. 
     */
    public void setWidget(Widget formField) {
        try {
            if (formField instanceof HasName){
                name = ((HasName)formField).getName();
            }
        } catch (Exception e) {
            
        }
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
   
    /**
     * Use to set text value for the underlying widget for this field.  
     * 
     */
    public void setText(String s){
        try {
            HasText text = (HasText)formField;
            text.setText(s);
        } catch (Exception e){
            throw new UnsupportedOperationException("Field widget doesn't implement HasText");
        }        
    }
    
    public String getName(){
        return name;
    }
    
    public void setName(String name){
        this.name = name;
    }

    public HelpInfo getHelpInfo() {
        return helpInfo;
    }

    public void setHelpInfo(HelpInfo helpInfo) {
        this.helpInfo = helpInfo;
    }
}
