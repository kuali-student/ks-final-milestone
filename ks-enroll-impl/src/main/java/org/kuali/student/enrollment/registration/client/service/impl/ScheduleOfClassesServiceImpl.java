package org.kuali.student.enrollment.registration.client.service.impl;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.kuali.rice.core.api.criteria.Predicate;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.common.collection.KSCollectionUtils;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.infc.CourseOffering;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.enrollment.registration.client.service.ScheduleOfClassesService;
import org.kuali.student.enrollment.registration.client.service.dto.*;
import org.kuali.student.enrollment.registration.client.service.impl.util.CourseRegistrationAndScheduleOfClassesUtil;
import org.kuali.student.enrollment.registration.client.service.impl.util.StaticUserDateUtil;
import org.kuali.student.enrollment.registration.search.service.impl.CourseRegistrationSearchServiceImpl;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.TimeOfDayInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.common.infc.ValidationResult;
import org.kuali.student.r2.common.util.TimeOfDayHelper;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.class1.search.ActivityOfferingSearchServiceImpl;
import org.kuali.student.r2.core.class1.search.CoreSearchServiceImpl;
import org.kuali.student.r2.core.class1.search.CourseOfferingManagementSearchImpl;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.scheduling.dto.TimeSlotInfo;
import org.kuali.student.r2.core.scheduling.model.TimeSlotEntity;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.*;

import static org.kuali.rice.core.api.criteria.PredicateFactory.equal;

public class ScheduleOfClassesServiceImpl implements ScheduleOfClassesService {

    public static final Logger LOGGER = LoggerFactory.getLogger(ScheduleOfClassesServiceImpl.class);

    private static final Comparator<RegGroupSearchResult> REG_RESULT_COMPARATOR = new RegResultComparator();

    private EntityManager entityManager;



    /**
     * COURSE OFFERINGS *
     */

    @Override
    public List<CourseSearchResult> searchForCourseOfferingsByTermIdAndCourse(String termId, String courseCode) throws InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException {
        return searchForCourseOfferingsByCourseLocal(termId, null, courseCode);
    }

    @Override
    public List<CourseSearchResult> searchForCourseOfferingsByTermCodeAndCourse(String termCode, String courseCode) throws InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException {
        return searchForCourseOfferingsByCourseLocal(null, termCode, courseCode);
    }

    /**
     * REGISTRATION GROUPS *
     */

