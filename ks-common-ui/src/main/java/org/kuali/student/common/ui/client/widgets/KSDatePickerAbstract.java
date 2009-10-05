package org.kuali.student.common.ui.client.widgets;


import java.util.Date;

import com.google.gwt.event.dom.client.HasBlurHandlers;
import com.google.gwt.event.dom.client.HasFocusHandlers;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;

public abstract class KSDatePickerAbstract extends Composite implements HasValue<Date>, HasFocusHandlers, HasBlurHandlers { 

    public abstract Date getValue();
    
    public abstract void setValue(Date date);
}
