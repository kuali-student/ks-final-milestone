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
package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.kim.api.identity.entity.EntityDefault;
import org.kuali.rice.kim.api.identity.entity.EntityDefaultQueryResults;
import org.kuali.rice.kim.api.identity.principal.Principal;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.ObjectUtils;
import org.kuali.rice.krms.api.repository.reference.ReferenceObjectBinding;
import org.kuali.student.common.collection.KSCollectionUtils;
import org.kuali.student.common.uif.util.GrowlIcon;
import org.kuali.student.common.uif.util.KSUifUtils;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingClusterWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingContextBar;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingListSectionWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.ExamOfferingClusterWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.ExamOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.RegistrationGroupWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.ScheduleCalcContainer;
import org.kuali.student.enrollment.class2.courseoffering.dto.ScheduleRequestCalcContainer;
import org.kuali.student.enrollment.class2.courseoffering.form.CourseOfferingManagementForm;
import org.kuali.student.enrollment.class2.courseoffering.service.CourseOfferingManagementViewHelperService;
import org.kuali.student.enrollment.class2.courseoffering.service.facade.ActivityOfferingResult;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingManagementToolbarUtil;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingManagementUtil;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingViewHelperUtil;
import org.kuali.student.enrollment.class2.courseoffering.util.ManageSocConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.RegistrationGroupConstants;
import org.kuali.student.enrollment.class2.courseofferingset.util.CourseOfferingSetUtil;
import org.kuali.student.enrollment.class2.scheduleofclasses.dto.ActivityOfferingDisplayWrapper;
import org.kuali.student.enrollment.class2.scheduleofclasses.dto.CourseOfferingDisplayWrapper;
import org.kuali.student.enrollment.class2.scheduleofclasses.form.ActivityOfferingDisplayUI;
import org.kuali.student.enrollment.class2.scheduleofclasses.form.CourseOfferingDisplayUI;
import org.kuali.student.enrollment.class2.scheduleofclasses.form.ScheduleOfClassesSearchForm;
import org.kuali.student.enrollment.class2.scheduleofclasses.sort.impl.ActivityOfferingTypeComparator;
import org.kuali.student.enrollment.class2.scheduleofclasses.util.ScheduleOfClassesConstants;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingClusterInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingDisplayInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingSetInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.enrollment.coursewaitlist.dto.CourseWaitListInfo;
import org.kuali.student.enrollment.examoffering.dto.ExamOfferingInfo;
import org.kuali.student.enrollment.examoffering.dto.ExamOfferingRelationInfo;
import org.kuali.student.enrollment.lpr.dto.LprInfo;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.BulkStatusInfo;
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
import org.kuali.student.r2.common.util.TimeOfDayHelper;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.common.util.constants.ExamOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.core.acal.dto.ExamPeriodInfo;
import org.kuali.student.r2.core.acal.dto.KeyDateInfo;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
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
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestComponentInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestInfo;
import org.kuali.student.r2.core.scheduling.dto.TimeSlotInfo;
import org.kuali.student.r2.core.scheduling.infc.ScheduleComponentDisplay;
import org.kuali.student.r2.core.scheduling.infc.TimeSlot;
import org.kuali.student.r2.core.scheduling.util.SchedulingServiceUtil;
import org.kuali.student.r2.core.search.dto.SearchParamInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.kuali.student.r2.lum.course.dto.ActivityInfo;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.dto.CourseJointInfo;
import org.kuali.student.r2.lum.course.dto.FormatInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.Arrays.asList;

/**
 * This class implements Course offering management functionality using auto-generated registration groups
 *
 * @author Kuali Student Team
 */
public class CourseOfferingManagementViewHelperServiceImpl extends CO_AO_RG_ViewHelperServiceImpl implements CourseOfferingManagementViewHelperService {

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(CourseOfferingManagementViewHelperServiceImpl.class);

