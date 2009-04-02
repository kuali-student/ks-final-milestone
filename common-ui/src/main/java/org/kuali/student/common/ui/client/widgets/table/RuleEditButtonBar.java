package org.kuali.student.common.ui.client.widgets.table;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;

public class RuleEditButtonBar extends FlowPanel {
  private Button andButton = new Button("And");
  private Button orButton = new Button("Or");
  private Button toggleButton = new Button("Toggle");
  private Button removeFromGroupButton = new Button("Remove from Group");
  private Button addToGroupButton = new Button("Add to Group");
  private Button undoButton = new Button("Undo");
 private Button redoButton = new Button("Redo");
  
  private RuleEditTable ruleEditTable;
  
  public RuleEditButtonBar(){
      add(andButton);
      add(orButton);
      add(toggleButton);
      add(removeFromGroupButton);
      add(addToGroupButton);
      add(undoButton);
      add(redoButton);
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

      removeFromGroupButton.addClickHandler(new ClickHandler() {
          @Override
          public void onClick(ClickEvent event) {
              ruleEditTable.doRemoveFromGroup();
              refreshState();
          }
      });
      addToGroupButton.addClickHandler(new ClickHandler() {
          @Override
          public void onClick(ClickEvent event) {
              ruleEditTable.doAddToGroup();
              refreshState();
          }
      });
      
      undoButton.addClickHandler(new ClickHandler() {
          @Override
          public void onClick(ClickEvent event) {
              ruleEditTable.doUnDo();
              refreshState();
          }
      });
      
      redoButton.addClickHandler(new ClickHandler() {
          @Override
          public void onClick(ClickEvent event) {
              ruleEditTable.doReDo();
              refreshState();
          }
      });
      toggleButton.addClickHandler(new ClickHandler() {
          @Override
          public void onClick(ClickEvent event) {
              ruleEditTable.doToggle();
              refreshState();
          }
      });
  }
  public void setRuleEditTable(RuleEditTable t){
      ruleEditTable = t;
  }


  private void disableAllButton(){
      andButton.setEnabled(false);
      orButton.setEnabled(false);
      toggleButton.setEnabled(false);
      removeFromGroupButton.setEnabled(false);
      addToGroupButton.setEnabled(false);
      undoButton.setEnabled(false);
      redoButton.setEnabled(false);
  }
  private void hideAllButton(){
      andButton.setVisible(false);
      orButton.setVisible(false);
      toggleButton.setVisible(false);
      removeFromGroupButton.setVisible(false);
      addToGroupButton.setVisible(false);
      undoButton.setVisible(false);
      redoButton.setVisible(false);
  }

  public void refreshState(){
      disableAllButton();
     // hideAllButton();
      if( ruleEditTable.getModel().isAndOrOrable()){
          andButton.setEnabled(true);
          orButton.setEnabled(true);
          andButton.setVisible(true);
          orButton.setVisible(true);
      }
      if(ruleEditTable.getModel().isTogglable()){
          toggleButton.setEnabled(true);
          toggleButton.setVisible(true);
      }

      if(ruleEditTable.getModel().isAddable()){
          addToGroupButton.setEnabled(true);
          addToGroupButton.setVisible(true);
          
      }
      if(ruleEditTable.getModel().isRemovable()){
          removeFromGroupButton.setEnabled(true);
          removeFromGroupButton.setVisible(true);
      }
      
      if(ruleEditTable.getModel().canRedo()){
          redoButton.setEnabled(true);
          redoButton.setVisible(true);
      }
      if(ruleEditTable.getModel().canUndo()){
          undoButton.setEnabled(true);
          undoButton.setVisible(true);
      }      
  }
}
