package edu.umd.ks.poc.lum.lu.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.kuali.student.poc.common.util.UUIDHelper;

@Entity
public class CluSet {
	@Id
	private String cluSetId;

	private String cluSetName;

	private String description;

	@Temporal(TemporalType.TIMESTAMP)
	private Date effectiveStartDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date effectiveEndDate;

	@ManyToMany
	private List<CluSet> cluSetList;

	@ManyToMany
	private List<Clu> cluList;

	@ManyToOne
	private Atp effectiveStartCycle;
	@ManyToOne
	private Atp effectiveEndCycle;
	
	@OneToOne(cascade=CascadeType.ALL)
	private CluCrit cluCriteria;

	/**
	 * AutoGenerate the Id
	 */
	@PrePersist
	public void prePersist() {
		this.cluSetId = UUIDHelper.genStringUUID(this.cluSetId);
	}

	/**
	 * @return the cluSetId
	 */
	public String getCluSetId() {
		return cluSetId;
	}

	/**
	 * @param cluSetId
	 *            the cluSetId to set
	 */
	public void setCluSetId(String cluSetId) {
		this.cluSetId = cluSetId;
	}

	/**
	 * @return the cluSetName
	 */
	public String getCluSetName() {
		return cluSetName;
	}

	/**
	 * @param cluSetName
	 *            the cluSetName to set
	 */
	public void setCluSetName(String cluSetName) {
		this.cluSetName = cluSetName;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the effectiveStartDate
	 */
	public Date getEffectiveStartDate() {
		return effectiveStartDate;
	}

	/**
	 * @param effectiveStartDate
	 *            the effectiveStartDate to set
	 */
	public void setEffectiveStartDate(Date effectiveStartDate) {
		this.effectiveStartDate = effectiveStartDate;
	}

	/**
	 * @return the effectiveEndDate
	 */
	public Date getEffectiveEndDate() {
		return effectiveEndDate;
	}

	/**
	 * @param effectiveEndDate
	 *            the effectiveEndDate to set
	 */
	public void setEffectiveEndDate(Date effectiveEndDate) {
		this.effectiveEndDate = effectiveEndDate;
	}

	/**
	 * @return the cluSetList
	 */
	public List<CluSet> getCluSetList() {
		if (cluSetList == null) {
			cluSetList = new ArrayList<CluSet>();
		}
		return cluSetList;
	}

	/**
	 * @param cluSetList
	 *            the cluSetList to set
	 */
	public void setCluSetList(List<CluSet> cluSetList) {
		this.cluSetList = cluSetList;
	}

	/**
	 * @return the cluList
	 */
	public List<Clu> getCluList() {
		if (cluList == null) {
			cluList = new ArrayList<Clu>();
		}
		return cluList;
	}

	/**
	 * @param cluList
	 *            the cluList to set
	 */
	public void setCluList(List<Clu> cluList) {
		this.cluList = cluList;
	}

	/**
	 * @return the cluCriteria
	 */
	public CluCrit getCluCriteria() {
		return cluCriteria;
	}

	/**
	 * @param cluCriteria the cluCriteria to set
	 */
	public void setCluCriteria(CluCrit cluCriteria) {
		this.cluCriteria = cluCriteria;
	}

	/**
	 * @return the effectiveStartCycle
	 */
	public Atp getEffectiveStartCycle() {
		return effectiveStartCycle;
	}

	/**
	 * @param effectiveStartCycle the effectiveStartCycle to set
	 */
	public void setEffectiveStartCycle(Atp effectiveStartCycle) {
		this.effectiveStartCycle = effectiveStartCycle;
	}

	/**
	 * @return the effectiveEndCycle
	 */
	public Atp getEffectiveEndCycle() {
		return effectiveEndCycle;
	}

	/**
	 * @param effectiveEndCycle the effectiveEndCycle to set
	 */
	public void setEffectiveEndCycle(Atp effectiveEndCycle) {
		this.effectiveEndCycle = effectiveEndCycle;
	}

}
