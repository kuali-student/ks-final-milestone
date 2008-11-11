package edu.umd.ks.poc.lum.lu.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;

import org.kuali.student.poc.common.util.UUIDHelper;

@Entity
public class Atp {
	@Id
	private String atpId;
	private String atpName;

	/**
	 * AutoGenerate the Id
	 */
	@PrePersist
	public void prePersist() {
		this.atpId = UUIDHelper.genStringUUID(this.atpId);
	}

	/**
	 * @return the atpId
	 */
	public String getAtpId() {
		return atpId;
	}

	/**
	 * @param atpId
	 *            the atpId to set
	 */
	public void setAtpId(String atpId) {
		this.atpId = atpId;
	}

	/**
	 * @return the atpName
	 */
	public String getAtpName() {
		return atpName;
	}

	/**
	 * @param atpName
	 *            the atpName to set
	 */
	public void setAtpName(String atpName) {
		this.atpName = atpName;
	}
}
