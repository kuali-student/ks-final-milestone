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

import java.util.HashMap;

import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSStyles;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasName;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.HTMLTable.CellFormatter;

/**
 * FormLayoutPanel can be used to display fields in a three column form format
 * containing field label, field widget, and field description. 
 * 
 * @author Kuali Student Team
 *
 */
public class FormLayoutPanel extends Composite {
    public static final int FORM_LABEL_COL = 0;
    public static final int FORM_FIELD_COL = 1;
    public static final int FORM_DESC_COL = 2;
    
    SimplePanel form = new SimplePanel();
    FlexTable table = new FlexTable();
    HashMap<String, FormField> formFields = new HashMap<String, FormField>();
    
    public FormLayoutPanel(){
        initWidget(form);
    }
       
    
    /**
     * Add a field widget to this form. The form field must not contain a 
     * widget having same name as widget in an existing field in this form.
     * 
     * @param field
     */
    public void addFormField(FormField field){
        KSLabel label = GWT.create(KSLabel.class);
        label.init(field.getLabelText(), false);
        int rowIdx = table.getRowCount();
        table.setWidget(rowIdx, FORM_LABEL_COL, label);
        table.setWidget(rowIdx, FORM_FIELD_COL, field.getWidget());
        table.setText(rowIdx, FORM_DESC_COL, field.getDescription());
        
        CellFormatter cf = table.getCellFormatter();
        cf.setStyleName(rowIdx, FORM_LABEL_COL, KSStyles.KS_FORMLAYOUT_LABEL);
        cf.setStyleName(rowIdx, FORM_FIELD_COL, KSStyles.KS_FORMLAYOUT_FIELD);
        cf.setStyleName(rowIdx, FORM_DESC_COL, KSStyles.KS_FORMLAYOUT_DESC);
        
        formFields.put(field.getName(), field);
        form.setWidget(table);
    }    
    
    /**
     * Use to get names for all field widgets contained in this form.
     */
    public String[] getFieldNames(){
        return (String[])formFields.keySet().toArray();
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
        return ((FormField)formFields.get(name)).getText();
    }
    
    /**
     * Use to get the underlying widget with given name in form field.
     * 
     * @param name
     * @return
     */
    public Widget getFieldWidget(String name){
        return formFields.get(name).getWidget();
    }
    
    public FormField getFormRow(int row){
        String name = ((HasName)table.getWidget(row, FORM_FIELD_COL)).getName();
        return formFields.get(name);        
    }
   
    public int getRowCount(){
        return table.getRowCount();
    }
}
