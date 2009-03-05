package org.kuali.student.common.ui.client.widgets;


import com.google.gwt.user.client.ui.Composite;

public abstract class KSProgressIndicatorAbstract extends Composite{ 

    public abstract void show();
    
    public abstract void hide();
    
    public abstract void setText(String labelText);
    
}
