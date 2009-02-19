package org.kuali.student.common.ui.client.widgets.impl;

import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSStyles;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Button;

public class KSButtonImpl extends KSButton{
	
	private Button button;
	@Override
	public void init(String html) {
		button = new Button(html);
		
	}

	@Override
	public void init(String html, ClickHandler handler) {
		button = new Button(html, handler);
		
	}
	
	private void setupDefaultStyle(){
		button.addStyleName(KSStyles.KS_BUTTON_STYLE);
		
		button.addBlurHandler(new BlurHandler(){
			public void onBlur(BlurEvent event) {
				button.removeStyleName(KSStyles.KS_BUTTON_FOCUS_STYLE);
				
			}	
		});	

		button.addFocusHandler(new FocusHandler(){
			public void onFocus(FocusEvent event) {
				button.addStyleName(KSStyles.KS_BUTTON_FOCUS_STYLE);

			}		
		});
		
		button.addMouseOverHandler(new MouseOverHandler(){
			public void onMouseOver(MouseOverEvent event) {
				button.addStyleName(KSStyles.KS_BUTTON_HOVER_STYLE);
				
			}		
		});
		
		button.addMouseOutHandler(new MouseOutHandler(){

			public void onMouseOut(MouseOutEvent event) {
				button.removeStyleName(KSStyles.KS_BUTTON_HOVER_STYLE);
				button.removeStyleName(KSStyles.KS_BUTTON_CLICK_STYLE);
			}
			
		});
		
		button.addClickHandler(new ClickHandler(){

			public void onClick(ClickEvent event) {
				button.removeStyleName(KSStyles.KS_BUTTON_HOVER_STYLE);
			}
			
		});
		
		button.addMouseDownHandler(new MouseDownHandler(){

			public void onMouseDown(MouseDownEvent event) {
				button.addStyleName(KSStyles.KS_BUTTON_CLICK_STYLE);
				
			}
		});
		
		button.addMouseUpHandler(new MouseUpHandler(){

			public void onMouseUp(MouseUpEvent event) {
				button.removeStyleName(KSStyles.KS_BUTTON_CLICK_STYLE);
				
			}
		});
		
		button.addKeyDownHandler(new KeyDownHandler(){

			public void onKeyDown(KeyDownEvent event) {
				if(event.getNativeKeyCode() == KeyCodes.KEY_ENTER){
					button.addStyleName(KSStyles.KS_BUTTON_CLICK_STYLE);
				}
			}
			
		});
		
		button.addKeyUpHandler(new KeyUpHandler(){

			public void onKeyUp(KeyUpEvent event) {
				if(event.getNativeKeyCode() == KeyCodes.KEY_ENTER){
					button.removeStyleName(KSStyles.KS_BUTTON_CLICK_STYLE);
				}
				
			}
			
		});
		
	}


	
	
	
}
