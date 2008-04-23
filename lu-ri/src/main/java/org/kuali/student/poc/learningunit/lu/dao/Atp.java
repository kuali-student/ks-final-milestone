package org.kuali.student.poc.learningunit.lu.dao;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;

@Entity
@TableGenerator(name = "idGen")
public class Atp {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "idGen")
	private String atpId;
	private String atpName;
	/**
	 * @return the atpId
	 */
	public String getAtpId() {
		return atpId;
	}
	/**
	 * @param atpId the atpId to set
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
	 * @param atpName the atpName to set
	 */
	public void setAtpName(String atpName) {
		this.atpName = atpName;
	}
}
