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

package org.kuali.student.common.ui.client.widgets.notification;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.HasCloseHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

public class KSNotification extends Composite implements HasCloseHandlers<KSNotification>{
	private final int duration;
	public static final int DEFAULT_DURATION = 4000;
	private final String id = HTMLPanel.createUniqueId();
	private final String contentId = HTMLPanel.createUniqueId();
	private final String closeLinkId = HTMLPanel.createUniqueId();
	private final HTMLPanel panel = new HTMLPanel("<p id='" + contentId + "'></p><a href='javascript:return false;' title='Close' class='ks-notification-close' style='visibility: hidden' id='" + closeLinkId + "'></a>");
	
	public KSNotification(final String message, boolean isHtml, final int duration) {
		this.duration = duration;
		panel.setStyleName("ks-notification-message");
		panel.getElement().setId(id);
		
		if (isHtml) {
			panel.add(new HTML(message), contentId);
		} else {
			panel.getElementById(contentId).setInnerText(message);
		}
		
		initHandlers();
		super.initWidget(panel);
	}
	
	public KSNotification(final String message, boolean isHtml) {
		this.duration = DEFAULT_DURATION;
		panel.setStyleName("ks-notification-message");
		panel.getElement().setId(id);
		
		if (isHtml) {
			panel.add(new HTML(message), contentId);
		} else {
			panel.getElementById(contentId).setInnerText(message);
		}
		
		initHandlers();
		super.initWidget(panel);
	}

	public KSNotification(final Widget widget, final int duration) {
		this.duration = duration;
		panel.setStyleName("ks-notification-message");
		panel.getElement().setId(id);
		panel.add(widget, contentId);
		
		initHandlers();
		super.initWidget(panel);
	}

	private void initHandlers() {
		addDomHandler(new MouseOverHandler() {
			@Override
			public void onMouseOver(MouseOverEvent event) {
				DOM.getElementById(closeLinkId).getStyle().setProperty("visibility", "visible");
			}
		}, MouseOverEvent.getType());

		addDomHandler(new MouseOutHandler() {
			@Override
			public void onMouseOut(MouseOutEvent event) {
				DOM.getElementById(closeLinkId).getStyle().setProperty("visibility", "hidden");
			}
		}, MouseOutEvent.getType());
		
		DOM.sinkEvents(panel.getElementById(closeLinkId), Event.ONCLICK);
		addDomHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Element e = Element.as(event.getNativeEvent().getEventTarget());
				if (e.equals(panel.getElementById(closeLinkId))) {
					CloseEvent.fire(KSNotification.this, KSNotification.this);
				}
			}
		}, ClickEvent.getType());
	}
	
	/**
	 * @return the duration
	 */
	public int getDuration() {
		return duration;
	}

	public String getId() {
		return id;
	}

	public String getContentId() {
		return contentId;
	}

	public String getCloseLinkId() {
		return closeLinkId;
	}

	
	@Override
	public HandlerRegistration addCloseHandler(
			CloseHandler<KSNotification> handler) {
		return addHandler(handler, CloseEvent.getType());
	}
}
