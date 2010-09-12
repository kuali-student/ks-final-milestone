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

import org.kuali.student.common.ui.client.widgets.KSButtonAbstract;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;

public class KSButtonImpl extends KSButtonAbstract{


	private static class SpanPanel extends ComplexPanel{

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

	@Override
	public HandlerRegistration addClickHandler(ClickHandler handler) {
		return anchor.addClickHandler(handler);
	}

	@Override
	public void setText(String text){
		anchor.setText(text);
		disabledLabel.setText(text);
	}

	@Override
	public HandlerRegistration addMouseOverHandler(MouseOverHandler handler) {
		return anchor.addMouseOverHandler(handler);
	}

	@Override
	public HandlerRegistration addMouseOutHandler(MouseOutHandler handler) {
		return anchor.addMouseOutHandler(handler);
	}

	@Override
	public void init(String text) {
		init(text, ButtonStyle.PRIMARY);
	}

	@Override
	public void init(String text, ButtonStyle style) {

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
    public void addStyleName(String style) {
        anchor.addStyleName(style);
        disabledLabel.addStyleName(style);
    }

    @Override
    public void removeStyleName(String style) {
        anchor.removeStyleName(style);
        disabledLabel.removeStyleName(style);        
    }

    @Override
    public void setStyleName(String style) {
        anchor.setStyleName(style);
        disabledLabel.setStyleName(style);        
    }

    @Override
	public void init() {
		init("", ButtonStyle.PRIMARY);
	}

	@Override
	public void init(String text, ClickHandler handler) {
		init(text, ButtonStyle.PRIMARY);
		anchor.addClickHandler(handler);
	}

    public void init(String text, ButtonStyle style, ClickHandler handler) {
        init(text, style);
        anchor.addClickHandler(handler);
    }
}