    @Override
    public RegGroupSearchResult searchForRegistrationGroupByTermAndCourseAndRegGroup(String termId, String termCode, String courseCode, String regGroupCode) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<RegGroupSearchResult> regGroupSearchResults =  searchForRegistrationGroupsLocal(null, termId, termCode, courseCode, regGroupCode);
        if (regGroupSearchResults != null && !regGroupSearchResults.isEmpty()) {
           return  KSCollectionUtils.getRequiredZeroElement(regGroupSearchResults);
        }  else {
            return null;
        }
    }

    /**
     * TERMS *
     */

    @Override
    public String getTermIdByTermCode(String termCode) throws InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException {
        return KSCollectionUtils.getRequiredZeroElement(CourseRegistrationAndScheduleOfClassesUtil.getTermsByTermCode(termCode)).getTermId();
    }


    /**
     * PROTECTED HELPERS *
     */

    protected EligibilityCheckResult checkStudentEligibilityForTermLocal(String termId) throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException, DoesNotExistException {
        ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();

        /*
         * If we are configured to use static dates for registration date testing, get the date for this user
         * (if it exists) and set it in the context.
         */
        DateTime staticDate = StaticUserDateUtil.getDateTimeForUser(contextInfo.getPrincipalId());
        if (staticDate != null) {
            contextInfo.setCurrentDate(staticDate.toDate());
        }

        List<ValidationResultInfo> validationResults = CourseRegistrationAndScheduleOfClassesUtil.getCourseRegistrationService()
                .checkStudentEligibilityForTerm(contextInfo.getPrincipalId(), termId, contextInfo);

        // Filter out anything that isn't an error
        List<ValidationResultInfo> reasons = new ArrayList<ValidationResultInfo>();
        for (ValidationResultInfo vr : validationResults) {
            if (ValidationResult.ErrorLevel.ERROR.equals(vr.getLevel())) {
                reasons.add(vr);
            }
        }

        EligibilityCheckResult result = new EligibilityCheckResult(reasons);
        result.setIsEligible(result.getReasons().isEmpty()); // The check passes if there are no errors
        result.setUserId(contextInfo.getPrincipalId());

        return result;
    }

    protected List<TermSearchResult> searchForTermsLocal(String termCode, boolean isActiveTerms) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        if (!StringUtils.isEmpty(termCode)) {
            return CourseRegistrationAndScheduleOfClassesUtil.getTermsByTermCode(termCode);
        }

        if (isActiveTerms) {
            return getActiveTerms();
        }

        return getAllTerms();
    }

    protected List<InstructorSearchResult> searchForInstructorsLocal(String courseOfferingId, String activityOfferingId, String termId, String termCode, String courseCode) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {

        List<InstructorSearchResult> instructors = null;

        if (!StringUtils.isEmpty(activityOfferingId)) {
            instructors =  getInstructorsByActivityOfferingId(activityOfferingId);
        }  else {

            courseOfferingId = getCourseOfferingId(courseOfferingId, courseCode, termId, termCode);

            if(!StringUtils.isEmpty(courseOfferingId)){
                instructors =  getInstructorsByCourseOfferingId(courseOfferingId);
            }
        }

        return emptyIfNull(instructors);
    }

    protected CourseOfferingInfoSearchResult searchForCourseOfferingInfoLocal(String courseOfferingId) throws MissingParameterException, InvalidParameterException, OperationFailedException, PermissionDeniedException {
        CourseOfferingInfoSearchResult courseOfferingInfo = searchForCourseOfferingInfo(courseOfferingId);

        return courseOfferingInfo;
    }

    protected List<ActivityTypeSearchResult> searchForActivityTypesLocal(String courseOfferingId, String termId, String termCode, String courseCode) throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException {
        courseOfferingId = getCourseOfferingId(courseOfferingId, courseCode, termId, termCode);

        // get the FOs for the course offering. Note: FO's contain a list of activity offering type keys
        ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();
        List<FormatOfferingInfo> formatOfferings;
        if(!StringUtils.isEmpty(courseOfferingId)){
            formatOfferings = CourseRegistrationAndScheduleOfClassesUtil.getCourseOfferingService().getFormatOfferingsByCourseOffering(courseOfferingId, contextInfo);
        } else {
            formatOfferings=new ArrayList<FormatOfferingInfo>();
        }

        return getActivityTypesForFormatOfferings(formatOfferings, contextInfo);
    }

    protected List<RegGroupSearchResult> searchForRegistrationGroupsLocal(String courseOfferingId, String termId, String termCode, String courseCode, String regGroupName) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        courseOfferingId = getCourseOfferingId(courseOfferingId, courseCode, termId, termCode);
        List<RegGroupSearchResult> retList = null;

        if(!StringUtils.isEmpty(courseOfferingId)){
            retList =  getRegGroupList(courseOfferingId, regGroupName);
        }

        // We want to add registration counts to the results
        if(retList != null && !retList.isEmpty()){
            List<String> rgIds = new ArrayList<String>();
            Map<String, RegGroupSearchResult> tmpMap = new HashMap<String, RegGroupSearchResult>(); // used for performance
            for(RegGroupSearchResult sr : retList){
                rgIds.add(sr.getRegGroupId()); // build list of ids to pass into search
                tmpMap.put(sr.getRegGroupId(), sr); // create mapping so we can update in O(n) time
            }

            List<RegistrationCountResult> countResults = getRegGroupCounts(rgIds); // perform search

            for(RegistrationCountResult rgCount : countResults){ // for each rg, update corresponding map value
                tmpMap.get(rgCount.getLuiId()).getRegistrationCounts().add(rgCount);
            }
        }

        return emptyIfNull(retList);

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

    protected List<CourseSearchResult> searchForCourseOfferingsByCourseLocal(String termId, String termCode, String courseCode) throws InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException {
        termId = CourseRegistrationAndScheduleOfClassesUtil.getTermId(termId, termCode);
        SearchRequestInfo searchRequest = createSearchRequest(termId, courseCode, null);

        return searchForCourseOfferings(searchRequest);
    }

    protected List<CourseSearchResult> searchForCourseOfferingsByCriteriaLocal(String termId, String termCode, String criteria) throws InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException {
        termId = CourseRegistrationAndScheduleOfClassesUtil.getTermId(termId, termCode);
        SearchRequestInfo searchRequest = createSearchRequest(termId, null, criteria);

        return searchForCourseOfferings(searchRequest);
    }

    protected List<CourseAndPrimaryAOSearchResult> searchForCourseOfferingsAndPrimaryAosByTermAndCourseLocal(String termId, String termCode, String courseCode) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {

        List<CourseAndPrimaryAOSearchResult> resultList = new ArrayList<CourseAndPrimaryAOSearchResult>();
        List<CourseSearchResult> courseOfferingList = searchForCourseOfferingsByCourseLocal(termId, termCode, courseCode); // search for the course offerings
        ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();  // build default context info

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

    protected List<ActivityOfferingSearchResult> searchForActivityOfferingsLocal(String courseOfferingId, String termId, String termCode, String courseCode) throws MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException, InvalidParameterException {
        List<ActivityOfferingSearchResult> activityOfferingSearchResults = null;
        courseOfferingId = getCourseOfferingId(courseOfferingId, courseCode, termId, termCode);
        if(!StringUtils.isEmpty(courseOfferingId)){
            activityOfferingSearchResults = loadPopulatedActivityOfferingsByCourseOfferingId(courseOfferingId, ContextUtils.createDefaultContextInfo());
        }

        return emptyIfNull(activityOfferingSearchResults);
    }

    /**
     * PRIVATE HELPERS *
     */

    private List<TermSearchResult> getActiveTerms() {
        List<AtpInfo> validAtps = getValidAtps(ContextUtils.createDefaultContextInfo());
        return CourseRegistrationAndScheduleOfClassesUtil.getTermSearchResultsFromAtpInfos(validAtps);
    }

    private List<TermSearchResult> getAllTerms() throws InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException {
        QueryByCriteria criteria = QueryByCriteria.Builder.create().build();
        return CourseRegistrationAndScheduleOfClassesUtil.getTermSearchResultsFromAtpInfos(CourseRegistrationAndScheduleOfClassesUtil.getAtpService().searchForAtps(criteria, ContextUtils.createDefaultContextInfo()));
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

    private List<RegistrationCountResult>  getRegGroupCounts(List<String> regGroupIds) throws MissingParameterException, InvalidParameterException, OperationFailedException, PermissionDeniedException {

        List<RegistrationCountResult> retList = new ArrayList<RegistrationCountResult>();
        SearchRequestInfo searchRequest = new SearchRequestInfo(CourseRegistrationSearchServiceImpl.SEAT_COUNT_INFO_BY_REG_GROUPS_SEARCH_KEY);
        searchRequest.addParam(CourseRegistrationSearchServiceImpl.SearchParameters.LUI_IDS, regGroupIds);

        SearchResultInfo searchResult = CourseRegistrationAndScheduleOfClassesUtil.getSearchService().search(searchRequest, ContextUtils.createDefaultContextInfo());

        for (SearchResultRowInfo row : searchResult.getRows()) {


            int seatCount = 0;
            String luiId = null;
            String lprType = null;

            for (SearchResultCellInfo cellInfo : row.getCells()) {

                String value = StringUtils.EMPTY;
                if (cellInfo.getValue() != null) {
                    value = cellInfo.getValue();
                }

                if (CourseRegistrationSearchServiceImpl.SearchResultColumns.SEAT_COUNT.equals(cellInfo.getKey())) {
                    seatCount = Integer.parseInt(value);
                } else if (CourseRegistrationSearchServiceImpl.SearchResultColumns.LUI_ID.equals(cellInfo.getKey())) {
                    luiId = value;
                } else if (CourseRegistrationSearchServiceImpl.SearchResultColumns.LPR_TYPE.equals(cellInfo.getKey())) {
                    lprType = value;
                }
            }
            retList.add(new RegistrationCountResult(luiId, seatCount, lprType));
        }
        return retList;
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
            return new ArrayList<RegGroupSearchResult>();
        }

        return new ArrayList<RegGroupSearchResult>(regGroupSearchResults);
    }

    private String getCourseOfferingId(String courseOfferingId, String courseCode, String termId, String termCode) throws MissingParameterException, PermissionDeniedException, OperationFailedException, InvalidParameterException {
        if (!StringUtils.isEmpty(courseOfferingId)) {
            return courseOfferingId;
        }

        termId = CourseRegistrationAndScheduleOfClassesUtil.getTermId(termId, termCode);
        List<String> coIds = searchForCourseOfferingIdByCourseCodeAndTerm(courseCode, termId);

        return getSingleItemFromListOrNull(coIds);
    }

    private <T> T getSingleItemFromListOrNull(List<T> items) throws OperationFailedException {
        if(items != null && !items.isEmpty()){
            return KSCollectionUtils.getRequiredZeroElement(items);
        } else {
            return null;
        }

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
        Map<String, List<ActivityOfferingSearchResult>> aoMap = groupActivityOfferingsByFormatOfferingId(searchForActivityOfferingsLocal(coId, null, null, null));

        for (FormatOfferingInfo formatOffering : formatOfferings) {
            CourseRegistrationAndScheduleOfClassesUtil.sortActivityOfferingTypeKeyList(formatOffering.getActivityOfferingTypeKeys(), contextInfo);  // sort the activity offerings type keys by priority order
            String primaryTypeKey = formatOffering.getActivityOfferingTypeKeys().get(0);
            List<ActivityOfferingSearchResult> aos = aoMap.get(formatOffering.getId());
            if (aos != null) {
                for (ActivityOfferingSearchResult ao : aos) {
                    if (primaryTypeKey.equals(ao.getActivityOfferingType())) {
                        resultList.add(ao);
                    }
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
            }
        } catch (Exception e) {
            throw new RuntimeException("Error getting Valid SOC Terms", e);
        }
        return atps;
    }

    private List<CourseSearchResult> searchForCourseOfferings (SearchRequestInfo searchRequest) throws InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException {
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

    private CourseOfferingInfoSearchResult searchForCourseOfferingInfo(String courseOfferingId) throws InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException {
        SearchRequestInfo searchRequest = new SearchRequestInfo(CourseRegistrationSearchServiceImpl.CO_AND_AO_INFO_BY_CO_ID_SEARCH_TYPE.getKey());

        searchRequest.addParam(CourseOfferingManagementSearchImpl.SearchParameters.CO_ID, courseOfferingId);

        SearchResultInfo searchResult = CourseRegistrationAndScheduleOfClassesUtil.getSearchService().search(searchRequest, ContextUtils.createDefaultContextInfo());

        List<CourseOfferingInfoSearchResult> results = new ArrayList<CourseOfferingInfoSearchResult>();

        for (SearchResultRowInfo row : searchResult.getRows()) {
            CourseOfferingInfoSearchResult courseSearchResult = new CourseOfferingInfoSearchResult();

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
//                    courseSearchResult.setCourseOfferingGradingOptionDisplay(cellInfo.getValue());
                } else if (CourseOfferingManagementSearchImpl.SearchResultColumns.CREDIT_OPTION_NAME.equals(cellInfo.getKey())) {
//                    courseSearchResult.setCourseOfferingCreditOptionDisplay(cellInfo.getValue());
                } else if (CourseOfferingManagementSearchImpl.SearchResultColumns.CO_ID.equals(cellInfo.getKey())) {
                    courseSearchResult.setCourseOfferingId(value);
                } else if (CourseOfferingManagementSearchImpl.SearchResultColumns.HAS_STUDENT_SELECTABLE_PASSFAIL.equals(cellInfo.getKey())) {
//                    courseSearchResult.setStudentSelectablePassFail(BooleanUtils.toBoolean(value));
                } else if (CourseOfferingManagementSearchImpl.SearchResultColumns.CAN_AUDIT_COURSE.equals(cellInfo.getKey())) {
//                    courseSearchResult.setAuditCourse(BooleanUtils.toBoolean(value));
                } else if (CourseOfferingManagementSearchImpl.SearchResultColumns.IS_HONORS_COURSE.equals(cellInfo.getKey())) {
//                    courseSearchResult.setHonorsCourse(BooleanUtils.toBoolean(value));
                }

            }

            results.add(courseSearchResult);
        }

        return results.get(0);
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


        Collections.sort(resultList, REG_RESULT_COMPARATOR);
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
        if(!scheduleIds.isEmpty()){
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
        if (retList != null && !retList.isEmpty()) {
            populateActivityOfferingsWithScheduleData(retList, contextInfo);
            populateActivityOfferingsWithInstructorData(retList, contextInfo);
            populateActivityOfferingsWithActivityTypeNames(retList, contextInfo);
        }
        return retList;
    }

    private SearchRequestInfo createSearchRequest(String termId, String courseCode, String criteria) {
        SearchRequestInfo searchRequest = new SearchRequestInfo(CourseOfferingManagementSearchImpl.CO_MANAGEMENT_SEARCH.getKey());

        List<String> filterCOStates = new ArrayList<String>(1);
        filterCOStates.add(LuiServiceConstants.LUI_CO_STATE_OFFERED_KEY);

        searchRequest.addParam(CourseOfferingManagementSearchImpl.SearchParameters.FILTER_CO_STATES, filterCOStates);
        searchRequest.addParam(CourseOfferingManagementSearchImpl.SearchParameters.ATP_ID, termId);
        searchRequest.addParam(CourseOfferingManagementSearchImpl.SearchParameters.CROSS_LIST_SEARCH_ENABLED, BooleanUtils.toStringTrueFalse(false));
        searchRequest.addParam(CourseOfferingManagementSearchImpl.SearchParameters.IS_EXACT_MATCH_CO_CODE_SEARCH, BooleanUtils.toStringTrueFalse(false));
        searchRequest.addParam(CourseOfferingManagementSearchImpl.SearchParameters.INCLUDE_PASSFAIL_AUDIT_HONORS_RESULTS, BooleanUtils.toStringTrueFalse(true));

        if (StringUtils.isNotEmpty(courseCode)) {
            // make the course code case insensitive
            if (courseCode != null && !courseCode.isEmpty()) {
                courseCode = courseCode.toUpperCase();
            }

            searchRequest.addParam(CourseOfferingManagementSearchImpl.SearchParameters.COURSE_CODE, courseCode);
        }

        if (StringUtils.isNotEmpty(criteria)) {
            searchRequest.addParam(CourseOfferingManagementSearchImpl.SearchParameters.COURSE_CODE, criteria);
        }

        return searchRequest;
    }

    private static  <T> List<T> emptyIfNull(List<T> inList) {
        if (inList == null) {
            inList = new ArrayList<T>();
        }
        return inList;

    }

    /**
     * The method was created for performance reasons
     * and goes directly against the JPA entities.
     * @param aoIds
     * @return  returns null if no records are found
     */
    @Override
    public Map<String, List<TimeSlotInfo>> getAoTimeSlotMap(List<String> aoIds) {
        Map<String, List<TimeSlotInfo>> resultMap = new HashMap<String, List<TimeSlotInfo>>();

        if(aoIds == null || aoIds.isEmpty()) return null;

        String queryStr =
                "SELECT\n" +
                        "    lui.id,\n" +
                        "    timeslot\n" +
                        "FROM\n" +
                        "    LuiEntity lui,\n" +
                        "    IN (lui.scheduleIds) scheduleId,\n" +
                        "    ScheduleEntity sch,\n" +
                        "    IN ( sch.scheduleComponents ) scheduleComponents,\n" +
                        "    IN ( scheduleComponents.timeSlotIds ) timeSlotId,\n" +
                        "    TimeSlotEntity timeslot\n" +
                        "WHERE\n" +
                        "    lui.id IN (:aoIds)\n" +
                        "AND sch.id = scheduleId\n" +
                        "AND timeslot.id = timeSlotId\n";

        TypedQuery<Object[]> query = getEntityManager().createQuery(queryStr, Object[].class);
        query.setParameter("aoIds", aoIds);
        List<Object[]> results = query.getResultList();

        if(results.isEmpty())return null;

        for(Object[] resultRow : results){
            int i = 0;

            String aoId =  (String)resultRow[i++];
            TimeSlotEntity timeSlotEntity = (TimeSlotEntity)resultRow[i++];

            if(!resultMap.containsKey(aoId)){
                resultMap.put(aoId, new ArrayList<TimeSlotInfo>());
            }

            resultMap.get(aoId).add(timeSlotEntity.toDto());
        }

        return  resultMap;
    }

    private EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
