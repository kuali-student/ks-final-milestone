package org.kuali.student.lum.program.client.events;

import java.util.List;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class StoreRequirementIDsEvent extends GwtEvent<StoreRequirementIDsEvent.Handler> {

    public static Type<Handler> TYPE = new Type<Handler>();
    private String programId;
    private String programType;
    private List<String> programRequirementIds;

    public StoreRequirementIDsEvent(String programId, String programType, List<String> programRequirementIds) {
        this.programId = programId;
        this.programType = programType;
        this.programRequirementIds = programRequirementIds;        
    }

    @Override
    public Type<Handler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(Handler handler) {
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

    public static interface Handler extends EventHandler {
        void onEvent(StoreRequirementIDsEvent event);
    }
}
