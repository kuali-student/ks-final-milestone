package org.kuali.student.common.ui.client.widgets.impl;

import org.kuali.student.common.ui.client.widgets.KSStyles;
import org.kuali.student.common.ui.client.widgets.KSTextBox;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.TextBox;

public class KSTextBoxImpl extends KSTextBox{ 

	private TextBox textBox;
	
	public KSTextBoxImpl() {
		super();
		initWidget(textBox);
		setupDefaultStyle();
	}

	
	private void setupDefaultStyle() {
		addStyleName(KSStyles.KS_TEXTBOX_STYLE);
		
		textBox.addBlurHandler(new BlurHandler(){
			public void onBlur(BlurEvent event) {
				removeStyleName(KSStyles.KS_TEXTBOX_FOCUS_STYLE);
				
			}	
		});	

		textBox.addFocusHandler(new FocusHandler(){
			public void onFocus(FocusEvent event) {
				addStyleName(KSStyles.KS_TEXTBOX_FOCUS_STYLE);

			}		
		});
		
		textBox.addMouseOverHandler(new MouseOverHandler(){
			public void onMouseOver(MouseOverEvent event) {
				addStyleName(KSStyles.KS_TEXTBOX_HOVER_STYLE);
				
			}		
		});
		
		textBox.addMouseOutHandler(new MouseOutHandler(){

			public void onMouseOut(MouseOutEvent event) {
				removeStyleName(KSStyles.KS_TEXTBOX_HOVER_STYLE);
				
			}
			
		});
		
	}

	@Override
	public TextBox getTextBox() {
		return textBox;
	}
}
