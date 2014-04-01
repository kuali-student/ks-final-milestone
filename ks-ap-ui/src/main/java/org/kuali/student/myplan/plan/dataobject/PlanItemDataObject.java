package org.kuali.student.myplan.plan.dataobject;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.kuali.student.ap.academicplan.infc.PlanItem;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.context.YearTerm;
import org.kuali.student.common.collection.KSCollectionUtils;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.infc.Attribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlanItemDataObject implements Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(PlanItemDataObject.class);
	private static final long serialVersionUID = -3416993703358577253L;

	//  Common properties.
    private String id;

    private Date dateAdded;
    private String planType;
    private String atp;
    private String refObjId;
    private String refObjType;
    private String term;
    private String termName;
    private int year;
    private String creditPref;

    private String campusCode;
    private String activityCode;

    public static PlanItemDataObject build(PlanItem item) {
        PlanItemDataObject itemDO = new PlanItemDataObject();

        // At the application level we are only dealing with single ATP per plan item
        try{
            itemDO.setAtp(KSCollectionUtils.getRequiredZeroElement(item.getPlanPeriods()));
            YearTerm termYear = KsapFrameworkServiceLocator.getTermHelper().getYearTerm(itemDO.getAtp());
            itemDO.setTermName(termYear.getTermName());
            itemDO.setYear(termYear.getYear());
            itemDO.setTerm(termYear.getLongName());
        }catch (OperationFailedException e){
            LOG.warn("No term found during build",e);
        }

        itemDO.setDateAdded(item.getMeta().getCreateTime());
        itemDO.setId(item.getId());
        itemDO.setRefObjId(item.getRefObjectId());
        itemDO.setRefObjType(item.getRefObjectType());
        itemDO.setPlanType(item.getTypeKey());

        for (Attribute attr : item.getAttributes()) {
        	String value = attr.getValue();
        	if ("campusCode".equals(attr.getKey()))
        		itemDO.campusCode = value;
        	if ("activityCode".equals(attr.getKey()))
        		itemDO.activityCode = value;
        }

        if(item.getCredit()!=null)itemDO.setCreditPref(item.getCredit().toString());

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
    public String getIdXmlSafe() {
        return getId() == null ? null : getId().replace('.', '_');
    }

    public String getTermName() {
        return termName;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }

	public String getCampusCode() {
		return campusCode;
	}

	public void setCampusCode(String campusCode) {
		this.campusCode = campusCode;
	}

	public String getActivityCode() {
		return activityCode;
	}

	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}

}
