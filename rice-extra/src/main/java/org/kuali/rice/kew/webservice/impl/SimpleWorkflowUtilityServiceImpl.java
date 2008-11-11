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
package org.kuali.rice.kew.webservice.impl;

import org.kuali.rice.kew.dto.ActionItemDTO;
import org.kuali.rice.kew.dto.DocumentContentDTO;
import org.kuali.rice.kew.dto.UuIdDTO;
import org.kuali.rice.kew.exception.WorkflowException;
import org.kuali.rice.kew.service.KEWServiceLocator;
import org.kuali.rice.kew.service.WorkflowUtility;
import org.kuali.rice.kew.webservice.SimpleWorkflowUtilityService;

/**
 * This is a description of what this class does - Will Gomes don't forget to fill this in. 
 * 
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 *
 */
public class SimpleWorkflowUtilityServiceImpl implements
		SimpleWorkflowUtilityService {

	/**
	 * This overridden method ...
	 * 
	 * @see org.kuali.rice.kew.webservice.SimpleWorkflowUtilityService#getDocumentContent(java.lang.Long)
	 */
	public DocumentContentDTO getDocumentContent(Long routeHeaderId)
			 {
		WorkflowUtility wfService = KEWServiceLocator.getWorkflowUtilityService();
		
		try {
			return wfService.getDocumentContent(routeHeaderId);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * This overridden method ...
	 * 
	 * @see org.kuali.rice.kew.webservice.SimpleWorkflowUtilityService#getActionItemsForUser(java.lang.String)
	 */
	public ActionItemDTO[] getActionItemsForUser(String userId){
		UuIdDTO uuIdDTO = new UuIdDTO();
		uuIdDTO.setUuId(userId);

		WorkflowUtility wfService = KEWServiceLocator.getWorkflowUtilityService();
			
		try{ 
			return wfService.getActionItemsForUser(uuIdDTO);
		} catch (Exception e) {
			return new ActionItemDTO[]{};
		}
	}

}
