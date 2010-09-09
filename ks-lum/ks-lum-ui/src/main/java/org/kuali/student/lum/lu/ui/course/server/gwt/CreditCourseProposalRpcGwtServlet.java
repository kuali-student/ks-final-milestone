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

package org.kuali.student.lum.lu.ui.course.server.gwt;

import org.apache.log4j.Logger;
import org.kuali.student.common.ui.client.service.exceptions.OperationFailedException;
import org.kuali.student.common.ui.server.gwt.DataGwtServlet;
import org.kuali.student.core.assembly.data.AssemblyException;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.lum.lu.assembly.ModifyCreditCourseProposalManager;
import org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService;

public class CreditCourseProposalRpcGwtServlet extends DataGwtServlet implements
		CreditCourseProposalRpcService {

	final static Logger LOG = Logger.getLogger(CreditCourseProposalRpcGwtServlet.class);
	
	private static final long serialVersionUID = 1L;
	ModifyCreditCourseProposalManager modifyCourseManager;

	@Override
	public Data getNewProposalWithCopyOfClu(String cluId)
			throws OperationFailedException {
		try {
			return modifyCourseManager.getNewProposalWithCopyOfClu(cluId);
		} catch (AssemblyException e) {
			LOG.error("Copy Failed on id:"+cluId, e);
			throw new OperationFailedException("Copy Failed on id:"+cluId,e);
		}
	}

	public void setModifyCourseManager(
			ModifyCreditCourseProposalManager modifyCourseManager) {
		this.modifyCourseManager = modifyCourseManager;
	}

}
