package edu.umd.ks.poc.util;
import javax.xml.namespace.QName;

import org.kuali.rice.core.resourceloader.GlobalResourceLoader;
import org.kuali.rice.kew.dto.DocumentContentDTO;
import org.kuali.rice.kew.postprocessor.ActionTakenEvent;
import org.kuali.rice.kew.postprocessor.AfterProcessEvent;
import org.kuali.rice.kew.postprocessor.BeforeProcessEvent;
import org.kuali.rice.kew.postprocessor.DeleteEvent;
import org.kuali.rice.kew.postprocessor.DocumentRouteLevelChange;
import org.kuali.rice.kew.postprocessor.DocumentRouteStatusChange;
import org.kuali.rice.kew.postprocessor.PostProcessor;
import org.kuali.rice.kew.postprocessor.ProcessDocReport;
import org.kuali.rice.kew.service.WorkflowInfo;
import org.kuali.rice.kew.util.KEWConstants;
import org.kuali.student.poc.common.ws.exceptions.DoesNotExistException;
import org.kuali.student.poc.common.ws.exceptions.InvalidParameterException;
import org.kuali.student.poc.common.ws.exceptions.MissingParameterException;
import org.kuali.student.poc.common.ws.exceptions.OperationFailedException;
import org.kuali.student.poc.common.ws.exceptions.PermissionDeniedException;

import edu.umd.ks.poc.lum.lu.dto.CluInfo;
import edu.umd.ks.poc.lum.lu.dto.CluUpdateInfo;
import edu.umd.ks.poc.lum.lu.service.LuService;

public class CluApprovalPostProcessor implements PostProcessor{
	
	private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger
	.getLogger(CluApprovalPostProcessor.class);
	
    public ProcessDocReport doRouteStatusChange(DocumentRouteStatusChange statusChangeEvent) throws Exception {
		if (statusChangeEvent.getNewRouteStatus().equals(KEWConstants.ROUTE_HEADER_APPROVED_CD)) {			
			LOG.info("CluApprovalPostProcessor: Status change to APPROVED");
			WorkflowInfo workflowInfo = new WorkflowInfo();
			DocumentContentDTO document = workflowInfo.getDocumentContent(statusChangeEvent.getRouteHeaderId());
			updateCluStatus(document);						
		}
        return new ProcessDocReport(true, "");
    }

    public ProcessDocReport doRouteLevelChange(DocumentRouteLevelChange levelChangeEvent) throws Exception {
        return new ProcessDocReport(true, "");
    }

    public ProcessDocReport doDeleteRouteHeader(DeleteEvent event) throws Exception {
        return new ProcessDocReport(false, "");
    }

    public ProcessDocReport doActionTaken(ActionTakenEvent event) throws Exception {
        return new ProcessDocReport(true, "");
    }

    public ProcessDocReport beforeProcess(BeforeProcessEvent event) throws Exception {
        return new ProcessDocReport(true, "");
    }

    public ProcessDocReport afterProcess(AfterProcessEvent event) throws Exception {
        return new ProcessDocReport(true, "");
    }
    
    private void updateCluStatus(DocumentContentDTO document) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException{
    	LuService luService = (LuService) GlobalResourceLoader.getService(new QName("http://edu.umd.ks/poc/lum/lu","LuService"));
    	String cluId = document.getApplicationContent().replaceAll("<[^>]+>", "");
    	CluInfo clu = luService.fetchClu(cluId);
    	CluUpdateInfo cluUpdateInfo = new CluUpdateInfo();
		cluUpdateInfo.setCluShortName(clu.getCluShortName());
		cluUpdateInfo.setDescription(clu.getDescription());
		cluUpdateInfo.setEffectiveEndCycle(clu.getEffectiveEndCycle());
		cluUpdateInfo.setEffectiveStartCycle(clu.getEffectiveStartCycle());
		cluUpdateInfo.setEffectiveEndDate(clu.getEffectiveEndDate());
		cluUpdateInfo.setEffectiveStartDate(clu.getEffectiveStartDate());
		cluUpdateInfo.setAdminDepartment(clu.getAdminDepartment());
		cluUpdateInfo.getAttributes().putAll(clu.getAttributes());
		
		cluUpdateInfo.setStatus("FApproved by Workflow");
		luService.updateClu(cluId, cluUpdateInfo);
    }
}
