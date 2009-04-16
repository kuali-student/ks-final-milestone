package org.kuali.student.lum.lu.ui.main.client.events;

import org.kuali.student.common.ui.client.mvc.ApplicationEvent;

public class ChangeViewStateEvent<V extends Enum<?>> extends ApplicationEvent<ChangeViewStateHandler>{
    public static final Type<ChangeViewStateHandler> TYPE = new Type<ChangeViewStateHandler>();
    V viewType;
    
    public ChangeViewStateEvent(V viewType){
        this.viewType = viewType;
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

}
