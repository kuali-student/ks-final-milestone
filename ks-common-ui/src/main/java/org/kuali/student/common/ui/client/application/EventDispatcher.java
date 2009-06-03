package org.kuali.student.common.ui.client.application;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.event.shared.GwtEvent.Type;

public class EventDispatcher implements HasHandlers {
    private HandlerManager handlerManager = new HandlerManager(this);

    public <H extends EventHandler> HandlerRegistration addHandler(Type<H> type, H handler) {
        return handlerManager.addHandler(type, handler);
    }

    public void fireEvent(GwtEvent<?> event) {
        handlerManager.fireEvent(event);
    }

    public <H extends EventHandler> H getHandler(Type<H> type, int index) {
        return handlerManager.getHandler(type, index);
    }

    public int getHandlerCount(Type<?> type) {
        return handlerManager.getHandlerCount(type);
    }

    public boolean isEventHandled(Type<?> e) {
        return handlerManager.isEventHandled(e);
    }
    
}
