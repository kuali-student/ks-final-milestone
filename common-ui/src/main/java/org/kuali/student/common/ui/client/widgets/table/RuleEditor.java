package org.kuali.student.common.ui.client.widgets.table;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class RuleEditor extends VerticalPanel{
    HorizontalPanel ungroupedConditionPanel = new HorizontalPanel();
    VerticalPanel treeTablePanel = new VerticalPanel();
    
    RuleEditorModel tableModel = new RuleEditorModel();

    RuleEditButtonBar buttonBar = new RuleEditButtonBar(tableModel);
    
    public RuleEditor() {
        this.add(ungroupedConditionPanel);
        this.add(treeTablePanel);
        this.add(buttonBar);
        
        tableModel.addModelChangedEvent(new ModelChangedListener(){
            @Override
            public void modelChanged(RuleEditorModel model) {
                refresh();
                
            }
        });
    }
    public void setNodeList(List<Node<Token>> list){
        tableModel.setNodeList(list);
    }
    public void setNodeFromExpressionEditor(Node<Token> node){
        tableModel.setNodeFromExpressionEditor(node);
    }
    public RuleEditorModel getModel(){
        return tableModel;
    }

    public void refresh(){
        //clear();
        ungroupedConditionPanel.clear();
        treeTablePanel.clear();
        for(Widget w: tableModel.getWidgetList()){
            if(w instanceof NodeWidget){
                ungroupedConditionPanel.add(w);
            }else if(w instanceof TreeTable){
                treeTablePanel.add(w);
            }
            
        }

        String str = getModel().getExpressionString();
    }
/*
    public void doRemoveFromGroup(){
        getModel().doRemoveFromGroup();
        refresh();
    }
    public void doAddToGroup(){
        getModel().doAddToGroup();
        refresh();
    }
    public void doAnd(){
        getModel().doAnd();
        refresh();
    }
    public void doToggle(){
        getModel().doToggle();
        refresh();
    }
    public void doOr(){
        getModel().doOr();
        refresh();
    }
    public void doReDo(){
        getModel().doRedo();
        refresh();
    }
    public void doUnDo(){
        getModel().doUndo();
        refresh();
    }
    */
}
