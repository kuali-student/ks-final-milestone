/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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

import org.kuali.student.common.ui.client.widgets.KSFloatPanel;
import org.kuali.student.common.ui.client.widgets.KSStyles;

import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.ScrollEvent;
import com.google.gwt.user.client.Window.ScrollHandler;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

public class KSFloatPanelImpl implements KSFloatPanel {
	
	private final PopupPanel panel = new PopupPanel(false, false);

	private final ResizeHandler resizeHandler = new ResizeHandler() {
		public void onResize(ResizeEvent event) {
			recalcSizeAndPosition();
		}
	};
	
	private final ScrollHandler scrollHandler = new ScrollHandler() {
		public void onWindowScroll(ScrollEvent event) {
			recalcSizeAndPosition();
		}
	};
	
	// default height is 75% of the client area height
	private int heightRatio = 75;
	private FloatLocation location = FloatLocation.FLOAT_LEFT;
	
	
	public KSFloatPanelImpl() {
		// no point in adding/removing on show/hide, because there is no remove method
		Window.addResizeHandler(resizeHandler);
		Window.addWindowScrollHandler(scrollHandler);
		panel.addStyleName(KSStyles.KS_FLOATPANEL);
	}
	

	public int getHeightRatio() {
		return heightRatio;
	}

	public void setHeightRatio(int heightRatio) {
		this.heightRatio = heightRatio;
	}

	
	public FloatLocation getLocation() {
		return location;
	}


	public void setLocation(FloatLocation location) {
		this.location = location;
	}


	public boolean isVisible() {
		return panel.isVisible();
	}

	public void setWidget(Widget w) {
		panel.setWidget(w);
	}

	public Widget getWidget() {
		return panel.getWidget();
	}


	public void show() {
		panel.show();
		recalcSizeAndPosition();
	}
	public void hide() {
		panel.hide();
	}
	
	public void setStyleName(String style){
		panel.setStyleName(style);
	}
	
	public void addStyleName(String style){
		panel.addStyleName(style);
	}
	
	protected void recalcSizeAndPosition() {
		if (panel.isVisible()) {
			int height = (int)(Window.getClientHeight() * (float) heightRatio / 100f);
			int left = (location == FloatLocation.FLOAT_LEFT) ? 0 : Window.getScrollLeft() + Window.getClientWidth() - panel.getOffsetWidth(); 
			int top = ((Window.getClientHeight() - height) / 2) + Window.getScrollTop();
			panel.setHeight(height + "px");
			panel.setPopupPosition(left, top);
			
		}
	}
}
