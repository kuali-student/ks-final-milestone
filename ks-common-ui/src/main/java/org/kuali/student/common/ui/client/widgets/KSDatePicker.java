package org.kuali.student.common.ui.client.widgets;


import java.util.Date;

import org.kuali.student.common.ui.client.widgets.impl.KSDatePickerImpl;

import com.google.gwt.core.client.GWT;
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
public class KSDatePicker extends KSDatePickerAbstract{ 
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
    
    
}
