/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.common.ui.client.widgets.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSButtonAbstract.ButtonStyle;
import org.kuali.student.common.ui.client.widgets.layout.HorizontalBlockFlowPanel;
import org.kuali.student.common.ui.client.widgets.layout.VerticalFlowPanel;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

@Deprecated
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

		public KSButton addLink(String linkText, final Enum<?> linkedPanelKey){
			NavLink link = new NavLink();
			link.linkName = linkText;
			link.linkToKey = linkedPanelKey;
			links.add(link);
			//KSLabel linkWidget = new KSLabel(linkText);
			KSButton linkWidget = new KSButton(linkText, ButtonStyle.DEFAULT_ANCHOR);
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

	public KSButton addLinkToPanel(Enum<?> panelKey, String linkText, Enum<?> linkedPanelKey){
		PanelInfo info = panels.get(panelKey);
		return info.addLink(linkText, linkedPanelKey);
	}
}
