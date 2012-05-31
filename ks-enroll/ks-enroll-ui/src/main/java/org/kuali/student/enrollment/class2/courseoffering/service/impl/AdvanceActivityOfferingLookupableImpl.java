package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.apache.commons.lang.StringUtils;
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
        String termId = null;
        String courseOfferingId = null;
        String termCode = fieldValues.get(ActivityOfferingConstants.ACTIVITYOFFERING_TERM_CODE);
        String courseOfferingCode = fieldValues.get(ActivityOfferingConstants.ACTIVITYOFFERING_COURSE_OFFERING_CODE);
        try {
            //1. get termId based on termCode
            if (StringUtils.isNotBlank(termCode)) {
                QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
                qbcBuilder.setPredicates(PredicateFactory.equal(ActivityOfferingConstants.ATP_CODE, termCode));
                QueryByCriteria criteria = qbcBuilder.build();

                // Do search.  In ideal case, termList contains one element, which is the desired term.
                List<TermInfo> termList = getAcalService().searchForTerms(criteria, new ContextInfo());

                if (termList != null  && termList.size()>0 ){
                    // Always get first term
                    termId = termList.get(0).getId();
                    System.out.println(">>> termId = "+termId);
                    if(termList.size()>1){
                        //TODO: need to log --> find more than one term for specified termCode
                        System.out.println(">>Alert: find more than one term for specified termCode: "+termCode);
                    }
                } else {
                    new Exception("Error: Does not find a valid term with the termCode equal to "+ termCode);
                }
            }

            //2. get courseOffering based on courseOfferingCode
            List<CourseOfferingInfo> finalResult = new ArrayList<CourseOfferingInfo>();
            if (StringUtils.isNotBlank(courseOfferingCode)) {
                QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
                qbcBuilder.setPredicates(PredicateFactory.equal("atpId", termId));
                QueryByCriteria criteria = qbcBuilder.build();

                //Do search.  In ideal case, returns one element, which is the desired CO.
                List<CourseOfferingInfo> courseOfferingList = getCourseOfferingService().searchForCourseOfferings(criteria, new ContextInfo());
                //Just a quick fix as PredicateFactory doesn't support search within collections
                for (CourseOfferingInfo coInfo : courseOfferingList){
                System.out.println("<<< courseOfferingCode from CO:    "+coInfo.getCourseOfferingCode());
                    if (StringUtils.equalsIgnoreCase(coInfo.getCourseOfferingCode(),courseOfferingCode)){
                        finalResult.add(coInfo);                               
                    }
                }
            }

            //3. get all AOs based on  courseOfferingId
            if(!finalResult.isEmpty() && finalResult.size()==1){
                //Get the courseOfferingId from THE CO
                courseOfferingId = finalResult.get(0).getId();
                activityOfferingInfos = getCourseOfferingService().getActivityOfferingsByCourseOffering(courseOfferingId, new ContextInfo());

            }
            else if(finalResult.size()>1){
                    //TODO: need to log --> find more than one CO for specified courseOfferingCode
                    System.out.println(">>Error: find more than one CO for specified courseOfferingCode: "+courseOfferingCode);
            }
            else {
                new Exception("Error: Does not find a valid Course Offering with the courseOfferingCode equal to "+courseOfferingCode);
            }

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

