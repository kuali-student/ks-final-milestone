package org.kuali.student.lum.lu.ui.course.client.widgets;

import java.util.ArrayList;

import org.kuali.student.common.ui.client.theme.Theme;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSImage;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;


class OutlineManagerToolbar extends HorizontalPanel {
    Button moveUpButton = new Button();

    Button moveDownButton = new Button();

    Button indentButton = new Button();

    Button outdentButton = new Button();

    Button deleteButton = new Button();


    Button addPeerButton = new Button();

    Button addChildButton = new Button();

  OutlineNodeModel outlineModel;

  OutlineManagerToolbar() {

    HorizontalPanel buttonPanel = new HorizontalPanel();

    this.setStyleName("KS-LOOutlineManagerToolbar");
    super.add(buttonPanel);
    buttonPanel.add(moveUpButton);
    buttonPanel.add(moveDownButton);
    buttonPanel.add(indentButton);
    buttonPanel.add(outdentButton);
    buttonPanel.add(deleteButton);
    buttonPanel.addStyleName("KS-LOButtonPanel");

    sinkEvents(Event.ONMOUSEMOVE);
    sinkEvents(Event.ONMOUSEOUT);
    
    moveUpButton.sinkEvents(Event.ONMOUSEMOVE);
    moveUpButton.sinkEvents(Event.ONMOUSEOUT);
    moveUpButton.addStyleName("KS-LOMoveUpButton");
    moveDownButton.addStyleName("KS-LOMoveDownButton");
    deleteButton.addStyleName("KS-LODeleteButton");
    indentButton.addStyleName("KS-LOIndentButton");
    outdentButton.addStyleName("KS-LOOutdentButton"); 
    
    moveUpButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        outlineModel.moveUpCurrent();

      }
    });
    moveDownButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        outlineModel.moveDownCurrent();
      }
    });
    indentButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        outlineModel.indentCurrent();
      }
    });
    outdentButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        outlineModel.outdentCurrent();
      }
    });
    deleteButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        outlineModel.deleteCurrent();
      }
    });
    addPeerButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        outlineModel.addPeer();
      }
    });
    addChildButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        outlineModel.addChild();
      }
    });
  }

  public void setModel(OutlineNodeModel model) {
    outlineModel = model;
  }

  public void updateButtonStates() {
    if(  this.outlineModel.isMoveUpable()){
        moveUpButton.setEnabled(true);
        moveUpButton.removeStyleName("KS-LOMoveUpButtonDisabled");
        moveUpButton.addStyleName("KS-LOMoveUpButton");
    }else{
        moveUpButton.setEnabled(false);
        moveUpButton.removeStyleName("KS-LOMoveUpButton");
        moveUpButton.addStyleName("KS-LOMoveUpButtonDisabled");
    }
    if(this.outlineModel.isDeletable()){
        deleteButton.setEnabled(true);
        deleteButton.removeStyleName("KS-LODeleteButtonDisabled");
        deleteButton.addStyleName("KS-LODeleteButton");
    } else{
        deleteButton.setEnabled(false);
        deleteButton.removeStyleName("KS-LODeleteButton");
        deleteButton.addStyleName("KS-LODeleteButtonDisabled");
    }
    if(this.outlineModel.isIndentable()){
        indentButton.setEnabled(true);
        indentButton.removeStyleName("KS-LOIndentButtonDisabled");
        indentButton.addStyleName("KS-LOIndentButton");
    }else{
        indentButton.setEnabled(false);
        indentButton.removeStyleName("KS-LOIndentButton");
        indentButton.addStyleName("KS-LOIndentButtonDisabled");
    }
    if(this.outlineModel.isMoveDownable()){
        moveDownButton.setEnabled(true);
        moveDownButton.removeStyleName("KS-LOMoveDownButtonDisabled");
        moveDownButton.addStyleName("KS-LOMoveDownButton");
    }else{
        moveDownButton.setEnabled(false);
        moveDownButton.removeStyleName("KS-LOMoveDownButton");
        moveDownButton.addStyleName("KS-LOMoveDownButtonDisabled");
    }
    if(this.outlineModel.isOutdentable()){
        outdentButton.setEnabled(true);
        outdentButton.removeStyleName("KS-LOOutdentButtonDisabled");
        outdentButton.addStyleName("KS-LOOutdentButton"); 
    }else{
        outdentButton.setEnabled(false);
        outdentButton.removeStyleName("KS-LOOutdentButton");
        outdentButton.addStyleName("KS-LOOutdentButtonDisabled"); 
    }
  }
}


