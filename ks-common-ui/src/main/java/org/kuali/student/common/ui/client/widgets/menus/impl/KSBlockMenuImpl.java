package org.kuali.student.common.ui.client.widgets.menus.impl;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.widgets.layout.HorizontalBlockFlowPanel;
import org.kuali.student.common.ui.client.widgets.menus.KSMenuItemData;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;

public class KSBlockMenuImpl extends Composite{
	private HorizontalBlockFlowPanel layout = new HorizontalBlockFlowPanel();
	private List<KSListMenuImpl> menus = new ArrayList<KSListMenuImpl>();
	
	public KSBlockMenuImpl(){
		layout.setStyleName("KS-Block-Menu");
		this.initWidget(layout);
	}
	
	public void addMenu(KSListMenuImpl menu){
		FlowPanel div = new FlowPanel();
		div.setStyleName("KS-Block-Menu-Sub");
		div.add(menu);
		layout.add(div);
		
	}
	
	public void setMenus(List<KSListMenuImpl> menus){
		for(KSListMenuImpl menu: menus){
			this.addMenu(menu);
		}
	}
	
	/**
	 * Use the top level menu items of a list of KSMenuItemData to generate separate menus in a block
	 * layout
	 * @param data
	 */
	public void setTopLevelItems(List<KSMenuItemData> data){
		for(KSMenuItemData item: data){
			List<KSMenuItemData> list = new ArrayList<KSMenuItemData>();
			list.add(item);
			KSListMenuImpl menu = new KSListMenuImpl();
			menu.setItems(list);
			this.addMenu(menu);
		}
	}
}
