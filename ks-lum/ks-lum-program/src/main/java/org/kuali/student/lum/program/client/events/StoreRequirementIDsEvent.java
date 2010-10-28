package org.kuali.student.lum.program.client.events;

import java.util.List;

import com.google.gwt.event.shared.GwtEvent;

public class StoreRequirementIDsEvent extends GwtEvent<StoreRequirementIdsEventHandler> {

    public static Type<StoreRequirementIdsEventHandler> TYPE = new Type<StoreRequirementIdsEventHandler>();
    private String programId;
    private String programType;
    private List<String> programRequirementIds;

    public StoreRequirementIDsEvent(String programId, String programType, List<String> programRequirementIds) {
        this.programId = programId;
        this.programType = programType;
        this.programRequirementIds = programRequirementIds;        
    }

    @Override
    public Type<StoreRequirementIdsEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(StoreRequirementIdsEventHandler handler) {
        handler.onEvent(this);
    }

    public String getProgramId() {
        return programId;
    }

    public String getProgramType() {
        return programType;
    }

    public List<String> getProgramRequirementIds() {
        return programRequirementIds;
    }    
}
