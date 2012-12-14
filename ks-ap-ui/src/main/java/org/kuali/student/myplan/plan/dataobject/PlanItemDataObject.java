package org.kuali.student.myplan.plan.dataobject;

import org.kuali.student.myplan.academicplan.infc.PlanItem;
import org.kuali.student.myplan.course.dataobject.CourseDetails;
import org.kuali.student.myplan.plan.util.AtpHelper;

import java.util.Date;
import java.util.List;

public class PlanItemDataObject {

    //  Common properties.
    private String id;

    private Date dateAdded;
    private String planType;
    private String atp;
    private String refObjId;
    private String refObjType;
    private String term;
    private int year;
    private String creditPref;


    public static PlanItemDataObject build(PlanItem item) {
        PlanItemDataObject itemDO = new PlanItemDataObject();

        // At the application level we are only dealing with single ATP per plan item
        if (item.getPlanPeriods() != null && item.getPlanPeriods().size() > 0) {
            itemDO.setAtp(item.getPlanPeriods().get(0));
            String[] termYear = AtpHelper.atpIdToTermAndYear(itemDO.getAtp());
            itemDO.setYear(Integer.valueOf(termYear[1]));
            itemDO.setTerm(termYear[0]);
        }

        itemDO.setDateAdded(item.getMeta().getCreateTime());
        itemDO.setId(item.getId());
        itemDO.setRefObjId(item.getRefObjectId());
        itemDO.setRefObjType(item.getRefObjectType());
        itemDO.setPlanType(item.getTypeKey());

        return itemDO;
    }

    //  Planned course specific properties.

    //  Ids of the ATPs associated with this course.
    private List<String> atpIds;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getPlanType() {
        return planType;
    }

    public void setPlanType(String planType) {
        this.planType = planType;
    }

    public String getAtp() {
        return atp;
    }

    public void setAtp(String atp) {
        this.atp = atp;
    }

    public String getRefObjId() {
        return refObjId;
    }

    public void setRefObjId(String refObjId) {
        this.refObjId = refObjId;
    }

    public String getRefObjType() {
        return refObjType;
    }

    public void setRefObjType(String refObjType) {
        this.refObjType = refObjType;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getCreditPref() {
        return creditPref;
    }

    public void setCreditPref(String creditPref) {
        this.creditPref = creditPref;
    }

    public List<String> getAtpIds() {
        return atpIds;
    }

    public void setAtpIds(List<String> atpIds) {
        this.atpIds = atpIds;
    }
}
