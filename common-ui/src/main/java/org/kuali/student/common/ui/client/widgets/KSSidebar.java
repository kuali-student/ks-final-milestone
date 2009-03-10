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

public class KSSidebar extends KSSidebarAbstract{
	private KSSidebarAbstract sidebar = GWT.create(KSSidebarImpl.class);
	
	public KSSidebar(){
		sidebar.init();
	}
	
	public KSSidebar(FloatLocation location){
		sidebar.init(location);
	}
	@Override
	public void addTab(Widget content, String name) {
		sidebar.addTab(content, name);
		
	}

	@Override
	public void hide() {
		sidebar.hide();
	}

	@Override
	public void hideContent() {
		sidebar.hideContent();
	}

	@Override
	public void removeTab(String name) {
		sidebar.removeTab(name);
		
	}

	@Override
	public void show() {
		sidebar.show();
		
	}

	@Override
	public void showContent(Widget content, String title) {
		sidebar.showContent(content, title);	
	}

	@Override
	public void init() {
		sidebar.init();
		
	}

	@Override
	public void init(FloatLocation location) {
		sidebar.init(location);
		
	}
	
}
