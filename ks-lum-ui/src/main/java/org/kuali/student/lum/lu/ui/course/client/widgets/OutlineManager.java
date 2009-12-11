package org.kuali.student.lum.lu.ui.course.client.widgets;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
public class OutlineManager {

}
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
class OutlineNodeModel {
  private ArrayList<OutlineNode> outlineNodeList = new ArrayList<OutlineNode>();

  private ArrayList<ChangeHandler> changeHandlerList = new ArrayList<ChangeHandler>();

  private OutlineNode currentNode;

  public void setCurrentNode(OutlineNode aNode) {
    currentNode = aNode;
  }

  public void addChangeHandler(ChangeHandler ch) {
    changeHandlerList.add(ch);
  }

  private void fireChangeEvents() {
    for (ChangeHandler ch : changeHandlerList) {
      ch.onChange(null);
    }
  }

  public void moveUpCurrent() {
    if (this.isMoveUpable() == false) {
      return;
    }
 //   int index = outlineNodeList.indexOf(currentNode);
//    if (index == -1 || index == outlineNodeList.size() - 1) {
  //    return;
   // }
    List<OutlineNode> siblingList = getSiblingList();
    int indexInSibling = siblingList.indexOf(currentNode);
    OutlineNode nextNodeInSibling = siblingList.get(indexInSibling - 1);

    List<OutlineNode> childList = getChildList(currentNode);
    childList.add(0, currentNode);// add parent
    for (int i = 0; i < childList.size(); i++) {
      OutlineNode aNode = childList.get(i);
      outlineNodeList.remove(aNode);
    }

    int moveToIndex = outlineNodeList.indexOf(nextNodeInSibling);

    for (int i = 0; i < childList.size(); i++) {
      OutlineNode aNode = childList.get(i);
      outlineNodeList.add(moveToIndex + i, aNode);
    }
    fireChangeEvents();
  }
  public void moveDownCurrent() {
    if (this.isMoveDownable() == false) {
      return;
    }
    int index = outlineNodeList.indexOf(currentNode);
    if (index == -1 || index == outlineNodeList.size() - 1) {
      return;
    }
    List<OutlineNode> siblingList = getSiblingList();
    int indexInSibling = siblingList.indexOf(currentNode);
    OutlineNode nextNodeInSibling = siblingList.get(indexInSibling + 1);

    List<OutlineNode> childList = getChildList(currentNode);
    childList.add(0, currentNode);// add parent
    for (int i = 0; i < childList.size(); i++) {
      OutlineNode aNode = childList.get(i);
      outlineNodeList.remove(aNode);
    }

    List<OutlineNode> nextNodeChildList = getChildList(nextNodeInSibling);
    int moveToIndex = -1;
    if(nextNodeChildList.size() != 0){
      moveToIndex = outlineNodeList.indexOf(nextNodeChildList.get(nextNodeChildList.size()-1));
    }else{
      moveToIndex =outlineNodeList.indexOf(nextNodeInSibling);
    }

    for (int i = 0; i < childList.size(); i++) {
      OutlineNode aNode = childList.get(i);
      outlineNodeList.add(moveToIndex + 1 + i, aNode);
    }

    fireChangeEvents();
  }

  public void indentCurrent() {
    if (this.isIndentable()) {
      currentNode.indent();
      fireChangeEvents();
    }
  }

  public void outdentCurrent() {
    if (this.isOutdentable()) {
      List<OutlineNode> childList = getChildList(currentNode);
      childList.add(0, currentNode);// add parent
      for(OutlineNode aNode:childList){
        aNode.outdent();
      }
      fireChangeEvents();
    }
  }

  public void deleteCurrent() {
    if (this.isDeletable()) {
      List<OutlineNode> childList = getChildList(currentNode);
      childList.add(0, currentNode);// add parent
      for (int i = 0; i < childList.size(); i++) {
        OutlineNode aNode = childList.get(i);
        outlineNodeList.remove(aNode);
      }
      fireChangeEvents();
    }
  }

  public void addPeer() {
    int index = outlineNodeList.indexOf(currentNode);
    OutlineNode aNode = new OutlineNode();
    aNode.setUserObject(new TextBox());
    aNode.setIndentLevel(currentNode.getIndentLevel());
    outlineNodeList.add(index + 1, aNode);
    fireChangeEvents();
  }

  public void addChild() {
    int index = outlineNodeList.indexOf(currentNode);
    OutlineNode aNode = new OutlineNode();
    aNode.setUserObject(new TextBox());
    aNode.setIndentLevel(currentNode.getIndentLevel() + 1);
    outlineNodeList.add(index + 1, aNode);
    fireChangeEvents();
  }

