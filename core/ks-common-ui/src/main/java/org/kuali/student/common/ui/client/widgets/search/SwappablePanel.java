package org.kuali.student.common.ui.client.widgets.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.widgets.KSDropDown;
import org.kuali.student.common.ui.client.widgets.layout.VerticalFlowPanel;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class SwappablePanel extends Composite{
	private VerticalFlowPanel panel = new VerticalFlowPanel();
	private ListBox panelSelector = new ListBox();
	private LinkedHashMap<String, Widget> panelMap = new LinkedHashMap<String, Widget>();
	private SimplePanel content = new SimplePanel();
	
	public SwappablePanel(LinkedHashMap<String, Widget> panelMap){
		this.panelMap = panelMap;
		drawPanel();
		panelSelector.addChangeHandler(new ChangeHandler(){

			@Override
			public void onChange(ChangeEvent event) {
				int i = panelSelector.getSelectedIndex();
				Widget p = SwappablePanel.this.panelMap.get(panelSelector.getItemText(i));
				content.setWidget(p);
			}
		});
		
		this.initWidget(panel);
	}
	
	private void drawPanel(){
		panel.add(panelSelector);
		panel.add(content);
		
		Iterator<String> it = panelMap.keySet().iterator();
		boolean contentSet = false;
		while(it.hasNext()){
			
			String text = it.next();
			panelSelector.addItem(text);
			if(!contentSet){
				content.setWidget(panelMap.get(text));
				contentSet = true;
			}
		}
	}
	
}
