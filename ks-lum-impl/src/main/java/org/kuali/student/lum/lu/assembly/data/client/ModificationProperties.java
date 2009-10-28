package org.kuali.student.lum.lu.assembly.data.client;

import org.kuali.student.common.assembly.PropertyEnum;

public enum ModificationProperties implements PropertyEnum {
		CREATED("crud_created"), DELETED("crud_deleted"), UPDATED("crud_updated");

		private final String key;

		private ModificationProperties(final String key) {
			this.key = key;
		}

		@Override
		public String getKey() {
			return this.key;
		}
}
