package org.kuali.student.common.ui.client.widgets.table.scroll;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;

@Deprecated
public class EditableLabel extends SimplePanel implements HasClickHandlers,  HasText, HasChangeHandlers {
	Label label = new Label();
	TextBox textBox = new TextBox();

	public EditableLabel() {
		super.setWidget(label);
		textBox.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				label.setText(textBox.getText());
			}
		});
		textBox.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				event.stopPropagation();
			}
			
		});
		textBox.addBlurHandler(new BlurHandler() {
			@Override
			public void onBlur(BlurEvent event) {
				label.setText(textBox.getText());
				EditableLabel.this.setWidget(label);
					
			}
		});
		label.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				textBox.setText(label.getText());
					int width = label.getOffsetWidth();
					int height = label.getOffsetHeight()+3;
					EditableLabel.this.setWidget(textBox);
					textBox.setWidth(""+width+"px");
					textBox.setHeight(""+height+"px");
					textBox.selectAll();
					textBox.setFocus(true);
			}
		});
	}

	@Override
	public String getText() {
		return label.getText();

	}

	@Override
	public void setText(String text) {
		label.setText(text);
	}

	@Override
	public HandlerRegistration addChangeHandler(ChangeHandler handler) {
		return textBox.addChangeHandler(handler);
	}

	@Override
	public HandlerRegistration addClickHandler(ClickHandler handler) {
		return label.addClickHandler(handler);
	}
}