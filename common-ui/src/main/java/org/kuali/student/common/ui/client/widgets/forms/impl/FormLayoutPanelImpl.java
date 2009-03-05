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
package org.kuali.student.common.ui.client.widgets.forms.impl;

import java.util.HashMap;

import org.kuali.student.common.ui.client.widgets.KSHelpLink;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSStyles;
import org.kuali.student.common.ui.client.widgets.forms.FormField;
import org.kuali.student.common.ui.client.widgets.forms.FormLayoutPanelAbstract;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasName;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.HTMLTable.CellFormatter;

/**
 * Impl class for FormLayoutPanel 
 *     
 * @author Kuali Student Team
 *
 */
public class FormLayoutPanelImpl extends FormLayoutPanelAbstract{
    public static final int FORM_LABEL_COL = 0;
    public static final int FORM_FIELD_COL = 1;
    public static final int FORM_DESC_COL = 2;

    VerticalPanel form = new VerticalPanel();
    FlexTable table = new FlexTable();
    HashMap<String, FormField> formFields = new HashMap<String, FormField>();

    public void init(String title){
        if (title != null){
            form.add(new Label(title));
        }
        form.add(table);
        this.initWidget(form);        
    }
    
    public void addFormField(FormField field){
        KSLabel label = new KSLabel(field.getLabelText(), false);
        int rowIdx = table.getRowCount();
        table.setWidget(rowIdx, FORM_LABEL_COL, label);
        table.setWidget(rowIdx, FORM_FIELD_COL, field.getWidget());
        
        KSHelpLink helpLink = new KSHelpLink();
        helpLink.setHelpInfo(field.getHelpInfo());
        table.setWidget(rowIdx, FORM_DESC_COL, helpLink);

        CellFormatter cf = table.getCellFormatter();
        cf.setStyleName(rowIdx, FORM_LABEL_COL, KSStyles.KS_FORMLAYOUT_LABEL);
        cf.setStyleName(rowIdx, FORM_FIELD_COL, KSStyles.KS_FORMLAYOUT_FIELD);
        cf.setStyleName(rowIdx, FORM_DESC_COL, KSStyles.KS_FORMLAYOUT_DESC);

        formFields.put(field.getName(), field);
    }    

    public String[] getFieldNames(){
        return (String[])formFields.keySet().toArray();
    }

    public String getFieldValue(String name){
        return ((FormField)formFields.get(name)).getText();
    }

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
    
    public void setFieldValue(String name, String value){
        ((FormField)formFields.get(name)).setText(value);
    }
}
