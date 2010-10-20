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

package org.kuali.student.common.ui.client.widgets.field.layout.element;

import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSButtonAbstract.ButtonStyle;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasMouseOutHandlers;
import com.google.gwt.event.dom.client.HasMouseOverHandlers;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.PopupPanel;

public class AbbrButton extends Composite implements HasClickHandlers, HasMouseOverHandlers, HasMouseOutHandlers{
	
	public enum AbbrButtonType{HELP, DELETE, VIEW};
	
	private AbbrPanel abbr;
	private KSButton button;
	private PopupPanel hoverPopup = new PopupPanel();
	
	public AbbrButton(AbbrButtonType type){
		
		switch(type){
			case HELP:
				abbr = new AbbrPanel("Help", "ks-form-module-elements-help");
				button = new KSButton("?", ButtonStyle.DEFAULT_ANCHOR);
				abbr.add(button);
				break;
			case DELETE:
				abbr = new AbbrPanel("Delete", "ks-form-module-elements-delete");
				button = new KSButton("X", ButtonStyle.DEFAULT_ANCHOR);
				abbr.add(button);
				break;
			case VIEW:
                abbr = new AbbrPanel("View", "ks-form-module-elements-delete");
                button = new KSButton("View", ButtonStyle.DEFAULT_ANCHOR);
                abbr.add(button);
			    break;
		}
		this.initWidget(abbr);
	}
	
	public void setHoverHTML(String html){
		hoverPopup.add(new HTMLPanel(html));
		hoverPopup.setStyleName("ks-help-popup");
		button.addMouseOverHandler(new MouseOverHandler(){

			@Override
			public void onMouseOver(MouseOverEvent event) {
				hoverPopup.setPopupPosition(button.getAbsoluteLeft() + button.getOffsetWidth() + 5, 
						button.getAbsoluteTop());
				hoverPopup.show();
			}
		});
		button.addMouseOutHandler(new MouseOutHandler(){

			@Override
			public void onMouseOut(MouseOutEvent event) {
				hoverPopup.hide();
			}
		});
		
	}

	@Override
	public HandlerRegistration addClickHandler(ClickHandler handler) {
		return button.addClickHandler(handler);
	}

	@Override
	public HandlerRegistration addMouseOverHandler(MouseOverHandler handler) {
		return button.addMouseOverHandler(handler);
	}

	@Override
	public HandlerRegistration addMouseOutHandler(MouseOutHandler handler) {
		return button.addMouseOutHandler(handler);
	}
}
