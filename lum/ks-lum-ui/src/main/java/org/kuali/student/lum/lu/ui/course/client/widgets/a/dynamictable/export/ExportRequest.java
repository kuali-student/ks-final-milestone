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
package org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.export;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.SortInfo;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.Selection.SelectionType;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.filters.Filter;

/**
 * Defines a request to export records from a table model
 * @author wilj
 *
 * @param <T> the generic type with which the table structure is associated
 */
public class ExportRequest<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	private String exportFormatClassName;
	private SelectionType selectionType;
	private Set<String> selectedIds;
	private Filter filter;
	private List<SortInfo> sort;

	/**
	 * Returns the class name of the server-side exporter to use
	 * @return the class name of the server-side exporter to use
	 */
	public String getExportFormatClassName() {
		return exportFormatClassName;
	}

	/**
	 * Returns the Filter implementation to use when exporting the records.
	 * The Filter interface is simply a marker interface, and can be used to wrap criteria structures such as a map of strings, etc.
	 * @return the Filter implementation to use when exporting the records.
	 */
	public Filter getFilter() {
		return filter;
	}

	/**
	 * Returns the record identifiers from the table model's Selection object
	 * Note that the selection type could be exclusive, in which case these are the identifiers that are <b>not</b> selected
	 * @return the record identifiers from the table model's Selection object
	 */
	public Set<String> getSelectedIds() {
		return selectedIds;
	}

	/**
	 * Returns the selection type (inclusive or exclusive)
	 * @return the selection type
	 */
	public SelectionType getSelectionType() {
		return selectionType;
	}

	/**
	 * Returns the list of SortInfo objects to be used when sorting the export
	 * @return the list of SortInfo objects to be used when sorting the export
	 */
	public List<SortInfo> getSort() {
		return sort;
	}

	/**
	 * Returns the class name of the server-side row exporter to use
	 * @param exportFormatClassName the class name of the server-side row exporter to use
	 */
	public void setExportFormatClassName(final String exportFormatClassName) {
		this.exportFormatClassName = exportFormatClassName;
	}

	/**
	 * Sets the Filter implementation to use when exporting the records.
	 * The Filter interface is simply a marker interface, and can be used to wrap criteria structures such as a map of strings, etc.
	 * @param filter the Filter implementation to use when exporting the records.
	 */
	public void setFilter(final Filter filter) {
		this.filter = filter;
	}

	/**
	 * Sets the record identifiers from the table model's Selection object
	 * Note that the selection type could be exclusive, in which case these are the identifiers that are <b>not</b> selected
	 * @param selectedIds the record identifiers from the table model's Selection object
	 */
	public void setSelectedIds(final Set<String> selectedIds) {
		this.selectedIds = selectedIds;
	}

	/**
	 * Sets the selection type (inclusive or exclusive)
	 * @param selectionType the selection type
	 */
	public void setSelectionType(final SelectionType selectionType) {
		this.selectionType = selectionType;
	}

	/**
	 * Sets the list of SortInfo objects to be used when sorting the export
	 * @param sort the list of SortInfo objects to be used when sorting the export
	 */
	public void setSort(final List<SortInfo> sort) {
		this.sort = sort;
	}

}
