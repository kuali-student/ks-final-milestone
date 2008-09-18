package org.kuali.student.embedded.service.impl;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang.StringUtils;
import org.kuali.student.embedded.beans.CourseParams;
import org.kuali.student.embedded.service.CourseClientService;
import org.kuali.student.poc.wsdl.learningunit.lu.jaxws.CreateClu;
import org.kuali.student.poc.xsd.learningunit.lu.dto.CluCreateInfo;

import org.kuali.rice.kew.util.KEWConstants;
import org.kuali.rice.kew.service.WorkflowDocument;
import org.kuali.rice.kew.dto.NetworkIdDTO;
import org.kuali.rice.kew.exception.WorkflowException;

public class CourseClientServiceImpl implements CourseClientService {
    private static final String DOC_TYPE = "CourseDocument";

    public boolean acknowledgeCourse(String userName, String documentId) {
        WorkflowDocument document;
        try {
            document = new WorkflowDocument(new NetworkIdDTO(userName), Long.valueOf(documentId));
            document.acknowledge("");
        } catch (WorkflowException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean approveCourse(String userName, String documentId) {
        WorkflowDocument document;
        try {
            document = new WorkflowDocument(new NetworkIdDTO(userName), Long.valueOf(documentId));
            document.approve("");
        } catch (WorkflowException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public String createCourse(CluCreateInfo clu, String userName, String docType) {
        WorkflowDocument document;
        String documentId = null;
        try {
            document = new WorkflowDocument(new NetworkIdDTO(userName), docType);
            CreateClu cluMsg = new CreateClu();
            cluMsg.setCluCreateInfo(clu);

            StringWriter sw = new StringWriter();

            try {
                JAXBContext jc = JAXBContext.newInstance(CreateClu.class);
                Marshaller marshaller = jc.createMarshaller();
                marshaller.marshal(cluMsg, sw);
            } catch (JAXBException jaxbe) {
                throw new RuntimeException(jaxbe);
            }

            document.setApplicationContent(sw.toString());
/*
            if (!StringUtils.isBlank(routeToUser)) {
				document.appSpecificRouteDocumentToUser(EdenConstants.ACTION_REQUEST_APPROVE_REQ, "Ad-Hoc Routing to " + routeToUser, new NetworkIdDTO(routeToUser), "", true);
			}
*/			

            document.routeDocument("Document Routed from the Course Client");
            documentId = document.getRouteHeaderId().toString();

        } catch (WorkflowException e) {
            e.printStackTrace();

        }
        return documentId;
    }

    public boolean disapproveCourse(String userName, String documentId) {
        WorkflowDocument document;
        try {
            document = new WorkflowDocument(new NetworkIdDTO(userName), Long.valueOf(documentId));
            document.disapprove("");
        } catch (WorkflowException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public CourseParams loadCourse(String userName, String documentId) {
        WorkflowDocument document = null;
        CluCreateInfo clu = null;
        CourseParams cp = new CourseParams();

        try {
            document = new WorkflowDocument(new NetworkIdDTO(userName), Long.valueOf(documentId));
            ByteArrayInputStream input = new ByteArrayInputStream(document.getApplicationContent().getBytes());
            JAXBContext jc = JAXBContext.newInstance(CreateClu.class);
            Unmarshaller u = jc.createUnmarshaller();
            CreateClu createClu = (CreateClu) u.unmarshal(input);
            clu = createClu.getCluCreateInfo();
            cp.setClu(clu);
            cp.setAcknowledgeRequested(document.isAcknowledgeRequested());
            cp.setApprovalRequested(document.isApprovalRequested());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cp;
    }

}
