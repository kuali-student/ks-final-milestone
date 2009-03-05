package org.kuali.student.common.ui.client.widgets;


import java.util.Date;

import com.google.gwt.user.client.ui.Composite;

public abstract class KSDatePickerAbstract extends Composite{ 

    public abstract Date getDate();
    
    public abstract void setDate(Date date);
}