  public void addOutlineNode(OutlineNode aNode) {
    outlineNodeList.add(aNode);
  }

  public OutlineNode[] toOutlineNodes() {
    return outlineNodeList.toArray(new OutlineNode[outlineNodeList.size()]);
  }

  public List<OutlineNode> getChildList(OutlineNode aNode) {
    int index = outlineNodeList.indexOf(aNode);
    List<OutlineNode> childList = new ArrayList<OutlineNode>();
    for (int i = index + 1; i < outlineNodeList.size(); i++) {
      if (outlineNodeList.get(i).getIndentLevel() > aNode.getIndentLevel()) {
        childList.add(outlineNodeList.get(i));
      } else {
        break;
      }
    }
    return childList;
  }

  public List<OutlineNode> getSiblingList() {
    List<OutlineNode> siblingList = new ArrayList<OutlineNode>();
    int index = outlineNodeList.indexOf(currentNode);
    // if first level
    if (currentNode.getIndentLevel() == 0) {
      for (int i = 0; i < outlineNodeList.size(); i++) {
        if (outlineNodeList.get(i).getIndentLevel() == 0) {
          siblingList.add(outlineNodeList.get(i));
        }
      }
      return siblingList;
    }
    // not first level
    // get parent first and then get all Siblings
    OutlineNode parentNode = null;
    for (int i = index - 1; i >= 0; i--) {
      if (outlineNodeList.get(i).getIndentLevel() - currentNode.getIndentLevel() == -1) {
        parentNode = outlineNodeList.get(i);
        break;
      }
    }
    int parentIndex = outlineNodeList.indexOf(parentNode);
    for (int i = parentIndex + 1; i < outlineNodeList.size(); i++) {
      if (outlineNodeList.get(i).getIndentLevel() - parentNode.getIndentLevel() == 1) {
        siblingList.add(outlineNodeList.get(i));
      } else if (outlineNodeList.get(i).getIndentLevel() == parentNode.getIndentLevel()) {
        break;
      }
    }
    return siblingList;
  }

  public boolean isIndentable() {
    int index = outlineNodeList.indexOf(currentNode);
    if (index == 0) {
      return false;
    }
    // if current node is the only child

    List<OutlineNode> siblingList = getSiblingList();
    if (siblingList.size() == 1) {
      return false;
    }
    //first kid
    int indexInSiblings = siblingList.indexOf(currentNode);
    if(indexInSiblings == 0){
      return false;
    }

    return true;
  }

  public boolean isOutdentable() {
    if (currentNode.getIndentLevel() == 0) {// first level
      return false;
    }
    // have kids
//    List<OutlineNode> childList = getChildList(currentNode);
  //  if(childList.size()>0){
    //  return false;
   // }
    return true;
  }

  public boolean isMoveUpable() {
    List<OutlineNode> list = getSiblingList();
    if (list.size() == 1) { // only child
      return false;
    }
    int index = list.indexOf(currentNode);
    if (index == 0) {// first child
      return false;
    }
    return true;
  }

  public boolean isMoveDownable() {
    List<OutlineNode> list = getSiblingList();
    if (list.size() == 0) { // only child
      return false;
    }
    int index = list.indexOf(currentNode);
    if (index == list.size() - 1) {// last child
      return false;
    }
    return true;
  }

  public boolean isDeletable() {
//    List<OutlineNode> list = getSiblingList();

  //  if (list.size() == 0) { // only child
    //  return false;
   // }
   // int index = list.indexOf(currentNode);
    
//    if (index == 0 && currentNode.getIndentLevel() == 0) {// first child
  //    return false;
    //}
    if(outlineNodeList.size() == 1){
      return false;
    }
    return true;
  }

}

class OutlineNode<T> {
  int indentLevel = 0;

  boolean isCurrentNode = false;

  T userObject;

  OutlineNodeModel outlineModel;

  public void setModel(OutlineNodeModel model) {
    outlineModel = model;
  }

  public void setCurrentNode() {
    outlineModel.setCurrentNode(this);
  }

  public T getUserObject() {
    return userObject;
  }

  public void setUserObject(T u) {
    userObject = u;
  }

  public void indent() {
    indentLevel++;
  }

  public void outdent() {
    indentLevel--;
  }

  public void setIndentLevel(int level) {
    indentLevel = level;
  }

  public int getIndentLevel() {
    return indentLevel;
  }
}

class OutlineManagerToolbar extends PopupPanel {
  Button moveUpButton = new Button("Up");

  Button moveDownButton = new Button("Down");

  Button indentButton = new Button("Indent");

  Button outdentButton = new Button("Outdent");

  Button deleteButton = new Button("Delete");

