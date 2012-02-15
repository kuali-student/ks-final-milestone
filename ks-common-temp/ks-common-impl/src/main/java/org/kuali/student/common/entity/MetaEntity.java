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

package org.kuali.student.common.entity;

import java.util.Date;

import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;

import org.kuali.student.common.util.security.SecurityUtils;

@MappedSuperclass
public abstract class MetaEntity extends BaseEntity{
	
	@Embedded
	private Meta meta;
	
	@Override
	protected void onPrePersist(){
		super.onPrePersist();
		if(meta==null){
			meta = new Meta();
		}
		meta.setCreateTime(new Date());
		meta.setUpdateTime(new Date());

		String user = SecurityUtils.getCurrentUserId();
		meta.setCreateId(user);
		meta.setUpdateId(user);
		
	}
	
	@Override
	protected void onPreUpdate(){
		super.onPreUpdate();
		//This code should not be here, but hibernate is calling update callback instead of prepersit if the id is not null.
		if(meta==null){
			meta = new Meta();
			meta.setCreateTime(new Date());
		}

		meta.setUpdateTime(new Date());

		String user = SecurityUtils.getCurrentUserId();
		meta.setUpdateId(user);
	}

	public Meta getMeta() {
		return meta;
	}

	public void setMeta(Meta meta) {
		this.meta = meta;
	}
}
