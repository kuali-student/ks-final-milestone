/**
 * Copyright 2010 The Kuali Foundation Licensed under the Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing permissions and limitations under the License.
 */

package org.kuali.student.r1.common.entity;

import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;

import org.kuali.student.common.util.UUIDHelper;

@Deprecated
@MappedSuperclass
public abstract class VersionEntity extends MetaEntity {

	@Embedded
	private Version version;
	
	@Override
	protected void onPrePersist(){
		super.onPrePersist();
		if(version == null){
			version = new Version();
		}
		if(version.getSequenceNumber()==null){
			version.setSequenceNumber(Long.valueOf(1));
		}
		version.setVersionIndId(UUIDHelper.genStringUUID(version.getVersionIndId()));
	}
	
	
	public Version getVersion() {
		return version;
	}

	public void setVersion(Version version) {
		this.version = version;
	}
}
