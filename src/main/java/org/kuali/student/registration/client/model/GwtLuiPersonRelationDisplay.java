/**
 * 
 */
package org.kuali.student.registration.client.model;

import java.io.Serializable;

import org.kuali.student.ui.personidentity.client.model.GwtPersonDisplay;
import org.kuali.student.ui.personidentity.client.model.lu.GwtLuiDisplay;

/**
 * @author Garey
 *
 */
public class GwtLuiPersonRelationDisplay implements Serializable {

	
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 4017020403341745402L;

	private GwtLuiDisplay luiDisplay;
    
    private GwtPersonDisplay personDisplay;
    
    private GwtLuiPersonRelationTypeInfo luiPersonRelationType;
    
    private GwtRelationStateInfo relationState;
    
    private String luiPersonRelationId;
    
    public GwtLuiDisplay getLuiDisplay() {
        return luiDisplay;
    }
    public void setLuiDisplay(GwtLuiDisplay luiDisplay) {
        this.luiDisplay = luiDisplay;
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
    public String getLuiPersonRelationId() {
        return luiPersonRelationId;
    }
    public void setLuiPersonRelationId(String luiPersonRelationId) {
        this.luiPersonRelationId = luiPersonRelationId;
    }
	
}
