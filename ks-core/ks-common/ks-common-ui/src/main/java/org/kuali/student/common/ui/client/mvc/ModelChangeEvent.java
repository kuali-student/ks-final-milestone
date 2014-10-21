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

package org.kuali.student.common.ui.client.mvc;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Event that is fired when the model is changed.
 * 
 * @author Kuali Student Team
 * @param <T>
 */
public class ModelChangeEvent extends GwtEvent<ModelChangeHandler> {
    public static final Type<ModelChangeHandler> TYPE = new Type<ModelChangeHandler>();

    /**
     * The actions that can be performed on a model.
     * 
     * @author Kuali Student Team
     */
    public enum Action {
        ADD, REMOVE, UPDATE, RELOAD
    }

    private final Action action;
    private final Model source;
    
    /**
     * Constructs a new ModelChangeEvent with an action and a value
     * 
     * @param action
     * @param value
     */
    public ModelChangeEvent(Action action, Model source) {
        this.action = action;
        this.source = source;
    }

    @Override
    protected void dispatch(ModelChangeHandler handler) {
        handler.onModelChange(this);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Type<ModelChangeHandler> getAssociatedType() {
        return (Type) TYPE;
    }

    /**
     * Returns the action (ADD/UPDATE/REMOVE) associated with the event
     * 
     * @return
     */
    public Action getAction() {
        return this.action;
    }

    /**
     * Returns the model from which this event originated
     * 
     * @return the model from which this event originated
     */
    public Model getSource() {
    	return this.source;
    }
}