    /**
     * This method fetches the <code>TermInfo</code> and validate for exact match
     *
     * @param form input CourseOfferingManagementForm
     * @throws Exception
     */
    public void populateTerm(CourseOfferingManagementForm form) throws Exception {

        String termCode = form.getTermCode();

        form.getCourseOfferingResultList().clear();

        List<TermInfo> terms = CourseOfferingManagementUtil.getAcademicCalendarService().getTermsByCode(termCode, createContextInfo());

        if (terms.isEmpty()) {
            GlobalVariables.getMessageMap().putError("termCode", CourseOfferingConstants.COURSEOFFERING_MSG_ERROR_NO_TERM_IS_FOUND, termCode);
        } else if (terms.size() > 1) {
            GlobalVariables.getMessageMap().putError("termCode", CourseOfferingConstants.COURSEOFFERING_MSG_ERROR_FOUND_MORE_THAN_ONE_TERM, termCode);
        } else {
            form.setTermInfo(terms.get(0));

            //Checking soc
            SocInfo soc = CourseOfferingSetUtil.getMainSocForTermId(form.getTermInfo().getId(), createContextInfo());

            if (soc == null) {
                GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, ManageSocConstants.MessageKeys.ERROR_SOC_NOT_EXISTS);
            } else {
                setSocStateKeys(form, soc);

                blockUserIfSocStateIs(form, CourseOfferingSetServiceConstants.SOC_SCHEDULING_STATE_IN_PROGRESS, ManageSocConstants.MessageKeys.ERROR_CANNOT_ACCESS_COURSE_OFFERING_WHILE_SOC_IS_IN_PROGRESS);
                blockUserIfSocStateIs(form, CourseOfferingSetServiceConstants.PUBLISHING_SOC_STATE_KEY, ManageSocConstants.MessageKeys.ERROR_CANNOT_ACCESS_COURSE_OFFERING_WHILE_SOC_IS_PUBLISHING);

                // setting term first day of classes
                List<KeyDateInfo> keyDateInfoList = CourseOfferingManagementUtil.getAcademicCalendarService().getKeyDatesForTerm(form.getTermInfo().getId(), createContextInfo());
                Date termClassStartDate = null;
                for (KeyDateInfo keyDateInfo : keyDateInfoList) {
                    if (keyDateInfo.getTypeKey().equalsIgnoreCase(AtpServiceConstants.MILESTONE_SEATPOOL_FIRST_DAY_OF_CLASSES_TYPE_KEY) && keyDateInfo.getStartDate() != null) {
                        termClassStartDate = keyDateInfo.getStartDate();
                        break;
                    }
                }
                for (KeyDateInfo keyDateInfo : keyDateInfoList) {

                    if (keyDateInfo.getTypeKey().equalsIgnoreCase(AtpServiceConstants.MILESTONE_INSTRUCTIONAL_PERIOD_TYPE_KEY) && keyDateInfo.getStartDate() != null && keyDateInfo.getEndDate() != null) {
                        termClassStartDate = keyDateInfo.getStartDate();
                        break;
                    }
                }
                form.setTermClassStartDate(termClassStartDate);
            }
        }
    }

    private void blockUserIfSocStateIs(CourseOfferingManagementForm form, String socStateKeyToBlockUserOn, String errorMessageKey) {
        errorMessageKey = StringUtils.defaultIfEmpty(errorMessageKey, ManageSocConstants.MessageKeys.ERROR_CANNOT_ACCESS_COURSE_OFFERING_WHILE_SOC_INVALID_STATE_DEFAULT);

        if (StringUtils.equalsIgnoreCase(form.getSocStateKey(), socStateKeyToBlockUserOn)) {
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, errorMessageKey);
        }
    }

    /**
     * This method loads all the course offerings for a term and course code.
     *
     * @param termInfo   Term Info
     * @param courseCode Course Code
     * @param form       Input Form
     * @throws Exception
     */
    public void loadCourseOfferingsByTermAndCourseCode(TermInfo termInfo, String courseCode, CourseOfferingManagementForm form) throws Exception {

        SearchRequestInfo searchRequest = new SearchRequestInfo(CourseOfferingManagementSearchImpl.CO_MANAGEMENT_SEARCH.getKey());
        searchRequest.addParam(CourseOfferingManagementSearchImpl.SearchParameters.COURSE_CODE, courseCode);
        searchRequest.addParam(CourseOfferingManagementSearchImpl.SearchParameters.ATP_ID, termInfo.getId());
        searchRequest.addParam(CourseOfferingManagementSearchImpl.SearchParameters.CROSS_LIST_SEARCH_ENABLED, BooleanUtils.toStringTrueFalse(true));

        // Check to see if the "exact co code match" param has been set. If so, add a query param.
        boolean isExactMatchSearch = BooleanUtils.toBoolean(form.getViewRequestParameters().get(CourseOfferingManagementSearchImpl.SearchParameters.IS_EXACT_MATCH_CO_CODE_SEARCH));
        if (isExactMatchSearch) {
            searchRequest.addParam(CourseOfferingManagementSearchImpl.SearchParameters.IS_EXACT_MATCH_CO_CODE_SEARCH, BooleanUtils.toStringTrueFalse(true));
        }

        loadCourseOfferings(searchRequest, form);

        if (form.getCourseOfferingResultList().isEmpty()) {
            LOG.error("Error: Can't find any Course Offering for a course code: " + courseCode + " in term: " + termInfo.getName());
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, CourseOfferingConstants.COURSEOFFERING_MSG_ERROR_NO_COURSE_OFFERING_IS_FOUND, "course code", courseCode, termInfo.getName());
        }

        form.setContextBar(CourseOfferingContextBar.NEW_INSTANCE(form.getTermInfo(), form.getSocStateKey(),
                getStateService(), CourseOfferingManagementUtil.getAcademicCalendarService(), createContextInfo()));
    }

    /**
     * This method loads all the course offerings for a term and subject area/code.
     *
     * @param termId      term id
     * @param subjectCode subject area
     * @param form        course offering management form
     * @throws Exception
     */
    public void loadCourseOfferingsByTermAndSubjectCode(String termId, String subjectCode, CourseOfferingDisplayUI form) throws Exception {

        SearchRequestInfo searchRequest = new SearchRequestInfo(CourseOfferingManagementSearchImpl.CO_MANAGEMENT_SEARCH.getKey());
        searchRequest.addParam(CourseOfferingManagementSearchImpl.SearchParameters.SUBJECT_AREA, subjectCode);
        searchRequest.addParam(CourseOfferingManagementSearchImpl.SearchParameters.ATP_ID, termId);
        searchRequest.addParam(CourseOfferingManagementSearchImpl.SearchParameters.CROSS_LIST_SEARCH_ENABLED, BooleanUtils.toStringTrueFalse(true));

        loadCourseOfferings(searchRequest, form);

        /* if (form.getCourseOfferingResultList().isEmpty()) {
            LOG.error("Error: Can't find any Course Offering for a Subject Code: " + subjectCode + " in term: " + termId);
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, CourseOfferingConstants.COURSEOFFERING_MSG_ERROR_NO_COURSE_OFFERING_IS_FOUND, "Subject Code", subjectCode, termId);
        }*/

    }

    /**
     * This method fetches, prepares and sets
     * activityWrapperList for View All Activities tab,
     * clusterResultList for View byClusters
     * rgResultList for View Registration Groupsall the course offerings for a term and course/subject code.
     * Note: Use the other overloaded function when the client needs to specify search params
     *
     * @param form input form
     * @throws Exception
     */
    public void build_AOs_RGs_AOCs_Lists_For_TheCourseOffering(ActivityOfferingDisplayUI form) throws Exception {


        //First search for AOs and Cluster information
        SearchRequestInfo searchRequestInfo = new SearchRequestInfo(ActivityOfferingSearchServiceImpl.AOS_AND_CLUSTERS_BY_CO_ID_SEARCH_KEY);

        this.build_AOs_RGs_AOCs_Lists_For_TheCourseOffering(form, searchRequestInfo, false);
    }


    /**
     * This method fetches, prepares and sets
     * activityWrapperList for View All Activities tab,
     * clusterResultList for View byClusters
     * rgResultList for View Registration Groupsall the course offerings for a term and course/subject code.
     * Note: Use the other overloaded function when the client does not need to specify search params
     *
     * @param form input form
     * @param sr   Client supplied search info with params
     * @throws Exception
     */
    public void build_AOs_RGs_AOCs_Lists_For_TheCourseOffering(ActivityOfferingDisplayUI form, SearchRequestInfo sr, boolean socView) throws Exception {
        //KSENROLL-6102 performance improvements, delete this code when performance work is complete

        //New Search Stuff!
        String coId = form.getCourseOfferingId();
        List<String> filterRegGroupStates = null;
        List<String> filterAOStates = null;

        for (SearchParamInfo param : sr.getParams()) {
            if (StringUtils.equals(param.getKey(), ActivityOfferingSearchServiceImpl.SearchParameters.REGGROUP_STATES)) {
                filterRegGroupStates = param.getValues();
            }
            if (StringUtils.equals(param.getKey(), ActivityOfferingSearchServiceImpl.SearchParameters.AO_STATES)) {
                filterAOStates = param.getValues();
            }
        }

        sr.addParam(ActivityOfferingSearchServiceImpl.SearchParameters.CO_ID, coId);
        SearchResultInfo results = CourseOfferingManagementUtil.getSearchService().search(sr, null);

        Map<String, List<ActivityOfferingWrapper>> sch2aoMap = new HashMap<String, List<ActivityOfferingWrapper>>();
        List<String> aoIdsWithoutSch = new ArrayList<String>();
        Map<String, ActivityOfferingClusterWrapper> clusterMap = new HashMap<String, ActivityOfferingClusterWrapper>();
        Map<String, ActivityOfferingWrapper> aoMap = new HashMap<String, ActivityOfferingWrapper>();
        Map<String, FormatOfferingInfo> foIds = new HashMap<String, FormatOfferingInfo>();
        Map<String, List<ScheduleCalcContainer>> ao2sch = new HashMap<String, List<ScheduleCalcContainer>>();
        Map<String, List<ScheduleRequestCalcContainer>> ao2schReq = new HashMap<String, List<ScheduleRequestCalcContainer>>();
        ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();

        //Parse the search results
        List<ActivityOfferingWrapper> wrappers = processAoClusterData(results, sch2aoMap, clusterMap, aoMap, foIds, aoIdsWithoutSch, contextInfo);
        setupRuleIndicator(wrappers);
        processAosData(coId, clusterMap);

        //Sort Activity Wrappers and Clusters
        ArrayList<ActivityOfferingClusterWrapper> clusterWrapperList = new ArrayList<ActivityOfferingClusterWrapper>(clusterMap.values());
        if (clusterWrapperList.size() > 1) {
            Collections.sort(clusterWrapperList, new Comparator<ActivityOfferingClusterWrapper>() {
                @Override
                public int compare(ActivityOfferingClusterWrapper o1, ActivityOfferingClusterWrapper o2) {
                    int nameComparison = o1.getAoCluster().getPrivateName().compareToIgnoreCase(o2.getAoCluster().getPrivateName());
                    int formatComparison = o1.getFormatNameForDisplay().compareToIgnoreCase(o2.getFormatNameForDisplay());

                    if (formatComparison == 0) {
                        return nameComparison;
                    } else {
                        return formatComparison;
                    }
                }
            });
        }

        if (wrappers.size() > 1) {
            Collections.sort(wrappers, new Comparator<ActivityOfferingWrapper>() {

                @Override
                public int compare(ActivityOfferingWrapper o1, ActivityOfferingWrapper o2) {
                    int typeComparison = (o1.getTypeName().compareTo(o2.getTypeName())) * -1;
                    int nameComparison = o1.getActivityCode().compareTo(o2.getActivityCode());
                    if (typeComparison == 0) {
                        return nameComparison;
                    } else {
                        return typeComparison;
                    }
                }
            });
        }

        //Set the items in the form
        form.setActivityWrapperList(wrappers);
        form.getClusterResultList().clear();
        form.getClusterResultList().addAll(clusterWrapperList);

        //fix for KSENROLL-7886: foIds was populated in processAoClusterData method.
        //However if one FO does not have a cluster and AO, that FO won't be added to foIds, which causes NPE error later.
        foIds = new HashMap<String, FormatOfferingInfo>();
        List<FormatOfferingInfo> foList = CourseOfferingManagementUtil.getCourseOfferingService().getFormatOfferingsByCourseOffering(coId, ContextUtils.createDefaultContextInfo());
        for (FormatOfferingInfo fo : foList) {
            foIds.put(fo.getId(), fo);
        }

        form.setFoId2aoTypeMap(foIds);

        if (!aoMap.keySet().isEmpty()) {
            //Process Colocated
            sr = new SearchRequestInfo(ActivityOfferingSearchServiceImpl.COLOCATED_AOS_BY_AO_IDS_SEARCH_KEY);
            sr.addParam(ActivityOfferingSearchServiceImpl.SearchParameters.AO_IDS, new ArrayList<String>(aoMap.keySet()));
            sr.addParam(ActivityOfferingSearchServiceImpl.SearchParameters.AO_STATES, filterAOStates);
            results = CourseOfferingManagementUtil.getSearchService().search(sr, null);

            processColocated(results, aoMap);


            //Addin LPR data
            processInstructors(aoMap, ContextUtils.createDefaultContextInfo());

            //Search for schedule information
            if (!sch2aoMap.isEmpty()) {
                sr = new SearchRequestInfo(CoreSearchServiceImpl.SCH_AND_ROOM_SEARH_BY_ID_SEARCH_KEY);
                sr.addParam(CoreSearchServiceImpl.SearchParameters.SCHEDULE_IDS, new ArrayList<String>(sch2aoMap.keySet()));
                results = CourseOfferingManagementUtil.getSearchService().search(sr, null);

                // the next two methods pull scheduling data from the DB and put them into the ao2sch map
                processScheduleInfo(results, sch2aoMap, ao2sch);
            }
            processScheduleRequestsForAos(aoIdsWithoutSch, ao2schReq, contextInfo);

            // this takes the scheduling data and puts it into the screen form
            processScheduleData(aoMap, ao2sch, ao2schReq);

            //Search for registration group information
            sr = new SearchRequestInfo(ActivityOfferingSearchServiceImpl.REG_GROUPS_BY_CO_ID_SEARCH_KEY);
            sr.addParam(ActivityOfferingSearchServiceImpl.SearchParameters.CO_ID, form.getCourseOfferingId());
            if (filterRegGroupStates != null && !filterRegGroupStates.isEmpty()) {
                sr.addParam(ActivityOfferingSearchServiceImpl.SearchParameters.REGGROUP_STATES, filterRegGroupStates);
            }
            results = CourseOfferingManagementUtil.getSearchService().search(sr, null);

            List<RegistrationGroupWrapper> rgWrappers;
            if (socView) {
                rgWrappers = processRgDataForSocView(results, clusterMap, aoMap);
            } else {
                rgWrappers = processRgData(results, clusterMap, aoMap);
            }

            //Sort rgWrappers
            if (rgWrappers.size() > 1) {
                Collections.sort(rgWrappers, new Comparator<RegistrationGroupWrapper>() {
                    @Override
                    public int compare(RegistrationGroupWrapper o1, RegistrationGroupWrapper o2) {
                        return o1.getRgInfo().getName().compareTo(o2.getRgInfo().getName());
                    }
                });
            }

            form.setRgResultList(rgWrappers);

            form.setHasMoreThanOneCluster(clusterMap.size() > 1);


            //Validate Reg Groups
            Date startOfValidation = new Date();
            int i = 0;
            for (ActivityOfferingClusterWrapper cluster : clusterWrapperList) {
                List<RegistrationGroupInfo> rgInfos = new ArrayList<RegistrationGroupInfo>();
                for (RegistrationGroupWrapper rgWrapper : cluster.getRgWrapperList()) {
                    rgInfos.add(rgWrapper.getRgInfo());
                }
                List<ActivityOfferingInfo> aoInfos = new ArrayList<ActivityOfferingInfo>();
                for (ActivityOfferingWrapper aoWrapper : cluster.getAoWrapperList()) {
                    aoInfos.add(aoWrapper.getAoInfo());
                }
                _validateRegistrationGroupsPerCluster(rgInfos, aoInfos, cluster, form, i, ao2sch, ao2schReq, aoMap);

                // Test the Cluster for Multiple AO types and Term types
                _validateMulitpleTermsPerCluster(form.getFoId2aoTypeMap().get(cluster.getFormatOfferingId()).getActivityOfferingTypeKeys(), cluster, i);

                i++;
            }
            Date endOfValidation = new Date();
            LOG.info("Time of RG Validation:" + (endOfValidation.getTime() - startOfValidation.getTime()) + "ms");
        }
    }

    private void _validateMulitpleTermsPerCluster(List<String> aoTypeKeys, ActivityOfferingClusterWrapper cluster, int clusterIndex) {
        // Test the Cluster for Multiple AO types and Term types
        if (aoTypeKeys.size() > 1) {
            Set<String> termNameSet = new HashSet<String>();
            Set<String> termNames = new HashSet<String>();

            // Tests for multiple subTerms
            for (ActivityOfferingWrapper aoWrapper : cluster.getAoWrapperList()) {
                String termName = StringUtils.isEmpty(aoWrapper.getSubTermId()) ? aoWrapper.getTermName() : aoWrapper.getSubTermName();
                if (!termNameSet.contains(termName)) {
                    termNameSet.add(termName);
                }
            }
            if (termNameSet.size() > 1) {
                StringBuilder termNameStr = new StringBuilder();
                for (String termName : termNameSet) {
                    termNameStr.append(termName).append(", ");
                }
                termNameStr.delete(termNameStr.length() - 2, termNameStr.length());
                GlobalVariables.getMessageMap().putWarningForSectionId("activityOfferingsPerCluster_line" + clusterIndex, RegistrationGroupConstants.MSG_ERROR_CLUSTER_MULTIPLE_TERMS, cluster.getAoCluster().getPrivateName(), termNameStr.toString());
            }
        }
    }

    private void processColocated(SearchResultInfo searchResults, Map<String, ActivityOfferingWrapper> aoMap) {

        for (SearchResultRowInfo row : searchResults.getRows()) {
            String aoId = null;
            String aoCode = null;
            String coCode = null;

            for (SearchResultCellInfo cell : row.getCells()) {
                if (ActivityOfferingSearchServiceImpl.SearchResultColumns.AO_ID.equals(cell.getKey())) {
                    aoId = cell.getValue();
                } else if (ActivityOfferingSearchServiceImpl.SearchResultColumns.AO_CODE.equals(cell.getKey())) {
                    aoCode = cell.getValue();
                } else if (ActivityOfferingSearchServiceImpl.SearchResultColumns.CO_CODE.equals(cell.getKey())) {
                    coCode = cell.getValue();
                }
            }
            ActivityOfferingWrapper aoWrapper = aoMap.get(aoId);
            aoWrapper.setColocatedAO(true);
            if (aoWrapper.getColocatedAoInfo() == null || aoWrapper.getColocatedAoInfo().isEmpty()) {
                aoWrapper.setColocatedAoInfo(coCode + " " + aoCode);
            } else {
                aoWrapper.setColocatedAoInfo(aoWrapper.getColocatedAoInfo() + "<br/>" + coCode + " " + aoCode);
            }

        }
    }

    private void processInstructors(Map<String, ActivityOfferingWrapper> aoMap, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException {
        List<LprInfo> lprInfos = CourseOfferingManagementUtil.getLprService().getLprsByLuis(new ArrayList<String>(aoMap.keySet()), contextInfo);
        if (lprInfos != null) {
            Map<String, Set<String>> principalId2aoIdMap = new HashMap<String, Set<String>>();
            for (LprInfo lprInfo : lprInfos) {
                //  Only include the main instructor.
                if (!StringUtils.equals(lprInfo.getTypeKey(), LprServiceConstants.INSTRUCTOR_MAIN_TYPE_KEY)) {
                    continue;
                }
                Set<String> aoIds = principalId2aoIdMap.get(lprInfo.getPersonId());
                if (aoIds == null) {
                    aoIds = new HashSet<String>();
                    principalId2aoIdMap.put(lprInfo.getPersonId(), aoIds);
                }
                aoIds.add(lprInfo.getLuiId());
            }
            if (!principalId2aoIdMap.keySet().isEmpty()) {
                EntityDefaultQueryResults results = getInstructorsInfoFromKim(new ArrayList<String>(principalId2aoIdMap.keySet()));

                for (EntityDefault entity : results.getResults()) {
                    for (Principal principal : entity.getPrincipals()) {
                        Set<String> aoIds = principalId2aoIdMap.get(principal.getPrincipalId());
                        if (aoIds != null) {
                            for (String aoId : aoIds) {
                                ActivityOfferingWrapper activityOfferingWrapper = aoMap.get(aoId);
                                if (entity.getName() != null) {
                                    activityOfferingWrapper.setInstructorDisplayNames(entity.getName().getCompositeName(), true);
                                } else {
                                    activityOfferingWrapper.setInstructorDisplayNames(principal.getPrincipalId());
                                }
                            }
                        }
                    }
                }

            }
        }
    }

    /**
     * Add scheduling information to the map if there are no "actual" schedules already in place for a particular AO.
     *
     * @param aoIdsWithoutSch list of aoIds without schedule information
     * @param ao2sch          map of aos to schedules
     * @param contextInfo     @throws Exception
     */
    protected void processScheduleRequestsForAos(Collection<String> aoIdsWithoutSch, Map<String, List<ScheduleRequestCalcContainer>> ao2sch, ContextInfo contextInfo) throws Exception {

        if (!aoIdsWithoutSch.isEmpty()) {
            Set<String> buildingIds = new HashSet<String>();
            Set<String> roomIds = new HashSet<String>();
            Set<String> timeslotIds = new HashSet<String>();

            Map<String, BuildingInfo> buildingIdMap = new HashMap<String, BuildingInfo>();
            Map<String, RoomInfo> roomIdMap = new HashMap<String, RoomInfo>();
            Map<String, TimeSlotInfo> timeslotIdMap = new HashMap<String, TimeSlotInfo>();

            Map<String, List<ScheduleRequestInfo>> ao2srMap = new HashMap<String, List<ScheduleRequestInfo>>();
            HashSet<String> aoIdsSet = new HashSet<String>(aoIdsWithoutSch);
            for (String aoId : aoIdsSet) {
                ao2srMap.put(aoId, new ArrayList<ScheduleRequestInfo>());
                List<ScheduleRequestInfo> srList = CourseOfferingManagementUtil.getSchedulingService().getScheduleRequestsByRefObjects(CourseOfferingServiceConstants.REF_OBJECT_URI_ACTIVITY_OFFERING, new ArrayList<String>(asList(aoId)), contextInfo);
                ao2srMap.get(aoId).addAll(srList);
                for (ScheduleRequestInfo sr : srList) {
                    for (ScheduleRequestComponentInfo schRequestCom : sr.getScheduleRequestComponents()) {
                        buildingIds.addAll(schRequestCom.getBuildingIds());
                        roomIds.addAll(schRequestCom.getRoomIds());
                        timeslotIds.addAll(schRequestCom.getTimeSlotIds());
                    }
                }

            }

            //Lookup the information using bulk service calls and store in maps
            List<TimeSlotInfo> timeSlotInfos = CourseOfferingManagementUtil.getSchedulingService().getTimeSlotsByIds(new ArrayList<String>(timeslotIds), contextInfo);
            for (TimeSlotInfo timeSlotInfo : timeSlotInfos) {
                timeslotIdMap.put(timeSlotInfo.getId(), timeSlotInfo);
            }
            // The method cachkey in RoomServiceImpl is checking to see if a list is empty, contains null or contains empaty
            // and it throws an exception. To avoid that check to see if the list is a valid list first. Awkward, I know.
            if (!roomIds.isEmpty() && !roomIds.contains(null) && !roomIds.contains("")) {
                List<RoomInfo> roomInfos = CourseOfferingManagementUtil.getRoomService().getRoomsByIds(new ArrayList<String>(roomIds), contextInfo);
                for (RoomInfo roomInfo : roomInfos) {
                    roomIdMap.put(roomInfo.getId(), roomInfo);
                    buildingIds.add(roomInfo.getBuildingId());
                }
            }
            // The method cachkey in RoomServiceImpl is checking to see if a list is empty, contains null or contains empaty
            // and it throws an exception. To avoid that check to see if the list is a valid list first. Awkward, I know.
            if (!buildingIds.isEmpty() && !buildingIds.contains(null) && !buildingIds.contains("")) {
                List<BuildingInfo> buildingInfos = CourseOfferingManagementUtil.getRoomService().getBuildingsByIds(new ArrayList<String>(buildingIds), contextInfo);
                for (BuildingInfo buildingInfo : buildingInfos) {
                    buildingIdMap.put(buildingInfo.getId(), buildingInfo);
                }
            }

            for (String aoId : aoIdsSet) {
                List<ScheduleRequestCalcContainer> srccList = new ArrayList<ScheduleRequestCalcContainer>();
                ao2sch.put(aoId, srccList);

                for (ScheduleRequestInfo sr : ao2srMap.get(aoId)) {
                    for (ScheduleRequestComponentInfo src : sr.getScheduleRequestComponents()) {

                        List<RoomInfo> rooms = new ArrayList<RoomInfo>();
                        List<BuildingInfo> buildings = new ArrayList<BuildingInfo>();
                        List<TimeSlotInfo> timeSlots = new ArrayList<TimeSlotInfo>();

                        for (String roomId : src.getRoomIds()) {
                            if (roomId != null) {
                                rooms.add(roomIdMap.get(roomId));
                                buildings.add(buildingIdMap.get(roomIdMap.get(roomId).getBuildingId()));
                            }
                        }

                        if (src.getRoomIds().isEmpty()) {
                            for (String buildingId : src.getBuildingIds()) {
                                buildings.add(buildingIdMap.get(buildingId));
                            }
                        }

                        for (String timeSlotId : src.getTimeSlotIds()) {
                            TimeSlotInfo timeSlotInfo = timeslotIdMap.get(timeSlotId);
                            timeSlots.add(timeSlotInfo);

                        }
                        ScheduleRequestCalcContainer srcc = new ScheduleRequestCalcContainer(aoId, sr.getId(),
                                CourseOfferingServiceConstants.REF_OBJECT_URI_ACTIVITY_OFFERING,
                                timeSlots,
                                rooms,
                                buildings,
                                src.getIsTBA());
                        srccList.add(srcc);
                    }
                }
            }
        }
    }

    private void processScheduleInfo(SearchResultInfo searchResults, Map<String, List<ActivityOfferingWrapper>> sch2aoMap, Map<String, List<ScheduleCalcContainer>> ao2sch) throws InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException, DoesNotExistException {
        //Process the search results

        for (SearchResultRowInfo row : searchResults.getRows()) {
            String schId = null;
            String startTime = null;
            String endTime = null;
            Boolean tbaInd = null;
            String roomCode = null;
            String buildingName = null;
            String buildingCode = null;
            String weekdays = null;
            for (SearchResultCellInfo cell : row.getCells()) {
                if (CoreSearchServiceImpl.SearchResultColumns.SCH_ID.equals(cell.getKey())) {
                    schId = cell.getValue();
                } else if (CoreSearchServiceImpl.SearchResultColumns.START_TIME.equals(cell.getKey())) {
                    startTime = cell.getValue();
                } else if (CoreSearchServiceImpl.SearchResultColumns.END_TIME.equals(cell.getKey())) {
                    endTime = cell.getValue();
                } else if (CoreSearchServiceImpl.SearchResultColumns.TBA_IND.equals(cell.getKey())) {
                    tbaInd = Boolean.parseBoolean(cell.getValue());
                } else if (CoreSearchServiceImpl.SearchResultColumns.ROOM_CODE.equals(cell.getKey())) {
                    roomCode = cell.getValue();
                } else if (CoreSearchServiceImpl.SearchResultColumns.BLDG_NAME.equals(cell.getKey())) {
                    buildingName = cell.getValue();
                } else if (CoreSearchServiceImpl.SearchResultColumns.BLDG_CODE.equals(cell.getKey())) {
                    buildingCode = cell.getValue();
                } else if (CoreSearchServiceImpl.SearchResultColumns.WEEKDAYS.equals(cell.getKey())) {
                    weekdays = cell.getValue();
                }
            }
            for (ActivityOfferingWrapper aoWrapper : sch2aoMap.get(schId)) {

                ScheduleCalcContainer scheduleCalcContainer = new ScheduleCalcContainer(aoWrapper.getId(), schId, SchedulingServiceConstants.SCHEDULE_TYPE_SCHEDULE, startTime, endTime, weekdays, roomCode, buildingName, buildingCode, tbaInd);
                if (ao2sch.containsKey(aoWrapper.getId())) {
                    ao2sch.get(aoWrapper.getId()).add(scheduleCalcContainer);
                } else {
                    aoWrapper.getStartTime().clear();
                    aoWrapper.getEndTime().clear();
                    aoWrapper.getWeekDays().clear();
                    List<ScheduleCalcContainer> schList = new ArrayList<ScheduleCalcContainer>();
                    schList.add(scheduleCalcContainer);
                    aoWrapper.getStartTime().add(startTime);
                    aoWrapper.getEndTime().add(endTime);
                    aoWrapper.getWeekDays().add(weekdays);
                    ao2sch.put(aoWrapper.getId(), schList);
                }
            }
        }
    }

    private void processScheduleData(Map<String, ActivityOfferingWrapper> aoMap, Map<String, List<ScheduleCalcContainer>> ao2sch, Map<String, List<ScheduleRequestCalcContainer>> ao2schReq) throws Exception {
        for (Map.Entry<String, ActivityOfferingWrapper> aoEntry : aoMap.entrySet()) {
            ActivityOfferingWrapper aoWrapper = aoEntry.getValue();
            String aoId = aoEntry.getKey();

            if (ao2sch.containsKey(aoId)) {
                List<ScheduleCalcContainer> schedList = ao2sch.get(aoId);
                boolean newRow = false;
                for (ScheduleCalcContainer sched : schedList) {
                    TimeOfDayInfo start = sched.getStart().isEmpty() ? null
                            : TimeOfDayHelper.setMillis(Long.parseLong(sched.getStart()));
                    aoWrapper.setStartTimeDisplay(start == null ? ""
                            : TimeOfDayHelper.makeFormattedTimeForAOSchedules(start), newRow);
                    TimeOfDayInfo end = sched.getEnd().isEmpty() ? null
                            : TimeOfDayHelper.setMillis(Long.parseLong(sched.getEnd()));
                    aoWrapper.setEndTimeDisplay(end == null ? ""
                            : TimeOfDayHelper.makeFormattedTimeForAOSchedules(end), newRow);
                    aoWrapper.setBuildingName(sched.getBldgName(), newRow);
                    aoWrapper.setBuildingCode(sched.getBldgCode(), newRow);
                    aoWrapper.setBldgCodeSimple(sched.getBldgCode());
                    aoWrapper.setRoomName(sched.getRoomCode(), newRow);
                    aoWrapper.setDaysDisplayName(sched.getWeekdays(), newRow);
                    aoWrapper.setTbaDisplayName(sched.getTbaInd(), newRow);
                    HashMap schedulingStateHash = aoWrapper.getSchedulingStateHash();
                    String key = aoWrapper.getAoInfo().getSchedulingStateKey();
                    if (StringUtils.isBlank(key)) {
                        key = LuiServiceConstants.LUI_AO_SCHEDULING_STATE_SCHEDULED_KEY;
                    }
                    String schState = (String) schedulingStateHash.get(key);
                    aoWrapper.setScheduledState(schState, newRow);
                    newRow = true;
                }

            } else if (ao2schReq.containsKey((aoId))) {
                List<ScheduleRequestCalcContainer> schedList = ao2schReq.get(aoId);
                for (ScheduleRequestCalcContainer sched : schedList) {

                    boolean newLine = aoWrapper.getTbaDisplayName() != null && !aoWrapper.getTbaDisplayName().isEmpty();
                    String cssStyle = "uif-scheduled-dl";

                    setRoomNameOnAoWrapper(sched, aoWrapper, newLine, cssStyle);
                    setBuildingNameAndCodeOnAoWrapper(sched, aoWrapper, newLine, cssStyle);
                    setTimesAndDaysAndScheduledStateOnAoWrapper(sched, aoWrapper, newLine, cssStyle);

                    aoWrapper.setTbaDisplayName(sched.getTbaInd(), true);
                }
            }
        }
    }

    private void setRoomNameOnAoWrapper(ScheduleRequestCalcContainer sched, ActivityOfferingWrapper aoWrapper, boolean newline, String cssStyle) {
        if (sched.getRooms() == null || sched.getRooms().isEmpty()) {
            aoWrapper.setRoomName(StringUtils.EMPTY, newline, cssStyle);
            return;
        }

        for (RoomInfo room : sched.getRooms()) {
            String roomName = StringUtils.defaultIfEmpty(room.getRoomCode(), StringUtils.EMPTY);
            aoWrapper.setRoomName(roomName, newline, cssStyle);
        }
    }

    private void setBuildingNameAndCodeOnAoWrapper(ScheduleRequestCalcContainer sched, ActivityOfferingWrapper aoWrapper, boolean newline, String cssStyle) {
        if (sched.getBldgs() == null || sched.getBldgs().isEmpty()) {
            aoWrapper.setBuildingName(StringUtils.EMPTY, newline, cssStyle);
            aoWrapper.setBuildingCode(StringUtils.EMPTY, newline, cssStyle);
            aoWrapper.setBldgCodeSimple(StringUtils.EMPTY);
            return;
        }

        for (BuildingInfo bldg : sched.getBldgs()) {
            String bldgName = StringUtils.defaultIfEmpty(bldg.getName(), StringUtils.EMPTY);
            String bldgCode = StringUtils.defaultIfEmpty(bldg.getBuildingCode(), StringUtils.EMPTY);
            aoWrapper.setBuildingName(bldgName, newline, "uif-scheduled-dl");
            aoWrapper.setBuildingCode(bldgCode, newline, "uif-scheduled-dl");
            aoWrapper.setBldgCodeSimple(bldgCode, bldgName, "uif-scheduled-dl");
        }
    }

    private void setTimesAndDaysAndScheduledStateOnAoWrapper(ScheduleRequestCalcContainer sched, ActivityOfferingWrapper aoWrapper, boolean newline, String cssStyle) {
        if (sched.getTimeSlots() == null || sched.getTimeSlots().isEmpty()) {
            setStartTimeOnAoWrapper(null, aoWrapper, newline, cssStyle);
            setEndTimeOnAoWrapper(null, aoWrapper, newline, cssStyle);
            setDaysOnAoWrapper(null, aoWrapper, newline, cssStyle);
            setScheduledStateOnAoWrapper(null, aoWrapper, newline, cssStyle);
            return;
        }

        for (TimeSlotInfo timeSlotInfo : sched.getTimeSlots()) {
            setStartTimeOnAoWrapper(timeSlotInfo, aoWrapper, newline, cssStyle);
            setEndTimeOnAoWrapper(timeSlotInfo, aoWrapper, newline, cssStyle);
            setDaysOnAoWrapper(timeSlotInfo, aoWrapper, newline, cssStyle);
            if (timeSlotInfo != null) {
                boolean timeAdded = false;
                if (timeSlotInfo.getStartTime() != null && timeSlotInfo.getStartTime().getHour() != null) {
                    aoWrapper.getStartTime().add(TimeOfDayHelper.getMillis(timeSlotInfo.getStartTime()).toString());
                    timeAdded = true;
                }
                if (timeSlotInfo.getEndTime() != null && timeSlotInfo.getEndTime().getHour() != null) {
                    aoWrapper.getEndTime().add(TimeOfDayHelper.getMillis(timeSlotInfo.getEndTime()).toString());
                    timeAdded = true;
                }
                if (timeSlotInfo.getWeekdays() != null && !timeSlotInfo.getWeekdays().isEmpty()) {
                    aoWrapper.getWeekDays().add(SchedulingServiceUtil.weekdaysList2WeekdaysString(timeSlotInfo.getWeekdays()));
                    timeAdded = true;
                }
                if (timeAdded) {
                    setScheduledStateOnAoWrapper(timeSlotInfo, aoWrapper, newline, cssStyle);
                }
            }
        }
    }

    private void setStartTimeOnAoWrapper(TimeSlotInfo timeSlotInfo, ActivityOfferingWrapper aoWrapper, boolean newline, String cssStyle) {
        if (timeSlotInfo == null || timeSlotInfo.getStartTime() == null || timeSlotInfo.getStartTime().getHour() == null) {
            aoWrapper.setStartTimeDisplay(StringUtils.EMPTY, newline, cssStyle);
            return;
        }
        aoWrapper.setStartTimeDisplay(TimeOfDayHelper.makeFormattedTimeForAOSchedules(timeSlotInfo.getStartTime()), newline, "uif-scheduled-dl");
    }

    private void setEndTimeOnAoWrapper(TimeSlotInfo timeSlotInfo, ActivityOfferingWrapper aoWrapper, boolean newline, String cssStyle) {
        if (timeSlotInfo == null || timeSlotInfo.getEndTime() == null || timeSlotInfo.getEndTime().getHour() == null) {
            aoWrapper.setEndTimeDisplay(StringUtils.EMPTY, newline, cssStyle);
            return;
        }
        aoWrapper.setEndTimeDisplay(TimeOfDayHelper.makeFormattedTimeForAOSchedules(timeSlotInfo.getEndTime()), newline, "uif-scheduled-dl");
    }

    private void setScheduledStateOnAoWrapper(TimeSlotInfo timeSlotInfo, ActivityOfferingWrapper aoWrapper, boolean newline, String cssStyle) {
        if (timeSlotInfo == null) {
            aoWrapper.setScheduledState(StringUtils.EMPTY, newline, cssStyle);
            return;
        }
        HashMap schedulingStateHash = aoWrapper.getSchedulingStateHash();
        String key = aoWrapper.getAoInfo().getSchedulingStateKey();
        if (StringUtils.isBlank(key)) {
            key = LuiServiceConstants.LUI_AO_SCHEDULING_STATE_UNSCHEDULED_KEY;
        }
        String schState = (String) schedulingStateHash.get(key);
        aoWrapper.setScheduledState(schState, newline, "uif-scheduled-dl");
    }

    private void setDaysOnAoWrapper(TimeSlotInfo timeSlotInfo, ActivityOfferingWrapper aoWrapper, boolean newline, String cssStyle) {
        if (timeSlotInfo == null || timeSlotInfo.getWeekdays() == null || timeSlotInfo.getWeekdays().isEmpty()) {
            aoWrapper.setDaysDisplayName(StringUtils.EMPTY, newline, cssStyle);
            return;
        }

        aoWrapper.setDaysDisplayName(SchedulingServiceUtil.weekdaysList2WeekdaysString(timeSlotInfo.getWeekdays()), newline, cssStyle);
    }

    private List<RegistrationGroupWrapper> processRgData(SearchResultInfo searchResults, Map<String, ActivityOfferingClusterWrapper> clusterMap,
                                                         Map<String, ActivityOfferingWrapper> aoMap)
            throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException {

        Map<String, RegistrationGroupWrapper> rgMap = new HashMap<String, RegistrationGroupWrapper>();
        Map<String, List<ActivityOfferingWrapper>> storedAOs = new HashMap<String, List<ActivityOfferingWrapper>>();

        processSearchResult(searchResults, aoMap, rgMap, storedAOs);

        for (Map.Entry<String, List<ActivityOfferingWrapper>> entry : storedAOs.entrySet()) {

            RegistrationGroupWrapper rgWrapper = rgMap.get(entry.getKey());

            //Sort aoList by AO types
            List<ActivityOfferingWrapper> aoList = storedAOs.get(entry.getKey());
            Collections.sort(aoList, new ActivityOfferingTypeComparator());

            boolean newLine = false;
            for (ActivityOfferingWrapper aoWrapper : aoList){

                rgWrapper.getRgInfo().getActivityOfferingIds().add(aoWrapper.getAoInfo().getId());

                ActivityOfferingClusterWrapper clusterWrapper = clusterMap.get(aoWrapper.getAoClusterID());
                if (clusterWrapper.getRgWrapperList().contains(rgWrapper)) {
                    clusterWrapper.getRgWrapperList().remove(rgWrapper);
                }
                if(newLine){
                    rgWrapper.setAoCluster(clusterWrapper.getAoCluster());
                    rgWrapper.setAoClusterName(clusterWrapper.getClusterNameForDisplay());
                }

                rgWrapper.setStartTime(aoWrapper.getStartTime());
                rgWrapper.setEndTime(aoWrapper.getEndTime());
                rgWrapper.setWeekDays(aoWrapper.getWeekDays());

                //if there are more than one instructors re-arrange the rows
                String lineBreaksInstructors = computeLineBreaks(aoWrapper.getInstructorDisplayNames());

                //if there are more than one Scheduling Information re-arrange the rows
                String lineBreaksDeliveries = computeLineBreaks(aoWrapper.getDaysDisplayName());

                String lineBreaks;
                //Set different line breaks according to number of instructors and number of Scheduling Information (can it be simpler?)
                if (lineBreaksInstructors.length() < lineBreaksDeliveries.length()) {
                    lineBreaks = lineBreaksDeliveries;
                    lineBreaksDeliveries = lineBreaksDeliveries.substring(0, lineBreaksDeliveries.length() - lineBreaksInstructors.length());
                    lineBreaksInstructors = "";
                } else {
                    lineBreaks = lineBreaksInstructors;
                    lineBreaksInstructors = lineBreaksInstructors.substring(0, lineBreaksInstructors.length() - lineBreaksDeliveries.length());
                    lineBreaksDeliveries = "";
                }

                //Set the wrapper
                rgWrapper.setAoMaxEnrText(rgWrapper.getAoMaxEnrText() + (newLine ? "<br/>" : "") + (aoWrapper.getAoInfo().getMaximumEnrollment() == null ? "" : aoWrapper.getAoInfo().getMaximumEnrollment()) + lineBreaks);
                if (rgWrapper.getRgMaxEnrText() != null && rgWrapper.getRgMaxEnrText().length() > 1 && aoWrapper.getAoInfo().getMaximumEnrollment() != null) {
                    Integer seats = Integer.parseInt(rgWrapper.getRgMaxEnrText());
                    Integer nSeats = aoWrapper.getAoInfo().getMaximumEnrollment();
                    if (seats.compareTo(nSeats) > 0) {
                        rgWrapper.setRgMaxEnrText(nSeats.toString());
                    }
                } else {
                    String rgSeats = aoWrapper.getAoInfo().getMaximumEnrollment() == null ? "" : aoWrapper.getAoInfo().getMaximumEnrollment().toString();
                    rgWrapper.setRgMaxEnrText(rgSeats);
                }
                rgWrapper.setAoStateNameText(rgWrapper.getAoStateNameText() + (newLine ? "<br/>" : "") + aoWrapper.getStateName() + lineBreaks);

                String aoCode = getAoActivityCodeText(rgWrapper, aoWrapper, lineBreaks);
                rgWrapper.setAoActivityCodeText(rgWrapper.getAoActivityCodeText() + (newLine ? "<br/>" : "") + aoCode);
                rgWrapper.setAoTypeNameText(rgWrapper.getAoTypeNameText() + (newLine ? "<br/>" : "") + aoWrapper.getTypeName() + lineBreaks);
                rgWrapper.setStartTimeDisplay(rgWrapper.getStartTimeDisplay() + (newLine ? "<br/>" : "") + aoWrapper.getStartTimeDisplay() + lineBreaksInstructors);
                rgWrapper.setEndTimeDisplay(rgWrapper.getEndTimeDisplay() + (newLine ? "<br/>" : "") + aoWrapper.getEndTimeDisplay() + lineBreaksInstructors);
                rgWrapper.setDaysDisplayName(rgWrapper.getDaysDisplayName() + (newLine ? "<br/>" : "") + aoWrapper.getDaysDisplayName() + lineBreaksInstructors);
                rgWrapper.setScheduledState(rgWrapper.getScheduledState() + (newLine ? "<br/>" : "") + aoWrapper.getScheduledState() + lineBreaksInstructors);
                rgWrapper.setRoomName(rgWrapper.getRoomName() + (newLine ? "<br/>" : "") + aoWrapper.getRoomName() + lineBreaksInstructors);
                rgWrapper.setBuildingName(rgWrapper.getBuildingName() + (newLine ? "<br/>" : "") + aoWrapper.getBuildingName() + lineBreaksInstructors);
                boolean isScheduled=aoWrapper.getBuildingName().contains("uif-scheduled-dl");
                if(isScheduled){
                    rgWrapper.setBuildingCodeWithTooltip(aoWrapper.getBldgCodeSimple(), aoWrapper.getBuildingName(), "uif-scheduled-dl");
                } else {
                    rgWrapper.setBuildingCodeWithTooltip(aoWrapper.getBldgCodeSimple(), aoWrapper.getBuildingName(), (newLine ? "<br>" : "uif-scheduled-dl"));
                }
                rgWrapper.setAoInstructorText(rgWrapper.getAoInstructorText() + (newLine ? "<br/>" : "") + (aoWrapper.getInstructorDisplayNames() == null ? "" : aoWrapper.getInstructorDisplayNames()) + lineBreaksDeliveries);

                clusterWrapper.getRgWrapperList().add(rgWrapper);

                newLine = true;
            }
        }

        return new ArrayList<RegistrationGroupWrapper>(rgMap.values());
    }

    private List<RegistrationGroupWrapper> processRgDataForSocView(SearchResultInfo searchResults, Map<String, ActivityOfferingClusterWrapper> clusterMap,
                                                                   Map<String, ActivityOfferingWrapper> aoMap)
            throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException {

        Map<String, RegistrationGroupWrapper> rgMap = new HashMap<String, RegistrationGroupWrapper>();
        Map<String, List<ActivityOfferingWrapper>> storedAOs = new HashMap<String, List<ActivityOfferingWrapper>>();

        processSearchResult(searchResults, aoMap, rgMap, storedAOs);

        for (Map.Entry<String, List<ActivityOfferingWrapper>> entry : storedAOs.entrySet()) {

            RegistrationGroupWrapper rgWrapper = rgMap.get(entry.getKey());

            //Sort aoList by AO types
            Collections.sort(entry.getValue(), new ActivityOfferingTypeComparator());

            for (ActivityOfferingWrapper aoWrapper : entry.getValue()){

                RegistrationGroupWrapper socRGWrapper = (RegistrationGroupWrapper) ObjectUtils.deepCopy(rgWrapper);

                ActivityOfferingClusterWrapper clusterWrapper = clusterMap.get(aoWrapper.getAoClusterID());
                clusterWrapper.getRgWrapperList().add(socRGWrapper);

                socRGWrapper.getRgInfo().getActivityOfferingIds().add(aoWrapper.getAoInfo().getId());
                socRGWrapper.setAoCluster(clusterWrapper.getAoCluster());
                socRGWrapper.setAoClusterName(clusterWrapper.getClusterNameForDisplay());

                socRGWrapper.setStartTime(aoWrapper.getStartTime());
                socRGWrapper.setEndTime(aoWrapper.getEndTime());
                socRGWrapper.setWeekDays(aoWrapper.getWeekDays());

                //if there are more than one instructors re-arrange the rows
                String lineBreaksInstructors = computeLineBreaks(aoWrapper.getInstructorDisplayNames());

                //if there are more than one Scheduling Information re-arrange the rows
                String lineBreaksDeliveries = computeLineBreaks(aoWrapper.getDaysDisplayName());

                //Set different line breaks according to number of instructors and number of Scheduling Information (can it be simpler?)
                String lineBreaks;
                if (lineBreaksInstructors.length() < lineBreaksDeliveries.length()) {
                    lineBreaks = lineBreaksDeliveries;
                    lineBreaksDeliveries = lineBreaksDeliveries.substring(0, lineBreaksDeliveries.length() - lineBreaksInstructors.length());
                    lineBreaksInstructors = "";
                } else {
                    lineBreaks = lineBreaksInstructors;
                    lineBreaksInstructors = lineBreaksInstructors.substring(0, lineBreaksInstructors.length() - lineBreaksDeliveries.length());
                    lineBreaksDeliveries = "";
                }

                //Set the wrapper
                socRGWrapper.setAoMaxEnrText(aoWrapper.getAoInfo().getMaximumEnrollment() == null ? "" : aoWrapper.getAoInfo().getMaximumEnrollment().toString());
                socRGWrapper.setAoStateNameText(aoWrapper.getStateName());
                socRGWrapper.setAoActivityCodeText(getAoActivityCodeText(rgWrapper, aoWrapper, lineBreaks));

                socRGWrapper.setAoTypeNameText(aoWrapper.getTypeName() + lineBreaks);
                socRGWrapper.setStartTimeDisplay(aoWrapper.getStartTimeDisplay() + lineBreaksInstructors);
                socRGWrapper.setEndTimeDisplay(aoWrapper.getEndTimeDisplay() + lineBreaksInstructors);
                socRGWrapper.setDaysDisplayName(aoWrapper.getDaysDisplayName() + lineBreaksInstructors);
                socRGWrapper.setRoomName(aoWrapper.getRoomName() + lineBreaksInstructors);
                socRGWrapper.setBuildingName(aoWrapper.getBuildingName() + lineBreaksInstructors);
                socRGWrapper.setBuildingCodeWithTooltip(aoWrapper.getBldgCodeSimple(), aoWrapper.getBuildingName());
                socRGWrapper.setAoInstructorText((aoWrapper.getInstructorDisplayNames() == null ? "" : aoWrapper.getInstructorDisplayNames()) + lineBreaksDeliveries);
            }

        }

        for(Map.Entry<String, ActivityOfferingClusterWrapper> cluster : clusterMap.entrySet()){

            List<RegistrationGroupWrapper> registrationGroupItems = cluster.getValue().getRgWrapperList();
            for (RegistrationGroupWrapper registrationGroupWrapper : registrationGroupItems) {
                RegistrationGroupWrapper regWrapper = new RegistrationGroupWrapper();
                for (RegistrationGroupWrapper partnerRegWrapper : registrationGroupItems) {
                    if (registrationGroupWrapper.getRgInfo().getName().equals(partnerRegWrapper.getRgInfo().getName()) &&
                            !registrationGroupWrapper.getAoActivityCodeText().equals(partnerRegWrapper.getAoActivityCodeText())) {
                        regWrapper = partnerRegWrapper;
                        break;
                    }
                }
                if (registrationGroupWrapper.getAoMaxEnrText() != null && registrationGroupWrapper.getAoMaxEnrText().length() > 1) {
                    if (regWrapper.getAoMaxEnrText() != null && regWrapper.getAoMaxEnrText().length() > 1) {
                        Integer seats = Integer.parseInt(registrationGroupWrapper.getAoMaxEnrText());
                        Integer nSeats = Integer.parseInt(regWrapper.getAoMaxEnrText());
                        if (seats.compareTo(nSeats) > 0) {
                            registrationGroupWrapper.setRgMaxEnrText(nSeats.toString());
                            regWrapper.setRgMaxEnrText(nSeats.toString());
                        } else {
                            registrationGroupWrapper.setRgMaxEnrText(seats.toString());
                            regWrapper.setRgMaxEnrText(seats.toString());
                        }
                    }
                }
            }
        }

        return new ArrayList<RegistrationGroupWrapper>(rgMap.values());
    }

    private String getAoActivityCodeText(RegistrationGroupWrapper rgWrapper, ActivityOfferingWrapper aoWrapper, String lineBreaks) {
        StringBuilder sb = new StringBuilder();
        sb.append(aoWrapper.getAoInfo().getActivityCode());

        //sub-term and honors icon and tooltip setup
        if (aoWrapper.getAoInfo().getIsHonorsOffering()) {
            sb.append("&nbsp;&nbsp;&nbsp;<img src=\"../themes/ksboot/images/h.png\" title=\"Honors activity");
            sb.append("\">");
            sb.append(lineBreaks);
        }

        if (!aoWrapper.getSubTermName().equals("None")) {  //sub-term? > icon + name and dates
            sb.append("&nbsp;&nbsp;&nbsp;<img src=\"../themes/ksboot/images/subterm_icon.png\" title=\"This activity is in ");
            sb.append(aoWrapper.getSubTermName());
            sb.append(" -\n");
            sb.append(aoWrapper.getTermStartEndDate());
            sb.append("\">");
            sb.append(lineBreaks);
        }
        return sb.toString();
    }

    private String computeLineBreaks(String displayString) {
        StringBuilder lineBreaksSB = new StringBuilder();
        if (displayString != null && StringUtils.contains(displayString, ("<br>"))) {   //more than one instructor
            String s = displayString;
            for (int k = 0; k < s.length(); k++) {    //add lines according to number of Scheduling Information
                if (s.contains("<br>")) {
                    lineBreaksSB.append("<br/>");
                    s = s.substring(s.indexOf("<br>") + 4);
                } else {
                    break;
                }
            }
        }
        return lineBreaksSB.toString();
    }

    private void processSearchResult(SearchResultInfo searchResults, Map<String, ActivityOfferingWrapper> aoMap,
                                     Map<String, RegistrationGroupWrapper> rgMap, Map<String, List<ActivityOfferingWrapper>> storedAOs)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        for (SearchResultRowInfo row : searchResults.getRows()) {

            String aoId = null;
            String rgId = null;
            String rgName = null;
            String rgState = null;
            for (SearchResultCellInfo cell : row.getCells()) {
                if (ActivityOfferingSearchServiceImpl.SearchResultColumns.AO_ID.equals(cell.getKey())) {
                    aoId = cell.getValue();
                } else if (ActivityOfferingSearchServiceImpl.SearchResultColumns.RG_ID.equals(cell.getKey())) {
                    rgId = cell.getValue();
                } else if (ActivityOfferingSearchServiceImpl.SearchResultColumns.RG_NAME.equals(cell.getKey())) {
                    rgName = cell.getValue();
                } else if (ActivityOfferingSearchServiceImpl.SearchResultColumns.RG_STATE.equals(cell.getKey())) {
                    rgState = cell.getValue();
                }
            }
            ActivityOfferingWrapper aoWrapper = aoMap.get(aoId);
            if (aoWrapper == null) {
                continue;
            }

            if (!rgMap.containsKey(rgId)) {

                storedAOs.put(rgId, new ArrayList<ActivityOfferingWrapper>());

                String rgStateKey = getStateService().getState(rgState, ContextUtils.createDefaultContextInfo()).getName();
                rgMap.put(rgId, createRegistrationGroupWrapper(rgId, rgName, rgState, rgStateKey));
            }
            storedAOs.get(rgId).add(aoWrapper);
        }
    }

    private RegistrationGroupWrapper createRegistrationGroupWrapper(String rgId, String rgName, String rgState, String rgStateKey) {

        RegistrationGroupWrapper rgWrapper = new RegistrationGroupWrapper();
        rgWrapper.setStateKey(rgState, rgStateKey);
        rgWrapper.getRgInfo().setName(rgName);
        rgWrapper.getRgInfo().setId(rgId);

        rgWrapper.setAoMaxEnrText("");
        rgWrapper.setAoStateNameText("");
        rgWrapper.setAoActivityCodeText("");
        rgWrapper.setAoTypeNameText("");
        rgWrapper.setStartTimeDisplay("");
        rgWrapper.setEndTimeDisplay("");
        rgWrapper.setDaysDisplayName("");
        rgWrapper.setScheduledState("");
        rgWrapper.setRoomName("");
        rgWrapper.setBuildingName("");
        rgWrapper.setBuildingCode("");
        rgWrapper.setAoInstructorText("");
        return rgWrapper;
    }

    /**
     * After the performance improvements, the clusterMap does not contain set information. So, lets add it here.
     * This method will use the dto to grab a full AOC.
     *
     * @param coId       Course Offering Id
     * @param clusterMap map of AoIds to AO cluster wrappers
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws DoesNotExistException
     * @throws PermissionDeniedException
     * @throws OperationFailedException
     */
    protected void processAosData(String coId, Map<String, ActivityOfferingClusterWrapper> clusterMap)
            throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException {

        List<ActivityOfferingClusterInfo> aoClusters = CourseOfferingManagementUtil.getCourseOfferingServiceFacade().getActivityOfferingClusterByCourseOffering(coId);

        //A fix for KSENROLL-8097: clusterMap was populated in processAoClusterData method.
        //However if one cluster does not have an AO, that cluster sometimes won't be added to clusterMap, which causes NPE error later.
        //The walkaround solution here is to add the missing cluster to clusterMap
        for (ActivityOfferingClusterInfo aoc : aoClusters) {
            if (clusterMap.get(aoc.getId()) != null) {
                clusterMap.get(aoc.getId()).setAoCluster(aoc);
            } else {
                ActivityOfferingClusterWrapper aoClusterWrapper = new ActivityOfferingClusterWrapper();
                aoClusterWrapper.setAoCluster(aoc);
                aoClusterWrapper.setActivityOfferingClusterId(aoc.getId());
                aoClusterWrapper.setClusterNameForDisplay(aoc.getName());
                String formatName = CourseOfferingManagementUtil.getCourseOfferingService().getFormatOffering(aoc.getFormatOfferingId(), ContextUtils.createDefaultContextInfo()).getName();
                aoClusterWrapper.setFormatNameForDisplay(formatName);
                aoClusterWrapper.setFormatOfferingId(aoc.getFormatOfferingId());
                clusterMap.put(aoc.getId(), aoClusterWrapper);
            }
        }

    }

    private List<ActivityOfferingWrapper> processAoClusterData(SearchResultInfo searchResults,
                                                               Map<String, List<ActivityOfferingWrapper>> sch2aoMap,
                                                               Map<String, ActivityOfferingClusterWrapper> clusterMap,
                                                               Map<String, ActivityOfferingWrapper> aoMap,
                                                               Map<String, FormatOfferingInfo> foIds,
                                                               List<String> aoIdsWithoutSch,
                                                               ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException {

        List<ActivityOfferingWrapper> activityOfferingWrappers = new ArrayList<ActivityOfferingWrapper>();
        ActivityOfferingWrapper aoWrapperStored = new ActivityOfferingWrapper();  //storage for sub-term only to compare

        for (SearchResultRowInfo row : searchResults.getRows()) {

            ActivityOfferingWrapper aoWrapper = new ActivityOfferingWrapper();
            String aocPrivateName = null;
            String foId = null;
            String formatId = null;
            String foName = null;
            List<String> scheduleIds = new ArrayList<String>();

            for (SearchResultCellInfo cell : row.getCells()) {

                if (ActivityOfferingSearchServiceImpl.SearchResultColumns.AO_ID.equals(cell.getKey())) {
                    aoWrapper.getAoInfo().setId(cell.getValue());
                } else if (ActivityOfferingSearchServiceImpl.SearchResultColumns.AO_CODE.equals(cell.getKey())) {
                    aoWrapper.setActivityCode(cell.getValue());
                    aoWrapper.getAoInfo().setActivityCode(cell.getValue());
                } else if (ActivityOfferingSearchServiceImpl.SearchResultColumns.AO_MAX_SEATS.equals(cell.getKey())) {
                    aoWrapper.getAoInfo().setMaximumEnrollment(cell.getValue() == null ? null : Integer.parseInt(cell.getValue()));
                } else if (ActivityOfferingSearchServiceImpl.SearchResultColumns.AO_STATE.equals(cell.getKey())) {
                    aoWrapper.getAoInfo().setStateKey(cell.getValue());
                    if (cell.getValue() != null) {
                        StateInfo stateInfo = getStateService().getState(cell.getValue(), contextInfo);
                        aoWrapper.setStateName(stateInfo.getName());
                    }
                } else if (ActivityOfferingSearchServiceImpl.SearchResultColumns.AO_TYPE.equals(cell.getKey())) {
                    aoWrapper.getAoInfo().setTypeKey(cell.getValue());
                    if (cell.getValue() != null) {
                        TypeInfo typeInfo = getTypeService().getType(cell.getValue(), contextInfo);
                        aoWrapper.setTypeKey(cell.getValue());
                        aoWrapper.setTypeName(typeInfo.getName());
                    }
                } else if (ActivityOfferingSearchServiceImpl.SearchResultColumns.SCHEDULE_ID.equals(cell.getKey())) {

                    if (cell.getValue() != null) {
                        scheduleIds.add(cell.getValue());

                    }

                } else if (ActivityOfferingSearchServiceImpl.SearchResultColumns.FO_ID.equals(cell.getKey())) {
                    aoWrapper.getAoInfo().setFormatOfferingId(cell.getValue());
                    foId = cell.getValue();
                } else if (ActivityOfferingSearchServiceImpl.SearchResultColumns.FO_NAME.equals(cell.getKey())) {
                    aoWrapper.setFormatOfferingName(cell.getValue());
                    aoWrapper.getAoInfo().setFormatOfferingName(cell.getValue());
                    foName = cell.getValue();
                } else if (ActivityOfferingSearchServiceImpl.SearchResultColumns.FORMAT_ID.equals(cell.getKey())) {
                    formatId = cell.getValue();
                } else if (ActivityOfferingSearchServiceImpl.SearchResultColumns.AOC_ID.equals(cell.getKey())) {
                    aoWrapper.setAoClusterID(cell.getValue());
                } else if (ActivityOfferingSearchServiceImpl.SearchResultColumns.AOC_NAME.equals(cell.getKey())) {
                    aoWrapper.setAoClusterName(cell.getValue());
                } else if (ActivityOfferingSearchServiceImpl.SearchResultColumns.AOC_PRIVATE_NAME.equals(cell.getKey())) {
                    aocPrivateName = cell.getValue();
                } else if (ActivityOfferingSearchServiceImpl.SearchResultColumns.ATP_ID.equals(cell.getKey())) {
                    aoWrapper.getAoInfo().setTermId(cell.getValue());
                } else if (ActivityOfferingSearchServiceImpl.SearchResultColumns.AO_ISHONORS.equals(cell.getKey())) {
                    aoWrapper.getAoInfo().setIsHonorsOffering(Boolean.valueOf(cell.getValue()));
                }
            }
            aoWrapper.getAoInfo().setScheduleIds(scheduleIds);
            for (String scheduleId : scheduleIds) {
                List<ActivityOfferingWrapper> list = sch2aoMap.get(scheduleId);
                if (list == null) {
                    list = new ArrayList<ActivityOfferingWrapper>();
                    sch2aoMap.put(scheduleId, list);
                }
                list.add(aoWrapper);//Add to schedule map
            }
            if (aoWrapper.getAoClusterID() != null) {
                ActivityOfferingClusterWrapper aoClusterWrapper = clusterMap.get(aoWrapper.getAoClusterID());
                if (aoClusterWrapper == null) {
                    aoClusterWrapper = new ActivityOfferingClusterWrapper();
                    ActivityOfferingClusterInfo activityOfferingClusterInfo = new ActivityOfferingClusterInfo();
                    activityOfferingClusterInfo.setFormatOfferingId(aoWrapper.getAoInfo().getFormatOfferingId());
                    activityOfferingClusterInfo.setId(aoWrapper.getAoClusterID());
                    activityOfferingClusterInfo.setName(aoWrapper.getAoClusterName());
                    activityOfferingClusterInfo.setPrivateName(aocPrivateName);
                    activityOfferingClusterInfo.setTypeKey(CourseOfferingServiceConstants.AOC_ROOT_TYPE_KEY);
                    activityOfferingClusterInfo.setStateKey(CourseOfferingServiceConstants.AOC_ACTIVE_STATE_KEY);
                    aoClusterWrapper.setAoCluster(activityOfferingClusterInfo);
                    aoClusterWrapper.setActivityOfferingClusterId(aoWrapper.getAoClusterID());
                    aoClusterWrapper.setClusterNameForDisplay(aoWrapper.getAoClusterName());
                    aoClusterWrapper.setFormatNameForDisplay(aoWrapper.getAoInfo().getFormatOfferingName());
                    aoClusterWrapper.setFormatOfferingId(aoWrapper.getAoInfo().getFormatOfferingId());
                    clusterMap.put(aoWrapper.getAoClusterID(), aoClusterWrapper);
                }
                if (aoWrapper.getId() != null) {
                    aoClusterWrapper.getAoWrapperList().add(aoWrapper);

                    aoMap.put(aoWrapper.getAoInfo().getId(), aoWrapper);

                    // Check if there is a schedule id, if not add it to the list to get RDLs
                    // Note that since AOs can have multiple Scheduling Information and it is possible to have a scheduleId
                    // in the collection while at the same time having an unprocessed request. However, the POs currently
                    // only want to see requests if there are no schedules.
                    if (aoWrapper.getAoInfo().getScheduleIds() == null || aoWrapper.getAoInfo().getScheduleIds().isEmpty()) {
                        aoIdsWithoutSch.add(aoWrapper.getAoInfo().getId());
                    }

                    //Check for sub-term or term and populate accordingly
                    //If no change of AO.getTermId() > avoid service calls and populate sub-term info as it has been stored in aoWrapperStored
                    if (aoWrapper.getAoInfo().getTermId().equals(aoWrapperStored.getAoInfo().getTermId())) {
                        aoWrapper.setHasSubTerms(aoWrapperStored.getHasSubTerms());
                        aoWrapper.setSubTermId(aoWrapperStored.getSubTermId());
                        aoWrapper.setSubTermName(aoWrapperStored.getSubTermName());
                        aoWrapper.setTerm(aoWrapperStored.getTerm());
                        aoWrapper.setTermName(aoWrapperStored.getTermName());
                        aoWrapper.setTermDisplayString(aoWrapperStored.getTermDisplayString());
                        aoWrapper.setTermStartEndDate(aoWrapperStored.getTermStartEndDate());
                    } else {
                        TermInfo term;
                        TermInfo subTerm;
                        aoWrapper.setHasSubTerms(false);
                        aoWrapper.setSubTermName("None");
                        aoWrapper.setSubTermId("");
                        List<TermInfo> terms = CourseOfferingManagementUtil.getAcademicCalendarService().getContainingTerms(aoWrapper.getAoInfo().getTermId(), contextInfo);
                        if (terms == null || terms.isEmpty()) {
                            term = CourseOfferingManagementUtil.getAcademicCalendarService().getTerm(aoWrapper.getAoInfo().getTermId(), contextInfo);
                            // checking if we can have sub-terms for giving term
                            List<TermInfo> subTerms = CourseOfferingManagementUtil.getAcademicCalendarService().getIncludedTermsInTerm(aoWrapper.getAoInfo().getTermId(), contextInfo);
                            if (!subTerms.isEmpty()) {
                                aoWrapper.setHasSubTerms(true);
                            }
                        } else {
                            subTerm = CourseOfferingManagementUtil.getAcademicCalendarService().getTerm(aoWrapper.getAoInfo().getTermId(), contextInfo);
                            term = terms.get(0);
                            aoWrapper.setHasSubTerms(true);
                            aoWrapper.setSubTermId(subTerm.getId());
                            TypeInfo subTermType = getTypeService().getType(subTerm.getTypeKey(), contextInfo);
                            aoWrapper.setSubTermName(subTermType.getName());
                            aoWrapper.setTermStartEndDate(CourseOfferingManagementUtil.getTermStartEndDate(subTerm.getId(), subTerm));
                        }
                        aoWrapper.setTerm(term);
                        if (term != null) {
                            aoWrapper.setTermName(term.getName());
                        }
                        aoWrapper.setTermDisplayString(CourseOfferingManagementUtil.getTermDisplayString(aoWrapper.getAoInfo().getTermId(), term));
                        aoWrapperStored = aoWrapper; //update for next comparison
                    }
                    // end sub-terms
                    activityOfferingWrappers.add(aoWrapper);
                }
            }

            FormatOfferingInfo fo = foIds.get(foId);
            if (fo == null) {
                fo = new FormatOfferingInfo();
                fo.setId(foId);
                fo.setName(foName);
                fo.setFormatId(formatId);
                foIds.put(foId, fo);
            }

        }

        return activityOfferingWrappers;
    }

    protected EntityDefaultQueryResults getInstructorsInfoFromKim(List<String> principalIds) {
        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(
                PredicateFactory.in("principals.principalId", principalIds.toArray())
        );

        QueryByCriteria criteria = qbcBuilder.build();

        EntityDefaultQueryResults entityResults = CourseOfferingManagementUtil.getIdentityService().findEntityDefaults(criteria);

        return entityResults;
    }

    /**
     * This method will indicate to the user if the cluster canot be generated because the AO Set does not contain
     * enough activities that meet the requirements of the FormatOffering
     *
     * @param aoTypeKeys       list of AO Type Keys
     * @param aoList           List Of Activity Offerings
     * @param aoClusterWrapper AO Cluster
     * @param clusterIndex     Used to tack the warning message onto a particular part of the screen
     * @param contextInfo      context of the call
     * @throws Exception
     */
    protected void _performAOCompletePerClusterValidation(List<String> aoTypeKeys, List<ActivityOfferingInfo> aoList,
                                                          ActivityOfferingClusterWrapper aoClusterWrapper, int clusterIndex, ContextInfo contextInfo) throws Exception {
        Map<String, Boolean> completeAoSet = new HashMap<String, Boolean>(); // using a map to store what's required

        for (String aoType : aoTypeKeys) {
            completeAoSet.put(aoType, false);
        }

        for (ActivityOfferingInfo aoInfo : aoList) {
            completeAoSet.put(aoInfo.getTypeKey(), true); // This is used to determine if the AO Set has all FO types
        }

        StringBuilder aoCompleteWarningMessageSB = new StringBuilder();
        String delim = "";
        for (Map.Entry<String, Boolean> completeAoEntry : completeAoSet.entrySet()) {
            if (!completeAoEntry.getValue()) {
                // type service should be cached so this shouldn't be slow
                aoCompleteWarningMessageSB.append(delim + getTypeService().getType(completeAoEntry.getKey(), contextInfo).getName());
                delim = ", ";
            }
        }
        // not to show warning message for deleted cluster.
        if (!(aoCompleteWarningMessageSB.length() == 0) && !(aoClusterWrapper.getAoCluster().getActivityOfferingSets().isEmpty())) {
            aoClusterWrapper.setRgStatus(RegistrationGroupConstants.RGSTATUS_NO_RG_GENERATED);
            aoClusterWrapper.setRgMessageStyle(ActivityOfferingClusterWrapper.RG_MESSAGE_ALL);
            aoClusterWrapper.setHasAllRegGroups(true);
            String clusterName = aoClusterWrapper.getClusterNameForDisplay();
            String formatName = aoClusterWrapper.getFormatNameForDisplay();
            GlobalVariables.getMessageMap().putWarningForSectionId("activityOfferingsPerCluster_line" + clusterIndex, CourseOfferingConstants.REGISTRATIONGROUP_INCOMPLETE_AOSET, clusterName, formatName, aoCompleteWarningMessageSB.toString());
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
                                                       ActivityOfferingDisplayUI theForm, int clusterIndex, Map<String, List<ScheduleCalcContainer>> ao2sch, Map<String, List<ScheduleRequestCalcContainer>> ao2schReq, Map<String, ActivityOfferingWrapper> aoMap) throws Exception {

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
        for (RegistrationGroupInfo rgInfo : rgInfos) {
            rgInfosCopy.add(rgInfo);
        }

        for (List<String> activityOfferingPermutation : generatedPermutations) {
            for (RegistrationGroupInfo rgInfo : rgInfosCopy) {
                if (_hasGeneratedRegGroup(activityOfferingPermutation, rgInfo)) {
                    rgInfosCopy.remove(rgInfo);
                    foundList.add(activityOfferingPermutation);
                    break;
                }
            }
        }
        if (generatedPermutations.size() != foundList.size()) {
            aoClusterWrapper.setRgStatus(RegistrationGroupConstants.RGSTATUS_SOME_RG_GENERATED);
            aoClusterWrapper.setRgMessageStyle(ActivityOfferingClusterWrapper.RG_MESSAGE_PARTIAL);
            aoClusterWrapper.setHasAllRegGroups(false);
        } else {
            aoClusterWrapper.setRgStatus(RegistrationGroupConstants.RGSTATUS_ALL_RG_GENERATED);
            aoClusterWrapper.setRgMessageStyle(ActivityOfferingClusterWrapper.RG_MESSAGE_ALL);
            aoClusterWrapper.setHasAllRegGroups(true);
            // perform max enrollment validation
            _performMaxEnrollmentValidation(aoMap, aoClusterWrapper, clusterIndex);
            //validate AO time conflict in RG
            _performRGTimeConflictValidation(aoClusterWrapper.getAoCluster(), rgInfos, clusterIndex, ao2sch, ao2schReq);
        }

        _performAOCompletePerClusterValidation(theForm.getFoId2aoTypeMap().get(aoClusterWrapper.getFormatOfferingId()).getActivityOfferingTypeKeys(), aoList, aoClusterWrapper, clusterIndex, ContextUtils.createDefaultContextInfo());

        if (!rgInfosCopy.isEmpty()) {
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

    private boolean _hasGeneratedRegGroup(List<String> activityOfferingPermutation, RegistrationGroupInfo rgInfo) {
        boolean isMatched = true;
        List<String> aoIds = rgInfo.getActivityOfferingIds();
        List<String> aoIdsCopy = new ArrayList<String>(aoIds.size());
        for (String aoId : aoIds) {
            aoIdsCopy.add(aoId);
        }
        List<String> foundList = new ArrayList<String>();
        for (String activityOfferingPermutationItem : activityOfferingPermutation) {
            for (String aoId : aoIdsCopy) {
                if (activityOfferingPermutationItem.equals(aoId)) {
                    aoIdsCopy.remove(aoId);
                    foundList.add(activityOfferingPermutationItem);
                    break;
                }
            }
        }
        if (activityOfferingPermutation.size() != foundList.size() || !aoIdsCopy.isEmpty()) {
            isMatched = false;
        }
        return isMatched;
    }

    /**
     * Checks if all the AOs in a cluster add up to the same max enrollment (e.g. that there are not more seats in labs then lectures)
     *
     * @param aoMap            map of aoIds to ao wrappers
     * @param aoClusterWrapper AO cluster wrapper
     * @param clusterIndex     index of the cluster
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws DoesNotExistException
     * @throws PermissionDeniedException
     * @throws OperationFailedException
     */
    private void _performMaxEnrollmentValidation(Map<String, ActivityOfferingWrapper> aoMap, ActivityOfferingClusterWrapper aoClusterWrapper, int clusterIndex) throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException {
        int previousMaxEnrollments = -1;
        String previousAOSetTypeKey = "";

        for (ActivityOfferingSetInfo aos : aoClusterWrapper.getAoCluster().getActivityOfferingSets()) {
            int aoSetMaxEnrollNumber = 0;

            //Sum the enrollment for each AO in a given set
            for (String aoId : aos.getActivityOfferingIds()) {
                ActivityOfferingWrapper aoWrapper = aoMap.get(aoId);
                ActivityOfferingInfo aoInfo;
                if ((aoWrapper != null) && ((aoInfo = aoWrapper.getAoInfo()) != null) && aoInfo.getMaximumEnrollment() != null && !(aoInfo.getStateKey().equals(LuiServiceConstants.LUI_AO_STATE_CANCELED_KEY))) {
                    aoSetMaxEnrollNumber += aoInfo.getMaximumEnrollment();
                }
            }
            //If this is not the first cluster looked at and the max enrollment differs then set a warning
            if (previousMaxEnrollments >= 0 && previousMaxEnrollments != aoSetMaxEnrollNumber) {
                String biggerAOTypeName, smallerAOTypeName;
                if (previousMaxEnrollments > aoSetMaxEnrollNumber) {
                    biggerAOTypeName = CourseOfferingManagementUtil.getTypeService().getType(previousAOSetTypeKey, ContextUtils.createDefaultContextInfo()).getName();
                    smallerAOTypeName = CourseOfferingManagementUtil.getTypeService().getType(aos.getActivityOfferingType(), ContextUtils.createDefaultContextInfo()).getName();
                } else {
                    biggerAOTypeName = CourseOfferingManagementUtil.getTypeService().getType(aos.getActivityOfferingType(), ContextUtils.createDefaultContextInfo()).getName();
                    smallerAOTypeName = CourseOfferingManagementUtil.getTypeService().getType(previousAOSetTypeKey, ContextUtils.createDefaultContextInfo()).getName();
                }
                GlobalVariables.getMessageMap().putWarningForSectionId("activityOfferingsPerCluster_line" + clusterIndex, RegistrationGroupConstants.MSG_WARNING_MAX_ENROLLMENT, aoClusterWrapper.getClusterNameForDisplay(), biggerAOTypeName, smallerAOTypeName);
                break;
            }

            previousAOSetTypeKey = aos.getActivityOfferingType();
            previousMaxEnrollments = aoSetMaxEnrollNumber;
        }

    }

    private List<Integer> _performRGTimeConflictValidation(ActivityOfferingClusterInfo aoCluster, List<RegistrationGroupInfo> registrationGroupInfos, int clusterIndex, Map<String, List<ScheduleCalcContainer>> ao2sch, Map<String, List<ScheduleRequestCalcContainer>> ao2schReq) throws Exception {
        List<Integer> rgIndexList = new ArrayList<Integer>();
        rgIndexList.clear();

        if (aoCluster != null && registrationGroupInfos != null && !registrationGroupInfos.isEmpty()) {
            int rgIndex = 0;
            for (RegistrationGroupInfo registrationGroupInfo : registrationGroupInfos) {

                List<ValidationResultInfo> validationResultInfoList = new ArrayList<ValidationResultInfo>();
                // Lets build a list of all schedules that need to be compared for this registration group.
                List<TimeSlotInfo> shed2Check = new ArrayList<TimeSlotInfo>();
                for (String aoId : registrationGroupInfo.getActivityOfferingIds()) {
                    if (ao2sch.get(aoId) != null) {
                        for (ScheduleCalcContainer sched : ao2sch.get(aoId)) {
                            shed2Check.add(toTimeSlotInfo(sched));
                        }
                    }
                    if (ao2schReq.get(aoId) != null) {
                        for (ScheduleRequestCalcContainer sched : ao2schReq.get(aoId)) {
                            shed2Check.addAll(sched.getTimeSlots());
                        }
                    }
                }

                for (TimeSlotInfo outerEntry : shed2Check) {
                    for (TimeSlotInfo innerEntry : shed2Check) {
                        if (outerEntry.equals(innerEntry)) {
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

                if (!validationResultInfoList.isEmpty() && validationResultInfoList.get(0).isWarn()) {
                    rgIndexList.add(rgIndex);
                }

                rgIndex++;
            }

            if (!rgIndexList.isEmpty()) {
                GlobalVariables.getMessageMap().putWarningForSectionId("activityOfferingsPerCluster_line" + clusterIndex, RegistrationGroupConstants.MSG_WARNING_AO_TIMECONFLICT, aoCluster.getPrivateName());
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

        info.setStartTime(TimeOfDayHelper.setMillis(((sched.getStart() != null && !"".equals(sched.getStart()))
                ? Long.valueOf(sched.getStart()) : null)));

        info.setEndTime(TimeOfDayHelper.setMillis(((sched.getEnd() != null && !"".equals(sched.getEnd()))
                ? Long.valueOf(sched.getEnd()) : null)));

        info.setDescr(new RichTextInfo());
        info.getDescr().setFormatted(null);
        info.getDescr().setPlain(null);

        info.setMeta(null);

        return info;
    }

    /**
     * This method fetches all the course offerings for a term and course/subject code.
     *
     * @param searchRequest search request
     * @param form          input form
     * @throws Exception
     * @see CourseOfferingManagementSearchImpl Actual CO search happens here
     */
    protected void loadCourseOfferings(SearchRequestInfo searchRequest, CourseOfferingDisplayUI form) throws Exception {

        ContextInfo contextInfo = createContextInfo();

        SearchResultInfo searchResult = CourseOfferingManagementUtil.getSearchService().search(searchRequest, contextInfo);

        if (form instanceof ScheduleOfClassesSearchForm) {
            ((ScheduleOfClassesSearchForm) form).getCoDisplayWrapperList().clear();
        } else {
            ((CourseOfferingManagementForm) form).getCourseOfferingResultList().clear();
        }

        for (SearchResultRowInfo row : searchResult.getRows()) {
            CourseOfferingListSectionWrapper coListWrapper = new CourseOfferingListSectionWrapper();
            if (form instanceof ScheduleOfClassesSearchForm) {
                coListWrapper = new CourseOfferingDisplayWrapper();
                ((ScheduleOfClassesSearchForm) form).getCoDisplayWrapperList().add((CourseOfferingDisplayWrapper) coListWrapper);
            } else {
                ((CourseOfferingManagementForm) form).getCourseOfferingResultList().add(coListWrapper);
            }

            for (SearchResultCellInfo cellInfo : row.getCells()) {

                String value = StringUtils.EMPTY;
                if (cellInfo.getValue() != null) {
                    value = cellInfo.getValue();
                }

                if (CourseOfferingManagementSearchImpl.SearchResultColumns.CODE.equals(cellInfo.getKey())) {
                    coListWrapper.setCourseOfferingCode(value);
                } else if (CourseOfferingManagementSearchImpl.SearchResultColumns.DESC.equals(cellInfo.getKey())) {
                    coListWrapper.setCourseOfferingDesc(value);
                } else if (CourseOfferingManagementSearchImpl.SearchResultColumns.STATE.equals(cellInfo.getKey())) {
                    coListWrapper.setCourseOfferingStateKey(value);
                    coListWrapper.setCourseOfferingStateDisplay(getStateInfo(value).getName());
                } else if (CourseOfferingManagementSearchImpl.SearchResultColumns.CREDIT_OPTION.equals(cellInfo.getKey())) {
                    coListWrapper.setCourseOfferingCreditOptionKey(value);
                } else if (CourseOfferingManagementSearchImpl.SearchResultColumns.GRADING_OPTION.equals(cellInfo.getKey())) {
                    coListWrapper.setCourseOfferingGradingOptionKey(value);
                } else if (CourseOfferingManagementSearchImpl.SearchResultColumns.GRADING_OPTION_NAME.equals(cellInfo.getKey())) {
                    coListWrapper.setCourseOfferingGradingOptionDisplay(cellInfo.getValue());
                } else if (CourseOfferingManagementSearchImpl.SearchResultColumns.CREDIT_OPTION_NAME.equals(cellInfo.getKey())) {
                    coListWrapper.setCourseOfferingCreditOptionDisplay(cellInfo.getValue());
                } else if (CourseOfferingManagementSearchImpl.SearchResultColumns.DEPLOYMENT_ORG_ID.equals(cellInfo.getKey())) {
                    coListWrapper.setAdminOrg(cellInfo.getValue());
                } else if (CourseOfferingManagementSearchImpl.SearchResultColumns.CO_ID.equals(cellInfo.getKey())) {
                    coListWrapper.setCourseOfferingId(value);
                } else if (CourseOfferingManagementSearchImpl.SearchResultColumns.SUBJECT_AREA.equals(cellInfo.getKey())) {
                    coListWrapper.setSubjectArea(value);
                } else if (CourseOfferingManagementSearchImpl.SearchResultColumns.IS_CROSS_LISTED.equals(cellInfo.getKey())) {
                    coListWrapper.setCrossListed(BooleanUtils.toBoolean(value));
                } else if (CourseOfferingManagementSearchImpl.SearchResultColumns.CROSS_LISTED_COURSES.equals(cellInfo.getKey())) {
                    coListWrapper.setAlternateCOCodes(Arrays.asList(StringUtils.split(value, ",")));
                } else if (CourseOfferingManagementSearchImpl.SearchResultColumns.OWNER_CODE.equals(cellInfo.getKey())) {
                    coListWrapper.setOwnerCode(value);
                } else if (CourseOfferingManagementSearchImpl.SearchResultColumns.OWNER_ALIASES.equals(cellInfo.getKey())) {
                    coListWrapper.setOwnerAliases(Arrays.asList(StringUtils.split(value, ",")));
                } else if (CourseOfferingManagementSearchImpl.SearchResultColumns.DESC_FORMATTED.equals(cellInfo.getKey())) {
                    coListWrapper.setCourseOfferingFormatedDesc(value);
                } else if (CourseOfferingManagementSearchImpl.SearchResultColumns.HAS_STUDENT_SELECTABLE_PASSFAIL.equals(cellInfo.getKey())) {
                    coListWrapper.setStudentSelectablePassFail(BooleanUtils.toBoolean(value));
                } else if (CourseOfferingManagementSearchImpl.SearchResultColumns.CAN_AUDIT_COURSE.equals(cellInfo.getKey())) {
                    coListWrapper.setAuditCourse(BooleanUtils.toBoolean(value));
                } else if (CourseOfferingManagementSearchImpl.SearchResultColumns.IS_HONORS_COURSE.equals(cellInfo.getKey())) {
                    coListWrapper.setHonorsCourse(BooleanUtils.toBoolean(value));
                }

            }
        }
    }

    /**
     * This method loads the previous and next course offerings for navigation purpose.
     *
     * @param form input form
     */
    public void loadPreviousAndNextCourseOffering(CourseOfferingManagementForm form) {
        try {
            ContextInfo contextInfo = createContextInfo();

            /**
             * Get all the course offerings for a term and subject area.
             */
            SearchRequestInfo searchRequest = new SearchRequestInfo(CourseOfferingManagementSearchImpl.CO_MANAGEMENT_SEARCH.getKey());
            searchRequest.addParam(CourseOfferingManagementSearchImpl.SearchParameters.SUBJECT_AREA, form.getSubjectCode());
            searchRequest.addParam(CourseOfferingManagementSearchImpl.SearchParameters.ATP_ID, form.getTermInfo().getId());
            searchRequest.addParam(CourseOfferingManagementSearchImpl.SearchParameters.CROSS_LIST_SEARCH_ENABLED, BooleanUtils.toStringTrueFalse(true));

            SearchResultInfo searchResult = CourseOfferingManagementUtil.getSearchService().search(searchRequest, contextInfo);
            List<CourseOfferingWrapper> availableCOs = new ArrayList<CourseOfferingWrapper>();

            for (SearchResultRowInfo row : searchResult.getRows()) {

                String courseOfferingCode = "";
                String courseOfferingId = "";
                String courseOfferingDesc = "";
                boolean isCrossListed = false;
                List<String> alternateCodes = null;
                String ownerCode = "";

                for (SearchResultCellInfo cellInfo : row.getCells()) {

                    String value = StringUtils.EMPTY;
                    if (cellInfo.getValue() != null) {
                        value = cellInfo.getValue();
                    }

                    if (CourseOfferingManagementSearchImpl.SearchResultColumns.CODE.equals(cellInfo.getKey())) {
                        courseOfferingCode = value;
                    } else if (CourseOfferingManagementSearchImpl.SearchResultColumns.CO_ID.equals(cellInfo.getKey())) {
                        courseOfferingId = value;
                    } else if (CourseOfferingManagementSearchImpl.SearchResultColumns.DESC.equals(cellInfo.getKey())) {
                        courseOfferingDesc = value;
                    } else if (CourseOfferingManagementSearchImpl.SearchResultColumns.IS_CROSS_LISTED.equals(cellInfo.getKey())) {
                        isCrossListed = BooleanUtils.toBoolean(value);
                    } else if (CourseOfferingManagementSearchImpl.SearchResultColumns.CROSS_LISTED_COURSES.equals(cellInfo.getKey())) {
                        alternateCodes = Arrays.asList(StringUtils.split(value, ","));
                    } else if (CourseOfferingManagementSearchImpl.SearchResultColumns.OWNER_CODE.equals(cellInfo.getKey())) {
                        ownerCode = value;
                    }

                }

                CourseOfferingWrapper coWrapper = new CourseOfferingWrapper(isCrossListed, courseOfferingCode, courseOfferingDesc, alternateCodes, courseOfferingId);
                coWrapper.setOwnerCode(ownerCode);
                availableCOs.add(coWrapper);
            }

            /**
             * Find the current course offering index and set the previous and next course offerings if exists.
             */
            for (CourseOfferingWrapper coWrapper : availableCOs) {
                if (StringUtils.equals(coWrapper.getCourseOfferingCode(), form.getCurrentCourseOfferingWrapper().getCourseOfferingCode())) {
                    int currentIndex = availableCOs.indexOf(coWrapper);
                    form.setInputCode(coWrapper.getCourseOfferingCode());
                    if (currentIndex > 0) {
                        form.setPreviousCourseOfferingWrapper(availableCOs.get(currentIndex - 1));
                    } else {
                        form.setPreviousCourseOfferingWrapper(null);
                    }
                    if (currentIndex < availableCOs.size() - 1) {
                        form.setNextCourseOfferingWrapper(availableCOs.get(currentIndex + 1));
                    } else {
                        form.setNextCourseOfferingWrapper(null);
                    }
                    break;
                }
            }


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method is a custom method to bulk create AO's via the UI.
     * <p/>
     * It has some code that will automatically create clusters if they don't exist plus extra validation on the
     * types.
     * <p/>
     * It eventually calls the business adapter class to create the AO.
     *
     * @param formatOfferingId
     * @param activityId
     * @param noOfActivityOfferings
     * @param form
     */
    public void createActivityOfferings(String formatOfferingId, String activityId, int noOfActivityOfferings, CourseOfferingManagementForm form) {
        String termcode;
        FormatOfferingInfo formatOfferingInfo;
        TypeInfo activityOfferingType = null;
        CourseInfo course;
        String clusterId = form.getClusterIdForNewAO();
        ContextInfo contextInfo = createContextInfo();
        ActivityOfferingClusterInfo defaultCluster;

        //create default cluster if there is no cluster for the FO yet
        try {
            int clusterCount = CourseOfferingManagementUtil.getCourseOfferingServiceFacade().getAoClusterCountByFoId(formatOfferingId, contextInfo);
            if (clusterCount == 0) {
                defaultCluster = CourseOfferingManagementUtil.getCourseOfferingServiceFacade().createDefaultCluster(formatOfferingId, contextInfo);
                if (defaultCluster != null) {
                    clusterId = defaultCluster.getId();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //the AO clusters associated with the given FO
        CourseOfferingInfo courseOffering = form.getCurrentCourseOfferingWrapper().getCourseOfferingInfo();

        // find the Activity object that matches the activity id selected
        ActivityInfo activity = null;

        // Get the format object for the id selected
        try {
            formatOfferingInfo = CourseOfferingManagementUtil.getCourseOfferingService().getFormatOffering(formatOfferingId, contextInfo);
            course = CourseOfferingManagementUtil.getCourseService().getCourse(courseOffering.getCourseId(), contextInfo);
            for (FormatInfo f : course.getFormats()) {
                if (f.getId().equals(formatOfferingInfo.getFormatId())) {
                    for (ActivityInfo info : f.getActivities()) {
                        if (StringUtils.equals(activityId, info.getId())) {
                            activity = info;
                        }
                    }
                    break;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
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
            AtpInfo termAtp = CourseOfferingManagementUtil.getAtpService().getAtp(courseOffering.getTermId(), contextInfo);
            termcode = termAtp.getCode();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        BulkStatusInfo examsGenerated = null;
        BulkStatusInfo examPeriodStatus = null;
        //create and persist AO
        for (int i = 0; i < noOfActivityOfferings; i++) {
            ActivityOfferingInfo aoInfo = new ActivityOfferingInfo();
            aoInfo.setActivityId(activityId);
            aoInfo.setFormatOfferingId(formatOfferingId);
            aoInfo.setTypeKey(activityOfferingType.getKey());
            aoInfo.setCourseOfferingId(courseOffering.getId());
            aoInfo.setStateKey(LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY);
            aoInfo.setTermId(courseOffering.getTermId());
            aoInfo.setTermCode(termcode);
            aoInfo.setFormatOfferingName(formatOfferingInfo.getName());
            aoInfo.setCourseOfferingTitle(courseOffering.getCourseOfferingTitle());
            aoInfo.setCourseOfferingCode(courseOffering.getCourseOfferingCode());


            try {
                ActivityOfferingInfo activityOfferingInfo;
                CourseWaitListInfo theWaitListInfo;

                // This is the "business" type call that will create the AO and link the appropiate objects.
                // This will also create + add any needed Waitlists
                ActivityOfferingResult aoResult = CourseOfferingManagementUtil.getCourseOfferingServiceFacade().createActivityOffering(aoInfo, clusterId, contextInfo);
                activityOfferingInfo = aoResult.getCreatedActivityOffering();
                theWaitListInfo = aoResult.getWaitListInfo();
                if (examsGenerated == null) {//the exam period either exists or not, first result is all we need
                    examsGenerated = aoResult.getExamOfferingsGenerated();
                }
                if (examPeriodStatus == null) {
                    examPeriodStatus = aoResult.getExamPeriodStatus();
                }

                ActivityOfferingWrapper wrapper = new ActivityOfferingWrapper(activityOfferingInfo);
                StateInfo state = getStateService().getState(wrapper.getAoInfo().getStateKey(), contextInfo);
                wrapper.setStateName(state.getName());
                TypeInfo typeInfo = getTypeInfo(wrapper.getAoInfo().getTypeKey());
                wrapper.setTypeName(typeInfo.getName());
                wrapper.setCourseWaitListInfo(theWaitListInfo);

                form.getActivityWrapperList().add(wrapper);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        CourseOfferingManagementToolbarUtil.processAoToolbarForUser(form.getActivityWrapperList(), form);
        if (noOfActivityOfferings == 1) {
            //determine which growl message to display
            if (!examPeriodStatus.getIsSuccess()) {
                KSUifUtils.addGrowlMessageIcon(GrowlIcon.INFORMATION, CourseOfferingConstants.ACTIVITYOFFERING_TOOLBAR_ADD_1_SUCCESS_WITH_MISSING_EXAMPERIOD);
            } else if (!examsGenerated.getIsSuccess()) {
                KSUifUtils.addGrowlMessageIcon(GrowlIcon.INFORMATION, CourseOfferingConstants.ACTIVITYOFFERING_TOOLBAR_ADD_1_SUCCESS);
            } else {
                KSUifUtils.addGrowlMessageIcon(GrowlIcon.INFORMATION, CourseOfferingConstants.ACTIVITYOFFERING_TOOLBAR_ADD_1_SUCCESS_WITH_EXAMOFFERING_GENERATED);
            }
        } else {
            //determine which growl message to display
            if (!examPeriodStatus.getIsSuccess()) {
                KSUifUtils.addGrowlMessageIcon(GrowlIcon.INFORMATION, CourseOfferingConstants.ACTIVITYOFFERING_TOOLBAR_ADD_N_SUCCESS_WITH_MISSING_EXAMPERIOD);
            } else if (!examsGenerated.getIsSuccess()) {
                KSUifUtils.addGrowlMessageIcon(GrowlIcon.INFORMATION, CourseOfferingConstants.ACTIVITYOFFERING_TOOLBAR_ADD_N_SUCCESS);
            } else {
                KSUifUtils.addGrowlMessageIcon(GrowlIcon.INFORMATION, CourseOfferingConstants.ACTIVITYOFFERING_TOOLBAR_ADD_N_SUCCESS_WITH_EXAMOFFERING_GENERATED);
            }
        }
    }

    public void loadActivityOfferingsByCourseOffering(CourseOfferingInfo theCourseOfferingInfo, CourseOfferingManagementForm form) throws Exception {
        String courseOfferingId = theCourseOfferingInfo.getId();
        List<ActivityOfferingInfo> activityOfferingInfoList;
        List<ActivityOfferingWrapper> activityOfferingWrapperList;

        try {
            activityOfferingInfoList = CourseOfferingManagementUtil.getCourseOfferingService().getActivityOfferingsByCourseOffering(courseOfferingId, createContextInfo());
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

    public List<ActivityOfferingWrapper> getActivityOfferingsByCourseOfferingId(String courseOfferingId, CourseOfferingManagementForm form) throws Exception {
        List<ActivityOfferingInfo> activityOfferingInfoList;
        List<ActivityOfferingWrapper> activityOfferingWrapperList;

        try {
            activityOfferingInfoList = CourseOfferingManagementUtil.getCourseOfferingService().getActivityOfferingsByCourseOffering(courseOfferingId, createContextInfo());
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

    public void approveCourseOfferings(CourseOfferingManagementForm form) throws Exception {
        List<CourseOfferingListSectionWrapper> coList = form.getCourseOfferingResultList();
        ContextInfo contextInfo = createContextInfo();
        int checked = 0;
        int enabled = 0;
        for (CourseOfferingListSectionWrapper co : coList) {
            if (co.getIsChecked()) {
                checked++;
                if (co.isEnableApproveButton()) {
                    enabled++;
                    List<ActivityOfferingWrapper> aos = getActivityOfferingsByCourseOfferingId(co.getCourseOfferingId(), form);
                    if (aos != null && !aos.isEmpty()) {
                        CourseOfferingManagementToolbarUtil.processAoToolbarForUser(aos, form);
                        for (ActivityOfferingWrapper ao : aos) {
                            if (ao.isEnableApproveButton()) {
                                CourseOfferingManagementUtil.getCourseOfferingService().changeActivityOfferingState(ao.getAoInfo().getId(), LuiServiceConstants.LUI_AO_STATE_APPROVED_KEY, contextInfo);
                            }
                        }
                    }
                }
            }
        }

        if (checked > enabled) {
            KSUifUtils.addGrowlMessageIcon(GrowlIcon.WARNING, CourseOfferingConstants.COURSEOFFERING_TOOLBAR_APPROVED);
        } else {
            if (enabled == 1) {
                KSUifUtils.addGrowlMessageIcon(GrowlIcon.SUCCESS, CourseOfferingConstants.COURSEOFFERING_TOOLBAR_APPROVED_1_SUCCESS);
            } else {
                KSUifUtils.addGrowlMessageIcon(GrowlIcon.SUCCESS, CourseOfferingConstants.COURSEOFFERING_TOOLBAR_APPROVED_N_SUCCESS);
            }
        }
    }

    public void deleteCourseOfferings(CourseOfferingManagementForm form) throws Exception {
        List<CourseOfferingListSectionWrapper> coList = form.getCourseOfferingResultList();
        int totalCosToDelete = 0;
        int totalColocatedCos = 0;
        int totalColocatedAos = 0;
        boolean iscolocated;
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
            if (co.getIsChecked()) {
                totalCosToDelete++;
                iscolocated = false;

                List<ActivityOfferingDisplayInfo> aoDisplayInfoList = CourseOfferingManagementUtil.getCourseOfferingService().getActivityOfferingDisplaysForCourseOffering(co.getCourseOfferingId(), contextInfo);
                List<ActivityOfferingInfo> aoInfoList = CourseOfferingManagementUtil.getCourseOfferingService().getActivityOfferingsByCourseOffering(co.getCourseOfferingId(), contextInfo);

                co.setCoHasAoToDelete(false);

                co.setColocated(false);

                if (aoDisplayInfoList != null && !aoDisplayInfoList.isEmpty()) {
                    co.setCoHasAoToDelete(true);
                    for (ActivityOfferingDisplayInfo aoDisplayInfo : aoDisplayInfoList) {
                        ActivityOfferingDisplayWrapper aoDisplayWrapper = new ActivityOfferingDisplayWrapper();
                        aoDisplayWrapper.setAoDisplayInfo(aoDisplayInfo);


                        // term stuff
                        List<TermInfo> terms = CourseOfferingManagementUtil.getAcademicCalendarService().getContainingTerms( aoDisplayInfo.getTermId(), contextInfo );
                        if( terms == null || terms.isEmpty() ) {    // has no sub-terms
                            aoDisplayWrapper.setSubTermName( "None" );
                            aoDisplayWrapper.setTermStartEndDate( "" );
                        }
                        else {      // has sub-terms
                            TermInfo subTerm = CourseOfferingManagementUtil.getAcademicCalendarService().getTerm( aoDisplayInfo.getTermId(), contextInfo );
                            TypeInfo subTermType = getTypeService().getType(subTerm.getTypeKey(), contextInfo);
                            aoDisplayWrapper.setSubTermName( subTermType.getName() );
                            aoDisplayWrapper.setTermStartEndDate( CourseOfferingManagementUtil.getTermStartEndDate( subTerm.getId(), subTerm ) );
                        }


                        // Adding Information (icons)
                        String information = "";
                        if (aoDisplayInfo.getIsHonorsOffering() != null && aoDisplayInfo.getIsHonorsOffering()) {
                            information = "<img src=" + ScheduleOfClassesConstants.SOC_RESULT_PAGE_HONORS_COURSE_IMG + " title=\"" + ScheduleOfClassesConstants.SOC_RESULT_PAGE_HELP_HONORS_ACTIVITY + "\"> ";
                        }
                        aoDisplayWrapper.setInformation(information);

                        if (aoDisplayInfo.getScheduleDisplay() != null && !aoDisplayInfo.getScheduleDisplay().getScheduleComponentDisplays().isEmpty()) {
                            //TODO handle TBA state
                            List<? extends ScheduleComponentDisplay> scheduleComponentDisplays = aoDisplayInfo.getScheduleDisplay().getScheduleComponentDisplays();
                            for (ScheduleComponentDisplay scheduleComponentDisplay : scheduleComponentDisplays) {
                                if (scheduleComponentDisplay.getBuilding() != null) {
                                    aoDisplayWrapper.setBuildingName(scheduleComponentDisplay.getBuilding().getBuildingCode(), true);
                                    aoDisplayWrapper.setBuildingCode(scheduleComponentDisplay.getBuilding().getBuildingCode(), true);
                                }
                                if (scheduleComponentDisplay.getRoom() != null) {
                                    aoDisplayWrapper.setRoomName(scheduleComponentDisplay.getRoom().getRoomCode(), true);
                                }
                                if (!scheduleComponentDisplay.getTimeSlots().isEmpty()) {
                                    TimeSlot timeSlot = KSCollectionUtils.getRequiredZeroElement(scheduleComponentDisplay.getTimeSlots());
                                    if (timeSlot.getStartTime() != null && (timeSlot.getStartTime().getHour() != null)) {
                                        aoDisplayWrapper.setStartTimeDisplay(timeOfDayToTime(timeSlot.getStartTime()), true);
                                    }
                                    if (timeSlot.getEndTime() != null && (timeSlot.getEndTime().getHour() != null)) {
                                        aoDisplayWrapper.setEndTimeDisplay(timeOfDayToTime(timeSlot.getEndTime()), true);
                                    }
                                    aoDisplayWrapper.setDaysDisplayName(getDays(timeSlot.getWeekdays()), true);
                                }
                            }
                        }
                        // if ao is colocated AO add colocated info
                        if (isColocatedAo(aoDisplayInfo.getActivityOfferingCode(), aoInfoList)) {
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

                if (iscolocated) {
                    totalColocatedCos++;
                } else {
                    // verify whether this CO is joint-defined or not
                    String jointDefinedCodes = getJointDefinedInfo(co);
                    if (!jointDefinedCodes.isEmpty()) {
                        co.setJointDefinedCoCode(jointDefinedCodes);
                        co.setJointDefined(true);
                        totalJointDefinedCosToDelete++;
                    }
                }
                qualifiedToDeleteList.add(co);
            }
        }
        form.setNumOfCrossListedCosToDelete(totalCrossListedCosToDelete);
        form.setNumOfJointDefinedCosToDelete(totalJointDefinedCosToDelete);
        if (totalColocatedCos == totalCosToDelete) {
            form.setColocatedCoOnly(true);
            form.setNumOfColocatedCosToDelete(totalColocatedCos);
        }
        if (totalJointDefinedCosToDelete >= 1) {
            form.setJointDefinedCo(true);
        }
        form.setNumOfColocatedAosToDelete(totalColocatedAos);
        form.setTotalAOsToBeDeleted(totalAos);
    }

    public void approveActivityOfferings(CourseOfferingManagementForm form) throws Exception {
        List<ActivityOfferingWrapper> aoList = form.getActivityWrapperList();
        ContextInfo contextInfo = createContextInfo();
        int checked = 0;
        int enabled = 0;
        for (ActivityOfferingWrapper ao : aoList) {
            if ((!"0".equals(form.getSelectedTabId())) && ao.getIsChecked() ||
                    ("0".equals(form.getSelectedTabId())) && ao.getIsCheckedByCluster()) {
                checked++;
                if (ao.isEnableApproveButton()) {
                    enabled++;
                    CourseOfferingManagementUtil.getCourseOfferingService().changeActivityOfferingState(ao.getAoInfo().getId(), LuiServiceConstants.LUI_AO_STATE_APPROVED_KEY, contextInfo);
                }
            }
        }

        if (checked > enabled) {
            KSUifUtils.addGrowlMessageIcon(GrowlIcon.WARNING, CourseOfferingConstants.ACTIVITYOFFERING_TOOLBAR_APPROVED);
        } else {
            if (enabled == 1) {
                KSUifUtils.addGrowlMessageIcon(GrowlIcon.SUCCESS, CourseOfferingConstants.ACTIVITYOFFERING_TOOLBAR_APPROVED_1_SUCCESS);
            } else {
                KSUifUtils.addGrowlMessageIcon(GrowlIcon.SUCCESS, CourseOfferingConstants.ACTIVITYOFFERING_TOOLBAR_APPROVED_N_SUCCESS);
            }
        }
    }

    public void draftActivityOfferings(CourseOfferingManagementForm form) throws Exception {
        List<ActivityOfferingWrapper> aoList = form.getActivityWrapperList();
        ContextInfo contextInfo = createContextInfo();
        int checked = 0;
        int enabled = 0;
        for (ActivityOfferingWrapper ao : aoList) {
            if ((!"0".equals(form.getSelectedTabId())) && ao.getIsChecked() ||
                    ("0".equals(form.getSelectedTabId())) && ao.getIsCheckedByCluster()) {
                checked++;
                if (ao.isEnableDraftButton()) {
                    enabled++;
                    CourseOfferingManagementUtil.getCourseOfferingService().changeActivityOfferingState(ao.getAoInfo().getId(), LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY, contextInfo);
                }
            }
        }

        if (checked > enabled) {
            KSUifUtils.addGrowlMessageIcon(GrowlIcon.WARNING, CourseOfferingConstants.ACTIVITYOFFERING_TOOLBAR_DRAFT);
        } else {
            if (enabled == 1) {
                KSUifUtils.addGrowlMessageIcon(GrowlIcon.INFORMATION, CourseOfferingConstants.ACTIVITYOFFERING_TOOLBAR_DRAFT_1_SUCCESS);
            } else {
                KSUifUtils.addGrowlMessageIcon(GrowlIcon.INFORMATION, CourseOfferingConstants.ACTIVITYOFFERING_TOOLBAR_DRAFT_N_SUCCESS);
            }
        }

    }

    /**
     * Notes by Bonnie: The following implementation tries to support "Approve for Scheduling" in two cases historically:
     * 1)when some COs are checked and approve for scheduling link or button is clicked
     * vs.
     * 2)when a user simply click "Approve Subject Code for Scheduling" link with/without selecting any CO
     * case 1) has been implemented by  approveCourseOfferings method -- invoked by Approve button in toolbar
     * case 2) is the only use case when markCourseOfferingsForScheduling is invoked. Therefore the parameter
     * checkedOnly and related checking for each CO seem not needed anymore...
     * TODO: code cleanup??
     * <p/>
     * Examines a List of CourseOffering wrappers and changes the state of each "checked" AO (meaning the
     * CO was selected on the UI) from "Draft" to "Approved". If the AO has a state other than "Draft" the AO is ignored.
     * Also, changes the state of the CourseOffering if appropriate.
     *
     * @param coWrappers, viewId, socStateKey provides list of CourseOfferings, viewId, socState.
     * @param checkedOnly True if the CO wrapper isChecked() flag should be respected.
     */
    public void markCourseOfferingsForScheduling(List<CourseOfferingListSectionWrapper> coWrappers,
                                                 String viewId, String socStateKey, boolean checkedOnly) throws Exception {
        boolean hasAOWarning = false, hasStateChangedAO = false, hasOrgWarning = false;
        ContextInfo contextInfo = createContextInfo();
        for (CourseOfferingListSectionWrapper coWrapper : coWrappers) {
            if (coWrapper.getIsChecked() || !checkedOnly) {
                // Checking if the person is authorized to approve
                Map<String, String> permissionDetails = new HashMap<String, String>();
                Map<String, String> roleQualifications = new HashMap<String, String>();
                CourseOfferingInfo coInfo = CourseOfferingManagementUtil.getCourseOfferingService().getCourseOffering(coWrapper.getCourseOfferingId(), contextInfo);
                List<String> orgIds = coInfo.getUnitsDeploymentOrgIds();
                StringBuilder orgIDs = new StringBuilder();
                if (orgIds != null && !orgIds.isEmpty()) {
                    for (String orgId : orgIds) {
                        orgIDs.append(orgId + ",");
                    }
                }
                boolean canApproveAOs = true;
                if (orgIDs.length() > 0) {
                    String principalId = GlobalVariables.getUserSession().getPerson().getPrincipalId();

                    roleQualifications.put("offeringAdminOrgId", orgIDs.substring(0, orgIDs.length() - 1));

                    permissionDetails.put(KimConstants.AttributeConstants.VIEW_ID, viewId);
                    permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT, "approveSubj");

                    String socState = socStateKey == null ? null : socStateKey.substring(socStateKey.lastIndexOf('.') + 1);
                    permissionDetails.put("socState", socState);

                    canApproveAOs = CourseOfferingManagementUtil.getPermissionService().isAuthorizedByTemplate(principalId, "KS-ENR", KimConstants.PermissionTemplateNames.PERFORM_ACTION, permissionDetails, roleQualifications);
                }

                if (!canApproveAOs) {  // if can't approve AOs for all COs (because they are in a different org)
                    if (!hasOrgWarning) hasOrgWarning = true;
                    continue;
                } else {

                    List<ActivityOfferingInfo> activityOfferingInfos = CourseOfferingManagementUtil.getCourseOfferingService().getActivityOfferingsByCourseOffering(coWrapper.getCourseOfferingId(), contextInfo);
                    if (activityOfferingInfos.size() == 0) {
                        if (!hasAOWarning) hasAOWarning = true;
                        continue;
                    }
                    // Iterate through the AOs and state change Draft -> Approved.
                    for (ActivityOfferingInfo activityOfferingInfo : activityOfferingInfos) {
                        boolean isAOStateDraft = StringUtils.equals(activityOfferingInfo.getStateKey(), LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY);
                        if (isAOStateDraft) {
                            StatusInfo statusInfo = CourseOfferingManagementUtil.getCourseOfferingService().changeActivityOfferingState(activityOfferingInfo.getId(), LuiServiceConstants.LUI_AO_STATE_APPROVED_KEY, contextInfo);
                            if (!statusInfo.getIsSuccess()) {
                                GlobalVariables.getMessageMap().putError("manageCourseOfferingsPage", CourseOfferingConstants.COURSE_OFFERING_STATE_CHANGE_ERROR, coWrapper.getCourseOfferingCode(), statusInfo.getMessage());
                            }
                            //  Flag if any AOs can be state changed. This affects the error message whi.
                            if (statusInfo.getIsSuccess()) {
                                hasStateChangedAO = true;
                            }
                        } else {
                            //  Flag if any AOs are not in a valid state for approval.
                            if (!hasAOWarning) hasAOWarning = true;
                        }
                    }
                }
            }
        }
        //  Set feedback messages.
        if (!hasStateChangedAO) {
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

    private void setSocStateKeys(CourseOfferingManagementForm form, SocInfo soc) throws Exception {
        if (soc != null) {
            form.setSocStateKey(soc.getStateKey());
            form.setSocSchedulingStateKey(soc.getSchedulingStateKey());

            //TODO: Set SOC State - temporary display, to be removed after testing is finished

            if (StringUtils.isNotBlank(soc.getStateKey())) {
                String socState = getStateService().getState(soc.getStateKey(), createContextInfo()).getName();
                socState = (socState.substring(0, 1)).toUpperCase() + socState.substring(1, socState.length());
                form.setSocState(socState);
            }

            return;
        }
    }

    private boolean isColocatedAo(String aoCode, List<ActivityOfferingInfo> aoList) {
        for (ActivityOfferingInfo ao : aoList) {
            if (StringUtils.equals(aoCode, ao.getActivityCode())) {
                if (ao.getIsColocated()) {
                    return true;
                }
            }
        }
        return false;
    }

    private ActivityOfferingInfo getAoInfo(String aoCode, List<ActivityOfferingInfo> aoList) {
        for (ActivityOfferingInfo ao : aoList) {
            if (StringUtils.equals(aoCode, ao.getActivityCode())) {
                if (ao.getIsColocated()) {
                    return ao;
                }
            }
        }
        return null;
    }

    private String getJointDefinedInfo(CourseOfferingListSectionWrapper co) {
        if (co == null) return null;

        List<CourseInfo> coInfoList = CourseOfferingViewHelperUtil.getMatchingCoursesFromClu(co.getCourseOfferingCode());
        StringBuilder jointDefinedCodes = new StringBuilder();
        for (CourseInfo coInfo : coInfoList) {
            List<CourseJointInfo> jointList = coInfo.getJoints();
            if (!jointList.isEmpty() && jointList.size() >= 1) {
                for (CourseJointInfo jointInfo : jointList) {
                    jointDefinedCodes.append(jointInfo.getSubjectArea());
                    jointDefinedCodes.append(jointInfo.getCourseNumberSuffix());
                    jointDefinedCodes.append(" ");
                }

            }
        }

        return jointDefinedCodes.toString();
    }

    public void setupRuleIndicator(List<ActivityOfferingWrapper> wrappers) {

        //Get the list of ao ids.
        List<String> aoIds = new ArrayList<String>();
        for (ActivityOfferingWrapper aoWrapper : wrappers) {
            if (aoWrapper.getAoInfo().getId() != null) {
                aoIds.add(aoWrapper.getAoInfo().getId());
            }
        }

        //Retrieve all reference object bindings for list of ao ids.
        List<ReferenceObjectBinding> refs = CourseOfferingManagementUtil.getRuleManagementService().
                findReferenceObjectBindingsByReferenceObjectIds(CourseOfferingServiceConstants.REF_OBJECT_URI_ACTIVITY_OFFERING, aoIds);
        for (ActivityOfferingWrapper aoWrapper : wrappers) {
            aoWrapper.setHasRule(false);
            for(ReferenceObjectBinding ref : refs){
                if(ref.getReferenceObjectId().equals(aoWrapper.getAoInfo().getId())){
                    aoWrapper.setHasRule(true);
                    break;
                }
            }
        }
    }

    public void loadExamOfferingRelations(CourseOfferingManagementForm theForm) throws Exception {

        List<ExamOfferingRelationInfo> eoRelations = CourseOfferingManagementUtil.getExamOfferingServiceFacade().getExamOfferingRelationsByCourseOffering(
                theForm.getCourseOfferingId(), ContextUtils.createDefaultContextInfo());
        theForm.setEoRelations(eoRelations);
    }

    public void loadExamOfferings(CourseOfferingManagementForm theForm) throws Exception {

        Set<String> examOfferingIds = new HashSet<String>();
        Map<String, ExamOfferingRelationInfo> eoToRln = new HashMap<String, ExamOfferingRelationInfo>();
        for (ExamOfferingRelationInfo examOfferingRelationInfo : theForm.getEoRelations()) {
            examOfferingIds.add(examOfferingRelationInfo.getExamOfferingId());
            eoToRln.put(examOfferingRelationInfo.getExamOfferingId(), examOfferingRelationInfo);
        }

        //Retrieve a list of all the unique examofferings.
        List<ExamOfferingInfo> examOfferingInfos = CourseOfferingManagementUtil.getExamOfferingService().getExamOfferingsByIds(
                new ArrayList<String>(examOfferingIds), ContextUtils.createDefaultContextInfo());

        for (ExamOfferingInfo examOfferingInfo : examOfferingInfos) {
            ExamOfferingWrapper examOfferingWrapper = createWrapperFromExamOffering(examOfferingInfo);

            if (isDriverPerAO(examOfferingInfo)) {

                ExamOfferingRelationInfo eor = eoToRln.get(examOfferingInfo.getId());
                examOfferingWrapper.setTypeName(this.getActivityDriver(examOfferingInfo));
                setExamOfferingPerAO(theForm, examOfferingWrapper, eor);
            } else {
                if (ExamOfferingServiceConstants.EXAM_OFFERING_CANCELED_STATE_KEY.equals(examOfferingInfo.getStateKey())) {
                    theForm.getExamOfferingCancelledList().add(examOfferingWrapper);
                } else {
                    theForm.getExamOfferingWrapperList().add(examOfferingWrapper);
                }
            }
        }

        //Sort EO Clusters in sync with AO Clusters
        List<ExamOfferingClusterWrapper> clusterWrapperList = theForm.getEoClusterResultList();
        if (clusterWrapperList.size() > 1) {
            Collections.sort(clusterWrapperList, new Comparator<ExamOfferingClusterWrapper>() {
                @Override
                public int compare(ExamOfferingClusterWrapper o1, ExamOfferingClusterWrapper o2) {
                    int formatComparison = o1.getFormatNameForDisplay().compareToIgnoreCase(o2.getFormatNameForDisplay());
                    int nameComparison = o1.getClusterNameForDisplay().compareToIgnoreCase(o2.getClusterNameForDisplay());
                    if (formatComparison == 0) {
                        return nameComparison;
                    } else {
                        return formatComparison;
                    }
                }
            });
        }
    }

    private boolean isDriverPerAO(ExamOfferingInfo eo) {
        for (AttributeInfo attribute : eo.getAttributes()) {
            if (attribute.getKey().equals(ExamOfferingServiceConstants.FINAL_EXAM_DRIVER_ATTR) &&
                    attribute.getValue().equals("PER_AO")) {
                return true;
            }
        }
        return false;
    }

    private String getActivityDriver(ExamOfferingInfo eo) throws PermissionDeniedException, MissingParameterException,
            InvalidParameterException, OperationFailedException, DoesNotExistException {
        for (AttributeInfo attribute : eo.getAttributes()) {
            if (attribute.getKey().equals(ExamOfferingServiceConstants.FINAL_EXAM_ACTIVITY_DRIVER_ATTR)) {
                TypeInfo type = CourseOfferingManagementUtil.getTypeService().getType(attribute.getValue(), ContextUtils.createDefaultContextInfo());
                return type.getName();
            }
        }
        return StringUtils.EMPTY;
    }

    private void setExamOfferingPerAO(CourseOfferingManagementForm theForm, ExamOfferingWrapper examOfferingWrapper,
                                      ExamOfferingRelationInfo eoRelation)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException,
            DoesNotExistException {

        for (ActivityOfferingClusterWrapper aoClusterWrapper : theForm.getClusterResultList()) {
            for (ActivityOfferingWrapper wrapper : aoClusterWrapper.getAoWrapperList()) {
                if (eoRelation.getActivityOfferingIds().contains(wrapper.getId())) {

                    ExamOfferingClusterWrapper eoClusterWrapper = null;
                    if (ExamOfferingServiceConstants.EXAM_OFFERING_CANCELED_STATE_KEY.equals(examOfferingWrapper.getEoInfo().getStateKey())) {
                        eoClusterWrapper = getExamOfferingClusterWrapper(aoClusterWrapper, theForm.getEoCancelClusterList());
                    } else {
                        eoClusterWrapper = getExamOfferingClusterWrapper(aoClusterWrapper, theForm.getEoClusterResultList());
                    }

                    examOfferingWrapper.setAoInfo(wrapper.getAoInfo());
                    examOfferingWrapper.setActivityCode(wrapper.getActivityCode());
                    eoClusterWrapper.getEoWrapperList().add(examOfferingWrapper);

                    return;
                }
            }

        }
    }

    private ExamOfferingClusterWrapper getExamOfferingClusterWrapper(ActivityOfferingClusterWrapper aoClusterWrapper, List<ExamOfferingClusterWrapper> wrapperList) {

        for (ExamOfferingClusterWrapper eoClusterWrap : wrapperList) {
            if (eoClusterWrap.getActivityOfferingClusterId().equals(aoClusterWrapper.getActivityOfferingClusterId())) {
                return eoClusterWrap;
            }
        }

        ExamOfferingClusterWrapper eoClusterWrapper = new ExamOfferingClusterWrapper();
        eoClusterWrapper.setActivityOfferingClusterId(aoClusterWrapper.getActivityOfferingClusterId());
        eoClusterWrapper.setFormatNameForDisplay(aoClusterWrapper.getFormatNameForDisplay());
        eoClusterWrapper.setClusterNameForDisplay(aoClusterWrapper.getClusterNameForDisplay());
        wrapperList.add(eoClusterWrapper);

        return eoClusterWrapper;
    }


    private ExamOfferingWrapper createWrapperFromExamOffering(ExamOfferingInfo examOfferingInfo) throws Exception {
        ExamOfferingWrapper eoWrapper = new ExamOfferingWrapper();
        eoWrapper.setEoInfo(examOfferingInfo);

        StateInfo state = getStateService().getState(examOfferingInfo.getStateKey(),
                ContextUtils.createDefaultContextInfo());
        eoWrapper.setStateName(state.getName());

        List<ScheduleRequestInfo> scheduleRequestInfos = CourseOfferingManagementUtil.getSchedulingService().getScheduleRequestsByRefObject(
                ExamOfferingServiceConstants.REF_OBJECT_URI_EXAM_OFFERING, examOfferingInfo.getId(), ContextUtils.createDefaultContextInfo());
        ExamPeriodInfo epInfo = CourseOfferingManagementUtil.getAcademicCalendarService().getExamPeriod(examOfferingInfo.getExamPeriodId() , ContextUtils.createDefaultContextInfo());

        for (ScheduleRequestInfo scheduleRequestInfo : scheduleRequestInfos) {
            for (ScheduleRequestComponentInfo componentInfo : scheduleRequestInfo.getScheduleRequestComponents()) {

                String timeSlotId = KSCollectionUtils.getOptionalZeroElement(componentInfo.getTimeSlotIds());
                TimeSlotInfo timeSlot = CourseOfferingManagementUtil.getSchedulingService().getTimeSlot(timeSlotId, ContextUtils.createDefaultContextInfo());
                if (timeSlot != null) {

                    TimeOfDayInfo startTime = timeSlot.getStartTime();
                    TimeOfDayInfo endTime = timeSlot.getEndTime();
                    List<Integer> days = timeSlot.getWeekdays();

                    if (startTime != null && startTime.getHour() != null) {
                        eoWrapper.setStartTimeDisplay(TimeOfDayHelper.makeFormattedTimeForAOSchedules(startTime));
                    }

                    if (endTime != null && endTime.getHour() != null) {
                        eoWrapper.setEndTimeDisplay(TimeOfDayHelper.makeFormattedTimeForAOSchedules(endTime));
                    }

                    if (days != null && days.size() > 0) {
                       eoWrapper.setDaysDisplayName(examPeriodDaysDisplay(days, epInfo.getStartDate(), epInfo.getEndDate(),
                               Boolean.parseBoolean(epInfo.getAttributeValue(AcademicCalendarServiceConstants.EXAM_PERIOD_EXCLUDE_SATURDAY_ATTR)), Boolean.parseBoolean(epInfo.getAttributeValue(AcademicCalendarServiceConstants.EXAM_PERIOD_EXCLUDE_SUNDAY_ATTR))));
                    }
                }

                String roomId = KSCollectionUtils.getOptionalZeroElement(componentInfo.getRoomIds());
                if (StringUtils.isNotBlank(roomId)) {
                    RoomInfo roomInfo = CourseOfferingManagementUtil.getRoomService().getRoom(roomId, ContextUtils.createDefaultContextInfo());
                    BuildingInfo buildingInfo = CourseOfferingManagementUtil.getRoomService().getBuilding(roomInfo.getBuildingId(),
                            ContextUtils.createDefaultContextInfo());
                    eoWrapper.setBuildingName(buildingInfo.getName());
                    eoWrapper.setRoomName(roomInfo.getRoomCode());
                }
            }
        }

        return eoWrapper;
    }

    private String timeOfDayToTime(TimeOfDayInfo tod) {
        return TimeOfDayHelper.makeFormattedTimeForAOSchedules(tod);
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
        if (intList == null) {
            return sb.toString();
        }

        for (Integer d : intList) {
            sb.append(convertIntoDaysDisplay(d));
        }

        return sb.toString();
    }

    public static String examPeriodDaysDisplay(List<Integer> weekdaysList, Date startTime,
                                                       Date endTime, boolean excludeSundays, boolean excludeSaturday) {
        StringBuilder result = new StringBuilder();
        List<Date> dates = new ArrayList<Date>();
        dates.addAll(getExamPeriodDates(startTime, endTime, excludeSundays, excludeSaturday ));
        for(Integer weekday : weekdaysList) {
          result.append("Day "+weekday);
          result.append(" - ");
          result.append(DateFormatters.EXAM_OFFERING_VIEW_EXAM_OFFERING_DATE_FORMATTER.format(dates.get(weekday - 1)));
        }

        return result.toString();
    }

    public static List<Date> getExamPeriodDates(Date fechInitial, Date fechFinal, boolean excludeSundays, boolean excludeSaturday)
    {
        List<Date> dates = new ArrayList<Date>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechInitial);

        while (calendar.getTime().before(fechFinal))
        {
            Date resultado = calendar.getTime();
            Calendar cal = Calendar.getInstance();
            cal.setTime(resultado);
            int weekday = cal.get(Calendar.DAY_OF_WEEK);
            if((weekday == Calendar.SATURDAY && !excludeSaturday) ||(weekday == Calendar.SUNDAY && !excludeSundays) ) {
               dates.add(resultado);
            }
            else if((weekday != Calendar.SATURDAY)&&(weekday != Calendar.SUNDAY))  {
               dates.add(resultado);
            }

            calendar.add(Calendar.DATE, 1);
        }
        calendar.setTime(fechFinal);
        Date finalDate = calendar.getTime();
        dates.add(finalDate);
        return dates;
    }
}
