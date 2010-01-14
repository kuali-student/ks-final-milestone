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
package org.kuali.student.common.ui.server.applicationstate;

import org.kuali.student.common.ui.client.mvc.dto.ModelDTO;
import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.DoesNotExistException;

public interface ApplicationStateManager {

	/**
	 * Creates or updates a <code>model</code> application state.
	 * 
	 * @param model ModelDTO
	 * @throws Thrown if creating an application state already exists
	 */
	public void createOrUpdateApplicationState(ModelDTO model) throws AlreadyExistsException;

	/**
	 * Gets a <code>model</code> application state
	 * @param model
	 * @return
	 * @throws DoesNotExistException Thrown if application state does not exists
	 */
    public ModelDTO getApplicationState(ModelDTO model) throws DoesNotExistException; 
}
