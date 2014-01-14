package org.kuali.student.enrollment.registration.client.service.impl;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.criteria.Predicate;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.kim.api.identity.IdentityService;
import org.kuali.rice.kim.api.identity.entity.EntityDefault;
import org.kuali.rice.kim.api.identity.entity.EntityDefaultQueryResults;
import org.kuali.rice.kim.api.identity.principal.Principal;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.student.common.collection.KSCollectionUtils;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.enrollment.courseofferingset.service.CourseOfferingSetService;
import org.kuali.student.enrollment.lpr.dto.LprInfo;
import org.kuali.student.enrollment.lpr.service.LprService;
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
import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.TimeOfDayInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.TimeOfDayHelper;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.class1.search.ActivityOfferingSearchServiceImpl;
import org.kuali.student.r2.core.class1.search.CoreSearchServiceImpl;
import org.kuali.student.r2.core.class1.search.CourseOfferingManagementSearchImpl;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.class1.type.infc.TypeTypeRelation;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.kuali.student.r2.core.constants.TypeServiceConstants;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.kuali.student.r2.core.search.service.SearchService;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.kuali.student.r2.lum.util.constants.CourseServiceConstants;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.kuali.rice.core.api.criteria.PredicateFactory.equal;

public class ScheduleOfClassesServiceImpl implements ScheduleOfClassesService {
    private SearchService searchService;
    private LprService lprService;
    private IdentityService identityService;
    private AtpService atpService;
    private CourseService courseService;
    private CourseOfferingService courseOfferingService;
    private TypeService typeService;
    private CourseOfferingSetService courseOfferingSetService;
    private Map<String, Integer> activityPriorityMap = null;

    Comparator<RegGroupSearchResult> regResultComparator = new RegResultComparator();


/** COURSE OFFERINGS **/

    @Override
    public List<CourseSearchResult> loadCourseOfferings( String termId, String termCode, String courseCode ) throws Exception {
        termId = getTermId( termId, termCode );
        List<CourseSearchResult> courseSearchResults = searchForCourseOfferings( termId, courseCode );

        return courseSearchResults;
    }

    @Override
    public List<CourseSearchResult> loadCourseOfferingsByTermIdAndCourse( String termId, String courseCode ) throws Exception {
        return loadCourseOfferings( termId, null, courseCode );
    }

    @Override
    public List<CourseSearchResult> loadCourseOfferingsByTermCodeAndCourse( String termCode, String courseCode ) throws Exception {
        return loadCourseOfferings( null, termCode, courseCode );
    }

    @Override
    public List<CourseAndPrimaryAOSearchResult> loadCourseOfferingsAndPrimaryAosByTermAndCourse( String termCode, String courseCode ) throws Exception {

        List<CourseAndPrimaryAOSearchResult> resultList = new ArrayList<CourseAndPrimaryAOSearchResult>();
        List<CourseSearchResult> courseOfferingList = loadCourseOfferingsByTermCodeAndCourse(termCode, courseCode); // search for the course offerings
        ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();  // build defualt context info

        if(courseOfferingList != null && !courseOfferingList.isEmpty()){   // if we found course offerings
            for(CourseSearchResult csr : courseOfferingList){
                CourseAndPrimaryAOSearchResult resultElement = new CourseAndPrimaryAOSearchResult(); // this is the element in the list we will return
                List<ActivityOfferingSearchResult> activityOfferingList = getPrimaryActivityOfferingsByCo(csr.getCourseOfferingId(), contextInfo);

                resultElement.setCourseOfferingInfo(csr);
                resultElement.setPrimaryActivityOfferingInfo(activityOfferingList);
                resultList.add(resultElement);
            }
        }
        return resultList;
    }


/** REGISTRATION GROUPS **/

    @Override
    public List<RegGroupSearchResult> loadRegistrationGroups( String courseOfferingId, String termId, String termCode, String courseCode, String regGroupName ) throws Exception {
        courseOfferingId = getCourseOfferingId(courseOfferingId, courseCode, termId, termCode);
        return getRegGroupList(courseOfferingId, regGroupName);
    }

