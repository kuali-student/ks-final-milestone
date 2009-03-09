package org.kuali.student.common.ui.client.widgets;

import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;



public class KSToolTip extends PopupPanel {

    //the distance from mouse to the tooltip
    public static final int DEF_MOUSE_OFFSET_X = 20;
    public static final int DEF_MOUSE_OFFSET_Y = 20;

    private KSLabel header;

    private VerticalPanel container = new VerticalPanel();
    private int offsetX;
    private int offsetY;

    public KSToolTip(Widget sender, String headerText, Widget content) {
        super();
        init(headerText, content);
    }

    //initialization
    private void init(String headerText, Widget content){
        this.header = new KSLabel(headerText);
        
        this.setStyleName(KSStyles.KS_TOOLTIP);
        header.setStyleName(KSStyles.KS_TOOLTIP_HEADER);
        content.setStyleName(KSStyles.KS_TOOLTIP_CONTENT);
        
        container.add(header);
        container.add(content);

        setWidget(container);

    }

    public void show(MouseOverEvent event) {

        int x = event.getClientX() + DEF_MOUSE_OFFSET_X;
        int y = event.getClientY() + DEF_MOUSE_OFFSET_Y;
        this.setPopupPosition(x, y);
       
        super.show();
    }

    public void hide() {
        super.hide();
    }
    
    //don't process any event, let parent process it
    public boolean onEventPreview(Event event) {
        return true;  
    }

    public int getOffsetX() {
        return offsetX;
    }

    public void setOffsetX(int offsetX) {
        this.offsetX = offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }

    public void setOffsetY(int offsetY) {
        this.offsetY = offsetY;
    }
}

