package org.kuali.student.common.ui.client.widgets;



import org.kuali.student.common.ui.client.images.TextIcons;
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
 * KS default rich text editor
 * 
 * TODO implement with a clean toolbar and i18n
 */
public class KSRichEditor extends KSRichEditorAbstract {
    private KSRichEditorAbstract richEditor = GWT.create(KSRichEditorImpl.class);
    
	public KSRichEditor(){
	    init(false);
	    initWidget(richEditor);
	}


	public KSRichEditor(boolean isUsedInPopup) {
	    init(true);
	    initWidget(richEditor);
	}
	
	public RichTextArea getRichTextArea(){
	    return richEditor.getRichTextArea();
	}
	
	// delegate methods to RichTextArea
	public String getHTML() {
		return richEditor.getHTML();
	}

	public String getText() {
		return richEditor.getText();
	}

	public void setHTML(String html) {
	    richEditor.setHTML(html);
	}

	public void setText(String text) {
	    richEditor.setText(text);
	}


    protected void init(boolean isUsedInPopup) {
        richEditor.init(isUsedInPopup);
    }
}
