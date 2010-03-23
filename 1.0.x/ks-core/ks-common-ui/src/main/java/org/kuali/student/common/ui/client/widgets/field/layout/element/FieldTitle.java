package org.kuali.student.common.ui.client.widgets.field.layout.element;

import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

public abstract class FieldTitle extends ComplexPanel implements HasText{
	 
	/**
	   * Adds a new child widget to the panel.
	   * 
	   * @param w the widget to be added
	   */
	  @Override
	  public void add(Widget w) {
	    add(w, getElement());
	  }

	  /**
	   * Inserts a widget before the specified index.
	   * 
	   * @param w the widget to be inserted
	   * @param beforeIndex the index before which it will be inserted
	   * @throws IndexOutOfBoundsException if <code>beforeIndex</code> is out of
	   *           range
	   */
	  public void insert(Widget w, int beforeIndex) {
	    insert(w, getElement(), beforeIndex, true);
	  }
	  
	  public void setText(String title){
		  this.getElement().setInnerText(title);
	  }
	  
	  public String getText(){
		  return this.getElement().getInnerText();
	  }
	
}
