package org.kuali.student.ap.audit.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.namespace.QName;

import org.apache.log4j.Logger;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.web.form.LookupForm;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.audit.dataobject.DegreeAuditItem;
import org.kuali.student.ap.audit.dto.AuditReportInfo;
import org.kuali.student.ap.audit.util.DegreeAuditDataObjectHelper;
import org.kuali.student.ap.coursesearch.service.impl.CourseDetailsInquiryHelperImpl;
import org.kuali.student.myplan.main.service.MyPlanLookupableImpl;
import org.springframework.dao.DataRetrievalFailureException;

public class DegreeAuditsLookupableHelperImpl extends MyPlanLookupableImpl {

	private static final long serialVersionUID = 5470488652754576047L;

	private final static Logger LOG = Logger.getLogger(CourseDetailsInquiryHelperImpl.class);

    private transient DegreeAuditService degreeAuditService;

    @Override
    protected List<DegreeAuditItem> getSearchResults(LookupForm lookupForm, Map<String, String> fieldValues, boolean unbounded) {
        String studentId = null;
        try {
            studentId = KsapFrameworkServiceLocator.getUserSessionHelper().getAuditSystemKey();
        } catch (DataRetrievalFailureException e) {
            List<DegreeAuditItem> degreeAuditItems = new ArrayList<DegreeAuditItem>();
            return degreeAuditItems;
        }

        List<DegreeAuditItem> degreeAuditItems = new ArrayList<DegreeAuditItem>();
        DegreeAuditService degreeAuditService = getDegreeAuditService();

        if (degreeAuditService == null) {
            throw new RuntimeException("Degree audit service handle was null.");
        }

        List<AuditReportInfo> audits = new ArrayList<AuditReportInfo>();

        Date begin = new Date();
        Date end = new Date();
        try {
            audits = degreeAuditService.getAuditsForStudentInDateRange(studentId, begin, end, KsapFrameworkServiceLocator.getContext().getContextInfo());
        } catch (Exception e) {
            e.printStackTrace();
            String[] params = {};
            /*throw new RuntimeException("Request for audit ids failed.", e);*/
        }

        /**
         *  Make a list of DegreeAuditItem, but only include the most recent audit for a particular program.
         */
        Set<String> programSet = new HashSet<String>();
        for (AuditReportInfo audit : audits) {
            String programId = audit.getProgramId();
            if (!programSet.contains(programId)) {
                programSet.add(programId);
                degreeAuditItems.add(DegreeAuditDataObjectHelper.makeDegreeAuditDataObject(audit));
            }
        }
        if(degreeAuditItems.size()>0){
            degreeAuditItems.get(0).setRecentAudit(true);
        }
        return degreeAuditItems;
    }

    public DegreeAuditService getDegreeAuditService() {
        if (degreeAuditService == null) {
            degreeAuditService = (DegreeAuditService)
                    GlobalResourceLoader.getService(new QName(DegreeAuditServiceConstants.NAMESPACE,
                            DegreeAuditServiceConstants.SERVICE_NAME));
        }
        return degreeAuditService;
    }


}
