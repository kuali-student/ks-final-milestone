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
package org.kuali.student.enrollment.hold.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.enrollment.hold.infc.Hold;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.kuali.student.r2.common.dto.MetaInfo;
import org.kuali.student.r2.common.infc.Meta;
import org.kuali.student.r2.common.infc.RichText;
import org.kuali.student.r2.common.infc.Attribute;
import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HoldInfo", propOrder = { "id", "typeKey", "stateKey", "name",
		"descr", "issueIdId", "personId", "isWarning", "isOverridable",
		"effectiveDate", "releasedDate", "metaInfo", "attributes",
		"_futureElements" })
public class HoldInfo extends IdEntityInfo implements Hold, Serializable {
	private static final long serialVersionUID = 1L;

	@XmlElement
	private String personId;

	@XmlElement
	private String issueId;

	@XmlElement
	private Boolean isWarning;

	@XmlElement
	private Boolean isOverridable;

	@XmlElement
	private Date effectiveDate;

	@XmlElement
	private Date releasedDate;

	@XmlAnyElement
	private List<Element> _futureElements;

	public HoldInfo() {
		personId = null;
		isWarning = false;
		isOverridable = false;
		issueId = null;
		effectiveDate = null;
		releasedDate = null;
		_futureElements = null;
	}

	private HoldInfo(String id, String typeKey, String stateKey, String name,
			RichText descr, String issueId, String personId, Boolean isWarning,
			Boolean isOverridable, Date effectiveDate, Date releasedDate,
			Meta metaInfo, List<? extends Attribute> attributes) {
		
		this.personId = personId;
		this.isWarning = isWarning;
		this.isOverridable = isOverridable;
		this.issueId = issueId;
		this.effectiveDate = effectiveDate;
		this.releasedDate = releasedDate;

	}

	public static HoldInfo newInstance(String id, String typeKey,
			String stateKey, String name, RichText descr, String issueIdId,
			String personId, Boolean isWarning, Boolean isOverridable,
			Date effectiveDate, Date releasedDate, MetaInfo metaInfo,
			List<AttributeInfo> attributes) {
		return new HoldInfo(id, typeKey, stateKey, name, descr, issueIdId,
				personId, isWarning, isOverridable, effectiveDate,
				releasedDate, metaInfo, attributes);
	}

	public static HoldInfo createHoldInfoFromHoldInfo(Hold hold) {
		return new HoldInfo(hold.getId(), hold.getTypeKey(),
				hold.getStateKey(), hold.getName(), hold.getDescr(),
				hold.getIssueId(), hold.getPersonId(), hold.isWarning(),
				hold.isOverridable(), hold.getEffectiveDate(),
				hold.getReleasedDate(), hold.getMetaInfo(),
				hold.getAttributes());
	}

	@Override
	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	@Override
	public String getIssueId() {
		return issueId;
	}

	public void setIssueId(String issueId) {
		this.issueId = issueId;
	}

	@Override
	public Boolean isWarning() {
		return isWarning;
	}

	@Override
	public void setWarning(boolean isWarning) {
		this.isWarning = isWarning;
	}

	@Override
	public Boolean isOverridable() {
		return isOverridable;
	}

	@Override
	public void setOverridable(boolean isOverridable) {
		this.isOverridable = isOverridable;
	}

	@Override
	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = new Date(effectiveDate.getTime());
	}

	@Override
	public Date getReleasedDate() {
		return releasedDate;
	}

	public void setReleasedDate(Date releasedDate) {
		this.releasedDate = new Date(releasedDate.getTime());
	}
}