    @Override
    public RegGroupSearchResult loadRegistrationGroupByTermAndCourseAndRegGroup( String termCode, String courseCode, String regGroupName ) throws Exception {
        List<RegGroupSearchResult> regGroupSearchResults = loadRegistrationGroups( null, null, termCode, courseCode, regGroupName );
        return KSCollectionUtils.getRequiredZeroElement(regGroupSearchResults);
    }


/** ACTIVITY OFFERINGS **/

    @Override
    public List<ActivityOfferingSearchResult> loadActivityOfferings( String courseOfferingId, String termId, String termCode, String courseCode ) throws Exception {
        courseOfferingId = getCourseOfferingId(courseOfferingId, courseCode, termId, termCode );
        List<ActivityOfferingSearchResult> activityOfferingSearchResults = loadPopulatedActivityOfferingsByCourseOfferingId(courseOfferingId, ContextUtils.createDefaultContextInfo());

        return activityOfferingSearchResults;
    }


/** ACTIVITY TYPES **/

    @Override
    public List<ActivityTypeSearchResult> loadActivityTypes( String courseOfferingId, String termCode, String courseCode ) throws Exception {
        courseOfferingId = getCourseOfferingId(courseOfferingId, courseCode, null, termCode );

        // get the FOs for the course offering. Note: FO's contain a list of activity offering type keys
        ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();
        List<FormatOfferingInfo> formatOfferings = getCourseOfferingService().getFormatOfferingsByCourseOffering(courseOfferingId,contextInfo);

        return getActivityTypesForFormatOfferings( formatOfferings, contextInfo );
    }


/** INSTRUCTORS **/

    @Override
    public List<InstructorSearchResult> loadInstructors( String courseOfferingId, String activityOfferingId, String termId, String termCode, String courseCode ) throws Exception {

        if( !StringUtils.isEmpty(activityOfferingId) ) {
            return getInstructorsByActivityOfferingId(activityOfferingId);
        }

        courseOfferingId = getCourseOfferingId( courseOfferingId, courseCode, termId, termCode );
        return getInstructorsByCourseOfferingId(courseOfferingId);
    }


/** TERMS **/

    @Override
    public String getTermIdByTermCode(String atpCode) throws Exception {
        String sRet = null;
        List<AtpInfo> atpList = getAtpService().getAtpsByCode(atpCode, ContextUtils.createDefaultContextInfo());
        if(atpList != null && !atpList.isEmpty()){
            sRet = KSCollectionUtils.getRequiredZeroElement(atpList).getId();
        }

        return sRet;
    }

    @Override
    public List<TermSearchResult> loadActiveTerms() throws Exception {
        List<TermSearchResult> termSearchResults = new ArrayList<TermSearchResult>();
        List<AtpInfo> validAtps = getValidAtps();
        for(AtpInfo atpInfo: validAtps){
            TermSearchResult termSearchResult = new TermSearchResult();
            termSearchResult.setTermId(atpInfo.getId());
            termSearchResult.setTermName(atpInfo.getName());
            termSearchResults.add(termSearchResult);
        }
        return termSearchResults;
    }


/** PRIVATE HELPERS **/


    private List<InstructorSearchResult> getInstructorsByCourseOfferingId( String courseOfferingId ) throws Exception {

        List<ActivityOfferingSearchResult> aoList = searchForRawActivities(courseOfferingId);

        List<String> aoIds = new ArrayList<String>();
        for(ActivityOfferingSearchResult ao : aoList){
            aoIds.add(ao.getActivityOfferingId());
        }

        return getInstructorListByAoIds(aoIds, ContextUtils.createDefaultContextInfo());

    }

    private List<InstructorSearchResult> getInstructorsByActivityOfferingId( String activityOfferingId ) throws Exception {
        List<String> aoIds = new ArrayList<String>();
        aoIds.add(activityOfferingId);
        return getInstructorListByAoIds(aoIds, ContextUtils.createDefaultContextInfo());
    }

