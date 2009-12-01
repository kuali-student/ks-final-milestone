package org.kuali.student.common.ui.client.widgets.menus;

import java.util.LinkedList;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author wilj
 *
 */
public class KSListPanel extends ComplexPanel {
	public enum ListType {
		ORDERED, UNORDERED
	}

	private final ListType listType;
	private final Element listElement;
	private final LinkedList<Widget> widgets = new LinkedList<Widget>();

	public KSListPanel() {
		this(ListType.UNORDERED);
	}

	public KSListPanel(final ListType listType) {
		this.listType = listType;
		setElement(DOM.createDiv());
		if (listType == ListType.ORDERED) {
			this.listElement = DOM.createElement("ol");
		} else {
			this.listElement = DOM.createElement("ul");
		}
		getElement().appendChild(listElement);
	}

	@Override
	public void add(final Widget widget) {
		super.add(widget, listElement.appendChild(DOM.createElement("li")));
	}

	/**
	 * @return the listType
	 */
	public ListType getListType() {
		return listType;
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.ComplexPanel#remove(int)
	 */
	@Override
	public boolean remove(final int index) {
		final Widget w = super.getWidget(index);
		final Element e = w.getElement();
		final boolean result = super.remove(index);
		return result;
	}

	@Override
	public void clear() {
		super.clear();
		for(int i = 0; i < listElement.getChildNodes().getLength(); i++){
			listElement.removeChild(listElement.getChildNodes().getItem(i));
		}
		
	}
	
	
}