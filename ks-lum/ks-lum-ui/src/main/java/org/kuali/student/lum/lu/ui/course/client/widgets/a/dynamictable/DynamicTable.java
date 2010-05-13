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

package org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable;

import org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.events.RowFocusEvent;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.events.RowFocusHandler;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.impl.DynamicTableImplDefault;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.util.StyleEnum;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.HasBlurHandlers;
import com.google.gwt.event.dom.client.HasFocusHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Focusable;

/**
 * Dynamic scrolling table widget.  Supports large data sets, with no upper bound on client side scaling.
 * 
 * @author wilj
 *
 * @param <T> the generic type with which the table structure is associated
 */
public class DynamicTable<T> extends Composite implements Focusable, HasFocusHandlers, HasBlurHandlers {

	/**
	 * Enumeration of style names associated with the dynamic table widget
	 *
	 */
	public enum Styles implements StyleEnum {
		TABLE("Table"), TABLE_CONTAINER("Table-Container"), ROW("TableRow"), ROW_ALT("TableRow-Alt"), ROW_SELECTED(
				"TableRow-Selected"), ROW_FOCUSED(
				"TableRow-Focused"), CELL("Cell"), SCROLLBAR_CELL("ScrollBar-Cell"), HEADER_FOOTER("HeaderFooter"), NO_RECORDS_CELL(
				"NoRecordsCell"), INDICATOR_IMAGE_PANEL("IndicatorImage-Panel");

		private final String style;

		private Styles(final String style) {
			this.style = style;
		}

		public String getStyle() {
			return this.style;
		}
	}

	private final DynamicTableImplDefault<T> impl = GWT.create(DynamicTableImplDefault.class);
	private final TableDefinition<T> definition;
	private final TableModel<T> model;

	/**
	 * Creates a new DynamicTable widget
	 * @param visibleRows the maximum number of data rows to display at once
	 * @param definition the table definition
	 * @param model the table model
	 */
	public DynamicTable(final int visibleRows, final TableDefinition<T> definition, final TableModel<T> model) {
		super.initWidget(impl);
		this.definition = definition;
		this.model = model;
		impl.initialize(visibleRows, this);
	}

	/**
	 * @see com.google.gwt.event.dom.client.HasBlurHandlers#addBlurHandler(com.google.gwt.event.dom.client.BlurHandler)
	 */
	@Override
	public HandlerRegistration addBlurHandler(final BlurHandler handler) {
		return impl.addBlurHandler(handler);
	}

	/**
	 * @see com.google.gwt.event.dom.client.HasFocusHandlers#addFocusHandler(com.google.gwt.event.dom.client.FocusHandler)
	 */
	@Override
	public HandlerRegistration addFocusHandler(final FocusHandler handler) {
		return impl.addFocusHandler(handler);
	}

	/**
	 * Adds a RowFocusHandler to the table
	 * Rows within the table support a pseudo-focus, allowing for keyboard navigation and other accessibility features.
	 * @param handler the RowFocusHandle
	 * @return HandlerRegistration used to remove the handler
	 */
	public HandlerRegistration addRowFocusHandler(final RowFocusHandler<T> handler) {
		final Type<RowFocusHandler<T>> type = RowFocusEvent.getType();
		return this.addHandler(handler, type);
	}

	/**
	 * Returns the TableDefinition used to construct this table
	 * @return the TableDefinition used to construct this table
	 */
	public TableDefinition<T> getDefinition() {
		return definition;
	}

	/**
	 * Returns the TableModel to which this table is bound
	 * @return the TableModel to which this table is bound
	 */
	public TableModel<T> getModel() {
		return model;
	}

	/**
	 * @see com.google.gwt.user.client.ui.Focusable#getTabIndex()
	 */
	@Override
	public int getTabIndex() {
		return impl.getTabIndex();
	}

	/**
	 * Requests that the table redraw itself 
	 */
	public void redraw() {
		impl.redrawAll();
	}

	/**
	 * Currently not implemented
	 * @see com.google.gwt.user.client.ui.Focusable#setAccessKey(char)
	 */
	@Override
	public void setAccessKey(final char key) {
		impl.setAccessKey(key);
	}

	/**
	 * @see com.google.gwt.user.client.ui.Focusable#setFocus(boolean)
	 */
	@Override
	public void setFocus(final boolean focused) {
		impl.setFocus(focused);
	}

	/**
	 * @see com.google.gwt.user.client.ui.Focusable#setTabIndex(int)
	 */
	@Override
	public void setTabIndex(final int index) {
		impl.setTabIndex(index);
	}

}
