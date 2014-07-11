package org.kuali.student.enrollment.class2.registration.admin.service.impl;

import net.sf.ehcache.Element;
import org.apache.commons.collections.keyvalue.MultiKey;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.kim.api.identity.affiliation.EntityAffiliation;
import org.kuali.rice.kim.api.identity.entity.Entity;
import org.kuali.rice.kim.api.identity.name.EntityName;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.student.common.uif.service.impl.KSViewHelperServiceImpl;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.enrollment.class2.courseoffering.service.impl.CourseInfoByTermLookupableImpl;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingManagementUtil;
import org.kuali.student.enrollment.class2.courseoffering.util.ManageSocConstants;
import org.kuali.student.enrollment.class2.registration.admin.form.AdminRegistrationForm;
import org.kuali.student.enrollment.class2.registration.admin.form.RegistrationActivity;
import org.kuali.student.enrollment.class2.registration.admin.form.RegistrationCourse;
import org.kuali.student.enrollment.class2.registration.admin.service.CourseRegAdminViewHelperService;
import org.kuali.student.enrollment.class2.registration.admin.util.AdminRegConstants;
import org.kuali.student.enrollment.class2.registration.admin.util.AdminRegistrationUtil;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseregistration.dto.ActivityRegistrationInfo;
import org.kuali.student.enrollment.courseregistration.dto.CourseRegistrationInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.DtoConstants;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.infc.SearchResultCell;
import org.kuali.student.r2.core.search.infc.SearchResultRow;
import org.kuali.student.r2.lum.util.constants.CluServiceConstants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by SW Genis on 2014/07/04.
 */
public class AdminRegistrationViewHelperServiceImpl extends KSViewHelperServiceImpl implements CourseRegAdminViewHelperService {
    private final static String CACHE_NAME = "CourseOfferingCodeCache";
    @Override
    public void getRegistrationStatus() {

    }

    @Override
    public void submitRegistrationRequest() {

        // get the regGroup
        //RegGroupSearchResult rg = CourseRegistrationAndScheduleOfClassesUtil.getRegGroup(null, termCode, courseCode, regGroupCode, regGroupId, contextInfo);

        // get the registration group, returns default (from Course Offering) credits (as creditId) and grading options (as a string of options)
        //CourseOfferingInfo courseOfferingInfo = CourseRegistrationAndScheduleOfClassesUtil.getCourseOfferingIdCreditGrading(rg.getCourseOfferingId(), courseCode, rg.getTermId(), termCode);

        // verify passed credits (must be non-empty unless fixed) and grading option (can be null)
        //credits = verifyRegistrationRequestCreditsGradingOption(courseOfferingInfo, credits, gradingOptionId, contextInfo);

        //Create the request object
        //RegistrationRequestInfo regReqInfo = createRegistrationRequest(contextInfo.getPrincipalId(), rg.getTermId(), rg.getRegGroupId(), null, credits, gradingOptionId, LprServiceConstants.LPRTRANS_REGISTER_TYPE_KEY, LprServiceConstants.LPRTRANS_NEW_STATE_KEY, LprServiceConstants.REQ_ITEM_ADD_TYPE_KEY, LprServiceConstants.LPRTRANS_ITEM_NEW_STATE_KEY, okToWaitlist);

        // persist the request object in the service
        //RegistrationRequestInfo newRegReq = CourseRegistrationAndScheduleOfClassesUtil.getCourseRegistrationService().createRegistrationRequest(LprServiceConstants.LPRTRANS_REGISTER_TYPE_KEY, regReqInfo, contextInfo);

        // submit the request to the registration engine.
        //return CourseRegistrationAndScheduleOfClassesUtil.getCourseRegistrationService().submitRegistrationRequest(newRegReq.getId(), contextInfo);
    }

    @Override
    public TermInfo getTermByCode(String termCode) {

        try{
            QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();

            qbcBuilder.setPredicates(PredicateFactory.equal(CourseOfferingConstants.ATP_CODE, termCode));

            QueryByCriteria criteria = qbcBuilder.build();

            List<TermInfo> terms = AdminRegistrationUtil.getAcademicCalendarService().searchForTerms(criteria, createContextInfo());
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
//            LOG.debug("Error getting term for the code - {}", termCode);
            throw convertServiceExceptionsToUI(e);
        }
    }

