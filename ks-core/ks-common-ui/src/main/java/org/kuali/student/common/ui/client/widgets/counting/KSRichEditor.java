/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.common.ui.client.widgets.counting;


import org.kuali.student.common.ui.client.widgets.counting.impl.KSRichEditorImpl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.RichTextArea;

/** 
 * KSRichEditor is the KS default rich text editor.  The editor provides a variety of text formatting options commonly
 * found in traditional text editors.  It also features a toolbar which only shows when the editor is in focus and a pop-out
 * window which allows a user to have more space to work (and can be resized).  Any changes made in the pop-out
 * editor are reflected in the original editor when the user is finished.
 * 
 * TODO implement i18n
 */
public class KSRichEditor extends KSRichEditorAbstract implements HasText{
    private KSRichEditorAbstract richEditor = GWT.create(KSRichEditorImpl.class);
    
	/**
	 * Creates a new KSRichEditor.
	 * 
	 */
	public KSRichEditor(){
	    this(false,256);
	}


	/**
	 * Creates a new KSRichEditor with the if the text editor is used in a popup
	 * flag set.  Text editors that are have this flag set to true do not have a
	 * pop-out option and always have the toolbar showing.
	 * 
	 * @param isUsedInPopup true if this will be used in a popup, false otherwise.
	 */
	public KSRichEditor(boolean isUsedInPopup,int maxTextLength) {
	    init(isUsedInPopup,maxTextLength);
	    initWidget(richEditor);
	}
	
	/**
	 * Gets the RichTextArea widget used for text input in this rich editor widget.
	 * 
	 * @return the RichTextArea used in this editor
	 */
	public RichTextArea getRichTextArea(){
	    return richEditor.getRichTextArea();
	}
	
	/**
	 * Get the HTML version of the text input (retains all formatting).
	 * 
	 * @return the HTML version of the input text (with formatting)
	 */
	public String getHTML() {
		return richEditor.getHTML();
	}

	/**
	 * Get the plain text version of the text input (retains NO formatting).
	 * 
	 * @return the plain text version of the input text (no formatting)
	 */
	public String getText() {
		return richEditor.getText();
	}

	/**
	 * Set the HTML of this rich text editor.
	 * 
	 * @param html the HTML to set this editor to
	 */
	public void setHTML(String html) {
	    richEditor.setHTML(html);
	}

	/**
	 * Set the text of this rich text editor (this is text with no formatting).
	 * 
	 * @param the plain text to set this editor to
	 */
	public void setText(String text) {
	    richEditor.setText(text);
	}


    /**
     * Initialize this text editor with the specified option.
     * 
     */
    protected void init(boolean isUsedInPopup,int maxTextLength) {
        richEditor.init(isUsedInPopup,maxTextLength);
    }


    public int getMaxTextLength() {
        return richEditor.getMaxTextLength();
    }


	@Override
	public HandlerRegistration addBlurHandler(BlurHandler handler) {
		return richEditor.addBlurHandler(handler);
	}


	@Override
	public HandlerRegistration addFocusHandler(FocusHandler handler) {
		return richEditor.addFocusHandler(handler);
	}
}
