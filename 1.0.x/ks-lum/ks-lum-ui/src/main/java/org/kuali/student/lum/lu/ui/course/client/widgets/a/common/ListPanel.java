/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

/**
 * 
 */
package org.kuali.student.lum.lu.ui.course.client.widgets.a.common;

import java.util.LinkedList;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author wilj
 *
 */
public class ListPanel extends ComplexPanel {
	public enum ListType {
		ORDERED, UNORDERED
	}

	private final ListType listType;
	private final Element listElement;
	private final LinkedList<Widget> widgets = new LinkedList<Widget>();

	public ListPanel() {
		this(ListType.UNORDERED);
	}

	public ListPanel(final ListType listType) {
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
		listElement.removeChild(e);
		return result;
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.ComplexPanel#remove(com.google.gwt.user.client.ui.Widget)
	 */
	@Override
	public boolean remove(final Widget w) {
		return this.remove(getWidgetIndex(w));
	}

}
