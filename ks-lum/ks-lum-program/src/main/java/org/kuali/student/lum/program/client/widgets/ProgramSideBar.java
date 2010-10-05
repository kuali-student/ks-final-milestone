package org.kuali.student.lum.program.client.widgets;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author Igor
 */
public class ProgramSideBar extends Composite {

    private VerticalPanel content = new VerticalPanel();

    public ProgramSideBar() {
        initWidget(content);
        setStyles();
        buildLayout();
    }

    private void buildLayout() {
    }

    private void setStyles() {
        content.addStyleName("sideBar");
    }
}
