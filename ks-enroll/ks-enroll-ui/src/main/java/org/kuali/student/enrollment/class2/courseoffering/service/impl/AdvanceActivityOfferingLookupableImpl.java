package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.lookup.LookupableImpl;
import org.kuali.rice.krad.web.form.LookupForm;
import org.kuali.student.enrollment.acal.constants.AcademicCalendarServiceConstants;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class2.courseoffering.util.ActivityOfferingConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.enrollment.common.util.ContextBuilder;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.dto.ContextInfo;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AdvanceActivityOfferingLookupableImpl extends LookupableImpl {

    @Override
    protected List<?> getSearchResults(LookupForm lookupForm, Map<String, String> fieldValues, boolean unbounded) {
        List<ActivityOfferingInfo> activityOfferingInfos = new ArrayList<ActivityOfferingInfo>();
        String courseOfferingId = null;
        String termCode = fieldValues.get(ActivityOfferingConstants.ACTIVITYOFFERING_TERM_CODE);
        String courseOfferingCode = fieldValues.get(ActivityOfferingConstants.ACTIVITYOFFERING_COURSE_OFFERING_CODE);

        final Logger logger = Logger.getLogger(FormatOfferingInfoMaintainableImpl.class);

        try {
            //get courseOfferingId based on courseOfferingCode and termCode
            if (StringUtils.isNotBlank(courseOfferingCode) && StringUtils.isNotBlank(termCode)) {
                QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
                qbcBuilder.setPredicates(PredicateFactory.and(
                        PredicateFactory.equalIgnoreCase(ActivityOfferingConstants.ACTIVITYOFFERING_COURSE_OFFERING_CODE, courseOfferingCode),
                        PredicateFactory.equalIgnoreCase("atpId", termCode)));
                QueryByCriteria criteria = qbcBuilder.build();

                //Do search.  In ideal case, returns one element, which is the desired CO.
                List<CourseOfferingInfo> courseOfferingList = getCourseOfferingService().searchForCourseOfferings(criteria, new ContextInfo());
                if(courseOfferingList!=null && courseOfferingList.size()>0){
                    // Always get first CO
                    courseOfferingId = courseOfferingList.get(0).getId();
                    if(courseOfferingList.size()>1){
                        logger.warn("AdvanceActivityOfferingLookupableImpl - find more than one CO for specified courseOfferingCode: " + courseOfferingCode) ;
                    }
                } else {
                    new Exception("Error: Does not find a valid Course Offering with the courseOfferingCode equal to "+courseOfferingCode);
                }
            }

            //get all AOs based on the retrieved courseOfferingId
            activityOfferingInfos =  getCourseOfferingService().getActivityOfferingsByCourseOffering (courseOfferingId, getContextInfo());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return activityOfferingInfos;
    }

    private boolean hasCriteria(Map<String, String> fieldValues){
        return StringUtils.isNotBlank(fieldValues.get(ActivityOfferingConstants.ACTIVITYOFFERING_ID));
    }

    public CourseOfferingService getCourseOfferingService() {
        return CourseOfferingResourceLoader.loadCourseOfferingService();
    }

    public AcademicCalendarService getAcalService() {
        return (AcademicCalendarService) GlobalResourceLoader.getService(new QName(AcademicCalendarServiceConstants.NAMESPACE,
                AcademicCalendarServiceConstants.SERVICE_NAME_LOCAL_PART));

    }

    public ContextInfo getContextInfo() {
        return ContextBuilder.loadContextInfo();
    }


}

