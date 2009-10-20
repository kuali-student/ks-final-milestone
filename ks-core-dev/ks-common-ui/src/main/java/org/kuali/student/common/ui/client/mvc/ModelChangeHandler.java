/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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

import org.kuali.student.core.dto.Idable;

import com.google.gwt.event.shared.EventHandler;

/**
 * Handler for ModelChangeEvent
 * 
 * @author Kuali Student Team
 * @param <T>
 */
public interface ModelChangeHandler<T> extends EventHandler {
    /**
     * Invoked when a ModelChangeEvent is dispatched to this handler.
     * 
     * @param event
     *            the event that was fired
     */
    public void onModelChange(ModelChangeEvent<T> event);
}
