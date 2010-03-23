package org.kuali.student.common.ui.client.widgets.field.layout.element;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Widget;

public class SpanPanel extends ComplexPanel{
	
	public SpanPanel(){
		setElement(DOM.createSpan());
	}
	
	public SpanPanel(String text){
		Element span = DOM.createSpan();
		span.setInnerText(text);
		setElement(span);
	}
	
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
	  
	  public void setText(String text){
		  this.getElement().setInnerText(text);
	  }
}
