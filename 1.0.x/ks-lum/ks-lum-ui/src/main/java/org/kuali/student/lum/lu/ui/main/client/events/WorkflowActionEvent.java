/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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

import org.kuali.student.common.ui.client.event.ActionEvent;
import org.kuali.student.common.ui.client.mvc.ActionCompleteCallback;
import org.kuali.student.common.ui.client.mvc.HasActionState;

/**
 * This class can be used to fire an event to tell controller to perform a 
 * workflow action. 
 * 
 * @author Kuali Student Team
 *
 * TODO: Possibly abstract out handling of event notification callback
 */
public class WorkflowActionEvent extends ActionEvent<WorkflowActionHandler> implements HasActionState{
    public static final Type<WorkflowActionHandler> TYPE = new Type<WorkflowActionHandler>();
    
    public enum WorkflowAction{CREATE, APPROVE, DISSAPROVE};
    
    WorkflowAction action;
    ActionState eventState;
    
    public WorkflowActionEvent(WorkflowAction action){
        this.action = action;
    }
    
    public WorkflowActionEvent(WorkflowAction action, ActionCompleteCallback callback){
        this.action = action;
        setActionCompleteCallback(callback);
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
   
    public void setActionState(ActionState state){
        this.eventState = state;
        if (getActionCompleteCallback() != null){
            getActionCompleteCallback().onActionComplete(this);
        }
    }

    /**
     * @see org.kuali.student.common.ui.client.mvc.HasEventState#getEventState()
     */
    public ActionState getActionState() {
        return this.eventState;
    }
    
}
