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

import java.util.ArrayList;
import java.util.List;

/**
 * Defines configuration for a DynamicTable
 * @author wilj
 *
 * @param <T> the generic type with which the table structure is associated
 */
public class TableDefinition<T> {

	/**
	 * Enumeration of row selection modes (single, multi-select, or disabled)
	 * Only multi-select is currently implemented
	 */
	public enum SelectionMode {
		DISABLED, SINGLE_ITEM, MULTI_ITEM
	}

	private final List<ColumnDefinition<T>> columns = new ArrayList<ColumnDefinition<T>>();
	private final List<TableDecorator<T>> decorators = new ArrayList<TableDecorator<T>>();
	private final List<ActionDefinition<T>> actions = new ArrayList<ActionDefinition<T>>();

	private final SelectionMode selectionMode;
	private final String tableName;
	private boolean showHeader = true;
	private boolean showFooter = true;

	/**
	 * Creates a new TableDefinition
	 * @param selectionMode the SelectionMode of the table
	 * @param tableName String value used for creating CSS style names at runtime
	 */
	public TableDefinition(final SelectionMode selectionMode, final String tableName) {
		this.selectionMode = selectionMode;
		this.tableName = tableName;
	}

	/**
	 * Adds an ActionDefinition to the table
	 * @param action the action to add
	 */
	public void addAction(final ActionDefinition<T> action) {
		actions.add(action);
	}

	/**
	 * Adds a ColumnDefinition to the table
	 * @param column the column to add
	 */
	public void addColumn(final ColumnDefinition<T> column) {
		columns.add(column);
	}

	/**
	 * Adds a TableDecorator to the table
	 * @param decorator the decorator to add
	 */
	public void addDecorator(final TableDecorator<T> decorator) {
		decorators.add(decorator);
	}

	/**
	 * Returns a List of the actions defined for the table
	 * @return List of the actions defined for the table
	 */
	public List<ActionDefinition<T>> getActions() {
		return actions;
	}

	/**
	 * Returns a List of the columns defined for the table
	 * @return List of the columns defined for the table
	 */
	public List<ColumnDefinition<T>> getColumns() {
		return columns;
	}

	/**
	 * Returns a List of decorators defined for the table
	 * @return List of decorators defined for the table
	 */
	public List<TableDecorator<T>> getDecorators() {
		return decorators;
	}

	/**
	 * Returns the table's selection mode
	 * @return the table's selection mode
	 */
	public SelectionMode getSelectionMode() {
		return selectionMode;
	}

	/**
	 * Returns the table's name
	 * @return the table's name
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * Returns true if the table displays a footer 
	 * @return true if the table displays a footer
	 */
	public boolean isShowFooter() {
		return showFooter;
	}

	/**
	 * Returns true if the table displays a header
	 * @return true if the table displays a header
	 */
	public boolean isShowHeader() {
		return showHeader;
	}

	/**
	 * Sets whether or not the table should display a footer row
	 * @param showFooter true if the table should display a footer row
	 */
	public void setShowFooter(final boolean showFooter) {
		this.showFooter = showFooter;
	}

	/**
	 * Sets whether or not the table should display a header row
	 * @param showHeader true if the table should display a header row
	 */
	public void setShowHeader(final boolean showHeader) {
		this.showHeader = showHeader;
	}

}
