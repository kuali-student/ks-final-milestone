package org.kuali.student.common.ui.client.widgets;

import java.util.HashMap;

import org.kuali.student.common.ui.client.widgets.KSFloatPanel.FloatLocation;
import org.kuali.student.common.ui.client.widgets.impl.KSCollapsableFloatPanelImpl;
import org.kuali.student.common.ui.client.widgets.impl.KSFloatPanelImpl;
import org.kuali.student.common.ui.client.widgets.impl.KSRichTextToolbarImpl;
import org.kuali.student.common.ui.client.widgets.impl.KSSidebarImpl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.HasVerticalAlignment.VerticalAlignmentConstant;

/**
 * The KSSidebar is a float panel that can be place on the right side or the left side of the screen.  This
 * panel can be opened and closed by clicking on the tabs of the panel.  Each tab can display different content in
 * the content panel.  When the selected tab is clicked again, the panel closes.  When the panel is closed
 * only the sidebar's tabs are visible.
 * 
 * @author Kuali Student Team
 *
 */
public class KSSidebar extends KSSidebarAbstract{
	private KSSidebarAbstract sidebar = GWT.create(KSSidebarImpl.class);
	
	/**
	 * Creates a sidebar that will display on the left side of the screen.
	 * 
	 */
	public KSSidebar(){
		sidebar.init();
	}
	
	/**
	 * Constructs a sidebar that will display at the screen location specified.
	 * 
	 * @param location the location of the sidebar (left or right)
	 */
	public KSSidebar(FloatLocation location){
		sidebar.init(location);
	}
	
	/**
	 * Adds a tab to the sidebar with the content specified used as the content which displays when the tab is clicked.
	 * The name is used as the category title and id.
	 * 
	 * @param content the Widget show as the content when this tab is clicked
	 * @param name the tab's name/title/id
	 */
	@Override
	public void addTab(Widget content, String name) {
		sidebar.addTab(content, name);
		
	}

	/**
	 * Hides the entire sidebar widget (including tabs).
	 * 
	 */
	@Override
	public void hide() {
		sidebar.hide();
	}

	/**
	 * Hides only the the content portion of the sidebar.  The tabs remain and are clickable.
	 * 
	 */
	@Override
	public void hideContent() {
		sidebar.hideContent();
	}

	/**
	 * Removes the tab with the name specified.
	 * 
	 * @param name the name of the tab to be removed
	 */
	@Override
	public void removeTab(String name) {
		sidebar.removeTab(name);
		
	}

	/**
	 * Shows the entire sidebar widget.
	 * 
	 */
	@Override
	public void show() {
		sidebar.show();
		
	}

	/**
	 * Shows the content passed in with and sets the title specified (this does not add a new tab).
	 * 
	 * @param content the content to be shown in the sidebar
	 * @name title the title of the content
	 */
	@Override
	public void showContent(Widget content, String title) {
		sidebar.showContent(content, title);	
	}

	/**
	 * Initializes the sidebar.
	 * 
	 */
	@Override
	protected void init() {
		sidebar.init();
		
	}

	/**
	 * Initializes the sidebar with the options specified.
	 * 
	 */
	@Override
	protected void init(FloatLocation location) {
		sidebar.init(location);
		
	}
	
}
