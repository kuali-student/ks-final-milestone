package org.kuali.student.common.ui.client.widgets.menus.impl;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.widgets.layout.HorizontalBlockFlowPanel;
import org.kuali.student.common.ui.client.widgets.menus.KSMenuItemData;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;

public class KSBlockMenuImpl extends Composite{
	private FlowPanel layout = new FlowPanel();
	private FlowPanel container = new FlowPanel();
	private List<KSListMenuImpl> menus = new ArrayList<KSListMenuImpl>();
	
	public KSBlockMenuImpl(){
		//layout.setStyleName("KS-Block-Menu");
		layout.setStyleName("ks-page-sub-navigation");
		container.add(layout);
		layout.add(new HTMLPanel("<div class=\"clear\">&nbsp;</div>"));
		this.initWidget(container);
	}
	
	public void addMenu(KSListMenuImpl menu){
		menu.setStyleName("ks-page-sub-navigation-menu");
		layout.insert(menu, layout.getWidgetCount() -1);
		//layout.add(menu);
		
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
