package org.kuali.student.common.ui.client.widgets.menus;

import org.kuali.student.common.ui.client.widgets.KSStackPanel;

import com.google.gwt.user.client.ui.SimplePanel;

public class KSAccordionMenu extends KSMenu{
	
	KSStackPanel menu = new KSStackPanel();
	
	@Override
	protected void populateMenu() {
		this.initWidget(menu);
		menu.clear();
		for(MenuItem i: items){
			if(i.getSubItems().isEmpty()){
				menu.add(new SimplePanel(), i.getLabel());
			}
			else{
				KSAccordionMenu subMenu = new KSAccordionMenu();
				subMenu.setItems(i.getSubItems());
				menu.add(subMenu, i.getLabel());
			}
		}
		
	}
	
}
