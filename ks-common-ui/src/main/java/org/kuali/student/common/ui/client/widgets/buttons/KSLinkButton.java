package org.kuali.student.common.ui.client.widgets.buttons;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;
@Deprecated
public class KSLinkButton extends Composite implements HasClickHandlers{

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
	
	public KSLinkButton(String text){
		this(text, ButtonStyle.PRIMARY);
	}
	
	public KSLinkButton(String text, ButtonStyle style){
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
	}
}
