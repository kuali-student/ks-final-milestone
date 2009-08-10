package org.kuali.student.common.ui.client.widgets;

import com.google.gwt.user.client.ui.Label;

/**
 * KSLabel wraps gwt Label.  This class provides most of the same functionality, but sets KS css styles
 * for its default look (for improved browser compatibility and customizability).
 * 
 * @author Kuali Student Team
 *
 */
public class KSLabel extends Label{
    /**
     * Creates an empty label.
     * 
     */
    public KSLabel(){
        super();
    }
    
    /**
     * Creates a label with the specified text.
     * 
     * @param text the new label's text
     */
    public KSLabel(String text){
        super(text);
    }
    
    /**
     * Creates a label with the specified text and sets the word wrap flag.
     * False will disable word wrap, otherwise word wrap will be enabled.
     * 
     * @param text the new label's text
     * @param wordWrap false to disable word wrapping
     */
    public KSLabel(String text, boolean wordWrap){
        super(text, wordWrap);
    }
    
    /**
     * This method sets the default style for labels.
     * 
     */
    private void setupDefaultStyle(){
        addStyleName(KSStyles.KS_LABEL_STYLE);
        //cant think of why you would need a default hover style for labels
    }
}
