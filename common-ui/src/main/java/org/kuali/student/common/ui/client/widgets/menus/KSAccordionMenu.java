package org.kuali.student.common.ui.client.widgets.menus;

import org.kuali.student.common.ui.client.widgets.KSAccordionPanel;
import org.kuali.student.common.ui.client.widgets.KSLabel;

public class KSAccordionMenu extends KSMenu{
	
	private KSAccordionPanel menu = new KSAccordionPanel();
	private String indent = "";
	
	@Override
	protected void populateMenu() {
		this.initWidget(menu);
		for(KSMenuItemData i: items){
			KSLabel categoryLabel = new KSLabel(indent + i.getLabel());
/*			if(labelStyle != null){
				categoryLabel.addStyleName(labelStyle);
			}*/
			if(i.getClickHandler() != null){
				categoryLabel.addClickHandler(i.getClickHandler());
			}
			
			if(i.getSubItems().isEmpty()){		
				menu.addPanel(categoryLabel);
			}
			else{
				KSAccordionMenu subMenu = new KSAccordionMenu();
				//subMenu.setLabelStyle("KS-Accordion-SubMenu-Indent");
				subMenu.setIndent(indent + "      ");
				subMenu.setItems(i.getSubItems());
				menu.addPanel(categoryLabel, subMenu);
			}
		}
		
	}
	
	public KSAccordionPanel getMenu(){
		return menu;
	}
	
	public void setIndent(String indent){
		this.indent = indent;
	}
	
}
