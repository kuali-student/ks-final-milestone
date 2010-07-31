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

import org.kuali.student.common.ui.client.mvc.ApplicationEvent;

/**
 * A save event with user defined save types. This allows a single widget to handle
 * different types of save events without having to create new events and handlers. 
 * 
 * @author Kuali Student Team
 *
 */
public class SaveEvent<SaveType extends Enum<?>> extends ApplicationEvent<SaveHandler>{
    public static final Type<SaveHandler> TYPE = new Type<SaveHandler>();
    
    private SaveType saveType;
    
    public SaveEvent(){
        
    }
    
    public SaveEvent(SaveType saveType){
        this.saveType = saveType;
    }
    
    @Override
    protected void dispatch(SaveHandler handler) {
        handler.onSave(this);
    }

    @Override
    public Type<SaveHandler> getAssociatedType() {
        return TYPE;
    }
    
    public SaveType getSaveType(){
        return this.saveType;
    }
    
}