    private List<ActivityTypeSearchResult> getActivityTypesForFormatOfferings( List<FormatOfferingInfo> formatOfferings, ContextInfo contextInfo ) throws Exception {

        List<ActivityTypeSearchResult> activitiesTypeKeys = new ArrayList<ActivityTypeSearchResult>();


        // for each FO
        for (FormatOfferingInfo formatOffering : formatOfferings){
            List<String> aoTypeKeys = formatOffering.getActivityOfferingTypeKeys(); // grab the type keys
            List<TypeInfo> typeInfos = getTypeService().getTypesByKeys(aoTypeKeys, contextInfo); // turn those keys into full type objects

            // for each type, build the search result
            for(TypeInfo activityTypeInfo : typeInfos){
                ActivityTypeSearchResult atsr = new ActivityTypeSearchResult();
                atsr.setTypeKey(activityTypeInfo.getKey());
                atsr.setName(activityTypeInfo.getName());

                // I'm not sure what do do here. Guess we return the formatted over the plain?
                if(activityTypeInfo.getDescr() != null){
                    if(activityTypeInfo.getDescr().getFormatted() != null){
                        atsr.setDescription(activityTypeInfo.getDescr().getFormatted());
                    }
                    else {
                        atsr.setDescription(activityTypeInfo.getDescr().getPlain());
                    }
                }
                // This prioirty comes from the configured lu (not lui) type attributes. Somewhat complex mapping
                // to get from the lu -> lui type key.
                atsr.setPriority(getActivityPriorityMap(contextInfo).get(activityTypeInfo.getKey()));
                atsr.setFormatOfferingId(formatOffering.getId());  // Adding FoId to keep data structures flat.
                activitiesTypeKeys.add(atsr);
            }
        }

        return activitiesTypeKeys;

    }

    private String getTermId( String termId, String termCode ) throws Exception {
        if( !StringUtils.isEmpty(termId) ) {
            return termId;
        }

        return getTermIdByTermCode(termCode);
    }

    private List<RegGroupSearchResult> getRegGroupList( String courseOfferingId, String regGroupName ) throws Exception {

        List<RegGroupSearchResult> regGroupSearchResults = searchForRegGroups(courseOfferingId);

        // return a list with a single entity if regGroupName was provided
        if( !StringUtils.isEmpty(regGroupName) ) {
            for(RegGroupSearchResult rg : regGroupSearchResults){
                if(rg.getRegGroupName().equals(regGroupName)){
                    List<RegGroupSearchResult> result = new ArrayList<RegGroupSearchResult>();
                    result.add(rg);
                    return result;
                }
            }
        }

        return new ArrayList<RegGroupSearchResult>( regGroupSearchResults );
    }

    private String getCourseOfferingId( String courseOfferingId, String courseCode, String termId, String termCode ) throws Exception {
        if( !StringUtils.isEmpty(courseOfferingId) ) {
            return courseOfferingId;
        }

        termId = getTermId(termId, termCode);
        List<String> coIds = searchForCourseOfferingIdByCourseCodeAndTerm(courseCode, termId);

        return KSCollectionUtils.getRequiredZeroElement(coIds);
    }

    /**
     *  There is a business case where the user wants a list of primary activity offerings for a particular
     *  course offering. Ie. CHEM 237 has a format offering of Lecture, Lab, Discussion. There are 6 lectures, 3 labs,
     *  and three discussions.
     *
     *  If the system is configured so Lecture is the highest priority, then this method would return a list of the
     *  6 activity offerings of type lecture.
     *
     * @param coId Course Offering Id
     * @param contextInfo
     * @return A list of Primary Activity Offerings for a particular Course Offering
     * @throws Exception
     */
    private List<ActivityOfferingSearchResult> getPrimaryActivityOfferingsByCo(String coId, ContextInfo contextInfo) throws Exception{

        List<ActivityOfferingSearchResult> resultList = new ArrayList<ActivityOfferingSearchResult>();

        // get a list of format offerings for this particular co. ie: CHEM231 has 1 FO: Lecture / Discussion
        // there is a possibility that a course can offer multiple formats. ie. "Lecture / Discussion"  and "Lecture / Web Discussion"
        List<FormatOfferingInfo> formatOfferings = getCourseOfferingService().getFormatOfferingsByCourseOffering(coId,contextInfo);

        // Because there can be multiple formats [Lec/lab, lab/disc] we need to seperate our results by the FoId. That is because if there are
        // multiple formats, the primary for one FO could be Lecture, while another FO could be Discussion.
        // Map<FoId, List<ActivityOfferings>>
        Map<String, List<ActivityOfferingSearchResult>> aoMap = groupActivityOfferingsByFormatOfferingId(loadActivityOfferings(coId, null, null, null));

        for(FormatOfferingInfo formatOffering : formatOfferings){
            sortActivityOfferingTypeKeyList(formatOffering.getActivityOfferingTypeKeys(), contextInfo);  // sort the activity offerings type keys by priority order
            String primaryTypeKey = formatOffering.getActivityOfferingTypeKeys().get(0);

            for(ActivityOfferingSearchResult ao : aoMap.get(formatOffering.getId())){
                if(primaryTypeKey.equals(ao.getActivityOfferingType())){
                    resultList.add(ao);
                }
            }
        }
        return resultList;
    }

