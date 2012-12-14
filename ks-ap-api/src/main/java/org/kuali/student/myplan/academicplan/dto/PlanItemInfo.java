package org.kuali.student.myplan.academicplan.dto;

import org.kuali.student.myplan.academicplan.infc.PlanItem;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.dto.TypeStateEntityInfo;
import org.w3c.dom.Element;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * PlanItem message structure
 *
 * @Author kmuthu
 * Date: 1/5/12
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PlanItemInfo", propOrder = {"refObjectId", "refObjectType", "learningPlanId", "planPeriods", "id",
        "typeKey", "stateKey", "descr", "meta", "attributes", "_futureElements"})
public class PlanItemInfo extends TypeStateEntityInfo implements PlanItem {

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
            this.refObjectId = item.getRefObjectId();
            this.refObjectType = item.getRefObjectType();
            this.learningPlanId = item.getLearningPlanId();

            if(null != item.getPlanPeriods()) {
                for(String atpId : item.getPlanPeriods()) {
                    this.planPeriods.add(atpId);
                }
            }

            this.descr = (null != item.getDescr()) ? new RichTextInfo(item.getDescr()) : null;

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
}
