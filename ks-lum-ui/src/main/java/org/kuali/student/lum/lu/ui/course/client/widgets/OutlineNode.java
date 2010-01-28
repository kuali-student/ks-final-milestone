package org.kuali.student.lum.lu.ui.course.client.widgets;

public class OutlineNode<T> {
    int indentLevel = 0;

    boolean isCurrentNode = false;

    T userObject;

    OutlineNodeModel<T> outlineModel;
    
    // TODO M4 - some way to subclass OutlineNode? 
    private Object opaque;

    public void setModel(OutlineNodeModel<T> model) {
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

	public void setOpaque(Object opaque) {
		this.opaque = opaque;
	}

	public Object getOpaque() {
		return opaque;
	}
  }