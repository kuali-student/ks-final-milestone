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
package org.kuali.student.common.ui.client.widgets.forms.impl;

import java.util.HashMap;

import org.kuali.student.common.ui.client.event.DirtyStateChangeEvent;
import org.kuali.student.common.ui.client.event.DirtyStateChangeHandler;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSStyles;
import org.kuali.student.common.ui.client.widgets.forms.EditModeChangeEvent;
import org.kuali.student.common.ui.client.widgets.forms.KSFormField;
import org.kuali.student.common.ui.client.widgets.forms.KSFormLayoutPanelAbstract;
import org.kuali.student.common.ui.client.widgets.forms.EditModeChangeEvent.EditMode;

import com.google.gwt.event.shared.HandlerManager;
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
public class KSFormLayoutPanelImpl extends KSFormLayoutPanelAbstract{
    public static final int FORM_LABEL_COL = 0;
    public static final int FORM_FIELD_COL = 1;
    public static final int FORM_DESC_COL = 2;

    HandlerManager handlers = new HandlerManager(this);
    
    EditMode editMode = EditMode.EDITABLE;
    boolean isDirty = false;
    
    VerticalPanel form = new VerticalPanel();
    FlexTable table = new FlexTable();
    HashMap<String, KSFormField> formFields = new HashMap<String, KSFormField>();

    public void init(String title){
        if (title != null){
            form.add(new Label(title));
        }
        form.add(table);
        this.initWidget(form);        
    }
    
    public void addFormField(KSFormField field){
        KSLabel label = new KSLabel(field.getLabelText(), false);
        label.setWordWrap(true);
        int rowIdx = table.getRowCount();
        table.setWidget(rowIdx, FORM_LABEL_COL, label);
        table.setWidget(rowIdx, FORM_FIELD_COL, field.getDisplayWidget());
        
        if (field.getHelpInfo() != null){
            table.setWidget(rowIdx, FORM_DESC_COL, field.getHelpLink());
        }

        CellFormatter cf = table.getCellFormatter();
        cf.setStyleName(rowIdx, FORM_LABEL_COL, KSStyles.KS_FORMLAYOUT_LABEL);
        cf.setStyleName(rowIdx, FORM_FIELD_COL, KSStyles.KS_FORMLAYOUT_FIELD);
        cf.setStyleName(rowIdx, FORM_DESC_COL, KSStyles.KS_FORMLAYOUT_HELP);

        //Add dirty handler to field to set form's dirty state when field becomes clean/dirty
        field.addDirtyStateHandler(new DirtyStateChangeHandler(){
            public void onDirtyStateChange(DirtyStateChangeEvent event) {
                if (event.isDirty()){
                    KSFormLayoutPanelImpl.this.isDirty = true;
                } else {
                    //Unfortunately must iterate through all form fields to see if other fields are still dirty
                    KSFormLayoutPanelImpl.this.isDirty = false;
                    for (KSFormField f:formFields.values()){
                        if (f.isDirty()){
                            KSFormLayoutPanelImpl.this.isDirty = true;
                            break;
                        }
                    }
                }
            }            
        });
        formFields.put(field.getName(), field);
        handlers.addHandler(EditModeChangeEvent.TYPE, field);
        handlers.addHandler(DirtyStateChangeEvent.TYPE, field);
    }    

    public String[] getFieldNames(){
        return (String[])formFields.keySet().toArray();
    }

    public Object getFieldValue(String name){
        return ((KSFormField)formFields.get(name)).getValue();
    }

    public String getFieldText(String name){
        return ((KSFormField)formFields.get(name)).getText();
    }

    public Widget getFieldWidget(String name){
        return formFields.get(name).getWidget();
    }

    public KSFormField getFormRow(int row){
        String name = ((HasName)table.getWidget(row, FORM_FIELD_COL)).getName();
        return formFields.get(name);        
    }

    public int getRowCount(){
        return table.getRowCount();
    }
    
    public void setFieldValue(String name, Object value){
        ((KSFormField)formFields.get(name)).setValue(value);
    }
    
    public void setEditMode(EditMode editable){
        if (this.editMode != editable){
            this.editMode = editable;
            handlers.fireEvent(new EditModeChangeEvent(editable));
        }
    }
    
    public void setDirtyState(boolean isDirty){
        handlers.fireEvent(new DirtyStateChangeEvent(isDirty));        
    }

    public boolean isDirty() {
        return isDirty;
    }
}
