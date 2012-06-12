package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.SelectControl;
import org.kuali.rice.krad.uif.field.InputField;
import org.kuali.rice.krad.uif.service.impl.ViewHelperServiceImpl;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.student.enrollment.acal.constants.AcademicCalendarServiceConstants;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class2.courseoffering.form.CourseOfferingManagementForm;
import org.kuali.student.enrollment.class2.courseoffering.service.CourseOfferingManagementViewHelperService;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.lum.course.dto.ActivityInfo;
import org.kuali.student.lum.course.dto.FormatInfo;
import org.kuali.student.lum.course.service.CourseService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.LocaleInfo;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class CourseOfferingManagementViewHelperServiceImpl extends ViewHelperServiceImpl implements CourseOfferingManagementViewHelperService{
    private transient AcademicCalendarService acalService = null;
    private transient CourseOfferingService coService = null;

    private CourseService courseService;

 
    public List<TermInfo> findTermByTermCode(String termCode) throws Exception {
        // TODO: Find sensible way to rewrap exception that acal service may throw
        // Find the term (alas, I think it does approximate search)
        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        // TODO: How does one get rid of hard-coding "atpCode"?
        qbcBuilder.setPredicates(PredicateFactory.equal("atpCode", termCode));

        QueryByCriteria criteria = qbcBuilder.build();

        // Do search.  In ideal case, terms returns one element, which is the desired term.
        AcademicCalendarService acalService = _getAcalService();
        List<TermInfo> terms = acalService.searchForTerms(criteria, new ContextInfo());
        return terms;
    }

    public void loadCourseOfferingsByTermAndSubjectCode (String termId, String subjectCode, CourseOfferingManagementForm form) throws Exception{
        List<String> courseOfferingIds = _getCourseOfferingService().getCourseOfferingIdsByTermAndSubjectArea(termId, subjectCode, getContextInfo());
        if(courseOfferingIds.size()>0){
            List<CourseOfferingInfo> courseOfferings = new ArrayList<CourseOfferingInfo>(courseOfferingIds.size());
            for(String coId : courseOfferingIds) {
                courseOfferings.add(_getCourseOfferingService().getCourseOffering(coId, getContextInfo()));
            }
            form.setCourseOfferingList(courseOfferings);
        }
    }

    public List<CourseOfferingInfo> findCourseOfferingsByTermAndCourseOfferingCode (String termCode, String courseOfferingCode, CourseOfferingManagementForm form) throws Exception{
        List<CourseOfferingInfo> courseOfferings = new ArrayList<CourseOfferingInfo>();
        String termId = null;

        try {
            //1. get termId based on termCode
            if (StringUtils.isNotBlank(termCode)) {
                QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
                qbcBuilder.setPredicates(PredicateFactory.equal(CourseOfferingConstants.ATP_CODE, termCode));
                QueryByCriteria criteria = qbcBuilder.build();

                // Do search.  In ideal case, termList contains one element, which is the desired term.
                List<TermInfo> termList = _getAcalService().searchForTerms(criteria, new ContextInfo());

                if (termList != null  && termList.size()>0 ){
                    // Always get first term
                    termId = termList.get(0).getId();
                    System.out.println(">>> termId = "+termId);
                    if(termList.size()>1){
                        //logger.warn("AdvanceActivityOfferingLookupableImpl - find more than one term for specified termCode: " + termCode) ;
                        //System.out.println(">>Alert: find more than one term for specified termCode: "+termCode);
                        throw new RuntimeException("Alert: find more than one term for specified termCode: "+termCode);
                    }
                } else {
                    new Exception("Error: Does not find a valid term with the termCode equal to "+ termCode);
                }
            }

            //get courseOfferingId based on courseOfferingCode and termId
            if (StringUtils.isNotBlank(courseOfferingCode) && StringUtils.isNotBlank(termId)) {
                QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
                qbcBuilder.setPredicates(PredicateFactory.and(
                        PredicateFactory.equalIgnoreCase(CourseOfferingConstants.COURSEOFFERING_COURSE_OFFERING_CODE, courseOfferingCode),
                        PredicateFactory.equalIgnoreCase(CourseOfferingConstants.ATP_ID, termId)));
                QueryByCriteria criteria = qbcBuilder.build();

                //Do search. In ideal case, returns one element, which is the desired CO.
                courseOfferings = getCourseOfferingService().searchForCourseOfferings(criteria, new ContextInfo());
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return courseOfferings;
    }

    public void populateFormatTypes(InputField field, CourseOfferingManagementForm coForm) {

        List<KeyValue> keyValues = new ArrayList<KeyValue>();
        keyValues.add(new ConcreteKeyValue("", "Select Format Type"));
        CourseOfferingInfo selectedCourseOffering = coForm.getTheCourseOffering();

        List<FormatInfo> formatInfos;
        try {
            formatInfos = getCourseService().getCourseFormats(selectedCourseOffering.getCourseId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            for (FormatInfo formatInfo : formatInfos) {
                keyValues.add(new ConcreteKeyValue(formatInfo.getType(),formatInfo.getId()));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        ((SelectControl) field.getControl()).setOptions(keyValues);

    }

    public void populateActivityTypes(InputField field, CourseOfferingManagementForm coForm) {

        List<KeyValue> keyValues = new ArrayList<KeyValue>();
        keyValues.add(new ConcreteKeyValue("", "Select Activity Type"));
        String formatId = coForm.getFormatIdForNewAO();

        try {
            List<ActivityInfo> activityInfos = getCourseService().getCourseActivities(formatId);
            for (ActivityInfo activityInfo : activityInfos) {
               keyValues.add(new ConcreteKeyValue(activityInfo.getActivityType(),activityInfo.getId()));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        ((SelectControl) field.getControl()).setOptions(keyValues);

    }

    public ActivityOfferingInfo createActivityOfferings(String formatOfferingId,String activityId,int noOfActivityOfferings, CourseOfferingInfo courseOfferingInfo){

        FormatOfferingInfo formatOfferingInfo;
        try {
            formatOfferingInfo = getCourseOfferingService().getFormatOffering(formatOfferingId,getContextInfo());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        ActivityInfo activity = null;
        try {
            List<ActivityInfo> activityInfo = getCourseService().getCourseActivities(formatOfferingInfo.getFormatId());
            for (ActivityInfo info : activityInfo) {
                if (StringUtils.equals(activityId,info.getId())){
                    activity = info;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        /**
         * FIXME: get activity offering type for an activity offering
         */

        for (int i=0;i<noOfActivityOfferings;i++){
            ActivityOfferingInfo aoInfo = new ActivityOfferingInfo();
            aoInfo.setFormatOfferingId(formatOfferingId);
            aoInfo.setTypeKey(activity.getActivityType());
            aoInfo.setCourseOfferingId(courseOfferingInfo.getId());
            try {
                return _getCourseOfferingService().createActivityOffering(formatOfferingId,activityId,"kuali.lui.type.grouping.activity",aoInfo,getContextInfo());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return null;

    }

    public void loadActivityOfferingsByCourseOffering (CourseOfferingInfo theCourseOfferingInfo,CourseOfferingManagementForm form) throws Exception{
        String courseOfferingId = theCourseOfferingInfo.getId();
        List<ActivityOfferingInfo> activityOfferingList =_getCourseOfferingService().getActivityOfferingsByCourseOffering(courseOfferingId, getContextInfo());
        form.setActivityWrapperList(activityOfferingList);
     }


    private CourseOfferingService _getCourseOfferingService() {
        if (coService == null) {
            coService = (CourseOfferingService) GlobalResourceLoader.getService(new QName(CourseOfferingServiceConstants.NAMESPACE,
                    CourseOfferingServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return coService;
    }

    public ContextInfo getContextInfo() {
        ContextInfo contextInfo = new ContextInfo();
        contextInfo.setAuthenticatedPrincipalId(GlobalVariables.getUserSession().getPrincipalId());
        contextInfo.setPrincipalId(GlobalVariables.getUserSession().getPrincipalId());
        LocaleInfo localeInfo = new LocaleInfo();
        localeInfo.setLocaleLanguage(Locale.getDefault().getLanguage());
        localeInfo.setLocaleRegion(Locale.getDefault().getCountry());
        contextInfo.setLocale(localeInfo);
        return contextInfo;
    }


    private AcademicCalendarService _getAcalService() {
        if (acalService == null) {
            acalService = (AcademicCalendarService) GlobalResourceLoader.getService(new QName(AcademicCalendarServiceConstants.NAMESPACE,
                    AcademicCalendarServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return acalService;
    }

    public CourseOfferingService getCourseOfferingService() {
        return CourseOfferingResourceLoader.loadCourseOfferingService();
    }

    public CourseService getCourseService() {
        if (courseService == null){
            courseService = CourseOfferingResourceLoader.loadCourseService();
        }
        return courseService;
    }

}
