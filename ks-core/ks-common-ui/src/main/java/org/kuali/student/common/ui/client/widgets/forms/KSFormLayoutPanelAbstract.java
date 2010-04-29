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

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/**
 * This class defines methods for implementations of a form layout panel.
 * Developers shouldn't reference this class, but should use FormLayoutPanel
 * directly. 
 * 
 * @author Kuali Student Team
 *
 */
public abstract class KSFormLayoutPanelAbstract extends Composite{
    
    public abstract void addFormField(KSFormField field);
    
    public abstract String[] getFieldNames();
    
    public abstract Object getFieldValue(String name);
    
    public abstract String getFieldText(String name);
    
    public abstract void setFieldValue(String name, Object value);
    
    public abstract Widget getFieldWidget(String name);
    
    public abstract KSFormField getFormRow(int row);
   
    public abstract int getRowCount();    

    public abstract void setEditMode(EditMode mode);
    
    public abstract void setDirtyState(boolean isDirty);
    
    public abstract boolean isDirty();
    
    public void init(String s){
        //Only impl class needs to implement this method.
    }
}
