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

package org.kuali.student.common.ui.client.widgets.impl;



import org.kuali.student.common.ui.client.widgets.KSRichEditorAbstract;

import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.RichTextArea;

/** 
 * Placeholder widget for future real rich text editor implementation
 * 
 */
public class KSRichEditorImpl extends KSRichEditorAbstract {
	private final RichTextArea textArea = new RichTextArea();

	@Override
	public String getHTML() {
		return textArea.getHTML();
	}

	@Override
	public RichTextArea getRichTextArea() {
		return textArea;
	}

	@Override
	public String getText() {
		return textArea.getText();
	}

	@Override
	protected void init() {
		this.initWidget(textArea);
	}

	@Override
	public void setHTML(String html) {
		textArea.setHTML(html);
	}

	@Override
	public void setStyleName(String style) {
		textArea.setStyleName(style);
		
	}

	@Override
	public void setText(String text) {
		textArea.setText(text);
	}

	@Override
	public HandlerRegistration addBlurHandler(BlurHandler handler) {
		return textArea.addBlurHandler(handler);
	}

	@Override
	public HandlerRegistration addFocusHandler(FocusHandler handler) {
		return textArea.addFocusHandler(handler);
	}

	
	
}
