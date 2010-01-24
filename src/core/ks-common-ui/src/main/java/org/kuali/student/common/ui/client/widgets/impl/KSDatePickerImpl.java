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
package org.kuali.student.common.ui.client.widgets.impl;

import java.util.Date;

import org.kuali.student.common.ui.client.widgets.KSDatePickerAbstract;
import org.kuali.student.common.ui.client.widgets.KSStyles;
import org.kuali.student.common.ui.client.widgets.KSTextBox;
import org.kuali.student.common.ui.client.widgets.focus.FocusGroup;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.HasBlurHandlers;
import com.google.gwt.event.dom.client.HasFocusHandlers;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.datepicker.client.DatePicker;

public class KSDatePickerImpl extends KSDatePickerAbstract implements HasFocusHandlers, HasBlurHandlers{
	private DatePicker picker = new DatePicker();
	private KSTextBox dateField = new KSTextBox();
	private PopupPanel popup = new PopupPanel(true);
	private Date selectedDate = null;
	private Date currentDate = new Date();
	private DateTimeFormat df = DateTimeFormat.getFormat("MM/dd/yyyy");
	private boolean justPicked = false;
	private final FocusGroup focus = new FocusGroup(this);
	
	
	public KSDatePickerImpl(){ 
		this.initWidget(dateField);
		focus.addWidget(picker);
		focus.addWidget(dateField);
		//pickerWrapper.add(picker);
		popup.add(picker);
		
		picker.setWidth(dateField.getOffsetWidth() + "px");
		dateField.addBlurHandler(new BlurHandler(){
			public void onBlur(BlurEvent event) {
				dateField.removeStyleName(KSStyles.KS_DATEFIELD_FOCUS_STYLE);	
				
			}	
		});
		

		dateField.addFocusHandler(new FocusHandler(){
			public void onFocus(FocusEvent event) {
				dateField.addStyleName(KSStyles.KS_DATEFIELD_FOCUS_STYLE);
				popup.setPopupPosition(getAbsoluteLeft(), getAbsoluteTop() + dateField.getOffsetHeight());
				if(justPicked){
					dateField.selectAll();
					justPicked = false;
				}
				else{
					popup.show();
					DeferredCommand.addCommand(new Command(){
						@Override
						public void execute() {
							focus.setSuppressed(true);
						}
					});
				}
				
			}		
		});
		
		dateField.addClickHandler(new ClickHandler(){

			public void onClick(ClickEvent event) {
				popup.setPopupPosition(getAbsoluteLeft(), getAbsoluteTop() + dateField.getOffsetHeight());
				popup.show();
				//dateField.selectAll();
				
			}
			
		});
		
		dateField.addKeyPressHandler(new KeyPressHandler(){

			public void onKeyPress(KeyPressEvent event) {
				String dateText = dateField.getText();
				String validInput = "0123456789";
				if(validInput.indexOf(event.getCharCode()) == -1){
						event.preventDefault();
				}
			}
			
		});
		
		dateField.addKeyDownHandler(new KeyDownHandler(){

			public void onKeyDown(KeyDownEvent event) {
				if(event.getNativeKeyCode() == KeyCodes.KEY_TAB){
					popup.hide();
					if(picker.getValue() != null){
						selectedDate = picker.getValue();
					}
				}
			}
			
		});
		
		dateField.addKeyUpHandler(new KeyUpHandler(){

			public void onKeyUp(KeyUpEvent event) {
				String dateText = dateField.getText();

				if(event.getNativeKeyCode() != KeyCodes.KEY_BACKSPACE && event.getNativeKeyCode() != KeyCodes.KEY_DELETE){
					if(dateText.length() == 2){
						dateField.setText(dateText + "/");
						String current = df.format(currentDate);
						Date newDate = df.parse(dateField.getText()+ "01" + current.substring(5));
						picker.setCurrentMonth(newDate);
					}
					else if(dateText.length() == 5){
						dateField.setText(dateText + "/");
						String current = df.format(currentDate);
						Date newDate = df.parse(dateField.getText() + current.substring(6));
						dateField.setText(df.format(newDate).substring(0, 6));
						picker.setCurrentMonth(newDate);
						picker.setValue(newDate, false);
						
					}
					else if(dateText.length() == 10){
						Date newDate = df.parse(dateField.getText());
						picker.setCurrentMonth(newDate);
						picker.setValue(newDate, false);
						selectedDate = picker.getValue();
					}
				}
			}
			
		});
		
		
		//pickerHandlers
		picker.addValueChangeHandler(new ValueChangeHandler<Date>(){

			public void onValueChange(ValueChangeEvent<Date> event) {
				if(picker.getValue() != null){
				    dateField.setText(df.format(picker.getValue()));
					selectedDate = picker.getValue();
				}
				dateField.setFocus(true);
				popup.hide();
				justPicked = true;
				focus.setSuppressed(false);
				fireValueChangeEvent();
			}	
		});
		
	}
	
	private void fireValueChangeEvent(){
        ValueChangeEvent.fire(this, selectedDate);	    
	}
	
	public Date getValue(){
		return this.selectedDate;
	}
	
	public void setValue(Date date){
		if(null==date){
			dateField.setText("");
		}else{
			dateField.setText(df.format(date));
	        picker.setCurrentMonth(date);
		}
        picker.setValue(date, false);
	    selectedDate = date;
	}

    @Override
    public void setValue(Date date, boolean fireEvents) {
        setValue(date);        
    }

    @Override
    public HandlerRegistration addValueChangeHandler(ValueChangeHandler<Date> handler) {
        return addHandler(handler, ValueChangeEvent.getType());
    }

	@Override
	public HandlerRegistration addFocusHandler(FocusHandler handler) {
		return focus.addFocusHandler(handler);
	}

	@Override
	public HandlerRegistration addBlurHandler(BlurHandler handler) {
		return focus.addBlurHandler(handler);
	}
	
}
