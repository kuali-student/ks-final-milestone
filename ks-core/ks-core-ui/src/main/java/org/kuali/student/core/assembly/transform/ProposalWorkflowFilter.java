package org.kuali.student.core.assembly.transform;

import java.io.StringWriter;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.rice.kew.dto.DocumentDetailDTO;
import org.kuali.rice.kew.service.WorkflowUtility;
import org.kuali.rice.kew.util.KEWConstants;
import org.kuali.rice.kew.webservice.DocumentResponse;
import org.kuali.rice.kew.webservice.SimpleDocumentActionsWebService;
import org.kuali.rice.kew.webservice.StandardResponse;
import org.kuali.student.common.assembly.data.Data;
import org.kuali.student.common.assembly.data.Metadata;
import org.kuali.student.common.assembly.data.Data.StringKey;
import org.kuali.student.common.assembly.dictionary.MetadataServiceImpl;
import org.kuali.student.common.assembly.transform.AbstractDataFilter;
import org.kuali.student.common.assembly.transform.DataBeanMapper;
import org.kuali.student.common.assembly.transform.DefaultDataBeanMapper;
import org.kuali.student.common.assembly.transform.DocumentTypeConfiguration;
import org.kuali.student.common.assembly.transform.FilterException;
import org.kuali.student.common.assembly.transform.MetadataFilter;
import org.kuali.student.common.assembly.transform.TransformFilter;
import org.kuali.student.common.dto.DtoConstants;
import org.kuali.student.common.util.MessageUtils;
import org.kuali.student.core.proposal.dto.ProposalInfo;
import org.kuali.student.core.proposal.service.ProposalService;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

/**
 * This filter is used to add and process proposal info to data object.
 * 
 * @author Will
 *
 */
public class ProposalWorkflowFilter extends AbstractDataFilter implements MetadataFilter{
	// Filter property keys
	public static final String PROPOSAL_INFO 		= "ProposalWorkflowFilter.ProposalInfo";
	public static final String WORKFLOW_ACTION		= "ProposalWorkflowFilter.Action";
	public static final String WORKFLOW_DOC_ID		= "ProposalWorkflowFilter.DocumentId";
    public static final String WORKFLOW_DOC_TYPE	= "ProposalWorkflowFilter.DocumentType";
    public static final String WORKFLOW_USER		= "ProposalWorkflowFilter.WorkflowUser";
        
	// below string MUST match org.kuali.student.lum.workflow.qualifierresolver.AbstractOrganizationServiceQualifierResolver.DOCUMENT_CONTENT_XML_ROOT_ELEMENT_NAME constant
    public static final String DOCUMENT_CONTENT_XML_ROOT_ELEMENT_NAME	= "info";

    final Logger LOG = Logger.getLogger(ProposalWorkflowFilter.class);
    
    //Services used by this filter
    private WorkflowUtility workflowUtilityService;
	private SimpleDocumentActionsWebService simpleDocService;
	private ProposalService proposalService;
	private MetadataServiceImpl metadataService;
	private final DataBeanMapper mapper = DefaultDataBeanMapper.INSTANCE;
		
	private Metadata proposalMetadata = null;
	private String proposalReferenceType;
	private String defaultDocType;
	
	List<DocumentTypeConfiguration> docTypeConfigs;

