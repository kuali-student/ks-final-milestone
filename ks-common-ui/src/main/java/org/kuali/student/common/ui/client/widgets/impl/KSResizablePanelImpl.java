/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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

import org.kuali.student.common.ui.client.widgets.KSResizablePanelAbstract;

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
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventPreview;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.MouseListenerAdapter;
import com.google.gwt.user.client.ui.Widget;

public class KSResizablePanelImpl  extends KSResizablePanelAbstract { 

    Image rightBottomCorner = new Image("images/corner3.png");
    //Image bottom = new Image();
    //Image right = new Image();
    Widget widget;

    Widget mask = new Image("images/transparent.gif");
    private final static int Right = 0;
    private final static int Bottom = 1;
    private final static int BottomRight = 2;
    
    private int handlerOffset = 14;
    public KSResizablePanelImpl() {
        //super.setStyleName("resizableComponent");

        mask.setStyleName("KS-ResizablePanel-Mask");
        
        rightBottomCorner.setStyleName("KS-ResizablePanel-RightBottomResizableHandler");
        rightBottomCorner.setPixelSize(16, 16);
      //  bottom.setStyleName("KS-ResizableComposite-BottomResizableHandler");
        //right.setStyleName("KS-ResizableComposite-RightResizableHandler");

        super.add(rightBottomCorner);
        //super.add(bottom);
        //super.add(right);
        
        MouseHandler resizeHandler = new MouseHandler(rightBottomCorner, BottomRight);
        rightBottomCorner.addMouseDownHandler(resizeHandler);
        rightBottomCorner.addMouseMoveHandler(resizeHandler);
        rightBottomCorner.addMouseOutHandler(resizeHandler);
        rightBottomCorner.addMouseUpHandler(resizeHandler);
        rightBottomCorner.addMouseOverHandler(resizeHandler);
        rightBottomCorner.addMouseListener(new PreventDefaultDuringDragging());        
        
        DeferredCommand.addCommand(new Command() {
            public void execute() {
                reposition();
            }
        });
    }

    private void reposition(){
        int w = getOffsetWidth();
        int h = getOffsetHeight();
    
        setWidgetPosition(rightBottomCorner, w-handlerOffset, h-handlerOffset);

        if(widget != null && super.getWidgetIndex(widget)!= -1 ){
            setWidgetPosition(widget, 0, 0);
        }
        if(super.getWidgetIndex(mask)!= -1 ){
            setWidgetPosition(mask, 0, 0);
        }
    }
    @Override
    public void setWidget(final Widget w){
        widget = w;
        super.add(w);
        
        DeferredCommand.addCommand(new Command() {
            public void execute() {
                if(w.getOffsetWidth()!= 0 && w.getOffsetHeight()!= 0){
                    setNewSize(w.getOffsetWidth(),w.getOffsetHeight()+handlerOffset);
                }
                
            }
        });
    }
    @Override
    public void setNewSize(int w, int h){
        setPixelSize(w, h);// (2*handlerOffset));
        if(widget!= null){
            widget.setPixelSize(w, h-handlerOffset);    
        }
        if(super.getWidgetIndex(mask)!= -1 ){
            mask.setPixelSize(w, h);
        }
        reposition();
    }
    class MouseHandler implements MouseUpHandler, MouseOverHandler, MouseOutHandler, MouseMoveHandler, MouseDownHandler {
        private int xoffset, yoffset, h, w;
        private boolean isResizing;

        Widget dragger;
        int direction;

        public MouseHandler(Widget dragger, int direction) {
            this.dragger = dragger;
            this.direction = direction;
        }

        public void onMouseMove(MouseMoveEvent event) {
            if (isResizing) {
                int newW = w + event.getClientX() - xoffset;
                int newH = h + event.getClientY() - yoffset;
                if (this.direction == KSResizablePanelImpl.BottomRight) {
                    setNewSize(newW, newH);
                } else if (this.direction == KSResizablePanelImpl.Bottom) {
                    setNewSize(w, newH);
                } else if (this.direction == KSResizablePanelImpl.Right) {
                    setNewSize(newW, h);
                }
            }
        }

        public void onMouseDown(MouseDownEvent event) {
            w = getOffsetWidth();
            h = getOffsetHeight();
            xoffset = event.getClientX();
            yoffset = event.getClientY();
            isResizing = true;

            DOM.setCapture(dragger.getElement());
            add(mask);
        }

        public void onMouseOut(MouseOutEvent event) {
        }

        public void onMouseUp(MouseUpEvent event) {
            isResizing = false;
            DOM.releaseCapture(dragger.getElement());
            remove(mask);
        }

        public void onMouseOver(MouseOverEvent event) {}
    }
    class PreventDefaultDuringDragging extends MouseListenerAdapter {
        private final PreventEventPreview preview = new PreventEventPreview();

        public void onMouseEnter(Widget sender) {
            DOM.addEventPreview(preview);
        }

        public void onMouseLeave(Widget sender) {
            DOM.removeEventPreview(preview);
        }

        public void removeEventPreview() {
            DOM.removeEventPreview(preview);
        }

        private class PreventEventPreview implements EventPreview {
            public boolean onEventPreview(Event event) {
                switch (DOM.eventGetType(event)) {
                    case Event.ONMOUSEDOWN:
                    case Event.ONMOUSEMOVE:
                   // case Event.ONMOUSEUP:
                        DOM.eventPreventDefault(event);
                }
                return true;
            }
        }
    }
}
