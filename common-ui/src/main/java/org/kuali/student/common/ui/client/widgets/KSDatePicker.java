package org.kuali.student.common.ui.client.widgets;


import java.util.Date;

import org.kuali.student.common.ui.client.widgets.impl.KSDatePickerImpl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;

public class KSDatePicker extends KSDatePickerAbstract{ 
    KSDatePickerAbstract datePicker = GWT.create(KSDatePickerImpl.class);
    
    public KSDatePicker() {
        this.initWidget(datePicker);
        
    }

    /**
     * @see org.kuali.student.common.ui.client.widgets.KSDatePickerAbstract#getSelectedDate()
     */
    @Override
    public Date getDate() {
        return datePicker.getDate();
    }

    /**
     * @see org.kuali.student.common.ui.client.widgets.KSDatePickerAbstract#setDate(java.util.Date)
     */
    @Override
    public void setDate(Date date) {
        datePicker.setDate(date);
    }
}
