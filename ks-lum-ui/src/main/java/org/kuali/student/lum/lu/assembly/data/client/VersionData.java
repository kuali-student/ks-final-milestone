package org.kuali.student.lum.lu.assembly.data.client;

import org.kuali.student.common.assembly.client.Data;

public class VersionData extends Data {
	public enum Properties implements PropertyEnum {
		TYPENAME("typeName"), ID("id"), VERSION_INDICATOR("versionIndicator");

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
	
	protected VersionData() {
		// for RPC serialization
		super(VersionData.class.getName());
	}
	public VersionData(String typeName, String id, String versionIndicator) {
		super(VersionData.class.getName());
		super.set(Properties.TYPENAME.getKey(), typeName);
		super.set(Properties.ID.getKey(), id);
		super.set(Properties.VERSION_INDICATOR.getKey(), versionIndicator);
	}
	public String getTypeName() {
		return super.get(Properties.TYPENAME.getKey());
	}
	public String getId() {
		return super.get(Properties.ID.getKey());
	}
	public String getVersionIndicator() {
		return super.get(Properties.VERSION_INDICATOR.getKey());
	}
}
