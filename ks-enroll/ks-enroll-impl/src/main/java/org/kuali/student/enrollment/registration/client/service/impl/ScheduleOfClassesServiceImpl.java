package org.kuali.student.enrollment.registration.client.service.impl;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.kuali.rice.core.api.criteria.Predicate;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.krms.api.repository.reference.ReferenceObjectBinding;
import org.kuali.student.common.collection.KSCollectionUtils;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.enrollment.lui.service.LuiService;
import org.kuali.student.enrollment.registration.client.service.ScheduleOfClassesService;
import org.kuali.student.enrollment.registration.client.service.dto.ActivityOfferingScheduleComponentResult;
import org.kuali.student.enrollment.registration.client.service.dto.ActivityOfferingSearchResult;
import org.kuali.student.enrollment.registration.client.service.dto.ActivityOfferingTypesSearchResult;
import org.kuali.student.enrollment.registration.client.service.dto.ActivityTypeSearchResult;
import org.kuali.student.enrollment.registration.client.service.dto.CourseOfferingDetailsSearchResult;
import org.kuali.student.enrollment.registration.client.service.dto.CourseOfferingLimitedInfoSearchResult;
import org.kuali.student.enrollment.registration.client.service.dto.CourseSearchResult;
import org.kuali.student.enrollment.registration.client.service.dto.EligibilityCheckResult;
import org.kuali.student.enrollment.registration.client.service.dto.InstructorSearchResult;
import org.kuali.student.enrollment.registration.client.service.dto.RegGroupLimitedInfoSearchResult;
import org.kuali.student.enrollment.registration.client.service.dto.RegGroupSearchResult;
import org.kuali.student.enrollment.registration.client.service.dto.RegistrationCountResult;
import org.kuali.student.enrollment.registration.client.service.dto.ResultValueGroupCourseOptions;
import org.kuali.student.enrollment.registration.client.service.dto.ScheduleSearchResult;
import org.kuali.student.enrollment.registration.client.service.dto.StudentScheduleActivityOfferingResult;
import org.kuali.student.enrollment.registration.client.service.dto.SubTermOfferingResult;
import org.kuali.student.enrollment.registration.client.service.dto.TermSearchResult;
import org.kuali.student.enrollment.registration.client.service.impl.util.CourseRegistrationAndScheduleOfClassesUtil;
import org.kuali.student.enrollment.registration.client.service.impl.util.SearchResultHelper;
import org.kuali.student.enrollment.registration.client.service.impl.util.StaticUserDateUtil;
import org.kuali.student.enrollment.registration.search.service.impl.CourseRegistrationSearchServiceImpl;
import org.kuali.student.enrollment.util.KSIdentityServiceHelper;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.TimeOfDayInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.infc.ValidationResult;
import org.kuali.student.r2.common.util.TimeOfDayHelper;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.common.util.constants.CourseWaitListServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
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
import org.kuali.student.r2.core.search.infc.SearchResult;
import org.kuali.student.r2.lum.lrc.dto.ResultValueInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;
import org.kuali.student.r2.lum.lrc.service.LRCService;
import org.kuali.student.r2.lum.util.constants.LrcServiceConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import static org.kuali.rice.core.api.criteria.PredicateFactory.equal;

public class ScheduleOfClassesServiceImpl implements ScheduleOfClassesService {

    public static final Logger LOGGER = LoggerFactory.getLogger(ScheduleOfClassesServiceImpl.class);
    private static final Comparator<RegGroupSearchResult> REG_RESULT_COMPARATOR = new RegResultComparator();
    private EntityManager entityManager;

    private AtpService atpService;
    private LRCService lrcService;
    private LuiService luiService;
    private KSIdentityServiceHelper ksIdentityServiceHelper;

    private Map<String, Integer> activityPriorityMap;

    /**
     * COURSE OFFERINGS *
     */

