package org.kuali.student.common.ui.client.widgets;

import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Widget;

public abstract class KSResizablePanelAbstract extends AbsolutePanel {

    public KSResizablePanelAbstract() {
        super();
    }

    public KSResizablePanelAbstract(Element elem) {
        super(elem);
    }

    public abstract void setNewSize(int w, int h);

    public abstract void setWidget(final Widget w);

}