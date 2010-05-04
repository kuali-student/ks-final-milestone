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

package org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.actions;

import org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.DynamicTable;

/**
 * Defines a handler for table actions, such as displaying settings, or exporting records
 * @author wilj
 *
 * @param <T> the generic type with which the table structure is associated
 */
public interface ActionHandler<T> {
	/**
	 * Checks to see if the action should be enabled for the table.
	 * Called each time the actions menu is displayed.
	 * @param table the table with which the action will be associated
	 * @return true if the action should be enabled for the table
	 */
	public boolean isEnabledFor(DynamicTable<T> table);

	/**
	 * Invoked when the action should be performed on the table
	 * @param table the table on which to perform the action
	 */
	public void onAction(DynamicTable<T> table);
}
