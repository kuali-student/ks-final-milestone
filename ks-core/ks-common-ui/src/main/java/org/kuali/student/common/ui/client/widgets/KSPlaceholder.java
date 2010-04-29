package org.kuali.student.common.ui.client.widgets;

import com.google.gwt.user.client.ui.Label;

public class KSPlaceholder extends Label {
    public KSPlaceholder() {
        super("[PLACEHOLDER]");
        setupDefaultStyle();
    }
    
    /**
     * This method sets the default style for placeholders.
     * 
     */
    private void setupDefaultStyle(){
        addStyleName(KSStyles.KS_PLACEHOLDER_STYLE);
    }
}
