package org.kuali.student.common.ui.client.widgets.impl;

import java.util.List;

import org.kuali.student.common.ui.client.widgets.KSListBox;
import org.kuali.student.common.ui.client.widgets.KSStyles;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.ListBox;

public class KSListBoxImpl extends KSListBox{ 

	private ListBox listBox;
	
	public KSListBoxImpl() {
	}

	@Override
	public void init(List<String> list) {
		listBox = new ListBox(true);
		initWidget(listBox);
		for(String s: list){
			listBox.addItem(s);
		}
		setupDefaultStyle();
	}


	
	private void setupDefaultStyle() {
		this.addStyleName(KSStyles.KS_LISTBOX_STYLE);
		
		listBox.addBlurHandler(new BlurHandler(){
			public void onBlur(BlurEvent event) {
				removeStyleName(KSStyles.KS_LISTBOX_FOCUS_STYLE);
				
			}	
		});	

		listBox.addFocusHandler(new FocusHandler(){
			public void onFocus(FocusEvent event) {
				addStyleName(KSStyles.KS_LISTBOX_FOCUS_STYLE);

			}		
		});
		
		listBox.addMouseOverHandler(new MouseOverHandler(){
			public void onMouseOver(MouseOverEvent event) {
				addStyleName(KSStyles.KS_LISTBOX_HOVER_STYLE);
				
			}		
		});
		
		listBox.addMouseOutHandler(new MouseOutHandler(){

			public void onMouseOut(MouseOutEvent event) {
				removeStyleName(KSStyles.KS_LISTBOX_HOVER_STYLE);
				
			}
			
		});
	}
}
