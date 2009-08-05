/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.lum.lu.ui.main.client.events;

import org.kuali.student.common.ui.client.mvc.ApplicationEvent;
import org.kuali.student.common.ui.client.mvc.EventStateCallback;
import org.kuali.student.common.ui.client.mvc.HasEventState;
import org.kuali.student.common.ui.client.mvc.HasEventState.EventState;

/**
 * This class can be used to fire an event to tell controller to perform a 
 * workflow action. 
 * 
 * @author Kuali Student Team
 *
 * TODO: Possibly abstract out handling of event notification callback
 */
public class WorkflowActionEvent extends ApplicationEvent<WorkflowActionHandler> implements HasEventState{
    public static final Type<WorkflowActionHandler> TYPE = new Type<WorkflowActionHandler>();
    
    public enum WorkflowAction{CREATE, APPROVE, DISSAPROVE};
    
    WorkflowAction action;
    EventStateCallback callback;
    EventState eventState;
    
    public WorkflowActionEvent(WorkflowAction action){
        this.action = action;
    }
    
    public WorkflowActionEvent(WorkflowAction action, EventStateCallback callback){
        this.action = action;
        this.callback = callback;
    }
    /**
     * @see com.google.gwt.event.shared.GwtEvent#dispatch(com.google.gwt.event.shared.EventHandler)
     */
    @Override
    protected void dispatch(WorkflowActionHandler handler) {
        handler.doWorkflowAction(this);        
    }

    /**
     * @see com.google.gwt.event.shared.GwtEvent#getAssociatedType()
     */
    @Override
    public com.google.gwt.event.shared.GwtEvent.Type<WorkflowActionHandler> getAssociatedType() {
        return TYPE;
    }

    public WorkflowAction getAction() {
        return action;
    }
   
    public void setEventState(EventState state){
        this.eventState = state;
        if (callback != null){
            callback.onEventComplete(this);
        }
    }

    /**
     * @see org.kuali.student.common.ui.client.mvc.HasEventState#getEventState()
     */
    public EventState getEventState() {
        return this.eventState;
    }
    
}
