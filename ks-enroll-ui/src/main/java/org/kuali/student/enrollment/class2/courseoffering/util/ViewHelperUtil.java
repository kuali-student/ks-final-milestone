/**
 * Copyright 2012 The Kuali Foundation Licensed under the
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
package org.kuali.student.enrollment.class2.courseoffering.util;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.identity.PersonService;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.kim.impl.KIMPropertyConstants;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingListSectionWrapper;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingCrossListingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.OfferingInstructorInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.search.dto.SearchParamInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.kuali.student.r2.lum.lrc.service.LRCService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class //TODO ... This calss is specific to CourseOffering and should be renamed appropriately
 *
 * @author Kuali Student Team
 */
public class ViewHelperUtil {

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(ViewHelperUtil.class);

    public static List<Person> getInstructorByPersonId(String personId){
        Map<String, String> searchCriteria = new HashMap<String, String>();
        searchCriteria.put(KIMPropertyConstants.Person.PRINCIPAL_ID, personId);
        List<Person> lstPerson = getPersonService().findPeople(searchCriteria);
        return lstPerson;
    }

    public static PersonService getPersonService() {
        return KimApiServiceLocator.getPersonService();
    }

    public static String trimTrailing0(String creditValue){
        if (creditValue.indexOf(".0") > 0) {
            return creditValue.substring(0, creditValue.length( )- 2);
        } else {
            return creditValue;
        }
    }

    public static LRCService getLrcService() {
        return CourseOfferingResourceLoader.loadLrcService();
    }

    public static CourseService getCourseService() {
        return CourseOfferingResourceLoader.loadCourseService();
    }

    public static OfferingInstructorInfo findDisplayInstructor(List<OfferingInstructorInfo> instructors) {
        OfferingInstructorInfo result = null;

        if(instructors != null && !instructors.isEmpty()) {

            // Build the display name for the Instructor
            Collection<OfferingInstructorInfo> highestInstEffortInstructors = new ArrayList<OfferingInstructorInfo>();
            float highestInstEffortComparison = 0f;

            for (OfferingInstructorInfo instructor : instructors) {
                if(instructor.getPercentageEffort() != null){
                    // if this instructor has a higher percent effort than any previous instructors,
                    // clear the list we are keeping track of and set the new comparison number to this instructor's percentage effort
                    if(instructor.getPercentageEffort() > highestInstEffortComparison) {
                        highestInstEffortInstructors.clear();
                        highestInstEffortComparison = instructor.getPercentageEffort();
                        highestInstEffortInstructors.add(instructor);
                    }
                    // if this instructor's percent effort is tied with the comparison number,
                    // add this instructor to the list of highest effort instructors
                    else if (instructor.getPercentageEffort() == highestInstEffortComparison) {
                        highestInstEffortInstructors.add(instructor);
                    }
                }
            }

            if(highestInstEffortInstructors.size() == 1) {
                result = highestInstEffortInstructors.iterator().next();
            }
            else {
                List<String> names = new ArrayList<String>(highestInstEffortInstructors.size());
                Map<String, OfferingInstructorInfo> nameMap = new HashMap<String, OfferingInstructorInfo>(highestInstEffortInstructors.size());
                for(OfferingInstructorInfo oiInfo : highestInstEffortInstructors) {
                    names.add(oiInfo.getPersonName());
                    nameMap.put(oiInfo.getPersonName(), oiInfo);
                }

                Collections.sort(names);

                result = nameMap.get(names.get(0));
            }
        }

        return result;
    }

    public static   CourseOfferingListSectionWrapper convertCourseOffering2ListSectionWrapper(CourseOfferingInfo coInfo){
        CourseOfferingListSectionWrapper coWrapper = new CourseOfferingListSectionWrapper();
        coWrapper.setSubjectArea(coInfo.getSubjectArea());
        coWrapper.setCourseOfferingCode(coInfo.getCourseOfferingCode());
        coWrapper.setCourseOfferingCreditOptionKey(coInfo.getCreditOptionId());
        coWrapper.setCourseOfferingGradingOptionKey(coInfo.getGradingOptionId());
        coWrapper.setCourseOfferingStateKey(coInfo.getStateKey());
        coWrapper.setCourseOfferingDesc(coInfo.getCourseOfferingTitle());
        coWrapper.setCourseOfferingId(coInfo.getId());
        return coWrapper;
    }

    public static void updateCourseOfferingStateFromActivityOfferingStateChange(CourseOfferingInfo coInfo, ContextInfo context) throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException, DataValidationErrorException, VersionMismatchException, ReadOnlyException {
       updateCourseOfferingStateFromActivityOfferingStateChange(convertCourseOffering2ListSectionWrapper(coInfo),context);
    }

