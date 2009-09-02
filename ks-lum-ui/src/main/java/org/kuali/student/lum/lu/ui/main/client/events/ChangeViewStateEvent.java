package org.kuali.student.lum.lu.ui.main.client.events;

import org.kuali.student.common.ui.client.mvc.ApplicationEvent;

public class ChangeViewStateEvent<V extends Enum<?>> extends ApplicationEvent<ChangeViewStateHandler>{
    public static final Type<ChangeViewStateHandler> TYPE = new Type<ChangeViewStateHandler>();
    V viewType;
    Object eventSource;
    
    public ChangeViewStateEvent(V viewType){
        this.viewType = viewType;
    }
    
    public ChangeViewStateEvent(V viewType, Object eventSource){
        this.viewType = viewType;
        this.eventSource = eventSource;
    }

    @Override
    protected void dispatch(ChangeViewStateHandler handler) {
        handler.onViewStateChange(this);
    }

    @Override
    public com.google.gwt.event.shared.GwtEvent.Type<ChangeViewStateHandler> getAssociatedType() {
        return TYPE;
    }
    
    public V getViewType(){
        return viewType;
    }

    public Object getEventSource() {
        return eventSource;
    }

    public void setEventSource(Object eventSource) {
        this.eventSource = eventSource;
    }
    
}
