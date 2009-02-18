package org.kuali.student.common.ui.client.widgets;

import java.util.List;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.ListBox;

public class KSListBox extends ListBox{

	public KSListBox() {
		super(true);
		setupDefaultStyle();
	}

	
	public KSListBox(List<String> list) {
		super(true);
		for(String s: list){
			this.addItem(s);
		}
		setupDefaultStyle();
	}

	public KSListBox(Element element) {
		super(element);
		setupDefaultStyle();
	}
	
	private void setupDefaultStyle() {
		this.addStyleName(KSStyles.KS_LISTBOX_STYLE);
		
		this.addBlurHandler(new BlurHandler(){
			public void onBlur(BlurEvent event) {
				KSListBox.this.removeStyleName(KSStyles.KS_LISTBOX_FOCUS_STYLE);
				
			}	
		});	

		this.addFocusHandler(new FocusHandler(){
			public void onFocus(FocusEvent event) {
				KSListBox.this.addStyleName(KSStyles.KS_LISTBOX_FOCUS_STYLE);

			}		
		});
		
		this.addMouseOverHandler(new MouseOverHandler(){
			public void onMouseOver(MouseOverEvent event) {
				KSListBox.this.addStyleName(KSStyles.KS_LISTBOX_HOVER_STYLE);
				
			}		
		});
		
		this.addMouseOutHandler(new MouseOutHandler(){

			public void onMouseOut(MouseOutEvent event) {
				KSListBox.this.removeStyleName(KSStyles.KS_LISTBOX_HOVER_STYLE);
				
			}
			
		});
	}
}
