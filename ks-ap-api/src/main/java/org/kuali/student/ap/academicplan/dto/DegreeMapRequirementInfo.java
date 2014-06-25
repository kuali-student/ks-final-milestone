package org.kuali.student.ap.academicplan.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.ap.academicplan.infc.DegreeMapRequirement;
import org.w3c.dom.Element;

/**
 * Degree Map Requirement message structure
 * 
 * @Author mguilla Date: 1/21/14
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DegreeMapRequirementInfo", propOrder = { "id", "degreeMapId",
		"degreeMapEffectiveDate", "itemSeq", "refObjectId", "refObjectType",
		"seqKey", "seqNo", "creditMax", "creditMin", "critical", "milestone",
		"minimumGrade", "suggestedTermId", "requiredTermId", "_futureElements" })
public class DegreeMapRequirementInfo implements DegreeMapRequirement,
		Serializable {

	private static final long serialVersionUID = 6019908398241577527L;

	@XmlAttribute
	private String id;

	@XmlElement
	private String degreeMapId;

	@XmlElement
	private Date degreeMapEffectiveDate;

	@XmlAttribute
	private int itemSeq;

	@XmlElement
	private String refObjectId;

	@XmlElement
	private String refObjectType;

	@XmlAttribute
	private String descr;

	@XmlAttribute
	private BigDecimal creditMax;

	@XmlAttribute
	private BigDecimal creditMin;

	@XmlAttribute
	private boolean critical;

	@XmlAttribute
	private boolean milestone;

	@XmlAttribute
	private String minimumGrade;

	@XmlAttribute
	private String seqKey;

	@XmlAttribute
	private int seqNo;

	@XmlAttribute
	private String suggestedTermId;

	@XmlAttribute
	private String requiredTermId;

	@XmlAttribute
	private String notes;

	@XmlAnyElement
	private List<Element> _futureElements;

	public DegreeMapRequirementInfo() {
	}

	public DegreeMapRequirementInfo(DegreeMapRequirement copy) {
		id = copy.getId();
		degreeMapId = copy.getDegreeMapId();
		degreeMapEffectiveDate = copy.getDegreeMapEffectiveDate();
		itemSeq = copy.getItemSeq();
		refObjectId = copy.getRefObjectId();
		refObjectType = copy.getRefObjectType();
		descr = copy.getDescr();
		creditMax = copy.getCreditMax();
		creditMin = copy.getCreditMin();
		critical = copy.isCritical();
		milestone = copy.isMilestone();
		minimumGrade = copy.getMinimumGrade();
		seqKey = copy.getSeqKey();
		seqNo = copy.getSeqNo();
		suggestedTermId = copy.getSuggestedTermId();
		requiredTermId = copy.getRequiredTermId();
		notes = copy.getNotes();

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDegreeMapId() {
		return degreeMapId;
	}

	public void setDegreeMapId(String degreeMapId) {
		this.degreeMapId = degreeMapId;
	}

	public Date getDegreeMapEffectiveDate() {
		return degreeMapEffectiveDate;
	}

	public void setDegreeMapEffectiveDate(Date degreeMapEffectiveDate) {
		this.degreeMapEffectiveDate = degreeMapEffectiveDate;
	}

	public String getRefObjectId() {
		return refObjectId;
	}

	public void setRefObjectId(String refObjectId) {
		this.refObjectId = refObjectId;
	}

	public String getRefObjectType() {
		return refObjectType;
	}

	public void setRefObjectType(String refObjectType) {
		this.refObjectType = refObjectType;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public BigDecimal getCreditMax() {
		return creditMax;
	}

	public void setCreditMax(BigDecimal creditMax) {
		this.creditMax = creditMax;
	}

	public BigDecimal getCreditMin() {
		return creditMin;
	}

	public void setCreditMin(BigDecimal creditMin) {
		this.creditMin = creditMin;
	}

	public boolean isCritical() {
		return critical;
	}

	public void setCritical(boolean critical) {
		this.critical = critical;
	}

	public String getMinimumGrade() {
		return minimumGrade;
	}

	public void setMinimumGrade(String minimumGrade) {
		this.minimumGrade = minimumGrade;
	}

	public boolean isMilestone() {
		return milestone;
	}

	public void setMilestone(boolean milestone) {
		this.milestone = milestone;
	}

	public int getItemSeq() {
		return itemSeq;
	}

	public void setItemSeq(int itemSeq) {
		this.itemSeq = itemSeq;
	}

	public String getSeqKey() {
		return seqKey;
	}

	public void setSeqKey(String seqKey) {
		this.seqKey = seqKey;
	}

	public int getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(int seqNo) {
		this.seqNo = seqNo;
	}

	public String getSuggestedTermId() {
		return suggestedTermId;
	}

	public void setSuggestedTermId(String suggestedTermId) {
		this.suggestedTermId = suggestedTermId;
	}

	public String getRequiredTermId() {
		return requiredTermId;
	}

	public void setRequiredTermId(String requiredTermId) {
		this.requiredTermId = requiredTermId;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

}
