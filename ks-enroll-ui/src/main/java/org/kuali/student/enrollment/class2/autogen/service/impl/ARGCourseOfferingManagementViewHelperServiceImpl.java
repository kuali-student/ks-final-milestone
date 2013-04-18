/**
 * Copyright 2013 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 */
package org.kuali.student.enrollment.class2.autogen.service.impl;

import org.apache.commons.collections.iterators.EntrySetMapIterator;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.kim.api.identity.IdentityService;
import org.kuali.rice.kim.api.identity.entity.EntityDefault;
import org.kuali.rice.kim.api.identity.entity.EntityDefaultQueryResults;
import org.kuali.rice.kim.api.identity.principal.Principal;
import org.kuali.rice.kim.api.permission.PermissionService;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.student.enrollment.class2.autogen.controller.ARGUtil;
import org.kuali.student.enrollment.class2.autogen.dto.ScheduleCalcContainer;
import org.kuali.student.enrollment.class2.autogen.dto.ScheduleRequestCalcContainer;
import org.kuali.student.enrollment.class2.autogen.form.ARGCourseOfferingManagementForm;
import org.kuali.student.enrollment.class2.autogen.service.ARGCourseOfferingManagementViewHelperService;
import org.kuali.student.enrollment.class2.autogen.util.ARGToolbarUtil;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingClusterWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingListSectionWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.RegistrationGroupWrapper;
import org.kuali.student.enrollment.class2.courseoffering.service.impl.CO_AO_RG_ViewHelperServiceImpl;
import org.kuali.student.enrollment.class2.courseoffering.service.util.RegistrationGroupUtil;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingViewHelperUtil;
import org.kuali.student.enrollment.class2.courseoffering.util.ManageSocConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.RegistrationGroupConstants;
import org.kuali.student.enrollment.class2.scheduleofclasses.dto.ActivityOfferingDisplayWrapper;
import org.kuali.student.enrollment.class2.scheduleofclasses.util.ScheduleOfClassesConstants;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingClusterInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingDisplayInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingSetInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.enrollment.courseofferingset.service.CourseOfferingSetService;
import org.kuali.student.enrollment.lpr.dto.LprInfo;
import org.kuali.student.enrollment.lpr.service.LprService;
import org.kuali.student.enrollment.uif.util.GrowlIcon;
import org.kuali.student.enrollment.uif.util.KSUifUtils;
import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.TimeOfDayInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.infc.ValidationResult;
import org.kuali.student.r2.common.permutation.PermutationUtils;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.core.acal.dto.KeyDateInfo;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.class1.search.ActivityOfferingSearchServiceImpl;
import org.kuali.student.r2.core.class1.search.CoreSearchServiceImpl;
import org.kuali.student.r2.core.class1.search.CourseOfferingManagementSearchImpl;
import org.kuali.student.r2.core.class1.state.dto.StateInfo;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.class1.type.dto.TypeTypeRelationInfo;
import org.kuali.student.r2.core.constants.AcademicCalendarServiceConstants;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.kuali.student.r2.core.constants.TypeServiceConstants;
import org.kuali.student.r2.core.room.dto.BuildingInfo;
import org.kuali.student.r2.core.room.dto.RoomInfo;
import org.kuali.student.r2.core.scheduling.constants.SchedulingServiceConstants;
import org.kuali.student.r2.core.scheduling.dto.ScheduleComponentDisplayInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestComponentInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestInfo;
import org.kuali.student.r2.core.scheduling.dto.TimeSlotInfo;
import org.kuali.student.r2.core.scheduling.infc.ScheduleComponentDisplay;
import org.kuali.student.r2.core.scheduling.util.SchedulingServiceUtil;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.kuali.student.r2.core.search.service.SearchService;
import org.kuali.student.r2.lum.course.dto.ActivityInfo;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.dto.CourseJointInfo;
import org.kuali.student.r2.lum.course.dto.FormatInfo;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.kuali.student.r2.lum.lrc.service.LRCService;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class ARGCourseOfferingManagementViewHelperServiceImpl extends CO_AO_RG_ViewHelperServiceImpl implements ARGCourseOfferingManagementViewHelperService {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(ARGCourseOfferingManagementViewHelperServiceImpl.class);

    private AcademicCalendarService acalService = null;
    private CourseOfferingService coService = null;
    private SearchService searchService = null;

    private CourseService courseService;
    private LRCService lrcService;
    private AtpService atpService;
    private CourseOfferingSetService socService;

    private static LprService lprService;
    private static PermissionService permissionService;
    private static IdentityService identityService;

    /**
     * This method fetches the <code>TermInfo</code> and validate for exact match
     *
     * @param form
     * @throws Exception
     */
    public void populateTerm(ARGCourseOfferingManagementForm form) throws Exception {

        String termCode = form.getTermCode();

        form.getCourseOfferingResultList().clear();

        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(PredicateFactory.equal("atpCode", termCode));

        QueryByCriteria criteria = qbcBuilder.build();

        List<TermInfo> terms = getAcalService().searchForTerms(criteria, createContextInfo());

        if (terms.isEmpty()){
            GlobalVariables.getMessageMap().putError("termCode", CourseOfferingConstants.COURSEOFFERING_MSG_ERROR_NO_TERM_IS_FOUND, termCode);
        } else if (terms.size() > 1){
            GlobalVariables.getMessageMap().putError("termCode", CourseOfferingConstants.COURSEOFFERING_MSG_ERROR_FOUND_MORE_THAN_ONE_TERM, termCode);
        } else {
            form.setTermInfo(terms.get(0));

            //Checking soc
            List<String> socIds;
            try {
                socIds = getSocService().getSocIdsByTerm(form.getTermInfo().getId(), createContextInfo());
            } catch (Exception e){
                throw convertServiceExceptionsToUI(e);
            }

            if (socIds.isEmpty()){
             GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, ManageSocConstants.MessageKeys.ERROR_SOC_NOT_EXISTS);
            } else {
                setSocStateKeys(form, socIds);

                // setting term first day of classes
                List<KeyDateInfo> keyDateInfoList = getAcalService().getKeyDatesForTerm(form.getTermInfo().getId(), createContextInfo());
                Date termClassStartDate = null;
                for (KeyDateInfo keyDateInfo : keyDateInfoList) {
                    if (keyDateInfo.getTypeKey().equalsIgnoreCase(AtpServiceConstants.MILESTONE_SEATPOOL_FIRST_DAY_OF_CLASSES_TYPE_KEY) && keyDateInfo.getStartDate() != null) {
                        termClassStartDate = keyDateInfo.getStartDate();
                        break;
                    }
                }
                form.setTermClassStartDate(termClassStartDate);
                }
        }

    }

    /**
     * This method loads all the course offerings for a term and course code.
     *
     * @param termId
     * @param courseCode
     * @param form
     * @throws Exception
     */
    public void loadCourseOfferingsByTermAndCourseCode(String termId, String courseCode, ARGCourseOfferingManagementForm           form) throws Exception {

        SearchRequestInfo searchRequest = new SearchRequestInfo(CourseOfferingManagementSearchImpl.CO_MANAGEMENT_SEARCH.getKey());
        searchRequest.addParam(CourseOfferingManagementSearchImpl.SearchParameters.COURSE_CODE, courseCode);
        searchRequest.addParam(CourseOfferingManagementSearchImpl.SearchParameters.ATP_ID, termId);
        searchRequest.addParam(CourseOfferingManagementSearchImpl.SearchParameters.CROSS_LIST_SEARCH_ENABLED, BooleanUtils.toStringTrueFalse(true));

        loadCourseOfferings(searchRequest, form);

        if (form.getCourseOfferingResultList().isEmpty()){
            LOG.error("Error: Can't find any Course Offering for a Course Code: " + courseCode + " in term: " + termId);
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, CourseOfferingConstants.COURSEOFFERING_MSG_ERROR_NO_COURSE_OFFERING_IS_FOUND, "Course Code", courseCode, termId);
        }

    }

    /**
     * This method loads all the course offerings for a term and subject area/code.
     *
     * @param termId term id
     * @param subjectCode subject area
     * @param form course offering management form
     * @throws Exception
     */
    public void loadCourseOfferingsByTermAndSubjectCode(String termId, String subjectCode, ARGCourseOfferingManagementForm           form) throws Exception {

        SearchRequestInfo searchRequest = new SearchRequestInfo(CourseOfferingManagementSearchImpl.CO_MANAGEMENT_SEARCH.getKey());
        searchRequest.addParam(CourseOfferingManagementSearchImpl.SearchParameters.SUBJECT_AREA, subjectCode);
        searchRequest.addParam(CourseOfferingManagementSearchImpl.SearchParameters.ATP_ID, termId);
        searchRequest.addParam(CourseOfferingManagementSearchImpl.SearchParameters.CROSS_LIST_SEARCH_ENABLED, BooleanUtils.toStringTrueFalse(true));

        loadCourseOfferings(searchRequest, form);

        if (form.getCourseOfferingResultList().isEmpty()){
            LOG.error("Error: Can't find any Course Offering for a Subject Code: " + subjectCode + " in term: " + termId);
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, CourseOfferingConstants.COURSEOFFERING_MSG_ERROR_NO_COURSE_OFFERING_IS_FOUND, "Subject Code", subjectCode, termId);
        }

    }

    /**
     * This method fetches, prepares and sets
     *  activityWrapperList for View All Activities tab,
     *  clusterResultList for View byClusters
     *  rgResultList for View Registration Groupsall the course offerings for a term and course/subject code.
     *
     * @param form
     * @param theCourseOffering
     * @throws Exception
     */
    public void build_AOs_RGs_AOCs_Lists_For_TheCourseOffering (ARGCourseOfferingManagementForm form, CourseOfferingWrapper theCourseOffering) throws Exception{
       //KSENROLL-6102 performance improvements, delete this code when performance work is complete
        //First cleanup and reset three lists
//        List<ActivityOfferingWrapper> activityWrapperList = new ArrayList<ActivityOfferingWrapper>();
//        List<ActivityOfferingClusterWrapper> clusterResultList = new ArrayList<ActivityOfferingClusterWrapper>();
//        List<RegistrationGroupWrapper> rgResultList = new ArrayList<RegistrationGroupWrapper>();
//
//        form.setActivityWrapperList(activityWrapperList);
//        form.setClusterResultList(clusterResultList);
//        form.setRgResultList(rgResultList);
//
//        List<FormatOfferingInfo> formatOfferingList = getCourseOfferingService().getFormatOfferingsByCourseOffering(theCourseOffering.getId(),ContextUtils.createDefaultContextInfo());
//        if  (formatOfferingList.size()>1){
//            form.setHasMoreThanOneFormat(true);
//        }
//        else {
//            form.setHasMoreThanOneFormat(false);
//        }
//        int numberOfClusters = 0;
//        for (FormatOfferingInfo foInfo : formatOfferingList) {
//            List<ActivityOfferingClusterInfo> aoClusterList = getCourseOfferingService().getActivityOfferingClustersByFormatOffering(foInfo.getId(), ContextUtils.createDefaultContextInfo());
//            numberOfClusters=+aoClusterList.size();
//
//            // convert List<ActivityOfferingClusterInfo> to List<ActivityOfferingClusterWrapper>
//            // and set it to the Form
//            List<ActivityOfferingClusterWrapper> aoClusterWrapperList = new ArrayList<ActivityOfferingClusterWrapper>();
//            int clusterIndex =0;
//            for (ActivityOfferingClusterInfo aoCluster: aoClusterList) {
//                ActivityOfferingClusterWrapper aoClusterWrapper = _buildAOClusterWrapper (foInfo, aoCluster, form, clusterIndex);
//                aoClusterWrapperList.add(aoClusterWrapper);
//                clusterIndex++;
//            }
//            clusterResultList.addAll(aoClusterWrapperList);
//        }
//        if (numberOfClusters>1) {
//            form.setHasMoreThanOneCluster(true);
//        }
//        else {
//            form.setHasMoreThanOneCluster(false);
//        }
        //unnecessary
//        form.setClusterResultList(clusterResultList);


        //New Search Stuff!
        String coId =  form.getCurrentCourseOfferingWrapper().getCourseOfferingId();

        //First search for AOs and Cluster information
        SearchRequestInfo sr = new SearchRequestInfo(ActivityOfferingSearchServiceImpl.AOS_AND_CLUSTERS_BY_CO_ID_SEARCH_KEY);
        sr.addParam(ActivityOfferingSearchServiceImpl.SearchParameters.CO_ID,coId);
        SearchResultInfo results = searchService.search(sr, null);

        Map<String, ActivityOfferingWrapper> sch2aoMap = new HashMap<String, ActivityOfferingWrapper>();
        List<String> aoIdsWithoutSch = new ArrayList<String>();
        Map<String, ActivityOfferingClusterWrapper> clusterMap = new HashMap<String, ActivityOfferingClusterWrapper>();
        Map<String, ActivityOfferingWrapper> aoMap = new HashMap<String, ActivityOfferingWrapper>();
        Map<String, List<String>> foIds = new HashMap<String,List<String>>();
        Map<String, List<ScheduleCalcContainer>> ao2sch = new HashMap<String, List<ScheduleCalcContainer>>();
        Map<String, List<ScheduleRequestCalcContainer>> ao2schReq = new HashMap<String, List<ScheduleRequestCalcContainer>>();
        ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();

        //Parse the search results
        List<ActivityOfferingWrapper> wrappers = processAoClusterData(results, form, sch2aoMap, clusterMap, aoMap, foIds, aoIdsWithoutSch, contextInfo);

        //Set the items in the form
        form.setActivityWrapperList(wrappers);
        form.getClusterResultList().clear();
        form.getClusterResultList().addAll(clusterMap.values());

        //Get the mapping of formatids to AO types
        processRelatedTypeKeysForFos(coId, foIds, contextInfo);

        form.setFoId2aoTypeMap(foIds);

        if(!aoMap.keySet().isEmpty()){
            //Process Colocated
            sr = new SearchRequestInfo(ActivityOfferingSearchServiceImpl.COLOCATED_AOS_BY_AO_IDS_SEARCH_KEY);
            sr.addParam(ActivityOfferingSearchServiceImpl.SearchParameters.AO_IDS, new ArrayList<String>(aoMap.keySet()));
            results = searchService.search(sr, null);

            processColocated(results, aoMap);


            //Addin LPR data
            processInstructors(aoMap, ContextUtils.createDefaultContextInfo());

            //Search for schedule information
            sr = new SearchRequestInfo(CoreSearchServiceImpl.SCH_AND_ROOM_SEARH_BY_ID_SEARCH_KEY);
            sr.addParam(CoreSearchServiceImpl.SearchParameters.SCHEDULE_IDS, new ArrayList<String>(sch2aoMap.keySet()));
            results = searchService.search(sr, null);

            //processSchData(results, sch2aoMap, aoIdsWithoutSch, aoMap, ContextUtils.createDefaultContextInfo());

            // the next two methods pull scheduling data from the DB and put them into the ao2sch map
            processScheduleInfo(results,sch2aoMap,ao2sch,contextInfo);
            processScheduleRequestsForAos(aoIdsWithoutSch,ao2schReq,contextInfo);

            // this takes the scheduling data and puts it into the screen form
            processScheduleData(aoMap,ao2sch,ao2schReq, contextInfo);

            //Search for registration group information
            sr = new SearchRequestInfo(ActivityOfferingSearchServiceImpl.REG_GROUPS_BY_CO_ID_SEARCH_KEY);
            sr.addParam(ActivityOfferingSearchServiceImpl.SearchParameters.CO_ID,form.getCurrentCourseOfferingWrapper().getCourseOfferingId());
            results = searchService.search(sr, null);

            List<RegistrationGroupWrapper> rgWrappers = processRgData(results, form, sch2aoMap, clusterMap, aoMap, contextInfo);

            form.setRgResultList(rgWrappers);

            form.setHasMoreThanOneCluster(clusterMap.size()>1);



            //Validate Reg Groups
            Date startOfValidation = new Date();
            int i = 0;
            for(ActivityOfferingClusterWrapper cluster : clusterMap.values()){
                List<RegistrationGroupInfo> rgInfos = new ArrayList<RegistrationGroupInfo>();
                for(RegistrationGroupWrapper rgWrapper:cluster.getRgWrapperList()){
                    rgInfos.add(rgWrapper.getRgInfo());
                }
                List<ActivityOfferingInfo> aoInfos = new ArrayList<ActivityOfferingInfo>();
                for(ActivityOfferingWrapper aoWrapper:cluster.getAoWrapperList()){
                    aoInfos.add(aoWrapper.getAoInfo());
                }
                _validateRegistrationGroupsPerCluster(rgInfos,aoInfos,cluster,form,i, ao2sch, ao2schReq,aoMap);
                i++;
            }
            Date endOfValidation = new Date();
            LOG.info("Time of RG Validation:"+(endOfValidation.getTime()-startOfValidation.getTime())+"ms");
        }
        // Normally we would use the KeyValue finder for this, but since we HAVE all the data, why waste sql calls
        // replaces : ARGActivitiesForCreateAOKeyValues.java
        //List<KeyValue>


    }

    private void processRelatedTypeKeysForFos(String coId, Map<String, List<String>> foIds, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException {
        SearchRequestInfo sr = new SearchRequestInfo(ActivityOfferingSearchServiceImpl.RELATED_AO_TYPES_BY_CO_ID_SEARCH_KEY);
        sr.addParam(ActivityOfferingSearchServiceImpl.SearchParameters.CO_ID, coId);

        SearchResultInfo results = getSearchService().search(sr, contextInfo);

        for(SearchResultRowInfo row:results.getRows()){
            String foId = null;
            String aoType = null;

            for(SearchResultCellInfo cell:row.getCells()){
                if(ActivityOfferingSearchServiceImpl.SearchResultColumns.FO_ID.equals(cell.getKey())){
                    foId = cell.getValue();
                }else if(ActivityOfferingSearchServiceImpl.SearchResultColumns.AO_TYPE.equals(cell.getKey())){
                    aoType = cell.getValue();
                }
            }
            foIds.get(foId).add(aoType);

        }
    }

    private List<String> _getFoIdsByCoId(String coId) throws Exception{
        List<String> lRet = new ArrayList<String>();
        SearchRequestInfo sr = new SearchRequestInfo(ActivityOfferingSearchServiceImpl.FO_BY_CO_ID_SEARCH_KEY);
        sr.addParam(ActivityOfferingSearchServiceImpl.SearchParameters.CO_ID, coId);

        SearchResultInfo results = getSearchService().search(sr, null);

        for(SearchResultRowInfo row:results.getRows()){
            String foId = null;
            String foName = null;


            for(SearchResultCellInfo cell:row.getCells()){
                if(ActivityOfferingSearchServiceImpl.SearchResultColumns.FO_ID.equals(cell.getKey())){
                    foId = cell.getValue();
                }else if(ActivityOfferingSearchServiceImpl.SearchResultColumns.FO_NAME.equals(cell.getKey())){
                    foName = cell.getValue();
                }
            }
            lRet.add(foId);

        }
        return lRet;
    }

    private void processColocated(SearchResultInfo searchResults, Map<String, ActivityOfferingWrapper> aoMap) {

        for(SearchResultRowInfo row:searchResults.getRows()){
            String aoId = null;
            String aoCode = null;
            String coCode = null;
            for(SearchResultCellInfo cell:row.getCells()){
                if(ActivityOfferingSearchServiceImpl.SearchResultColumns.AO_ID.equals(cell.getKey())){
                    aoId = cell.getValue();
                }else if(ActivityOfferingSearchServiceImpl.SearchResultColumns.AO_CODE.equals(cell.getKey())){
                    aoCode = cell.getValue();
                }else if(ActivityOfferingSearchServiceImpl.SearchResultColumns.CO_CODE.equals(cell.getKey())){
                    coCode = cell.getValue();
                }
            }
            ActivityOfferingWrapper aoWrapper = aoMap.get(aoId);
            aoWrapper.setColocatedAO(true);
            if(aoWrapper.getColocatedAoInfo() == null || aoWrapper.getColocatedAoInfo().isEmpty()){
                aoWrapper.setColocatedAoInfo(coCode + " " + aoCode);
            }else{
                aoWrapper.setColocatedAoInfo(aoWrapper.getColocatedAoInfo() + "<br/>" + coCode + " " + aoCode);
            }

        }
    }

    private void processInstructors(Map<String, ActivityOfferingWrapper> aoMap, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException {
        List<LprInfo> lprInfos = getLprService().getLprsByLuis(new ArrayList<String>(aoMap.keySet()), contextInfo);
        Map<String, Set<String>> principalId2aoIdMap = new HashMap<String, Set<String>>();
        if(lprInfos!=null){
            for(LprInfo lprInfo:lprInfos){
                Set<String> aoIds = principalId2aoIdMap.get(lprInfo.getPersonId());
                if(aoIds==null){
                    aoIds = new HashSet<String>();
                    principalId2aoIdMap.put(lprInfo.getPersonId(),aoIds);
                }
                aoIds.add(lprInfo.getLuiId());
            }
            if(!principalId2aoIdMap.keySet().isEmpty()){
                EntityDefaultQueryResults results = getInstructorsInfoFromKim(new ArrayList<String>(principalId2aoIdMap.keySet()), contextInfo);

                for(EntityDefault entity:results.getResults()){
                    for(Principal principal : entity.getPrincipals()){
                        Set<String> aoIds = principalId2aoIdMap.get(principal.getPrincipalId());
                        if(aoIds != null){
                            for(String aoId:aoIds){
                                ActivityOfferingWrapper activityOfferingWrapper = aoMap.get(aoId);
                                activityOfferingWrapper.setInstructorDisplayNames(entity.getName().getCompositeName(), true);
                            }
                        }
                    }
                }

            }
        }
    }


    /**
     * Add scheduling information to the map if there are no "actual" schedules already in place for a particular AO.
     * @param aoIdsWithoutSch
     * @param ao2sch
     * @param contextInfo
     * @throws Exception
     */
    protected void processScheduleRequestsForAos(Collection<String> aoIdsWithoutSch, Map<String, List<ScheduleRequestCalcContainer>> ao2sch, ContextInfo contextInfo) throws Exception {

        if(!aoIdsWithoutSch.isEmpty()){
            Set<String> buildingIds = new HashSet<String>();
            Set<String> roomIds = new HashSet<String>();
            Set<String> timeslotIds = new HashSet<String>();

            Map<String,BuildingInfo> buildingIdMap = new HashMap<String,BuildingInfo>();
            Map<String,RoomInfo> roomIdMap = new HashMap<String,RoomInfo>();
            Map<String,TimeSlotInfo> timeslotIdMap = new HashMap<String,TimeSlotInfo>();


            List<ScheduleRequestInfo> schRequests = getSchedulingService().getScheduleRequestsByRefObjects(CourseOfferingServiceConstants.REF_OBJECT_URI_ACTIVITY_OFFERING, new ArrayList(aoIdsWithoutSch), contextInfo);
            for(ScheduleRequestInfo schRequest:schRequests){
                for(ScheduleRequestComponentInfo schRequestCom:schRequest.getScheduleRequestComponents()){
                    buildingIds.addAll(schRequestCom.getBuildingIds());
                    roomIds.addAll(schRequestCom.getRoomIds());
                    timeslotIds.addAll(schRequestCom.getTimeSlotIds());
                }

            }

            List<TimeSlotInfo> timeSlotInfos = getSchedulingService().getTimeSlotsByIds(new ArrayList<String>(timeslotIds), contextInfo);
            for(TimeSlotInfo timeSlotInfo : timeSlotInfos){
                timeslotIdMap.put(timeSlotInfo.getId(), timeSlotInfo);
            }
            List<RoomInfo> roomInfos = getRoomService().getRoomsByIds(new ArrayList<String>(roomIds),contextInfo);
            for(RoomInfo roomInfo : roomInfos){
                roomIdMap.put(roomInfo.getId(), roomInfo);
                buildingIds.add(roomInfo.getBuildingId());
            }
            List<BuildingInfo> buildingInfos = getRoomService().getBuildingsByIds(new ArrayList<String>(buildingIds),contextInfo);
            for(BuildingInfo buildingInfo : buildingInfos){
                buildingIdMap.put(buildingInfo.getId(), buildingInfo);
            }

            for(ScheduleRequestInfo schRequest:schRequests){
                String aoId =  schRequest.getRefObjectId();

                for(ScheduleRequestComponentInfo schRequestCom:schRequest.getScheduleRequestComponents()){
                    List<RoomInfo> rooms = new ArrayList<RoomInfo>();
                    List<BuildingInfo> bldgs = new ArrayList<BuildingInfo>();
                    List<TimeSlotInfo> timeSlots = new ArrayList<TimeSlotInfo>();
                    for(String roomId : schRequestCom.getRoomIds()){
                        rooms.add(roomIdMap.get(roomId));
                        bldgs.add(buildingIdMap.get(roomIdMap.get(roomId).getBuildingId()));
                    }
                    for(String timeSlotId : schRequestCom.getTimeSlotIds()){
                        TimeSlotInfo timeSlotInfo = timeslotIdMap.get(timeSlotId);
                        timeSlots.add(timeSlotInfo);

                    }
                    ScheduleRequestCalcContainer src = new ScheduleRequestCalcContainer(aoId,schRequest.getId(),CourseOfferingServiceConstants.REF_OBJECT_URI_ACTIVITY_OFFERING,timeSlots,rooms,bldgs,schRequestCom.getIsTBA());


                    if(ao2sch.containsKey(aoId)){

                            ao2sch.get(aoId).add(src);

                    }   else {
                        List<ScheduleRequestCalcContainer> schList = new ArrayList<ScheduleRequestCalcContainer>();
                        schList.add(src);
                        ao2sch.put(aoId, schList );
                    }
                }
            }
        }



            //ScheduleCalcContainer scheduleCalcContainer = new ScheduleCalcContainer(aoId,cmpId,CourseOfferingServiceConstants.REF_OBJECT_URI_ACTIVITY_OFFERING, startTime, endTime, weekdays, roomCode, bldgName, tbaInd);


    }

    private void processScheduleInfo(SearchResultInfo searchResults, Map<String, ActivityOfferingWrapper> sch2aoMap, Map<String, List<ScheduleCalcContainer>> ao2sch, ContextInfo context) throws InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException, DoesNotExistException {
        //Process the search results

        for(SearchResultRowInfo row:searchResults.getRows()){
            String schId = null;
            String startTime = null;
            String endTime = null;
            Boolean tbaInd = null;
            String roomCode = null;
            String buildingName = null;
            String weekdays = null;
            for(SearchResultCellInfo cell:row.getCells()){
                if(CoreSearchServiceImpl.SearchResultColumns.SCH_ID.equals(cell.getKey())){
                    schId = cell.getValue();
                }else if(CoreSearchServiceImpl.SearchResultColumns.START_TIME.equals(cell.getKey())){
                    startTime = cell.getValue();
                }else if(CoreSearchServiceImpl.SearchResultColumns.END_TIME.equals(cell.getKey())){
                    endTime = cell.getValue();
                }else if(CoreSearchServiceImpl.SearchResultColumns.TBA_IND.equals(cell.getKey())){
                    tbaInd = Boolean.parseBoolean(cell.getValue());
                }else if(CoreSearchServiceImpl.SearchResultColumns.ROOM_CODE.equals(cell.getKey())){
                    roomCode = cell.getValue();
                }else if(CoreSearchServiceImpl.SearchResultColumns.BLDG_NAME.equals(cell.getKey())){
                    buildingName = cell.getValue();
                }else if(CoreSearchServiceImpl.SearchResultColumns.WEEKDAYS.equals(cell.getKey())){
                    weekdays = cell.getValue();
                }
            }
            ActivityOfferingWrapper aoWrapper = sch2aoMap.get(schId);

            ScheduleCalcContainer scheduleCalcContainer = new ScheduleCalcContainer(aoWrapper.getId(),schId,SchedulingServiceConstants.SCHEDULE_TYPE_SCHEDULE, startTime, endTime, weekdays, roomCode,buildingName, tbaInd);
            if(ao2sch.containsKey(aoWrapper.getId())){
                ao2sch.get(aoWrapper.getId()).add(scheduleCalcContainer);
            }   else {
                List<ScheduleCalcContainer> schList = new ArrayList<ScheduleCalcContainer>();
                schList.add(scheduleCalcContainer);
                ao2sch.put(aoWrapper.getId(), schList );
            }
        }
    }

    private void processScheduleData(Map<String,ActivityOfferingWrapper> aoMap, Map<String, List<ScheduleCalcContainer>> ao2sch, Map<String, List<ScheduleRequestCalcContainer>> ao2schReq, ContextInfo contextInfo) throws Exception {
        for(String aoId : aoMap.keySet()){
            ActivityOfferingWrapper aoWrapper = aoMap.get(aoId);

            if(ao2sch.containsKey(aoId)){
                List<ScheduleCalcContainer> schedList = ao2sch.get(aoId);
                boolean newRow = false;
                for(ScheduleCalcContainer sched : schedList){
                        aoWrapper.setScheduleInfo(new ScheduleInfo());
                        aoWrapper.setStartTimeDisplay(sched.getStart().isEmpty()?sched.getStart():DateFormatters.HOUR_MINUTE_AM_PM_TIME_FORMATTER.format(new Date(Long.parseLong(sched.getStart()))), newRow);
                        aoWrapper.setEndTimeDisplay(sched.getEnd().isEmpty()?sched.getEnd():DateFormatters.HOUR_MINUTE_AM_PM_TIME_FORMATTER.format(new Date(Long.parseLong(sched.getEnd()))), newRow);
                        aoWrapper.setBuildingName(sched.getBldgName(), newRow);
                        aoWrapper.setRoomName(sched.getRoomCode(), newRow);
                        aoWrapper.setDaysDisplayName(sched.getWeekdays(), newRow);
                        aoWrapper.setTbaDisplayName(sched.getTbaInd(), newRow);
                    newRow = true;
                }

            }  else if(ao2schReq.containsKey((aoId))){
                List<ScheduleRequestCalcContainer> schedList = ao2schReq.get(aoId);
                for(ScheduleRequestCalcContainer sched : schedList){
                    boolean newLine = aoWrapper.getTbaDisplayName()!=null && !aoWrapper.getTbaDisplayName().isEmpty();
                    for(RoomInfo room : sched.getRooms()){
                        aoWrapper.setRoomName(room.getRoomCode(), newLine, "uif-scheduled-dl");
                    }
                    for(BuildingInfo bldg : sched.getBldgs()){
                        aoWrapper.setBuildingName(bldg.getBuildingCode(), newLine, "uif-scheduled-dl");
                    }
                    for(TimeSlotInfo timeSlotInfo : sched.getTimeSlots()){

                        aoWrapper.setStartTimeDisplay(DateFormatters.HOUR_MINUTE_AM_PM_TIME_FORMATTER.format(new Date(timeSlotInfo.getStartTime().getMilliSeconds())), newLine, "uif-scheduled-dl");
                        aoWrapper.setEndTimeDisplay(DateFormatters.HOUR_MINUTE_AM_PM_TIME_FORMATTER.format(new Date(timeSlotInfo.getEndTime().getMilliSeconds())), newLine, "uif-scheduled-dl");
                        aoWrapper.setDaysDisplayName(SchedulingServiceUtil.weekdaysList2WeekdaysString(timeSlotInfo.getWeekdays()), newLine, "uif-scheduled-dl");
                    }
                    aoWrapper.setTbaDisplayName(sched.getTbaInd(),true);
                }

            }
        }
    }

    private void processSchData(SearchResultInfo searchResults, Map<String, ActivityOfferingWrapper> sch2aoMap, List<String> aoIdsWithoutSch, Map<String,ActivityOfferingWrapper> aoMap, ContextInfo context) throws InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException, DoesNotExistException {
        //Process the search results

        for(SearchResultRowInfo row:searchResults.getRows()){
            String schId = null;
            String startTime = null;
            String endTime = null;
            Boolean tbaInd = null;
            String roomCode = null;
            String buildingName = null;
            String weekdays = null;
            for(SearchResultCellInfo cell:row.getCells()){
                if(CoreSearchServiceImpl.SearchResultColumns.SCH_ID.equals(cell.getKey())){
                    schId = cell.getValue();
                }else if(CoreSearchServiceImpl.SearchResultColumns.START_TIME.equals(cell.getKey())){
                    startTime = cell.getValue();
                }else if(CoreSearchServiceImpl.SearchResultColumns.END_TIME.equals(cell.getKey())){
                    endTime = cell.getValue();
                }else if(CoreSearchServiceImpl.SearchResultColumns.TBA_IND.equals(cell.getKey())){
                    tbaInd = Boolean.parseBoolean(cell.getValue());
                }else if(CoreSearchServiceImpl.SearchResultColumns.ROOM_CODE.equals(cell.getKey())){
                    roomCode = cell.getValue();
                }else if(CoreSearchServiceImpl.SearchResultColumns.BLDG_NAME.equals(cell.getKey())){
                    buildingName = cell.getValue();
                }else if(CoreSearchServiceImpl.SearchResultColumns.WEEKDAYS.equals(cell.getKey())){
                    weekdays = cell.getValue();
                }
            }
            ActivityOfferingWrapper aoWrapper = sch2aoMap.get(schId);

            //Use ScheduleInfo as a flag for if this AO has had schedule info added to it
            boolean newRow = true;
            if(aoWrapper.getScheduleInfo()==null){
                aoWrapper.setScheduleInfo(new ScheduleInfo());
                newRow = false;
            }

            aoWrapper.setStartTimeDisplay(startTime.isEmpty()?startTime:DateFormatters.HOUR_MINUTE_AM_PM_TIME_FORMATTER.format(new Date(Long.parseLong(startTime))), newRow);
            aoWrapper.setEndTimeDisplay(endTime.isEmpty()?endTime:DateFormatters.HOUR_MINUTE_AM_PM_TIME_FORMATTER.format(new Date(Long.parseLong(endTime))), newRow);
            aoWrapper.setBuildingName(buildingName, newRow);
            aoWrapper.setRoomName(roomCode, newRow);
            aoWrapper.setDaysDisplayName(weekdays, newRow);
            aoWrapper.setTbaDisplayName(tbaInd, newRow);

        }

        //Process RDLs for aos that don't have schedule ids
        if(!aoIdsWithoutSch.isEmpty()){
            Set<String> buildingIds = new HashSet<String>();
            Set<String> roomIds = new HashSet<String>();
            Set<String> timeslotIds = new HashSet<String>();

            Map<String,BuildingInfo> buildingIdMap = new HashMap<String,BuildingInfo>();
            Map<String,RoomInfo> roomIdMap = new HashMap<String,RoomInfo>();
            Map<String,TimeSlotInfo> timeslotIdMap = new HashMap<String,TimeSlotInfo>();


            List<ScheduleRequestInfo> schRequests = getSchedulingService().getScheduleRequestsByRefObjects(CourseOfferingServiceConstants.REF_OBJECT_URI_ACTIVITY_OFFERING, aoIdsWithoutSch, context);
            for(ScheduleRequestInfo schRequest:schRequests){
                for(ScheduleRequestComponentInfo schRequestCom:schRequest.getScheduleRequestComponents()){
                    buildingIds.addAll(schRequestCom.getBuildingIds());
                    roomIds.addAll(schRequestCom.getRoomIds());
                    timeslotIds.addAll(schRequestCom.getTimeSlotIds());
                }

            }

            List<TimeSlotInfo> timeSlotInfos = getSchedulingService().getTimeSlotsByIds(new ArrayList<String>(timeslotIds), context);
            for(TimeSlotInfo timeSlotInfo : timeSlotInfos){
                timeslotIdMap.put(timeSlotInfo.getId(), timeSlotInfo);
            }
            List<RoomInfo> roomInfos = getRoomService().getRoomsByIds(new ArrayList<String>(roomIds),context);
            for(RoomInfo roomInfo : roomInfos){
                roomIdMap.put(roomInfo.getId(), roomInfo);
                buildingIds.add(roomInfo.getBuildingId());
            }
            List<BuildingInfo> buildingInfos = getRoomService().getBuildingsByIds(new ArrayList<String>(buildingIds),context);
            for(BuildingInfo buildingInfo : buildingInfos){
                buildingIdMap.put(buildingInfo.getId(), buildingInfo);
            }

            for(ScheduleRequestInfo schRequest:schRequests){
                ActivityOfferingWrapper aoWrapper = aoMap.get(schRequest.getRefObjectId());
                for(ScheduleRequestComponentInfo schRequestCom:schRequest.getScheduleRequestComponents()){
                    boolean newLine = aoWrapper.getTbaDisplayName()!=null && !aoWrapper.getTbaDisplayName().isEmpty();
                    for(String roomId : schRequestCom.getRoomIds()){
                        aoWrapper.setRoomName(roomIdMap.get(roomId).getRoomCode(), newLine, "uif-scheduled-dl");
                        aoWrapper.setBuildingName(buildingIdMap.get(roomIdMap.get(roomId).getBuildingId()).getBuildingCode(), newLine, "uif-scheduled-dl");
                    }
                    for(String timeSlotId : schRequestCom.getTimeSlotIds()){
                        TimeSlotInfo timeSlotInfo = timeslotIdMap.get(timeSlotId);
                        aoWrapper.setStartTimeDisplay(DateFormatters.HOUR_MINUTE_AM_PM_TIME_FORMATTER.format(new Date(timeSlotInfo.getStartTime().getMilliSeconds())), newLine, "uif-scheduled-dl");
                        aoWrapper.setEndTimeDisplay(DateFormatters.HOUR_MINUTE_AM_PM_TIME_FORMATTER.format(new Date(timeSlotInfo.getEndTime().getMilliSeconds())), newLine, "uif-scheduled-dl");
                        aoWrapper.setDaysDisplayName(SchedulingServiceUtil.weekdaysList2WeekdaysString(timeSlotInfo.getWeekdays()), newLine, "uif-scheduled-dl");
                    }
                    aoWrapper.setTbaDisplayName(schRequestCom.getIsTBA(),true);
                }
            }
        }
    }

    private List<RegistrationGroupWrapper> processRgData(SearchResultInfo searchResults, ARGCourseOfferingManagementForm form, Map<String, ActivityOfferingWrapper> sch2aoMap, Map<String, ActivityOfferingClusterWrapper> clusterMap, Map<String, ActivityOfferingWrapper> aoMap, ContextInfo defaultContextInfo) throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException {
        Map<String, RegistrationGroupWrapper> rgMap = new HashMap<String, RegistrationGroupWrapper>();
        for(SearchResultRowInfo row:searchResults.getRows()){

            String aoId = null;
            String rgId = null;
            String rgName = null;
            String rgState = null;
            for(SearchResultCellInfo cell:row.getCells()){
                if(ActivityOfferingSearchServiceImpl.SearchResultColumns.AO_ID.equals(cell.getKey())){
                    aoId = cell.getValue();
                } else if(ActivityOfferingSearchServiceImpl.SearchResultColumns.RG_ID.equals(cell.getKey())){
                    rgId = cell.getValue();
                } else if(ActivityOfferingSearchServiceImpl.SearchResultColumns.RG_NAME.equals(cell.getKey())){
                    rgName = cell.getValue();
                } else if(ActivityOfferingSearchServiceImpl.SearchResultColumns.RG_STATE.equals(cell.getKey())){
                    rgState = cell.getValue();
                }
            }

            ActivityOfferingWrapper aoWrapper = aoMap.get(aoId);
            ActivityOfferingClusterWrapper clusterWrapper = clusterMap.get(aoWrapper.getAoClusterID());

            RegistrationGroupWrapper rgWrapper = rgMap.get(rgId);
            boolean newLine = true;
            if(rgWrapper == null){
                newLine = false;
                rgWrapper = new RegistrationGroupWrapper();
                rgWrapper.setAoCluster(clusterWrapper.getAoCluster());
                rgWrapper.setAoClusterName(clusterWrapper.getClusterNameForDisplay());
                rgWrapper.setStateKey(rgState,getStateService().getState(rgState, ContextUtils.createDefaultContextInfo()).getName());
                rgWrapper.getRgInfo().setName(rgName);
                rgWrapper.getRgInfo().setId(rgId);

                rgWrapper.setAoMaxEnrText("");
                rgWrapper.setAoStateNameText("");
                rgWrapper.setAoActivityCodeText("");
                rgWrapper.setAoTypeNameText("");
                rgWrapper.setStartTimeDisplay("");
                rgWrapper.setEndTimeDisplay("");
                rgWrapper.setDaysDisplayName("");
                rgWrapper.setRoomName("");
                rgWrapper.setBuildingName("");
                rgWrapper.setAoInstructorText("");

                rgMap.put(rgId,rgWrapper);
                clusterWrapper.getRgWrapperList().add(rgWrapper);
            }
            rgWrapper.getRgInfo().getActivityOfferingIds().add(aoId);

            rgWrapper.setAoMaxEnrText(rgWrapper.getAoMaxEnrText()+(newLine?"<br/>":"")+(aoWrapper.getAoInfo().getMaximumEnrollment()==null?"":aoWrapper.getAoInfo().getMaximumEnrollment()));
            rgWrapper.setAoStateNameText(rgWrapper.getAoStateNameText()+(newLine?"<br/>":"")+aoWrapper.getStateName());
            rgWrapper.setAoActivityCodeText(rgWrapper.getAoActivityCodeText()+(newLine?"<br/>":"")+aoWrapper.getAoInfo().getActivityCode());
            rgWrapper.setAoTypeNameText(rgWrapper.getAoTypeNameText()+(newLine?"<br/>":"")+aoWrapper.getTypeName());
            rgWrapper.setStartTimeDisplay(rgWrapper.getStartTimeDisplay()+(newLine?"<br/>":"")+aoWrapper.getStartTimeDisplay());
            rgWrapper.setEndTimeDisplay(rgWrapper.getEndTimeDisplay()+(newLine?"<br/>":"")+aoWrapper.getEndTimeDisplay());
            rgWrapper.setDaysDisplayName(rgWrapper.getDaysDisplayName()+(newLine?"<br/>":"")+aoWrapper.getDaysDisplayName());
            rgWrapper.setRoomName(rgWrapper.getRoomName()+(newLine?"<br/>":"")+aoWrapper.getRoomName());
            rgWrapper.setBuildingName(rgWrapper.getBuildingName()+(newLine?"<br/>":"")+aoWrapper.getBuildingName());
            rgWrapper.setAoInstructorText(rgWrapper.getAoInstructorText()+(newLine?"<br/>":"")+(aoWrapper.getInstructorDisplayNames()==null?"":aoWrapper.getInstructorDisplayNames()));
        }

        return new ArrayList<RegistrationGroupWrapper>(rgMap.values());
    }

    private List<ActivityOfferingWrapper> processAoClusterData(SearchResultInfo searchResults, ARGCourseOfferingManagementForm form,
                                                               Map<String, ActivityOfferingWrapper> sch2aoMap,
                                                               Map<String, ActivityOfferingClusterWrapper> clusterMap,
                                                               Map<String, ActivityOfferingWrapper> aoMap,
                                                               Map<String, List<String>> foIds,
                                                               List<String> aoIdsWithoutSch,
                                                               ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException {

        List<ActivityOfferingWrapper> activityOfferingWrappers = new ArrayList<ActivityOfferingWrapper>();

        for(SearchResultRowInfo row:searchResults.getRows()){

            ActivityOfferingWrapper aoWrapper = new ActivityOfferingWrapper();
            String aocPrivateName = null;
            for(SearchResultCellInfo cell:row.getCells()){

                if(ActivityOfferingSearchServiceImpl.SearchResultColumns.AO_ID.equals(cell.getKey())){
                    aoWrapper.getAoInfo().setId(cell.getValue());
                }
                else if(ActivityOfferingSearchServiceImpl.SearchResultColumns.AO_CODE.equals(cell.getKey())){
                    aoWrapper.setActivityCode(cell.getValue());
                    aoWrapper.getAoInfo().setActivityCode(cell.getValue());
                }
                else if(ActivityOfferingSearchServiceImpl.SearchResultColumns.AO_MAX_SEATS.equals(cell.getKey())){
                    aoWrapper.getAoInfo().setMaximumEnrollment(cell.getValue()==null?null:Integer.parseInt(cell.getValue()));
                }
                else if(ActivityOfferingSearchServiceImpl.SearchResultColumns.AO_STATE.equals(cell.getKey())){
                    aoWrapper.getAoInfo().setStateKey(cell.getValue());
                    if(cell.getValue()!=null){
                        StateInfo stateInfo = getStateService().getState(cell.getValue(), contextInfo);
                        aoWrapper.setStateName(stateInfo.getName());
                    }
                }
                else if(ActivityOfferingSearchServiceImpl.SearchResultColumns.AO_TYPE.equals(cell.getKey())){
                    aoWrapper.getAoInfo().setTypeKey(cell.getValue());
                    if(cell.getValue()!=null){
                        TypeInfo typeInfo = getTypeService().getType(cell.getValue(), contextInfo);
                        aoWrapper.setTypeKey(cell.getValue());
                        aoWrapper.setTypeName(typeInfo.getName());
                    }
                }
                else if(ActivityOfferingSearchServiceImpl.SearchResultColumns.SCHEDULE_ID.equals(cell.getKey())){
                    aoWrapper.getAoInfo().setScheduleId(cell.getValue());
                    sch2aoMap.put(cell.getValue(), aoWrapper);//Add to schedule map
                }
                else if(ActivityOfferingSearchServiceImpl.SearchResultColumns.FO_ID.equals(cell.getKey())){
                    aoWrapper.getAoInfo().setFormatOfferingId(cell.getValue());
                    foIds.put(cell.getValue(), new ArrayList<String>());
                }
                else if(ActivityOfferingSearchServiceImpl.SearchResultColumns.FO_NAME.equals(cell.getKey())){
                    aoWrapper.setFormatOfferingName(cell.getValue());
                    aoWrapper.getAoInfo().setFormatOfferingName(cell.getValue());
                }
                else if(ActivityOfferingSearchServiceImpl.SearchResultColumns.AOC_ID.equals(cell.getKey())){
                    aoWrapper.setAoClusterID(cell.getValue());
                }
                else if(ActivityOfferingSearchServiceImpl.SearchResultColumns.AOC_NAME.equals(cell.getKey())){
                    aoWrapper.setAoClusterName(cell.getValue());
                }
                else if(ActivityOfferingSearchServiceImpl.SearchResultColumns.AOC_PRIVATE_NAME.equals(cell.getKey())){
                    aocPrivateName = cell.getValue();
                }
            }
            ActivityOfferingClusterWrapper aoClusterWrapper = clusterMap.get(aoWrapper.getAoClusterID());
            if(aoClusterWrapper == null){
                aoClusterWrapper = new ActivityOfferingClusterWrapper();
                aoClusterWrapper.setActivityOfferingClusterId(aoWrapper.getAoClusterID());
                aoClusterWrapper.setClusterNameForDisplay(aoWrapper.getAoClusterName());
                aoClusterWrapper.setFormatNameForDisplay(aoWrapper.getAoInfo().getFormatOfferingName());
                aoClusterWrapper.setFormatOfferingId(aoWrapper.getAoInfo().getFormatOfferingId());
                ActivityOfferingClusterInfo activityOfferingClusterInfo = new ActivityOfferingClusterInfo();
                activityOfferingClusterInfo.setFormatOfferingId(aoWrapper.getAoInfo().getFormatOfferingId());
                activityOfferingClusterInfo.setId(aoWrapper.getAoClusterID());
                activityOfferingClusterInfo.setName(aoWrapper.getAoClusterName());
                activityOfferingClusterInfo.setPrivateName(aocPrivateName);
                activityOfferingClusterInfo.setTypeKey(CourseOfferingServiceConstants.AOC_ROOT_TYPE_KEY);
                activityOfferingClusterInfo.setStateKey(CourseOfferingServiceConstants.AOC_ACTIVE_STATE_KEY);
                aoClusterWrapper.setAoCluster(activityOfferingClusterInfo);
                clusterMap.put(aoWrapper.getAoClusterID(), aoClusterWrapper);
            }
            if(aoWrapper.getId()!=null){
                aoClusterWrapper.getAoWrapperList().add(aoWrapper);

                aoMap.put(aoWrapper.getAoInfo().getId(),aoWrapper);

                //Check if there is a schedule id, if not add it to the list to get RDLs
                if(aoWrapper.getAoInfo().getScheduleId()==null){
                   aoIdsWithoutSch.add(aoWrapper.getAoInfo().getId());
                }

                activityOfferingWrappers.add(aoWrapper);

            }

        }
        return activityOfferingWrappers;
    }

    protected EntityDefaultQueryResults getInstructorsInfoFromKim(List<String> principalIds, ContextInfo contextInfo){
        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(
                PredicateFactory.in("principals.principalId", principalIds.toArray())
        );

        QueryByCriteria criteria = qbcBuilder.build();

        EntityDefaultQueryResults entityResults = getIdentityService().findEntityDefaults(criteria);

        return entityResults;
    }

    /*
    private  ActivityOfferingClusterWrapper _buildAOClusterWrapper (FormatOfferingInfo foInfo,
                            ActivityOfferingClusterInfo aoCluster, ARGCourseOfferingManagementForm theForm,
                                                                    int clusterIndex) throws Exception{

        ActivityOfferingClusterWrapper aoClusterWrapper = new ActivityOfferingClusterWrapper();
        aoClusterWrapper.setActivityOfferingClusterId(aoCluster.getId());
        aoClusterWrapper.setAoCluster(aoCluster);
        aoClusterWrapper.setClusterNameForDisplay("Forget to set cluster name?");
        aoClusterWrapper.setFormatOfferingId(foInfo.getId());
        aoClusterWrapper.setFormatNameForDisplay("Forget to set format name?");

        ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();

        List<ActivityOfferingInfo> aoInfoList = getCourseOfferingService().getActivityOfferingsByCluster(aoCluster.getId(),contextInfo );
        List<ActivityOfferingWrapper> aoWrapperListPerCluster = new ArrayList<ActivityOfferingWrapper>();
        for(ActivityOfferingInfo aoInfo: aoInfoList){
            ActivityOfferingWrapper aoWrapper = convertAOInfoToWrapper(aoInfo);

            String cssClass = (aoInfo.getScheduleId() == null ? "uif-scheduled-dl" : "uif-actual-dl");
            aoWrapper.setDaysDisplayName(aoWrapper.getDaysDisplayName(), false, cssClass);
            aoWrapper.setStartTimeDisplay(aoWrapper.getStartTimeDisplay(), false, cssClass);
            aoWrapper.setEndTimeDisplay(aoWrapper.getEndTimeDisplay(), false, cssClass);
            aoWrapper.setBuildingName(aoWrapper.getBuildingName(), false, cssClass);
            aoWrapper.setRoomName(aoWrapper.getRoomName(), false, cssClass);

            //set AOC related info in an AOWrapper
            aoWrapper.setAoCluster(aoCluster);
            aoWrapper.setAoClusterID(aoCluster.getId());

            String pubName=aoCluster.getName();
            if (pubName != null && !pubName.isEmpty()) {
                aoWrapper.setAoClusterName(aoCluster.getPrivateName()+" ("+pubName+")");
            }
            else{
                aoWrapper.setAoClusterName(aoCluster.getPrivateName());
            }

            //set FO related info in an AOWrapper
            aoWrapper.setFormatOffering(foInfo);
            aoWrapper.setFormatOfferingName(foInfo.getName());
            
            aoWrapperListPerCluster.add(aoWrapper);

            //add to the activityWrapperList
            theForm.getActivityWrapperList().add(aoWrapper);
        }
        aoClusterWrapper.setAoWrapperList(aoWrapperListPerCluster);

        List<RegistrationGroupWrapper> rgListPerCluster = new ArrayList<RegistrationGroupWrapper>();
        List<RegistrationGroupInfo> rgInfos =getCourseOfferingService().getRegistrationGroupsByActivityOfferingCluster(aoCluster.getId(), contextInfo);
        if (rgInfos.size() > 0 ){
            _validateRegistrationGroupsPerCluster(rgInfos, aoInfoList, aoClusterWrapper, theForm, clusterIndex,null, null );   // never called
            rgListPerCluster= _getRGsForSelectedFO(rgInfos, aoWrapperListPerCluster);
        }else{
            _performAOCompletePerClusterValidation(foInfo, aoInfoList, aoClusterWrapper, clusterIndex, contextInfo);
        }

        //TODO: seem we don't need to keep track the following info any more!
//        else {
//            aoClusterWrapper.setHasAllRegGroups(false);
//            aoClusterWrapper.setRgStatus(RegistrationGroupConstants.RGSTATUS_NO_RG_GENERATED);
//            aoClusterWrapper.setRgMessageStyle(ActivityOfferingClusterWrapper.RG_MESSAGE_NONE);
//        }
        aoClusterWrapper.setRgWrapperList(rgListPerCluster);
        return aoClusterWrapper;
    }
    */

    /**
     * This method will indicate to the user if the cluster canot be generated because the AO Set does not contain
     * enough activities that meet the requirements of the FormatOffering
     *
     * @param aoTypeKeys
     * @param aoList
     * @param aoClusterWrapper
     * @param clusterIndex  Used to tack the warning message onto a particular part of the screen
     * @param contextInfo
     * @throws Exception
     */
    protected void _performAOCompletePerClusterValidation(List<String> aoTypeKeys, List<ActivityOfferingInfo> aoList,
                                                          ActivityOfferingClusterWrapper aoClusterWrapper, int clusterIndex, ContextInfo contextInfo) throws Exception{
        Map<String, Boolean> completeAoSet = new HashMap<String, Boolean>(); // using a map to store what's required

        for(String aoType :aoTypeKeys){
            completeAoSet.put(aoType, false);
        }

        for(ActivityOfferingInfo aoInfo : aoList){
            completeAoSet.put(aoInfo.getTypeKey(), true); // This is used to determine if the AO Set has all FO types
        }

        String aoCompleteWarningMessage = "";
        String delim = "";
        for(String aoTypeKey : completeAoSet.keySet()){
            if(!completeAoSet.get(aoTypeKey)){
                // type service should be cached so this shouldn't be slow
                aoCompleteWarningMessage += delim + getTypeService().getType(aoTypeKey, contextInfo).getName();
                delim = ", ";
            }
        }
        if(!aoCompleteWarningMessage.isEmpty()){
            aoClusterWrapper.setRgStatus(RegistrationGroupConstants.RGSTATUS_NO_RG_GENERATED);
            aoClusterWrapper.setRgMessageStyle(ActivityOfferingClusterWrapper.RG_MESSAGE_ALL);
            aoClusterWrapper.setHasAllRegGroups(true);
            GlobalVariables.getMessageMap().putWarningForSectionId("activityOfferingsPerCluster_line"+clusterIndex, CourseOfferingConstants.REGISTRATIONGROUP_INCOMPLETE_AOSET, aoCompleteWarningMessage);
        }

    }

    /*
    * Perform several validations:
    * 1. check if All RGs have been generated given AOs in a cluster
    *    if not, set rgStatus="Only Some Registration Groups Generated" and
    *            set hasAllRegGroups=false, therefore "Generate Registration Group" action link will show up
    *    if yes, set rgStatus="All Registration Groups Generated" and
    *            set hasAllRegGroups=true, therefore "View Registration Group" action link will show up
    * 2. when #1 validation result is yes, need to perform max enrollment validation.
    * 3. when #1 validation result is yes, need to perform time conflict validation
    *
    */
    private void _validateRegistrationGroupsPerCluster(List<RegistrationGroupInfo> rgInfos, List<ActivityOfferingInfo> aoList,
                                                       ActivityOfferingClusterWrapper aoClusterWrapper,
                                                       ARGCourseOfferingManagementForm theForm, int clusterIndex, Map<String, List<ScheduleCalcContainer>> ao2sch, Map<String, List<ScheduleRequestCalcContainer>> ao2schReq,Map<String, ActivityOfferingWrapper> aoMap) throws Exception{

        Map<String, List<String>> activityOfferingTypeToAvailableActivityOfferingMap =
                _constructActivityOfferingTypeToAvailableActivityOfferingMap(aoList);

        List<List<String>> generatedPermutations = new ArrayList<List<String>>();
        List<List<String>> foundList = new ArrayList<List<String>>();

        PermutationUtils.generatePermutations(new ArrayList<String>(
                activityOfferingTypeToAvailableActivityOfferingMap.keySet()),
                new ArrayList<String>(),
                activityOfferingTypeToAvailableActivityOfferingMap,
                generatedPermutations);

        List<RegistrationGroupInfo> rgInfosCopy = new ArrayList<RegistrationGroupInfo>(rgInfos.size());
        for(RegistrationGroupInfo rgInfo:rgInfos) {
            rgInfosCopy.add(rgInfo);
        }

        for (List<String> activityOfferingPermutation : generatedPermutations) {
            for (RegistrationGroupInfo rgInfo : rgInfosCopy){
                if (_hasGeneratedRegGroup(activityOfferingPermutation,rgInfo)){
                    rgInfosCopy.remove(rgInfo);
                    foundList.add(activityOfferingPermutation);
                    break;
                }
            }
        }
        if (generatedPermutations.size() != foundList.size() )  {
            aoClusterWrapper.setRgStatus(RegistrationGroupConstants.RGSTATUS_SOME_RG_GENERATED);
            aoClusterWrapper.setRgMessageStyle(ActivityOfferingClusterWrapper.RG_MESSAGE_PARTIAL);
            aoClusterWrapper.setHasAllRegGroups(false);
        }
        else {
            aoClusterWrapper.setRgStatus(RegistrationGroupConstants.RGSTATUS_ALL_RG_GENERATED);
            aoClusterWrapper.setRgMessageStyle(ActivityOfferingClusterWrapper.RG_MESSAGE_ALL);
            aoClusterWrapper.setHasAllRegGroups(true);
            // perform max enrollment validation
            _performMaxEnrollmentValidation(aoMap, aoClusterWrapper.getAoCluster(), clusterIndex);
            //validate AO time conflict in RG
            _performRGTimeConflictValidation(aoClusterWrapper.getAoCluster(), rgInfos, clusterIndex,ao2sch, ao2schReq);
        }

        _performAOCompletePerClusterValidation(theForm.getFoId2aoTypeMap().get(aoClusterWrapper.getFormatOfferingId()), aoList, aoClusterWrapper, clusterIndex,ContextUtils.createDefaultContextInfo());

        if (!rgInfosCopy.isEmpty()){
            GlobalVariables.getMessageMap().putWarningForSectionId("registrationGroupsPerFormatSection", CourseOfferingConstants.REGISTRATIONGROUP_INVALID_REGGROUPS);
        }
    }

    private Map<String, List<String>> _constructActivityOfferingTypeToAvailableActivityOfferingMap(List<ActivityOfferingInfo> aoList) {
        Map<String, List<String>> activityOfferingTypeToAvailableActivityOfferingMap = new HashMap<String, List<String>>();

        for (ActivityOfferingInfo info : aoList) {
            String activityType = info.getTypeKey();
            List<String> activityList = activityOfferingTypeToAvailableActivityOfferingMap
                    .get(activityType);

            if (activityList == null) {
                activityList = new ArrayList<String>();
                activityOfferingTypeToAvailableActivityOfferingMap.put(
                        activityType, activityList);
            }

            activityList.add(info.getId());

        }
        return activityOfferingTypeToAvailableActivityOfferingMap;
    }

    private boolean _hasGeneratedRegGroup(List<String>activityOfferingPermutation, RegistrationGroupInfo rgInfo){
        boolean isMatched = true;
        List<String> aoIds = rgInfo.getActivityOfferingIds();
        List<String> aoIdsCopy = new ArrayList<String>(aoIds.size());
        for (String aoId: aoIds){
            aoIdsCopy.add(aoId);
        }
        List<String> foundList = new ArrayList<String>();
        for (String activityOfferingPermutationItem : activityOfferingPermutation){
            for (String aoId: aoIdsCopy){
                if (activityOfferingPermutationItem.equals(aoId)){
                    aoIdsCopy.remove(aoId);
                    foundList.add(activityOfferingPermutationItem);
                    break;
                }
            }
        }
        if (activityOfferingPermutation.size() != foundList.size() ||!aoIdsCopy.isEmpty()  )  {
            isMatched = false;
        }
        return isMatched;
    }



    private void _performMaxEnrollmentValidation(Map<String, ActivityOfferingWrapper> aoMap, ActivityOfferingClusterInfo aoCluster, int clusterIndex) throws Exception{

        int aoSetMaxEnrollNumber = 0;
        int currentAoSetMaxEnrollNumber = 0;
        int listIndex = 0;
        ValidationResultInfo validationResultInfo = new ValidationResultInfo();

        for(ActivityOfferingSetInfo aos : aoCluster.getActivityOfferingSets()){
            for(String aoId : aos.getActivityOfferingIds()){
                ActivityOfferingWrapper aoWrapper = aoMap.get(aoId);
                ActivityOfferingInfo aoInfo = aoWrapper.getAoInfo();
                if (aoInfo != null &&  aoInfo.getMaximumEnrollment() != null) {
                    aoSetMaxEnrollNumber += aoInfo.getMaximumEnrollment();
                }
            }

            if (listIndex == 0) {
                currentAoSetMaxEnrollNumber = aoSetMaxEnrollNumber;
            } else {
                if (aoSetMaxEnrollNumber != currentAoSetMaxEnrollNumber) {

                    validationResultInfo.setLevel(ValidationResult.ErrorLevel.WARN);
                    validationResultInfo.setMessage("Sum of enrollment for each AO type is not equal");
                    break;
                }
            }
            aoSetMaxEnrollNumber = 0;
            listIndex++;
        }

        //The max enrollment numbers of all the aoSets in the given AOC are the same. The validation passes.
        validationResultInfo.setLevel(ValidationResult.ErrorLevel.OK);
        validationResultInfo.setMessage("Sum of enrollment for each AO type is equal");


        if (validationResultInfo.isWarn())  {
            GlobalVariables.getMessageMap().putWarningForSectionId("registrationGroupsPerCluster_line"+clusterIndex, RegistrationGroupConstants.MSG_WARNING_MAX_ENROLLMENT, aoCluster.getPrivateName());
            GlobalVariables.getMessageMap().putWarningForSectionId("activityOfferingsPerCluster_line"+clusterIndex, RegistrationGroupConstants.MSG_WARNING_MAX_ENROLLMENT, aoCluster.getPrivateName());
        }
    }

    private List<Integer> _performRGTimeConflictValidation(ActivityOfferingClusterInfo aoCluster, List<RegistrationGroupInfo> registrationGroupInfos, int clusterIndex, Map<String, List<ScheduleCalcContainer>> ao2sch, Map<String, List<ScheduleRequestCalcContainer>> ao2schReq) throws Exception{
        List<Integer> rgIndexList = new ArrayList<Integer>();
        rgIndexList.clear();

        if (aoCluster != null && registrationGroupInfos != null && !registrationGroupInfos.isEmpty()) {
            int rgIndex = 0;
            for (RegistrationGroupInfo registrationGroupInfo : registrationGroupInfos) {

                List<ValidationResultInfo> validationResultInfoList = new ArrayList<ValidationResultInfo>();
                // Lets build a list of all schedules that need to be compared for this registration group.
                List<TimeSlotInfo> shed2Check = new ArrayList<TimeSlotInfo>();
                for(String aoId : registrationGroupInfo.getActivityOfferingIds()){
                    if(ao2sch.get(aoId)!= null){
                        for(ScheduleCalcContainer sched : ao2sch.get(aoId)){
                            shed2Check.add(toTimeSlotInfo(sched));
                        }
                    }
                    if(ao2schReq.get(aoId)!= null){
                        for(ScheduleRequestCalcContainer sched : ao2schReq.get(aoId)){
                            shed2Check.addAll(sched.getTimeSlots());
                        }
                    }
                }

                for(TimeSlotInfo outerEntry : shed2Check){
                    for(TimeSlotInfo innerEntry : shed2Check){
                        if(outerEntry.equals(innerEntry)){
                            break;
                        }
                        if (SchedulingServiceUtil.areTimeSlotsInConflict(outerEntry, innerEntry)) {
                            ValidationResultInfo validationResultInfo = new ValidationResultInfo();
                            validationResultInfo.setLevel(ValidationResult.ErrorLevel.WARN);
                            validationResultInfo.setMessage("time conflict between AO: " + outerEntry.getId() + " and AO: " + innerEntry.getId());
                            validationResultInfoList.add(validationResultInfo);
                        }
                    }
                }

                if (!validationResultInfoList.isEmpty() && validationResultInfoList.get(0).isWarn())  {
                    //Why are we alter the RG state? Commenting out for now.
                    //getCourseOfferingService().changeRegistrationGroupState(registrationGroupInfo.getId(), LuiServiceConstants.REGISTRATION_GROUP_INVALID_STATE_KEY, ContextUtils.createDefaultContextInfo());
                    rgIndexList.add(rgIndex);
                }

                rgIndex++;
            }

            if (!rgIndexList.isEmpty()) {
                GlobalVariables.getMessageMap().putWarningForSectionId("activityOfferingsPerCluster_line"+clusterIndex, RegistrationGroupConstants.MSG_WARNING_AO_TIMECONFLICT, aoCluster.getPrivateName());
                GlobalVariables.getMessageMap().putWarningForSectionId("registrationGroupsPerCluster_line"+clusterIndex, RegistrationGroupConstants.MSG_WARNING_AO_TIMECONFLICT, aoCluster.getPrivateName());
            }
        }

        return rgIndexList;
    }

    public TimeSlotInfo toTimeSlotInfo(ScheduleCalcContainer sched) {
        TimeSlotInfo info = new TimeSlotInfo();



        info.setId(null);
        info.setTypeKey(null);
        info.setStateKey(null);
        info.setName(null);
        info.setWeekdays(SchedulingServiceUtil.weekdaysString2WeekdaysList(sched.getWeekdays()));

        info.setStartTime(new TimeOfDayInfo());
        info.getStartTime().setMilliSeconds(((sched.getStart() != null && !"".equals(sched.getStart())) ? new Long(sched.getStart()) : null));

        info.setEndTime(new TimeOfDayInfo());
        info.getEndTime().setMilliSeconds(( (sched.getEnd() != null  && !"".equals(sched.getEnd())) ? new Long(sched.getEnd()) : null));

        info.setDescr(new RichTextInfo());
        info.getDescr().setFormatted(null);
        info.getDescr().setPlain(null);

        info.setMeta(null);

        return info;
    }

    private List<Integer> _performRGTimeConflictValidation(ActivityOfferingClusterInfo aoCluster, List<RegistrationGroupInfo> registrationGroupInfos, int clusterIndex) throws Exception{
        List<Integer> rgIndexList = new ArrayList<Integer>();
        rgIndexList.clear();

        if (aoCluster != null && registrationGroupInfos != null && !registrationGroupInfos.isEmpty()) {
            int rgIndex = 0;
            for (RegistrationGroupInfo registrationGroupInfo : registrationGroupInfos) {
                List<ValidationResultInfo> validationResultInfoList = getCourseOfferingService().verifyRegistrationGroup(registrationGroupInfo.getId(), ContextUtils.createDefaultContextInfo());
                if (validationResultInfoList.get(0).isWarn())  {
                    //Why are we alter the RG state? Commenting out for now.
                    //getCourseOfferingService().changeRegistrationGroupState(registrationGroupInfo.getId(), LuiServiceConstants.REGISTRATION_GROUP_INVALID_STATE_KEY, ContextUtils.createDefaultContextInfo());
                    rgIndexList.add(rgIndex);
                }

                rgIndex++;
            }

            if (!rgIndexList.isEmpty()) {
                GlobalVariables.getMessageMap().putWarningForSectionId("activityOfferingsPerCluster_line"+clusterIndex, RegistrationGroupConstants.MSG_WARNING_AO_TIMECONFLICT, aoCluster.getPrivateName());
                GlobalVariables.getMessageMap().putWarningForSectionId("registrationGroupsPerCluster_line"+clusterIndex, RegistrationGroupConstants.MSG_WARNING_AO_TIMECONFLICT, aoCluster.getPrivateName());
            }
        }

        return rgIndexList;
    }


    private List<RegistrationGroupWrapper> _getRGsForSelectedFO(List<RegistrationGroupInfo> rgInfos, List<ActivityOfferingWrapper> filteredAOs) throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException {
        _fixAoIdOrderingInRegGroups(rgInfos);
        List<RegistrationGroupWrapper> filterdRGList = new ArrayList<RegistrationGroupWrapper>();

        HashMap<String, ActivityOfferingWrapper> filteredAOsHM = new HashMap<String, ActivityOfferingWrapper>();
        for (ActivityOfferingWrapper wrapper : filteredAOs) {
            filteredAOsHM.put(wrapper.getAoInfo().getId(), wrapper);
        }

        for (RegistrationGroupInfo rgInfo : rgInfos) {
            RegistrationGroupWrapper rgWrapper = new RegistrationGroupWrapper();
            rgWrapper.setRgInfo(rgInfo);
            String aoActivityCodeText = "", aoStateNameText = "", aoTypeNameText = "", aoInstructorText = "", aoMaxEnrText = "";
            for (String aoID : rgInfo.getActivityOfferingIds()) {
                String cssClass = (filteredAOsHM.get(aoID).getAoInfo().getScheduleId() == null ? "uif-scheduled-dl" : "uif-actual-dl");
                if (filteredAOsHM.get(aoID).getAoInfo().getActivityCode() != null && !filteredAOsHM.get(aoID).getAoInfo().getActivityCode().equalsIgnoreCase("")) {
                    aoActivityCodeText = aoActivityCodeText + filteredAOsHM.get(aoID).getAoInfo().getActivityCode() + "<br/>";
                }
                if (filteredAOsHM.get(aoID).getStateName() != null && !filteredAOsHM.get(aoID).getStateName().equalsIgnoreCase("")) {
                    aoStateNameText = aoStateNameText + filteredAOsHM.get(aoID).getStateName() + "<br/>";
                }
                if (filteredAOsHM.get(aoID).getTypeName() != null && !filteredAOsHM.get(aoID).getTypeName().equalsIgnoreCase("")) {
                    aoTypeNameText = aoTypeNameText + filteredAOsHM.get(aoID).getTypeName() + "<br/>";
                }
                if (filteredAOsHM.get(aoID).getFirstInstructorDisplayName() != null && !filteredAOsHM.get(aoID).getFirstInstructorDisplayName().equalsIgnoreCase("")) {
                    aoInstructorText = aoInstructorText + filteredAOsHM.get(aoID).getFirstInstructorDisplayName() + "<br/>";
                }
                if (filteredAOsHM.get(aoID).getAoInfo().getMaximumEnrollment() != null) {
                    aoMaxEnrText = aoMaxEnrText + Integer.toString(filteredAOsHM.get(aoID).getAoInfo().getMaximumEnrollment()) + "<br/>";
                }

                if(filteredAOsHM.get(aoID).getStartTimeDisplay() != null){
                    rgWrapper.setStartTimeDisplay(filteredAOsHM.get(aoID).getStartTimeDisplay(), true, cssClass);
                }

                if(filteredAOsHM.get(aoID).getEndTimeDisplay() != null){
                    rgWrapper.setEndTimeDisplay(filteredAOsHM.get(aoID).getEndTimeDisplay(), true, cssClass);
                }

                if(filteredAOsHM.get(aoID).getBuildingName() != null){
                    rgWrapper.setBuildingName(filteredAOsHM.get(aoID).getBuildingName(), true, cssClass);
                }

                if(filteredAOsHM.get(aoID).getRoomName() != null){
                    rgWrapper.setRoomName(filteredAOsHM.get(aoID).getRoomName(), true, cssClass);
                }

                if(filteredAOsHM.get(aoID).getDaysDisplayName() != null){
                    rgWrapper.setDaysDisplayName(filteredAOsHM.get(aoID).getDaysDisplayName(), true, cssClass);
                }
            }
            if (aoActivityCodeText.length() > 0) {
                aoActivityCodeText = aoActivityCodeText.substring(0, aoActivityCodeText.lastIndexOf("<br/>"));
            }
            if (aoStateNameText.length() > 0) {
                aoStateNameText = aoStateNameText.substring(0, aoStateNameText.lastIndexOf("<br/>"));
            }
            if (aoTypeNameText.length() > 0) {
                aoTypeNameText = aoTypeNameText.substring(0, aoTypeNameText.lastIndexOf("<br/>"));
            }
            if (aoInstructorText.length() > 0) {
                aoInstructorText = aoInstructorText.substring(0, aoInstructorText.lastIndexOf("<br/>"));
            }
            if (aoMaxEnrText.length() > 0) {
                aoMaxEnrText = aoMaxEnrText.substring(0, aoMaxEnrText.lastIndexOf("<br/>"));
            }

            rgWrapper.setAoActivityCodeText(aoActivityCodeText);
            rgWrapper.setAoStateNameText(aoStateNameText);
            rgWrapper.setAoTypeNameText(aoTypeNameText);
            rgWrapper.setAoInstructorText(aoInstructorText);
            rgWrapper.setAoMaxEnrText(aoMaxEnrText);
            filterdRGList.add(rgWrapper);

            try{
                rgWrapper.setStateKey(rgInfo.getStateKey(), getStateService().getState(rgInfo.getStateKey(), ContextUtils.createDefaultContextInfo()).getName());
            }catch (Exception e){
                LOG.info("Error occured to get the StateService" + e.getMessage());
            }
        }

        return filterdRGList;
    }

    private void _fixAoIdOrderingInRegGroups(List<RegistrationGroupInfo> rgInfos)
            throws InvalidParameterException, MissingParameterException, DoesNotExistException,
            OperationFailedException, PermissionDeniedException {

        for (RegistrationGroupInfo regGroup: rgInfos) {
            Map<String, String> aoIdsToAoTypes =
                    RegistrationGroupUtil.createAoIdsToAoTypesMap(regGroup.getActivityOfferingIds(), getCourseOfferingService(), ContextUtils.createDefaultContextInfo());
            RegistrationGroupUtil.orderActivityOfferingIdsInRegistrationGroup(regGroup, aoIdsToAoTypes);
        }
    }


    /**
     * This method fetches all the course offerings for a term and course/subject code.
     *
     * @see CourseOfferingManagementSearchImpl Actual CO search happens here
     * @param searchRequest
     * @param form
     * @throws Exception
     */
    protected void loadCourseOfferings(SearchRequestInfo searchRequest, ARGCourseOfferingManagementForm form) throws Exception {

        ContextInfo contextInfo = createContextInfo();

        SearchResultInfo searchResult = getSearchService().search(searchRequest, contextInfo);

        form.getCourseOfferingResultList().clear();

        for (SearchResultRowInfo row : searchResult.getRows()) {
            CourseOfferingListSectionWrapper coListWrapper = new CourseOfferingListSectionWrapper();

            for(SearchResultCellInfo cellInfo : row.getCells()){

                String value = StringUtils.EMPTY;
                if(cellInfo.getValue() != null)  {
                    value = new String(cellInfo.getValue());
                }

                if(CourseOfferingManagementSearchImpl.SearchResultColumns.CODE.equals(cellInfo.getKey())){
                    coListWrapper.setCourseOfferingCode(value);
                }
                else if(CourseOfferingManagementSearchImpl.SearchResultColumns.DESC.equals(cellInfo.getKey())){
                    coListWrapper.setCourseOfferingDesc(value);
                }
                else if(CourseOfferingManagementSearchImpl.SearchResultColumns.STATE.equals(cellInfo.getKey())){
                    coListWrapper.setCourseOfferingStateKey(value);
                    coListWrapper.setCourseOfferingStateDisplay(getStateInfo(value).getName());
                }
                else if(CourseOfferingManagementSearchImpl.SearchResultColumns.CREDIT_OPTION.equals(cellInfo.getKey())){
                    coListWrapper.setCourseOfferingCreditOptionKey(value);
                }
                else if(CourseOfferingManagementSearchImpl.SearchResultColumns.GRADING_OPTION.equals(cellInfo.getKey())){
                    coListWrapper.setCourseOfferingGradingOptionKey(value);
                }
                else if(CourseOfferingManagementSearchImpl.SearchResultColumns.GRADING_OPTION_NAME.equals(cellInfo.getKey())){
                    coListWrapper.setCourseOfferingGradingOptionDisplay(cellInfo.getValue());
                }
                else if(CourseOfferingManagementSearchImpl.SearchResultColumns.CREDIT_OPTION_NAME.equals(cellInfo.getKey())){
                    coListWrapper.setCourseOfferingCreditOptionDisplay(cellInfo.getValue());
                }
                else if(CourseOfferingManagementSearchImpl.SearchResultColumns.DEPLOYMENT_ORG_ID.equals(cellInfo.getKey())){
                    coListWrapper.setAdminOrg(cellInfo.getValue());
                }
                else if(CourseOfferingManagementSearchImpl.SearchResultColumns.CO_ID.equals(cellInfo.getKey())){
                    coListWrapper.setCourseOfferingId(value);
                }
                else if(CourseOfferingManagementSearchImpl.SearchResultColumns.SUBJECT_AREA.equals(cellInfo.getKey())){
                    coListWrapper.setSubjectArea(value);
                }
                else if(CourseOfferingManagementSearchImpl.SearchResultColumns.IS_CROSS_LISTED.equals(cellInfo.getKey())){
                    coListWrapper.setCrossListed(BooleanUtils.toBoolean(value));
                }
                else if(CourseOfferingManagementSearchImpl.SearchResultColumns.CROSS_LISTED_COURSES.equals(cellInfo.getKey())){
                    coListWrapper.setAlternateCOCodes(Arrays.asList(StringUtils.split(value, ",")));
                }
                else if(CourseOfferingManagementSearchImpl.SearchResultColumns.OWNER_CODE.equals(cellInfo.getKey())){
                    coListWrapper.setOwnerCode(value);
                }
                else if(CourseOfferingManagementSearchImpl.SearchResultColumns.OWNER_ALIASES.equals(cellInfo.getKey())){
                    coListWrapper.setOwnerAliases(Arrays.asList(StringUtils.split(value, ",")));
                }

            }
            form.getCourseOfferingResultList().add(coListWrapper);
        }
    }


    /**
     * This method loads the previous and next course offerings for navigation purpose.
     *
     * @param form
     */
    public void loadPreviousAndNextCourseOffering(ARGCourseOfferingManagementForm      form){
        try{
            ContextInfo contextInfo = createContextInfo();

            /**
             * Get all the course offerings for a term and subject area.
             */
            SearchRequestInfo searchRequest = new SearchRequestInfo(CourseOfferingManagementSearchImpl.CO_MANAGEMENT_SEARCH.getKey());
            searchRequest.addParam(CourseOfferingManagementSearchImpl.SearchParameters.SUBJECT_AREA, form.getSubjectCode());
            searchRequest.addParam(CourseOfferingManagementSearchImpl.SearchParameters.ATP_ID, form.getTermInfo().getId());
            searchRequest.addParam(CourseOfferingManagementSearchImpl.SearchParameters.CROSS_LIST_SEARCH_ENABLED, BooleanUtils.toStringTrueFalse(true));

            SearchResultInfo searchResult = getSearchService().search(searchRequest, contextInfo);
            List<CourseOfferingWrapper> availableCOs = new ArrayList<CourseOfferingWrapper>();

            for (SearchResultRowInfo row : searchResult.getRows()) {

                String courseOfferingCode = "";
                String courseOfferingId = "";
                String courseOfferingDesc = "";
                boolean isCrossListed = false;
                List<String> alternateCodes = null;

                for(SearchResultCellInfo cellInfo : row.getCells()){

                    String value = StringUtils.EMPTY;
                    if(cellInfo.getValue() != null)  {
                        value = new String(cellInfo.getValue());
                    }

                    if(CourseOfferingManagementSearchImpl.SearchResultColumns.CODE.equals(cellInfo.getKey())){
                        courseOfferingCode = value;
                    }
                    else if(CourseOfferingManagementSearchImpl.SearchResultColumns.CO_ID.equals(cellInfo.getKey())){
                        courseOfferingId = value;
                    }
                    else if(CourseOfferingManagementSearchImpl.SearchResultColumns.DESC.equals(cellInfo.getKey())){
                        courseOfferingDesc = value;
                    }
                    else if(CourseOfferingManagementSearchImpl.SearchResultColumns.IS_CROSS_LISTED.equals(cellInfo.getKey())){
                        isCrossListed = BooleanUtils.toBoolean(value);
                    }
                    else if(CourseOfferingManagementSearchImpl.SearchResultColumns.CROSS_LISTED_COURSES.equals(cellInfo.getKey())){
                        alternateCodes = Arrays.asList(StringUtils.split(value, ","));
                    }

                }

                CourseOfferingWrapper coWrapper = new CourseOfferingWrapper(isCrossListed,courseOfferingCode,courseOfferingDesc,alternateCodes,courseOfferingId);
                availableCOs.add(coWrapper);
            }

            /**
             * Find the current course offering index and set the previous and next course offerings if exists.
             */
            for (CourseOfferingWrapper coWrapper : availableCOs) {
                if (StringUtils.equals(coWrapper.getCourseOfferingCode(),form.getCurrentCourseOfferingWrapper().getCourseOfferingCode())){
                    int currentIndex = availableCOs.indexOf(coWrapper);
                    form.setInputCode(coWrapper.getCourseOfferingCode());
                    if (currentIndex > 0){
                        form.setPreviousCourseOfferingWrapper(availableCOs.get(currentIndex-1));
                    }else{
                        form.setPreviousCourseOfferingWrapper(null);
                    }
                    if (currentIndex < availableCOs.size()-1){
                        form.setNextCourseOfferingWrapper(availableCOs.get(currentIndex+1));
                    }else{
                        form.setNextCourseOfferingWrapper(null);
                    }
                    break;
                }
            }


        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void createActivityOfferings(String formatOfferingId, String activityId, int noOfActivityOfferings, ARGCourseOfferingManagementForm form){
        String termcode;
        FormatInfo format = null;
        FormatOfferingInfo formatOfferingInfo = null;
        TypeInfo activityOfferingType = null;
        CourseInfo course;
        String clusterId = form.getClusterIdForNewAO();
        ContextInfo contextInfo = createContextInfo();
        List<ActivityOfferingClusterInfo> clusters = null;
        ActivityOfferingClusterInfo defaultCluster = null;

        //create default cluster if there is no cluster for the FO yet
        try {
            clusters = getCourseOfferingService().getActivityOfferingClustersByFormatOffering(formatOfferingId, contextInfo);
            if (clusters == null || clusters.size()<=0) {
                defaultCluster = ARGUtil.getArgServiceAdapter().createDefaultCluster(formatOfferingId, contextInfo);
                if (defaultCluster != null) {
                    clusterId = defaultCluster.getId();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //the AO clusters associated with the given FO
        CourseOfferingInfo courseOffering = form.getCurrentCourseOfferingWrapper().getCourseOfferingInfo();


        // Get the format object for the id selected
        try {
            formatOfferingInfo = getCourseOfferingService().getFormatOffering(formatOfferingId, contextInfo);
            course = getCourseService().getCourse(courseOffering.getCourseId(), contextInfo);
            for (FormatInfo f : course.getFormats()) {
                if (f.getId().equals(formatOfferingInfo.getFormatId())) {
                    format = f;
                    break;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // find the format offering object for the selected format
        /*FormatOfferingInfo formatOfferingInfo = null;
        try {
            List<FormatOfferingInfo> courseOfferingFOs = getCourseOfferingService().getFormatOfferingsByCourseOffering(courseOffering.getId(), contextInfo);
            for(FormatOfferingInfo fo : courseOfferingFOs) {
                if (fo.getFormatId().equals(formatId)) {
                    formatOfferingInfo = fo;
                    break;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }*/

        // find the Activity object that matches the activity id selected
        ActivityInfo activity = null;
        //FindBugs: getActivities() null check is in FormatInfo
        List<ActivityInfo> activities = format.getActivities();
        for (ActivityInfo info : activities) {
            if (StringUtils.equals(activityId, info.getId())) {
                activity = info;
            }
        }

        // Get the matching activity offering type for the selected activity
        try {
            for (String aoTypeKey : formatOfferingInfo.getActivityOfferingTypeKeys()) {
                List<TypeTypeRelationInfo> typeTypeRelationInfos = getTypeService().getTypeTypeRelationsByRelatedTypeAndType(aoTypeKey, TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, ContextUtils.getContextInfo());
                if (typeTypeRelationInfos != null && typeTypeRelationInfos.size() > 0) {
                    if (StringUtils.equals(activity.getTypeKey(), typeTypeRelationInfos.get(0).getOwnerTypeKey())) {
                        activityOfferingType = getTypeService().getType(aoTypeKey, contextInfo);
                    }

                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            AtpInfo termAtp = getAtpService().getAtp(courseOffering.getTermId(), contextInfo);
            termcode = termAtp.getCode();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        for (int i = 0; i < noOfActivityOfferings; i++) {
            ActivityOfferingInfo aoInfo = new ActivityOfferingInfo();
            aoInfo.setActivityId(activityId);
            aoInfo.setFormatOfferingId(formatOfferingInfo.getId());
            aoInfo.setTypeKey(activityOfferingType.getKey());
            aoInfo.setCourseOfferingId(courseOffering.getId());
            aoInfo.setStateKey(LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY);
            aoInfo.setTermId(courseOffering.getTermId());
            aoInfo.setTermCode(termcode);
            aoInfo.setFormatOfferingName(formatOfferingInfo.getName());
            aoInfo.setCourseOfferingTitle(courseOffering.getCourseOfferingTitle());
            aoInfo.setCourseOfferingCode(courseOffering.getCourseOfferingCode());

            ActivityOfferingInfo activityOfferingInfo = null;
            try {
                //Temp solution here: if there is cluster(s) assocaited with the given FO, call createAO method in adapter
                //It will associate created AOs with the first cluster of the given FO
                if (clusterId != null && !clusterId.isEmpty()) {
                    activityOfferingInfo = ARGUtil.getArgServiceAdapter().createActivityOffering(aoInfo, clusterId, contextInfo).getCreatedActivityOffering();
                } else {
                    activityOfferingInfo = _getCourseOfferingService().createActivityOffering(formatOfferingInfo.getId(), activityId, activityOfferingType.getKey(), aoInfo, contextInfo);
                }
                ActivityOfferingWrapper wrapper = new ActivityOfferingWrapper(activityOfferingInfo);
                StateInfo state = getStateService().getState(wrapper.getAoInfo().getStateKey(), contextInfo);
                wrapper.setStateName(state.getName());
                TypeInfo typeInfo = getTypeInfo(wrapper.getAoInfo().getTypeKey());
                wrapper.setTypeName(typeInfo.getName());
                form.getActivityWrapperList().add(wrapper);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        ARGToolbarUtil.processAoToolbarForUser(form.getActivityWrapperList(), form);
        if (noOfActivityOfferings == 1) {
            KSUifUtils.addGrowlMessageIcon(GrowlIcon.INFORMATION, CourseOfferingConstants.ACTIVITYOFFERING_TOOLBAR_ADD_1_SUCCESS);
        } else {
            KSUifUtils.addGrowlMessageIcon(GrowlIcon.INFORMATION, CourseOfferingConstants.ACTIVITYOFFERING_TOOLBAR_ADD_N_SUCCESS);
        }
    }

    public void loadActivityOfferingsByCourseOffering (CourseOfferingInfo theCourseOfferingInfo, ARGCourseOfferingManagementForm      form) throws Exception{
        String courseOfferingId = theCourseOfferingInfo.getId();
        List<ActivityOfferingInfo> activityOfferingInfoList;
        List<ActivityOfferingWrapper> activityOfferingWrapperList;

        try {
            activityOfferingInfoList =_getCourseOfferingService().getActivityOfferingsByCourseOffering(courseOfferingId, createContextInfo());
            activityOfferingWrapperList = new ArrayList<ActivityOfferingWrapper>(activityOfferingInfoList.size());

            for (ActivityOfferingInfo info : activityOfferingInfoList) {
                ActivityOfferingWrapper aoWrapper = convertAOInfoToWrapper(info);
                activityOfferingWrapperList.add(aoWrapper);
            }
        } catch (Exception e) {
            throw new RuntimeException(String.format("Could not load AOs for course offering [%s].", courseOfferingId), e);
        }
        form.setActivityWrapperList(activityOfferingWrapperList);
    }

    public List<ActivityOfferingWrapper> getActivityOfferingsByCourseOfferingId (String courseOfferingId, ARGCourseOfferingManagementForm      form) throws Exception{
        List<ActivityOfferingInfo> activityOfferingInfoList;
        List<ActivityOfferingWrapper> activityOfferingWrapperList;

        try {
            activityOfferingInfoList =_getCourseOfferingService().getActivityOfferingsByCourseOffering(courseOfferingId, createContextInfo());
            activityOfferingWrapperList = new ArrayList<ActivityOfferingWrapper>(activityOfferingInfoList.size());

            for (ActivityOfferingInfo info : activityOfferingInfoList) {
                ActivityOfferingWrapper aoWrapper = new ActivityOfferingWrapper(info);
                activityOfferingWrapperList.add(aoWrapper);
            }
        } catch (Exception e) {
            throw new RuntimeException(String.format("Could not load AOs for course offering [%s].", courseOfferingId), e);
        }
        return activityOfferingWrapperList;
    }

    public void approveCourseOfferings(ARGCourseOfferingManagementForm      form) throws Exception{
        List<CourseOfferingListSectionWrapper> coList = form.getCourseOfferingResultList();
        ContextInfo contextInfo = createContextInfo();
        int checked = 0;
        int enabled = 0;
        for(CourseOfferingListSectionWrapper co : coList) {
            if(co.getIsChecked()){
                checked++;
                if(co.isEnableApproveButton()) {
                    enabled++;
                    List<ActivityOfferingWrapper> aos = getActivityOfferingsByCourseOfferingId(co.getCourseOfferingId(), form);
                    if(aos != null && !aos.isEmpty()){
                        ARGToolbarUtil.processAoToolbarForUser(aos, form);
                        for(ActivityOfferingWrapper ao : aos){
                            if(ao.isEnableApproveButton()){
                                getCourseOfferingService().changeActivityOfferingState(ao.getAoInfo().getId(), LuiServiceConstants.LUI_AO_STATE_APPROVED_KEY, contextInfo);
                            }
                        }
                    }
                }
            }
        }

        if(checked > enabled){
            KSUifUtils.addGrowlMessageIcon(GrowlIcon.WARNING, CourseOfferingConstants.COURSEOFFERING_TOOLBAR_APPROVED);
        }else{
            if(enabled == 1){
                KSUifUtils.addGrowlMessageIcon(GrowlIcon.INFORMATION, CourseOfferingConstants.COURSEOFFERING_TOOLBAR_APPROVED_1_SUCCESS);
            }else {
                KSUifUtils.addGrowlMessageIcon(GrowlIcon.INFORMATION, CourseOfferingConstants.COURSEOFFERING_TOOLBAR_APPROVED_N_SUCCESS);
            }
        }
    }

    public void deleteCourseOfferings(ARGCourseOfferingManagementForm form) throws Exception {
        List<CourseOfferingListSectionWrapper> coList = form.getCourseOfferingResultList();
        int checked = 0;
        int enabled = 0;
        int totalCosToDelete = 0;
        int totalColocatedCos = 0;
        int totalColocatedAos = 0;
        boolean iscolocated = false;
        int totalCrossListedCosToDelete = 0;
        int totalJointDefinedCosToDelete = 0;

        form.setNumOfColocatedCosToDelete(0);
        form.setNumOfColocatedAosToDelete(0);
        form.setNumOfCrossListedCosToDelete(0);
        form.setNumOfJointDefinedCosToDelete(0);

        List<CourseOfferingListSectionWrapper> qualifiedToDeleteList = form.getSelectedCoToDeleteList();
        qualifiedToDeleteList.clear();
        ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();

        int totalAos = 0;
        form.setCrossListedCO(false);
        form.setColocatedCO(false);
        for (CourseOfferingListSectionWrapper co : coList) {
            boolean hasDeletion = true;
            if (co.getIsChecked()) {
                checked++;
                totalCosToDelete++;
                iscolocated = false;

                List<ActivityOfferingDisplayInfo> aoDisplayInfoList = getCourseOfferingService().getActivityOfferingDisplaysForCourseOffering(co.getCourseOfferingId(), contextInfo);
                List<ActivityOfferingInfo> aoInfoList = getCourseOfferingService().getActivityOfferingsByCourseOffering(co.getCourseOfferingId(), contextInfo);

                co.setCoHasAoToDelete(true);
                if (aoDisplayInfoList.isEmpty()) {
                    co.setCoHasAoToDelete(false);
                }
                co.setColocated(false);

                if (aoDisplayInfoList != null && !aoDisplayInfoList.isEmpty()) {
                    for (ActivityOfferingDisplayInfo aoDisplayInfo : aoDisplayInfoList) {

                        ActivityOfferingDisplayWrapper aoDisplayWrapper = new ActivityOfferingDisplayWrapper();
                        aoDisplayWrapper.setAoDisplayInfo(aoDisplayInfo);
                        aoDisplayWrapper.setActivityOfferingCode(aoDisplayInfo.getActivityOfferingCode());
                        // Adding Information (icons)
                        String information = "";
                        if (aoDisplayInfo.getIsHonorsOffering() != null && aoDisplayInfo.getIsHonorsOffering()) {
                            information = "<img src=" + ScheduleOfClassesConstants.SOC_RESULT_PAGE_HONORS_COURSE_IMG + " title=\"" + ScheduleOfClassesConstants.SOC_RESULT_PAGE_HELP_HONORS_ACTIVITY + "\"> ";
                        }
                        aoDisplayWrapper.setInformation(information);

                        if (aoDisplayInfo.getScheduleDisplay() != null && !aoDisplayInfo.getScheduleDisplay().getScheduleComponentDisplays().isEmpty()) {
                            //TODO handle TBA state
                            //ScheduleComponentDisplay scheduleComponentDisplay = aoDisplayInfo.getScheduleDisplay().getScheduleComponentDisplays().get(0);
                            List<ScheduleComponentDisplayInfo> scheduleComponentDisplays = (List<ScheduleComponentDisplayInfo>) aoDisplayInfo.getScheduleDisplay().getScheduleComponentDisplays();
                            for (ScheduleComponentDisplay scheduleComponentDisplay : scheduleComponentDisplays) {
                                if (scheduleComponentDisplay.getBuilding() != null) {
                                    aoDisplayWrapper.setBuildingName(scheduleComponentDisplay.getBuilding().getBuildingCode(), true);
                                }
                                if (scheduleComponentDisplay.getRoom() != null) {
                                    aoDisplayWrapper.setRoomName(scheduleComponentDisplay.getRoom().getRoomCode(), true);
                                }
                                if (!scheduleComponentDisplay.getTimeSlots().isEmpty()) {
                                    if (scheduleComponentDisplay.getTimeSlots().get(0).getStartTime() != null) {
                                        aoDisplayWrapper.setStartTimeDisplay(millisToTime(scheduleComponentDisplay.getTimeSlots().get(0).getStartTime().getMilliSeconds()), true);
                                    }
                                    if (scheduleComponentDisplay.getTimeSlots().get(0).getEndTime() != null) {
                                        aoDisplayWrapper.setEndTimeDisplay(millisToTime(scheduleComponentDisplay.getTimeSlots().get(0).getEndTime().getMilliSeconds()), true);
                                    }
                                    aoDisplayWrapper.setDaysDisplayName(getDays(scheduleComponentDisplay.getTimeSlots().get(0).getWeekdays()), true);
                                }
                            }
                        }
                        // if ao is colocated AO add colocated info
                        if(isColocatedAo(aoDisplayInfo.getActivityOfferingCode(), aoInfoList))  {
                            String colocateInfo = CourseOfferingViewHelperUtil.createColocatedDisplayData(getAoInfo(aoDisplayInfo.getActivityOfferingCode(), aoInfoList), contextInfo);
                            aoDisplayWrapper.setColocatedAoInfo(colocateInfo);
                            co.setColocated(true);
                            co.setColocatedCoCode(colocateInfo);
                            form.setColocatedCO(true);
                            totalColocatedAos++;
                            iscolocated = true;
                        }
                        co.getAoToBeDeletedList().add(aoDisplayWrapper);
                    }

                    totalAos = totalAos + co.getAoToBeDeletedList().size();
                    co.setCrossListed(false);
                    if (co.getAlternateCOCodes() != null && co.getAlternateCOCodes().size() > 0) {
                        co.setCrossListed(true);
                        form.setCrossListedCO(true);
                        totalCrossListedCosToDelete++;
                    }
                }
                co.setJointDefined(false);

                if(iscolocated) {
                    totalColocatedCos++;
                }else {
                    // verify whether this CO is joint-defined or not
                    String jointDefinedCodes = getJointDefinedInfo(co);
                    if(!jointDefinedCodes.isEmpty()) {
                        co.setJointDefinedCoCode(jointDefinedCodes);
                        co.setJointDefined(true);
                        totalJointDefinedCosToDelete++;
                    }
                }
                qualifiedToDeleteList.add(co);

                if (hasDeletion) {
                    enabled++;
                }
            }
        }
        form.setNumOfCrossListedCosToDelete(totalCrossListedCosToDelete);
        form.setNumOfJointDefinedCosToDelete(totalJointDefinedCosToDelete);
        if(totalColocatedCos == totalCosToDelete) {
            form.setColocatedCoOnly(true);
            form.setNumOfColocatedCosToDelete(totalColocatedCos);
        }
        if(totalJointDefinedCosToDelete >= 1)  {
            form.setJointDefinedCo(true);
        }
        form.setNumOfColocatedAosToDelete(totalColocatedAos);
        form.setTotalAOsToBeDeleted(totalAos);
    }

    public void approveActivityOfferings(ARGCourseOfferingManagementForm      form) throws Exception{
        List<ActivityOfferingWrapper> aoList = form.getActivityWrapperList();
        ContextInfo contextInfo = createContextInfo();
        int checked = 0;
        int enabled = 0;
        for(ActivityOfferingWrapper ao : aoList) {
            if((!"0".equals(form.getSelectedTabId()))&& ao.getIsChecked()||
                    ( "0".equals(form.getSelectedTabId()))&& ao.getIsCheckedByCluster()){
                checked++;
                if(ao.isEnableApproveButton()) {
                    enabled++;
                    getCourseOfferingService().changeActivityOfferingState(ao.getAoInfo().getId(), LuiServiceConstants.LUI_AO_STATE_APPROVED_KEY, contextInfo);
                }
            }
        }

        if(checked > enabled){
            KSUifUtils.addGrowlMessageIcon(GrowlIcon.WARNING, CourseOfferingConstants.ACTIVITYOFFERING_TOOLBAR_APPROVED);
        }else{
            if(enabled == 1){
                KSUifUtils.addGrowlMessageIcon(GrowlIcon.INFORMATION, CourseOfferingConstants.ACTIVITYOFFERING_TOOLBAR_APPROVED_1_SUCCESS);
            }else {
                KSUifUtils.addGrowlMessageIcon(GrowlIcon.INFORMATION, CourseOfferingConstants.ACTIVITYOFFERING_TOOLBAR_APPROVED_N_SUCCESS);
            }
        }
    }

    public void draftActivityOfferings(ARGCourseOfferingManagementForm      form) throws Exception{
        List<ActivityOfferingWrapper> aoList = form.getActivityWrapperList();
        ContextInfo contextInfo = createContextInfo();
        int checked = 0;
        int enabled = 0;
        for(ActivityOfferingWrapper ao : aoList) {
            if((!"0".equals(form.getSelectedTabId()))&& ao.getIsChecked()||
                    ( "0".equals(form.getSelectedTabId()))&& ao.getIsCheckedByCluster()){
                checked++;
                if(ao.isEnableDraftButton()) {
                    enabled++;
                    getCourseOfferingService().changeActivityOfferingState(ao.getAoInfo().getId(), LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY, contextInfo);
                }
            }
        }

        if(checked > enabled){
            KSUifUtils.addGrowlMessageIcon(GrowlIcon.WARNING, CourseOfferingConstants.ACTIVITYOFFERING_TOOLBAR_DRAFT);
        }else{
            if(enabled == 1){
                KSUifUtils.addGrowlMessageIcon(GrowlIcon.INFORMATION, CourseOfferingConstants.ACTIVITYOFFERING_TOOLBAR_DRAFT_1_SUCCESS);
            }else {
                KSUifUtils.addGrowlMessageIcon(GrowlIcon.INFORMATION, CourseOfferingConstants.ACTIVITYOFFERING_TOOLBAR_DRAFT_N_SUCCESS);
            }
        }

    }
//    /**
//     * @param aoList The list of AOs to evaluate.
//     * @param selectedAction The state change action to perform.
//     * @throws Exception
//     */
//    @Override
//    public void changeActivityOfferingsState(List<ActivityOfferingWrapper> aoList, CourseOfferingInfo courseOfferingInfo, String selectedAction) throws Exception {
//        ContextInfo contextInfo = createContextInfo();
//        StateInfo draftState = getStateService().getState(LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY, contextInfo);
//        StateInfo approvedState = getStateService().getState(LuiServiceConstants.LUI_AO_STATE_APPROVED_KEY, contextInfo);
//
//        boolean hasBadStateWarning = false, hasStateChangedAO = false, isDraftAction = false;
//        String messageKeyWarn, messageKeyError;
//
//        //  Setup feedback message keys.
//        if (StringUtils.equals(CourseOfferingConstants.ACTIVITY_OFFERING_DRAFT_ACTION, selectedAction)) {
//            isDraftAction = true;
//            messageKeyWarn = CourseOfferingConstants.COURSEOFFERING_SET_TO_DRAFT_SOME_AOS_UPATED;
//            messageKeyError = CourseOfferingConstants.COURSEOFFERING_SET_TO_DRAFT_NO_AOS_UPDATED;
//        } else {
//            messageKeyWarn = CourseOfferingConstants.COURSEOFFERING_APPROVE_FOR_SCHEDULING_SOME_AOS_UPDATED;
//            messageKeyError = CourseOfferingConstants.COURSEOFFERING_APPROVE_FOR_SCHEDULING_NO_AOS_UPDATED;
//        }
//
//        for (ActivityOfferingWrapper wrapper : aoList) {
//            //  Only evaluate items that were selected/checked in the UI.
//            if (wrapper.getIsChecked()){
////                    (!"0".equals(form.getSelectedTabId()))&& aoWrapper.getIsChecked())||
////                ( "KS-CourseOfferingManagement-ViewAOClustersSection".equals(theForm.getActionParameters().get("toolBarSectionId"))&& aoWrapper.getIsCheckedByCluster())) {
//                //  If the action is "Set as Draft" then the current state of the AO must be "Approved".
//                if (isDraftAction) {
//                    if (StringUtils.equals(wrapper.getAoInfo().getStateKey(), LuiServiceConstants.LUI_AO_STATE_APPROVED_KEY)){
//                        wrapper.getAoInfo().setStateKey(LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY);
//                        wrapper.setStateName(draftState.getName());
//                        getCourseOfferingService().changeActivityOfferingState(wrapper.getAoInfo().getId(), LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY, contextInfo);
//                        if ( ! hasStateChangedAO) hasStateChangedAO = true;
//                    } else {
//                        if ( ! hasBadStateWarning) hasBadStateWarning = true;
//                    }
//                    //  If the action is "Approve for Scheduling" then AO state must be "Draft"
//                } else {
//                    if (StringUtils.equals(LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY, wrapper.getAoInfo().getStateKey())) {
//                        wrapper.getAoInfo().setStateKey(LuiServiceConstants.LUI_AO_STATE_APPROVED_KEY);
//                        wrapper.setStateName(approvedState.getName());
//                        getCourseOfferingService().changeActivityOfferingState(wrapper.getAoInfo().getId(), LuiServiceConstants.LUI_AO_STATE_APPROVED_KEY, contextInfo);
//                        if ( ! hasStateChangedAO) hasStateChangedAO = true;
//                    } else {
//                        if ( ! hasBadStateWarning) hasBadStateWarning = true;
//                    }
//                }
//            }
//        }
//
//        //  Set feedback message.
//        if ( ! hasStateChangedAO) {
//            GlobalVariables.getMessageMap().putError("selectedOfferingAction", messageKeyError);
//        } else {
//            if (hasBadStateWarning) {
//                GlobalVariables.getMessageMap().putWarning("selectedOfferingAction", messageKeyWarn);
//            }
//        }
//    }

    /**
     *  Notes by Bonnie: The following implementation tries to support "Approve for Scheduling" in two cases historically:
     *  1)when some COs are checked and approve for scheduling link or button is clicked
     *  vs.
     *  2)when a user simply click "Approve Subject Code for Scheduling" link with/without selecting any CO
     *  case 1) has been implemented by  approveCourseOfferings method -- invoked by Approve button in toolbar
     *  case 2) is the only use case when markCourseOfferingsForScheduling is invoked. Therefore the parameter
     *  checkedOnly and related checking for each CO seem not needed anymore...
     *  TODO: code cleanup??
     *
     *  Examines a List of CourseOffering wrappers and changes the state of each "checked" AO (meaning the
     *  CO was selected on the UI) from "Draft" to "Approved". If the AO has a state other than "Draft" the AO is ignored.
     *  Also, changes the state of the CourseOffering if appropriate.
     *
     * @param coWrappers, viewId, socStateKey provides list of CourseOfferings, viewId, socState.
     * @param checkedOnly True if the CO wrapper isChecked() flag should be respected.
     */
    public void markCourseOfferingsForScheduling(List<CourseOfferingListSectionWrapper> coWrappers,
                                                 String viewId, String socStateKey, boolean checkedOnly) throws Exception {
        boolean hasAOWarning = false, hasStateChangedAO = false, hasOrgWarning = false;
        ContextInfo contextInfo = createContextInfo();
        for (CourseOfferingListSectionWrapper coWrapper : coWrappers) {
            if (coWrapper.getIsChecked() || ! checkedOnly) {
                // Checking if the person is authorized to approve
                Map<String,String> permissionDetails = new HashMap<String,String>();
                Map<String,String> roleQualifications = new HashMap<String,String>();
                CourseOfferingInfo coInfo = getCourseOfferingService().getCourseOffering(coWrapper.getCourseOfferingId(), contextInfo);
                List<String> orgIds = coInfo.getUnitsDeploymentOrgIds();
                String orgIDs = "";
                if(orgIds != null && !orgIds.isEmpty()){
                    for (String orgId : orgIds) {
                        orgIDs = orgIDs + orgId + ",";
                    }
                }
                boolean canApproveAOs = true;
                if (orgIDs.length() > 0) {
                    String principalId = GlobalVariables.getUserSession().getPerson().getPrincipalId();

                    roleQualifications.put("org", orgIDs.substring(0, orgIDs.length()-1));

                    permissionDetails.put(KimConstants.AttributeConstants.VIEW_ID, viewId);
                    permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT, "approveSubj");

                    String socState = socStateKey==null?null:socStateKey.substring(socStateKey.lastIndexOf('.')+1);
                    permissionDetails.put("socState", socState);

                    canApproveAOs = getPermissionService().isAuthorizedByTemplate(principalId, "KS-ENR", KimConstants.PermissionTemplateNames.PERFORM_ACTION, permissionDetails, roleQualifications);
                }

                if (!canApproveAOs) {  // if can't approve AOs for all COs (because they are in a different org)
                    if (!hasOrgWarning) hasOrgWarning = true;
                    continue;
                } else {

                    List<ActivityOfferingInfo> activityOfferingInfos = getCourseOfferingService().getActivityOfferingsByCourseOffering(coWrapper.getCourseOfferingId(),contextInfo);
                    if (activityOfferingInfos.size() == 0) {
                        if ( ! hasAOWarning) hasAOWarning = true;
                        continue;
                    }
                    // Iterate through the AOs and state change Draft -> Approved.
                    for (ActivityOfferingInfo activityOfferingInfo : activityOfferingInfos) {
                        boolean isAOStateDraft = StringUtils.equals(activityOfferingInfo.getStateKey(), LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY);
                        if (isAOStateDraft) {
                            StatusInfo statusInfo = getCourseOfferingService().changeActivityOfferingState(activityOfferingInfo.getId(), LuiServiceConstants.LUI_AO_STATE_APPROVED_KEY, contextInfo);
                            if (!statusInfo.getIsSuccess()){
                                GlobalVariables.getMessageMap().putError("manageCourseOfferingsPage", CourseOfferingConstants.COURSE_OFFERING_STATE_CHANGE_ERROR,coWrapper.getCourseOfferingCode(),statusInfo.getMessage());
                            }
                            //  Flag if any AOs can be state changed. This affects the error message whi.
                            if (statusInfo.getIsSuccess()){
                                hasStateChangedAO = true;
                            }
                        } else {
                            //  Flag if any AOs are not in a valid state for approval.
                            if ( ! hasAOWarning) hasAOWarning = true;
                        }
                    }
                }
            }
        }
        //  Set feedback messages.
        if ( ! hasStateChangedAO) {
            GlobalVariables.getMessageMap().putError("manageCourseOfferingsPage", CourseOfferingConstants.COURSEOFFERING_NONE_APPROVED);
        } else {
            if (hasAOWarning) {
                GlobalVariables.getMessageMap().putWarning("manageCourseOfferingsPage", CourseOfferingConstants.COURSEOFFERING_WITH_AO_DRAFT_APPROVED_ONLY);
            }
            if (hasOrgWarning) {
                GlobalVariables.getMessageMap().putWarning("manageCourseOfferingsPage", CourseOfferingConstants.COURSEOFFERING_WITH_AO_ORG_APPROVED_ONLY);
            }
        }
    }

    private void setSocStateKeys (ARGCourseOfferingManagementForm form, List<String> socIds) throws Exception{
        if (socIds != null && !socIds.isEmpty()) {
            List<SocInfo> targetSocs = this.getSocService().getSocsByIds(socIds, createContextInfo());
            for (SocInfo soc: targetSocs) {
                if (soc.getTypeKey().equals(CourseOfferingSetServiceConstants.MAIN_SOC_TYPE_KEY)) {
                    form.setSocStateKey(soc.getStateKey());
                    form.setSocSchedulingStateKey(soc.getSchedulingStateKey());
                    return;
                }
            }
        }
    }

    public static String trimTrailing0(String creditValue){
        if (creditValue.indexOf(".0") > 0) {
            return creditValue.substring(0, creditValue.length( )- 2);
        } else {
            return creditValue;
        }
    }

    private boolean isColocatedAo(String aoCode, List<ActivityOfferingInfo> aoList) {
        for(ActivityOfferingInfo ao : aoList) {
            if(StringUtils.equals(aoCode, ao.getActivityCode())) {
                if(ao.getIsPartOfColocatedOfferingSet()) {
                    return true;
                }
            }
        }
        return false;
    }

    private ActivityOfferingInfo getAoInfo(String aoCode, List<ActivityOfferingInfo> aoList) {
        for(ActivityOfferingInfo ao : aoList) {
            if(StringUtils.equals(aoCode, ao.getActivityCode())) {
                if(ao.getIsPartOfColocatedOfferingSet()) {
                    return ao;
                }
            }
        }
        return null;
    }

    private String getJointDefinedInfo(CourseOfferingListSectionWrapper co) {
        if(co == null) return null;

        List<CourseInfo> coInfoList = CourseOfferingViewHelperUtil.getMatchingCoursesFromClu(co.getCourseOfferingCode());
        StringBuffer jointDefinedCodes  = new StringBuffer();

        for(CourseInfo coInfo : coInfoList) {
            List<CourseJointInfo> jointList = coInfo.getJoints();
            if(!jointList.isEmpty() && jointList.size() >= 1 ) {
                for(CourseJointInfo jointInfo : jointList)  {
                    jointDefinedCodes.append(jointInfo.getSubjectArea());
                    jointDefinedCodes.append(jointInfo.getCourseNumberSuffix());
                    jointDefinedCodes.append(" ");
                }

            }
        }

        return jointDefinedCodes.toString();
    }

    private CourseOfferingService _getCourseOfferingService() {
        if (coService == null) {
            coService = (CourseOfferingService) GlobalResourceLoader.getService(new QName(CourseOfferingServiceConstants.NAMESPACE,
                    CourseOfferingServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return coService;
    }

    private AcademicCalendarService getAcalService() {
        if (acalService == null) {
            acalService = (AcademicCalendarService) GlobalResourceLoader.getService(new QName(AcademicCalendarServiceConstants.NAMESPACE,
                    AcademicCalendarServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return acalService;
    }

    private String millisToTime(Long milliseconds){
        if(milliseconds == null){
            return null;
        }
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(milliseconds);
        return DateFormatters.HOUR_MINUTE_AM_PM_TIME_FORMATTER.format(cal.getTime());

    }

    private String convertIntoDaysDisplay(int day) {
        String dayOfWeek;
        switch (day) {
            case 1:
                dayOfWeek = SchedulingServiceConstants.SUNDAY_TIMESLOT_DISPLAY_DAY_CODE;
                break;
            case 2:
                dayOfWeek = SchedulingServiceConstants.MONDAY_TIMESLOT_DISPLAY_DAY_CODE;
                break;
            case 3:
                dayOfWeek = SchedulingServiceConstants.TUESDAY_TIMESLOT_DISPLAY_DAY_CODE;
                break;
            case 4:
                dayOfWeek = SchedulingServiceConstants.WEDNESDAY_TIMESLOT_DISPLAY_DAY_CODE;
                break;
            case 5:
                dayOfWeek = SchedulingServiceConstants.THURSDAY_TIMESLOT_DISPLAY_DAY_CODE;
                break;
            case 6:
                dayOfWeek = SchedulingServiceConstants.FRIDAY_TIMESLOT_DISPLAY_DAY_CODE;
                break;
            case 7:
                dayOfWeek = SchedulingServiceConstants.SATURDAY_TIMESLOT_DISPLAY_DAY_CODE;
                break;
            default:
                dayOfWeek = StringUtils.EMPTY;
        }
        // TODO implement TBA when service stores it.
        return dayOfWeek;
    }

    private String getDays(List<Integer> intList) {

        StringBuilder sb = new StringBuilder();
        if(intList == null){
            return sb.toString();
        }

        for(Integer d : intList) {
            sb.append(convertIntoDaysDisplay(d));
        }

        return sb.toString();
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


    protected LRCService getLrcService() {
        if(lrcService == null) {
            lrcService = (LRCService) GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/lrc", "LrcService"));
        }
        return this.lrcService;
    }

    public SearchService getSearchService() {
        if(searchService == null) {
            searchService = (SearchService) GlobalResourceLoader.getService(new QName(CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "search", SearchService.class.getSimpleName()));
        }
        return searchService;
    }
    public AtpService getAtpService() {
        if (atpService == null){
            atpService = CourseOfferingResourceLoader.loadAtpService();
        }
        return atpService;
    }

    public CourseOfferingSetService getSocService() {
        // If it hasn't been set by Spring, then look it up by GlobalResourceLoader
        if (socService == null) {
            socService = (CourseOfferingSetService) GlobalResourceLoader.getService(new QName(CourseOfferingSetServiceConstants.NAMESPACE,
                    CourseOfferingSetServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return socService;
    }

    private static PermissionService getPermissionService() {
        if(permissionService == null){
            permissionService = KimApiServiceLocator.getPermissionService();
        }
        return permissionService;
    }

    public static IdentityService getIdentityService() {
        if(identityService == null){
            identityService = KimApiServiceLocator.getIdentityService();
        }
        return identityService;
    }

    public static void setIdentityService(IdentityService identityService) {
        ARGCourseOfferingManagementViewHelperServiceImpl.identityService = identityService;
    }
    public static LprService getLprService() {
        if(lprService == null){
            lprService = (LprService) GlobalResourceLoader.getService(new QName(LprServiceConstants.NAMESPACE,
                    LprServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return lprService;
    }

}
