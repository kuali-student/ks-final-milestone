package org.kuali.student.lum.ui.requirements.client.view;

import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.table.Node;
import org.kuali.student.lum.ui.requirements.client.model.ReqComponentVO;
import org.kuali.student.lum.ui.requirements.client.model.StatementVO;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class RuleNodeWidget extends SimplePanel {
    private Node node;
    HTML html = new HTML();
    CheckBox checkBox = new CheckBox();
    KSButton edit = new KSButton("Edit");
    KSButton toggle = new KSButton("Toggle");
    HandlerRegistration clickHandlerRegistration;
    HandlerRegistration toggleHandlerRegistration;
    HandlerRegistration editClauseHandlerRegistration;
//    SimplePanel ruleNodeWidgetPanel new 
    public RuleNodeWidget(Node n) {
        node = n;
        setNode(n); 
        
       // DOM.setStyleAttribute(checkBox.getElement(), "background", "#ffeeff"); 
       //DOM.setStyleAttribute(checkBox.getElement(), "fontStyle", "italic");
    }

    public Node getNode() {
        return node;
    }
    public boolean isSelected(){
        return checkBox.getValue() == true;
    }
    public void clearCheckBoxClickHandler(){
        if(clickHandlerRegistration != null){
            clickHandlerRegistration.removeHandler();
        }   
    }
    public void addCheckBoxClickHandler(ClickHandler ch){
        clickHandlerRegistration = checkBox.addClickHandler(ch);
    }
    
    public void addToggleHandler(ClickHandler ch) {
        toggleHandlerRegistration = toggle.addClickHandler(ch);
    }
    
    public void clearToggleHandler() {
        if (toggleHandlerRegistration != null) {
            toggleHandlerRegistration.removeHandler();
        }
    }
    
    public void addEditClauseHandler(ClickHandler ch) {
        editClauseHandlerRegistration = edit.addClickHandler(ch);
    }

    public void clearEditClauseHandler() {
        if (editClauseHandlerRegistration != null) {
            editClauseHandlerRegistration.removeHandler();
        }
    }

    public void setNode(Node n) {
        Object userObject = null;
        node = n;
        userObject = node.getUserObject();
        if (userObject instanceof StatementVO) {
            StatementVO statementVO = (StatementVO) userObject;
            VerticalPanel checkBoxAndToggle = new VerticalPanel();
            super.setWidget(checkBoxAndToggle);
            checkBoxAndToggle.add(checkBox);
            checkBoxAndToggle.add(toggle);
            html.setHTML(node.getUserObject().toString());
            checkBox.setHTML(node.getUserObject().toString());
            checkBox.setValue(new Boolean(statementVO.isCheckBoxOn()), false);
        } else if (userObject instanceof ReqComponentVO) {
            ReqComponentVO reqComponentVO = (ReqComponentVO) userObject;
            HorizontalPanel checkBoxAndEdit= new HorizontalPanel();
            super.setWidget(checkBoxAndEdit);
            checkBoxAndEdit.add(checkBox);
            checkBoxAndEdit.add(edit);
            html.setHTML(node.getUserObject().toString());
            checkBox.setHTML(node.getUserObject().toString());
            checkBox.setValue(new Boolean(reqComponentVO.isCheckBoxOn()));
        } else {
            super.setWidget(html);
            html.setHTML(node.getUserObject().toString());
            checkBox.setHTML(node.getUserObject().toString());
        }
    }
}