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


/**
 * Event that is fired when the model is changed.
 * 
 * @author Kuali Student Team
 * @param <T>
 */
public class CollectionModelChangeEvent<T> extends ModelChangeEvent {
    private final T value;
    
    /**
     * Constructs a new ModelChangeEvent with an action and a value
     * 
     * @param action
     * @param value
     */
    public CollectionModelChangeEvent(Action action, Model source, T value) {
        super(action, source);
        this.value = value;
    }
    

    /**
     * Returns the object with which the event is associated
     * 
     * @return
     */
    public T getValue() {
        return this.value;
    }
}
