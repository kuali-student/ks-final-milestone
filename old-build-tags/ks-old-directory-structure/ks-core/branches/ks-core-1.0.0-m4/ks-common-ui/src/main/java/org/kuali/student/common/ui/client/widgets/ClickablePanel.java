package org.kuali.student.common.ui.client.widgets;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasAllFocusHandlers;
import com.google.gwt.event.dom.client.HasAllKeyHandlers;
import com.google.gwt.event.dom.client.HasAllMouseHandlers;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
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
import com.google.gwt.event.dom.client.MouseWheelEvent;
import com.google.gwt.event.dom.client.MouseWheelHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class ClickablePanel extends SimplePanel implements HasAllMouseHandlers, HasClickHandlers, HasAllKeyHandlers{
	  
	public ClickablePanel(Widget child){
		  this();
		  setWidget(child);
	  }
	
	  public ClickablePanel() {
		  super();
	  }

	  public HandlerRegistration addClickHandler(ClickHandler handler) {
		    return addDomHandler(handler, ClickEvent.getType());
	  }
	  
	  public HandlerRegistration addKeyDownHandler(KeyDownHandler handler) {
	    return addDomHandler(handler, KeyDownEvent.getType());
	  }

	  public HandlerRegistration addKeyPressHandler(KeyPressHandler handler) {
	    return addDomHandler(handler, KeyPressEvent.getType());
	  }

	  public HandlerRegistration addKeyUpHandler(KeyUpHandler handler) {
	    return addDomHandler(handler, KeyUpEvent.getType());
	  }

	  public HandlerRegistration addMouseDownHandler(MouseDownHandler handler) {
	    return addDomHandler(handler, MouseDownEvent.getType());
	  }
	  
	  public HandlerRegistration addMouseMoveHandler(MouseMoveHandler handler) {
	    return addDomHandler(handler, MouseMoveEvent.getType());
	  }

	  public HandlerRegistration addMouseOutHandler(MouseOutHandler handler) {
	    return addDomHandler(handler, MouseOutEvent.getType());
	  }

	  public HandlerRegistration addMouseOverHandler(MouseOverHandler handler) {
	    return addDomHandler(handler, MouseOverEvent.getType());
	  }

	  public HandlerRegistration addMouseUpHandler(MouseUpHandler handler) {
	    return addDomHandler(handler, MouseUpEvent.getType());
	  }

	  public HandlerRegistration addMouseWheelHandler(MouseWheelHandler handler) {
	    return addDomHandler(handler, MouseWheelEvent.getType());
	  }
}
