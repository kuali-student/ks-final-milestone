package org.kuali.student.lum.lu.ui.course.client.widgets;

public class OutlineNode<T> {
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