public class OutlineManager extends Composite {
  OutlineNodeModel outlineModel;


  VerticalPanel mainPanel = new VerticalPanel();

  public OutlineManager() {
    super.initWidget(mainPanel);
    mainPanel.setStyleName("KS-LOMainPanel");
  }

  public void setModel(OutlineNodeModel model) {
    outlineModel = model;
  }

  public void render() {
    mainPanel.clear();
    OutlineNode[] outlineNodes = outlineModel.toOutlineNodes();
    for (final OutlineNode aNode : outlineNodes) {
      NodePanel nodePanel = new NodePanel();
      nodePanel.setStyleName("KS-LONodePanel");
      nodePanel.setOutlineNode(aNode);

      mainPanel.add(nodePanel);
    }
  }
  public void closeAllToolbar(){
      for(int i=0;i< mainPanel.getWidgetCount();i++){
          if(mainPanel.getWidget(i) instanceof NodePanel){
              NodePanel p = (NodePanel)mainPanel.getWidget(i);
              p.hideToolbar();
          }
      }
  }
  class NodePanel extends  VerticalPanel{
   OutlineManagerToolbar toolbar = new OutlineManagerToolbar();
   HorizontalPanel emptySpacePanel = new HorizontalPanel();
    ArrayList<MouseMoveHandler> mouseMoveHandlerList = new ArrayList<MouseMoveHandler>();
    HorizontalPanel horitonalPanel = new HorizontalPanel();
    OutlineNode currentNode; 
    NodePanel() {
      toolbar.setModel(outlineModel);
      horitonalPanel.setStyleName("KS-LOHNodePanel");
      super.sinkEvents(Event.ONMOUSEMOVE);
      super.sinkEvents(Event.ONMOUSEOUT);
      emptySpacePanel.setStyleName("KS-LOOutlineManagerToolbar");
      KSImage ieHack = Theme.INSTANCE.getCommonImages().getSpacer();
      emptySpacePanel.add(ieHack);
      super.insert(emptySpacePanel,0);
    }

    public void setOutlineNode(OutlineNode aNode) {
        currentNode = aNode;
      for (int i = 0; i < aNode.getIndentLevel(); i++) {
        Label label = new Label();
        label.setStyleName("KS-LONodeIndent");
        horitonalPanel.add(label);
        
        // space for toolbar
        label = new Label();
        label.setStyleName("KS-LONodeIndentToolbar");
        toolbar.insert(label, 0);
      }
      Widget userWidget = (Widget) aNode.getUserObject();
      userWidget.setStyleName("KS-LOaNode");
      horitonalPanel.add(userWidget);

      add(horitonalPanel);
    }

    public void addMouseMoveHandler(MouseMoveHandler handler) {
      mouseMoveHandlerList.add(handler);
    }

    public void setToolbar(OutlineManagerToolbar t) {
      toolbar = t;
    }
    public void showToolbar(){
        super.remove(emptySpacePanel);
        super.insert(toolbar, 0);
    }
    public void hideToolbar(){
        super.remove(toolbar);
        super.insert(emptySpacePanel,0);
    }
    public void onBrowserEvent(Event event) {
      switch (DOM.eventGetType(event)) {
      case Event.ONMOUSEMOVE: {
          closeAllToolbar();
 

        outlineModel.setCurrentNode(currentNode);

        showToolbar();
        toolbar.updateButtonStates();
        break;
      }
      case Event.ONMOUSEOUT:
          break;
      }
      super.onBrowserEvent(event);
    }
  }
}