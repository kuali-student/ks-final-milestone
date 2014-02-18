package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.krad.lookup.LookupForm;
import org.kuali.rice.krad.lookup.LookupableImpl;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingManagementUtil;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.enrollment.class2.courseoffering.util.ActivityOfferingConstants;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.ContextUtils;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AdvanceActivityOfferingLookupableImpl extends LookupableImpl {

    private static final Logger LOG = Logger.getLogger(AdvanceActivityOfferingLookupableImpl.class);

    @Override
    public List<?> performSearch(LookupForm lookupForm, Map<String, String> searchCriteria, boolean bounded) {
        List<ActivityOfferingInfo> activityOfferingInfos;
        List<CourseOfferingInfo> courseOfferingList = new ArrayList<CourseOfferingInfo>();
        String termId = null;
        String courseOfferingId;
        String termCode = searchCriteria.get(ActivityOfferingConstants.ACTIVITYOFFERING_TERM_CODE);
        String courseOfferingCode = searchCriteria.get(ActivityOfferingConstants.ACTIVITYOFFERING_COURSE_OFFERING_CODE);

        try {
            //1. get termId based on termCode
            if (StringUtils.isNotBlank(termCode)) {
                QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
                qbcBuilder.setPredicates(PredicateFactory.equal(ActivityOfferingConstants.ATP_CODE, termCode));
                QueryByCriteria criteria = qbcBuilder.build();

                // Do search.  In ideal case, termList contains one element, which is the desired term.
                List<TermInfo> termList = CourseOfferingManagementUtil.getAcademicCalendarService().searchForTerms(criteria, new ContextInfo());

                if (termList != null  && termList.size()>0 ){
                    int firstTerm = 0;
                    // Always get first term
                    termId = termList.get(firstTerm).getId();
                    LOG.info(">>> termId = " + termId);
                    if(termList.size()>1){
                        //logger.warn("AdvanceActivityOfferingLookupableImpl - find more than one term for specified termCode: " + termCode) ;
                        //System.out.println(">>Alert: find more than one term for specified termCode: "+termCode);
                        throw new RuntimeException("Alert: find more than one term for specified termCode: " + termCode);
                    }
                } else {
                    throw new RuntimeException("Error: Does not find a valid term with the termCode equal to " + termCode);
                }
            }

            //get courseOfferingId based on courseOfferingCode and termId
            if (StringUtils.isNotBlank(courseOfferingCode) && StringUtils.isNotBlank(termId)) {
                QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
                qbcBuilder.setPredicates(PredicateFactory.and(
                        PredicateFactory.equalIgnoreCase(ActivityOfferingConstants.ACTIVITYOFFERING_COURSE_OFFERING_CODE, courseOfferingCode),
                        PredicateFactory.equalIgnoreCase("atpId", termId)));
                QueryByCriteria criteria = qbcBuilder.build();

                //Do search. In ideal case, returns one element, which is the desired CO.
                courseOfferingList = CourseOfferingManagementUtil.getCourseOfferingService().searchForCourseOfferings(criteria, new ContextInfo());
            }

            //get all AOs based on the retrieved courseOfferingId
            if(!courseOfferingList.isEmpty() && courseOfferingList.size()==1){
                int firstCOInfo = 0;
                //Get the courseOfferingId from THE CO
                courseOfferingId = courseOfferingList.get(firstCOInfo).getId();
                activityOfferingInfos =  CourseOfferingManagementUtil.getCourseOfferingService().getActivityOfferingsByCourseOffering (courseOfferingId, ContextUtils.createDefaultContextInfo());
            } else if (courseOfferingList.size()>1) {
                throw new RuntimeException("Error: find more than one CO for specified courseOfferingCode: "+courseOfferingCode);
            } else {
                throw new RuntimeException("Error: Does not find a valid Course Offering with the courseOfferingCode equal to "+courseOfferingCode);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return activityOfferingInfos;
    }

}

