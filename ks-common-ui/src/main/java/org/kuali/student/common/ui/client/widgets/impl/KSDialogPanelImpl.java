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
package org.kuali.student.common.ui.client.widgets.impl;

import org.kuali.student.common.ui.client.widgets.KSDialogPanelAbstract;
import org.kuali.student.common.ui.client.widgets.KSStyles;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;


public class KSDialogPanelImpl extends KSDialogPanelAbstract {
    private final KSResizablePanelImpl resizableContent = GWT.create(KSResizablePanelImpl.class);
    //private final SimplePanel content = new SimplePanel();
    private boolean resizable = true;
    private Label dialogTitleLabel = new Label();
    private final SimplePanel content = new SimplePanel();  
    private final VerticalPanel dialogContainer = new VerticalPanel();
    private final HorizontalPanel headerPanel = new HorizontalPanel();
    private final FocusPanel focusPanel = new FocusPanel();
    private boolean loaded = false;
    private Widget w;
    
    public KSDialogPanelImpl(){
        
        dialogContainer.add(resizableContent);
        super.setWidget(dialogContainer);

        setupDefaultStyle();
        SimpleWindowMover resizeHandler = new SimpleWindowMover();
        focusPanel.addMouseDownHandler(resizeHandler);
        focusPanel.addMouseMoveHandler(resizeHandler);
        focusPanel.addMouseUpHandler(resizeHandler);
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
        loaded = false;
    }
    
    public void show(){
        super.show();
        
        if(resizable){
            if(!loaded){
                resizableContent.setWidget(w);
                loaded = true;
            }
        }else{
            if(!loaded){
                content.setWidget(w);
                loaded = true;
            }
        }

    }
    
    public void center(){
        super.center();
        if(resizable){
            if(!loaded){
                resizableContent.setWidget(w);
                loaded = true;
            }
        }else{
            if(!loaded){
                content.setWidget(w);
                loaded = true;
            }
        }

    }

    
    public void setResizable(boolean resizable){
        this.resizable = resizable;
        if(resizable){
            dialogContainer.remove(content);
            dialogContainer.add(resizableContent);
        }else{
            dialogContainer.remove(resizableContent);
            dialogContainer.add(content);         
        }
        loaded = false;
    }
    
    class SimpleWindowMover implements MouseUpHandler, MouseMoveHandler, MouseDownHandler {
        private int offsetX, offsetY;
        private boolean isMoving = false;

        public SimpleWindowMover() {}

        @Override
        public void onMouseUp(MouseUpEvent event) {
            if (isMoving) {

                isMoving = false;
            }
            DOM.releaseCapture(focusPanel.getElement());
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
