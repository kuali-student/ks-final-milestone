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

package org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.export;

import java.util.Map;

/**
 * Defines a request to export records from a table model
 * @author wilj
 *
 * @param <T> the generic type with which the table structure is associated
 */
public class MappedExportRequest<T> extends ExportRequest<T> {

	private static final long serialVersionUID = 1L;
	private String rowMapperClassName;
	private Map<String, String> columns;

	/**
	 * Returns the map of column names and display labels
	 * @return the columns
	 */
	public Map<String, String> getColumns() {
		return columns;
	}

	/**
	 * Returns the class name of the server-side row exporter to use
	 * @return the class name of the server-side row exporter to use
	 */
	public String getRowMapperClassName() {
		return rowMapperClassName;
	}

	/**
	 * Sets the map of column names and display labels
	 * @param columns the columns to set
	 */
	public void setColumns(final Map<String, String> columns) {
		this.columns = columns;
	}

	/**
	 * Sets the class name of the server-side row exporter to use
	 * @param rowMapperClassName the class name of the server-side row exporter to use
	 */
	public void setRowMapperClassName(final String rowMapperClassName) {
		this.rowMapperClassName = rowMapperClassName;
	}

}
