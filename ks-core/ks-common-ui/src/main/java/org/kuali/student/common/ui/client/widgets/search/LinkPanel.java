package org.kuali.student.common.ui.client.widgets.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.widgets.buttons.KSLinkButton;
import org.kuali.student.common.ui.client.widgets.layout.HorizontalBlockFlowPanel;
import org.kuali.student.common.ui.client.widgets.layout.VerticalFlowPanel;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class LinkPanel extends Composite{

	private Map<Enum<?>, PanelInfo> panels = new HashMap<Enum<?>, PanelInfo>();
	private SimplePanel container = new SimplePanel();

	private class PanelInfo extends Composite{
		private VerticalFlowPanel layout = new VerticalFlowPanel();
		private HorizontalBlockFlowPanel linkPanel = new HorizontalBlockFlowPanel();
		private Enum<?> key;
		private Widget content;
		private List<NavLink> links = new ArrayList<NavLink>();
		private class NavLink{
			protected String linkName;
			protected Enum<?> linkToKey;
		}

		public PanelInfo(Enum<?> key, Widget content){
			this.key = key;
			this.content = content;
			layout.add(content);
			layout.add(linkPanel);
			this.initWidget(layout);
		}

		public KSLinkButton addLink(String linkText, final Enum<?> linkedPanelKey){
			NavLink link = new NavLink();
			link.linkName = linkText;
			link.linkToKey = linkedPanelKey;
			links.add(link);
			//KSLabel linkWidget = new KSLabel(linkText);
			KSLinkButton linkWidget = new KSLinkButton(linkText);
			linkWidget.addClickHandler(new ClickHandler(){

				@Override
				public void onClick(ClickEvent event) {
					Widget newPanel = panels.get(linkedPanelKey);
					container.setWidget(newPanel);
				}
			});

			linkPanel.add(linkWidget);
			return linkWidget;
		}
	}

	public LinkPanel(Enum<?> defaultPanelKey, Widget content){
		PanelInfo info = new PanelInfo(defaultPanelKey, content);
		panels.put(defaultPanelKey, info);
		container.setWidget(info);
		this.initWidget(container);
	}

	public void addPanel(Enum<?> panelKey, Widget content){
		PanelInfo info = new PanelInfo(panelKey, content);
		panels.put(panelKey, info);
	}

	public KSLinkButton addLinkToPanel(Enum<?> panelKey, String linkText, Enum<?> linkedPanelKey){
		PanelInfo info = panels.get(panelKey);
		return info.addLink(linkText, linkedPanelKey);
	}


}