    @Override
    public List<RegistrationCourse> getCourseRegStudentAndTerm(String studentId, String termCode) {

        List<RegistrationCourse> registeredCourses = new ArrayList<RegistrationCourse>();

        try {

            List<CourseRegistrationInfo> courseRegistrationInfos = AdminRegistrationUtil.getCourseRegistrationService().getCourseRegistrationsByStudentAndTerm(studentId, termCode, createContextInfo());

            for (CourseRegistrationInfo courseRegInfo : courseRegistrationInfos) {

                RegistrationCourse registeredCourse = new RegistrationCourse();
                CourseOfferingInfo coInfo = AdminRegistrationUtil.getCourseOfferingService().getCourseOffering(courseRegInfo.getCourseOfferingId(), createContextInfo());
                registeredCourse.setCode(coInfo.getCourseOfferingCode());
                registeredCourse.setCourseName(coInfo.getCourseOfferingTitle());
                registeredCourse.setCredits(Integer.parseInt(coInfo.getCreditCnt()));
                registeredCourse.setRegDate(courseRegInfo.getEffectiveDate());

                List<ActivityRegistrationInfo> activityOfferings = AdminRegistrationUtil.getCourseRegistrationService().getActivityRegistrationsForCourseRegistration(courseRegInfo.getId(), createContextInfo());
                registeredCourse.setSection(AdminRegistrationUtil.getCourseOfferingService().getRegistrationGroup(courseRegInfo.getRegistrationGroupId(), createContextInfo()).getRegistrationCode());

                for (ActivityRegistrationInfo activityRegInfos : activityOfferings) {
                    //Use activityRegInfos - to retrieve the hardcoded values
                    RegistrationActivity regActivity = new RegistrationActivity("Lec", "MWF 04:00pm - 05:30pm", "Steve Capriani", "PTX 2391");
                    registeredCourse.getActivities().add(regActivity);

                }
                registeredCourses.add(registeredCourse);
            }


        } catch (Exception e) {
            throw convertServiceExceptionsToUI(e);
        }
        return registeredCourses;
    }


    @Override
    public void populateStudentInfo(AdminRegistrationForm form) throws Exception {

        Entity entityInfo =  AdminRegistrationUtil.getIdentityService().getEntity(form.getStudentId());
        if ((entityInfo != null)) {
          
            //KSENROLL-13558 :work around for incorrect Data
            form.getPrincipalIDs().addAll(entityInfo.getPrincipals());

            Boolean validStudent = false;
            for (EntityAffiliation entityAffiliationInfo : entityInfo.getAffiliations()) {
                if (entityAffiliationInfo.getAffiliationType().getCode().equals(AdminRegConstants.STUDENT_AFFILIATION_TYPE_CODE)) {
                    validStudent = true;
                }
            }

            if (!validStudent) {
//                GlobalVariables.getMessageMap().putErrorForSectionId(AdminRegConstants.STUDENT_INFO_SECTION, AdminRegConstants.ADMIN_REG_MSG_ERROR_STUDENT_ROLE_NOT_FOUND, form.getStudentId());
//                return;
            }

            for (EntityName entityNameInfo : entityInfo.getNames()) {
                if (entityNameInfo.isDefaultValue()) {
                    form.setStudentName(entityNameInfo.getFirstName() + " " + entityNameInfo.getLastName());
                    break;
                }
            }

        } else {
            GlobalVariables.getMessageMap().putErrorForSectionId(AdminRegConstants.STUDENT_INFO_SECTION, AdminRegConstants.ADMIN_REG_MSG_ERROR_INVALID_STUDENT, form.getStudentId());
        }
    }

