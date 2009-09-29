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
