package org.kuali.student.common.ui.client.widgets.table;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTML;

public class NodeWidget extends FocusPanel {
    private Node node;
    HTML html = new HTML();
    CheckBox checkBox = new CheckBox();
    HandlerRegistration handlerRegistration;
    public NodeWidget(Node n) {
        node = n;
        super.setWidth("100%");
        super.setHeight("100%");

        super.setWidget(checkBox);
        setNode(n);

        checkBox.addBlurHandler(new BlurHandler() {
            @Override
            public void onBlur(BlurEvent event) {
                DOM.setStyleAttribute(NodeWidget.this.getElement(), "background", "#ffffff");
            }
        });
        checkBox.addFocusHandler(new FocusHandler() {
            @Override
            public void onFocus(FocusEvent event) {
                DOM.setStyleAttribute(NodeWidget.this.getElement(), "background", "#ffeeff");
            }
        });
       
        super.addBlurHandler(new BlurHandler() {
            @Override
            public void onBlur(BlurEvent event) {
                DOM.setStyleAttribute(NodeWidget.this.getElement(), "background", "#ffffff");
            }
        });
        super.addFocusHandler(new FocusHandler() {
            @Override
            public void onFocus(FocusEvent event) {
                DOM.setStyleAttribute(NodeWidget.this.getElement(), "background", "#ffeeff");
            }
        });
        super.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
              //  event.stopPropagation();
                boolean before = checkBox.getValue();
                checkBox.setValue(!before);

                ValueChangeEvent.fireIfNotEqual(checkBox, before,checkBox.getValue());
                checkBox.setFocus(true);
                setFocus(true);
            }
        });

    }

    public Node getNode() {
        return node;
    }

    public void installCheckBox() {
        super.setWidget(checkBox);
        //contentPanel.setWidget(checkBox);
    }

    public boolean isSelected() {
        return checkBox.getValue() == true;
    }

    public void addSelectionHandler(ValueChangeHandler<Boolean> ch) {
        if (handlerRegistration != null) {
            handlerRegistration.removeHandler();
        }
        handlerRegistration = checkBox.addValueChangeHandler(ch);
    }

    public void setNode(Node n) {
        node = n;
        html.setHTML(node.getUserObject().toString());
        checkBox.setHTML(node.getUserObject().toString());
    }
}
