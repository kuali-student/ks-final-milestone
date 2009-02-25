package org.kuali.student.common.ui.client.widgets.impl;

import java.util.HashMap;
import java.util.List;

import org.kuali.student.common.ui.client.widgets.KSDropDown;
import org.kuali.student.common.ui.client.widgets.KSStyles;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.ListBox;

public class KSDropDownImpl extends KSDropDown{ 

	private ListBox listBox;
	private HashMap<String, ?> map;
	
	public KSDropDownImpl() {

	}
	
	@Override
	public void init(boolean isMultipleSelect) {
		listBox = new ListBox(isMultipleSelect);
        this.initWidget(listBox);
		setupDefaultStyle();
	}

	private void setupDefaultStyle() {
	    listBox.addStyleName(KSStyles.KS_DROPDOWN_STYLE);
		
		listBox.addBlurHandler(new BlurHandler(){
			public void onBlur(BlurEvent event) {
			    listBox.removeStyleName(KSStyles.KS_DROPDOWN_FOCUS_STYLE);
				
			}	
		});	

		listBox.addFocusHandler(new FocusHandler(){
			public void onFocus(FocusEvent event) {
			    listBox.addStyleName(KSStyles.KS_DROPDOWN_FOCUS_STYLE);

			}		
		});
		
		listBox.addMouseOverHandler(new MouseOverHandler(){
			public void onMouseOver(MouseOverEvent event) {
			    listBox.addStyleName(KSStyles.KS_DROPDOWN_HOVER_STYLE);
				
			}		
		});
		
		listBox.addMouseOutHandler(new MouseOutHandler(){

			public void onMouseOut(MouseOutEvent event) {
			    listBox.removeStyleName(KSStyles.KS_DROPDOWN_HOVER_STYLE);
				
			}
			
		});
		
		listBox.addClickHandler(new ClickHandler(){

			public void onClick(ClickEvent event) {
				if(listBox.getSelectedIndex() != -1){
				    listBox.addStyleName(KSStyles.KS_DROPDOWN_SELECTED_STYLE);
				}
				else{
				    listBox.removeStyleName(KSStyles.KS_DROPDOWN_SELECTED_STYLE);
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
		listBox.clear();
		for(String item: map.keySet()){
			listBox.addItem(item);
		}
		
	}
	
	public void populateDropDown(HashMap<String, ?> theMap){
		if(map != null){
			map.clear();
		}
		map = theMap;
		listBox.clear();
		for(String item: map.keySet()){
			listBox.addItem(item);
		}
	}
	
	public Object getSelectedObject(){
		Object selected = null;
		if(listBox.getSelectedIndex() != -1){
			map.get(listBox.getValue(listBox.getSelectedIndex()));
		}
		return selected;
	}
	
	public void selectItem(String value){
		for(int i = 0; i < listBox.getItemCount(); i++){
			if(value.equals(listBox.getItemText(i))){
				listBox.setSelectedIndex(i);
			}
		}
	}

	@Override
    public void addChangeHandler(ChangeHandler handler) {
	    listBox.addChangeHandler(handler);
	}
	
	@Override
    public void addItem(String item) {
        listBox.addItem(item); 
    }
	
	@Override
    public int getSelectedIndex() {
	    return listBox.getSelectedIndex();
	}
	
    @Override
    public void removeItem(int index) {
        listBox.removeItem(index);
    }
    
    @Override
    public void clear() {
        listBox.clear();
    }
}
