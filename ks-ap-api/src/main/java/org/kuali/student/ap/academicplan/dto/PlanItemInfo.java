/*
 * Copyright 2014 The Kuali Foundation Licensed under the
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
package org.kuali.student.ap.academicplan.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.ap.academicplan.constants.AcademicPlanServiceConstants;
import org.kuali.student.ap.academicplan.infc.PlanItem;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.kuali.student.r2.common.dto.MetaInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.infc.Meta;
import org.w3c.dom.Element;

/**
 * PlanItem message structure
 *
 * @author Kuali Student Team
 * @version 1.0 (Dev)
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PlanItemInfo", propOrder = {"refObjectId", "refObjectType", "learningPlanId", "planTermIds", "id",
        "credit", "category", "typeKey", "stateKey", "name", "descr", "meta", "attributes", "_futureElements"})
public class PlanItemInfo extends IdEntityInfo implements PlanItem {

 	private static final long serialVersionUID = 7795677206429530520L;

    @XmlElement
    private String refObjectId;

    @XmlElement
    private String refObjectType;

    @XmlElement
    private String learningPlanId;

    @XmlElement
    private List<String> planTermIds;

    @XmlElement
    private BigDecimal credit;

    @XmlElement
    private AcademicPlanServiceConstants.ItemCategory category;

    @XmlAnyElement
    private List<Element> _futureElements;

    public PlanItemInfo() {
        this.planTermIds = new ArrayList<String>();
    }

    public PlanItemInfo(PlanItem item) {
        copy(item);
    }

    public void copy(PlanItem item) {
        if(null != item) {
            this.setId(item.getId());
            this.setName(item.getName());
			this.setTypeKey(item.getTypeKey());
			this.setStateKey(item.getStateKey());
            this.refObjectId = item.getRefObjectId();
            this.refObjectType = item.getRefObjectType();
            this.learningPlanId = item.getLearningPlanId();
			this.credit = item.getCredits();
            this.category = item.getCategory();

			List<String> planTermIds = item.getPlanTermIds();
			if (null != planTermIds) {
				this.planTermIds = new ArrayList<String>(planTermIds);
            }

            this.setDescr((null != item.getDescr()) ? new RichTextInfo(item.getDescr()) : null);
            
            Meta meta = item.getMeta();
            if (meta != null)
            	this.setMeta(new MetaInfo(meta));

			List<? extends Attribute> attrs = item.getAttributes();
			if (attrs != null) {
				List<AttributeInfo> attrInfos = new ArrayList<AttributeInfo>(attrs.size());
				for (Attribute attr : attrs) {
					attrInfos.add(new AttributeInfo(attr));
				}
				this.setAttributes(attrInfos);
			}
        }
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

    public String getLearningPlanId() {
        return learningPlanId;
    }

    public void setLearningPlanId(String learningPlanId) {
        this.learningPlanId = learningPlanId;
    }

    public List<String> getPlanTermIds() {
        return planTermIds;
    }

    public void setPlanTermIds(List<String> planTermIds) {
        this.planTermIds = planTermIds;
    }

     public BigDecimal getCredits() {
        return credit;
    }

    public void setCredit(BigDecimal credit) {
         this.credit = credit;
    }

    public AcademicPlanServiceConstants.ItemCategory getCategory() {
        return this.category;
    }

    public void setCategory(AcademicPlanServiceConstants.ItemCategory  category) {
        this.category=category;
    }

}
