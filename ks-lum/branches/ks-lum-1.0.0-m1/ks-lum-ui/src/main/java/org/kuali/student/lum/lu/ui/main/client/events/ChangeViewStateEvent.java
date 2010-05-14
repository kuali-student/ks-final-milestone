/*
 * Copyright 2009 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 * 
 * http://www.osedu.org/licenses/ECL-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
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
