package org.kuali.student.common.ui.client.widgets.table;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;

public class RuleEditButtonBar extends FlowPanel {
  private Button andButton = new Button("And");
  private Button orButton = new Button("Or");
  private Button ungroupButton = new Button("Ungroup");
  private Button removeFromGroupButton = new Button("Remove from Group");
  
  private Button undoButton = new Button("Undo");
 private Button redoButton = new Button("Redo");
  
  private RuleEditTable ruleEditTable;
  
  public RuleEditButtonBar(){
      add(andButton);
      add(orButton);
      add(ungroupButton);
      add(removeFromGroupButton);
  //   add(undoButton);
  //  add(redoButton);
      disableAllButton();

      andButton.addClickHandler(new ClickHandler() {
          @Override
          public void onClick(ClickEvent event) {
              ruleEditTable.doAnd();
              refreshState();
          }
      });
      orButton.addClickHandler(new ClickHandler() {
          @Override
          public void onClick(ClickEvent event) {
              ruleEditTable.doOr();
              refreshState();
          }
      });

      ungroupButton.addClickHandler(new ClickHandler() {
          @Override
          public void onClick(ClickEvent event) {
              ruleEditTable.doUngroup();
              refreshState();
          }
      });
      removeFromGroupButton.addClickHandler(new ClickHandler() {
          @Override
          public void onClick(ClickEvent event) {
              ruleEditTable.doRemoveFromGroup();
          }
      });
      
      undoButton.addClickHandler(new ClickHandler() {
          @Override
          public void onClick(ClickEvent event) {
              ruleEditTable.doUnDo();
          }
      });
      
      redoButton.addClickHandler(new ClickHandler() {
          @Override
          public void onClick(ClickEvent event) {
              ruleEditTable.doReDo();
          }
      });
  }
  public void setRuleEditTable(RuleEditTable t){
      ruleEditTable = t;
      
  }


  private void disableAllButton(){
      andButton.setEnabled(false);
      orButton.setEnabled(false);
      ungroupButton.setEnabled(false);
      removeFromGroupButton.setEnabled(false);
     undoButton.setEnabled(false);
     redoButton.setEnabled(false);
  }
  
  
  
  public void refreshState(){
      String state = ruleEditTable.getModel().getState();
      disableAllButton();
      if(RuleEditTableModel.NoSelection.equals(state)){
          disableAllButton();
      }else if (RuleEditTableModel.SingleUnGroupedRelationSelected.equals(state)){
          ungroupButton.setEnabled(true);
     }else if (RuleEditTableModel.OneOrMoreGroupedConditionSelected.equals(state)){
          removeFromGroupButton.setEnabled(true);
    //  }else if (RuleEditTableModel.SingleGroupedRelationSelected.equals(state)){
          
      }else if(RuleEditTableModel.MoreThanOneUnGroupedConditionAndUnGroupedRelationSelected.equals(state)){
          andButton.setEnabled(true);
          orButton.setEnabled(true);
      }
      
      if(ruleEditTable.getModel().canRedo()){
          redoButton.setEnabled(true);
      }
      if(ruleEditTable.getModel().canUndo()){
          undoButton.setEnabled(true);
      }
      
  }
}
