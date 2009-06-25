package org.kuali.student.common.ui.client.widgets;


import java.util.Date;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;

public abstract class KSDatePickerAbstract extends Composite implements HasValue<Date>{ 

    public abstract Date getValue();
    
    public abstract void setValue(Date date);
}
