package org.kuali.student.common.ui.client.widgets;

import com.google.gwt.user.client.ui.Composite;

public abstract class RichTextEditor extends Composite{
    public abstract String getText();
    public abstract void setText(String text);
    public abstract String getHTML();
    public abstract void setHTML(String html);
}
