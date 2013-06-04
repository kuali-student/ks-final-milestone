/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.core.organization.assembly.data.client;

import org.kuali.student.r1.common.assembly.data.Data;
import org.kuali.student.r1.common.assembly.helper.PropertyEnum;

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