    /**
     * Evaluates whether to update the state of a Course Offering (and possibly its Format Offerings) based on
     * the state of its Activity Offerings
     *
     * This is a utility method that combines logic for updating related objects when the state of one or more
     * Activity Offerings is changed.
     *
     */
    public static void updateCourseOfferingStateFromActivityOfferingStateChange(CourseOfferingListSectionWrapper coInfo, ContextInfo context) throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException, DataValidationErrorException, VersionMismatchException, ReadOnlyException {

        CourseOfferingService coService = CourseOfferingResourceLoader.loadCourseOfferingService();

        List<FormatOfferingInfo> formatOfferings = coService.getFormatOfferingsByCourseOffering(coInfo.getCourseOfferingId(), context);

        String oldFoState, newFoState;
        // Verify each FO, CO state with AO state consistence

        for (FormatOfferingInfo fo : formatOfferings) {
            oldFoState = fo.getStateKey();
            List<ActivityOfferingInfo> activityOfferings = coService.getActivityOfferingsByFormatOffering(fo.getId(), context);
            newFoState = getNewFoState(activityOfferings);

            if (newFoState != null && !StringUtils.equals(oldFoState, newFoState)) {
                fo.setStateKey(newFoState);
                StatusInfo statusInfo = coService.changeFormatOfferingState(fo.getId(), newFoState, context);
                if (!statusInfo.getIsSuccess()){
                     throw new RuntimeException(statusInfo.getMessage());
                }
            }
        }
    }

    // if all of the AO states are Draft or Approved, the FO that owns the AO's cannot be Offered.  
    // If no FO is Offered, then the CO which owns the FO's cannot be Offered.  
    // It is possible for a CO with multiple FO's to have an Offered FO and one or more FO's in a non-Offered state;
    // in that case, since at least one of the FO's is Offered, the CO which owns it is Offered.

    // If all of the AO's owned by an FO are deleted, that FO's state will change to Draft.  
    // As above, if all of the FO's owned by a CO are Draft, the CO's state reverts to Draft;
    // however, if the CO owns multiple FO's and only one of the FO's reverts to Draft, the CO state doesn't change if any of the other FO's is not in Draft state.
    public static String getNewFoState(List<ActivityOfferingInfo> activityOfferings) {
        boolean draftState= false;
        boolean plannedState= false;
        boolean offeredState= false;
        boolean cancelledState= false;

        if(activityOfferings == null || activityOfferings.size() == 0) {
            return LuiServiceConstants.LUI_FO_STATE_DRAFT_KEY;
        }

        for (ActivityOfferingInfo ao : activityOfferings) {
            if (StringUtils.equals(LuiServiceConstants.LUI_AO_STATE_APPROVED_KEY, ao.getStateKey()) ||
                    StringUtils.equals(LuiServiceConstants.LUI_AO_STATE_SUBMITTED_KEY, ao.getStateKey()) ) {
                plannedState = true;
            }  else if (StringUtils.equals(LuiServiceConstants.LUI_AO_STATE_OFFERED_KEY, ao.getStateKey())) {
                offeredState = true;
            }  else if (StringUtils.equals(LuiServiceConstants.LUI_AO_STATE_CANCELED_KEY, ao.getStateKey())) {
                cancelledState = true;
            }  else if (StringUtils.equals(LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY, ao.getStateKey())) {
                draftState = true;
            }
        }

        // if ALL the AOs within this FO are in a draft state (or if there are no AOs), and the current state of the FO is Planned, update the FO state to Draft
        if (offeredState) {
            return LuiServiceConstants.LUI_FO_STATE_OFFERED_KEY;
        }  else if(plannedState) {
            return LuiServiceConstants.LUI_FO_STATE_PLANNED_KEY;
        }  else if (draftState) {
            return LuiServiceConstants.LUI_FO_STATE_DRAFT_KEY;
        }

        // no offered, planned, and draft state
        if(cancelledState)  {
            return LuiServiceConstants.LUI_FO_STATE_CANCELED_KEY;
        }
        // If all AOs are suspended
        return null;
    }

