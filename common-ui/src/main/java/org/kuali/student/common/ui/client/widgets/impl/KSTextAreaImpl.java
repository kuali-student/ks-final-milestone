package org.kuali.student.common.ui.client.widgets.impl;

import org.kuali.student.common.ui.client.widgets.KSStyles;
import org.kuali.student.common.ui.client.widgets.KSTextArea;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;

public class KSTextAreaImpl extends KSTextArea{ 

	private TextArea textArea;
	
	public KSTextAreaImpl() {
		super();
		textArea = new TextArea();
		initWidget(textArea);
		setupDefaultStyle();
	}

	private void setupDefaultStyle() {
		addStyleName(KSStyles.KS_TEXTAREA_STYLE);
		
		textArea.addBlurHandler(new BlurHandler(){
			public void onBlur(BlurEvent event) {
				removeStyleName(KSStyles.KS_TEXTAREA_FOCUS_STYLE);
				
			}	
		});	

		textArea.addFocusHandler(new FocusHandler(){
			public void onFocus(FocusEvent event) {
				addStyleName(KSStyles.KS_TEXTAREA_FOCUS_STYLE);

			}		
		});
		
		textArea.addMouseOverHandler(new MouseOverHandler(){
			public void onMouseOver(MouseOverEvent event) {
				addStyleName(KSStyles.KS_TEXTAREA_HOVER_STYLE);
				
			}		
		});
		
		textArea.addMouseOutHandler(new MouseOutHandler(){

			public void onMouseOut(MouseOutEvent event) {
				removeStyleName(KSStyles.KS_TEXTAREA_HOVER_STYLE);
				
			}
			
		});
		
	}

	@Override
	public TextArea getTextArea() {
		return textArea;
	}
	
	

}
