/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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

package org.kuali.student.common.ui.client.widgets;


import java.util.Date;

import org.kuali.student.common.ui.client.widgets.impl.KSDatePickerImpl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;

/**
 * The KSDatePicker widget provides an easy way for a date to be entered by a user.  When this widget obtains focus,
 * a datepicker popup calendar appears below the text field.  The user may either select the date using the calendar,
 * or fill in the date manually (which will automatically selects the date for them in the calendar).
 * 
 * TODO 03/11/2009 - This widget currently only supports dates entered in the mm/dd/yyyy format.  Known unresolved backspace bug in Firefox.
 * 
 * @author Kuali Student Team
 *
 */
public class KSDatePicker extends KSDatePickerAbstract implements HasWatermark { 
    KSDatePickerAbstract datePicker = GWT.create(KSDatePickerImpl.class);
    
    /**
     * Creates a KSDatePicker widget.
     * 
     */
    public KSDatePicker() {
        this.initWidget(datePicker);
        
    }

    /**
     * Get the currently selected date.
     * 
     * @return the Date selected
     */
    @Override
    public Date getValue() {
        return datePicker.getValue();
    }

    /**
     * Sets the date and selects it.
     * 
     * @param date the Date to set the calendar and associated field to
     */
    @Override
    public void setValue(Date date) {
        datePicker.setValue(date);
    }

    /**
     * @see com.google.gwt.user.client.ui.HasValue#setValue(java.lang.Object, boolean)
     */
    @Override
    public void setValue(Date date, boolean fireEvents) {
        datePicker.setValue(date, fireEvents);
    }

    /**
     * @see com.google.gwt.event.logical.shared.HasValueChangeHandlers#addValueChangeHandler(com.google.gwt.event.logical.shared.ValueChangeHandler)
     */
    @Override
    public HandlerRegistration addValueChangeHandler(ValueChangeHandler<Date> handler) {
        return datePicker.addValueChangeHandler(handler);
    }

	@Override
	public HandlerRegistration addFocusHandler(FocusHandler handler) {
		return datePicker.addFocusHandler(handler);
	}

	@Override
	public HandlerRegistration addBlurHandler(BlurHandler handler) {
		return datePicker.addBlurHandler(handler);
	}

    @Override
    public boolean hasWatermark() {
        return datePicker.hasWatermark();
    }

    @Override
    public void setWatermarkText(String waterMark) {
        datePicker.setWatermarkText(waterMark);
    }

    @Override
    public boolean watermarkShowing() {
        return datePicker.watermarkShowing();
    }
    
    
}
