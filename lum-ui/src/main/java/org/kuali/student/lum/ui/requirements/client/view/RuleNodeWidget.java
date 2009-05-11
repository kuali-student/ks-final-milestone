package org.kuali.student.lum.ui.requirements.client.view;

import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSLabel;
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
    private boolean showControls;
    HTML html = new HTML();
    CheckBox checkBox = new CheckBox();
    KSLabel edit = new KSLabel("Edit");
    KSButton toggle = new KSButton("Toggle");
    HorizontalPanel checkBoxAndEdit;
    HandlerRegistration clickHandlerRegistration;
    HandlerRegistration toggleHandlerRegistration;
    HandlerRegistration editClauseHandlerRegistration;
    HandlerRegistration textClickHandlerRegistration;
    
    public RuleNodeWidget(Node n) {
        init(n, true);
    }
    
    public RuleNodeWidget(Node n, boolean showControls) {
        init(n, showControls);
    }
    
    private void init(Node n, boolean showControls) {
        node = n;
        this.showControls = showControls;
        setNode(n); 
    }

    public boolean isShowControls() {
        return showControls;
    }

    public void setShowControls(boolean showControls) {
        this.showControls = showControls;
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
    
    public void addTextClicHandler(ClickHandler ch) {
       // textClickHandlerRegistration = checkBoxAndEdit.addClickHandler(ch);
    }
    
    public void clearTextClickHandler() {
        if (textClickHandlerRegistration != null) {
            textClickHandlerRegistration.removeHandler();
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
            if (showControls) {
                checkBoxAndToggle.add(checkBox);
                checkBoxAndToggle.add(toggle);
                checkBoxAndToggle.setStyleName((statementVO.isCheckBoxOn() ? "KS-ReqComp-Selected" : "KS-ReqComp-DeSelected"));
            } else {
                checkBoxAndToggle.add(html);
                checkBoxAndToggle.setStyleName("KS-ReqComp-DeSelected");
            }
            html.setHTML(node.getUserObject().toString());
            checkBox.setHTML(node.getUserObject().toString());
            checkBox.setValue(new Boolean(statementVO.isCheckBoxOn()), false);
        } else if (userObject instanceof ReqComponentVO) {
            ReqComponentVO reqComponentVO = (ReqComponentVO) userObject;
            KSLabel rcLabel = null;
            checkBoxAndEdit= new HorizontalPanel();
            checkBoxAndEdit.setStyleName("KS-Rules-Table-Cell-Margin");
            super.setWidget(checkBoxAndEdit);
            if (reqComponentVO.getGuiReferenceLabelId() != null) {
                rcLabel = new KSLabel(reqComponentVO.getGuiReferenceLabelId());
                checkBoxAndEdit.add(rcLabel);
            }
            if (showControls) {
                checkBoxAndEdit.add(checkBox);
                edit.addStyleName("KS-Rules-Edit-Link");
                checkBoxAndEdit.add(edit);
                checkBoxAndEdit.addStyleName((reqComponentVO.isCheckBoxOn() ? "KS-ReqComp-Selected" : "KS-ReqComp-DeSelected"));
            } else {
                checkBoxAndEdit.add(html);
                checkBoxAndEdit.addStyleName("KS-ReqComp-DeSelected");
            }
           // html.setHTML(node.getUserObject().toString());
            checkBox.setHTML(node.getUserObject().toString());            
            checkBox.setValue(new Boolean(reqComponentVO.isCheckBoxOn()));
        } else {
            super.setWidget(html);
            html.setHTML(node.getUserObject().toString());
            checkBox.setHTML(node.getUserObject().toString());
        }
    }
}