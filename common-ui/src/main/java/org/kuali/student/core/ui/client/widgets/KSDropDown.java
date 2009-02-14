package org.kuali.student.core.ui.client.widgets;

import java.util.HashMap;
import java.util.List;

import com.google.gwt.dom.client.Element;
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
import com.google.gwt.user.client.ui.ListBox;

public class KSDropDown extends ListBox{

	private HashMap<String, ?> map;
	
	public KSDropDown() {
		super();
		setupDefaultStyle();
	}

	public KSDropDown(boolean isMultipleSelect) {
		super(isMultipleSelect);
		setupDefaultStyle();
	}

	public KSDropDown(Element element) {
		super(element);
		setupDefaultStyle();
	}

	private void setupDefaultStyle() {
		addStyleName(KSStyles.KS_DROPDOWN_STYLE);
		
		this.addBlurHandler(new BlurHandler(){
			public void onBlur(BlurEvent event) {
				KSDropDown.this.removeStyleName(KSStyles.KS_DROPDOWN_FOCUS_STYLE);
				
			}	
		});	

		this.addFocusHandler(new FocusHandler(){
			public void onFocus(FocusEvent event) {
				KSDropDown.this.addStyleName(KSStyles.KS_DROPDOWN_FOCUS_STYLE);

			}		
		});
		
		this.addMouseOverHandler(new MouseOverHandler(){
			public void onMouseOver(MouseOverEvent event) {
				KSDropDown.this.addStyleName(KSStyles.KS_DROPDOWN_HOVER_STYLE);
				
			}		
		});
		
		this.addMouseOutHandler(new MouseOutHandler(){

			public void onMouseOut(MouseOutEvent event) {
				KSDropDown.this.removeStyleName(KSStyles.KS_DROPDOWN_HOVER_STYLE);
				
			}
			
		});
		
		this.addClickHandler(new ClickHandler(){

			public void onClick(ClickEvent event) {
				if(KSDropDown.this.getSelectedIndex() != -1){
					KSDropDown.this.addStyleName(KSStyles.KS_DROPDOWN_SELECTED_STYLE);
				}
				else{
					KSDropDown.this.removeStyleName(KSStyles.KS_DROPDOWN_SELECTED_STYLE);
				}
				
			}
			
		});
		
	}
	
	public void populateDropDown(List<String> stringList){
		HashMap<String, String> strings = new HashMap<String, String>();
		for(String s: stringList){
			strings.put(s, s);
		}
		
		if(map != null){
			map.clear();
		}
		
		map = strings;
		this.clear();
		for(String item: map.keySet()){
			this.addItem(item);
		}
		
	}
	
	public void populateDropDown(HashMap<String, ?> theMap){
		if(map != null){
			map.clear();
		}
		map = theMap;
		this.clear();
		for(String item: map.keySet()){
			this.addItem(item);
		}
	}
	
	public Object getSelectedObject(){
		Object selected = null;
		if(this.getSelectedIndex() != -1){
			map.get(this.getValue(this.getSelectedIndex()));
		}
		return selected;
	}
	
	public void selectItem(String value){
		for(int i = 0; i < this.getItemCount(); i++){
			if(value.equals(this.getItemText(i))){
				this.setSelectedIndex(i);
			}
		}
	}
	
	
	

}
