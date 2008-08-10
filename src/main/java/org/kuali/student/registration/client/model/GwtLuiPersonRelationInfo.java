package org.kuali.student.registration.client.model;



import java.io.Serializable;
import java.util.Date;

import org.kuali.student.ui.personidentity.client.model.GwtPersonDisplay;
import org.kuali.student.ui.personidentity.client.model.lu.GwtLuiDisplay;

public class GwtLuiPersonRelationInfo implements Serializable {       

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2143296234456225835L;

	private String luiPersonRelationId;
    
    private GwtLuiDisplay GwtLuiDisplay;
    
    private GwtPersonDisplay personDisplay;
    
    private GwtLuiPersonRelationTypeInfo luiPersonRelationType;
    
    private GwtRelationStateInfo relationState;
    
    private Date effectiveStartDate;
    
    private Date effectiveEndDate;
                   
    public String getLuiPersonRelationId() {
        return luiPersonRelationId;
    }
    public void setLuiPersonRelationId(String luiPersonRelationId) {
        this.luiPersonRelationId = luiPersonRelationId;
    }
    public GwtLuiDisplay getGwtLuiDisplay() {
        return GwtLuiDisplay;
    }
    public void setGwtLuiDisplay(GwtLuiDisplay GwtLuiDisplay) {
        this.GwtLuiDisplay = GwtLuiDisplay;
    }
    public GwtPersonDisplay getPersonDisplay() {
        return personDisplay;
    }
    public void setPersonDisplay(GwtPersonDisplay personDisplay) {
        this.personDisplay = personDisplay;
    }
    public GwtLuiPersonRelationTypeInfo getLuiPersonRelationType() {
        return luiPersonRelationType;
    }
    public void setLuiPersonRelationType(GwtLuiPersonRelationTypeInfo luiPersonRelationType) {
        this.luiPersonRelationType = luiPersonRelationType;
    }
    public GwtRelationStateInfo getRelationState() {
        return relationState;
    }
    public void setRelationState(GwtRelationStateInfo relationState) {
        this.relationState = relationState;
    }
    public Date getEffectiveStartDate() {
        return effectiveStartDate;
    }
    public void setEffectiveStartDate(Date effectiveStartDate) {
        this.effectiveStartDate = effectiveStartDate;
    }
    public Date getEffectiveEndDate() {
        return effectiveEndDate;
    }
    public void setEffectiveEndDate(Date effectiveEndDate) {
        this.effectiveEndDate = effectiveEndDate;
    }
}
