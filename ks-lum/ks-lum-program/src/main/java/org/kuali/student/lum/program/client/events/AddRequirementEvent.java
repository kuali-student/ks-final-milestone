package org.kuali.student.lum.program.client.events;

import com.google.gwt.event.shared.GwtEvent;

public class AddRequirementEvent extends GwtEvent<AddRequirementEventHandler> {

    public static Type<AddRequirementEventHandler> TYPE = new Type<AddRequirementEventHandler>();
    private String programRequirementId;

    public AddRequirementEvent(String programRequirementId) {
        this.programRequirementId = programRequirementId;
    }

    @Override
    public Type<AddRequirementEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(AddRequirementEventHandler handler) {
        handler.onEvent(this);
    }

    public String getProgramRequirementId() {
        return programRequirementId;
    }
}
