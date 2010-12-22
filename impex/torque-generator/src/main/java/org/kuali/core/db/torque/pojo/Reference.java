package org.kuali.core.db.torque.pojo;

public class Reference {
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
