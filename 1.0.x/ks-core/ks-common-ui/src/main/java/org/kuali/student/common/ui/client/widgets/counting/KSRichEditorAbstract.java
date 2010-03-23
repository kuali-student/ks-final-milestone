/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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




import org.kuali.student.common.ui.client.widgets.RichTextEditor;

import com.google.gwt.user.client.ui.RichTextArea;

/** 
 * KS default rich text editor
 * 
 * TODO implement with a clean toolbar and i18n
 */
public abstract class KSRichEditorAbstract extends RichTextEditor {
	
	public abstract RichTextArea getRichTextArea();
	
	// delegate methods to RichTextArea
	public abstract String getHTML();

	public abstract String getText();

	public abstract void setHTML(String html);

	public abstract void setText(String text);

    protected abstract void init(boolean isUsedInPopup,int maxTextLength);

    public abstract int getMaxTextLength();
}
