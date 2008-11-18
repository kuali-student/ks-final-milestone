package org.kuali.student.core.entity;

import java.util.Date;

import javax.persistence.Embeddable;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@Embeddable
public class Meta {
    @Version
	private long versionInd;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;

	private String createId;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updateTime;

	private String updateId;

	public long getVersionInd() {
		return versionInd;
	}

	public void setVersionInd(long versionInd) {
		this.versionInd = versionInd;
	}

	@PrePersist
	public Date getCreateTime() {
		createTime = new Date();
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

	@PreUpdate
	public Date getUpdateTime() {
		updateTime = new Date();
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
