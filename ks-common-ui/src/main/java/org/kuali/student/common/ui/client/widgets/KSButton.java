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
package org.kuali.student.common.ui.client.widgets;

import org.kuali.student.common.ui.client.widgets.buttons.KSLinkButton.ButtonStyle;
import org.kuali.student.common.ui.client.widgets.buttons.KSLinkButton.SpanPanel;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasMouseOutHandlers;
import com.google.gwt.event.dom.client.HasMouseOverHandlers;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;

/**
 * KSButton wraps gwt Button.  This class provides most of the same functionality, but sets KS css styles
 * for its default look and a variety of button events (for improved browser compatibility and customizability).
 * An image can be also be added to a KSButton (an improvement over gwt Button).
 * 
 * @author Kuali Student Team
 *
 */
public class KSButton extends Composite implements HasClickHandlers, HasMouseOverHandlers, HasMouseOutHandlers{

	public static enum ButtonStyle{
		PRIMARY("ks-button-primary", "ks-button-primary-disabled"),
		SECONDARY("ks-button-secondary", "ks-button-secondary-disabled"),
		PRIMARY_SMALL("ks-button-primary-small", "ks-button-primary-small-disabled"),
		SECONDARY_SMALL("ks-button-secondary-small", "ks-button-secondary-small-disabled"),
		FORM_SMALL("ks-form-button-small", null),
		FORM_LARGE("ks-form-button-large", null),
		HELP("ks-form-module-elements-help", null),
		DELETE("ks-form-module-elements-delete", null),
		DEFAULT_ANCHOR("ks-link", "ks-link-disabled");
		
		private String style;
		private String disabledStyle;

		private ButtonStyle(String style, String disabledStyle){
			this.style = style;
			this.disabledStyle = disabledStyle;
		}
		
		public String getStyle(){
			return style;
		}
		
		public String getDisabledStyle() {
			return disabledStyle;
		}
	};
	
	public class SpanPanel extends ComplexPanel{
		
		public SpanPanel(){
			setElement(DOM.createSpan());
		}
		
		  /**
		   * Adds a new child widget to the panel.
		   * 
		   * @param w the widget to be added
		   */
		  @Override
		  public void add(Widget w) {
		    add(w, getElement());
		  }

		  /**
		   * Inserts a widget before the specified index.
		   * 
		   * @param w the widget to be inserted
		   * @param beforeIndex the index before which it will be inserted
		   * @throws IndexOutOfBoundsException if <code>beforeIndex</code> is out of
		   *           range
		   */
		  public void insert(Widget w, int beforeIndex) {
		    insert(w, getElement(), beforeIndex, true);
		  }
	}
	
	private SpanPanel panel = new SpanPanel();
	private Anchor anchor;
	private InlineLabel disabledLabel = new InlineLabel();
	private boolean enabled = true;
	private ButtonStyle currentStyle;
	private String text;
	private Widget widget;
	
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		anchor.setEnabled(enabled);
		if(enabled){
			panel.remove(disabledLabel);
			panel.add(anchor);
		}
		else{
			panel.remove(anchor);
			panel.add(disabledLabel);

		}
	}
	
	public KSButton(String text){
		this(text, ButtonStyle.PRIMARY);
	}
	
	public KSButton(String text, ButtonStyle style){
		super();

		this.text = text;
		this.currentStyle = style;
		disabledLabel.setText(text);
		anchor = new Anchor();
		if(currentStyle.getStyle() != null){
			disabledLabel.setStyleName(currentStyle.getStyle());
			anchor.setStyleName(currentStyle.getStyle());
		}
		String disabledStyle = currentStyle.getDisabledStyle();
		if(disabledStyle == null){
			disabledLabel.addStyleName("disabled");
		}
		else{
			disabledLabel.setStyleName(disabledStyle);
		}
		anchor.setText(text);
		anchor.setHref("javascript:return false;");

		panel.add(anchor);
		
		this.initWidget(panel);
	}
	
	@Override
	public HandlerRegistration addClickHandler(ClickHandler handler) {
		return anchor.addClickHandler(handler);
	}
	
	public void setText(String text){
		this.text = text;
		anchor.setText(text);
		disabledLabel.setText(text);
	}
	
	public KSButton(){
		this("", ButtonStyle.PRIMARY);
	}
	
	public KSButton(String text, ClickHandler handler){
		this(text, ButtonStyle.PRIMARY);
		anchor.addClickHandler(handler);
	}

	@Override
	public HandlerRegistration addMouseOverHandler(MouseOverHandler handler) {
		return anchor.addMouseOverHandler(handler);
	}

	@Override
	public HandlerRegistration addMouseOutHandler(MouseOutHandler handler) {
		return anchor.addMouseOutHandler(handler);
	}
	
	
}
