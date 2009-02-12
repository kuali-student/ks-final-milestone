package org.kuali.student.core.ui.client.widgets;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.Button;

public class KSButton extends Button{
	
	public KSButton(){
		super();
		setupDefaultStyle();
		
	}
	
	public KSButton(String html){
		super(html);
		setupDefaultStyle();
	}
	
	public KSButton(String html,ClickHandler handler){
		super(html, handler);
		setupDefaultStyle();
	}
	
	private void setupDefaultStyle(){
		addStyleName(KSStyles.KS_BUTTON_STYLE);
		
		this.addBlurHandler(new BlurHandler(){
			public void onBlur(BlurEvent event) {
				KSButton.this.removeStyleName(KSStyles.KS_BUTTON_FOCUS_STYLE);
				
			}	
		});	

		this.addFocusHandler(new FocusHandler(){
			public void onFocus(FocusEvent event) {
				KSButton.this.addStyleName(KSStyles.KS_BUTTON_FOCUS_STYLE);

			}		
		});
		
		this.addMouseOverHandler(new MouseOverHandler(){
			public void onMouseOver(MouseOverEvent event) {
				KSButton.this.addStyleName(KSStyles.KS_BUTTON_HOVER_STYLE);
				
			}		
		});
		
		this.addMouseOutHandler(new MouseOutHandler(){

			public void onMouseOut(MouseOutEvent event) {
				KSButton.this.removeStyleName(KSStyles.KS_BUTTON_HOVER_STYLE);
				
			}
			
		});
		
		this.addClickHandler(new ClickHandler(){

			public void onClick(ClickEvent event) {
				KSButton.this.removeStyleName(KSStyles.KS_BUTTON_HOVER_STYLE);
			}
			
		});
		
		
	}
	
	
	
}
