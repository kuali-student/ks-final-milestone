package org.kuali.student.core.entity;

import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@MappedSuperclass
public abstract class MetaEntity {
	
	@Version
	private long versionInd;
	
	@Embedded
	private Meta meta;
	
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
