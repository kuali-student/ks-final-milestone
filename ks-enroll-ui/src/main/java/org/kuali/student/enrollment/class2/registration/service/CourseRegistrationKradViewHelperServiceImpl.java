package org.kuali.student.enrollment.class2.registration.service;

import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.student.common.uif.service.impl.KSViewHelperServiceImpl;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingManagementUtil;
import org.kuali.student.enrollment.class2.courseoffering.util.ManageSocConstants;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by swedev on 1/12/14.
 */
public class CourseRegistrationKradViewHelperServiceImpl extends KSViewHelperServiceImpl {
    private static final long serialVersionUID = 1L;
    private final static Logger LOG = LoggerFactory.getLogger(CourseRegistrationKradViewHelperServiceImpl.class);

    public TermInfo getTermByCode(String termCode) {

        try{
            QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();

            qbcBuilder.setPredicates(PredicateFactory.equal(CourseOfferingConstants.ATP_CODE, termCode));

            QueryByCriteria criteria = qbcBuilder.build();

            AcademicCalendarService acalService = CourseOfferingManagementUtil.getAcademicCalendarService();
            List<TermInfo> terms = acalService.searchForTerms(criteria, createContextInfo());
            int firstTerm = 0;
            if (terms.size() > 1) {
                GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, ManageSocConstants.MessageKeys.ERROR_MULTIPLE_TERMS);
                return null;
            }
            if (terms.isEmpty()) {
                GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, ManageSocConstants.MessageKeys.ERROR_INVALID_TERM);
                return null;
            }
            return terms.get(firstTerm);
        }catch (Exception e){
            LOG.debug("Error getting term for the code - {}", termCode);
            throw convertServiceExceptionsToUI(e);
        }
    }


}
