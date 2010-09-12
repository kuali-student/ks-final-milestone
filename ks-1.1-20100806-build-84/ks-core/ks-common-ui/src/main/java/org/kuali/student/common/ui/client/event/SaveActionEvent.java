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

package org.kuali.student.common.ui.client.event;

import org.kuali.student.common.ui.client.mvc.HasActionState;

/**
 * A save event with user defined save types. This allows a single widget to handle
 * different types of save events without having to create new events and handlers. 
 * 
 * @author Kuali Student Team
 *
 */
public class SaveActionEvent extends ActionEvent<SaveActionHandler> implements HasActionState{
    public static final Type<SaveActionHandler> TYPE = new Type<SaveActionHandler>();
    
    private ActionState actionState;
    private String message = "Saving";
    private boolean acknowledgeRequired = true;
    private boolean gotoNextView = false;
    
    public SaveActionEvent(){
    }
    
    public SaveActionEvent(boolean gotoNextView){
    	this.gotoNextView = gotoNextView;
    }
    
    public SaveActionEvent(String message){
        this.message = message;
    }    
   
    @Override
    protected void dispatch(SaveActionHandler handler) {
        handler.doSave(this);
    }

    @Override
    public Type<SaveActionHandler> getAssociatedType() {
        return TYPE;
    }
        

    public void setActionState(ActionState state){
        this.actionState = state;
    }
    
    /**
     * @see org.kuali.student.common.ui.client.mvc.HasActionState#getActionState()
     */
    @Override
    public ActionState getActionState() {
        return this.actionState;
    }
    
    public String getMessage(){
        return message;
    }

    public boolean isAcknowledgeRequired() {
        return acknowledgeRequired;
    }

    public void setAcknowledgeRequired(boolean acknowledgeRequired) {
        this.acknowledgeRequired = acknowledgeRequired;
    }
    
    public boolean gotoNextView(){
    	return gotoNextView;
    }
    
}
