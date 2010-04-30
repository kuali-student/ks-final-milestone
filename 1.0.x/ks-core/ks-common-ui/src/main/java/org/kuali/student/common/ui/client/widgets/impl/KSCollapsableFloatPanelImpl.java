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

package org.kuali.student.common.ui.client.widgets.impl;

import org.kuali.student.common.ui.client.widgets.KSCollapsableFloatPanel;
import org.kuali.student.common.ui.client.widgets.KSStyles;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class KSCollapsableFloatPanelImpl extends KSFloatPanelImpl implements KSCollapsableFloatPanel {
	private final HorizontalPanel hpanel = new HorizontalPanel();
	private final SimplePanel expandCollapsePanel = new SimplePanel();
	private final SimplePanel content = new SimplePanel();
	
	private final Button expand = new Button("", new ClickHandler() {
		public void onClick(ClickEvent event) {
			setExpanded(true);
		}
	});
	
	private final Button collapse = new Button("", new ClickHandler() {
		public void onClick(ClickEvent event) {
			setExpanded(false);
		}
	});
	

	private boolean expanded = false;

	public KSCollapsableFloatPanelImpl() {
		super();
		expandCollapsePanel.addStyleName(KSStyles.KS_COLLAPSABLEFLOATPANEL);
	}
	
	public boolean isExpanded() {
		return expanded;
	}

	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
		if (expanded) {
			expand();
		} else {
			collapse();
		}
	}
	
	

	@Override
	public Widget getWidget() {
		return content.getWidget();
	}

	@Override
	public void setWidget(Widget w) {
		content.setWidget(w);
	}

	@Override
	public void show() {
		if (super.getLocation() == FloatLocation.FLOAT_LEFT) {
			expand.setText(">");
			collapse.setText("<");
			hpanel.add(content);
			hpanel.add(expandCollapsePanel);
		} else {
			expand.setText("<");
			collapse.setText(">");
			hpanel.add(expandCollapsePanel);
			hpanel.add(content);
		}
		
		expandCollapsePanel.clear();// If this is second time through need to clear previous widget
        expandCollapsePanel.add(expand);
		content.setVisible(false);
		super.setWidget(hpanel);
		super.show();
	}
	
	protected void expand() {
		content.setVisible(true);
		expandCollapsePanel.remove(expandCollapsePanel.getWidget());
		expandCollapsePanel.setWidget(collapse);
		super.recalcSizeAndPosition();
	}
	protected void collapse() {
		content.setVisible(false);
		expandCollapsePanel.remove(expandCollapsePanel.getWidget());
		expandCollapsePanel.setWidget(expand);
		super.recalcSizeAndPosition();
	}
}
