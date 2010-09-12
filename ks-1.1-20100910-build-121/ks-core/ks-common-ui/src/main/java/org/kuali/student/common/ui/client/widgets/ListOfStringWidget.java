package org.kuali.student.common.ui.client.widgets;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;

public class ListOfStringWidget extends Composite {
    private String addItemText;
	
	private boolean loaded = false;
	private FlowPanel mainPanel = new FlowPanel();
	private FlowPanel itemsPanel = new FlowPanel();
	
	
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
		ArrayList<String> values = new ArrayList<String>(itemsPanel.getWidgetCount());
		for(int i=0;i<itemsPanel.getWidgetCount();i++){
			FlowPanel item = (FlowPanel) itemsPanel.getWidget(i);
			KSLabel label = (KSLabel) item.getWidget(0);
			values.add(label.getText());
		}
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
		KSLabel label = new KSLabel(itemValue);
		KSButton deleteButton = new KSButton("X");
		deleteButton.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				itemsPanel.remove(item);
			}
		});
		item.add(label);
		item.add(deleteButton);
		itemsPanel.add(item);
	}
}
