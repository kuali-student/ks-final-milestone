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

import java.util.List;

import org.kuali.student.common.ui.client.mvc.ApplicationEvent;
import org.kuali.student.common.ui.client.mvc.EventStateCallback;
import org.kuali.student.common.ui.client.mvc.HasEventState;

/**
 * A load event is fired to reload the model 
 * 
 * @author Kuali Student Team
 *
 */
public class LoadModelEvent extends ApplicationEvent<LoadModelHandler> implements HasEventState{
    public static final Type<LoadModelHandler> TYPE = new Type<LoadModelHandler>();
    
    private EventStateCallback callback;
    private EventState eventState;
    
    List<String> modelIdList;
    
    public LoadModelEvent(){
    }
        
    @Override
    protected void dispatch(LoadModelHandler handler) {
        handler.loadModel(this);
    }

    @Override
    public Type<LoadModelHandler> getAssociatedType() {
        return TYPE;
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

    public List<String> getModelIds(){
        return modelIdList;
    }
    
    /**
     * This will return the first item in a model
     * 
     * @return
     */
    public String getModelId(){
        return null;
    }
}