  // Button insertPeerAboveButton = new Button("InsertPeerAbove");

  Button addPeerButton = new Button("AddPeer");

  Button addChildButton = new Button("AddChild");

  OutlineNodeModel outlineModel;

  OutlineManagerToolbar() {
    super(true, false);
    HorizontalPanel buttonPanel = new HorizontalPanel();
    super.add(buttonPanel);
    buttonPanel.add(moveUpButton);
    buttonPanel.add(moveDownButton);
    buttonPanel.add(indentButton);
    buttonPanel.add(outdentButton);
    buttonPanel.add(deleteButton);
    buttonPanel.add(addPeerButton);
    buttonPanel.add(addChildButton);

    moveUpButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        outlineModel.moveUpCurrent();
        OutlineManagerToolbar.this.hide();
      }
    });
    moveDownButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        outlineModel.moveDownCurrent();
        OutlineManagerToolbar.this.hide();
      }
    });
    indentButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        outlineModel.indentCurrent();
        OutlineManagerToolbar.this.hide();
      }
    });
    outdentButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        outlineModel.outdentCurrent();
        OutlineManagerToolbar.this.hide();
      }
    });
    deleteButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        outlineModel.deleteCurrent();
        OutlineManagerToolbar.this.hide();
      }
    });
    addPeerButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        outlineModel.addPeer();
        OutlineManagerToolbar.this.hide();
      }
    });
    addChildButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        outlineModel.addChild();
        OutlineManagerToolbar.this.hide();
      }
    });
  }

  public void setModel(OutlineNodeModel model) {
    outlineModel = model;
  }

  public void updateButtonStates() {
    this.moveUpButton.setEnabled(this.outlineModel.isMoveUpable());
    this.deleteButton.setEnabled(this.outlineModel.isDeletable());
    this.indentButton.setEnabled(this.outlineModel.isIndentable());
    this.moveDownButton.setEnabled(this.outlineModel.isMoveDownable());
    this.outdentButton.setEnabled(this.outlineModel.isOutdentable());
    
  }
}


class OutlineMananger extends Composite {
  OutlineNodeModel outlineModel;

  OutlineManagerToolbar toolbar = new OutlineManagerToolbar();

  VerticalPanel mainPanel = new VerticalPanel();

  public OutlineMananger() {
    super.initWidget(mainPanel);
  }

  public void setModel(OutlineNodeModel model) {
    outlineModel = model;
    toolbar.setModel(model);
  }

  public void render() {
    mainPanel.clear();
    OutlineNode[] outlineNodes = outlineModel.toOutlineNodes();
    for (final OutlineNode aNode : outlineNodes) {
      NodePanel nodePanel = new NodePanel();
      nodePanel.setToolbar(toolbar);
      nodePanel.setOutlineNode(aNode);
      nodePanel.addMouseMoveHandler(new MouseMoveHandler() {
        public void onMouseMove(MouseMoveEvent event) {
          outlineModel.setCurrentNode(aNode);
        }
      });
      mainPanel.add(nodePanel);
    }
  }
  class NodePanel extends HorizontalPanel {
    OutlineManagerToolbar toolbar;

    ArrayList<MouseMoveHandler> mouseMoveHandlerList = new ArrayList<MouseMoveHandler>();

    NodePanel() {

      super.sinkEvents(Event.ONMOUSEMOVE);
      super.sinkEvents(Event.ONMOUSEOUT);
    }

    public void setOutlineNode(OutlineNode aNode) {
      for (int i = 0; i < aNode.getIndentLevel(); i++) {
        Label label = new Label();
        label.setPixelSize(50,50);
        add(label);
      }
      Widget userWidget = (Widget) aNode.getUserObject();
      userWidget.setPixelSize(200, 50);
      add(userWidget);

    }

    public void addMouseMoveHandler(MouseMoveHandler handler) {
      mouseMoveHandlerList.add(handler);
    }

    public void setToolbar(OutlineManagerToolbar t) {
      toolbar = t;
    }

    public void onBrowserEvent(Event event) {
      switch (DOM.eventGetType(event)) {
      case Event.ONMOUSEMOVE: {
     //   if (toolbar.isShowing()) {
         // break;
       // }
        for (MouseMoveHandler handler : mouseMoveHandlerList) {
          handler.onMouseMove(null);
        }

        toolbar.setPopupPosition(super.getAbsoluteLeft(), super.getAbsoluteTop() - 20);

        toolbar.show();
        toolbar.updateButtonStates();

        break;
      }
      case Event.ONMOUSEOUT:
    //    toolbar.hide();
        // outlineNode.setCurrentNode(false);

        break;
      }
      super.onBrowserEvent(event);
    }
  }

}