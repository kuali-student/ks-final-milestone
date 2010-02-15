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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.kuali.student.common.ui.client.dto.HelpInfo;
import org.kuali.student.common.ui.client.event.DirtyStateChangeEvent;
import org.kuali.student.common.ui.client.event.DirtyStateChangeHandler;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.validator.CharFilter;
import org.kuali.student.common.ui.client.validator.Constraint;
import org.kuali.student.common.ui.client.widgets.KSHelpLink;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.forms.EditModeChangeEvent.EditMode;
import org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract;
import org.kuali.student.common.ui.client.widgets.list.SelectionChangeHandler;
import org.kuali.student.core.validation.dto.ValidationResultContainer;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.HasFocusHandlers;
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
    private Object initValue = null;
    private boolean isDirty = false;
    
    private final List<Constraint> constraints = new ArrayList<Constraint>();
    private final List<CharFilter> charFilters = new ArrayList<CharFilter>();
    
    private boolean hasHadFocus = false;
    private FocusHandler fieldFocusHandler = new FocusHandler() {
        public void onFocus(FocusEvent event) {
            hasHadFocus = true;
        }
    };
    
    private HandlerManager handlers = new HandlerManager(this);
      
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

    public KSFormField setLabelText(String label) {
        this.label = label;
        return this;
    }

    public String getDescription() {
        return desc;
    }
    
    public KSFormField setDescription(String desc) {
        this.desc = desc;
        return this;
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
    public KSFormField setWidget(Widget formField) {
        this.fieldWidget = formField;
        
        try {
            //Default form field name to widget name
            if (formField instanceof HasName && name == null){
                name = ((HasName)formField).getName();
            }
            
            //Add a value change handler to update dirty state
            if (formField instanceof HasValueChangeHandlers){ 
                ((HasValueChangeHandlers<Object>) formField).addValueChangeHandler(new ValueChangeHandler<Object>(){
                    public void onValueChange(ValueChangeEvent<Object> event) {
                        Object currentValue = event.getValue();
                        isDirty =   (initValue == null && (currentValue instanceof String ? ((String)currentValue).length() > 0:currentValue != null)) || 
                                    (initValue != null && !initValue.equals(event.getValue()));
                        handlers.fireEvent(new DirtyStateChangeEvent(isDirty));
                    }                    
                });
            }
            
            //TODO: Would this be better if KSSelectItemWidgetAbstract implemented HasValueChangeHandlers instead
            if (formField instanceof KSSelectItemWidgetAbstract){
                ((KSSelectItemWidgetAbstract) formField).addSelectionChangeHandler(new SelectionChangeHandler(){
                    public void onSelectionChange(KSSelectItemWidgetAbstract w) {
                        List<String> currentSelected = w.getSelectedItems();
                        isDirty = !(currentSelected.size() == ((List<String>)initValue).size() &&
                                   currentSelected.containsAll((List<String>)initValue));
                        handlers.fireEvent(new DirtyStateChangeEvent(isDirty));
                    }                    
                });
            }
            
            
            setDirty(false);
            
            if (formField instanceof HasFocusHandlers) {
                ((HasFocusHandlers)formField).addFocusHandler(fieldFocusHandler);
            }
        } catch (Exception e) {
            
        }

        return this;
    }
    
    
    
    @SuppressWarnings("unchecked")
    public Object getValue() {
        Object result = null;
        if (fieldWidget instanceof HasValue) {
            result = ((HasValue)fieldWidget).getValue();
        } else if (fieldWidget instanceof KSSelectItemWidgetAbstract){
            result = ((KSSelectItemWidgetAbstract)fieldWidget).getSelectedItem();
        } else if (fieldWidget instanceof HasText){
            result = getText();
        }
        return result;
    }
    
    @SuppressWarnings("unchecked")
    public KSFormField setValue(Object value) {
        try{
            if (fieldWidget instanceof HasValue) {
                ((HasValue)fieldWidget).setValue(value);
                setDirty(false);
            } else if (fieldWidget instanceof KSSelectItemWidgetAbstract){
                ((KSSelectItemWidgetAbstract)fieldWidget).selectItem((String)value);
                setDirty(false);
            } else if (value instanceof String){
                setText((String)value);
                setDirty(false);
            } else {
                throw new UnsupportedOperationException();
            }
        } catch (Exception e) {
            GWT.log("Form field [" + name + "] widget does not support setting value [" + value + "]", e);
        }
        return this;
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
                Object value = ((HasValue<?>)fieldWidget).getValue();
                if (value != null){
                    s = value.toString();
                }
            } catch (ClassCastException e2) {
            	//TODO: Should this return text for current selected item for single select list... Derek says yes
                //TODO   Try returning text instead of ID.
                try {
                String x = ((KSSelectItemWidgetAbstract)fieldWidget).getSelectedItem();
                s = ((KSSelectItemWidgetAbstract)fieldWidget).getListItems().getItemText(x);
 
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
    public KSFormField setText(String s){
        try {
            HasText text = (HasText)fieldWidget;
            text.setText(s);
        } catch (Exception e){
            GWT.log("Form field [" + name + "] does not support setText.", e);
        }        
        return this;
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
    public KSFormField setName(String name){
        this.name = name;
        return this;
    }

    public HelpInfo getHelpInfo() {
        return helpInfo;
    }

    public KSFormField setHelpInfo(HelpInfo helpInfo) {
        this.helpInfo = helpInfo;
        if (helpInfo != null){
            fieldHelpLink.setHelpInfo(helpInfo);
        } else {
            fieldHelpLink = new KSHelpLink();
        }        
        return this;
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
    public KSFormField setEditMode(EditMode mode){
        switch (mode) {
            case VIEW_ONLY:
                fieldValueLabel.setText(getText());  
//                fieldValueLabel.setText((String) getValue());//TODO Check if this is OK?
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
        return this;
    }

    /**
     * Use this to determine if form field is dirty. Form field is dirty if the value of the field changes
     * based on user input which differ from the value from the last value of the field's widget based on setValue, 
     * setFieldWidget, or setDirty operations.  
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
    public KSFormField setDirty(boolean isDirty) {
        this.isDirty = isDirty;
        if (fieldWidget instanceof HasValue){
            initValue = ((HasValue<?>)fieldWidget).getValue();
        } else if (fieldWidget instanceof KSSelectItemWidgetAbstract){
            initValue = ((KSSelectItemWidgetAbstract)fieldWidget).getSelectedItems();
        }
        return this;
    }
    
    /** 
     * Add handler to listen for dirty state change event. This is fired whenever user interaction with the underlying
     * form widget causes it to be dirty. This handler is not be invoked if the fields dirty state is changed via
     * calls to KSFormField.setDirty() or KSFormField.onDirtyStateChange().
     *
     */
    public KSFormField addDirtyStateHandler(DirtyStateChangeHandler handler){
        handlers.addHandler(DirtyStateChangeEvent.TYPE, handler);
        return this;
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

    
    public KSFormField addConstraint(Constraint c) {
        this.constraints.add(c);
        return this;
    }
    
    public List<Constraint> getConstraints() {
        return Collections.unmodifiableList(constraints);
    }
    
    public KSFormField addCharFilter(CharFilter filter) {
        this.charFilters.add(filter);
        return this;
    }
    
    public List<CharFilter> getCharFilters() {
        return Collections.unmodifiableList(charFilters);
    }
    
    public boolean hasHadFocus() {
        return this.hasHadFocus;
    }
    public KSFormField setHasHadFocus(boolean b) {
        this.hasHadFocus = b;
        return this;
    }
    
    public KSFormField validate(final Callback<ValidationResultContainer> callback) {
//        final Holder<DictValidationResultContainer> holder = new Holder<DictValidationResultContainer>();
//        
//        final Object value = getValue();
//        final Iterator<Constraint> itr = constraints.iterator();
//        
//        if (itr.hasNext()) {
//            itr.next().validate(value, new Callback<DictValidationResultContainer>() {
//                public void exec(DictValidationResultContainer vr) {
//                    if (!vr.isOk()) {
//                        holder.set(vr);
//                    }
//                    if (!vr.isError() && itr.hasNext()) {
//                        itr.next().validate(value, this);
//                    } else {
//                        // this is the end of the line
//                        if (holder.get() == null) {
//                            holder.set(DictValidationResultContainer.OK);
//                        }
//    
//                        if (hasHadFocus()) {
//                            // update helpinfo status
//                            switch (holder.get().getErrorLevel()) {
//                            case ERROR:
//                                getHelpLink().setStateError(flattenMessages(holder.get()));
//                                break;
//                            case WARN:
//                                getHelpLink().setStateError(flattenMessages(holder.get()));
//                                break;
//                            case OK:
//                                getHelpLink().setStateOK(flattenMessages(holder.get()));
//                            }
//                        }
//                        callback.exec(holder.get());
//                    }
//                }
//            });
//        } else {
//            callback.exec(DictValidationResultContainer.OK);
//        }
        return this;
    }
    
    public void reset() {
        getHelpLink().setStateDefault();
        hasHadFocus = false;
    }

    private String flattenMessages(ValidationResultContainer vr) {
//        StringBuilder sb = new StringBuilder();
//        for (String s : vr.getMessages()) {
//            sb.append(s);
//            sb.append("\n");
//        }
//        return sb.toString();
        return vr.toString();
    }
}
