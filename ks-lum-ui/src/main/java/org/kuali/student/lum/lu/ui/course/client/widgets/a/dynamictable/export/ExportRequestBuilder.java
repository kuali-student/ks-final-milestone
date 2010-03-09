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
package org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.export;

import java.util.List;
import java.util.Set;

import org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.ColumnDefinition;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.DynamicTable;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.SortInfo;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.Selection.SelectionType;
import org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.filters.Filter;

/**
 * Base class for building ExportRequests on the client, to be sent to the server.
 * Some export requests require additional calculation or gathering of information from the user.
 *  
 * @author wilj
 *
 */
public class ExportRequestBuilder<T> {
	public interface BuilderCallback<T> {
		void onBuildCanceled();

		void onBuildComplete(ExportRequest<T> request);
	}

	private String exportFormatClassName;
	private SelectionType selectionType;
	private Set<String> selectedIds;
	private Filter filter;
	private List<SortInfo> sort;
	private DynamicTable<T> table;
	private List<ColumnDefinition<T>> columns;

	/**
	 * Builds the export request
	 * @param callback
	 */
	public void build(final BuilderCallback<T> callback) {
		final ExportRequest<T> request = getRequestInstance();
		request.setExportFormatClassName(exportFormatClassName);
		request.setFilter(filter);
		request.setSelectedIds(selectedIds);
		request.setSelectionType(selectionType);
		request.setSort(sort);
		callback.onBuildComplete(request);
	}

	/**
	 * @return the columns
	 */
	public List<ColumnDefinition<T>> getColumns() {
		return columns;
	}

	/**
	 * @return the exportFormatClassName
	 */
	public String getExportFormatClassName() {
		return exportFormatClassName;
	}

	/**
	 * @return the filter
	 */
	public Filter getFilter() {
		return filter;
	}

	protected ExportRequest<T> getRequestInstance() {
		return new ExportRequest<T>();
	}

	/**
	 * @return the selectedIds
	 */
	public Set<String> getSelectedIds() {
		return selectedIds;
	}

	/**
	 * @return the selectionType
	 */
	public SelectionType getSelectionType() {
		return selectionType;
	}

	/**
	 * @return the sort
	 */
	public List<SortInfo> getSort() {
		return sort;
	}

	/**
	 * @return the table
	 */
	public DynamicTable<T> getTable() {
		return table;
	}

	/**
	 * @param columns the columns to set
	 */
	public void setColumns(final List<ColumnDefinition<T>> columns) {
		this.columns = columns;
	}

	/**
	 * @param exportFormatClassName the exportFormatClassName to set
	 */
	public ExportRequestBuilder<T> setExportFormatClassName(final String exportFormatClassName) {
		this.exportFormatClassName = exportFormatClassName;
		return this;
	}

	/**
	 * @param filter the filter to set
	 */
	public ExportRequestBuilder<T> setFilter(final Filter filter) {
		this.filter = filter;
		return this;
	}

	/**
	 * @param selectedIds the selectedIds to set
	 */
	public ExportRequestBuilder<T> setSelectedIds(final Set<String> selectedIds) {
		this.selectedIds = selectedIds;
		return this;
	}

	/**
	 * @param selectionType the selectionType to set
	 */
	public ExportRequestBuilder<T> setSelectionType(final SelectionType selectionType) {
		this.selectionType = selectionType;
		return this;
	}

	/**
	 * @param sort the sort to set
	 */
	public ExportRequestBuilder<T> setSort(final List<SortInfo> sort) {
		this.sort = sort;
		return this;
	}

	/**
	 * @param table the table to set
	 */
	public ExportRequestBuilder<T> setTable(final DynamicTable<T> table) {
		this.table = table;
		return this;
	}

}
