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
	
	public enum AbbrButtonType{HELP, DELETE};
	
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
