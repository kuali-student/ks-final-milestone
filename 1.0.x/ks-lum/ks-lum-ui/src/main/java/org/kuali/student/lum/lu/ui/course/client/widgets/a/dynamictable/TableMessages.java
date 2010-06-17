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
package org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable;

/**
 * Defines text strings to be used with the dynamic table widget
 * @author wilj
 *
 */
public interface TableMessages {
	/**
	 * Returns the alt-text for the table's "loading spinner"
	 * @return the alt-text for the table's "loading spinner"
	 */
	String getLoadingData();

	/**
	 * Returns the text to display when the user has not selected a column from the column picker
	 * @return the text to display when the user has not selected a column from the column picker
	 */
	String getMustSelectAtLeastOneColumn();

	/**
	 * Returns the text to be displayed when the model indicates that there are no rows
	 * @return the text to be displayed when the model indicates that there are no rows
	 */
	String getNoRecordsFound();

	/**
	 * Returns the alt-text for the table options icon
	 * @return the alt-text for the table options icon
	 */
	String getOptions();

	/**
	 * Returns the "Select All" text displayed by the ColumnPicker
	 * @return the "Select All" text displayed by the ColumnPicker
	 */
	String getSelectAll();

	/**
	 * Returns the "select columns to display" text on the settings dialog
	 * @return the "select columns to display" text on the settings dialog
	 */
	String getSelectColumns();
}
