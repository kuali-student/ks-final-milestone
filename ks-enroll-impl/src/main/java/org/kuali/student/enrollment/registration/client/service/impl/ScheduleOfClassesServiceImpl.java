package org.kuali.student.enrollment.registration.client.service.impl;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.rice.core.api.criteria.Predicate;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.kim.api.identity.entity.EntityDefault;
import org.kuali.rice.kim.api.identity.entity.EntityDefaultQueryResults;
import org.kuali.rice.kim.api.identity.principal.Principal;
import org.kuali.student.common.collection.KSCollectionUtils;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.enrollment.lpr.dto.LprInfo;
import org.kuali.student.enrollment.registration.client.service.ScheduleOfClassesService;
import org.kuali.student.enrollment.registration.client.service.dto.ActivityOfferingScheduleComponentResult;
import org.kuali.student.enrollment.registration.client.service.dto.ActivityOfferingSearchResult;
import org.kuali.student.enrollment.registration.client.service.dto.ActivityTypeSearchResult;
import org.kuali.student.enrollment.registration.client.service.dto.CourseAndPrimaryAOSearchResult;
import org.kuali.student.enrollment.registration.client.service.dto.CourseSearchResult;
import org.kuali.student.enrollment.registration.client.service.dto.InstructorSearchResult;
import org.kuali.student.enrollment.registration.client.service.dto.RegGroupSearchResult;
import org.kuali.student.enrollment.registration.client.service.dto.ScheduleSearchResult;
import org.kuali.student.enrollment.registration.client.service.dto.TermSearchResult;
import org.kuali.student.enrollment.registration.client.service.impl.util.CourseRegistrationAndScheduleOfClassesUtil;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.TimeOfDayInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.TimeOfDayHelper;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.class1.search.ActivityOfferingSearchServiceImpl;
import org.kuali.student.r2.core.class1.search.CoreSearchServiceImpl;
import org.kuali.student.r2.core.class1.search.CourseOfferingManagementSearchImpl;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;

import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.kuali.rice.core.api.criteria.PredicateFactory.equal;

public class ScheduleOfClassesServiceImpl implements ScheduleOfClassesService {

    public static final Logger LOGGER = Logger.getLogger(ScheduleOfClassesServiceImpl.class);

    private static final Comparator<RegGroupSearchResult> regResultComparator = new RegResultComparator();

    /**
     * COURSE OFFERINGS *
     */

    @Override
    public Response restfulSearchForCourseOfferings(String termId, String termCode, String courseCode) {
        Response.ResponseBuilder response;

        try {
            List<CourseSearchResult> courseSearchResults = searchForCourseOfferings(termId, termCode, courseCode);
            response = Response.ok(courseSearchResults);
        } catch (Throwable t) {
            LOGGER.warn(t);
            response = Response.serverError().entity(t.getMessage());
        }

        return response.build();
    }

    @Override
    public List<CourseSearchResult> searchForCourseOfferingsByTermIdAndCourse(String termId, String courseCode) throws InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException {
        return searchForCourseOfferings(termId, null, courseCode);
    }

    @Override
    public List<CourseSearchResult> searchForCourseOfferingsByTermCodeAndCourse(String termCode, String courseCode) throws InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException {
        return searchForCourseOfferings(null, termCode, courseCode);
    }

    @Override
    public Response restfulSearchForCourseOfferingsAndPrimaryAosByTermAndCourse(String termId, String termCode, String courseCode) {
        Response.ResponseBuilder response;

        try {
            List<CourseAndPrimaryAOSearchResult> courseSearchResults = searchForCourseOfferingsAndPrimaryAosByTermAndCourse(termId, termCode, courseCode);
            response = Response.ok(courseSearchResults);
        } catch (Throwable t) {
            LOGGER.warn(t);
            response = Response.serverError().entity(t.getMessage());
        }

        return response.build();
    }


    /**
     * REGISTRATION GROUPS *
     */

    @Override
    public Response restfulSearchForRegistrationGroups(String courseOfferingId, String termId, String termCode, String courseCode, String regGroupName) {
        Response.ResponseBuilder response;

        try {
            List<RegGroupSearchResult> regGroupSearchResults = searchForRegistrationGroups(courseOfferingId, termId, termCode, courseCode, regGroupName);
            response = Response.ok(regGroupSearchResults);
        } catch (Throwable t) {
            LOGGER.warn(t);
            response = Response.serverError().entity(t.getMessage());
        }

        return response.build();
    }

