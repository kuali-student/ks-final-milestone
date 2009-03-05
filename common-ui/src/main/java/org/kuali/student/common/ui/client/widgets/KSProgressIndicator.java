package org.kuali.student.common.ui.client.widgets;


import org.kuali.student.common.ui.client.widgets.impl.KSProgressIndicatorImpl;

import com.google.gwt.core.client.GWT;

public class KSProgressIndicator extends KSProgressIndicatorAbstract{ 
    KSProgressIndicatorAbstract indicator = GWT.create(KSProgressIndicatorImpl.class);
    
    public KSProgressIndicator() {
        this.initWidget(indicator);
        
    }

    @Override
    public void hide() {
        indicator.hide();
        
    }

    @Override
    public void show() {
        indicator.show();
        
    }

    @Override
    public void setText(String labelText) {
        indicator.setText(labelText);
        
    }


}
