package org.kuali.student.ap.audit.service;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.kns.inquiry.KualiInquirableImpl;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.audit.dataobject.DegreeAuditItem;
import org.kuali.student.ap.audit.dto.AuditReportInfo;
import org.kuali.student.ap.audit.util.DegreeAuditDataObjectHelper;

import javax.xml.namespace.QName;
import java.util.Map;

/**
 *  Provides the data object for the DegreeAuditDetails-InquiryView view.
 */
public class DegreeAuditInquiryViewHelperServiceImpl extends KualiInquirableImpl {

    private transient DegreeAuditService degreeAuditService;

    @Override
    public DegreeAuditItem retrieveDataObject(Map fieldValues) {
        String degreeAuditId = (String) fieldValues.get(DegreeAuditConstants.AUDIT_PARAM_ID);
        String auditType = (String) fieldValues.get(DegreeAuditConstants.AUDIT_PARAM_TYPE);

        if (degreeAuditId == null) {
            throw new RuntimeException("Audit ID was null.");
        }

        if (degreeAuditId.length() == 0) {
            throw new RuntimeException("Audit ID was empty.");
        }

        if (auditType == null) {
//            auditType = DegreeAuditConstants.AUDIT_TYPE_KEY_HTML;
            auditType = DegreeAuditConstants.AUDIT_TYPE_KEY_DEFAULT;
        }

        AuditReportInfo degreeAuditReport = null;
        try {
            degreeAuditReport = getDegreeAuditService().getAuditReport(degreeAuditId,
                    auditType, KsapFrameworkServiceLocator.getContext().getContextInfo());
        } catch (Exception e) {
            throw new RuntimeException(String.format("Failed to fetch audit report [%s].", degreeAuditId), e);
        }

        return DegreeAuditDataObjectHelper.makeDegreeAuditDataObject(degreeAuditReport);
    }

    public DegreeAuditService getDegreeAuditService() {
       if (degreeAuditService == null) {
            degreeAuditService = (DegreeAuditService)
                GlobalResourceLoader.getService(new QName(DegreeAuditConstants.NAMESPACE, DegreeAuditConstants.SERVICE_NAME));
        }
        return degreeAuditService;
    }

    public void setDegreeAuditService(DegreeAuditService degreeAuditService) {
        this.degreeAuditService = degreeAuditService;
    }
}