    /**
     * The premise of this is rather simple. Return a distinct list of course code. At a minimum there needs to
     * be one character. It then does a char% search. so E% will return all ENGL or any E* codes.
     *
     * This implementation is a little special. It's both cached and recursive.
     *
     * Because this is a structured search and course codes don't update often we can cache this pretty heavily and make
     * some assumptions that allow us to make this very efficient.
     *
     * So a user wants to type and see the type ahead results very quickly. The server wants as few db calls as possible.
     * The "bad" way to do this is to search on Every character entered. If we cache the searches then we'll get much
     * better performance. But we can go one step further because ths is a structured search. The first letter E in
     * ENGL will return EVERY course that starts with an E. So when you search for EN... why would you call the DB if
     * you have already called a search for E. So this uses recursion to build the searches. So, in the average case
     * you will only have to call a db search Once for Every first letter of the course codes.
     *
     * @return List of distinct course codes or an empty list
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     */
    public List<String> retrieveCourseCodes(String targetTermCode, String catalogCourseCode) throws InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException {

        List<String> results = new ArrayList<String>();

        if(catalogCourseCode == null || catalogCourseCode.isEmpty())   return results;   // if nothing passed in, return empty list

        catalogCourseCode = catalogCourseCode.toUpperCase(); // force toUpper

        MultiKey cacheKey = new MultiKey(targetTermCode+"retrieveCourseCodes", catalogCourseCode);

        // only one character. This is the base search.
        if(catalogCourseCode.length() == 1){
            Element cachedResult = CourseOfferingManagementUtil.getCacheManager().getCache(CACHE_NAME).get(cacheKey);

            Object result;
            if (cachedResult == null) {
                result = _retrieveCourseCodes(targetTermCode, catalogCourseCode);
                CourseOfferingManagementUtil.getCacheManager().getCache(CACHE_NAME).put(new Element(cacheKey, result));
                results = (List<String>)result;
            } else {
                results = (List<String>)cachedResult.getValue();
            }
        }else{
            Element cachedResult = CourseOfferingManagementUtil.getCacheManager().getCache(CACHE_NAME).get(cacheKey);

            if (cachedResult == null) {
                // This is where the recursion happens. If you entered CHEM and it didn't find anything it will
                // recurse and search for CHE -> CH -> C (C is the base). Each time building up the cache.
                // This for loop is the worst part of this method. I'd love to use some logic to remove the for loop.
                for(String courseCode : retrieveCourseCodes(targetTermCode, catalogCourseCode.substring(0,catalogCourseCode.length()-1))){
                    // for every course code, see if it's part of the Match.
                    if(courseCode.startsWith(catalogCourseCode)){
                        results.add(courseCode);
                    }
                }

                CourseOfferingManagementUtil.getCacheManager().getCache(CACHE_NAME).put(new Element(cacheKey, results));
            } else {
                results = (List<String>)cachedResult.getValue();
            }
        }

        return results;
    }

    /**
     * Does a search Query for course codes used for auto suggest
     * @param catalogCourseCode the starting characters of a course code
     * @return a list of CourseCodeSuggestResults containing matching course codes
     */
    private List<String> _retrieveCourseCodes(String targetTermCode, String catalogCourseCode) throws InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException {

        List<String> rList = new ArrayList<String>();
        Set<String> rSet = new LinkedHashSet<String>(rList);
        ContextInfo context = ContextUtils.createDefaultContextInfo();

        //First get ATP information
        List<AtpInfo> atps = CourseOfferingManagementUtil.getAtpService().getAtpsByCode(targetTermCode, context);
        if(atps == null || atps.size() != 1){
            return rList;
        }

        //Then do the search
        SearchRequestInfo request = new SearchRequestInfo("lu.search.courseCodes");
        request.addParam(CourseInfoByTermLookupableImpl.QueryParamEnum.CODE.getQueryKey(), catalogCourseCode);
        request.addParam("lu.queryParam.luOptionalType", CluServiceConstants.CREDIT_COURSE_LU_TYPE_KEY);
        request.addParam("lu.queryParam.luOptionalState", Arrays.asList(new String[]{
                DtoConstants.STATE_ACTIVE, DtoConstants.STATE_RETIRED, DtoConstants.STATE_SUPERSEDED}));
        request.addParam(CourseInfoByTermLookupableImpl.QueryParamEnum.TERM_START.getQueryKey(), DateFormatters.QUERY_SERVICE_TIMESTAMP_FORMATTER.format(atps.get(0).getStartDate()));
        request.addParam(CourseInfoByTermLookupableImpl.QueryParamEnum.TERM_END.getQueryKey(), DateFormatters.QUERY_SERVICE_TIMESTAMP_FORMATTER.format(atps.get(0).getEndDate()));
        request.setSortColumn("lu.resultColumn.cluOfficialIdentifier.cluCode");

        SearchResultInfo results = CourseOfferingManagementUtil.getCluService().search(request, context);
        for(SearchResultRow row:results.getRows()){
            for(SearchResultCell cell:row.getCells()){
                if("lu.resultColumn.cluOfficialIdentifier.cluCode".equals(cell.getKey())){
                    rSet.add(cell.getValue());
                }
            }
        }
        return new ArrayList<String>(rSet);
    }

}
