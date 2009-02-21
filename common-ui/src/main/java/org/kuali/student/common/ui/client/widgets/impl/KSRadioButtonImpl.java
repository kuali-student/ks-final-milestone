package org.kuali.student.common.ui.client.widgets.impl;

import org.kuali.student.common.ui.client.widgets.KSRadioButton;
import org.kuali.student.common.ui.client.widgets.KSStyles;

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

public class KSRadioButtonImpl extends KSRadioButton {

	private RadioButton radioButton;
	
	public KSRadioButtonImpl() {
	}
	
	@Override
	public void init(String name, String label, boolean asHTML) {
		if(label != null) {
			radioButton = new RadioButton(name, label, asHTML);
		} else {
			radioButton = new RadioButton(name);
		}
		initWidget(radioButton);
		setupDefaultStyle();
	}

	private void setupDefaultStyle() {
		addStyleName(KSStyles.KS_RADIO_STYLE);
		
		radioButton.addBlurHandler(new BlurHandler(){
			public void onBlur(BlurEvent event) {
				removeStyleName(KSStyles.KS_RADIO_FOCUS_STYLE);
				
			}	
		});	

		radioButton.addFocusHandler(new FocusHandler(){
			public void onFocus(FocusEvent event) {
				addStyleName(KSStyles.KS_RADIO_FOCUS_STYLE);

			}		
		});
		
		radioButton.addMouseOverHandler(new MouseOverHandler(){
			public void onMouseOver(MouseOverEvent event) {
				addStyleName(KSStyles.KS_RADIO_HOVER_STYLE);
				
			}		
		});
		
		radioButton.addMouseOutHandler(new MouseOutHandler(){

			public void onMouseOut(MouseOutEvent event) {
				removeStyleName(KSStyles.KS_RADIO_HOVER_STYLE);
				
			}
			
		});
		
		radioButton.addClickHandler(new ClickHandler(){

			public void onClick(ClickEvent event) {
				if(radioButton.getValue()){
					addStyleName(KSStyles.KS_RADIO_CHECKED_STYLE);
				}
				else{
					removeStyleName(KSStyles.KS_RADIO_CHECKED_STYLE);
				}
				
			}
			
		});
		
	}

	@Override
	public RadioButton getRadioButton() {
		return radioButton;
	}
	

}
