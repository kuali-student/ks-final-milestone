package org.kuali.student.common.ui.client.widgets.impl;

import static org.kuali.student.common.ui.client.widgets.KSStyles.KS_PROGRESS_INDICATOR;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.widgets.KSImage;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSMessages;
import org.kuali.student.common.ui.client.widgets.KSProgressIndicatorAbstract;

import com.google.gwt.user.client.ui.HorizontalPanel;

public class KSProgressIndicatorImpl extends KSProgressIndicatorAbstract {
    
    HorizontalPanel main = new HorizontalPanel();
    private KSImage image = new KSImage("images/twiddler3.gif");
    private KSLabel label = new KSLabel();
        
    public KSProgressIndicatorImpl(){ 
        super();
        main.add(image);
        main.add(label);
        main.addStyleName(KS_PROGRESS_INDICATOR);
        this.initWidget(main);
    }

    @Override
    public void hide() {
        main.setVisible(false);
    }

    @Override
    public void show() {
        main.setVisible(true);
      
    }

    @Override
    public void setText(String labelText) {
        label.setText(labelText);
       
    }
}
