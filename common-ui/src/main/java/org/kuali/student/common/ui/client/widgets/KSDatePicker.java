package org.kuali.student.common.ui.client.widgets;


import org.kuali.student.common.ui.client.widgets.impl.KSDatePickerImpl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;

public class KSDatePicker extends KSDatePickerAbstract{ 
    KSDatePickerAbstract datePicker = GWT.create(KSDatePickerImpl.class);
    
    public KSDatePicker() {
        this.initWidget(datePicker);
        
    }
}
