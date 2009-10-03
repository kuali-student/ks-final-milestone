package org.kuali.student.common.ui.client.widgets;

import com.google.gwt.event.dom.client.HasBlurHandlers;
import com.google.gwt.event.dom.client.HasFocusHandlers;
import com.google.gwt.user.client.ui.Composite;

public abstract class RichTextEditor extends Composite implements HasBlurHandlers, HasFocusHandlers{
    public abstract String getText();
    public abstract void setText(String text);
    public abstract String getHTML();
    public abstract void setHTML(String html);
}
