package org.kuali.student.common.ui.client.widgets;


import java.util.Date;

import org.kuali.student.common.ui.client.widgets.impl.KSDatePickerImpl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;

/**
 * The KSDatePicker widget provides an easy way for a date to be entered by a user.  When this widget obtains focus,
 * a datepicker popup calendar appears below the text field.  The user may either select the date using the calendar,
 * or fill in the date manually (which will automatically selects the date for them in the calendar).
 * 
 * 03/11/2009 - This widget currently only supports dates entered in the mm/dd/yyyy format.  Unresolved backspace bug in Firefox.
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
     * @see org.kuali.student.common.ui.client.widgets.KSDatePickerAbstract#getSelectedDate()
     */
    @Override
    public Date getDate() {
        return datePicker.getDate();
    }

    /**
     * Sets the date and selects it.
     * 
     * @see org.kuali.student.common.ui.client.widgets.KSDatePickerAbstract#setDate(java.util.Date)
     */
    @Override
    public void setDate(Date date) {
        datePicker.setDate(date);
    }
}
