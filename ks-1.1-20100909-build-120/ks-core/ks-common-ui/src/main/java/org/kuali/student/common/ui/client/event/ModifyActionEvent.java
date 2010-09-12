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

public class ModifyActionEvent extends ActionEvent<ModifyActionHandler> implements HasActionState{
    public static final Type<ModifyActionHandler> TYPE = new Type<ModifyActionHandler>();
    
    private ActionState actionState;
    private String message = "Fetching";
    private boolean acknowledgeRequired = true;
    private String id;
    
    public ModifyActionEvent(){
    }
    
    public ModifyActionEvent(String id){
        this();
        this.id = id;
    }
    
    @Override
    protected void dispatch(ModifyActionHandler handler) {
        handler.onModify(this);
        
    }

    @Override
    public com.google.gwt.event.shared.GwtEvent.Type<ModifyActionHandler> getAssociatedType() {

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
