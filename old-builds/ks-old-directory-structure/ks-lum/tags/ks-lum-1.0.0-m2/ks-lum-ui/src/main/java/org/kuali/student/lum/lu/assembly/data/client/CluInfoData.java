package org.kuali.student.lum.lu.assembly.data.client;

import org.kuali.student.common.assembly.client.Data;

public class CluInfoData extends Data {
	public enum Properties implements PropertyEnum {
		CHILD_CLUS("childClus"), PARENT_RELATION_TYPE("parentRelationType");
		
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
	
	public String getParentRelationType() {
		return super.get(Properties.PARENT_RELATION_TYPE.getKey());
	}

	public Data getChildClus() {
		Data result = super.get(Properties.CHILD_CLUS.getKey());
		if (result == null) {
			result = new Data();
			super.set(Properties.CHILD_CLUS.getKey(), result);
		}
		return result;
	}
}
