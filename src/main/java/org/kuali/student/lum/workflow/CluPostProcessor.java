package org.kuali.student.lum.workflow;
import java.security.InvalidParameterException;
import java.util.List;

import javax.xml.namespace.QName;

import org.kuali.rice.core.resourceloader.GlobalResourceLoader;
import org.kuali.rice.kew.dto.DocumentContentDTO;
import org.kuali.rice.kew.postprocessor.ActionTakenEvent;
import org.kuali.rice.kew.postprocessor.AfterProcessEvent;
import org.kuali.rice.kew.postprocessor.BeforeProcessEvent;
import org.kuali.rice.kew.postprocessor.DeleteEvent;
import org.kuali.rice.kew.postprocessor.DocumentLockingEvent;
import org.kuali.rice.kew.postprocessor.DocumentRouteLevelChange;
import org.kuali.rice.kew.postprocessor.DocumentRouteStatusChange;
import org.kuali.rice.kew.postprocessor.PostProcessor;
import org.kuali.rice.kew.postprocessor.ProcessDocReport;
import org.kuali.rice.kew.service.WorkflowInfo;
import org.kuali.rice.kew.util.KEWConstants;
import org.kuali.student.core.exceptions.DataValidationErrorException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.exceptions.VersionMismatchException;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.service.LuService;
public class CluPostProcessor implements PostProcessor{

	private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger
	.getLogger(CluPostProcessor.class);
	
	public ProcessDocReport afterProcess(AfterProcessEvent arg0)
			throws Exception {
        return new ProcessDocReport(true, "");
	}

	public ProcessDocReport beforeProcess(BeforeProcessEvent arg0)
			throws Exception {
        return new ProcessDocReport(true, "");
	}

	public ProcessDocReport doActionTaken(ActionTakenEvent arg0)
			throws Exception {
        return new ProcessDocReport(true, "");
	}

	public ProcessDocReport doDeleteRouteHeader(DeleteEvent arg0)
			throws Exception {
        return new ProcessDocReport(true, "");
	}

	public ProcessDocReport doRouteLevelChange(DocumentRouteLevelChange arg0)
			throws Exception {
        return new ProcessDocReport(true, "");
	}

	public ProcessDocReport doRouteStatusChange(DocumentRouteStatusChange statusChangeEvent)
			throws Exception {
		if (statusChangeEvent.getNewRouteStatus().equals(KEWConstants.ROUTE_HEADER_APPROVED_CD)) {			
			LOG.info("CluApprovalPostProcessor: Status change to APPROVED");
			WorkflowInfo workflowInfo = new WorkflowInfo();
			DocumentContentDTO document = workflowInfo.getDocumentContent(statusChangeEvent.getRouteHeaderId());
			updateCluStatus(document);						
		}
        return new ProcessDocReport(true, "");
	}

	public List<Long> getDocumentIdsToLock(DocumentLockingEvent arg0)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
    private void updateCluStatus(DocumentContentDTO document) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DataValidationErrorException, org.kuali.student.core.exceptions.InvalidParameterException, VersionMismatchException{
    	LuService luService = (LuService) GlobalResourceLoader.getService(new QName("http://edu.umd.ks/poc/lum/lu","LuService"));
    	String cluId = document.getApplicationContent().replaceAll("<[^>]+>", "");
    	CluInfo clu = luService.getClu(cluId);
    	clu.setState("Approved by Workflow");
		luService.updateClu(cluId, clu);
    }
}
