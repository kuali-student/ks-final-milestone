package org.kuali.student.core.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Embeddable
public class Meta {

//	Hibernate will not allow @Version in @Embeddable for some annoying reason
//	@Version
//	private long versionInd;
	
//	public long getVersionInd() {
//	    return versionInd;
//  }
//
//  public void setVersionInd(long versionInd) {
//	    this.versionInd = versionInd;
//  }
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(updatable=false)
	private Date createTime;

	@Column(updatable=false)
	private String createId;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updateTime;

	private String updateId;

	@PrePersist
	public void prePersist(){
		createTime = new Date();
		//TODO Also set the create user id from context
	}
	
	@PreUpdate
	public void preUpdate(){
		updateTime = new Date();
		//TODO Also set the update user id from context
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
}
