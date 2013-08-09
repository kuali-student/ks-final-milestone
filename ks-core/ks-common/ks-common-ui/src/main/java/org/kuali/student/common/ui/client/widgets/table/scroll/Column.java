package org.kuali.student.common.ui.client.widgets.table.scroll;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * Column definition for table column.
 * 
 * 
 * */
public class Column {
	/** Ascending order. */
	public final static int Ascending = 1;
	/** Descending order */
	public final static int Descending = 2;
	/** Not sorted */
	public final static int NotOrdered = 3;
    
	private Widget columnTitleWidget;
	private String id;
	private String name = "";
	private String width;
	private int sortDirection = NotOrdered;
	private boolean isVisible = true;
	private boolean isSortable;

	private RowComparator ascendingRowComparator;
	private RowComparator descendingRowComparator;

	/**
	 * Get the row comparator.
	 * */
	public RowComparator getComparator() {
		if (this.sortDirection == Ascending) {
			return ascendingRowComparator;
		} else if (this.sortDirection == Descending) {
			return descendingRowComparator;
		} else {
			return null;
		}
	}
    /** Get comparator for ascending sort*/
	public RowComparator getAscendingRowComparator() {
		return ascendingRowComparator;
	}

	public void setAscendingRowComparator(RowComparator ascendingRowComparator) {
		this.ascendingRowComparator = ascendingRowComparator;
		this.isSortable = true;
	}

	public RowComparator getDescendingRowComparator() {
		return descendingRowComparator;
	}

	public void setDescendingRowComparator(RowComparator descedingRowcomparator) {
		this.descendingRowComparator = descedingRowcomparator;
		this.isSortable = true;
	}

	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

	public Widget getColumnTitleWidget() {
		if (columnTitleWidget == null) {
			return new Label(name);
		}

		return columnTitleWidget;
	}

	public void setColumnTitleWidget(Widget c) {
		columnTitleWidget = c;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isSortable() {
		return isSortable;
	}

	public void setSortable(boolean isSortable) {
		this.isSortable = isSortable;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public void changeSortDirection() {
		if (this.sortDirection == NotOrdered) {
			this.sortDirection = Ascending;
		} else if (this.sortDirection == Ascending) {
			this.sortDirection = Descending;
		} else if (this.sortDirection == Descending) {
			this.sortDirection = Ascending;
		}
	}

	public void setSortDirection(int sortDirection) {
		this.sortDirection = sortDirection;
	}

	public int getSortDirection() {
		return sortDirection;
	}

	public void setSortedType(int direction) {
		sortDirection = direction;
	}
}