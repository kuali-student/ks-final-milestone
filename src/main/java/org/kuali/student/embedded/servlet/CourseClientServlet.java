package org.kuali.student.embedded.servlet;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.kuali.student.embedded.beans.CourseParams;
import org.kuali.student.embedded.service.CourseClientService;
import org.kuali.student.embedded.service.impl.CourseClientServiceImpl;
import org.kuali.student.embedded.workflow.CoursePostProcessor;
import org.kuali.student.poc.xsd.learningunit.lu.dto.CluCreateInfo;

import org.kuali.rice.kew.util.KEWConstants;
//import edu.iu.uis.eden.batch.KEWXmlDataLoader;
import org.kuali.rice.kew.service.WorkflowDocument;
import org.kuali.rice.kew.dto.NetworkIdDTO;

public class CourseClientServlet extends HttpServlet {

    private CourseClientService courseClientService = new CourseClientServiceImpl();

    private static final long serialVersionUID = -3491548161476043591L;

    private static final String CREATE_ACTION = "create";
    private static final String LOAD_ACTION = "load";
    private static final String REROUTE_ACTION = "reroute";
    private static final String APPROVE_ACTION = "approve";
    private static final String ACK_ACTION = "ack";
    private static final String DISAPPROVE_ACTION = "disapprove";

    private static final String USERNAME_PARAM = "userName";
    private static final String DOCUMENT_ID_PARAM = "documentId";
    
	private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger
	.getLogger(CourseClientServlet.class);
    

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // parse parameters and set up attributes
        String userName = request.getParameter(USERNAME_PARAM);
        String routeToUser = request.getParameter("routeToUser");

        Long documentId = null;
        if (!StringUtils.isBlank(request.getParameter(DOCUMENT_ID_PARAM))) {
            documentId = new Long(request.getParameter(DOCUMENT_ID_PARAM));
        } else if (!StringUtils.isBlank(request.getParameter("docId"))) {
            documentId = new Long(request.getParameter("docId"));
        }

        request.setAttribute(USERNAME_PARAM, userName);
        request.setAttribute(DOCUMENT_ID_PARAM, documentId);

        //Ad-HOC Routing
        if (!StringUtils.isBlank(routeToUser)) {
        	try {
                WorkflowDocument document = new WorkflowDocument(new NetworkIdDTO(userName), Long.valueOf(documentId));
            	document.appSpecificRouteDocumentToUser(KEWConstants.ACTION_REQUEST_APPROVE_REQ, "Ad-Hoc Routing to " + routeToUser, new NetworkIdDTO(routeToUser), "", true);				
			} catch (Exception e) {
				// TODO: handle exception
			}
		}		      
        
        // process actions

        if (isAction(request, CREATE_ACTION)) {

            CluCreateInfo clu = new CluCreateInfo();
            
            clu.setCluShortName(request.getParameter("course"));
            clu.setCluLongName(request.getParameter("name"));
            clu.setDescription(request.getParameter("description"));
            clu.setEffectiveStartCycle("11223344-1122-1122-1111-000000000001");
            clu.setEffectiveEndCycle("11223344-1122-1122-1111-000000000002");
    		clu.getAttributes().put("Grading Method", request.getParameter("gradeMethod"));
    		clu.getAttributes().put("Credits", request.getParameter("credits"));	
    		clu.getAttributes().put("Level", request.getParameter("level"));

            documentId = Long.valueOf(courseClientService.createCourse(clu, userName, request.getParameter("docType")));

            request.setAttribute("documentId", documentId);
            RequestDispatcher dispatcher = request.getRequestDispatcher("created.jsp");
            dispatcher.forward(request, response);
            return;

        } else if (isAction(request, LOAD_ACTION)) {

            CourseParams cp = courseClientService.loadCourse(userName, documentId.toString());
            request.setAttribute("acknowledgeRequested", cp.isAcknowledgeRequested());
            request.setAttribute("approvalRequested", cp.isApprovalRequested());

            CluCreateInfo clu = cp.getClu();
            request.setAttribute("course", clu.getCluShortName());
            request.setAttribute("name", clu.getCluLongName());
            request.setAttribute("description", clu.getDescription());
            request.setAttribute("startDate", clu.getEffectiveEndCycle());
            request.setAttribute("endDate", clu.getEffectiveStartCycle());
            request.setAttribute("gradeMethod", clu.getAttributes().get("Grading Method"));
            request.setAttribute("credits", clu.getAttributes().get("Credits"));

            try {
	            WorkflowDocument document = new WorkflowDocument(new NetworkIdDTO(userName), Long.valueOf(documentId));
	            request.setAttribute("document", document);
	            
	            String[] previousNodes = document.getPreviousNodeNames();
	            LOG.info(previousNodes);
	            request.setAttribute("previousNodes", previousNodes);
            } catch (Exception e) {
            	e.printStackTrace();
			}
	            
            RequestDispatcher dispatcher = request.getRequestDispatcher("loaded.jsp");
            dispatcher.forward(request, response);
            
            
            return;
        } else if (isAction(request, REROUTE_ACTION)) {
            String previousNode = request.getParameter("previousNode");
            String message = request.getParameter("message");
        	
        	try {	            
        		WorkflowDocument document = new WorkflowDocument(new NetworkIdDTO(userName), Long.valueOf(documentId));
        		LOG.info("Returning to: " + previousNode);
	            document.returnToPreviousNode(message, previousNode);	            
            } catch (Exception e) {
            	e.printStackTrace();
			}
	                    
            RequestDispatcher dispatcher = request.getRequestDispatcher("rerouted.jsp");
            dispatcher.forward(request, response);     	
        	
        } else if (isAction(request, APPROVE_ACTION)) {
            courseClientService.approveCourse(userName, documentId.toString());
            RequestDispatcher dispatcher = request.getRequestDispatcher("actionsubmitted.jsp");
            dispatcher.forward(request, response);
            return;
        } else if (isAction(request, ACK_ACTION)) {
            courseClientService.acknowledgeCourse(userName, documentId.toString());
            RequestDispatcher dispatcher = request.getRequestDispatcher("actionsubmitted.jsp");
            dispatcher.forward(request, response);
            return;
        } else if (isAction(request, DISAPPROVE_ACTION)) {
            courseClientService.disapproveCourse(userName, documentId.toString());
            RequestDispatcher dispatcher = request.getRequestDispatcher("actionsubmitted.jsp");
            dispatcher.forward(request, response);
            return;
        } else if (isAction(request, "Ingest")){
    		String[] data = {"classpath:RiceBootstrapData_0.9.3-M1.xml", "classpath:users.xml", "classpath:course-doctype-full.xml"};
    		for (String s : data) {
    			try {
    				//KEWXmlDataLoader.loadXmlResource(s);	
    			} catch (Exception e) {
    				e.printStackTrace(System.out);
    			}		
    		} 

            RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
            dispatcher.forward(request, response);     	
        }

        super.doPost(request, response);

    }

    private boolean isAction(HttpServletRequest req, String actionName) {
        return !StringUtils.isBlank(req.getParameter(actionName));
    }


}
