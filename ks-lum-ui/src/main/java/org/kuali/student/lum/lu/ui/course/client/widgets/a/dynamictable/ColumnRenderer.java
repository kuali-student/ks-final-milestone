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
 * Defines a column renderer.
 * @author wilj
 *
 * @param <T> the generic type with which the table structure is associated
 */
public interface ColumnRenderer<T> {

	/**
	 * Returns the display name/label for the column
	 * @return the display name/label for the column
	 */
	public String getDisplayName();

	/**
	 * Called by the table when the table contents are redrawn
	 * Called once per header and/or footer that is shown
	 * @param table the table undergoing redraw
	 */
	public void onRedraw(DynamicTable<T> table, Widget headerFooterWidget);

	/**
	 * Render the cell contents for the specified value
	 * @param cell the cell into which the contents should be rendered
	 * @param value the value for the current row in the table
	 */
	public void renderCell(DynamicTable<T> table, TableCell cell, T value);

	/**
	 * Render the header for the column
	 * @param cell the cell into which the column header should be rendered
	 */
	public void renderHeader(DynamicTable<T> table, TableCell cell);
}
