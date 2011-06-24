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
@XmlType(name = "ExemptionRequestInfo", propOrder = { "id", "typeKey", 
                "stateKey", "name", "descr", "checkKey", "personId", 
                "qualifierTypeKey", "qualifierId", "requestDate", 
                "approvedByPersonId", "approvedDate", "restrictionOverride", 
                "dateOverride", "milestoneOverride", "statementOverride",
		"holdOverride", "meta", "attributes", "_futureElements" })

public class ExemptionRequestInfo extends IdEntityInfo implements ExemptionRequest, Serializable {
	private static final long serialVersionUID = 1L;

	@XmlElement
	private String checkKey;

	@XmlElement
	private String personId;

	@XmlElement
	private String qualifierTypeKey;

	@XmlElement
	private String qualifierId;

	@XmlElement
	private Date requestDate;

	@XmlElement
	private String approvedByPersonId;

	@XmlElement
	private Date approvedDate;

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

	public ExemptionRequestInfo() {
		super();

		checkKey = null;
		personId = null;
		qualifierTypeKey = null;
		qualifierId = null;
		requestDate = null;
		approvedByPersonId = null;
		approvedDate = null;
		
		restrictionOverride = null;
		dateOverride = null;
		milestoneOverride = null;
		statementOverride = null;
		holdOverride = null;
		learningResultOverride = null;

		_futureElements = null;
	}

	/**
	 * Constructs a new ExemptionRequestInfo from another 
	 * ExemptionRequest.
	 * 
	 * @param exemption the ExemptionRequest to copy
	 */
	public ExemptionRequestInfo(ExemptionRequest request) {
		super(request);
		if (null != request) {
			this.checkKey = request.getCheckKey();
			this.personId = request.getPersonId();
			this.qualifierTypeKey = request.getQualifierTypeKey();
			this.qualifierId = request.getQualifierId();
			this.requestDate = request.getRequestDate();
			this.approvedByPersonId = request.getApprovedByPersonId();
			this.approvedDate = request.getApprovedDate();

			if (request.getRestrictionOverride() != null) {
			    this.restrictionOverride = new RestrictionOverrideInfo(request.getRestrictionOverride());
			}

			if (request.getDateOverride() != null) {
			    this.dateOverride = new DateOverrideInfo(request.getDateOverride());
			}

			if (request.getMilestoneOverride() != null) {
			    this.milestoneOverride = new MilestoneOverrideInfo(request.getMilestoneOverride());
			}

			if (request.getStatementOverride() != null) {
			    this.statementOverride = new StatementOverrideInfo(request.getStatementOverride());
			}

			if (request.getHoldOverride() != null) {
			    this.holdOverride = new HoldOverrideInfo(request.getHoldOverride());
			}

			if (request.getLearningResultOverride() != null) {
			    this.learningResultOverride = new LearningResultOverrideInfo(request.getLearningResultOverride());
			}
		}
		
		_futureElements = null;
	}

	@Override
	public String getCheckKey() {
	    return checkKey;
	}

	public void setCheckKey(String checkKey) {
	    this.checkKey = checkKey;
	}

	@Override
	public String getPersonId() {
	    return personId;
	}

	public void setPersonId(String personId) {
	    this.personId = personId;
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
	public Date getRequestDate() {
	    return requestDate;
	}

	public void setRequestDate(Date requestDate) {
	    this.requestDate = requestDate;
	}

	@Override
	public String getApprovedByPersonId() {
	    return approvedByPersonId;
	}

	public void setApprovedByPersonId(String approvedByPersonId) {
	    this.approvedByPersonId = approvedByPersonId;
	}

	@Override
	public Date getApprovedDate() {
	    return approvedDate;
	}

	public void setApprovedDate(Date approvedDate) {
	    this.approvedDate = approvedDate;
	}

	@Override
	public RestrictionOverride getRestrictionOverride() {
	    return restrictionOverride;
	}

	public void setRestrictionOverride(RestrictionOverride restrictionOverride) {
	    this.restrictionOverride = new RestrictionOverrideInfo(restrictionOverride);
	}

	@Override
	public DateOverride getDateOverride() {
	    return dateOverride;
	}

	public void setDateOverride(DateOverride dateOverride) {
	    this.dateOverride = new DateOverrideInfo(dateOverride);
	}

	@Override
	public MilestoneOverride getMilestoneOverride() {
	    return milestoneOverride;
	}

	public void setMilestoneOverride(MilestoneOverride milestoneOverride) {
	    this.milestoneOverride = new MilestoneOverrideInfo(milestoneOverride);
	}

	@Override
	public StatementOverride getStatementOverride() {
	    return statementOverride;
	}

	public void setStatementOverride(StatementOverride statementOverride) {
	    this.statementOverride = new StatementOverrideInfo(statementOverride);
	}

	@Override
	public HoldOverride getHoldOverride() {
	    return holdOverride;
	}

	public void setHoldOverride(HoldOverride holdOverride) {
	    this.holdOverride = new HoldOverrideInfo(holdOverride);
	}

	@Override
	public LearningResultOverride getLearningResultOverride() {
	    return learningResultOverride;
	}

	public void setLearningResultOverride(LearningResultOverride learningResultOverride) {
	    this.learningResultOverride = new LearningResultOverrideInfo(learningResultOverride);
	}
}
