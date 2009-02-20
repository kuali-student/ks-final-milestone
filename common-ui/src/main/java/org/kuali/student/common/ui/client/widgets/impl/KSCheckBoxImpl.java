package org.kuali.student.common.ui.client.widgets.impl;

import org.kuali.student.common.ui.client.widgets.KSCheckBox;
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
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;


public class KSCheckBoxImpl extends KSCheckBox{
	
	private CheckBox checkBox;
	
	public KSCheckBoxImpl(){
		super();
		
	}
	
	@Override
	public void init(String label){
	    if(label != null) {
	    	checkBox = new CheckBox(label);
		} else {
			checkBox = new CheckBox();
		}
	    this.initWidget(checkBox);
		setupDefaultStyle();
	}
	
	private void setupDefaultStyle(){
		addStyleName(KSStyles.KS_CHECKBOX_STYLE);
		
		checkBox.addBlurHandler(new BlurHandler(){
			public void onBlur(BlurEvent event) {
				checkBox.removeStyleName(KSStyles.KS_CHECKBOX_FOCUS_STYLE);
				
			}	
		});	

		checkBox.addFocusHandler(new FocusHandler(){
			public void onFocus(FocusEvent event) {
				checkBox.addStyleName(KSStyles.KS_CHECKBOX_FOCUS_STYLE);

			}		
		});
		
		checkBox.addMouseOverHandler(new MouseOverHandler(){
			public void onMouseOver(MouseOverEvent event) {
				checkBox.addStyleName(KSStyles.KS_CHECKBOX_HOVER_STYLE);
				
			}		
		});
		
		checkBox.addMouseOutHandler(new MouseOutHandler(){

			public void onMouseOut(MouseOutEvent event) {
				checkBox.removeStyleName(KSStyles.KS_CHECKBOX_HOVER_STYLE);
				
			}
			
		});
		
		checkBox.addClickHandler(new ClickHandler(){

			public void onClick(ClickEvent event) {
				if(checkBox.getValue()){
					checkBox.addStyleName(KSStyles.KS_CHECKBOX_CHECKED_STYLE);
				}
				else{
					checkBox.removeStyleName(KSStyles.KS_CHECKBOX_CHECKED_STYLE);
				}
				
			}
			
		});
	}

}
