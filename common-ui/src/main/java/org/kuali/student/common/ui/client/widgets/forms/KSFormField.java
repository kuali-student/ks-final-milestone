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
import org.kuali.student.common.ui.client.event.DirtyStateChangeEvent;
import org.kuali.student.common.ui.client.event.DirtyStateChangeHandler;
import org.kuali.student.common.ui.client.widgets.KSHelpLink;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.forms.EditModeChangeEvent.EditMode;
import org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.HasName;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * FormField can be used to add fields to a FormLayoutPanel. FormField just wraps
 * a widget implementing HasName interface with a field label and description
 * to be displayed in the  FormLayoutPanel.
 * 
 * @author Kuali Student Team
 *
 */
public class KSFormField implements EditModeChangeHandler, DirtyStateChangeHandler {
    private SimplePanel fieldDispWidget = new SimplePanel();
    private KSHelpLink fieldHelpLink = new KSHelpLink();
    private KSLabel fieldValueLabel = new KSLabel();
    private Widget fieldWidget;
    
    private String name = null;
    private String label = null;
    private String desc = null;
    private HelpInfo helpInfo = null;
    private EditMode mode = EditMode.EDITABLE;
    private Object previousValue = null;
    private boolean isDirty = false;
    
    HandlerManager handlers = new HandlerManager(this);
      
    public KSFormField(){
    }

    public KSFormField(String name){
        this.name = name;
    }

    public KSFormField(String name, String label){
        this.name = name;
        this.label = label;
    }
    
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
     * This method returns a display widget, which will be toggled based on
     * toggle event.
     * 
     * @return
     */
    public Widget getDisplayWidget(){
        setEditMode(this.mode);
        return this.fieldDispWidget;
    }
    
    /**
     * This method returns the underlying widget for this form field. Use this
     * only to get access to the underlying field widget, to display this widget
     * call getDisplayWidget instead.
     * 
     * @param name
     * @return
     */
    public Widget getWidget() {
        return fieldWidget;
    }

    /**
     * Set the widget for this form field. If the widget implements the HasName
     * interface, the default name will be set to the name of the underlying 
     * widget, and this form field can be accessed in a FormLayoutPanel using  
     * the same name as the underlying widget. 
     */
    public void setWidget(Widget formField) {
        try {
            //Default form field name to widget name
            if (formField instanceof HasName && name == null){
                name = ((HasName)formField).getName();
            }
            
            //Add a value change handler to update dirty state
            if (formField instanceof HasValueChangeHandlers){
                ((HasValueChangeHandlers<Object>) formField).addValueChangeHandler(new ValueChangeHandler<Object>(){
                    public void onValueChange(ValueChangeEvent<Object> event) {
                        if (!isDirty && previousValue != null && previousValue.equals(event.getValue())){
                            isDirty = true;
                            handlers.fireEvent(new DirtyStateChangeEvent(true));
                        }
                        previousValue = event.getValue();
                    }                    
                });
            }
        } catch (Exception e) {
            
        }
        this.fieldWidget = formField;
    }
    
    /**
     * Use to get string representation for current value of the underlying widget for this field. 
     * 
     * @return string representation of widget value. Null if string representation couldn't
     * be obtained.
     */
    public String getText() {
        String s = null;
        try {
            s = ((HasText)fieldWidget).getText();
        } catch (ClassCastException e){
            try{
                Object value = ((HasValue)fieldWidget).getValue();
                if (value != null){
                    s = value.toString();
                }
            } catch (ClassCastException e2) {
            	//TODO: Should this return text for current selected item for single select list... Derek says yes
                try {
                s = ((KSSelectItemWidgetAbstract)fieldWidget).getSelectedItem();
                } catch(ClassCastException e3) {
                    GWT.log("Form field [" + name + "] does not support getText.", e);
                }
            }
        }
        
        return s;
    }
   
    /**
     * Use to set text value for the underlying widget for this field.  
     * 
     */
    public void setText(String s){
        try {
            HasText text = (HasText)fieldWidget;
            text.setText(s);
        } catch (Exception e){
            throw new UnsupportedOperationException("Containing field widget doesn't implement HasText");
        }        
    }
       
    /** 
     * @return the name of this form field
     */
    public String getName(){
        return name;
    }
    
    /**
     * Set the name of this form field.
     * 
     * @param name
     */
    public void setName(String name){
        this.name = name;
    }

    public HelpInfo getHelpInfo() {
        return helpInfo;
    }

    public void setHelpInfo(HelpInfo helpInfo) {
        this.helpInfo = helpInfo;
        if (helpInfo != null){
            fieldHelpLink.setHelpInfo(helpInfo);
        } else {
            fieldHelpLink = new KSHelpLink();
        }        
    }

    public KSHelpLink getHelpLink(){
        return this.fieldHelpLink;
    }
    
    /**
     * Handle a edit mode change event. Should only be called by a handler manager. 
     * 
     * @see org.kuali.student.common.ui.client.widgets.forms.EditModeChangeHandler#onEditModeChange(org.kuali.student.common.ui.client.widgets.forms.EditModeChangeEvent)
     */
    @Override
    public void onEditModeChange(EditModeChangeEvent event) {
        if (this.mode != event.getEditMode()){
            this.mode = event.getEditMode();
            setEditMode(this.mode);
        }
    }
    
    /** 
     * Use this to toggle the edit mode of all fields in this form
     * 
     * @param mode
     */
    public void setEditMode(EditMode mode){
        switch (mode) {
            case VIEW_ONLY:
                fieldValueLabel.setText(getText());
                fieldDispWidget.setWidget(fieldValueLabel);
                fieldHelpLink.setVisible(false);
                break;
            case EDITABLE:
                try {
                    ((FocusWidget)fieldWidget).setEnabled(true);
                } catch (Exception e) {
                }
                fieldDispWidget.setWidget(fieldWidget);
                fieldHelpLink.setVisible(true);
                break;
            case UNEDITABLE:
                try {
                    ((FocusWidget)fieldWidget).setEnabled(false);
                } catch (Exception e) {
                    GWT.log("Form field [" + name + "] could not be disabled.", e);
                }
                fieldDispWidget.setWidget(fieldWidget);
                fieldHelpLink.setVisible(true);
        }
    }

    /**
     * Use this to determine if form field is dirty
     * 
     * @return
     */
    public boolean isDirty() {
        return isDirty;
    }

    /** 
     * Use to set the dirty state of the form field. 
     * 
     * @param isDirty
     */
    public void setDirty(boolean isDirty) {
        this.isDirty = isDirty;
    }
    
    /** 
     * Add handler to listen for dirty state change event. This is fired whenever user interaction with the underlying
     * form widget causes it to be dirty. This handler is not be invoked if the fields dirty state is changed via
     * calls to KSFormField.setDirty() or KSFormField.onDirtyStateChange().
     *
     */
    public void addDirtyStateHandler(DirtyStateChangeHandler handler){
        handlers.addHandler(DirtyStateChangeEvent.TYPE, handler);
    }

    /**
     * This will set the dirty state of this form field based on the dirty event. Should only be called
     * by a handler manager. 
     * 
     * @see org.kuali.student.common.ui.client.event.DirtyStateChangeHandler#onDirtyStateChange(org.kuali.student.common.ui.client.event.DirtyStateChangeEvent)
     */
    @Override
    public void onDirtyStateChange(DirtyStateChangeEvent event) {
        setDirty(event.isDirty());
    }

}
