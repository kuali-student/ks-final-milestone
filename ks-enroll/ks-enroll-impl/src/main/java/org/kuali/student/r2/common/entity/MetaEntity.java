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

package org.kuali.student.r2.common.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.kuali.student.common.entity.BaseEntity;
import org.kuali.student.common.util.security.SecurityUtils;
import org.kuali.student.r2.common.dto.MetaInfo;
import org.kuali.student.r2.common.infc.HasMeta;
import org.kuali.student.r2.common.infc.Meta;

@MappedSuperclass
@Embeddable
public abstract class MetaEntity extends BaseEntity{
	
	// Hibernate will not allow @Version in @Embeddable for some annoying reason
//	@Version
//	private long versionInd;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(updatable=false)
	private Date createTime;

	@Column(updatable=false)
	private String createId;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updateTime;

	private String updateId;

//	public long getVersionInd() {
//	    return versionInd;
//	}

//	public void setVersionInd(long versionInd) {
//	    this.versionInd = versionInd;
//	}
		

	protected MetaEntity() {
	    
	}
	
	// TODO - need a BaseEntity(HasMeta) to deal w/ version, id, and other fields
    public MetaEntity(HasMeta hasMeta) {
        if (null != hasMeta) {
            Meta meta = hasMeta.getMeta();
            if (null != meta) {
	            this.setCreateTime(meta.getCreateTime());
	            this.setCreateId(meta.getCreateId());
	            this.setUpdateTime(meta.getUpdateTime());
	            this.setUpdateId(meta.getUpdateId());
	            this.setVersionNumber(null != meta.getVersionInd() ? Long.valueOf(meta.getVersionInd()) : null);
	        }
        }
    }

    public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateId() {
		return createId;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
	}
	
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateId() {
		return updateId;
	}

	public void setUpdateId(String updateId) {
		this.updateId = updateId;
	}

	@Override
	protected void onPrePersist(){
		super.onPrePersist();
		setCreateTime(new Date());
		setUpdateTime(new Date());

		String user = SecurityUtils.getCurrentUserId();
		setCreateId(user);
		setUpdateId(user);
		
	}
	
	@Override
	protected void onPreUpdate(){
		super.onPreUpdate();
		//This code should not be here, but hibernate is calling update callback instead of prepersit if the id is not null.
		if(getCreateTime() == null){
			setCreateTime(new Date());
		}

		String user = SecurityUtils.getCurrentUserId();
		setUpdateId(user);
	}

	public MetaInfo toDTO() {
		MetaInfo miInfo = MetaInfo.newInstance();
		miInfo.setCreateId(getCreateId());
		miInfo.setCreateTime(getCreateTime());
		miInfo.setUpdateId(getUpdateId());
		miInfo.setUpdateTime(getUpdateTime());
		miInfo.setVersionInd(getVersionNumber().toString());
		return miInfo;
	}
}
