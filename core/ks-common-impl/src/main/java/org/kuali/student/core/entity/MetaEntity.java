/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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
package org.kuali.student.core.entity;

import java.util.Date;

import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Version;

@MappedSuperclass
public abstract class MetaEntity {
	
	@Version
	private long versionInd;
	
	@Embedded
	private Meta meta;
	
	@PrePersist
	public void prePersist(){
		if(meta==null){
			meta = new Meta();
		}
		meta.setCreateTime(new Date());
		meta.setUpdateTime(new Date());
		//TODO Also set the create and update user id from context
		
		onPrePersist();
	}
	
	@PreUpdate
	public void preUpdate(){
		//This code should not be here, but hibernate is calling update callback instead of prepersit if the id is not null.
		if(meta==null){
			meta = new Meta();
			meta.setCreateTime(new Date());
		}

		meta.setUpdateTime(new Date());
		//TODO Also set the update user id from context
		
		onPreUpdate();
	}
	

	//Override this to add additional functionality for the PrePersist Lifecycle
	protected void onPrePersist() {
	}
	
	//Override this to add additional functionality for the PreUpdate Lifecycle
	protected void onPreUpdate() {
	}
	
	public long getVersionInd() {
		return versionInd;
	}

	public void setVersionInd(long versionInd) {
		this.versionInd = versionInd;
	}

	public Meta getMeta() {
		return meta;
	}

	public void setMeta(Meta meta) {
		this.meta = meta;
	}
}
