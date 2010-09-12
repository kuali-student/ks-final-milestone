package org.kuali.student.lum.lu.ui.course.server.gwt;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.apache.log4j.Logger;
import org.kuali.student.common.ui.client.service.exceptions.OperationFailedException;
import org.kuali.student.core.assembly.transform.FilterException;
import org.kuali.student.core.assembly.transform.WorkflowFilter;
import org.kuali.student.lum.course.dto.CourseInfo;
import org.kuali.student.lum.lu.dto.workflow.CluProposalDocInfo;

public class CourseWorkflowFilter extends WorkflowFilter{

	public static final String WF_TYPE_CLU_DOCUMENT = "CluCreditCourseProposal";
	
	final Logger LOG = Logger.getLogger(CourseWorkflowFilter.class);
	
	@Override
	public Class<?> getType() {
		return CourseInfo.class;
	}

	@Override
	public String getDocumentType() {
		return WF_TYPE_CLU_DOCUMENT;
	}

	@Override
	public String getObjectId(Object dto) {
		CourseInfo course = (CourseInfo)dto;
		String proposalId = course.getAttributes().get("proposalId");
		return proposalId;//course.getId();
	}

	@Override
	public String getDocumentTitle(Object dto) {
		CourseInfo course = (CourseInfo)dto;
		return course.getCourseTitle();
	}

	@Override
	public String getDocumentContent(Object dto) throws FilterException{		
    	try{
    		if(null == dto){
    			throw new OperationFailedException("CluInfo must be set.");
    		}

    		CluProposalDocInfo docContent = new CluProposalDocInfo();
    		CourseInfo course = (CourseInfo)dto;
    		
    		
    		String cluId = course.getId()==null? "":course.getId(); 
    		// Administering Orgs is a list in Course. But this change has not been made
    		// on the workflow side and its associated doc content. For now we are attaching the first 
    		// element of the administering orgs to adminOrg    		
    		String adminOrg = "";
    		if (course.getCurriculumOversightOrgs() != null && !course.getCurriculumOversightOrgs().isEmpty()){
    			adminOrg = course.getCurriculumOversightOrgs().get(0)==null? "":course.getCurriculumOversightOrgs().get(0);
    		}
    		
    		String proposalId = course.getAttributes().get("proposalId");    		
    		proposalId = proposalId==null?"":proposalId;
    		
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
    		throw new FilterException("Error creating document content for Course",e);
    	}		
	}

}
