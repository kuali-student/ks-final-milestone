package org.kuali.student.common.ui.client.widgets;



import org.kuali.student.common.ui.client.widgets.impl.KSRichEditorImpl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/** 
 * KSRichEditor is the KS default rich text editor.  The editor provides a variety of text formatting options commonly
 * found in traditional text editors.  It also features a toolbar which only shows when the editor is in focus and a pop-out
 * window which allows a user to have more space to work (and can be resized).  Any changes made in the pop-out
 * editor are reflected in the original editor when the user is finished.
 * 
 * TODO implement i18n
 */
public class KSRichEditor extends KSRichEditorAbstract {
    private KSRichEditorAbstract richEditor = GWT.create(KSRichEditorImpl.class);
    
	/**
	 * Creates a new KSRichEditor.
	 * 
	 */
	public KSRichEditor(){
	    init(false);
	    initWidget(richEditor);
	}


	/**
	 * Creates a new KSRichEditor with the if the text editor is used in a popup
	 * flag set.  Text editors that are have this flag set to true do not have a
	 * pop-out option and always have the toolbar showing.
	 * 
	 * @param isUsedInPopup true if this will be used in a popup, false otherwise.
	 */
	public KSRichEditor(boolean isUsedInPopup) {
	    init(true);
	    initWidget(richEditor);
	}
	
	/**
	 * Gets the RichTextArea widget used for text input in this rich editor widget.
	 * 
	 * @see org.kuali.student.common.ui.client.widgets.KSRichEditorAbstract#getRichTextArea()
	 */
	public RichTextArea getRichTextArea(){
	    return richEditor.getRichTextArea();
	}
	
	/**
	 * Get the HTML version of the text input (retains all formatting).
	 * 
	 * @see org.kuali.student.common.ui.client.widgets.KSRichEditorAbstract#getHTML()
	 */
	public String getHTML() {
		return richEditor.getHTML();
	}

	/**
	 * Get the pure text version of the text input (retains NO formatting).
	 * 
	 * @see org.kuali.student.common.ui.client.widgets.KSRichEditorAbstract#getText()
	 */
	public String getText() {
		return richEditor.getText();
	}

	/**
	 * Set the HTML of this rich text editor.
	 * 
	 * @see org.kuali.student.common.ui.client.widgets.KSRichEditorAbstract#setHTML(java.lang.String)
	 */
	public void setHTML(String html) {
	    richEditor.setHTML(html);
	}

	/**
	 * Set the text of this rich text editor (this is text with no formatting).
	 * 
	 * @see org.kuali.student.common.ui.client.widgets.KSRichEditorAbstract#setText(java.lang.String)
	 */
	public void setText(String text) {
	    richEditor.setText(text);
	}


    /**
     * Initialize this text editor with the specified option.
     * 
     * @see org.kuali.student.common.ui.client.widgets.KSRichEditorAbstract#init(boolean)
     */
    protected void init(boolean isUsedInPopup) {
        richEditor.init(isUsedInPopup);
    }
}
