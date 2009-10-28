package org.kuali.student.lum.lu.assembly.data.client;

import org.kuali.student.common.assembly.Data;

public class ModifiableData extends Data {

	private static final long serialVersionUID = 1L;
	
	public ModifiableData() {
		super();
	}
	public ModifiableData(String className) {
		super(className);
	}
	public void setCreated(boolean created) {
		super.set(ModificationProperties.CREATED.getKey(), created);
	}
	public boolean isCreated() {
		return super.get(ModificationProperties.CREATED.getKey());
	}
	
	public void setUpdated(boolean updated) {
		super.set(ModificationProperties.UPDATED.getKey(), updated);
	}
	public boolean isUpdated() {
		return super.get(ModificationProperties.UPDATED.getKey());
	}
	
	public void setDeleted(boolean deleted) {
		super.set(ModificationProperties.DELETED.getKey(), deleted);
	}
	public boolean isDeleted() {
		return super.get(ModificationProperties.DELETED.getKey());
	}
	
}
