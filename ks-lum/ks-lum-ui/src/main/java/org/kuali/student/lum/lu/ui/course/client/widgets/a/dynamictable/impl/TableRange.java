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

package org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.impl;

/**
 * Represents a range of cells within a table.
 * This is a convenience class used only within the table implementation
 * @author wilj
 *
 */
public final class TableRange {
	private int startRow;
	private int startColumn;
	private int rowCount;
	private int columnCount;
	private int iteratorEndRow;
	private int iteratorEndColumn;
	private int endRow;
	private int endColumn;

	public TableRange() {
		super();
		reset();
	}

	public boolean contains(final int cellRow, final int cellColumn) {
		return cellRow >= startRow && cellRow <= endRow && cellColumn >= startColumn && cellColumn <= endColumn;
	}

	public int getColumnCount() {
		return columnCount;
	}

	public int getEndColumn() {
		return endColumn;
	}

	public int getEndRow() {
		return endRow;
	}

	public int getIteratorEndColumn() {
		return iteratorEndColumn;
	}

	public int getIteratorEndRow() {
		return iteratorEndRow;
	}

	public int getRowCount() {
		return rowCount;
	}

	public int getStartColumn() {
		return startColumn;
	}

	public int getStartRow() {
		return startRow;
	}

	public void reset() {
		startRow = 0;
		startColumn = 0;
		rowCount = 0;
		columnCount = 0;
		iteratorEndRow = 0;
		iteratorEndColumn = 0;
	}

	public void reset(final int startRow, final int startColumn, final int rowCount, final int columnCount) {
		this.startRow = startRow;
		this.startColumn = startColumn;
		this.rowCount = rowCount;
		this.columnCount = columnCount;
		this.iteratorEndRow = startRow + rowCount;
		this.iteratorEndColumn = startColumn + columnCount;
		this.endRow = iteratorEndRow - 1;
		this.endColumn = iteratorEndColumn - 1;
	}
}
