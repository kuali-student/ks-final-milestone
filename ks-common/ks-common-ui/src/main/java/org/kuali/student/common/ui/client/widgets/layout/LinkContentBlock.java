package org.kuali.student.common.ui.client.widgets.layout;

import org.kuali.student.common.ui.client.widgets.menus.KSListPanel;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Widget;

/**
 * A content block which contains a list of links/widgets and adds them in a consistent manner
 * 
 * @author Kuali Student Team
 *
 */
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
	
	/**
	 * Use this when you simply want to Navigate to other internal, KS screens.
	 * @param text - label
	 * @param location - relative internal path to screen
	 */
	public void addNavLinkWidget(String text, String location){
		Hyperlink hyperlink = new Hyperlink(text, location);
		hyperlink.addStyleName("contentBlock-navLink");
		listLayout.add(hyperlink);
	}
	
	/**
	 * Use this when you want an anchor attached to a custom click handler
	 * @param text - label
	 * @param handler - whatever handler you need.
	 */
	public void addNavLinkWidget(String text, ClickHandler handler){
		Anchor anchor = new Anchor(text);
		anchor.addClickHandler(handler);
		anchor.addStyleName("contentBlock-navLink");
		this.add(anchor);
	}
	
	/**
	 * Use this when you want a simple External link.
	 * @param text - label of link
	 * @param externalHref - URL
	 */
	public void addExternalLink(String text, String externalHref, boolean openInNewWin){
		Anchor anchor = new Anchor(text, externalHref);
		anchor.addStyleName("contentBlock-navLink");
		if (openInNewWin){
		anchor.setTarget("newWin");
		}
		this.add(anchor);
	}
}
