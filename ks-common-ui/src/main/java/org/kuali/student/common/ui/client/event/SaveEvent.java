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
package org.kuali.student.common.ui.client.event;

import org.kuali.student.common.ui.client.mvc.ApplicationEvent;
import org.kuali.student.common.ui.client.mvc.EventStateCallback;
import org.kuali.student.common.ui.client.mvc.HasEventState;

/**
 * A save event with user defined save types. This allows a single widget to handle
 * different types of save events without having to create new events and handlers. 
 * 
 * @author Kuali Student Team
 *
 */
public class SaveEvent<SaveType extends Enum<?>> extends ApplicationEvent<SaveHandler> implements HasEventState{
    public static final Type<SaveHandler> TYPE = new Type<SaveHandler>();
    
    private SaveType saveType;
    private EventStateCallback callback;
    private EventState eventState;
    
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

    public EventStateCallback getCallback() {
        return callback;
    }

    public void setCallback(EventStateCallback callback) {
        this.callback = callback;
    }

    public void setEventState(EventState state){
        this.eventState = state;
    }
    
    /**
     * @see org.kuali.student.common.ui.client.mvc.HasEventState#getEventState()
     */
    @Override
    public EventState getEventState() {
        return this.eventState;
    }

    
}
