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

package org.kuali.student.lum.lu.ui.tools.server.gwt;

import org.apache.log4j.Logger;
import org.kuali.rice.kim.service.PermissionService;
import org.kuali.student.common.ui.server.gwt.AbstractBaseDataOrchestrationRpcGwtServlet;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.lum.lu.ui.tools.client.service.CluSetManagementRpcService;

public class CluSetManagementRpcGwtServlet extends
		AbstractBaseDataOrchestrationRpcGwtServlet implements
		CluSetManagementRpcService {

	private static final long serialVersionUID = 1L;
	final Logger LOG = Logger.getLogger(CluSetManagementRpcGwtServlet.class);
    private static final String WF_TYPE_CLU_DOCUMENT = "CluCreditCourseProposal";
	private static final String DEFAULT_METADATA_STATE = "draft";
	private static final String DEFAULT_METADATA_TYPE = null;
    
	@Override
	protected String getDefaultMetaDataState() {
		return null; //DEFAULT_METADATA_STATE;
	}

	@Override
	protected String getDefaultMetaDataType() {
		return null; //DEFAULT_METADATA_TYPE;
	}

	@Override
	protected String getDefaultWorkflowDocumentType() {
		return null; //WF_TYPE_CLU_DOCUMENT;
	}
	
	@Override
	protected String deriveAppIdFromData(Data data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String deriveDocContentFromData(Data data) {
		// TODO Auto-generated method stub
		return null;
	}
}
