package org.kuali.core.db.torque;

import java.util.List;

public class KualiForeignKey {
	String refTableName;
	String onDelete;
	List<KualiReference> references;

	public String getRefTableName() {
		return refTableName;
	}

	public void setRefTableName(String refTableName) {
		this.refTableName = refTableName;
	}

	public String getOnDelete() {
		return onDelete;
	}

	public void setOnDelete(String onDelete) {
		this.onDelete = onDelete;
	}

	public List<KualiReference> getReferences() {
		return references;
	}

	public void setReferences(List<KualiReference> references) {
		this.references = references;
	}

}
