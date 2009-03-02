package org.kuali.student.common.ui.client.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class KSDialogPanel extends KSPopupPanel{
    private final KSResizablePanel resizePanel = new KSResizablePanel();
    VerticalPanel dialogPanel = new VerticalPanel();
    SimplePanel contentPanel = new SimplePanel();
    SimplePanel buttonPanel = new SimplePanel();
    Label dialogTitleLabel = new Label();

    public KSDialogPanel() {
        //dialogPanel.setPixelSize(200, 200);
        dialogPanel.add(dialogTitleLabel);
        dialogPanel.add(contentPanel);
        dialogPanel.add(buttonPanel);
        
        dialogTitleLabel.setStyleName(KSStyles.KS_DIALOG_CAPTION);
        resizePanel.setWidget(dialogPanel);
        super.setWidget(resizePanel);
        // super.

        SimpleWindowMover resizeHandler = new SimpleWindowMover();
        dialogTitleLabel.addMouseDownHandler(resizeHandler);
        dialogTitleLabel.addMouseMoveHandler(resizeHandler);
        dialogTitleLabel.addMouseOutHandler(resizeHandler);
        dialogTitleLabel.addMouseUpHandler(resizeHandler);
        dialogTitleLabel.addMouseOverHandler(resizeHandler);
    }
    public void setResizable(boolean resizable){
        if(resizable){
            resizePanel.setWidget(dialogPanel);
            super.setWidget(resizePanel);
        }else{
            super.setWidget(dialogPanel);
            
        }
    }
    public void setHeader(String title) {
        dialogTitleLabel.setText(title);
    }

    public void setContent(Widget w) {
        contentPanel.setWidget(w);
    }
    public void setButtonPanel(Widget w){
        buttonPanel.setWidget(w);
    }

    class SimpleWindowMover implements MouseUpHandler, MouseOverHandler, MouseOutHandler, MouseMoveHandler, MouseDownHandler {
        private int offsetX, offsetY;
        private boolean isMoving = false;

        public SimpleWindowMover() {}

        @Override
        public void onMouseUp(MouseUpEvent event) {
            if (isMoving) {
                // dialogTitleLabel.removeStyleName("");
                isMoving = false;
            }
            DOM.releaseCapture(dialogTitleLabel.getElement());
        }

        @Override
        public void onMouseOver(MouseOverEvent event) {}

        @Override
        public void onMouseOut(MouseOutEvent event) {
        // dialogTitleLabel.removeStyleName("MoveCursor");
        }

        @Override
        public void onMouseMove(MouseMoveEvent event) {
            if (isMoving) {
                setLocation(event.getClientX() - offsetX, event.getClientY() - offsetY);
            }
        }

        @Override
        public void onMouseDown(MouseDownEvent event) {
            offsetX = event.getClientX() - getX();
            offsetY = event.getClientY() - getY();

            isMoving = true;

            DOM.setCapture(dialogTitleLabel.getElement());
        }
    }
	/*
    private final SimplePanel content = new SimplePanel();	
    private final VerticalPanel dialogContainer = new VerticalPanel();
	private final HorizontalPanel headerPanel = new HorizontalPanel();
	
	public KSDialogPanel(){
		
        dialogContainer.add(content);
        super.setWidget(dialogContainer);

		setupDefaultStyle();
	}
	
	
	public void addHeader(String headerText){
		headerPanel.setHorizontalAlignment(HorizontalPanel.ALIGN_CENTER);
		KSLabel kSLabel = new KSLabel(headerText, false);
		headerPanel.add(kSLabel);
		dialogContainer.insert(headerPanel, 0);
	}
	
	public boolean removeHeader(){
		return dialogContainer.remove(headerPanel);
	}
	
    public void setWidget(Widget w) {
        content.setWidget(w);
    }
	
	private void setupDefaultStyle(){
		headerPanel.addStyleName(KSStyles.KS_POPUP_HEADER);
	}
	*/
}
