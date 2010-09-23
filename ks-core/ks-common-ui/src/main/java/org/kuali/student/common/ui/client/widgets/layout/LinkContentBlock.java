package org.kuali.student.common.ui.client.widgets.layout;

import org.kuali.student.common.ui.client.widgets.menus.KSListPanel;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Widget;

public class LinkContentBlock extends ContentBlock{

	protected KSListPanel listLayout = new KSListPanel();
	
	public LinkContentBlock(String blockTitle, String blockDescriptionHtml,
			int blockSize) {
		super(blockTitle, blockDescriptionHtml, blockSize);
		super.add(listLayout);
		listLayout.setStyleName("contentBlock-list");
	}
	
	public LinkContentBlock(String blockTitle, String blockDescriptionHtml){
		this(blockTitle, blockDescriptionHtml, 1);
	}
	
	@Override
	public void add(Widget widget){
		listLayout.add(widget);
	}
	
	public void addNavLinkWidget(String text, String location){
		Hyperlink hyperlink = new Hyperlink(text, location);
		hyperlink.addStyleName("contentBlock-navLink");
		listLayout.add(hyperlink);
	}
	
	public void addNavLinkWidget(String text, ClickHandler handler){
		Anchor anchor = new Anchor(text);
		anchor.addClickHandler(handler);
		anchor.addStyleName("contentBlock-navLink");
		this.add(anchor);
	}

}
