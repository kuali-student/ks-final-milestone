package org.kuali.student.lum.lu.ui.course.client.widgets;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.TextBox;

public 
class OutlineNodeModel {
    private ArrayList<OutlineNode> outlineNodeList = new ArrayList<OutlineNode>();

    private ArrayList<ChangeHandler> changeHandlerList = new ArrayList<ChangeHandler>();

    private OutlineNode currentNode;
    
    public void clearNodes(){
        outlineNodeList = new ArrayList<OutlineNode>();
    }
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
//      if (index == -1 || index == outlineNodeList.size() - 1) {
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
//      List<OutlineNode> childList = getChildList(currentNode);
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
//      List<OutlineNode> list = getSiblingList();

    //  if (list.size() == 0) { // only child
      //  return false;
     // }
     // int index = list.indexOf(currentNode);
      
//      if (index == 0 && currentNode.getIndentLevel() == 0) {// first child
    //    return false;
      //}
      if(outlineNodeList.size() == 1){
        return false;
      }
      return true;
    }

  }