    @Override
    public RegGroupSearchResult searchForRegistrationGroupByTermAndCourseAndRegGroup(String termCode, String courseCode, String regGroupName) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<RegGroupSearchResult> regGroupSearchResults = searchForRegistrationGroups(null, null, termCode, courseCode, regGroupName);
        return KSCollectionUtils.getRequiredZeroElement(regGroupSearchResults);
    }


    /**
     * ACTIVITY OFFERINGS *
     */

    @Override
    public Response restfulSearchForActivityOfferings(String courseOfferingId, String termId, String termCode, String courseCode) {
        Response.ResponseBuilder response;

        try {
            List<ActivityOfferingSearchResult> activityOfferingSearchResults = searchForActivityOfferings(courseOfferingId, termId, termCode, courseCode);
            response = Response.ok(activityOfferingSearchResults);
        } catch (Throwable t) {
            LOGGER.warn(t);
            response = Response.serverError().entity(t);
        }

        return response.build();
    }


    /**
     * ACTIVITY TYPES *
     */

    @Override
    public Response restfulSearchForActivityTypes(String courseOfferingId, String termId, String termCode, String courseCode) {
        Response.ResponseBuilder response;

        try {
            List<ActivityTypeSearchResult> activityTypeSearchResults = searchForActivityTypes(courseOfferingId, termId, termCode, courseCode);
            response = Response.ok(activityTypeSearchResults);
        } catch (Throwable t) {
            LOGGER.warn(t);
            response = Response.serverError().entity(t);
        }

        return response.build();
    }


    /**
     * INSTRUCTORS *
     */

    @Override
    public Response restfulSearchForInstructors(String courseOfferingId, String activityOfferingId, String termId, String termCode, String courseCode) {
        Response.ResponseBuilder response;

        try {
            List<InstructorSearchResult> instructorSearchResults = searchForInstructors(courseOfferingId, activityOfferingId, termId, termCode, courseCode);
            response = Response.ok(instructorSearchResults);
        } catch (Throwable t) {
            LOGGER.warn(t);
            response = Response.serverError().entity(t);
        }

        return response.build();
    }


    /**
     * TERMS *
     */

    @Override
    public Response restfulSearchForTerms(String termCode, boolean isActiveTerms) {
        Response.ResponseBuilder response;

        try {
            List<TermSearchResult> termSearchResults = searchForTerms(termCode, isActiveTerms);
            response = Response.ok(termSearchResults);
        } catch (Throwable t) {
            LOGGER.warn(t);
            response = Response.serverError().entity(t);
        }

        return response.build();
    }

    @Override
    public String getTermIdByTermCode(String termCode) throws InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException {
        return KSCollectionUtils.getRequiredZeroElement(CourseRegistrationAndScheduleOfClassesUtil.getTermsByTermCode(termCode)).getTermId();
    }


    /**
     * PRIVATE HELPERS *
     */

    private List<TermSearchResult> searchForTerms(String termCode, boolean isActiveTerms) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        if (!StringUtils.isEmpty(termCode)) {
            return CourseRegistrationAndScheduleOfClassesUtil.getTermsByTermCode(termCode);
        }

        if (isActiveTerms) {
            return getActiveTerms();
        }

        return getAllTerms();
    }

    private List<TermSearchResult> getActiveTerms() {
        List<AtpInfo> validAtps = getValidAtps(ContextUtils.createDefaultContextInfo());
        return CourseRegistrationAndScheduleOfClassesUtil.getTermSearchResultsFromAtpInfos(validAtps);
    }

    private List<TermSearchResult> getAllTerms() throws InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException {
        QueryByCriteria criteria = QueryByCriteria.Builder.create().build();
        return CourseRegistrationAndScheduleOfClassesUtil.getTermSearchResultsFromAtpInfos(CourseRegistrationAndScheduleOfClassesUtil.getAtpService().searchForAtps(criteria, ContextUtils.createDefaultContextInfo()));
    }

    private List<InstructorSearchResult> searchForInstructors(String courseOfferingId, String activityOfferingId, String termId, String termCode, String courseCode) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {

        if (!StringUtils.isEmpty(activityOfferingId)) {
            return getInstructorsByActivityOfferingId(activityOfferingId);
        }

        courseOfferingId = getCourseOfferingId(courseOfferingId, courseCode, termId, termCode);
        return getInstructorsByCourseOfferingId(courseOfferingId);
    }

    private List<InstructorSearchResult> getInstructorsByCourseOfferingId(String courseOfferingId) throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException {

        List<ActivityOfferingSearchResult> aoList = searchForRawActivities(courseOfferingId);

        List<String> aoIds = new ArrayList<String>();
        for (ActivityOfferingSearchResult ao : aoList) {
            aoIds.add(ao.getActivityOfferingId());
        }

        return getInstructorListByAoIds(aoIds, ContextUtils.createDefaultContextInfo());

    }

    private List<InstructorSearchResult> getInstructorsByActivityOfferingId(String activityOfferingId) throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException {
        List<String> aoIds = new ArrayList<String>();
        aoIds.add(activityOfferingId);
        return getInstructorListByAoIds(aoIds, ContextUtils.createDefaultContextInfo());
    }


    private List<ActivityTypeSearchResult> searchForActivityTypes(String courseOfferingId, String termId, String termCode, String courseCode) throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException {
        courseOfferingId = getCourseOfferingId(courseOfferingId, courseCode, termId, termCode);

        // get the FOs for the course offering. Note: FO's contain a list of activity offering type keys
        ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();
        List<FormatOfferingInfo> formatOfferings = CourseRegistrationAndScheduleOfClassesUtil.getCourseOfferingService().getFormatOfferingsByCourseOffering(courseOfferingId, contextInfo);

        return getActivityTypesForFormatOfferings(formatOfferings, contextInfo);
    }

    private List<ActivityTypeSearchResult> getActivityTypesForFormatOfferings(List<FormatOfferingInfo> formatOfferings, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException {

        List<ActivityTypeSearchResult> activitiesTypeKeys = new ArrayList<ActivityTypeSearchResult>();


        // for each FO
        for (FormatOfferingInfo formatOffering : formatOfferings) {
            List<String> aoTypeKeys = formatOffering.getActivityOfferingTypeKeys(); // grab the type keys
            List<TypeInfo> typeInfos = CourseRegistrationAndScheduleOfClassesUtil.getTypeService().getTypesByKeys(aoTypeKeys, contextInfo); // turn those keys into full type objects

            // for each type, build the search result
            for (TypeInfo activityTypeInfo : typeInfos) {
                ActivityTypeSearchResult atsr = new ActivityTypeSearchResult();
                atsr.setTypeKey(activityTypeInfo.getKey());
                atsr.setName(activityTypeInfo.getName());

                // I'm not sure what do do here. Guess we return the formatted over the plain?
                if (activityTypeInfo.getDescr() != null) {
                    if (activityTypeInfo.getDescr().getFormatted() != null) {
                        atsr.setDescription(activityTypeInfo.getDescr().getFormatted());
                    } else {
                        atsr.setDescription(activityTypeInfo.getDescr().getPlain());
                    }
                }
                // This prioirty comes from the configured lu (not lui) type attributes. Somewhat complex mapping
                // to get from the lu -> lui type key.
                atsr.setPriority(CourseRegistrationAndScheduleOfClassesUtil.getActivityPriorityMap(contextInfo).get(activityTypeInfo.getKey()));
                atsr.setFormatOfferingId(formatOffering.getId());  // Adding FoId to keep data structures flat.
                activitiesTypeKeys.add(atsr);
            }
        }

        return activitiesTypeKeys;

    }

    private List<RegGroupSearchResult> searchForRegistrationGroups(String courseOfferingId, String termId, String termCode, String courseCode, String regGroupName) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        courseOfferingId = getCourseOfferingId(courseOfferingId, courseCode, termId, termCode);
        return getRegGroupList(courseOfferingId, regGroupName);
    }

    private List<RegGroupSearchResult> getRegGroupList(String courseOfferingId, String regGroupName) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        List<RegGroupSearchResult> regGroupSearchResults = searchForRegGroups(courseOfferingId);

        // return a list with a single entity if regGroupName was provided
        if (!StringUtils.isEmpty(regGroupName)) {
            for (RegGroupSearchResult rg : regGroupSearchResults) {
                if (rg.getRegGroupName().equals(regGroupName)) {
                    List<RegGroupSearchResult> result = new ArrayList<RegGroupSearchResult>();
                    result.add(rg);
                    return result;
                }
            }
        }

        return new ArrayList<RegGroupSearchResult>(regGroupSearchResults);
    }

    private String getCourseOfferingId(String courseOfferingId, String courseCode, String termId, String termCode) throws MissingParameterException, PermissionDeniedException, OperationFailedException, InvalidParameterException {
        if (!StringUtils.isEmpty(courseOfferingId)) {
            return courseOfferingId;
        }

        termId = CourseRegistrationAndScheduleOfClassesUtil.getTermId(termId, termCode);
        List<String> coIds = searchForCourseOfferingIdByCourseCodeAndTerm(courseCode, termId);

        return KSCollectionUtils.getRequiredZeroElement(coIds);
    }

    /**
     * There is a business case where the user wants a list of primary activity offerings for a particular
     * course offering. Ie. CHEM 237 has a format offering of Lecture, Lab, Discussion. There are 6 lectures, 3 labs,
     * and three discussions.
     * <p/>
     * If the system is configured so Lecture is the highest priority, then this method would return a list of the
     * 6 activity offerings of type lecture.
     *
     * @param coId        Course Offering Id
     * @param contextInfo context of the call
     * @return A list of Primary Activity Offerings for a particular Course Offering
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws DoesNotExistException
     * @throws PermissionDeniedException
     * @throws OperationFailedException
     */
    private List<ActivityOfferingSearchResult> getPrimaryActivityOfferingsByCo(String coId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException {

        List<ActivityOfferingSearchResult> resultList = new ArrayList<ActivityOfferingSearchResult>();

        // get a list of format offerings for this particular co. ie: CHEM231 has 1 FO: Lecture / Discussion
        // there is a possibility that a course can offer multiple formats. ie. "Lecture / Discussion"  and "Lecture / Web Discussion"
        List<FormatOfferingInfo> formatOfferings = CourseRegistrationAndScheduleOfClassesUtil.getCourseOfferingService().getFormatOfferingsByCourseOffering(coId, contextInfo);

        // Because there can be multiple formats [Lec/lab, lab/disc] we need to seperate our results by the FoId. That is because if there are
        // multiple formats, the primary for one FO could be Lecture, while another FO could be Discussion.
        // Map<FoId, List<ActivityOfferings>>
        Map<String, List<ActivityOfferingSearchResult>> aoMap = groupActivityOfferingsByFormatOfferingId(searchForActivityOfferings(coId, null, null, null));

        for (FormatOfferingInfo formatOffering : formatOfferings) {
            CourseRegistrationAndScheduleOfClassesUtil.sortActivityOfferingTypeKeyList(formatOffering.getActivityOfferingTypeKeys(), contextInfo);  // sort the activity offerings type keys by priority order
            String primaryTypeKey = formatOffering.getActivityOfferingTypeKeys().get(0);

            for (ActivityOfferingSearchResult ao : aoMap.get(formatOffering.getId())) {
                if (primaryTypeKey.equals(ao.getActivityOfferingType())) {
                    resultList.add(ao);
                }
            }
        }
        return resultList;
    }

    /**
     * Because there can be multiple formats [Lec/lab, lab/disc] we need to seperate our results by the FoId. That is because if there are
     * multiple formats, the primary for one FO could be Lecture, while another FO could be Discussion.
     * <p/>
     * Note: Most, 98% of all courses only have one format, but this needs to be here for the rare case.
     *
     * @param aoList list of activity offerings
     * @return a mapping of format id to list of activity offerings
     */
    private Map<String, List<ActivityOfferingSearchResult>> groupActivityOfferingsByFormatOfferingId(List<ActivityOfferingSearchResult> aoList) {
        Map<String, List<ActivityOfferingSearchResult>> retMap = new HashMap<String, List<ActivityOfferingSearchResult>>();
        for (ActivityOfferingSearchResult ao : aoList) {
            if (!retMap.containsKey(ao.getFormatOfferingId())) {
                retMap.put(ao.getFormatOfferingId(), new ArrayList<ActivityOfferingSearchResult>());
            }
            retMap.get(ao.getFormatOfferingId()).add(ao);
        }
        return retMap;
    }

    //This is a helper method to get all terms in SOCs with a state of Published
    private List<AtpInfo> getValidAtps(ContextInfo contextInfo) {
        List<SocInfo> socs;
        List<String> termIds = new ArrayList<String>();
        List<AtpInfo> atps = new ArrayList<AtpInfo>();
        // Build a predicate to search for published Socs
        QueryByCriteria.Builder qBuilder = QueryByCriteria.Builder.create();
        qBuilder.setPredicates();
        Predicate pred = equal(CourseOfferingSetServiceConstants.SearchParameters.SOC_STATE, CourseOfferingSetServiceConstants.PUBLISHED_SOC_STATE_KEY);
        qBuilder.setPredicates(pred);
        try {
            socs = CourseRegistrationAndScheduleOfClassesUtil.getCourseOfferingSetService().searchForSocs(qBuilder.build(), contextInfo);
            if (socs != null && !socs.isEmpty()) {
                for (SocInfo soc : socs) {
                    // Add all published Soc termIds to termIds List
                    termIds.add(soc.getTermId());
                }
                // Use AtpService to get Term name by Id
                atps = CourseRegistrationAndScheduleOfClassesUtil.getAtpService().getAtpsByIds(termIds, contextInfo);
            } else {
                return atps;
            }
        } catch (Exception e) {
            throw new RuntimeException("Error getting Valid SOC Terms", e);
        }
        return atps;
    }

    private List<CourseSearchResult> searchForCourseOfferings(String termId, String termCode, String courseCode) throws InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException {
        termId = CourseRegistrationAndScheduleOfClassesUtil.getTermId(termId, termCode);
        List<CourseSearchResult> courseSearchResults = searchForCourseOfferings(termId, courseCode);

        return courseSearchResults;
    }

    private List<CourseAndPrimaryAOSearchResult> searchForCourseOfferingsAndPrimaryAosByTermAndCourse(String termId, String termCode, String courseCode) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {

        List<CourseAndPrimaryAOSearchResult> resultList = new ArrayList<CourseAndPrimaryAOSearchResult>();
        List<CourseSearchResult> courseOfferingList = searchForCourseOfferings(termId, termCode, courseCode); // search for the course offerings
        ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();  // build defualt context info

        if (courseOfferingList != null && !courseOfferingList.isEmpty()) {   // if we found course offerings
            for (CourseSearchResult csr : courseOfferingList) {
                CourseAndPrimaryAOSearchResult resultElement = new CourseAndPrimaryAOSearchResult(); // this is the element in the list we will return
                List<ActivityOfferingSearchResult> activityOfferingList = getPrimaryActivityOfferingsByCo(csr.getCourseOfferingId(), contextInfo);

                resultElement.setCourseOfferingInfo(csr);
                resultElement.setPrimaryActivityOfferingInfo(activityOfferingList);
                resultList.add(resultElement);
            }
        }
        return resultList;
    }

    private List<CourseSearchResult> searchForCourseOfferings(String termId, String courseCode) throws InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException {

        // make the course code case insensitive
        if (courseCode != null && !courseCode.isEmpty()) {
            courseCode = courseCode.toUpperCase();
        }

        SearchRequestInfo searchRequest = createSearchRequest(termId, courseCode);
        SearchResultInfo searchResult = CourseRegistrationAndScheduleOfClassesUtil.getSearchService().search(searchRequest, ContextUtils.createDefaultContextInfo());

        List<CourseSearchResult> results = new ArrayList<CourseSearchResult>();

        for (SearchResultRowInfo row : searchResult.getRows()) {
            CourseSearchResult courseSearchResult = new CourseSearchResult();

            for (SearchResultCellInfo cellInfo : row.getCells()) {

                String value = StringUtils.EMPTY;
                if (cellInfo.getValue() != null) {
                    value = cellInfo.getValue();
                }

                if (CourseOfferingManagementSearchImpl.SearchResultColumns.CODE.equals(cellInfo.getKey())) {
                    courseSearchResult.setCourseOfferingCode(value);
                } else if (CourseOfferingManagementSearchImpl.SearchResultColumns.DESC.equals(cellInfo.getKey())) {
                    courseSearchResult.setCourseOfferingDesc(value);
                } else if (CourseOfferingManagementSearchImpl.SearchResultColumns.GRADING_OPTION_NAME.equals(cellInfo.getKey())) {
                    courseSearchResult.setCourseOfferingGradingOptionDisplay(cellInfo.getValue());
                } else if (CourseOfferingManagementSearchImpl.SearchResultColumns.CREDIT_OPTION_NAME.equals(cellInfo.getKey())) {
                    courseSearchResult.setCourseOfferingCreditOptionDisplay(cellInfo.getValue());
                } else if (CourseOfferingManagementSearchImpl.SearchResultColumns.CO_ID.equals(cellInfo.getKey())) {
                    courseSearchResult.setCourseOfferingId(value);
                } else if (CourseOfferingManagementSearchImpl.SearchResultColumns.HAS_STUDENT_SELECTABLE_PASSFAIL.equals(cellInfo.getKey())) {
                    courseSearchResult.setStudentSelectablePassFail(BooleanUtils.toBoolean(value));
                } else if (CourseOfferingManagementSearchImpl.SearchResultColumns.CAN_AUDIT_COURSE.equals(cellInfo.getKey())) {
                    courseSearchResult.setAuditCourse(BooleanUtils.toBoolean(value));
                } else if (CourseOfferingManagementSearchImpl.SearchResultColumns.IS_HONORS_COURSE.equals(cellInfo.getKey())) {
                    courseSearchResult.setHonorsCourse(BooleanUtils.toBoolean(value));
                }

            }

            results.add(courseSearchResult);
        }

        return results;
    }

    private List<RegGroupSearchResult> searchForRegGroups(String courseOfferingId) throws InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException {

        SearchRequestInfo searchRequest = createRegGroupSearchRequest(courseOfferingId);
        SearchResultInfo searchResult = CourseRegistrationAndScheduleOfClassesUtil.getSearchService().search(searchRequest, ContextUtils.createDefaultContextInfo());

        Map<String, RegGroupSearchResult> regGroupResultMap = new HashMap<String, RegGroupSearchResult>();


        for (SearchResultRowInfo row : searchResult.getRows()) {


            String activityOfferingId = null;
            String regGroupId = null;
            String regGroupName = null;
            String regGroupState = null;
            String termId = null;

            for (SearchResultCellInfo cellInfo : row.getCells()) {

                String value = StringUtils.EMPTY;
                if (cellInfo.getValue() != null) {
                    value = cellInfo.getValue();
                }

                if (ActivityOfferingSearchServiceImpl.SearchResultColumns.AO_ID.equals(cellInfo.getKey())) {
                    activityOfferingId = value;
                } else if (ActivityOfferingSearchServiceImpl.SearchResultColumns.RG_ID.equals(cellInfo.getKey())) {
                    regGroupId = value;
                } else if (ActivityOfferingSearchServiceImpl.SearchResultColumns.RG_NAME.equals(cellInfo.getKey())) {
                    regGroupName = value;
                } else if (ActivityOfferingSearchServiceImpl.SearchResultColumns.RG_STATE.equals(cellInfo.getKey())) {
                    regGroupState = value;
                }else if (ActivityOfferingSearchServiceImpl.SearchResultColumns.ATP_ID.equals(cellInfo.getKey())) {
                    termId = value;
                }

            }

            if (regGroupResultMap.containsKey(regGroupId)) {
                regGroupResultMap.get(regGroupId).getActivityOfferingIds().add(activityOfferingId);
            } else {
                RegGroupSearchResult regGroupSearchResult = new RegGroupSearchResult();
                regGroupSearchResult.setCourseOfferingId(courseOfferingId);
                regGroupSearchResult.setRegGroupId(regGroupId);
                regGroupSearchResult.setRegGroupName(regGroupName);
                regGroupSearchResult.setRegGroupState(regGroupState);
                regGroupSearchResult.setTermId(termId);
                regGroupSearchResult.getActivityOfferingIds().add(activityOfferingId);
                regGroupResultMap.put(regGroupId, regGroupSearchResult);

            }


        }

        List<RegGroupSearchResult> resultList = new ArrayList<RegGroupSearchResult>(regGroupResultMap.values());


        Collections.sort(resultList, regResultComparator);
        return resultList;
    }

    /**
     * Searches for a raw ( schedules, and instructors aren't pulled) of activities.
     *
     * @param courseOfferingId id of the course containing the activities you are searching for
     * @return list of activity offering search results
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws PermissionDeniedException
     * @throws OperationFailedException
     */
    private List<ActivityOfferingSearchResult> searchForRawActivities(String courseOfferingId) throws InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException {
        SearchRequestInfo searchRequestInfo = new SearchRequestInfo(ActivityOfferingSearchServiceImpl.AOS_AND_CLUSTERS_BY_CO_ID_SEARCH_KEY);

        List<String> aoStates = new ArrayList<String>(1);
        aoStates.add(LuiServiceConstants.LUI_AO_STATE_OFFERED_KEY);

        searchRequestInfo.addParam(ActivityOfferingSearchServiceImpl.SearchParameters.CO_ID, courseOfferingId);
        searchRequestInfo.addParam(ActivityOfferingSearchServiceImpl.SearchParameters.AO_STATES, aoStates);
        SearchResultInfo searchResult = CourseRegistrationAndScheduleOfClassesUtil.getSearchService().search(searchRequestInfo, ContextUtils.createDefaultContextInfo());

        List<ActivityOfferingSearchResult> resultList = new ArrayList<ActivityOfferingSearchResult>();


        for (SearchResultRowInfo row : searchResult.getRows()) {

            String formatOfferingId = null;
            String formatOfferingName = null;
            String formatId = null;
            String activityOfferingClusterId = null;
            String activityOfferingClusterName = null;
            String activityOfferingClusterPrivateName = null;
            String activityOfferingId = null;
            String activityOfferingCode = null;
            String activityOfferingType = null;
            String activityOfferingState = null;
            String activityOfferingMaxSeats = null;
            String scheduleId = null;
            String atpId = null;

            ActivityOfferingSearchResult result = new ActivityOfferingSearchResult();

            for (SearchResultCellInfo cellInfo : row.getCells()) {

                String value = StringUtils.EMPTY;
                if (cellInfo.getValue() != null) {
                    value = cellInfo.getValue();
                }

                if (ActivityOfferingSearchServiceImpl.SearchResultColumns.FO_ID.equals(cellInfo.getKey())) {
                    formatOfferingId = value;
                } else if (ActivityOfferingSearchServiceImpl.SearchResultColumns.FO_NAME.equals(cellInfo.getKey())) {
                    formatOfferingName = value;
                } else if (ActivityOfferingSearchServiceImpl.SearchResultColumns.FORMAT_ID.equals(cellInfo.getKey())) {
                    formatId = value;
                } else if (ActivityOfferingSearchServiceImpl.SearchResultColumns.AOC_ID.equals(cellInfo.getKey())) {
                    activityOfferingClusterId = value;
                } else if (ActivityOfferingSearchServiceImpl.SearchResultColumns.AOC_NAME.equals(cellInfo.getKey())) {
                    activityOfferingClusterName = value;
                } else if (ActivityOfferingSearchServiceImpl.SearchResultColumns.AOC_PRIVATE_NAME.equals(cellInfo.getKey())) {
                    activityOfferingClusterPrivateName = value;
                } else if (ActivityOfferingSearchServiceImpl.SearchResultColumns.AO_ID.equals(cellInfo.getKey())) {
                    activityOfferingId = value;
                } else if (ActivityOfferingSearchServiceImpl.SearchResultColumns.AO_CODE.equals(cellInfo.getKey())) {
                    activityOfferingCode = value;
                } else if (ActivityOfferingSearchServiceImpl.SearchResultColumns.AO_TYPE.equals(cellInfo.getKey())) {
                    activityOfferingType = value;
                } else if (ActivityOfferingSearchServiceImpl.SearchResultColumns.AO_STATE.equals(cellInfo.getKey())) {
                    activityOfferingState = value;
                } else if (ActivityOfferingSearchServiceImpl.SearchResultColumns.AO_MAX_SEATS.equals(cellInfo.getKey())) {
                    activityOfferingMaxSeats = value;
                } else if (ActivityOfferingSearchServiceImpl.SearchResultColumns.SCHEDULE_ID.equals(cellInfo.getKey())) {
                    scheduleId = value;
                } else if (ActivityOfferingSearchServiceImpl.SearchResultColumns.ATP_ID.equals(cellInfo.getKey())) {
                    atpId = value;
                }
            }

            result.setFormatId(formatId);
            result.setFormatOfferingId(formatOfferingId);
            result.setFormatOfferingName(formatOfferingName);
            result.setActivityOfferingClusterId(activityOfferingClusterId);
            result.setActivityOfferingClusterName(activityOfferingClusterName);
            result.setActivityOfferingClusterPrivateName(activityOfferingClusterPrivateName);
            result.setActivityOfferingClusterId(activityOfferingClusterId);
            result.setActivityOfferingCode(activityOfferingCode);
            result.setActivityOfferingType(activityOfferingType);
            result.setActivityOfferingId(activityOfferingId);
            result.setActivityOfferingState(activityOfferingState);
            result.setActivityOfferingMaxSeats(activityOfferingMaxSeats);
            result.setScheduleId(scheduleId);
            result.setAtpId(atpId);
            resultList.add(result);

        }
        return resultList;

    }

    private List<String> searchForCourseOfferingIdByCourseCodeAndTerm(String courseCode, String atpId) throws InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException {
        List<String> resultList = new ArrayList<String>();


        SearchRequestInfo searchRequestInfo = new SearchRequestInfo(CourseOfferingManagementSearchImpl.COID_BY_TERM_AND_COURSE_CODE_SEARCH_SEARCH_KEY);

        searchRequestInfo.addParam(CourseOfferingManagementSearchImpl.SearchParameters.COURSE_CODE, courseCode);
        searchRequestInfo.addParam(CourseOfferingManagementSearchImpl.SearchParameters.ATP_ID, atpId);

        SearchResultInfo searchResult = CourseRegistrationAndScheduleOfClassesUtil.getSearchService().search(searchRequestInfo, ContextUtils.createDefaultContextInfo());


        for (SearchResultRowInfo row : searchResult.getRows()) {
            for (SearchResultCellInfo cellInfo : row.getCells()) {
                String value = StringUtils.EMPTY;
                if (cellInfo.getValue() != null) {
                    value = cellInfo.getValue();
                }
                if (CourseOfferingManagementSearchImpl.SearchResultColumns.CO_ID.equals(cellInfo.getKey())) {
                    resultList.add(value);
                }
            }
        }

        return resultList;
    }

    /**
     * This is an internal method that will return a map of scheduleId, ScheduleSearchResult. We are using a map object
     * so it is easier to build up complex objects in a more performant way. ie. If you're building a list of complex
     * ActivityOffering display objects and you want that object to contain schedule information. you can build this
     * list of schedules THEN as your building your list of ActivityObjects you can easily add a schedule object.
     *
     * @param scheduleIds list of schedule Ids to retrieve from db
     * @param contextInfo context of the call
     * @return a mapping of schedule id to a schedule result
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws PermissionDeniedException
     * @throws OperationFailedException
     */
    protected Map<String, ScheduleSearchResult> searchForScheduleByScheduleIds(List<String> scheduleIds, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException {
        Map<String, ScheduleSearchResult> resultList = new HashMap<String, ScheduleSearchResult>();

        SearchRequestInfo sr = new SearchRequestInfo(CoreSearchServiceImpl.SCH_AND_ROOM_SEARH_BY_ID_SEARCH_KEY);
        sr.addParam(CoreSearchServiceImpl.SearchParameters.SCHEDULE_IDS, new ArrayList<String>(scheduleIds));
        SearchResultInfo searchResult = CourseRegistrationAndScheduleOfClassesUtil.getSearchService().search(sr, contextInfo);

        for (SearchResultRowInfo row : searchResult.getRows()) {
            ScheduleSearchResult searchResultRow = new ScheduleSearchResult();
            for (SearchResultCellInfo cell : row.getCells()) {
                String value = StringUtils.EMPTY;
                if (cell.getValue() != null) {
                    value = cell.getValue();
                }
                if (CoreSearchServiceImpl.SearchResultColumns.SCH_ID.equals(cell.getKey())) {
                    searchResultRow.setScheduleId(value);
                } else if (CoreSearchServiceImpl.SearchResultColumns.START_TIME.equals(cell.getKey())) {
                    searchResultRow.setStartTimeMili(value);
                    if (value != null && !value.isEmpty()) {
                        TimeOfDayInfo tod = TimeOfDayHelper.setMillis(Long.parseLong(value));
                        searchResultRow.setStartTimeDisplay(convertTimeOfDayToDisplayTime(tod));
                    }
                } else if (CoreSearchServiceImpl.SearchResultColumns.END_TIME.equals(cell.getKey())) {
                    searchResultRow.setEndTimeMili(value);
                    if (value != null && !value.isEmpty()) {
                        TimeOfDayInfo tod = TimeOfDayHelper.setMillis(Long.parseLong(value));
                        searchResultRow.setEndTimeDisplay(convertTimeOfDayToDisplayTime(tod));
                    }
                } else if (CoreSearchServiceImpl.SearchResultColumns.TBA_IND.equals(cell.getKey())) {
                    searchResultRow.setTba(Boolean.parseBoolean(value));
                } else if (CoreSearchServiceImpl.SearchResultColumns.ROOM_CODE.equals(cell.getKey())) {
                    searchResultRow.setRoomName(value);
                } else if (CoreSearchServiceImpl.SearchResultColumns.BLDG_NAME.equals(cell.getKey())) {
                    searchResultRow.setBuildingName(value);
                } else if (CoreSearchServiceImpl.SearchResultColumns.BLDG_CODE.equals(cell.getKey())) {
                    searchResultRow.setBuildingCode(value);
                } else if (CoreSearchServiceImpl.SearchResultColumns.WEEKDAYS.equals(cell.getKey())) {
                    searchResultRow.setDays(value);
                } else if (CoreSearchServiceImpl.SearchResultColumns.SCH_ID.equals(cell.getKey())) {
                    searchResultRow.setScheduleId(value);
                }
            }
            resultList.put(searchResultRow.getScheduleId(), searchResultRow);
        }

        return resultList;
    }

    /**
     * Most of our public instructor methods take a list of AO IDS and want a list of instructors. Our interal instructor
     * processing returns a map. So, to make things easy we're providing a way to bypass the fact a map is used.
     *
     * @param aoIds list of activity offering ids
     * @return list of instructor results
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws DoesNotExistException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    private List<InstructorSearchResult> getInstructorListByAoIds(List<String> aoIds, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, DoesNotExistException, OperationFailedException, PermissionDeniedException {
        List<InstructorSearchResult> resultList = new ArrayList<InstructorSearchResult>();
        Map<String, List<InstructorSearchResult>> resultMap = CourseRegistrationAndScheduleOfClassesUtil.searchForInstructorsByAoIds(aoIds, contextInfo);

        if (resultMap != null && !resultMap.isEmpty()) {
            for (List<InstructorSearchResult> insrList : resultMap.values()) {
                resultList.addAll(insrList);
            }
        }
        return resultList;

    }

    /**
     * Since schedule data must be pulled in a separate query, it's best if we populate the AOSearchResult
     * in this method. We could do all of this in the the raw activity search, but we have found cases
     * where the additional schedule search is not desired.
     *
     * @param aoList      list of ActivityOfferingSearchResults that will be modified to include schedule data.
     * @param contextInfo the context of the call
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    private void populateActivityOfferingsWithScheduleData(List<ActivityOfferingSearchResult> aoList, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String> scheduleIds = new ArrayList<String>();

        for (ActivityOfferingSearchResult ao : aoList) {
            String scheduleId = ao.getScheduleId();

            if (!StringUtils.isEmpty(scheduleId)) {
                scheduleIds.add(scheduleId);
            }
        }

        Map<String, ScheduleSearchResult> schedMap = searchForScheduleByScheduleIds(scheduleIds, contextInfo);

        if (schedMap != null && !schedMap.isEmpty()) {
            for (ActivityOfferingSearchResult ao : aoList) {
                String scheduleId = ao.getScheduleId();
                if (!StringUtils.isEmpty(scheduleId) && schedMap.containsKey(scheduleId)) {
                    ao.setSchedule(convertScheduleSearchResultToAOSchedComponent(schedMap.get(scheduleId)));
                }
            }

        }
    }

    private ActivityOfferingScheduleComponentResult convertScheduleSearchResultToAOSchedComponent(ScheduleSearchResult scheduleSearchResult) {
        ActivityOfferingScheduleComponentResult ret = new ActivityOfferingScheduleComponentResult();
        if (scheduleSearchResult != null) {
            ret.setBuildingCode(scheduleSearchResult.getBuildingCode());
            ret.setEndTime(scheduleSearchResult.getEndTimeDisplay());
            ret.setStartTime(scheduleSearchResult.getStartTimeDisplay());
            ret.setRoomCode(scheduleSearchResult.getRoomName());
            ret.setBooleanSchedules(scheduleSearchResult.getDays());
        }
        return ret;
    }

    /**
     * Since schedule data must be pulled in a separate query, it's best if we populate the AOSearchResult
     * in this method. We could do all of this in the the raw activity search, but we have found cases
     * where the additional schedule search is not desired.
     *
     * @param aoList list of ActivityOfferingSearchResults that will be modified to include schedule data.
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws DoesNotExistException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    private void populateActivityOfferingsWithInstructorData(List<ActivityOfferingSearchResult> aoList, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, DoesNotExistException, OperationFailedException, PermissionDeniedException {
        List<String> aoIds = new ArrayList<String>();

        for (ActivityOfferingSearchResult ao : aoList) {
            String aoId = ao.getActivityOfferingId();
            if (!StringUtils.isEmpty(aoId)) {
                aoIds.add(aoId);
            }
        }

        Map<String, List<InstructorSearchResult>> instMap = CourseRegistrationAndScheduleOfClassesUtil.searchForInstructorsByAoIds(aoIds, contextInfo);

        if (instMap != null && !instMap.isEmpty()) {
            for (ActivityOfferingSearchResult ao : aoList) {
                String aoId = ao.getActivityOfferingId();
                if (!StringUtils.isEmpty(aoId) && instMap.containsKey(aoId)) {
                    ao.setInstructors(instMap.get(aoId));
                }
            }
        }
    }

    private void populateActivityOfferingsWithActivityTypeNames(List<ActivityOfferingSearchResult> aoList, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException {
        for (ActivityOfferingSearchResult ao : aoList) {
            String aoTypeKey = ao.getActivityOfferingType();
            if (!StringUtils.isEmpty(aoTypeKey)) {
                String aoTypeName = CourseRegistrationAndScheduleOfClassesUtil.getTypeService().getType(aoTypeKey, contextInfo).getName();
                ao.setActivityOfferingTypeName(aoTypeName);
            }
        }
    }

    /**
     * We're making this a protected method so that implementing institutions can extend this class and change the way
     * that times are displayed.
     *
     * @param tod Tune Of Day object
     * @return String result of the time of day conversion
     */
    protected String convertTimeOfDayToDisplayTime(TimeOfDayInfo tod) {
        String sRet = "";
        if (tod != null) {
            sRet = TimeOfDayHelper.makeFormattedTimeForAOSchedules(tod);
        }
        return sRet;

    }

    private static class RegResultComparator implements Comparator<RegGroupSearchResult>, Serializable {
        // Note: this comparator imposes orderings that are inconsistent with equals.
        @Override
        public int compare(RegGroupSearchResult a, RegGroupSearchResult b) {
            return a.getRegGroupName().compareToIgnoreCase(b.getRegGroupName());
        }
    }

    private SearchRequestInfo createRegGroupSearchRequest(String courseOfferingId) {
        SearchRequestInfo searchRequest = new SearchRequestInfo(ActivityOfferingSearchServiceImpl.REG_GROUPS_BY_CO_ID_SEARCH_KEY);

        //List<String> filterCOStates = new ArrayList<String>(1);
        //filterCOStates.add(LuiServiceConstants.LUI_CO_STATE_OFFERED_KEY);
        searchRequest.addParam(ActivityOfferingSearchServiceImpl.SearchParameters.CO_ID, courseOfferingId);

        return searchRequest;
    }

    private List<ActivityOfferingSearchResult> searchForActivityOfferings(String courseOfferingId, String termId, String termCode, String courseCode) throws MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException, InvalidParameterException {
        courseOfferingId = getCourseOfferingId(courseOfferingId, courseCode, termId, termCode);
        List<ActivityOfferingSearchResult> activityOfferingSearchResults = loadPopulatedActivityOfferingsByCourseOfferingId(courseOfferingId, ContextUtils.createDefaultContextInfo());

        return activityOfferingSearchResults;
    }

    /**
     * This is the method that should be called when you want a FULLY populated ActivityOfferingSearchResult object.
     * That means that the activityOfferingSearchResult objects will be populated with schedule, and instructor data.
     *
     * @param courseOfferingId id of the course offering you are searching on
     * @param contextInfo      context of the call
     * @return list of activity offering results attached to the course offering iid
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     * @throws DoesNotExistException
     * @throws InvalidParameterException
     */
    private List<ActivityOfferingSearchResult> loadPopulatedActivityOfferingsByCourseOfferingId(String courseOfferingId, ContextInfo contextInfo) throws MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, InvalidParameterException {
        List<ActivityOfferingSearchResult> retList = searchForRawActivities(courseOfferingId);
        populateActivityOfferingsWithScheduleData(retList, contextInfo);
        populateActivityOfferingsWithInstructorData(retList, contextInfo);
        populateActivityOfferingsWithActivityTypeNames(retList, contextInfo);
        return retList;
    }

    private SearchRequestInfo createSearchRequest(String termId, String courseCode) {
        SearchRequestInfo searchRequest = new SearchRequestInfo(CourseOfferingManagementSearchImpl.CO_MANAGEMENT_SEARCH.getKey());

        List<String> filterCOStates = new ArrayList<String>(1);
        filterCOStates.add(LuiServiceConstants.LUI_CO_STATE_OFFERED_KEY);
        searchRequest.addParam(CourseOfferingManagementSearchImpl.SearchParameters.COURSE_CODE, courseCode);
        searchRequest.addParam(CourseOfferingManagementSearchImpl.SearchParameters.FILTER_CO_STATES, filterCOStates);
        searchRequest.addParam(CourseOfferingManagementSearchImpl.SearchParameters.ATP_ID, termId);
        searchRequest.addParam(CourseOfferingManagementSearchImpl.SearchParameters.CROSS_LIST_SEARCH_ENABLED, BooleanUtils.toStringTrueFalse(false));
        searchRequest.addParam(CourseOfferingManagementSearchImpl.SearchParameters.IS_EXACT_MATCH_CO_CODE_SEARCH, BooleanUtils.toStringTrueFalse(false));
        searchRequest.addParam(CourseOfferingManagementSearchImpl.SearchParameters.INCLUDE_PASSFAIL_AUDIT_HONORS_RESULTS, BooleanUtils.toStringTrueFalse(true));
        return searchRequest;
    }
}
