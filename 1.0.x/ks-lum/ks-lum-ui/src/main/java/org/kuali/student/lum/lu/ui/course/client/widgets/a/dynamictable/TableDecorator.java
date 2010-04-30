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

import com.google.gwt.user.client.ui.Widget;

/**
 * A TableDecorator is a pseudo-column that precedes the normal table columns.  An example of this is the checkbox used for multi-select.
 * 
 * TableDecorators can be used when additional metadata is required, beyond that which is provided to a ColumnRenderer.
 * TableDecorators also support recycling of the cell content widget, since they are frequently state driven while the content itself varies little.
 * @author wilj
 *
 * @param <T> the generic type with which the table structure is associated
 */
public interface TableDecorator<T> {
	/**
	 * Returns the decorator widget to be used for rows
	 * @param definition the TableDefinition for the table
	 * @param model the TableModel for the table
	 * @return the decorator widget to be used for rows
	 */
	public Widget getRowDecorator(TableDefinition<T> definition, TableModel<T> model);

	/**
	 * Returns the decorator widget to be used as the column header
	 * @param definition the TableDefinition for the table
	 * @param model the TableModel for the table
	 * @return the decorator widget to be used as the column header
	 */
	public Widget getTableDecorator(TableDefinition<T> definition, TableModel<T> model);

	/**
	 * Called by the DynamicTable when the decorator contents need to be updated
	 * @param definition the TableDefinition for the table
	 * @param model the TableModel for the table
	 * @param widget the decorator widget to be updated
	 * @param rowValue the value of the row
	 */
	public void updateRowDecorator(TableDefinition<T> definition, TableModel<T> model, Widget widget, T rowValue);

	/**
	 * Called by the DynamicTable when the decorator contents need to be updated
	 * @param definition the TableDefinition for the table
	 * @param model the TableModel for the table
	 * @param widget the decorator widget to be updated
	 */
	public void updateTableDecorator(TableDefinition<T> definition, TableModel<T> model, Widget widget);
}
