/*
 * Copyright 2009 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the
 * "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.  See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.kuali.student.r2.core.atp.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.common.dto.RelationshipInfo;
import org.kuali.student.r2.core.atp.infc.AtpMilestoneRelation;
import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AtpMilestoneRelationInfo", propOrder = { "id", "typeKey",
		"stateKey", "atpKey", "milestoneKey", "effectiveDate",
		"expirationDate", "meta", "attributes", "_futureElements" })
public class AtpMilestoneRelationInfo extends RelationshipInfo implements
		AtpMilestoneRelation, Serializable {

	private static final long serialVersionUID = 1L;
	@XmlElement
	private String atpKey;
	@XmlElement
	private String milestoneKey;
	@XmlAnyElement
	private List<Element> _futureElements;

	public AtpMilestoneRelationInfo() {
		atpKey = null;
		milestoneKey = null;
		_futureElements = null;
	}

	/**
	 * Constructs a new AtpMilestoneRelationInfo from another
	 * AtpMilestoneRelation.
	 * 
	 * @param amr
	 *            the AtpMilestoneRelation to copy
	 */
	public AtpMilestoneRelationInfo(AtpMilestoneRelation amr) {
		super(amr);
		if (null != amr) {
			this.atpKey = amr.getAtpKey();
			this.milestoneKey = amr.getMilestoneKey();
			_futureElements = null;
		}
	}

	@Override
	public String getAtpKey() {
		return atpKey;
	}

	@Override
	public void setAtpKey(String atpKey) {
		this.atpKey = atpKey;
	}

	@Override
	public String getMilestoneKey() {
		return milestoneKey;
	}

	@Override
	public void setMilestoneKey(String milestoneKey) {
		this.milestoneKey = milestoneKey;
	}
}
