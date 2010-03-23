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
    
	private PermissionService permissionService;

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
	
	public PermissionService getPermissionService() {
        return permissionService;
    }

    public void setPermissionService(PermissionService permissionService) {
        this.permissionService = permissionService;
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
