package org.kuali.student.common.ui.client.mvc.history;

import org.kuali.student.common.ui.client.mvc.UncheckedApplicationEvent;

import com.google.gwt.user.client.ui.Widget;

public class NavigationEvent extends UncheckedApplicationEvent<NavigationEventHandler>{
    public static final Type<NavigationEventHandler> TYPE = new Type<NavigationEventHandler>();
    
    private final Widget originatingWidget;
    
    public NavigationEvent(final Widget originatingWidget) {
        this.originatingWidget = originatingWidget;
    }
    
    @Override
    protected void dispatch(NavigationEventHandler handler) {
        handler.onNavigationEvent(this);
    }

    @Override
    public com.google.gwt.event.shared.GwtEvent.Type<NavigationEventHandler> getAssociatedType() {
        return TYPE;
    }

    public Widget getOriginatingWidget() {
        return originatingWidget;
    }

}
