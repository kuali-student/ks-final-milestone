/*
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
package org.kuali.student.enrollment.exemption.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.enrollment.exemption.infc.Exemption;
import org.kuali.student.enrollment.exemption.infc.ExemptionRequest;
import org.kuali.student.enrollment.exemption.infc.RestrictionOverride;
import org.kuali.student.enrollment.exemption.infc.DateOverride;
import org.kuali.student.enrollment.exemption.infc.MilestoneOverride;
import org.kuali.student.enrollment.exemption.infc.StatementOverride;
import org.kuali.student.enrollment.exemption.infc.HoldOverride;
import org.kuali.student.enrollment.exemption.infc.LearningResultOverride;

import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ExemptionInfo", propOrder = { "id", "typeKey", "stateKey", 
                "name", "descr", "exemptionRequestId", "exemptedPersonId", 
		"qualifierTypeKey", "qualifierId", "effectiveDate", 
		"expirationDate", "useLimit", "useCount", 
                "restrictionOverride", "dateOverride", 
                "milestoneOverride", "statementOverride", "holdOverride", 
                "meta", "attributes", "_futureElements" })

public class ExemptionInfo extends IdEntityInfo implements Exemption, Serializable {
	private static final long serialVersionUID = 1L;

	@XmlElement
	private String exemptionRequestId;

	@XmlElement
	private String exemptedPersonId;

	@XmlElement
	private String qualifierTypeKey;

	@XmlElement
	private String qualifierId;

	@XmlElement
	private Date effectiveDate;

	@XmlElement
	private Date expirationDate;

	@XmlElement
	private Integer useLimit;

	@XmlElement
	private Integer useCount;

	@XmlElement
	private RestrictionOverrideInfo restrictionOverride;

	@XmlElement
	private DateOverrideInfo dateOverride;

	@XmlElement
	private MilestoneOverrideInfo milestoneOverride;

	@XmlElement
	private StatementOverrideInfo statementOverride;

	@XmlElement
	private HoldOverrideInfo holdOverride;

	@XmlElement
	private LearningResultOverrideInfo learningResultOverride;

	@XmlAnyElement
	private List<Element> _futureElements;

	public ExemptionInfo() {
		super();

		exemptionRequestId = null;
		exemptedPersonId = null;
		qualifierTypeKey = null;
		qualifierId = null;
		effectiveDate = null;
		expirationDate = null;
		useLimit = null;
		useCount = null;

		restrictionOverride = null;
		dateOverride = null;
		milestoneOverride = null;
		statementOverride = null;
		holdOverride = null;
		learningResultOverride = null;

		_futureElements = null;
	}

	/**
	 * Constructs a new ExemptionInfo from another Exemption.
	 * 
	 * @param exemption the Exemption to copy
	 */
	public ExemptionInfo(Exemption exemption) {
		super(exemption);
		if (null != exemption) {
			this.exemptionRequestId = exemption.getExemptionRequestId();
			this.exemptedPersonId = exemption.getExemptedPersonId();
			this.qualifierTypeKey = exemption.getQualifierTypeKey();
			this.qualifierId = exemption.getQualifierId();
			this.effectiveDate = exemption.getEffectiveDate();
			this.expirationDate = exemption.getExpirationDate();
			this.useLimit = exemption.getUseLimit();
			this.useCount = exemption.getUseCount();

			if (exemption.getRestrictionOverride() != null) {
			    this.restrictionOverride = new RestrictionOverrideInfo(exemption.getRestrictionOverride());
			}

			if (exemption.getDateOverride() != null) {
			    this.dateOverride = new DateOverrideInfo(exemption.getDateOverride());
			}

			if (exemption.getMilestoneOverride() != null) {
			    this.milestoneOverride = new MilestoneOverrideInfo(exemption.getMilestoneOverride());
			}

			if (exemption.getStatementOverride() != null) {
			    this.statementOverride = new StatementOverrideInfo(exemption.getStatementOverride());
			}

			if (exemption.getHoldOverride() != null) {
			    this.holdOverride = new HoldOverrideInfo(exemption.getHoldOverride());
			}

			if (exemption.getLearningResultOverride() != null) {
			    this.learningResultOverride = new LearningResultOverrideInfo(exemption.getLearningResultOverride());
			}
		}
		
		_futureElements = null;
	}

	@Override
	public String getExemptionRequestId() {
	    return exemptionRequestId;
	}

	public void setExemptionRequestId(String exemptionRequestId) {
	    this.exemptionRequestId = exemptionRequestId;
	}

	@Override
	public String getExemptedPersonId() {
	    return exemptedPersonId;
	}

	public void setExemptedPersonId(String exemptedPersonId) {
	    this.exemptedPersonId = exemptedPersonId;
	}

	@Override
	public String getQualifierTypeKey() {
	    return qualifierTypeKey;
	}

	public void setQualfiierTypeKey(String qualiferTypeKey) {
	    this.qualifierTypeKey = qualifierTypeKey;
	}

	@Override
	public String getQualifierId() {
	    return qualifierId;
	}

	public void setQualfiierId(String qualiferId) {
	    this.qualifierId = qualifierId;
	}

	@Override
	public Date getEffectiveDate() {
	    return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
	    this.effectiveDate = effectiveDate;
	}

	@Override
	public Date getExpirationDate() {
	    return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
	    this.expirationDate = expirationDate;
	}

	@Override
	public Integer getUseLimit() {
	    return useLimit;
	}

	public void setUseLimit(Integer useLimit) {
	    this.useLimit = useLimit;
	}

	@Override
	public Integer getUseCount() {
	    return useCount;
	}

	public void setUseCount(Integer useCount) {
	    this.useCount = useCount;
	}

	@Override
	public RestrictionOverrideInfo getRestrictionOverride() {
	    return restrictionOverride;
	}

	public void setRestrictionOverride(RestrictionOverride restrictionOverride) {
	    this.restrictionOverride = new RestrictionOverrideInfo(restrictionOverride);
	}

	@Override
	public DateOverrideInfo getDateOverride() {
	    return dateOverride;
	}

	public void setDateOverride(DateOverride dateOverride) {
	    this.dateOverride = new DateOverrideInfo(dateOverride);
	}

	@Override
	public MilestoneOverrideInfo getMilestoneOverride() {
	    return milestoneOverride;
	}

	public void setMilestoneOverride(MilestoneOverride milestoneOverride) {
	    this.milestoneOverride = new MilestoneOverrideInfo(milestoneOverride);
	}

	@Override
	public StatementOverrideInfo getStatementOverride() {
	    return statementOverride;
	}

	public void setStatementOverride(StatementOverride statementOverride) {
	    this.statementOverride = new StatementOverrideInfo(statementOverride);
	}

	@Override
	public HoldOverrideInfo getHoldOverride() {
	    return holdOverride;
	}

	public void setHoldOverride(HoldOverride holdOverride) {
	    this.holdOverride = new HoldOverrideInfo(holdOverride);
	}

	@Override
	public LearningResultOverrideInfo getLearningResultOverride() {
	    return learningResultOverride;
	}

	public void setLearningResultOverride(LearningResultOverride learningResultOverride) {
	    this.learningResultOverride = new LearningResultOverrideInfo(learningResultOverride);
	}
}
