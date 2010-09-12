/*
 * Copyright 2009 Johnson Consulting Services
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;

/**
 * A table cell as it is presented to a ColumnRenderer
 * @author wilj
 *
 */
public interface TableCell {
	/**
	 * Gets the cell's widget, if any
	 * @return the widget, or null if the cell does not contain a widget
	 */
	public Widget getWidget();

	/**
	 * Sets a DOM element as the contents of the table cell
	 * @param element the new content of the table cell
	 */
	public void setElement(Element element);

	/**
	 * Directly sets the InnerHtml contents of the table cell
	 * Warning, very dangerous, only use on thoroughly sanitized values, or not at all
	 * @param html the HTML to be directly injected into the DOM
	 */
	public void setInnerHtml(String html);

	/**
	 * Sets the contents text of the table cell
	 * @param text the new content of the table cell
	 */
	public void setText(String text);

	/**
	 * Sets a Widget as the contents of the table cell
	 * @param widget the new content of the table cell
	 */
	public void setWidget(Widget widget);
}
