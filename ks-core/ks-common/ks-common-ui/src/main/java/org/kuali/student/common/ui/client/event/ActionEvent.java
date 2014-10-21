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

import org.kuali.student.common.ui.client.mvc.ActionCompleteCallback;
import org.kuali.student.common.ui.client.mvc.ApplicationEvent;
import org.kuali.student.common.ui.client.mvc.ApplicationEventHandler;

/**
 * This is an abstract action event, which provides for getting/setting of
 * an ActionCompleteCallback for any custom defined actions.  ActionEvents are
 * used to perform some action and have a action complete call back to be invoked
 * when the action is completed so additional steps can be take when action has
 * completed. The standard way of doing this is as follows:
 * 
 *  1. Create an specific action event (eg. SaveActionEvent)
 *  2. Register an ActionEventHandler (eg. SaveActionEventHandler) to perform some action (eg. save data)
 *  3. Fire the action event with an ActionCompleteCall back to do some additional processing (eg. close save dialog or notify user)
 * 
 * @author Kuali Student Team
 *
 */
public abstract class ActionEvent<H extends ApplicationEventHandler> extends ApplicationEvent<H>{
    private ActionCompleteCallback actionCompleteCallback;

    public ActionCompleteCallback getActionCompleteCallback() {
        return actionCompleteCallback;
    }

    public void setActionCompleteCallback(ActionCompleteCallback actionCompleteCallback) {
        this.actionCompleteCallback = actionCompleteCallback;
    }
    
    public void doActionComplete(){
        if (actionCompleteCallback != null){
            actionCompleteCallback.onActionComplete(this);
        }
    }
        
}
