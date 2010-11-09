package org.kuali.student.common.ui.client.widgets;

import java.util.ArrayList;

import org.kuali.student.common.ui.client.widgets.KSButtonAbstract.ButtonStyle;
import org.kuali.student.common.ui.client.widgets.field.layout.element.AbbrButton;
import org.kuali.student.common.ui.client.widgets.field.layout.element.LabelPanel;
import org.kuali.student.common.ui.client.widgets.field.layout.element.AbbrButton.AbbrButtonType;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;

public class ListOfStringWidget extends Composite {
    private String addItemText;
	
	private boolean loaded = false;
	private FlowPanel mainPanel = new FlowPanel();
	private FlowPanel itemsPanel = new FlowPanel();
	private ArrayList<String> values = new ArrayList<String>();
	
	
	public ListOfStringWidget(String addItemText) {
		super();
		super.initWidget(mainPanel);
		this.addItemText = addItemText;
	}

	public void onLoad() {
        if (!loaded) {            
            final KSTextBox inputText = new KSTextBox();
            KSButton addItemButton = new KSButton(addItemText);
            addItemButton.addClickHandler(new ClickHandler(){
				public void onClick(ClickEvent event) {
					addListItem(inputText.getText());
					inputText.setText("");
				}
            });
            mainPanel.add(inputText);
            mainPanel.add(addItemButton);
            mainPanel.add(itemsPanel);            
            loaded = true;
        }
    }
	
	public ArrayList<String> getStringValues(){
		return values;
	}
	
	public void setStringValues(ArrayList<String> values){
		itemsPanel.clear();
		if(values!=null){
			for(String value:values){
				addListItem(value);
			}
		}
	}
	
	protected void addListItem(String itemValue){
		final FlowPanel item = new FlowPanel();
		final String curVal = itemValue;
		if (!values.contains(itemValue)) {
	        
			String fieldHTMLId = HTMLPanel.createUniqueId();
	        LabelPanel fieldTitle = new LabelPanel(itemValue, fieldHTMLId);
	        
	    	AbbrButton delButton = new AbbrButton(AbbrButtonType.DELETE);
	    	delButton.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					itemsPanel.remove(item);
					values.remove(curVal);
				}
			});
	        fieldTitle.add(delButton);
	        item.add(fieldTitle);
			itemsPanel.add(item);
			values.add(curVal);
		}
	}
}
