package org.kuali.student.commons.ui.widgets;

import java.util.ArrayList;
import java.util.List;


import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;

public class AccordionPanel extends Composite {
    FlowPanel content = new FlowPanel();
    List<AccordionTitleBar> titleBarList = new ArrayList<AccordionTitleBar>();
    List<Widget> widgetList = new ArrayList<Widget>();

    public AccordionPanel() {
        content.setStyleName("accordionPanel");
        initWidget(content);
    }

    public void addPanel(String title, Widget com) {
        AccordionTitleBar titleBar = new AccordionTitleBar(title);
        titleBarList.add(titleBar);
        ScrollPanel scrollPane = new ScrollPanel(com);
        widgetList.add(scrollPane);
        content.add(titleBar);
        titleBar.addClickListener(new ClickListener() {
            public void onClick(Widget arg0) {
                doTitleBarClick(arg0);
            }
        });
    }
    private void doTitleBarClick(Widget arg0) {
        for (Widget w : widgetList) {
            content.remove(w);
        }
        int i = content.getWidgetIndex(arg0);
        Widget toBeAdded = widgetList.get(i);
        toBeAdded.setHeight(getHeightLeft()+"px");
        content.insert(toBeAdded, i + 1);
    }
    private int getHeightLeft(){
        return content.getOffsetHeight() - titleBarList.size()*22;
    }
    class AccordionTitleBar extends Label {
        AccordionTitleBar(String name) {
            super(name);
            setStyleName("accordionTitleBar");
        }
    }
}