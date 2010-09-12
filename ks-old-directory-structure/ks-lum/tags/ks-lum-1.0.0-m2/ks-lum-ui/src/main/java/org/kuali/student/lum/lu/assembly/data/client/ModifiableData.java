package org.kuali.student.lum.lu.assembly.data.client;

import org.kuali.student.common.assembly.client.Data;

public class ModifiableData extends Data {
	public enum Properties implements PropertyEnum {
		MODIFICATIONS("modifications");

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
	
	public ModifiableData() {
		super();
	}
	public ModifiableData(String className) {
		super(className);
	}
	
	public ModificationData getModifications() {
		ModificationData result = super.get(Properties.MODIFICATIONS.getKey());
		if (result == null) {
			result = new ModificationData();
			super.set(Properties.MODIFICATIONS.getKey(), result);
		}
		return result;
	}
	public void setModifications(ModificationData modifications) {
		super.set(Properties.MODIFICATIONS.getKey(), modifications);
	}
}