    public static String getNewCoState(List<FormatOfferingInfo>  formatOfferings) {
        boolean draftState= false;
        boolean plannedState= false;
        boolean offeredState= false;
        boolean cancelledState= false;

        if(formatOfferings == null || formatOfferings.size() == 0) {
            return LuiServiceConstants.LUI_CO_STATE_DRAFT_KEY;
        }

        for (FormatOfferingInfo fo : formatOfferings) {
             if(StringUtils.equals(LuiServiceConstants.LUI_FO_STATE_DRAFT_KEY, fo.getStateKey())) {
                 draftState = true;
             } else if(StringUtils.equals(LuiServiceConstants.LUI_FO_STATE_PLANNED_KEY, fo.getStateKey())) {
                 plannedState = true;
             } else if(StringUtils.equals(LuiServiceConstants.LUI_FO_STATE_OFFERED_KEY, fo.getStateKey())) {
                 offeredState = true;
             } else if(StringUtils.equals(LuiServiceConstants.LUI_FO_STATE_CANCELED_KEY, fo.getStateKey())) {
                 cancelledState = true;
             }
        }

        if (offeredState) {
            return LuiServiceConstants.LUI_CO_STATE_OFFERED_KEY;
        }  else if(plannedState) {
            return LuiServiceConstants.LUI_CO_STATE_PLANNED_KEY;
        }  else if (draftState) {
            return LuiServiceConstants.LUI_CO_STATE_DRAFT_KEY;
        }

        // no offered, planned, and draft state
        if(cancelledState)  {
            return LuiServiceConstants.LUI_CO_STATE_CANCELED_KEY;
        }
        // Something wrong return null
        return null;
    }

    public static String createTheCrossListedCos(CourseOfferingInfo coToShow){
        if (coToShow != null && coToShow.getCrossListings() != null && coToShow.getCrossListings().size() > 0) {
            // Always include an option for Course
            StringBuffer crossListedCodes = new StringBuffer();

            for (CourseOfferingCrossListingInfo courseInfo : coToShow.getCrossListings()) {
                crossListedCodes.append(courseInfo.getCode());
                crossListedCodes.append(" ");
            }
            return crossListedCodes.toString();
        }
        return null;
    }


    public static String createColocatedDisplayData(ActivityOfferingInfo ao, ContextInfo context) throws InvalidParameterException, MissingParameterException, PermissionDeniedException,
            OperationFailedException, DoesNotExistException {

        StringBuffer buffer = new StringBuffer();
        buffer.append(" ");
        CourseOfferingService coService = CourseOfferingResourceLoader.loadCourseOfferingService();
        /* TODOSSR
        List<ColocatedOfferingSetInfo> colos = coService.getColocatedOfferingSetsByActivityOffering(ao.getId(),
                context);
        for(ColocatedOfferingSetInfo colo : colos) {
            List<ActivityOfferingInfo> aoList = coService.getActivityOfferingsByIds(colo.getActivityOfferingIds(), context);
            for(ActivityOfferingInfo aoInfo : aoList) {
                buffer.append(aoInfo.getCourseOfferingCode() + " " + aoInfo.getActivityCode() + " ");
            }
        }*/

        return buffer.toString();
    }

    /**
     * This method loads the wrapper details for the joint courses
     *
     * @param courseName CourseOfferingCreateWrapper
     * @throws Exception throws one of the services exceptions
     */
    public static List<CourseInfo> getMatchingCoursesFromClu(String courseName) {

        CourseInfo returnCourseInfo;
        String courseId;
        List<SearchParamInfo> searchParams = new ArrayList<SearchParamInfo>();
        List<CourseInfo> courseInfoList = new ArrayList<CourseInfo>();
        CluService cluService = CourseOfferingResourceLoader.loadCluService();

        SearchParamInfo qpv1 = new SearchParamInfo();
        qpv1.setKey("lu.queryParam.startsWith.cluCode");
        qpv1.getValues().add(courseName.toUpperCase());
        searchParams.add(qpv1);

        SearchParamInfo qpv2 = new SearchParamInfo();
        qpv2.setKey("lu.queryParam.cluState");
        qpv2.setValues(Arrays.asList("Active"));
        searchParams.add(qpv2);

        SearchRequestInfo searchRequest = new SearchRequestInfo();
        searchRequest.setParams(searchParams);
        searchRequest.setSearchKey("lu.search.cluByCodeAndState");

        try {
            SearchResultInfo searchResult = cluService.search(searchRequest, ContextUtils.getContextInfo());
            if (searchResult.getRows().size() > 0) {
                for (SearchResultRowInfo row : searchResult.getRows()) {
                    List<SearchResultCellInfo> srCells = row.getCells();
                    if (srCells != null && srCells.size() > 0) {
                        for (SearchResultCellInfo cell : srCells) {
                            if ("lu.resultColumn.cluId".equals(cell.getKey())) {
                                courseId = cell.getValue();
                                returnCourseInfo = getCourseService().getCourse(courseId, ContextUtils.getContextInfo());
                                courseInfoList.add(returnCourseInfo);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return courseInfoList;
    }
}