    @Override
    public List<CourseSearchResult> searchForCourseOfferingsByTermIdAndCourse(String termId, String courseCode, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException {
        return searchForCourseOfferingsByCourseLocal(termId, null, courseCode, contextInfo);
    }

    @Override
    public List<CourseSearchResult> searchForCourseOfferingsByTermCodeAndCourse(String termCode, String courseCode, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException {
        return searchForCourseOfferingsByCourseLocal(null, termCode, courseCode, contextInfo);
    }

    @Override
    public List<CourseSearchResult> searchForCourseOfferingsByTermIdAndCluId(String termId, String cluId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException {
        return searchForCourseOfferingsByCluLocal(termId, null, cluId, contextInfo);
    }

    /**
     * REGISTRATION GROUPS *
     */

    @Override
    public RegGroupSearchResult searchForRegistrationGroupByTermAndCourseAndRegGroup(String termId, String termCode, String courseCode, String regGroupCode, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<RegGroupSearchResult> regGroupSearchResults = searchForRegistrationGroupsLocal(null, termId, termCode, courseCode, regGroupCode, contextInfo);
        if (regGroupSearchResults != null && !regGroupSearchResults.isEmpty()) {
            return KSCollectionUtils.getRequiredZeroElement(regGroupSearchResults);
        } else {
            return null;
        }
    }

    /**
     * TERMS *
     */

    @Override
    public String getTermIdByTermCode(String termCode, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException {
        return KSCollectionUtils.getRequiredZeroElement(CourseRegistrationAndScheduleOfClassesUtil.getTermsByTermCode(termCode, contextInfo)).getTermId();
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


        String entityId = getKsIdentityServiceHelper().getEntityIdByPrincipalId(contextInfo.getPrincipalId());
        List<ValidationResultInfo> validationResults = CourseRegistrationAndScheduleOfClassesUtil.getCourseRegistrationService()
                .checkStudentEligibilityForTerm(entityId, termId, contextInfo);

        // Filter out anything that isn't an error
        List<ValidationResultInfo> reasons = new ArrayList<>();
        for (ValidationResultInfo vr : validationResults) {
            if (ValidationResult.ErrorLevel.ERROR.equals(vr.getLevel())) {
                reasons.add(vr);
            }
        }

        EligibilityCheckResult result = new EligibilityCheckResult(reasons);
        result.setIsEligible(result.getMessages().isEmpty()); // The check passes if there are no errors
        result.setUserId(contextInfo.getPrincipalId());

        return result;
    }

    protected List<TermSearchResult> searchForTermsLocal(String termCode, boolean isActiveTerms, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        if (!StringUtils.isEmpty(termCode)) {
            return CourseRegistrationAndScheduleOfClassesUtil.getTermsByTermCode(termCode, contextInfo);
        }

        if (isActiveTerms) {
            return getActiveTerms();
        }

        return getAllTerms();
    }

    protected List<InstructorSearchResult> searchForInstructorsLocal(String courseOfferingId, String activityOfferingId, String termId, String termCode, String courseCode, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {

        List<InstructorSearchResult> instructors = null;

        if (!StringUtils.isEmpty(activityOfferingId)) {
            instructors = getInstructorsByActivityOfferingId(activityOfferingId);
        } else {

            courseOfferingId = getCourseOfferingId(courseOfferingId, courseCode, termId, termCode, contextInfo);

            if (!StringUtils.isEmpty(courseOfferingId)) {
                instructors = getInstructorsByCourseOfferingId(courseOfferingId);
            }
        }

        return emptyIfNull(instructors);
    }

    /*
    protected CourseOfferingDetailsSearchResult searchForCourseOfferingDetailsLocal(String courseOfferingId) throws MissingParameterException, InvalidParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
        CourseOfferingDetailsSearchResult courseOfferingInfo = searchForCourseOfferingDetails(courseOfferingId);

        return courseOfferingInfo;
    }
    */

    protected List<ActivityTypeSearchResult> searchForActivityTypesLocal(String courseOfferingId, String termId, String termCode, String courseCode, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException {
        courseOfferingId = getCourseOfferingId(courseOfferingId, courseCode, termId, termCode, contextInfo);

        // get the FOs for the course offering. Note: FO's contain a list of activity offering type keys
        List<FormatOfferingInfo> formatOfferings;
        if (!StringUtils.isEmpty(courseOfferingId)) {
            formatOfferings = CourseRegistrationAndScheduleOfClassesUtil.getCourseOfferingService().getFormatOfferingsByCourseOffering(courseOfferingId, contextInfo);
        } else {
            formatOfferings = new ArrayList<>();
        }

        return getActivityTypesForFormatOfferings(formatOfferings, contextInfo);
    }

    protected List<RegGroupSearchResult> searchForRegistrationGroupsLocal(String courseOfferingId, String termId, String termCode, String courseCode, String regGroupName, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        courseOfferingId = getCourseOfferingId(courseOfferingId, courseCode, termId, termCode, contextInfo);
        List<RegGroupSearchResult> retList = null;

        if (!StringUtils.isEmpty(courseOfferingId)) {
            retList = searchForRegGroupsByCourseAndName(courseOfferingId, regGroupName, contextInfo);
        }

        // We want to add registration counts to the results
        if (retList != null && !retList.isEmpty()) {
            List<String> rgIds = new ArrayList<>();
            Map<String, RegGroupSearchResult> tmpMap = new HashMap<>(); // used for performance
            for (RegGroupSearchResult sr : retList) {
                rgIds.add(sr.getRegGroupId()); // build list of ids to pass into search
                tmpMap.put(sr.getRegGroupId(), sr); // create mapping so we can update in O(n) time
            }

            List<RegistrationCountResult> countResults = getRegGroupCounts(rgIds); // perform search

            for (RegistrationCountResult rgCount : countResults) { // for each rg, update corresponding map value
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
        Map<String, ScheduleSearchResult> resultList = new HashMap<>();

        SearchRequestInfo sr = new SearchRequestInfo(CoreSearchServiceImpl.SCH_AND_ROOM_SEARH_BY_ID_SEARCH_KEY);
        sr.addParam(CoreSearchServiceImpl.SearchParameters.SCHEDULE_IDS, new ArrayList<>(scheduleIds));
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

    protected List<CourseSearchResult> searchForCourseOfferingsByCluLocal(String termId, String termCode, String clu, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException {
        termId = CourseRegistrationAndScheduleOfClassesUtil.getTermId(termId, termCode, contextInfo);
        SearchRequestInfo searchRequest = createSearchRequest(termId, null, null, clu);

        return searchForCourseOfferings(searchRequest);
    }

    protected List<CourseSearchResult> searchForCourseOfferingsByCourseLocal(String termId, String termCode, String courseCode, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException {
        termId = CourseRegistrationAndScheduleOfClassesUtil.getTermId(termId, termCode, contextInfo);
        SearchRequestInfo searchRequest = createSearchRequest(termId, courseCode, null, null);

        return searchForCourseOfferings(searchRequest);
    }

    protected List<ActivityOfferingSearchResult> searchForActivityOfferingsLocal(String courseOfferingId, String termId, String termCode, String courseCode, ContextInfo contextInfo) throws MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException, InvalidParameterException {
        List<ActivityOfferingSearchResult> activityOfferingSearchResults = null;
        courseOfferingId = getCourseOfferingId(courseOfferingId, courseCode, termId, termCode, contextInfo);
        if (!StringUtils.isEmpty(courseOfferingId)) {
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

        List<String> aoIds = new ArrayList<>();
        for (ActivityOfferingSearchResult ao : aoList) {
            aoIds.add(ao.getActivityOfferingId());
        }

        return getInstructorListByAoIds(aoIds, ContextUtils.createDefaultContextInfo());

    }

    private List<InstructorSearchResult> getInstructorsByActivityOfferingId(String activityOfferingId) throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException {
        List<String> aoIds = new ArrayList<>();
        aoIds.add(activityOfferingId);
        return getInstructorListByAoIds(aoIds, ContextUtils.createDefaultContextInfo());
    }

    private List<ActivityTypeSearchResult> getActivityTypesForFormatOfferings(List<FormatOfferingInfo> formatOfferings, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException {

        List<ActivityTypeSearchResult> activitiesTypeKeys = new ArrayList<>();


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

    private List<RegistrationCountResult> getRegGroupCounts(List<String> regGroupIds) throws MissingParameterException, InvalidParameterException, OperationFailedException, PermissionDeniedException {

        List<RegistrationCountResult> retList = new ArrayList<>();
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

    public List<RegGroupSearchResult> searchForRegGroupsByCourseAndName(String courseOfferingId, String regGroupName, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        List<RegGroupSearchResult> regGroupSearchResults = searchForRegGroups(courseOfferingId, contextInfo);

        // return a list with a single entity if regGroupName was provided
        if (!StringUtils.isEmpty(regGroupName)) {
            for (RegGroupSearchResult rg : regGroupSearchResults) {
                if (rg.getRegGroupName().equals(regGroupName)) {
                    List<RegGroupSearchResult> result = new ArrayList<>();
                    result.add(rg);
                    return result;
                }
            }
            return new ArrayList<>();
        }

        return new ArrayList<>(regGroupSearchResults);
    }

    private String getCourseOfferingId(String courseOfferingId, String courseCode, String termId, String termCode, ContextInfo contextInfo) throws MissingParameterException, PermissionDeniedException, OperationFailedException, InvalidParameterException {
        if (!StringUtils.isEmpty(courseOfferingId)) {
            return courseOfferingId;
        }

        termId = CourseRegistrationAndScheduleOfClassesUtil.getTermId(termId, termCode, contextInfo);
        List<String> coIds = searchForCourseOfferingIdByCourseCodeAndTerm(courseCode, termId);

        return getSingleItemFromListOrNull(coIds);
    }

    private <T> T getSingleItemFromListOrNull(List<T> items) throws OperationFailedException {
        if (items != null && !items.isEmpty()) {
            return KSCollectionUtils.getRequiredZeroElement(items);
        } else {
            return null;
        }

    }

    //This is a helper method to get all terms in SOCs with a state of Published
    private List<AtpInfo> getValidAtps(ContextInfo contextInfo) {
        List<SocInfo> socs;
        List<String> termIds = new ArrayList<>();
        List<AtpInfo> atps = new ArrayList<>();
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

    private List<CourseSearchResult> searchForCourseOfferings(SearchRequestInfo searchRequest) throws InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException {
        SearchResultInfo searchResult = CourseRegistrationAndScheduleOfClassesUtil.getSearchService().search(searchRequest, ContextUtils.createDefaultContextInfo());

        List<CourseSearchResult> results = new ArrayList<>();

        for (SearchResultRowInfo row : searchResult.getRows()) {
            CourseSearchResult courseSearchResult = new CourseSearchResult();

            for (SearchResultCellInfo cellInfo : row.getCells()) {

                String value = StringUtils.EMPTY;
                if (cellInfo.getValue() != null) {
                    value = cellInfo.getValue();
                }

                if (CourseOfferingManagementSearchImpl.SearchResultColumns.CODE.equals(cellInfo.getKey())) {
                    courseSearchResult.setCourseCode(value);
                } else if (CourseOfferingManagementSearchImpl.SearchResultColumns.CO_ID.equals(cellInfo.getKey())) {
                    courseSearchResult.setCourseId(value);
                }

            }

            results.add(courseSearchResult);
        }

        return results;
    }

    //   Returns a list of course offering details such as main info (code, name, desc, etc.), cross-listed courses, prereqs, and AO info (main info, schedule, instructor, reg groups).
    @Override
    public CourseOfferingDetailsSearchResult searchForCourseOfferingDetails(String courseOfferingId, String courseCode, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException, DoesNotExistException {
        long startTime = System.currentTimeMillis();

        SearchRequestInfo searchRequest = new SearchRequestInfo(CourseRegistrationSearchServiceImpl.CO_AND_AO_INFO_BY_CO_ID_SEARCH_TYPE.getKey());
        searchRequest.addParam(CourseOfferingManagementSearchImpl.SearchParameters.CO_ID, courseOfferingId);
        searchRequest.addParam(CourseOfferingManagementSearchImpl.SearchParameters.COURSE_CODE, courseCode);
        SearchResultInfo searchResult = CourseRegistrationAndScheduleOfClassesUtil.getSearchService().search(searchRequest, contextInfo);

        CourseOfferingDetailsSearchResult courseSearchResult = new CourseOfferingDetailsSearchResult();
        Map<String, CourseOfferingLimitedInfoSearchResult> hmCOCrossListed = new HashMap<>();
        Map<String, StudentScheduleActivityOfferingResult> hmActivityOfferings = new HashMap<>();
        Map<String, String> hmRGsWLMaxSize = new HashMap<>();

        for (SearchResultHelper.KeyValue row : SearchResultHelper.wrap(searchResult)) {
            String coId = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.CO_ID);
            String coCode = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.CO_CODE);
            String coSubjectArea = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.CO_SUBJECT_AREA);
            String coLongName = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.CO_LONG_NAME);
            String cluId = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.CO_CLU_ID);
            String coDesc = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.CO_DESC_FORMATTED);
            String resultValuesGroupKey = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.RES_VAL_GROUP_KEY);
            String coClId = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.CO_CROSSLISTED_ID);
            String coClCode = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.CO_CROSSLISTED_CODE);
            String coClSubjectArea = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.CO_CROSSLISTED_SUBJECT_AREA);
            String aoId = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.AO_ID);
            String aoType = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.AO_TYPE);
            String aoName = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.AO_NAME);
            String aoCode = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.AO_CODE);
            String aoMaxSeats = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.AO_MAX_SEATS);
            String aoSeatCount = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.SEAT_COUNT);
            String aoWlState = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.CWL_STATE);
            String aoWlMaxSize = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.CWL_MAX_SIZE);
            String rgWlCount = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.RG_WAITLIST_COUNT);
            String rgId = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.RG_ID);
            String rgCode = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.RG_CODE);
            String isTBA = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.TBA_IND);
            String roomCode = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.ROOM_CODE);
            String buildingCode = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.BUILDING_CODE);
            String weekdays = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.WEEKDAYS);
            String startTimeMs = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.START_TIME_MS);
            String endTimeMs = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.END_TIME_MS);
            String honorsFlag = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.HONORS_FLAG);
            String coAtpId = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.CO_ATP_ID);
            String aoAtpId = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.AO_ATP_ID);

            // general CO info
            courseSearchResult.setCourseOfferingId(coId);
            courseSearchResult.setCourseOfferingCode(coCode);
            courseSearchResult.setCourseOfferingSubjectArea(coSubjectArea);
            courseSearchResult.setCourseOfferingLongName(coLongName);
            courseSearchResult.setCourseOfferingDesc(coDesc);
            courseSearchResult.setCluId(cluId);

            // Grading and credit options
            setGradingAndCreditOptionsForCourseOfferingDetails(courseSearchResult, resultValuesGroupKey, contextInfo);

            // running over the list of results returned. One CO can have multiple Cross-Listed COs, so adding them to hashmap
            addCrossListedCoursesToCourseOfferingDetails(hmCOCrossListed, coClCode, coClId, coClSubjectArea);

            // putting RG + WLmax into hashmap while looping over AOs. We want to find smallest aoWlMaxSize out of all AOs for given RG -> it'll be RG WLMax
            if (rgId != null) {
                if (!hmRGsWLMaxSize.containsKey(rgId)) {
                    hmRGsWLMaxSize.put(rgId, aoWlMaxSize);
                } else {
                    if (aoWlMaxSize != null) {
                        if (hmRGsWLMaxSize.get(rgId) != null) {
                            if (Integer.parseInt(aoWlMaxSize) < Integer.parseInt(hmRGsWLMaxSize.get(rgId))) {
                                hmRGsWLMaxSize.put(rgId, aoWlMaxSize);
                            }
                        } else {
                            hmRGsWLMaxSize.put(rgId, aoWlMaxSize);
                        }
                    }
                }
            }

            // running over the list of results returned. One CO can have multiple AOs
            // Scheduling info
            ActivityOfferingScheduleComponentResult scheduleComponent = CourseRegistrationAndScheduleOfClassesUtil.getActivityOfferingScheduleComponent(isTBA, roomCode, buildingCode,
                    weekdays, startTimeMs, endTimeMs);
            // have to check if we already have the AO in our list, because we can have multiple schedules for the same AO
            if (!hmActivityOfferings.containsKey(aoId) && !StringUtils.isEmpty(aoId)) {
                StudentScheduleActivityOfferingResult ao = createActivityOfferingResult(aoId, aoName, aoType, aoCode, aoMaxSeats, aoSeatCount, aoWlState, aoWlMaxSize, rgWlCount, rgId, rgCode, scheduleComponent, coAtpId, aoAtpId, honorsFlag, contextInfo);
                hmActivityOfferings.put(aoId, ao);
            } else if (hmActivityOfferings.containsKey(aoId)) {
                // reg group
                if (!hmActivityOfferings.get(aoId).getRegGroupInfos().containsKey(rgId) && rgId != null) {
                    RegGroupLimitedInfoSearchResult rgGroup = createRegGroupLimitedInfoSearchResult(rgId, rgCode, rgWlCount, aoWlState);
                    hmActivityOfferings.get(aoId).getRegGroupInfos().put(rgId, rgGroup);
                }
                // schedule components: if it's the same (based on time/location) then do nothing
                boolean sameScheduleComponent = false;
                for (ActivityOfferingScheduleComponentResult scheduleComponentResult : hmActivityOfferings.get(aoId).getScheduleComponents()) {
                    if (StringUtils.equals(scheduleComponentResult.getBuildingCode(), scheduleComponent.getBuildingCode()) && StringUtils.equals(scheduleComponentResult.getRoomCode(), scheduleComponent.getRoomCode()) &&
                            StringUtils.equals(scheduleComponentResult.getDays(), scheduleComponent.getDays()) && (scheduleComponentResult.getIsTBA() == scheduleComponent.getIsTBA()) &&
                            StringUtils.equals(scheduleComponentResult.getStartTime(), scheduleComponent.getStartTime()) && StringUtils.equals(scheduleComponentResult.getEndTime(), scheduleComponent.getEndTime())) {
                        sameScheduleComponent = true;
                        break;
                    }
                }
                if (!sameScheduleComponent) {
                    hmActivityOfferings.get(aoId).getScheduleComponents().add(scheduleComponent);
                }
            }
        }

        courseSearchResult.setCourseOfferingNumber(courseSearchResult.getCourseOfferingCode().replaceFirst(courseSearchResult.getCourseOfferingSubjectArea(), ""));

        // setting Cross-Listed COs
        if (!hmCOCrossListed.isEmpty()) {
            List<CourseOfferingLimitedInfoSearchResult> coCrossListed = new ArrayList<>();
            for (Entry<String, CourseOfferingLimitedInfoSearchResult> entry : hmCOCrossListed.entrySet()) {
                coCrossListed.add(entry.getValue());
            }
            courseSearchResult.setCrossListedCourses(coCrossListed);
        }

        // setting AOs
        courseSearchResult.setRegGroupsOffered(false);
        if (!hmActivityOfferings.isEmpty()) {
            List<String> aoIDs = new ArrayList<>();
            for (Map.Entry<String, StudentScheduleActivityOfferingResult> entry: hmActivityOfferings.entrySet()) {
                // checking if there are offered reg groups for given CO, if no RGs for given AO -> remove that AO
                if (!entry.getValue().getRegGroupInfos().isEmpty()) {
                    aoIDs.add(entry.getKey());
                    if (!courseSearchResult.isRegGroupsOffered()) {
                        courseSearchResult.setRegGroupsOffered(true);
                    }
                }
            }

            if (!aoIDs.isEmpty()) {
                Map<String, List<InstructorSearchResult>> hmAOInstructors = CourseRegistrationAndScheduleOfClassesUtil.searchForInstructorsByAoIds(aoIDs, contextInfo);
                Map<String, ActivityOfferingTypesSearchResult> hmActivityOfferingTypes = new HashMap<>();

                for (String key : aoIDs) {
                    StudentScheduleActivityOfferingResult activityOffering = hmActivityOfferings.get(key);

                    // setting WLmax for RGs for given AO
                    for (Entry<String, RegGroupLimitedInfoSearchResult> entry : activityOffering.getRegGroupInfos().entrySet()) {
                        entry.getValue().setMaxWaitListSize(hmRGsWLMaxSize.get(entry.getKey()) == null ?  null : Integer.parseInt(hmRGsWLMaxSize.get(entry.getKey())));
                    }

                    // getting instructor info for AOs
                    activityOffering.setInstructors(hmAOInstructors.get(key));

                    //See if there are rules on the AO
                    List<ReferenceObjectBinding> referenceObjectBindings = CourseRegistrationAndScheduleOfClassesUtil.getRuleManagementService().findReferenceObjectBindingsByReferenceObject(CourseOfferingServiceConstants.REF_OBJECT_URI_ACTIVITY_OFFERING, key);
                    for (ReferenceObjectBinding referenceObjectBinding : referenceObjectBindings) {
                        String requisite = CourseRegistrationAndScheduleOfClassesUtil.getRuleManagementService().translateNaturalLanguageForObject("KS-KRMS-NL-USAGE-1005", "agenda", referenceObjectBinding.getKrmsObjectId(), "en");
                        activityOffering.getRequisites().add(requisite);
                    }

                    // Creating ActivityOfferingTypesSearchResult types
                    if (!hmActivityOfferingTypes.containsKey(activityOffering.getActivityOfferingType())) {
                        ActivityOfferingTypesSearchResult activityOfferingType = new ActivityOfferingTypesSearchResult();
                        activityOfferingType.setActivityOfferingType(activityOffering.getActivityOfferingType());
                        activityOfferingType.setActivityOfferingTypeName(activityOffering.getActivityOfferingTypeName());
                        activityOfferingType.setActivityOfferingCode(activityOffering.getActivityOfferingTypeName().substring(0, 3));
                        List<StudentScheduleActivityOfferingResult> activityOfferings = new ArrayList<>();
                        activityOfferings.add(activityOffering);
                        activityOfferingType.setActivityOfferings(activityOfferings);
                        hmActivityOfferingTypes.put(activityOffering.getActivityOfferingType(), activityOfferingType);
                    } else {
                        hmActivityOfferingTypes.get(activityOffering.getActivityOfferingType()).getActivityOfferings().add(activityOffering);
                    }
                }

                // Creating AO Types
                List<ActivityOfferingTypesSearchResult> activityOfferingTypes = new ArrayList<>();
                List<String> aoTypes = new ArrayList<>();
                for (String key : hmActivityOfferingTypes.keySet()) {
                    aoTypes.add(key);
                }
                // sorting over AO types
                CourseRegistrationAndScheduleOfClassesUtil.sortActivityOfferingTypeKeyList(aoTypes, getActivityPriorityMap(contextInfo));  // sort the activity offerings type keys by priority order
                for (String key : aoTypes) {
                    ActivityOfferingTypesSearchResult aoType = hmActivityOfferingTypes.get(key);
                    CourseRegistrationAndScheduleOfClassesUtil.sortActivityOfferings(aoType);
                    activityOfferingTypes.add(aoType);
                }

                courseSearchResult.setActivityOfferingTypes(activityOfferingTypes);
            }
        }

        // setting prerequisites
        courseSearchResult.setPrerequisites(searchForPrerequisitesByCourseOffering(courseOfferingId));

        LOGGER.info("SearchDetails took:" + (System.currentTimeMillis() - startTime) + "ms");

        return courseSearchResult;
    }

    private List<String> searchForPrerequisitesByCourseOffering(String courseOfferingId) {
        List<String> prerequisites = new ArrayList<>();

        List<ReferenceObjectBinding> referenceObjectBindings = CourseRegistrationAndScheduleOfClassesUtil.getRuleManagementService().findReferenceObjectBindingsByReferenceObject(CourseOfferingServiceConstants.REF_OBJECT_URI_COURSE_OFFERING, courseOfferingId);
        for (ReferenceObjectBinding referenceObjectBinding : referenceObjectBindings) {
            String prerequisite = CourseRegistrationAndScheduleOfClassesUtil.getRuleManagementService().translateNaturalLanguageForObject("KS-KRMS-NL-USAGE-1005", "agenda", referenceObjectBinding.getKrmsObjectId(), "en");
            prerequisites.add(prerequisite);
        }

        return prerequisites;
    }

    @Override
    public RegGroupSearchResult getRegGroup(String regGroupId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException {
        SearchRequestInfo searchRequest = createRegGroupSearchRequest(null, regGroupId);
        List<RegGroupSearchResult> resultList =  processRegGroupSearch(searchRequest);
        return KSCollectionUtils.getRequiredZeroElement(resultList);
    }

    @Override
    public List<RegGroupSearchResult> getRegGroups(List<String> regGroupIds, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException {
        SearchRequestInfo searchRequest = new SearchRequestInfo(ActivityOfferingSearchServiceImpl.REG_GROUPS_BY_CO_ID_SEARCH_KEY);

        searchRequest.addParam(ActivityOfferingSearchServiceImpl.SearchParameters.RG_IDS, regGroupIds);

        return processRegGroupSearch(searchRequest);
    }

    @Override
    public List<RegGroupSearchResult> searchForRegGroups(String courseOfferingId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException {
        SearchRequestInfo searchRequest = createRegGroupSearchRequest(courseOfferingId, null);
        return processRegGroupSearch(searchRequest);
    }

    protected List<RegGroupSearchResult> processRegGroupSearch(SearchRequestInfo searchRequest) throws InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException {


        SearchResultInfo searchResult = CourseRegistrationAndScheduleOfClassesUtil.getSearchService().search(searchRequest, ContextUtils.createDefaultContextInfo());

        Map<String, RegGroupSearchResult> regGroupResultMap = new HashMap<>(searchResult.getRows().size());


        for (SearchResultRowInfo row : searchResult.getRows()) {

            String returnedCoId = null;
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

                if (ActivityOfferingSearchServiceImpl.SearchResultColumns.CO_ID.equals(cellInfo.getKey())) {
                    returnedCoId = value;
                }
                else if (ActivityOfferingSearchServiceImpl.SearchResultColumns.AO_ID.equals(cellInfo.getKey())) {
                    activityOfferingId = value;
                } else if (ActivityOfferingSearchServiceImpl.SearchResultColumns.RG_ID.equals(cellInfo.getKey())) {
                    regGroupId = value;
                } else if (ActivityOfferingSearchServiceImpl.SearchResultColumns.RG_NAME.equals(cellInfo.getKey())) {
                    regGroupName = value;
                } else if (ActivityOfferingSearchServiceImpl.SearchResultColumns.RG_STATE.equals(cellInfo.getKey())) {
                    regGroupState = value;
                } else if (ActivityOfferingSearchServiceImpl.SearchResultColumns.ATP_ID.equals(cellInfo.getKey())) {
                    termId = value;
                }

            }

            if (regGroupResultMap.containsKey(regGroupId)) {
                regGroupResultMap.get(regGroupId).getActivityOfferingIds().add(activityOfferingId);
            } else {
                RegGroupSearchResult regGroupSearchResult = new RegGroupSearchResult();
                regGroupSearchResult.setCourseOfferingId(returnedCoId);
                regGroupSearchResult.setRegGroupId(regGroupId);
                regGroupSearchResult.setRegGroupName(regGroupName);
                regGroupSearchResult.setRegGroupState(regGroupState);
                regGroupSearchResult.setTermId(termId);
                regGroupSearchResult.getActivityOfferingIds().add(activityOfferingId);
                regGroupResultMap.put(regGroupId, regGroupSearchResult);

            }


        }

        List<RegGroupSearchResult> resultList = new ArrayList<>(regGroupResultMap.values());


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

        List<String> aoStates = new ArrayList<>(1);
        aoStates.add(LuiServiceConstants.LUI_AO_STATE_OFFERED_KEY);

        searchRequestInfo.addParam(ActivityOfferingSearchServiceImpl.SearchParameters.CO_ID, courseOfferingId);
        searchRequestInfo.addParam(ActivityOfferingSearchServiceImpl.SearchParameters.AO_STATES, aoStates);
        SearchResultInfo searchResult = CourseRegistrationAndScheduleOfClassesUtil.getSearchService().search(searchRequestInfo, ContextUtils.createDefaultContextInfo());

        List<ActivityOfferingSearchResult> resultList = new ArrayList<>();


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

    protected List<String> searchForCourseOfferingIdByCourseCodeAndTerm(String courseCode, String atpId) throws InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException {
        List<String> resultList = new ArrayList<>();


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
    @Override
    public List<InstructorSearchResult> getInstructorListByAoIds(List<String> aoIds, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, DoesNotExistException, OperationFailedException, PermissionDeniedException {
        List<InstructorSearchResult> resultList = new ArrayList<>();
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
        List<String> scheduleIds = new ArrayList<>();

        for (ActivityOfferingSearchResult ao : aoList) {
            String scheduleId = ao.getScheduleId();

            if (!StringUtils.isEmpty(scheduleId)) {
                scheduleIds.add(scheduleId);
            }
        }
        if (!scheduleIds.isEmpty()) {
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
        List<String> aoIds = new ArrayList<>();

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

    /**
     *
     * @param courseOfferingId    optional
     * @param regGroupId          optional
     * @return search results
     */
    private SearchRequestInfo createRegGroupSearchRequest(String courseOfferingId, String regGroupId) {
        SearchRequestInfo searchRequest = new SearchRequestInfo(ActivityOfferingSearchServiceImpl.REG_GROUPS_BY_CO_ID_SEARCH_KEY);

        searchRequest.addParam(ActivityOfferingSearchServiceImpl.SearchParameters.CO_ID, courseOfferingId);
        searchRequest.addParam(ActivityOfferingSearchServiceImpl.SearchParameters.RG_ID, regGroupId);

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

    private SearchRequestInfo createSearchRequest(String termId, String courseCode, String criteria, String cluId) {
        SearchRequestInfo searchRequest = new SearchRequestInfo(CourseOfferingManagementSearchImpl.CO_MANAGEMENT_SEARCH.getKey());

        List<String> filterCOStates = new ArrayList<>(1);
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

        if (StringUtils.isNotEmpty(cluId)) {
            searchRequest.addParam(CourseOfferingManagementSearchImpl.SearchParameters.CLU_ID, cluId);
        }

        return searchRequest;
    }

    private static <T> List<T> emptyIfNull(List<T> inList) {
        if (inList == null) {
            inList = new ArrayList<>();
        }
        return inList;

    }

    /**
     * The method was created for performance reasons
     * and goes directly against the JPA entities.
     *
     * @param aoIds list of activity offering ids
     * @return returns null if no records are found
     */
    @Override
    public Map<String, List<TimeSlotInfo>> getAoTimeSlotMap(List<String> aoIds, ContextInfo contextInfo) {
        Map<String, List<TimeSlotInfo>> resultMap = new HashMap<>();

        if (aoIds == null || aoIds.isEmpty()) return null;

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

        if (results.isEmpty()) return null;

        for (Object[] resultRow : results) {
            int i = 0;

            String aoId = (String) resultRow[i++];
            TimeSlotEntity timeSlotEntity = (TimeSlotEntity) resultRow[i];

            if (!resultMap.containsKey(aoId)) {
                resultMap.put(aoId, new ArrayList<TimeSlotInfo>());
            }

            resultMap.get(aoId).add(timeSlotEntity.toDto());
        }

        return resultMap;
    }

    private List<String> getCourseOfferingCreditOptionValues(String creditOptionId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
        int firstValue = 0;
        List<String> creditOptions = new ArrayList<>();

        //Lookup the selected credit option and set from persisted values
        if (!creditOptionId.isEmpty()) {
            //Lookup the resultValueGroup Information
            ResultValuesGroupInfo resultValuesGroupInfo = CourseRegistrationAndScheduleOfClassesUtil.getLrcService().getResultValuesGroup(creditOptionId, contextInfo);
            String typeKey = resultValuesGroupInfo.getTypeKey();

            //Get the actual values
            List<ResultValueInfo> resultValueInfos = CourseRegistrationAndScheduleOfClassesUtil.getLrcService().getResultValuesByKeys(resultValuesGroupInfo.getResultValueKeys(), contextInfo);

            if (!resultValueInfos.isEmpty()) {
                if (typeKey.equals(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_FIXED)) {
                    creditOptions.add(resultValueInfos.get(firstValue).getValue()); // fixed credits
                } else if (typeKey.equals(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_MULTIPLE) ||
                        typeKey.equals(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_RANGE)) {  // multiple or range
                    for (ResultValueInfo resultValueInfo : resultValueInfos) {
                        creditOptions.add(resultValueInfo.getValue());
                    }
                }
            }
        }

        return creditOptions;
    }

    @Override
    public ResultValueGroupCourseOptions getCreditAndGradingOptions(String courseOfferingId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        ResultValueGroupCourseOptions rvgCourseOptions = new ResultValueGroupCourseOptions();
        rvgCourseOptions.setCourseOfferingId(courseOfferingId);

        // Get the LUI so we can get the ResultValueGroupKeys (Groups holding the credit & grading options)
        LuiInfo luiInfo = getLuiService().getLui(courseOfferingId, contextInfo);

        if (!luiInfo.getResultValuesGroupKeys().isEmpty()) {
            // Identify & translate the grading option groups
            for (String rvgKey : luiInfo.getResultValuesGroupKeys()) {
                String gradingOptionLabel = CourseRegistrationAndScheduleOfClassesUtil.translateGradingOptionKeyToName(rvgKey);
                if (StringUtils.isNotEmpty(gradingOptionLabel)) {
                    rvgCourseOptions.getGradingOptions().put(rvgKey, gradingOptionLabel);
                }
            }

            // Load any credit option values - Only necessary if there were RVGs that weren't grading option values
            if (rvgCourseOptions.getGradingOptions().size() != luiInfo.getResultValuesGroupKeys().size()) {
                // Fetch the ResultValues for the groups
                List<ResultValueInfo> resultValueInfos = getLrcService().getResultValuesForResultValuesGroups(
                        luiInfo.getResultValuesGroupKeys(), contextInfo);

                // Pick out the credit option values and store them to be returned out
                for (ResultValueInfo resultValueInfo : resultValueInfos) {
                    if (LrcServiceConstants.RESULT_SCALE_KEY_CREDIT_DEGREE.equals(resultValueInfo.getResultScaleKey())) {
                        rvgCourseOptions.getCreditOptions().put(resultValueInfo.getKey(), resultValueInfo.getValue());
                    }
                }
            }
        }

        return rvgCourseOptions;
    }

    // Setting grading and credit options
    private void setGradingAndCreditOptionsForCourseOfferingDetails(CourseOfferingDetailsSearchResult courseSearchResult, String resultValuesGroupKey, ContextInfo contextInfo) throws DoesNotExistException, MissingParameterException, InvalidParameterException, OperationFailedException, PermissionDeniedException {
        if (!StringUtils.isEmpty(resultValuesGroupKey) && resultValuesGroupKey.startsWith(LrcServiceConstants.RESULT_GROUP_KEY_KUALI_CREDITTYPE_CREDIT_BASE)) {
            courseSearchResult.setCreditOptions(getCourseOfferingCreditOptionValues(resultValuesGroupKey, contextInfo));
        } else {
            if (!courseSearchResult.getGradingOptions().containsKey(resultValuesGroupKey)) {
                String gradingOptionName = CourseRegistrationAndScheduleOfClassesUtil.translateGradingOptionKeyToName(resultValuesGroupKey);
                if (!StringUtils.isEmpty(gradingOptionName)) {
                    courseSearchResult.getGradingOptions().put(resultValuesGroupKey, gradingOptionName);
                }
            }
        }
    }

    // Adding Cross-listed COs to the original CO details
    private void addCrossListedCoursesToCourseOfferingDetails(Map<String, CourseOfferingLimitedInfoSearchResult> hmCOCrossListed, String coClCode, String coClId, String coClSubjectArea) {
        // running over the list of results returned. One CO can have multiple Cross-Listed COs
        if (!hmCOCrossListed.containsKey(coClCode) && !StringUtils.isEmpty(coClId)) {
            CourseOfferingLimitedInfoSearchResult coCL = new CourseOfferingLimitedInfoSearchResult();
            coCL.setCourseOfferingId(coClId);
            coCL.setCourseOfferingCode(coClCode);
            coCL.setCourseOfferingSubjectArea(coClSubjectArea);
            coCL.setCourseOfferingNumber(coClCode.replaceFirst(coClSubjectArea, ""));
            hmCOCrossListed.put(coClCode, coCL);
        }
    }

    private RegGroupLimitedInfoSearchResult createRegGroupLimitedInfoSearchResult(String rgId, String rgCode, String rgWlCount, String rgWlState) {
        RegGroupLimitedInfoSearchResult regGroup = new RegGroupLimitedInfoSearchResult();
        regGroup.setRegGroupId(rgId);
        regGroup.setRegGroupCode(rgCode);
        regGroup.setWaitListSize(rgWlCount == null ? null : Integer.parseInt(rgWlCount));
        if (StringUtils.equals(rgWlState, CourseWaitListServiceConstants.COURSE_WAIT_LIST_ACTIVE_STATE_KEY)) {
            regGroup.setWaitListOffered(true);
        } else {
            regGroup.setWaitListOffered(false);
        }
        return regGroup;
    }

    private StudentScheduleActivityOfferingResult createActivityOfferingResult(String aoId, String aoName, String aoType, String aoCode,
                                                                               String aoMaxSeats, String aoSeatCount, String aoWlState, String aoWlMaxSize, String rgWlCount, String rgId, String rgCode,
                                                                               ActivityOfferingScheduleComponentResult scheduleComponent, String coAtpId, String aoAtpId, String honorsFlag, ContextInfo contextInfo) throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException, DoesNotExistException {
        StudentScheduleActivityOfferingResult ao = new StudentScheduleActivityOfferingResult();

        // main info
        ao.setActivityOfferingId(aoId);
        ao.setActivityOfferingTypeName(aoName);
        ao.setActivityOfferingType(aoType);
        ao.setActivityOfferingCode(aoCode);
        ao.setSeatsAvailable(aoMaxSeats == null ? -1 : Integer.parseInt(aoMaxSeats));
        if (ao.getSeatsAvailable() != null && aoSeatCount != null) {
            ao.setSeatsOpen(ao.getSeatsAvailable() - Integer.parseInt(aoSeatCount));
        }
        ao.setMaxWaitListSize(aoWlMaxSize == null ? null : Integer.parseInt(aoWlMaxSize));
        // reg group : may be null if there is no offered rg for ao
        Map<String, RegGroupLimitedInfoSearchResult> regGroups = new HashMap<>();
        if (rgId != null) {
            RegGroupLimitedInfoSearchResult regGroup = createRegGroupLimitedInfoSearchResult(rgId, rgCode, rgWlCount, aoWlState);
            regGroups.put(rgId, regGroup);
        }
        ao.setRegGroupInfos(regGroups);
        // schedule components
        List<ActivityOfferingScheduleComponentResult> scheduleComponents = new ArrayList<>();
        scheduleComponents.add(scheduleComponent);
        ao.setScheduleComponents(scheduleComponents);

        //If the AO and CO terms differ, then look up the AO's term information and add it to the result
        if (!StringUtils.equals(coAtpId, aoAtpId)) {
            AtpInfo aoAtp = atpService.getAtp(aoAtpId, contextInfo);
            SubTermOfferingResult subTermOfferingResult = new SubTermOfferingResult();
            subTermOfferingResult.setName(aoAtp.getName());
            subTermOfferingResult.setSubTermId(aoAtp.getId());
            subTermOfferingResult.setStartDate(aoAtp.getStartDate());
            subTermOfferingResult.setEndDate(aoAtp.getEndDate());
            ao.setSubterm(subTermOfferingResult);
        }

        ao.setHonors(honorsFlag != null && honorsFlag.equalsIgnoreCase("true"));

        return ao;
    }

    /**
     * This method uses a somewhat fast search to grab the updated seatcounts and waitlist counts and update the
     * courseOfferingSearchResults with those values
     * @param courseOfferingSearchResults search results
     * @param contextInfo context of the call
     * @throws MissingParameterException
     * @throws InvalidParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    protected void updateSeatcounts(CourseOfferingDetailsSearchResult courseOfferingSearchResults, ContextInfo contextInfo) throws MissingParameterException, InvalidParameterException, OperationFailedException, PermissionDeniedException {
        if (courseOfferingSearchResults.getActivityOfferingTypes() == null || courseOfferingSearchResults.getActivityOfferingTypes().isEmpty()) {
            return;
        }

        //Build up references to the AOs
        Map<String,StudentScheduleActivityOfferingResult> aoId2Ao = new HashMap<>();
        for(ActivityOfferingTypesSearchResult aoType: courseOfferingSearchResults.getActivityOfferingTypes()){
            for(StudentScheduleActivityOfferingResult ao :aoType.getActivityOfferings()){
                aoId2Ao.put(ao.getActivityOfferingId(), ao);
            }
        }

        //Do a search
        SearchRequestInfo searchRequest = new SearchRequestInfo(CourseRegistrationSearchServiceImpl.RG_WAITLIST_AND_AO_SEATCOUNT_BY_COID_SEARCH_INFO_SEARCH_TYPE.getKey());
        searchRequest.addParam(CourseOfferingManagementSearchImpl.SearchParameters.CO_ID, courseOfferingSearchResults.getCourseOfferingId());
        SearchResultInfo searchResult = CourseRegistrationAndScheduleOfClassesUtil.getSearchService().search(searchRequest, contextInfo);

        //Parse out the results
        for (SearchResultHelper.KeyValue row : SearchResultHelper.wrap(searchResult)) {
            String aoId = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.AO_ID);
            String rgId = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.RG_ID);
            String rgWlCount = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.WAITLIST_COUNT);
            String aoSeatCount = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.SEAT_COUNT);
            String aoMaxSeatsStr = row.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.AO_MAX_SEATS);

            //Look up the associated ao and update the seatcount values
            StudentScheduleActivityOfferingResult ao = aoId2Ao.get(aoId);
            // adding logic here because of caching: rgId should be technically there, but not yet
            if (ao != null) {
                // make sure the max seats are up-to-date.
                if(aoMaxSeatsStr != null && !aoMaxSeatsStr.isEmpty()){
                    ao.setSeatsAvailable(Integer.parseInt(aoMaxSeatsStr));
                }
                if (!ao.getRegGroupInfos().isEmpty() && ao.getRegGroupInfos().containsKey(rgId)) {
                    RegGroupLimitedInfoSearchResult rg = ao.getRegGroupInfos().get(rgId);
                    rg.setWaitListSize(Integer.parseInt(rgWlCount));
                }
                if (ao.getSeatsAvailable() != null) {
                    // if the number is negative, then set to 0
                    ao.setSeatsOpen(Math.max(0,(ao.getSeatsAvailable() - Integer.parseInt(aoSeatCount))));
                }
            }
        }
    }

    @Override
    public CourseSearchResult getCourseOfferingById(String courseOfferingId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException, DoesNotExistException, IOException {
        return KSCollectionUtils.getRequiredZeroElement(getCourseOfferings(Arrays.asList(courseOfferingId), null, contextInfo));
    }

    public List<CourseSearchResult> getCourseOfferings(List<String> luiIds, List<String> atpIds, ContextInfo contextInfo) throws MissingParameterException, InvalidParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {

        List<CourseSearchResult> courseSearchResults = new ArrayList<>();

        //Use search service to grab the course data
        SearchRequestInfo searchRequest = new SearchRequestInfo(CourseRegistrationSearchServiceImpl.CO_SEARCH_INFO_SEARCH_KEY);
        if(luiIds != null && !luiIds.isEmpty()) {
            searchRequest.addParam(CourseRegistrationSearchServiceImpl.SearchParameters.LUI_IDS, new ArrayList<>(luiIds));
        }

        if(atpIds != null && !atpIds.isEmpty()) {
            searchRequest.addParam(CourseRegistrationSearchServiceImpl.SearchParameters.ATP_IDS, new ArrayList<>(atpIds));
        }
        ContextInfo context = ContextUtils.createDefaultContextInfo();

        SearchResult searchResults = CourseRegistrationAndScheduleOfClassesUtil.getSearchService().search(searchRequest, context);

        //Process the results
        for (SearchResultHelper.KeyValue keyValue : SearchResultHelper.wrap(searchResults)) {

            CourseSearchResult courseSearchResult = new CourseSearchResult();
            courseSearchResult.setCluId(keyValue.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.CO_CLU_ID));
            courseSearchResult.setCourseIdentifierType(keyValue.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.CO_IDENT_TYPE));
            courseSearchResult.setCourseCode(keyValue.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.LUI_CODE));
            courseSearchResult.setCourseId(keyValue.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.LUI_ID));
            courseSearchResult.setCourseLevel(keyValue.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.LUI_LEVEL));
            courseSearchResult.setCourseNumber(keyValue.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.COURSE_NUMBER));
            courseSearchResult.setCoursePrefix(keyValue.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.COURSE_DIVISION));
            courseSearchResult.setLongName(keyValue.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.LUI_LONG_NAME));
            courseSearchResult.setTermId(keyValue.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.ATP_ID));
            courseSearchResult.setCourseDescription(keyValue.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.LUI_DESC));
            courseSearchResult.setState(keyValue.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.CO_STATE));

            // Set seats available
            String seatsAvailable = keyValue.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.SEATS_AVAILABLE);
            if (StringUtils.isNotEmpty(seatsAvailable)) {
                courseSearchResult.setSeatsAvailable(Integer.parseInt(seatsAvailable));
            }

            // Grab Learning Results data
            // These two calls are cached so they are actually more performant
            List<String> creditResultsValueKeys = lrcService.getResultValuesGroup(keyValue.get(CourseRegistrationSearchServiceImpl.SearchResultColumns.CREDITS), context).getResultValueKeys();
            List<ResultValueInfo> creditResultValues = lrcService.getResultValuesByKeys(creditResultsValueKeys, context);

            //Add all the credits from the result value group
            for (ResultValueInfo creditResultValue : creditResultValues) {
                courseSearchResult.getCreditOptions().add(creditResultValue.getValue());
            }

            courseSearchResults.add(courseSearchResult);
        }

        return courseSearchResults;
    }

    private EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public AtpService getAtpService() {
        return atpService;
    }

    public void setAtpService(AtpService atpService) {
        this.atpService = atpService;
    }

    public LRCService getLrcService() {
        return lrcService;
    }

    public void setLrcService(LRCService lrcService) {
        this.lrcService = lrcService;
    }

    public LuiService getLuiService() {
        return luiService;
    }

    public void setLuiService(LuiService luiService) {
        this.luiService = luiService;
    }

    public KSIdentityServiceHelper getKsIdentityServiceHelper() {
        return ksIdentityServiceHelper;
    }

    public void setKsIdentityServiceHelper(KSIdentityServiceHelper ksIdentityServiceHelper) {
        this.ksIdentityServiceHelper = ksIdentityServiceHelper;
    }

    private Map<String, Integer> getActivityPriorityMap(ContextInfo contextInfo) throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException, DoesNotExistException {
        if(activityPriorityMap == null){
            activityPriorityMap = CourseRegistrationAndScheduleOfClassesUtil.getActivityPriorityMap(contextInfo);
        }
        return activityPriorityMap;
    }
}
