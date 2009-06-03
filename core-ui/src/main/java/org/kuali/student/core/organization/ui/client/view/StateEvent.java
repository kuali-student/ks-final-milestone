package org.kuali.student.core.organization.ui.client.view;

import com.google.gwt.event.shared.GwtEvent;

public class StateEvent extends GwtEvent<StateHandler> {
    public static final Type<StateHandler> TYPE = new Type<StateHandler>();
    
    String state;
    
    public StateEvent() {}
    public StateEvent(String state) {this.state = state;}

    @Override
    protected void dispatch(StateHandler handler) {
        handler.onStateChange(this);
    }

    @Override
    public Type<StateHandler> getAssociatedType() {
        return TYPE;
    }
    
    public String getState() {
        return state;
    }

}