    /**
     * Because there can be multiple formats [Lec/lab, lab/disc] we need to seperate our results by the FoId. That is because if there are
     * multiple formats, the primary for one FO could be Lecture, while another FO could be Discussion.
     *
     * Note: Most, 98% of all courses only have one format, but this needs to be here for the rare case.
     *
     * @param aoList  list of activity offerings
     * @return    Map<FormatOfferingId, List<ActivityOfferings>>
     */
    private Map<String, List<ActivityOfferingSearchResult>> groupActivityOfferingsByFormatOfferingId(List<ActivityOfferingSearchResult> aoList){
        Map<String, List<ActivityOfferingSearchResult>> retMap = new HashMap<String, List<ActivityOfferingSearchResult>>();
        for(ActivityOfferingSearchResult ao : aoList){
            if(!retMap.containsKey(ao.getFormatOfferingId())){
                retMap.put(ao.getFormatOfferingId(), new ArrayList<ActivityOfferingSearchResult>());
            }
            retMap.get(ao.getFormatOfferingId()).add(ao);
        }
        return retMap;
    }


    /**
     * This method takes in a list of activity offering type keys and sorts them in priority order. The priority
     * order comes from the priority established in the Activity Types.
     * @param typeKeys  list of activity offering type keys
     * @param contextInfo
     */
    private void sortActivityOfferingTypeKeyList(List<String> typeKeys, final ContextInfo contextInfo) {
        Collections.sort(typeKeys, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                int val1 = 0;
                int val2 = 0;
                try{
                    val1 = getActivityPriorityMap(contextInfo).get(o1).intValue();
                    val2 = getActivityPriorityMap(contextInfo).get(o2).intValue();
                }catch (Exception ex){
                    // I'm not sure if this is the correct thing to do here.
                    throw new RuntimeException("Failed to sort activity offering types", ex);
                }

                return (val1<val2 ? -1 : (val1==val2 ? 0 : 1));
            }
        });
    }

    /**
     * This method grabs all kuali.lu.type.grouping.activity types. These types have attributes with priority values.
     * Since we are interested in the priority of the lui types, not the lu types we then need to get the type type
     * relation between lu and lui.
     *
     * Ex:
     * Lu.Lucture priority = 1; Lu.Lecture has a 1:1 relation with Lui.Lecture so we say
     * the Lui.Lecture.priority = Lu.Lecture.priority
     *
     * These values never change so I'm storing them in a map inside the service for performance reasons.
     *
     * So, if a course offering has Lec, Lab, Discussion and you want to know which is the Primary type, just get the
     * lowest priority of the ao type.
     *
     * @param contextInfo
     * @return  Map<ActivityOfferingTypeKey, priorityInt>
     * @throws Exception
     */
    private Map<String, Integer> getActivityPriorityMap(ContextInfo contextInfo) throws Exception{
        if(activityPriorityMap == null){
            activityPriorityMap = new HashMap<String, Integer>();
            List<TypeInfo> activityTypes = getTypeService().getTypesForGroupType("kuali.lu.type.grouping.activity", contextInfo);

            for(TypeInfo typeInfo : activityTypes){
                List<AttributeInfo> attributes = typeInfo.getAttributes();
                if(attributes != null && !attributes.isEmpty()){
                    for(AttributeInfo attribute : attributes){
                        if(TypeServiceConstants.ACTIVITY_SELECTION_PRIORITY_ATTR.equals(attribute.getKey())){
                            TypeTypeRelation typeTypeRelation = KSCollectionUtils.getRequiredZeroElement(getTypeService().getTypeTypeRelationsByOwnerAndType(typeInfo.getKey(), TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, contextInfo));
                            activityPriorityMap.put(typeTypeRelation.getRelatedTypeKey(), new Integer(Integer.parseInt(attribute.getValue())));
                        }
                    }
                }
            }


        }

        return activityPriorityMap;
    }

    private List<AtpInfo> getValidAtps() {
        List<SocInfo> socs;
        List<String> termIds = new ArrayList<String>();
        List<AtpInfo> atps = new ArrayList<AtpInfo>();
        courseOfferingSetService = getCourseOfferingSetService();
        atpService = getAtpService();
        // Build a predicate to search for published Socs
        QueryByCriteria.Builder qBuilder = QueryByCriteria.Builder.create();
        qBuilder.setPredicates();
        Predicate pred = equal(CourseOfferingSetServiceConstants.SearchParameters.SOC_STATE, CourseOfferingSetServiceConstants.PUBLISHED_SOC_STATE_KEY);
        qBuilder.setPredicates(pred);
        try {
            socs = courseOfferingSetService.searchForSocs(qBuilder.build(), ContextUtils.createDefaultContextInfo());
            if (socs != null && socs.size() > 0) {
                for(SocInfo soc: socs){
                    // Add all published Soc termIds to termIds List
                    termIds.add(soc.getTermId());
                }
                // Use AtpService to get Term name by Id
                atps = atpService.getAtpsByIds(termIds, ContextUtils.createDefaultContextInfo());
            } else {
                return atps;
            }
        } catch (Exception e) {
            throw new RuntimeException("Error getting Valid SOC Terms", e);
        }
        return atps;
    }

    private List<CourseSearchResult> searchForCourseOfferings(String termId, String courseCode) throws InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException {

        SearchRequestInfo searchRequest = createSearchRequest(termId, courseCode);
        SearchResultInfo searchResult = getSearchService().search(searchRequest, ContextUtils.createDefaultContextInfo());


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
        SearchResultInfo searchResult = getSearchService().search(searchRequest, ContextUtils.createDefaultContextInfo());

        Map<String, RegGroupSearchResult> regGroupResultMap = new HashMap<String, RegGroupSearchResult>();


        for (SearchResultRowInfo row : searchResult.getRows()) {


            String activityOfferingId = null;
            String regGroupId = null;
            String regGroupName = null;
            String regGroupState = null;

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
     * @param courseOfferingId
     * @return
     * @throws Exception
     */
    private List<ActivityOfferingSearchResult> searchForRawActivities(String courseOfferingId) throws Exception {
        SearchRequestInfo searchRequestInfo = new SearchRequestInfo(ActivityOfferingSearchServiceImpl.AOS_AND_CLUSTERS_BY_CO_ID_SEARCH_KEY);

        searchRequestInfo.addParam(ActivityOfferingSearchServiceImpl.SearchParameters.CO_ID, courseOfferingId);
        SearchResultInfo searchResult = getSearchService().search(searchRequestInfo, ContextUtils.createDefaultContextInfo());

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

    private List<String> searchForCourseOfferingIdByCourseCodeAndTerm(String courseCode, String atpId) throws Exception {
        List<String> resultList = new ArrayList<String>();



        SearchRequestInfo searchRequestInfo = new SearchRequestInfo(CourseOfferingManagementSearchImpl.COID_BY_TERM_AND_COURSE_CODE_SEARCH_SEARCH_KEY);

        searchRequestInfo.addParam(CourseOfferingManagementSearchImpl.SearchParameters.COURSE_CODE, courseCode);
        searchRequestInfo.addParam(CourseOfferingManagementSearchImpl.SearchParameters.ATP_ID, atpId);

        SearchResultInfo searchResult = getSearchService().search(searchRequestInfo, ContextUtils.createDefaultContextInfo());


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
     * @param scheduleIds   list of schedule Ids to retrieve from db
     * @param contextInfo
     * @return
     * @throws Exception
     */
    protected Map<String, ScheduleSearchResult> searchForScheduleByScheduleIds(List<String> scheduleIds, ContextInfo contextInfo) throws Exception {
        Map<String, ScheduleSearchResult> resultList = new HashMap<String, ScheduleSearchResult>();

        SearchRequestInfo sr = new SearchRequestInfo(CoreSearchServiceImpl.SCH_AND_ROOM_SEARH_BY_ID_SEARCH_KEY);
        sr.addParam(CoreSearchServiceImpl.SearchParameters.SCHEDULE_IDS, new ArrayList<String>(scheduleIds));
        SearchResultInfo searchResult  = getSearchService().search(sr, contextInfo);

        for (SearchResultRowInfo row : searchResult.getRows()) {
            ScheduleSearchResult searchResultRow = new ScheduleSearchResult();
            for (SearchResultCellInfo cell : row.getCells()) {
                String value = StringUtils.EMPTY;
                if (cell.getValue() != null) {
                    value = cell.getValue();
                }
                if (CoreSearchServiceImpl.SearchResultColumns.SCH_ID.equals(cell.getKey())) {
                    searchResultRow.setScheduleId(value);
                }else if (CoreSearchServiceImpl.SearchResultColumns.START_TIME.equals(cell.getKey())) {
                    searchResultRow.setStartTimeMili(value);
                    if(value != null && !value.isEmpty()){
                        TimeOfDayInfo tod = TimeOfDayHelper.setMillis(Long.parseLong(value));
                        searchResultRow.setStartTimeDisplay(convertTimeOfDayToDisplayTime(tod));
                    }
                }else if (CoreSearchServiceImpl.SearchResultColumns.END_TIME.equals(cell.getKey())) {
                    searchResultRow.setEndTimeMili(value);
                    if(value != null && !value.isEmpty()){
                        TimeOfDayInfo tod = TimeOfDayHelper.setMillis(Long.parseLong(value));
                        searchResultRow.setEndTimeDisplay(convertTimeOfDayToDisplayTime(tod));
                    }
                }else if (CoreSearchServiceImpl.SearchResultColumns.TBA_IND.equals(cell.getKey())) {
                    searchResultRow.setTba(Boolean.parseBoolean(value));
                }else if (CoreSearchServiceImpl.SearchResultColumns.ROOM_CODE.equals(cell.getKey())) {
                    searchResultRow.setRoomName(value);
                }else if (CoreSearchServiceImpl.SearchResultColumns.BLDG_NAME.equals(cell.getKey())) {
                    searchResultRow.setBuildingName(value);
                }else if (CoreSearchServiceImpl.SearchResultColumns.BLDG_CODE.equals(cell.getKey())) {
                    searchResultRow.setBuildingCode(value);
                }else if (CoreSearchServiceImpl.SearchResultColumns.WEEKDAYS.equals(cell.getKey())) {
                    searchResultRow.setDays(value);
                }else if (CoreSearchServiceImpl.SearchResultColumns.SCH_ID.equals(cell.getKey())) {
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
     * @param aoIds
     * @return
     * @throws Exception
     */
    private  List<InstructorSearchResult> getInstructorListByAoIds(List<String> aoIds, ContextInfo contextInfo) throws Exception {
        List<InstructorSearchResult> resultList = new ArrayList<InstructorSearchResult>();
        Map<String, List<InstructorSearchResult>> resultMap = searchForInstructorsByAoIds(aoIds, contextInfo);

        if(resultMap != null && !resultMap.isEmpty()){
            for(List<InstructorSearchResult> insrList : resultMap.values()){
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
     * @param aoList list of ActivityOfferingSearchResults that will be modified to include schedule data.
     * @param contextInfo
     * @throws Exception
     */
    private void populateActivityOfferingsWithScheduleData(List<ActivityOfferingSearchResult> aoList, ContextInfo contextInfo) throws Exception {
        List<String> scheduleIds = new ArrayList<String>();

        for(ActivityOfferingSearchResult ao : aoList){
            String scheduleId = ao.getScheduleId();
            if(scheduleId != null && !"".equals(scheduleId)){
                scheduleIds.add(scheduleId);
            }
        }

        Map<String, ScheduleSearchResult> schedMap = searchForScheduleByScheduleIds(scheduleIds, contextInfo);

        if(schedMap != null && !schedMap.isEmpty()){
            for(ActivityOfferingSearchResult ao : aoList){
                String scheduleId = ao.getScheduleId();
                if(scheduleId != null && !"".equals(scheduleId) && schedMap.containsKey(scheduleId)){
                    ao.setSchedule(convertScheduleSearchResultToAOSchedComponent(schedMap.get(scheduleId)));
                }
            }

        }
    }

    private ActivityOfferingScheduleComponentResult convertScheduleSearchResultToAOSchedComponent(ScheduleSearchResult scheduleSearchResult){
        ActivityOfferingScheduleComponentResult ret = new ActivityOfferingScheduleComponentResult();
        if(scheduleSearchResult != null){
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
     * @param contextInfo
     * @throws Exception
     */
    private void populateActivityOfferingsWithInstructorData(List<ActivityOfferingSearchResult> aoList, ContextInfo contextInfo) throws Exception {
        List<String> aoIds = new ArrayList<String>();

        for(ActivityOfferingSearchResult ao : aoList){
            String aoId = ao.getActivityOfferingId();
            if(aoId != null && !"".equals(aoId)){
                aoIds.add(aoId);
            }
        }

        Map<String, List<InstructorSearchResult>> instMap = searchForInstructorsByAoIds(aoIds, contextInfo);

        if(instMap != null && !instMap.isEmpty()){
            for(ActivityOfferingSearchResult ao : aoList){
                String aoId = ao.getActivityOfferingId();
                if(aoId != null && !"".equals(aoId) && instMap.containsKey(aoId)){
                    ao.setInstructors(instMap.get(aoId));
                }
            }
        }
    }

    private void populateActivityOfferingsWithActivityTypeNames(List<ActivityOfferingSearchResult> aoList, ContextInfo contextInfo) throws Exception {
        for(ActivityOfferingSearchResult ao : aoList){
            String aoTypeKey = ao.getActivityOfferingType();
            if(aoTypeKey != null && !"".equals(aoTypeKey)){
                String aoTypeName = getTypeService().getType(aoTypeKey, contextInfo).getName();
                ao.setActivityOfferingTypeName(aoTypeName);
            }
        }
    }

    /**
     * We're making this a protected method so that implementing institutions can extend this class and change the way
     * that times are displayed.
     * @param tod Tune Of Day object
     * @return
     */
    protected String convertTimeOfDayToDisplayTime(TimeOfDayInfo tod){
        String sRet = "";
        if (tod != null){
            sRet = TimeOfDayHelper.makeFormattedTimeForAOSchedules(tod);
        }
        return sRet;

    }

    /**
     * This is an internal method that will return a map of aoId, InstructorSearchResult. We are using a map object
     * so it is easier to build up complex objects in a more performant way
     *
     * @param aoIds
     * @param contextInfo
     * @return Map<activityOfferingId, List<InstructorSearchResult>>
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws DoesNotExistException
     * @throws PermissionDeniedException
     * @throws OperationFailedException
     */
    protected Map<String, List<InstructorSearchResult>> searchForInstructorsByAoIds(List<String> aoIds, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException {

        Map<String, List<InstructorSearchResult>> resultList = new HashMap<String, List<InstructorSearchResult>>();
        Map<String, InstructorSearchResult> principalId2aoIdMap = new HashMap<String, InstructorSearchResult>();

        List<LprInfo> lprInfos = getLprService().getLprsByLuis(aoIds, contextInfo);
        if (lprInfos != null) {

            for (LprInfo lprInfo : lprInfos) {
                InstructorSearchResult result = new InstructorSearchResult();

                String aoId = lprInfo.getLuiId();
                //  Only include the main instructor.
                if (!StringUtils.equals(lprInfo.getTypeKey(), LprServiceConstants.INSTRUCTOR_MAIN_TYPE_KEY)) {
                    result.setPrimary(false);
                }else{
                    result.setPrimary(true);
                }
                result.setPrincipalId(lprInfo.getPersonId());
                principalId2aoIdMap.put(lprInfo.getPersonId(), result);
                result.setActivityOfferingId(aoId);

                if(resultList.containsKey(aoId)){
                  resultList.get(aoId).add(result);
                } else{
                    List<InstructorSearchResult> newList = new ArrayList<InstructorSearchResult>();
                    newList.add(result);
                    resultList.put(aoId, newList);
                }
            }

            // if we have a list of instructors
            if (!resultList.isEmpty()) {
                // get the instructor entities from KIM.
                EntityDefaultQueryResults results = getInstructorsInfoFromKim(new ArrayList<String>(principalId2aoIdMap.keySet()));

                for (EntityDefault entity : results.getResults()) {
                    // Each KIM entity can have multiple principals. So we need to loop through the principals
                    for (Principal principal : entity.getPrincipals()) {
                        if(principalId2aoIdMap.containsKey(principal.getPrincipalId())){  // does this principal match the ks instructor
                            InstructorSearchResult instructor = principalId2aoIdMap.get(principal.getPrincipalId());
                            populateInstructorWithEntityInfo(instructor, entity);  // populate the instructor with KIM information
                        }
                    }
                }

            }
        }
        return resultList;
    }

    /**
     * This method populates the InstructorSearchResult object with infor from the Kim entity
     * @param instructor Kuali Student Instructor
     * @param entity     Kim Entity object
     */
    private void populateInstructorWithEntityInfo(InstructorSearchResult instructor, EntityDefault entity){
        if(entity.getName() != null){
           instructor.setDisplayName(entity.getName().getCompositeName());
           instructor.setFirstName(entity.getName().getFirstName());
           instructor.setLastName(entity.getName().getLastName());
       }
    }

    class RegResultComparator implements Comparator<RegGroupSearchResult> {
        // Note: this comparator imposes orderings that are inconsistent with equals.
        public int compare(RegGroupSearchResult a, RegGroupSearchResult b) {
            return a.getRegGroupName().compareToIgnoreCase(b.getRegGroupName());
        }
    }

    protected EntityDefaultQueryResults getInstructorsInfoFromKim(List<String> principalIds) {
        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(
                PredicateFactory.in("principals.principalId", principalIds.toArray())
        );

        QueryByCriteria criteria = qbcBuilder.build();

        EntityDefaultQueryResults entityResults = getIdentityService().findEntityDefaults(criteria);

        return entityResults;
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
     * @param courseOfferingId
     * @param contextInfo
     * @return
     * @throws Exception
     */
    private List<ActivityOfferingSearchResult> loadPopulatedActivityOfferingsByCourseOfferingId(String courseOfferingId, ContextInfo contextInfo) throws Exception {
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

    private SearchService getSearchService() {
        if (searchService == null) {
            searchService = (SearchService) GlobalResourceLoader.getService(new QName(CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "search", SearchService.class.getSimpleName()));
        }
        return searchService;
    }

    public LprService getLprService() {
        if (lprService == null){
            lprService = (LprService) GlobalResourceLoader.getService(new QName(LprServiceConstants.NAMESPACE, LprServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return lprService;
    }

    public void setLprService(LprService lprService) {
        this.lprService = lprService;
    }

    public IdentityService getIdentityService() {
        if (identityService == null) {
            identityService = KimApiServiceLocator.getIdentityService();
        }
        return identityService;
    }

    public void setIdentityService(IdentityService identityService) {
        this.identityService = identityService;
    }

    public AtpService getAtpService() {
        if (atpService == null){
            atpService = (AtpService) GlobalResourceLoader.getService(new QName(AtpServiceConstants.NAMESPACE, AtpServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return atpService;
    }

    public void setAtpService(AtpService atpService) {
        this.atpService = atpService;
    }

    public CourseService getCourseService() {
        if (courseService == null) {
            QName qname = new QName(CourseServiceConstants.NAMESPACE,
                    CourseServiceConstants.SERVICE_NAME_LOCAL_PART);
            courseService = GlobalResourceLoader.getService(qname);
        }
        return courseService;
    }

    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }

    public CourseOfferingService getCourseOfferingService() {
        if(courseOfferingService == null) {
            courseOfferingService =  GlobalResourceLoader.getService(new QName(CourseOfferingServiceConstants.NAMESPACE,
                    CourseOfferingServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return courseOfferingService;
    }

    public void setCourseOfferingService(CourseOfferingService courseOfferingService) {
        this.courseOfferingService = courseOfferingService;
    }

    public TypeService getTypeService() {
        if(typeService == null) {
            typeService =  GlobalResourceLoader.getService(new QName(TypeServiceConstants.NAMESPACE,
                    TypeServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return typeService;
    }

    public void setTypeService(TypeService typeService) {
        this.typeService = typeService;
    }

    public CourseOfferingSetService getCourseOfferingSetService() {
        if(courseOfferingSetService == null) {
            courseOfferingSetService =  GlobalResourceLoader.getService(new QName(CourseOfferingSetServiceConstants.NAMESPACE,
                    CourseOfferingSetServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return courseOfferingSetService;
    }

    public void setCourseOfferingSetService(CourseOfferingSetService courseOfferingSetService) {
        this.courseOfferingSetService = courseOfferingSetService;
    }
}
