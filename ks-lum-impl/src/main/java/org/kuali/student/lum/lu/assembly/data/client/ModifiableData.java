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
		Boolean result = super.get(ModificationProperties.CREATED.getKey());
		return (result != null && (boolean) result);
	}
	
	public void setUpdated(boolean updated) {
		super.set(ModificationProperties.UPDATED.getKey(), updated);
	}
	public boolean isUpdated() {
		Boolean result = super.get(ModificationProperties.UPDATED.getKey());
		return (result != null && (boolean) result);
	}
	
	public void setDeleted(boolean deleted) {
		super.set(ModificationProperties.DELETED.getKey(), deleted);
	}
	public boolean isDeleted() {
		Boolean result = super.get(ModificationProperties.DELETED.getKey());
		return (result != null && (boolean) result);
	}
	
	public String getVersionIndicator() {
		return super.get(ModificationProperties.VERSION_INDICATOR.getKey());
	}
	public void setVersionIndicator(String version) {
		super.set(ModificationProperties.VERSION_INDICATOR.getKey(), version);
	}
	
	public boolean isModified() {
		return isCreated() || isUpdated() || isDeleted();
	}
}
