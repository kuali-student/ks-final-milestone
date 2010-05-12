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

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.apache.log4j.Logger;
import org.kuali.student.common.ui.client.service.DataSaveResult;
import org.kuali.student.common.ui.client.service.exceptions.OperationFailedException;
import org.kuali.student.common.ui.server.gwt.AbstractBaseDataOrchestrationRpcGwtServlet;
import org.kuali.student.core.assembly.data.AssemblyException;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.lum.lu.assembly.ModifyCreditCourseProposalManager;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseHelper;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseProposalHelper;
import org.kuali.student.lum.lu.dto.workflow.CluProposalDocInfo;
import org.kuali.student.lum.lu.ui.course.client.configuration.LUConstants;
import org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService;

public class CreditCourseProposalRpcGwtServlet extends
		AbstractBaseDataOrchestrationRpcGwtServlet implements
		CreditCourseProposalRpcService {

	private static final long serialVersionUID = 1L;
	final static Logger LOG = Logger.getLogger(CreditCourseProposalRpcGwtServlet.class);
    private static final String WF_TYPE_CLU_DOCUMENT = "CluCreditCourseProposal";
	private static final String DEFAULT_METADATA_STATE = "draft";
	private static final String DEFAULT_METADATA_TYPE = null;

	private ModifyCreditCourseProposalManager modifyCourseManager;
	
	@Override
	public DataSaveResult submitDocumentWithData(Data data) throws OperationFailedException{
	    
	    CreditCourseHelper course = null;
	    CreditCourseProposalHelper helper = CreditCourseProposalHelper.wrap(data);
        course = CreditCourseHelper.wrap(helper.getCourse().getData());               
	    course.setState(LUConstants.LU_STATE_SUBMITTED);
        
	    return super.submitDocumentWithData(data);
	}
	
	@Override
	public Data getNewProposalWithCopyOfClu(String dataId) throws OperationFailedException {
		try {
			return modifyCourseManager.getNewProposalWithCopyOfClu(dataId);
		} catch (AssemblyException e) {
			LOG.error("Copy Failed on id:"+dataId, e);
			throw new OperationFailedException("Copy Failed on id:"+dataId,e);
		}
	}
	
    @Override
	protected String deriveAppIdFromData(Data data) {
		CreditCourseProposalHelper cluProposal = CreditCourseProposalHelper.wrap(data);
		if(cluProposal!=null&&cluProposal.getProposal()!=null){
			return cluProposal.getProposal().getId();
		}
		return null;
	}

	@Override
	protected String deriveDocContentFromData(Data data) {
    	try{
    		CreditCourseProposalHelper cluProposal = CreditCourseProposalHelper.wrap(data);
    		
    		CluProposalDocInfo docContent = new CluProposalDocInfo();
    		
    		if(null == cluProposal.getCourse()){
    			throw new OperationFailedException("CluInfo must be set.");
    		}
    		
    		String cluId = cluProposal.getCourse().getId()==null?"":cluProposal.getCourse().getId(); 
    		String adminOrg = cluProposal.getCourse().getDepartment()==null?"":cluProposal.getCourse().getDepartment(); 
    		String proposalId = cluProposal.getProposal().getId()==null?"":cluProposal.getProposal().getId();
    		
    		docContent.setCluId(cluId);
            docContent.setOrgId(adminOrg);
            docContent.setProposalId(proposalId);
            
    		JAXBContext context = JAXBContext.newInstance(docContent.getClass());
    		Marshaller marshaller = context.createMarshaller();
            StringWriter writer = new StringWriter();
    		marshaller.marshal(docContent, writer);
    		return writer.toString();

    	} catch(Exception e) {
    		LOG.error("Error creating Document content for Clu. ", e);
    	}
    	return null;
	}

	@Override
	protected String getDefaultMetaDataState() {
		return DEFAULT_METADATA_STATE;
	}

	@Override
	protected String getDefaultMetaDataType() {
		return DEFAULT_METADATA_TYPE;
	}

	@Override
	protected String getDefaultWorkflowDocumentType() {
		return WF_TYPE_CLU_DOCUMENT;
	}


	@Override
	protected boolean checkDocumentLevelPermissions() {
		return true;
	}

	public ModifyCreditCourseProposalManager getModifyCourseManager() {
		return modifyCourseManager;
	}

	public void setModifyCourseManager(
			ModifyCreditCourseProposalManager modifyCourseManager) {
		this.modifyCourseManager = modifyCourseManager;
	}
}
