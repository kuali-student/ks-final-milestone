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

import com.google.gwt.event.shared.HandlerRegistration;

/**
 * Model object used as a container for the model state. Sources ModelChangeEvents used for tracking the model state.
 * 
 * @author Kuali Student Team
 * @param <T>
 *            the type of model object to be contained within the model
 */
public interface Model {
    /**
     * Adds a ModelChangeHandler that will be invoked for ModelChangeEvents
     * 
     * @param handler
     *            the handler to add
     * @return HandlerRegistration that can be used to unregister the handler later
     */
    public HandlerRegistration addModelChangeHandler(ModelChangeHandler handler);
    
}
