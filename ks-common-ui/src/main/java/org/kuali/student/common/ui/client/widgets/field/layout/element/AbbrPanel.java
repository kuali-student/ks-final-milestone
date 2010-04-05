package org.kuali.student.common.ui.client.widgets.field.layout.element;

import org.kuali.student.common.ui.client.widgets.KSButton;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Widget;

public class AbbrPanel extends ComplexPanel {
	
	
	public KSButton button;
	
	public AbbrPanel(String title, String className, String text){
		Element abbr = DOM.createElement("abbr");
		abbr.setTitle(title);
		abbr.setClassName(className);
		abbr.setInnerText(text);
		this.setElement(abbr);
		
	}
	
	public AbbrPanel(String title, String className){
		Element abbr = DOM.createElement("abbr");
		abbr.setTitle(title);
		abbr.setClassName(className);
		this.setElement(abbr);
		
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
	


}
