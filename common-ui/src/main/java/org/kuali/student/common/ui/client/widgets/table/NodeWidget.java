package org.kuali.student.common.ui.client.widgets.table;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.SimplePanel;

public class NodeWidget extends SimplePanel {
    private Node node;
    HTML html = new HTML();
    CheckBox checkBox = new CheckBox();
    HandlerRegistration clickHandlerHandlerRegistration; 
    public NodeWidget(Node n) {
        node = n;
        super.setWidget(html);
        setNode(n); 
        
        //DOM.setStyleAttribute(checkBox.getElement(), "background", "#ffeeff"); 
    }

    public Node getNode() {
        return node;
    }
    public void installCheckBox() {
        super.setWidget(checkBox);
    }
    public boolean isSelected(){
        return checkBox.getValue() == true;
    }
    public void clearCheckBoxClickHandler(){
        if(clickHandlerHandlerRegistration != null){
            clickHandlerHandlerRegistration.removeHandler();
        }   
    }
    public void addCheckBoxClickHandler(ClickHandler ch){
        clickHandlerHandlerRegistration = checkBox.addClickHandler(ch);
    }

    public void setNode(Node n) {
        node = n;
        html.setHTML(node.getUserObject().toString());
        checkBox.setHTML(node.getUserObject().toString());
    }
}