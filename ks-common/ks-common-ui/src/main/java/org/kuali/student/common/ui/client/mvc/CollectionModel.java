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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.kuali.student.common.ui.client.mvc.ModelChangeEvent.Action;
import org.kuali.student.r1.common.dto.Idable;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;

/**
 * Model object used as a container for the model state. Sources ModelChangeEvents used for tracking the model state.
 * 
 * @author Kuali Student Team
 * @param <T>
 *            the type of model object to be contained within the model
 */
@Deprecated
public class CollectionModel<T> implements Model {
    private Map<String, T> data = new HashMap<String, T>();
    private HandlerManager handlers = new HandlerManager(this);
    
    private T value = null;
    /**
     * Adds an object to the model, and fires a ModelChangeEvent
     * @param object
     */
    public void add(T object) {
        if (object instanceof Idable){
            data.put(((Idable)object).getId(), object);
            handlers.fireEvent(new CollectionModelChangeEvent<T>(Action.ADD, this, object));
        }
    }

	/**
	 * Removes the specified model object from the model
	 * 
	 * @param object
	 * @return the object that was removed, or null if not found
	 * @deprecated should use new Data structures instead, accessed via getValue, setValue  
	 */
    public T remove(T object) {
	    if (object instanceof Idable){
	        T result = data.remove(((Idable)object).getId());
	        if (result != null) {
	            handlers.fireEvent(new CollectionModelChangeEvent<T>(Action.REMOVE, this, result));
	        }
	        return result;
	    } else {
	        return null;
	    }
    }
    
    /**
     * Adds a ModelChangeHandler that will be invoked for ModelChangeEvents
     * 
     * @param handler
     *            the handler to add
     * @return HandlerRegistration that can be used to unregister the handler later
     */
    @Override
    public HandlerRegistration addModelChangeHandler(ModelChangeHandler handler) {
        return handlers.addHandler(ModelChangeEvent.TYPE, handler);
    }

    /**
     * Returns an unsorted, umodifiable collection of the values contained within the model.
     * 
     * @return an unsorted, umodifiable collection of the values contained within the model.
     */
    public Collection<T> getValues() {
        return Collections.unmodifiableList(new ArrayList<T>(data.values()));
    }

	
    /**
     * Returns the Model's value
     * Going forward, the Model class should primarily be used with a single DataModel instance for use with the configurable UI framework.
     * @return the Model's value
     */
    public T getValue() {
		return value;
	}

    /**
     * Sets the Model's value
     * Going forward, the Model class should primarily be used with a single DataModel instance for use with the configurable UI framework.
     * @param value the new value
	 */
	public void setValue(T value) {
		this.value = value;
		handlers.fireEvent(new CollectionModelChangeEvent<T>(Action.RELOAD, this, value));
	}
    
    
    
}
