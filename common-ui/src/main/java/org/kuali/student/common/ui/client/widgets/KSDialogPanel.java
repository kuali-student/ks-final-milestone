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
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class KSDialogPanel extends KSPopupPanel{
    private final KSResizablePanel resizableContent = new KSResizablePanel();
    //private final SimplePanel content = new SimplePanel();
    private boolean resizable = false;
    private Label dialogTitleLabel = new Label();
    private final SimplePanel content = new SimplePanel();	
    private final VerticalPanel dialogContainer = new VerticalPanel();
	private final HorizontalPanel headerPanel = new HorizontalPanel();
	private final FocusPanel focusPanel = new FocusPanel();
	private Widget w;
	
	public KSDialogPanel(){
		
        dialogContainer.add(resizableContent);
        super.setWidget(dialogContainer);

		setupDefaultStyle();
        SimpleWindowMover resizeHandler = new SimpleWindowMover();
        focusPanel.addMouseDownHandler(resizeHandler);
        focusPanel.addMouseMoveHandler(resizeHandler);
        focusPanel.addMouseOutHandler(resizeHandler);
        focusPanel.addMouseUpHandler(resizeHandler);
        focusPanel.addMouseOverHandler(resizeHandler);
	}
	
	
	public void setHeader(String headerText){
		headerPanel.setHorizontalAlignment(HorizontalPanel.ALIGN_CENTER);
		dialogTitleLabel = new Label(headerText, false);
		focusPanel.add(dialogTitleLabel);
		headerPanel.add(focusPanel);
		dialogContainer.insert(headerPanel, 0);
	}
	
	public boolean removeHeader(){
		return dialogContainer.remove(headerPanel);
	}
	
	private void setupDefaultStyle(){
		headerPanel.addStyleName(KSStyles.KS_POPUP_HEADER);
		
	}
	
    public void setWidget(Widget w) {
    	this.w = w;
            //resizableContent.setWidget(w);
        	//content.setWidget(w);
    }
    
    public void show(){
    	super.show();
    	if(resizable){
    		resizableContent.setWidget(w);
    	}else{
    		 content.setWidget(w);
    	}
    }
    
    public void center(){
    	super.center();
    	if(resizable){
    		resizableContent.setWidget(w);
    	}else{
    		 content.setWidget(w);
    	}
    }

    
    public void setResizable(boolean resizable){
    	this.resizable = resizable;
    	if(resizable){
    		//dialogContainer.remove(content);
    		dialogContainer.add(resizableContent);
        }else{
        	//dialogContainer.remove(resizableContent);
    		dialogContainer.add(content);         
        }
        
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
            DOM.releaseCapture(focusPanel.getElement());
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

            DOM.setCapture(focusPanel.getElement());
        }
    }
    

	

	

	
}