	/**
	 *	This removes the proposal data from incoming data and saves it to be used by the outbound filter
	 */
	@Override
	public void applyInboundDataFilter(Data data, Metadata metadata,
			Map<String, Object> properties) throws Exception {
		
		//Get the proposal data portion from the data
		Data proposalData = data.query("proposal");
		data.remove(new StringKey("proposal"));
		ProposalInfo proposalInfo = (ProposalInfo)mapper.convertFromData(proposalData, ProposalInfo.class, getProposalMetadata());
				
		//Create new proposalInfo if no proposal data sent from client
		if (proposalInfo == null) {
			proposalInfo = new ProposalInfo();			
		}
		
		//Set proposal type
		if (proposalInfo.getType() == null){
			String proposalType = (String)properties.get(WORKFLOW_DOC_TYPE);
			if (proposalType == null){
				proposalType = getDefaultDocType();
			}
			proposalInfo.setType(proposalType);
		}			

		properties.put(ProposalWorkflowFilter.WORKFLOW_DOC_TYPE, proposalInfo.getType());
		
		//Place the proposal data in properties map, so outbound filter operation has access to this data.
		properties.put(PROPOSAL_INFO, proposalInfo);		
	}
	
	
	/**
	 *  This creates or updates the proposal and workflow using proposal obtained from the inbound filter
	 */
	@Override
	public void applyOutboundDataFilter(Data data, Metadata metadata,
			Map<String, Object> properties) throws Exception {
		
		//Get proposal associated with this data
		ProposalInfo proposalInfo = (ProposalInfo)properties.get(PROPOSAL_INFO);

		//Update proposal/workflow data for a save request
		if (TransformFilterAction.SAVE == properties.get(TransformFilter.FILTER_ACTION)){
			//If new proposal create proposal 
			if (proposalInfo.getId() == null){
				String docType = proposalInfo.getType();
				DocumentTypeConfiguration docTypeConfig = getDocTypeConfig(docType);
				String referenceId = data.query("id");
				proposalInfo.setProposalReferenceType(getProposalReferenceType());
				proposalInfo.getProposalReference().add(referenceId);
				
                // TODO: this needs to be defined as a constant where all references will resolve
                if ("kuali.proposal.type.course.modify".equals(proposalInfo.getType())) {
                    proposalInfo.setName(getDefaultDocumentTitle(docTypeConfig, data));
                }

				proposalInfo.setState("Saved");
								
				proposalInfo = proposalService.createProposal(proposalInfo.getType(), proposalInfo);			
			} 
				
			//Update the workflow process for this proposal
			proposalInfo = updateWorkflow(proposalInfo, data, properties);
	
			//Update the propsal service with new proposal info
			proposalInfo = proposalService.updateProposal(proposalInfo.getId(), proposalInfo);

			//Place updated info in properties in case other filters wish to make use of it
			properties.put(PROPOSAL_INFO, proposalInfo);
		}	
		
		//Tack on proposal data to data returned to UI client
		Data proposalData = mapper.convertFromBean(proposalInfo);
		data.set("proposal", proposalData);		
	}

	
	/**
	 * Adds proposalInfo metadata to metadata sent to client
	 */
	@Override
	public void applyMetadataFilter(String dtoName, Metadata metadata,
			Map<String, Object> filterProperties) {	
		//String dtoState = (String)filterProperties.get(DtoConstants.DTO_STATE);
		String nextState = (String)filterProperties.get(DtoConstants.DTO_NEXT_STATE);
		Metadata proposalMetadata = metadataService.getMetadata(ProposalInfo.class.getName(), null, "SAVED", nextState);
				
		Map<String, Metadata> properties = metadata.getProperties();
		properties.put("proposal", proposalMetadata);		
	}
	

	/**
	 * Using a newly created/updated proposal and data object and initiates a workflow, or submits/approves 
	 * the workflow document for the object. 
	 */
	public ProposalInfo updateWorkflow(ProposalInfo proposalInfo, Data data, Map<String, Object> properties) throws Exception {
        if(simpleDocService==null){
        	throw new Exception("Workflow Service is unavailable");
        }
		
        //Get the doc type & config info for doc type
        String docType = proposalInfo.getType();       
        DocumentTypeConfiguration docTypeConfig = getDocTypeConfig(docType);

        //get a user name
        String username = (String)properties.get(WORKFLOW_USER);
        	        
        //Setting the app id to proposal id        
        String appId = proposalInfo.getId(); 
        
        //Get the workflow id
        String workflowId = proposalInfo.getWorkflowId();
                
        //Get the document title
        if (proposalInfo.getName() == null){
        	proposalInfo.setName(getDefaultDocumentTitle(docTypeConfig, data));
        }
        String docTitle = proposalInfo.getName(); 
        
        //Get the workflow document or create one if workflow document doesn't exist
        DocumentDetailDTO docDetail;
        if (workflowId != null){
        	docDetail = workflowUtilityService.getDocumentDetail(Long.parseLong(workflowId));
        } else  {
            LOG.info("Creating Workflow Document.");
                        
            DocumentResponse docResponse = simpleDocService.create(username, appId, docType, docTitle);
            if (StringUtils.isNotBlank(docResponse.getErrorMessage())) {
            	throw new RuntimeException("Error found creating document: " + docResponse.getErrorMessage());
            }
            
            workflowId = docResponse.getDocId();
            proposalInfo.setWorkflowId(workflowId);
            
            //Lookup the workflow document detail to see if create was successful
			try {
				docDetail = workflowUtilityService.getDocumentDetail(Long.parseLong(workflowId));
			} catch (Exception e) {
            	throw new RuntimeException("Error found gettting document for newly created object with id " + appId);
			}			
		}

        //Generate the document content xml
        String docContent = getDocumentContent(data, docTypeConfig);
        
        //Save
        StandardResponse stdResp;
        if ( (KEWConstants.ROUTE_HEADER_INITIATED_CD.equals(docDetail.getDocRouteStatus())) ||
        	 (KEWConstants.ROUTE_HEADER_SAVED_CD.equals(docDetail.getDocRouteStatus())) ) {
        	//if the route status is initial, then save initial
        	stdResp = simpleDocService.save(docDetail.getRouteHeaderId().toString(), username, docTitle, docContent, "");
        } else {
        	//Otherwise just update the doc content
        	stdResp = simpleDocService.saveDocumentContent(docDetail.getRouteHeaderId().toString(), username, docTitle, docContent);
        }

        //Check if there were errors saving
        if(stdResp==null||StringUtils.isNotBlank(stdResp.getErrorMessage())){
        	if(stdResp==null){
        		throw new RuntimeException("Error found updating document");
        	}else{
        		throw new RuntimeException("Error found updating document: " + stdResp.getErrorMessage());
        	}
    	}
        
        return proposalInfo;
	}
	
	
	public String getDefaultDocumentTitle(DocumentTypeConfiguration docTypeConfig, Data data){
        String docTitle = docTypeConfig.getDefaultDocumentTitle();
		docTitle = docTitle.replaceAll("\\{", "\\$\\{");
        Set<String> fields = MessageUtils.findFields(docTitle);
        for (String s : fields) {
        	String value = data.query(s);
        	if(data.get(s) != null){
        		docTitle = MessageUtils.interpolate(docTitle, s, value);
        	}
        }
		
		return docTitle;		
	}
	
