package org.kuali.student.lum.lu.assembly.data.client;

import org.kuali.student.common.assembly.client.Data;

public class ModificationData extends Data {
	public enum Properties implements PropertyEnum {
		CREATED("isCreated"), DELETED("isDeleted"), UPDATED("isUpdated"), VERSIONS("versions");

		private final String key;

		private Properties(final String key) {
			this.key = key;
		}

		@Override
		public String getKey() {
			return this.key;
		}
	}

	private static final long serialVersionUID = 1L;
	
	public ModificationData() {
		super(ModificationData.class.getName());
	}
	
	public void setCreated(boolean created) {
		super.set(Properties.CREATED.getKey(), created);
	}
	public boolean isCreated() {
		Boolean result = super.get(Properties.CREATED.getKey());
		return (result != null && (boolean) result);
	}
	
	public void setUpdated(boolean updated) {
		super.set(Properties.UPDATED.getKey(), updated);
	}
	public boolean isUpdated() {
		Boolean result = super.get(Properties.UPDATED.getKey());
		return (result != null && (boolean) result);
	}
	
	public void setDeleted(boolean deleted) {
		super.set(Properties.DELETED.getKey(), deleted);
	}
	public boolean isDeleted() {
		Boolean result = super.get(Properties.DELETED.getKey());
		return (result != null && (boolean) result);
	}
	
	public Data getVersions() {
		Data result = super.get(Properties.VERSIONS.getKey());
		if (result == null) {
			result = new Data();
			super.set(Properties.VERSIONS.getKey(), result);
		}
		return result;
	}
	public void setVersions(Data versions) {
		super.set(Properties.VERSIONS.getKey(), versions);
	}
	
	public boolean isModified() {
		return isCreated() || isUpdated() || isDeleted();
	}
	
	public String getVersionIndicator() {
		String result = null;
		Data versions = getVersions();
		if (versions.size() > 0) {
			VersionData v = versions.get(0);
			if (v != null) {
				result = v.getVersionIndicator();
			}
		}
		return result;
	}
	public String getVersionIndicator(String typeName) {
		return getVersionIndicator(typeName, null);
	}
	public String getVersionIndicator(String typeName, String id) {
		String result = null;
		Data versions = getVersions();
		for (Property p : versions) {
			VersionData v = p.getValue();
			if (typeName.equals(v.getTypeName()) && (id == null || id.equals(v.getId()))) {
				result = v.getVersionIndicator();
			}
		}
		return result;
		
	}
}
