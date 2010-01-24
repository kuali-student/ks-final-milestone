package org.kuali.student.lum.lu.ui.course.client.widgets;

import java.util.ArrayList;

import org.kuali.student.common.ui.client.widgets.KSButton;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

//***************************************************

//public class OutlineManager {
//}
//  public void onModuleLoad() {
//    final OutlineManangerComposite outlineComposite = new OutlineManangerComposite();
//    OutlineNodeModel outlineModel = new OutlineNodeModel();
//    for (int i = 0; i < 8; i++) {
//      OutlineNode aNode = new OutlineNode();
//      aNode.setModel(outlineModel);
//      aNode.setUserObject(new TextBox());
//      outlineModel.addOutlineNode(aNode);

//    }
//    outlineComposite.setModel(outlineModel);
//    outlineModel.addChangeHandler(new ChangeHandler() {
//      public void onChange(ChangeEvent event) {
//        outlineComposite.render();
//      }
//    });

//    outlineComposite.render();
//    RootPanel.get().add(new Label("Outline Manager "));
//    RootPanel.get().add(new Label(" "));
//    RootPanel.get().add(new Label(" "));
    
//    RootPanel.get().add(outlineComposite);
//  }




class OutlineManagerToolbar extends HorizontalPanel {
    KSButton moveUpButton = new KSButton();

    KSButton moveDownButton = new KSButton();

    KSButton indentButton = new KSButton();

    KSButton outdentButton = new KSButton();

    KSButton deleteButton = new KSButton();

  // Button insertPeerAboveButton = new Button("InsertPeerAbove");

    KSButton addPeerButton = new KSButton();

    KSButton addChildButton = new KSButton();

  OutlineNodeModel outlineModel;

  OutlineManagerToolbar() {
    //super(true, false);
    HorizontalPanel buttonPanel = new HorizontalPanel();
    super.setPixelSize(100, 22);
    super.add(buttonPanel);
    buttonPanel.add(moveUpButton);
    buttonPanel.add(moveDownButton);
    buttonPanel.add(indentButton);
    buttonPanel.add(outdentButton);
    buttonPanel.add(deleteButton);
//    buttonPanel.add(addPeerButton);
  //  buttonPanel.add(addChildButton);
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
     //   OutlineManagerToolbar.this.hide();
      }
    });
    moveDownButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        outlineModel.moveDownCurrent();
       // OutlineManagerToolbar.this.hide();
      }
    });
    indentButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        outlineModel.indentCurrent();
        //OutlineManagerToolbar.this.hide();
      }
    });
    outdentButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        outlineModel.outdentCurrent();
        //OutlineManagerToolbar.this.hide();
      }
    });
    deleteButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        outlineModel.deleteCurrent();
        //OutlineManagerToolbar.this.hide();
      }
    });
    addPeerButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        outlineModel.addPeer();
        //OutlineManagerToolbar.this.hide();
      }
    });
    addChildButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        outlineModel.addChild();
        //OutlineManagerToolbar.this.hide();
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

//  OutlineManagerToolbar toolbar = new OutlineManagerToolbar();

  VerticalPanel mainPanel = new VerticalPanel();

  public OutlineManager() {
    super.initWidget(mainPanel);
  }

  public void setModel(OutlineNodeModel model) {
    outlineModel = model;
  //  toolbar.setModel(model);
  }

  public void render() {
    mainPanel.clear();
    OutlineNode[] outlineNodes = outlineModel.toOutlineNodes();
    for (final OutlineNode aNode : outlineNodes) {
      NodePanel nodePanel = new NodePanel();
  //    nodePanel.setToolbar(toolbar);
      nodePanel.setOutlineNode(aNode);
   //   nodePanel.addMouseMoveHandler(new MouseMoveHandler() {
     //   public void onMouseMove(MouseMoveEvent event) {
//          outlineModel.setCurrentNode(aNode);
       // }
     // });
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

    ArrayList<MouseMoveHandler> mouseMoveHandlerList = new ArrayList<MouseMoveHandler>();
    HorizontalPanel horitonalPanel = new HorizontalPanel();
    OutlineNode currentNode; 
    NodePanel() {
      toolbar.setModel(outlineModel);
      super.sinkEvents(Event.ONMOUSEMOVE);
      super.sinkEvents(Event.ONMOUSEOUT);
    }

    public void setOutlineNode(OutlineNode aNode) {
        currentNode = aNode;
      for (int i = 0; i < aNode.getIndentLevel(); i++) {
        Label label = new Label();
        label.setPixelSize(50,10);
        horitonalPanel.add(label);
        
        // space for toolbar
        label = new Label();
        label.setPixelSize(50,10);
        toolbar.insert(label, 0);
      }
      Widget userWidget = (Widget) aNode.getUserObject();
      userWidget.setPixelSize(200, 50);
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
        super.insert(toolbar, 0);
    }
    public void hideToolbar(){
        super.remove(toolbar);//toolbar.setVisible(true);
    }
    public void onBrowserEvent(Event event) {
      switch (DOM.eventGetType(event)) {
      case Event.ONMOUSEMOVE: {
          closeAllToolbar();
 
      //  for (MouseMoveHandler handler : mouseMoveHandlerList) {
        //  handler.onMouseMove(null);
       // }
        outlineModel.setCurrentNode(currentNode);
   //    toolbar.setPopupPosition(super.getAbsoluteLeft(), super.getAbsoluteTop() - 20);
        //toolbar.setVisible(true);
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