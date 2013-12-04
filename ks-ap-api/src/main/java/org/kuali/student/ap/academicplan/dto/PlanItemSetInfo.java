package org.kuali.student.ap.academicplan.dto;

import org.kuali.student.ap.academicplan.infc.PlanItemSet;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.dto.TypeStateEntityInfo;
import org.w3c.dom.Element;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * LearningPlan message structure
 *
 * @Author kmuthu
 * Date: 1/5/12
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PlanItemSetInfo", propOrder = {"planItemIds", "interestedInItemsCount", "interestedInAllItems", "id",
        "typeKey", "stateKey", "descr", "meta", "attributes", "_futureElements"})
public class PlanItemSetInfo extends TypeStateEntityInfo implements PlanItemSet  {

    @XmlAttribute
    private String id;

    @XmlElement
    private RichTextInfo descr;

    @XmlElement
    private List<String> planItemIds;

    @XmlElement
    private Integer interestedInItemsCount;

    @XmlElement
    private boolean interestedInAllItems;

    @XmlAnyElement
    private List<Element> _futureElements;


    public PlanItemSetInfo() {
        this.id = null;
        this.descr = null;
        this.interestedInAllItems = false;
        this.interestedInItemsCount = null;
        this.planItemIds = new ArrayList<String>();
        this._futureElements = null;
    }

    public PlanItemSetInfo(PlanItemSet planSet) {
        super(planSet);

        if(null != planSet) {
            this.id = planSet.getId();
            this.interestedInAllItems = planSet.isInterestedInAllItems();
            this.interestedInItemsCount = planSet.getInterestedInItemsCount();

            this.planItemIds = (null != planSet.getPlanItemIds()) ? new ArrayList<String>( planSet.getPlanItemIds() ) : new ArrayList<String>();
            this.descr = (null != planSet.getDescr()) ? new RichTextInfo(planSet.getDescr()) : null;
        }
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public RichTextInfo getDescr() {
        return descr;
    }

    public void setDescr(RichTextInfo descr) {
        this.descr = descr;
    }

    @Override
    public List<String> getPlanItemIds() {
        return planItemIds;
    }

    public void setPlanItemIds(List<String> planItemIds) {
        this.planItemIds = planItemIds;
    }

    @Override
    public Integer getInterestedInItemsCount() {
        return interestedInItemsCount;
    }

    public void setInterestedInItemsCount(Integer interestedInItemsCount) {
        this.interestedInItemsCount = interestedInItemsCount;
    }

    @Override
    public boolean isInterestedInAllItems() {
        return interestedInAllItems;
    }

    public void setInterestedInAllItems(boolean interestedInAllItems) {
        this.interestedInAllItems = interestedInAllItems;
    }
}
