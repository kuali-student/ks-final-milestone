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

package org.kuali.student.lum.lu.ui.course.client.widgets.a.common;

import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.widgetideas.client.GlassPanel;

/**
 * Simple modal popup panel that shades out the rest of the window.
 * @author wilj
 *
 */
public class LightBox {
	private final PopupPanel pop = new PopupPanel(false, true);
	private final GlassPanel glass = new GlassPanel(false);
	private final AbsolutePanel parentPanel;
	private boolean showing = false;

	/**
	 * Creates an empty LightBox
	 * @param parentPanel the AbsolutePanel to shade out, normally the RootPanel
	 */
	public LightBox(final AbsolutePanel parentPanel) {
		this.parentPanel = parentPanel;
	}

	/**
	 * Adds CloseHandler to the underlying PopupPanel
	 * @param handler
	 * @return HandlerRegistration for handler removal
	 */
	public HandlerRegistration addCloseHandler(final CloseHandler<PopupPanel> handler) {
		return pop.addCloseHandler(handler);
	}

	/**
	 * Returns the content widget
	 * @return the content widget
	 */
	public Widget getWidget() {
		return pop.getWidget();
	}

	/**
	 * Hides the lightbox
	 */
	public void hide() {
		pop.hide();
		ZIndexStack.push();
		parentPanel.remove(glass);
		ZIndexStack.push();
		showing = false;
	}

	/**
	 * Sets the content widget
	 * @param widget the content widget
	 */
	public void setWidget(final Widget widget) {
		pop.setWidget(widget);
	}

	/**
	 * Shows the lightbox
	 */
	public void show() {
		if (!showing) {
			glass.getElement().setAttribute("zIndex", String.valueOf(ZIndexStack.pop()));
			parentPanel.add(glass, 0, 0);
			pop.getElement().setAttribute("zIndex", String.valueOf(ZIndexStack.pop()));
		}
		pop.center();
		//		pop.setPopupPositionAndShow(new PopupPanel.PositionCallback() {
		//			public void setPosition(final int offsetWidth, final int offsetHeight) {
		//				int left = (parentPanel.getOffsetWidth() - offsetWidth) / 2;
		//				int top = (parentPanel.getOffsetHeight() - offsetHeight) / 2;
		//				if (left < 0) {
		//					left = 0;
		//				}
		//				if (top < 0) {
		//					top = 0;
		//				}
		//				pop.setPopupPosition(parentPanel.getAbsoluteLeft() + left, parentPanel.getAbsoluteTop() + top);
		//			}
		//		});
		showing = true;
	}

}
