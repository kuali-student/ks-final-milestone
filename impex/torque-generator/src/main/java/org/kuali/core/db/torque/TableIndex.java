package org.kuali.core.db.torque;

import java.util.ArrayList;
import java.util.List;

public class TableIndex {
	List<String> columns = new ArrayList<String>();
	String name;
	boolean unique;

	public List<String> getColumns() {
		return columns;
	}

	public void setColumns(List<String> columns) {
		this.columns = columns;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isUnique() {
		return unique;
	}

	public void setUnique(boolean unique) {
		this.unique = unique;
	}
}
