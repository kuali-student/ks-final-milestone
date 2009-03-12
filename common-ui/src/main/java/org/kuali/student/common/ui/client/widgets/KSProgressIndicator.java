package org.kuali.student.common.ui.client.widgets;


import org.kuali.student.common.ui.client.widgets.impl.KSProgressIndicatorImpl;

import com.google.gwt.core.client.GWT;

/**
 * KSProgressIndicator can be shown to indicate processing of some request.  It can then be hidden again, to
 * indicate process completion.
 * 
 * The indicator contains a twiddler/spinner image and a text label.
 * 
 * @author Kuali Student Team
 *
 */
public class KSProgressIndicator extends KSProgressIndicatorAbstract{ 
    KSProgressIndicatorAbstract indicator = GWT.create(KSProgressIndicatorImpl.class);
    
    public KSProgressIndicator() {
        this.initWidget(indicator);
        
    }

    /**
     * Hides the progress indicator.
     * 
     * @see org.kuali.student.common.ui.client.widgets.KSProgressIndicatorAbstract#hide()
     */
    @Override
    public void hide() {
        indicator.hide();
        
    }

    /**
     * Shows the progress indicator.
     * 
     * @see org.kuali.student.common.ui.client.widgets.KSProgressIndicatorAbstract#show()
     */
    @Override
    public void show() {
        indicator.show();
        
    }

    /**
     * Sets the text for the progress indicator, explaining what is being processed.
     * 
     * @see org.kuali.student.common.ui.client.widgets.KSProgressIndicatorAbstract#setText(java.lang.String)
     */
    @Override
    public void setText(String labelText) {
        indicator.setText(labelText);
        
    }


}
