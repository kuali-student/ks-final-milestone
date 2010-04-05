package org.kuali.student.common.ui.client.widgets.impl;


import org.kuali.student.common.ui.client.widgets.KSButtonAbstract;
import org.kuali.student.common.ui.client.widgets.KSButtonAbstract.ButtonStyle;

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
	
	@Override
	public HandlerRegistration addClickHandler(ClickHandler handler) {
		return anchor.addClickHandler(handler);
	}
	
	@Override
	public void setText(String text){
		this.text = text;
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
	public void init() {
		init("", ButtonStyle.PRIMARY);
		
	}

	@Override
	public void init(String text, ClickHandler handler) {
		init(text, ButtonStyle.PRIMARY);
		anchor.addClickHandler(handler);
		
	}
	
}
