package org.kuali.student.common.ui.client.widgets;


import org.kuali.student.common.ui.client.widgets.impl.KSRichTextToolbarImpl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.i18n.client.Constants;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ImageBundle;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * KSRichTextToolbar provides an interface for adding different text formatting styles to text in a
 * a RichTextArea.
 * 
 * The class implementation is a modified version of the one found in the GWT Samples.
 * 
 * @author Kuali Student Team
 *
 */
public class KSRichTextToolbar extends KSRichTextToolbarAbstract{ 

    private KSRichTextToolbarAbstract richTextToolbar = GWT.create(KSRichTextToolbarImpl.class);

    /**
     * Creates a new toolbar that drives the given rich text area.
     * 
     * @param richText the rich text area to be controlled
     */
    public KSRichTextToolbar(RichTextArea richText) {
        init(richText);
        initWidget(richTextToolbar);
    }



    /**
     * Returns true if the toolbar is being interacted with, false otherwise.
     * 
     * @see org.kuali.student.common.ui.client.widgets.KSRichTextToolbarAbstract#inUse()
     */
    public boolean inUse(){
        return richTextToolbar.inUse();
    }



    /**
     * Initializes this toolbar with the specified options.
     * 
     * @see org.kuali.student.common.ui.client.widgets.KSRichTextToolbarAbstract#init(com.google.gwt.user.client.ui.RichTextArea)
     */
    @Override
    protected void init(RichTextArea richText) {
        richTextToolbar.init(richText);
        
    }

}
