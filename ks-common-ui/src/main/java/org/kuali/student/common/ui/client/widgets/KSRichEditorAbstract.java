package org.kuali.student.common.ui.client.widgets;





import com.google.gwt.user.client.ui.Composite;
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

    protected abstract void init(boolean isUsedInPopup);
}
