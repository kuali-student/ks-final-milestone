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

package org.kuali.student.common.ui.client.widgets;

import org.kuali.student.common.ui.client.widgets.impl.KSButtonImpl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.shared.HandlerRegistration;

/**
 * KSButton wraps gwt Button.  This class provides most of the same functionality, but sets KS css styles
 * for its default look and a variety of button events (for improved browser compatibility and customizability).
 * An image can be also be added to a KSButton (an improvement over gwt Button).
 * 
 * @author Kuali Student Team
 *
 */
public class KSButton extends KSButtonAbstract{
	
	KSButtonAbstract button = GWT.create(KSButtonImpl.class);
	
	public boolean isEnabled() {
		return button.isEnabled();
	}

	public void setEnabled(boolean enabled) {
		button.setEnabled(enabled);
	}
	
	public KSButton(){
		this.init();
		this.initWidget(button);
	}
	
	public KSButton(String text, ClickHandler handler){
		this.init(text, handler);
		this.initWidget(button);
	}	
	
	public KSButton(String text){
		this.init(text);
		this.initWidget(button);
	}
	
	public KSButton(String text, ButtonStyle style){
		this.init(text, style);
		this.initWidget(button);
	}

    public KSButton(String text, ButtonStyle style, ClickHandler handler){
        this.init(text, style, handler);
        this.initWidget(button);
    }   
    
    public void setText(String text){
        button.setText(text);
    }
    
	@Override
	public HandlerRegistration addClickHandler(ClickHandler handler) {
		return button.addClickHandler(handler);
	}

	@Override
	public HandlerRegistration addMouseOverHandler(MouseOverHandler handler) {
		return button.addMouseOverHandler(handler);
	}

	@Override
	public HandlerRegistration addMouseOutHandler(MouseOutHandler handler) {
		return button.addMouseOutHandler(handler);
	}

    @Override
    public void init() {
        button.init();
    }	
	
	@Override
	public void init(String text) {
		button.init(text);
	}

	@Override
	public void init(String text, ButtonStyle style) {
		button.init(text, style);		
	}

    public void init(String text, ButtonStyle style, ClickHandler handler) {
        button.init(text, style, handler);       
    }	
	
	@Override
	public void init(String text, ClickHandler handler) {
		button.init(text, handler);
	}
	
    @Override
    public void addStyleName(String style) {
        button.addStyleName(style);
    }

    @Override
    public void removeStyleName(String style) {
        button.removeStyleName(style);
    }

    @Override
    public void setStyleName(String style) {
        button.setStyleName(style);
    }	
}
