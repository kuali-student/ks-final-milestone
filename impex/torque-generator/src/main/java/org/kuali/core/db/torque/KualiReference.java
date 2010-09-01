package org.kuali.core.db.torque;

public class KualiReference {
	String localColumn;
	String foreignColumn;

	public String getLocalColumn() {
		return localColumn;
	}

	public void setLocalColumn(String localColumn) {
		this.localColumn = localColumn;
	}

	public String getForeignColumn() {
		return foreignColumn;
	}

	public void setForeignColumn(String foreignColumn) {
		this.foreignColumn = foreignColumn;
	}
}
