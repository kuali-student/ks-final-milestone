/*
 * Copyright 2009 Johnson Consulting Services
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 * 
 */
package org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable;

import com.google.gwt.user.client.ui.Widget;

/**
 * Base class for columns that will recycle their widgets, simply updating state on change.
 * @author wilj
 *
 */
public abstract class StaticColumnDefinition<T> extends ColumnDefinition<T> {

	public static final ColumnAttribute DECORATOR_COLUMN = new ColumnAttribute() {
	};

	/**
	 * Creates a new ColumnDefinition
	 * @param name key used for identifying the column
	 * @param attributes varargs list of ColumnAttributes
	 */
	protected StaticColumnDefinition(final String name,
			final ColumnAttribute... attributes) {
		super(name, false, true, null, attributes);
		super.setRenderer(getRendererImpl());
	}

	/**
	 * Creates an instance of the widget to be used when rendering record cell contents
	 * @param definition the TableDefinition for the table
	 * @param model the TableModel for the table
	 * @return an instance of the widget to be used when rendering record cell contents
	 */
	public abstract Widget getCellWidget(DynamicTable<T> table);

	/**
	 * Creates an instance of the widget to be used for the column header/footer
	 * @param definition the TableDefinition for the table
	 * @param model the TableModel for the table
	 * @return an instance of the widget to be used for the column header/footer
	 */
	public abstract Widget getHeaderFooterWidget(DynamicTable<T> table);

	private ColumnRenderer<T> getRendererImpl() {
		return new ColumnRenderer<T>() {

			@Override
			public String getDisplayName() {
				// should never be called
				return null;
			}

			@Override
			public void onRedraw(final DynamicTable<T> table, final Widget headerFooterWidget) {
				updateHeaderFooterWidget(table, headerFooterWidget);
			}

			@Override
			public void renderCell(final DynamicTable<T> table, final TableCell cell, final T value) {
				Widget w = cell.getWidget();
				if (w == null) {
					w = getCellWidget(table);
					cell.setWidget(w);
				}
				updateCellWidget(table, w, value);
			}

			@Override
			public void renderHeader(final DynamicTable<T> table, final TableCell cell) {
				final Widget w = getHeaderFooterWidget(table);
				if (w != null) {
					cell.setWidget(w);
				}
			}
		};
	}

	/**
	 * Called by the DynamicTable when the cell contents need to be updated
	 * @param definition the TableDefinition for the table
	 * @param model the TableModel for the table
	 * @param widget the cell widget to be updated
	 * @param rowValue the value of the row
	 */
	public abstract void updateCellWidget(DynamicTable<T> table, Widget widget, T value);

	/**
	 * Called by the DynamicTable when the header/footer contents need to be updated
	 * @param definition the TableDefinition for the table
	 * @param model the TableModel for the table
	 * @param widget the header/footer widget to be updated
	 */
	public abstract void updateHeaderFooterWidget(DynamicTable<T> table, Widget widget);

}
