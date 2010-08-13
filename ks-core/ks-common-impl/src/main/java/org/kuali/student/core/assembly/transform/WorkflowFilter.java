package org.kuali.student.core.assembly.transform;

import java.io.StringWriter;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.rice.kew.dto.DocumentDetailDTO;
import org.kuali.rice.kew.service.WorkflowUtility;
import org.kuali.rice.kew.util.KEWConstants;
import org.kuali.rice.kew.webservice.DocumentResponse;
import org.kuali.rice.kew.webservice.SimpleDocumentActionsWebService;
import org.kuali.rice.kew.webservice.StandardResponse;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

public class WorkflowFilter extends AbstractDTOFilter {
    
	public static final String WORKFLOW_ACTION		= "WorkflowFilter.Action";
    public static final String WORKFLOW_DOC_TYPE	= "WorkflowFilter.DocumentType";
    public static final String WORKFLOW_USER		= "WorkflowFilter.WorkflowUser";
    
    public static final String WORKFLOW_SUBMIT		= "Submit";
    public static final String WORKFLOW_APPROVE		= "Approve";
    
    final Logger LOG = Logger.getLogger(WorkflowFilter.class);
    
    private WorkflowUtility workflowUtilityService;
	private SimpleDocumentActionsWebService simpleDocService;
	
	private String objectIdPath;
	private Map<String, String> docFieldMap;
	private String docTitlePath;
	private String docType;
	private Class<?> dtoClass;
    
	/**
	 * This intercepts a newly created/updated object and initiates a workflow, or submits/approves 
	 * the workflow document for the object. 
	 */
	@Override
	public void applyOutboundDtoFilter(Object data, Map<String, String> properties) throws Exception {
        if(simpleDocService==null){
        	throw new Exception("Workflow Service is unavailable");
        }
		
		//get a user name
        String username = properties.get(WORKFLOW_USER);
        	        
        //Setting the app id to the id of data object
        String appId = getObjectId(data);

        //Lookup the workflowId from the object id
        DocumentDetailDTO docDetail;
        try {
        	docDetail = workflowUtilityService.getDocumentDetailFromAppId(getDocumentType(), appId);
        } catch (Exception e){
        	docDetail = null;
        }
        	
		if (docDetail == null) {
			//No workflow details found, create a new workflow document
			String docTitle = getDocumentTitle(data);
            docTitle = docTitle==null ? "Unnamed":docTitle;
            
            LOG.info("Creating Workflow Document.");
            DocumentResponse docResponse = simpleDocService.create(username, appId, getDocumentType(), docTitle);
            if (StringUtils.isNotBlank(docResponse.getErrorMessage())) {
            	throw new RuntimeException("Error found creating document: " + docResponse.getErrorMessage());
            }
            
            //Lookup the workflow document detail to see if create was successful
			try {
				docDetail = workflowUtilityService.getDocumentDetailFromAppId(getDocumentType(), appId);
			} catch (Exception e) {
            	throw new RuntimeException("Error found gettting document for newly created object with id " + appId);
			}
		}

        //Generate the document content xml
        String docContent = getDocumentContent(data);
        
        //Save
        StandardResponse stdResp;
        if ( (KEWConstants.ROUTE_HEADER_INITIATED_CD.equals(docDetail.getDocRouteStatus())) ||
        	 (KEWConstants.ROUTE_HEADER_SAVED_CD.equals(docDetail.getDocRouteStatus())) ) {
        	//if the route status is initial, then save initial
        	stdResp = simpleDocService.save(docDetail.getRouteHeaderId().toString(), username, docDetail.getDocTitle(), docContent, "");
        } else {
        	//Otherwise just update the doc content
        	stdResp = simpleDocService.saveDocumentContent(docDetail.getRouteHeaderId().toString(), username, docDetail.getDocTitle(), docContent);
        }

        //Check if there were errors saving
        if(stdResp==null||StringUtils.isNotBlank(stdResp.getErrorMessage())){
        	if(stdResp==null){
        		throw new RuntimeException("Error found updating document");
        	}else{
        		throw new RuntimeException("Error found updating document: " + stdResp.getErrorMessage());
        	}
    	}            						
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

	
	/**
	 * @return The doctype of the workflow document to be associated with this workflow process.
	 */
	public String getDocumentType(){
		return docType;
	}
	

	/**
	 * This method should be implemented to return the id to be used to link the workflow document to
	 * the to a data object. Normally this is simply the id of the data object
	 * 
	 * @param data
	 * @return The object id used to link a workflow document to the application object
	 */
	public String getObjectId(Object dto) throws Exception{
		return getString(dto, objectIdPath);
	}
	

	/**
	 * The title to associate with the workflow process.
	 * 
	 * @param data
	 * @return
	 */
	public String getDocumentTitle(Object dto) throws Exception{
		return getString(dto, docTitlePath);
	}
	
	/**
	 * This method should be implemented to provide the document content required to properly
	 * handle the workflow associated with the object being processed.
	 * 
	 * @param data
	 * @return the document content required by the workflow process
	 */
	public String getDocumentContent(Object dto) throws FilterException {
		String docContentString = "";
		
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			DOMImplementation impl = builder.getDOMImplementation();
			
			//Create the document content
			Document docContent = null;
			docContent = impl.createDocument(null, null, null);
			Element root = docContent.createElement("info");
			docContent.appendChild(root);
			for (Entry<String,String> entry:docFieldMap.entrySet()){
				Element element = docContent.createElement(entry.getKey());
				Text node = docContent.createTextNode(getString(dto,entry.getValue()));
				element.appendChild(node);
				root.appendChild(element);
			}
			
			//Convert document content to string
			DOMSource domSource = new DOMSource(docContent);
	        TransformerFactory tf = TransformerFactory.newInstance();
	        Transformer transformer = tf.newTransformer();
	        StringWriter sw = new StringWriter();
	        StreamResult sr = new StreamResult(sw);
	        transformer.transform(domSource, sr);

	        docContentString = sw.toString();
		} catch (Exception e) {
			LOG.error(e);
			throw new FilterException("Error creating document content",e);
		}
		
		return docContentString;
	}

	private String getString(Object dto, String fieldName) throws Exception{
		String value = "";
		try {
			if (fieldName.contains("[")){
				try {
					value = BeanUtils.getIndexedProperty(dto, fieldName);	
				} catch (IndexOutOfBoundsException e){
					//Just return empty string
				}				
			} else {
				try {
					value = BeanUtils.getProperty(dto, fieldName);
				} catch (NoSuchMethodException nsme){
					//This could be result of Unknown Property error
					//The property could be a dynamic attribute
					String mappedProperty = "attributes(" + fieldName + ")";
					if (BeanUtils.getMappedProperty(dto, mappedProperty) != null){
						value = BeanUtils.getMappedProperty(dto, mappedProperty);
					} else {
						LOG.warn("Property " + fieldName + " has a null dynamic attribute value. Make sure this field is a dynamic attribute.");
					}
				}
			}
		} catch (NullPointerException npe){
			//Just return empty string
		}
		
		return value;
	}

	@Override
	public Class<?> getType() {
		return dtoClass;
	}
	
	public void setObjectIdPath(String objectIdPath) {
		this.objectIdPath = objectIdPath;
	}


	public void setDocFieldPaths(Map<String,String> docFieldMap) {
		this.docFieldMap = docFieldMap;
	}


	public void setDocTitlePath(String docTitlePath) {
		this.docTitlePath = docTitlePath;
	}


	public void setDocType(String docType) {
		this.docType = docType;
	}


	public void setDtoClass(Class<?> dtoClass) {
		this.dtoClass = dtoClass;
	}

}