	/**
	 * This method should be implemented to provide the document content required to properly
	 * handle the workflow associated with the object being processed.
	 * 
	 * @param data
	 * @return the document content required by the workflow process
	 */
	public String getDocumentContent(Data data, DocumentTypeConfiguration docTypeConfig) throws FilterException {
		String docContentString = "";
		
		Map<String, String> docFieldMap = docTypeConfig.getDocContentFieldMap();
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			DOMImplementation impl = builder.getDOMImplementation();
			
			//Create the document content
			Document docContent = null;
			docContent = impl.createDocument(null, null, null);
			Element root = docContent.createElement(DOCUMENT_CONTENT_XML_ROOT_ELEMENT_NAME);
			docContent.appendChild(root);
			for (Entry<String,String> entry:docFieldMap.entrySet()){
				Element element = docContent.createElement(entry.getKey());
				String value = (String)data.query(entry.getValue());
				if (value != null){
					Text node = docContent.createTextNode(value);					
					element.appendChild(node);
					root.appendChild(element);
				}
			}
			
			//Convert document content to string
			DOMSource domSource = new DOMSource(docContent);
	        TransformerFactory tf = TransformerFactory.newInstance();
	        Transformer transformer = tf.newTransformer();
	        StringWriter sw = new StringWriter();
	        StreamResult sr = new StreamResult(sw);
	        transformer.transform(domSource, sr);

	        docContentString = sw.toString();
	        
	        LOG.debug("Generated workflow doc content: " + docContentString);
		} catch (Exception e) {
			LOG.error(e);
			throw new FilterException("Error creating document content",e);
		}
		
		return docContentString;
	}

	/**
	 * This method returns the default metadata (w/o) regard to state. The intent is
	 * to cut down on repeated metadata service calls by having a cached version. This 
	 * metadata is passed into the DataBeanMapper where state does not matter. 
	 * 
	 * @return
	 */
	private Metadata getProposalMetadata(){
		if (proposalMetadata == null){
			proposalMetadata = metadataService.getMetadata(ProposalInfo.class.getName());
		}
		
		return proposalMetadata;
	}
		
	/**
	 * @return The default doctype of the workflow document to be associated with this workflow process.
	 */
	public String getDefaultDocType(){
		return defaultDocType;
	}

	/**
	 * The default workflow document type to create for workflow. The default is used when workflow
	 * document type could not be obtained from the WORKFLOW_DOC_TYPE filter property.
	 * 
	 * @param docType
	 */
	public void setDefaultDocType(String docType) {
		this.defaultDocType = docType;
	}
	
	public String getProposalReferenceType() {
		return proposalReferenceType;
	}

	public void setProposalReferenceType(String proposalReferenceType) {
		this.proposalReferenceType = proposalReferenceType;
	}
	
	/**
	 * 
	 * @param docType
	 * @return doc type config for the docType
	 */
	public DocumentTypeConfiguration getDocTypeConfig(String docType){
		for (DocumentTypeConfiguration docTypeConfig:docTypeConfigs){
			if (docTypeConfig.getDocumentType().equals(docType)){
				return docTypeConfig;
			}
		}
		return null;
	}
	
	public List<DocumentTypeConfiguration> getDocTypeConfigs() {
		return docTypeConfigs;
	}

	public void setDocTypeConfigs(List<DocumentTypeConfiguration> docTypeConfigs) {
		this.docTypeConfigs = docTypeConfigs;
	}
	

	/**
	 * Used to set the workflow utility service required by this filter
	 * 
	 * @param workflowUtilityService
	 */
	public void setWorkflowUtilityService(WorkflowUtility workflowUtilityService) {
		this.workflowUtilityService = workflowUtilityService;
	}
	
	/**
	 * Used to set the simple doc service required by this filter
	 * @param simpleDocService
	 */
	public void setSimpleDocService(SimpleDocumentActionsWebService simpleDocService) {
		this.simpleDocService = simpleDocService;
	}

	public void setProposalService(ProposalService proposalService) {
		this.proposalService = proposalService;
	}

	public void setMetadataService(MetadataServiceImpl metadataService) {
		this.metadataService = metadataService;
    }
}
