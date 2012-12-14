package org.kuali.student.myplan.audit.service;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.web.form.LookupForm;
import org.kuali.student.myplan.audit.dataobject.DegreeAuditItem;
import org.kuali.student.myplan.audit.dto.AuditReportInfo;
import org.kuali.student.myplan.audit.form.DegreeAuditForm;
import org.kuali.student.myplan.audit.util.DegreeAuditDataObjectHelper;
import org.kuali.student.myplan.course.service.CourseDetailsInquiryViewHelperServiceImpl;

import org.apache.log4j.Logger;
import org.kuali.student.myplan.main.service.MyPlanLookupableImpl;
import org.kuali.student.myplan.utils.UserSessionHelper;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.web.servlet.ModelAndView;

import javax.xml.namespace.QName;
import java.util.*;

public class DegreeAuditsLookupableHelperImpl extends MyPlanLookupableImpl {

    private final Logger logger = Logger.getLogger(CourseDetailsInquiryViewHelperServiceImpl.class);

    private transient DegreeAuditService degreeAuditService;

    @Override
    protected List<DegreeAuditItem> getSearchResults(LookupForm lookupForm, Map<String, String> fieldValues, boolean unbounded) {
        String studentId = null;
        try {
            studentId = UserSessionHelper.getAuditSystemKey();
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
        //  TODO: Calculate dates that make sense.
        Date begin = new Date();
        Date end = new Date();
        try {
            audits = degreeAuditService.getAuditsForStudentInDateRange(studentId, begin, end, DegreeAuditConstants.CONTEXT_INFO);
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
