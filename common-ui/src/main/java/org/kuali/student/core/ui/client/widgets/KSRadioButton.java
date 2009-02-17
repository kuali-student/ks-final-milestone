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
import com.google.gwt.user.client.ui.RadioButton;

public class KSRadioButton extends RadioButton{

	public KSRadioButton(String name, String label, boolean asHTML) {
		super(name, label, asHTML);
		setupDefaultStyle();
	}

	public KSRadioButton(String name, String label) {
		super(name, label);
		setupDefaultStyle();
	}

	public KSRadioButton(String name) {
		super(name);
		setupDefaultStyle();
	}

	private void setupDefaultStyle() {
		addStyleName(KSStyles.KS_RADIO_STYLE);
		
		this.addBlurHandler(new BlurHandler(){
			public void onBlur(BlurEvent event) {
				KSRadioButton.this.removeStyleName(KSStyles.KS_RADIO_FOCUS_STYLE);
				
			}	
		});	

		this.addFocusHandler(new FocusHandler(){
			public void onFocus(FocusEvent event) {
				KSRadioButton.this.addStyleName(KSStyles.KS_RADIO_FOCUS_STYLE);

			}		
		});
		
		this.addMouseOverHandler(new MouseOverHandler(){
			public void onMouseOver(MouseOverEvent event) {
				KSRadioButton.this.addStyleName(KSStyles.KS_RADIO_HOVER_STYLE);
				
			}		
		});
		
		this.addMouseOutHandler(new MouseOutHandler(){

			public void onMouseOut(MouseOutEvent event) {
				KSRadioButton.this.removeStyleName(KSStyles.KS_RADIO_HOVER_STYLE);
				
			}
			
		});
		
		this.addClickHandler(new ClickHandler(){

			public void onClick(ClickEvent event) {
				if(KSRadioButton.this.getValue()){
					KSRadioButton.this.addStyleName(KSStyles.KS_RADIO_CHECKED_STYLE);
				}
				else{
					KSRadioButton.this.removeStyleName(KSStyles.KS_RADIO_CHECKED_STYLE);
				}
				
			}
			
		});
		
	}
	

}
