package org.kuali.student.common.ui.client.widgets.table;

import java.util.List;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class RuleEditor extends VerticalPanel{
    HorizontalPanel ungroupedConditionPanel = new HorizontalPanel();
    VerticalPanel treeTablePanel = new VerticalPanel();
    
    RuleEditorModel tableModel = new RuleEditorModel();

    RuleEditButtonBar buttonBar = new RuleEditButtonBar(tableModel);
    BooleanExpressionInputPanel booleanExpressionInput = new BooleanExpressionInputPanel(tableModel);    
    
    public RuleEditor() {
      //  treeTablePanel.setPixelSize(300,300);
        //treeTablePanel.add(new Label(""));
        this.add(ungroupedConditionPanel);
        this.add(treeTablePanel);
        this.add(buttonBar);
        this.add(booleanExpressionInput);
        
        tableModel.addModelChangedEvent(new ModelChangedListener(){
            @Override
            public void modelChanged(RuleEditorModel model) {
                refresh();
                
            }
        });
    }
  //  public void setNodeList(List<Node<Token>> list){
    //    tableModel.setNodeList(list);
   // }
    public void setNodeFromExpressionEditor(Node<Token> node){
        tableModel.setNodeFromExpressionEditor(node);
    }
    public RuleEditorModel getModel(){
        return tableModel;
    }

    public void refresh(){
        booleanExpressionInput.addExpressionFromTableEditor(tableModel.getExpressionString());
        ungroupedConditionPanel.clear();
        treeTablePanel.clear();
        for(Widget w: tableModel.getWidgetList()){
            if(w instanceof NodeWidget){
                ungroupedConditionPanel.add(w);
            }else if(w instanceof TreeTable){
                treeTablePanel.add(w);
            }
        }
    }

}
