package org.kuali.student.ap.academicplan.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.ap.academicplan.infc.PlanItem;
import org.kuali.student.ap.academicplan.service.AcademicPlanServiceConstants;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.dto.TypeStateEntityInfo;
import org.kuali.student.r2.common.infc.Attribute;
import org.w3c.dom.Element;

/**
 * PlanItem message structure
 *
 * @Author kmuthu Date: 1/5/12
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PlanItemInfo", propOrder = {"refObjectId", "refObjectType", "learningPlanId", "planPeriods", "id",
        "credit", "category", "typeKey", "stateKey", "descr", "meta", "attributes", "_futureElements"})
public class PlanItemInfo extends TypeStateEntityInfo implements PlanItem {

 	private static final long serialVersionUID = 7795677206429530520L;

	@XmlAttribute
    private String id;

    @XmlElement
    private RichTextInfo descr;

    @XmlElement
    private String refObjectId;

    @XmlElement
    private String refObjectType;

    @XmlElement
    private String learningPlanId;

    @XmlElement
    private List<String> planPeriods;

    @XmlElement
    private BigDecimal credit;

    @XmlElement
    private AcademicPlanServiceConstants.ItemCategory category;

    @SuppressWarnings("unused")
	@XmlAnyElement
    private List<Element> _futureElements;

    public PlanItemInfo() {
        this.id = null;
        this.descr = null;
        this.refObjectId = null;
        this.refObjectType = null;
        this.learningPlanId = null;
        this.planPeriods = new ArrayList<String>();
        this._futureElements = null;
    }

    public PlanItemInfo(PlanItem item) {
        super(item);

        if(null != item) {
            this.id = item.getId();
			this.setTypeKey(item.getTypeKey());
			this.setStateKey(item.getStateKey());
            this.refObjectId = item.getRefObjectId();
            this.refObjectType = item.getRefObjectType();
            this.learningPlanId = item.getLearningPlanId();
			this.credit = item.getCredit();
            this.category = item.getCategory();

			List<String> planPeriods = item.getPlanPeriods();
			if (null != planPeriods) {
				this.planPeriods = new ArrayList<String>(planPeriods);
            }

            this.descr = (null != item.getDescr()) ? new RichTextInfo(item.getDescr()) : null;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public RichTextInfo getDescr() {
        return descr;
    }

    public void setDescr(RichTextInfo descr) {
        this.descr = descr;
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

    public List<String> getPlanPeriods() {
        return planPeriods;
    }

    public void setPlanPeriods(List<String> planPeriods) {
        this.planPeriods = planPeriods;
    }

     public BigDecimal getCredit() {
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
