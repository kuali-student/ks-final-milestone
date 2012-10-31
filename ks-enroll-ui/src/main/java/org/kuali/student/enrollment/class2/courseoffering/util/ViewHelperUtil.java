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
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.OfferingInstructorInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.kuali.student.r2.lum.lrc.dto.ResultValueInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;
import org.kuali.student.r2.lum.lrc.service.LRCService;
import org.kuali.student.r2.lum.util.constants.LrcServiceConstants;

import java.util.*;

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


    //get credit count from persisted COInfo or from CourseInfo
    public static String getCreditCount(CourseOfferingInfo coInfo, CourseInfo courseInfo) throws Exception{

        String creditCount="";

        ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();

        ResultValuesGroupInfo resultValuesGroupInfo;

        //Lookup persisted values (if the CO has a Credit set use that, otherwise look at the RVG of Course/Clu
        if (coInfo.getCreditOptionId() != null) {
            resultValuesGroupInfo = getLrcService().getResultValuesGroup(coInfo.getCreditOptionId(), contextInfo);
        }else{
            if (courseInfo == null) {
                //Lookup the course if none was passed in
                courseInfo = getCourseService().getCourse(coInfo.getCourseId(), contextInfo);
            }
            resultValuesGroupInfo = courseInfo.getCreditOptions().get(0);
        }
        if(resultValuesGroupInfo!=null){
            String typeKey = resultValuesGroupInfo.getTypeKey();
            if (typeKey.equals(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_FIXED)) {
                //Get the actual values with a service call
                List<ResultValueInfo> resultValueInfos = getLrcService().getResultValuesByKeys(resultValuesGroupInfo.getResultValueKeys(), contextInfo);
                creditCount = trimTrailing0(resultValueInfos.get(0).getValue());
            } else if (typeKey.equals(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_RANGE)) {                          //range
                //Use the min/max values from the RVG
                creditCount = trimTrailing0(resultValuesGroupInfo.getResultValueRange().getMinValue()) + " - " +
                        trimTrailing0(resultValuesGroupInfo.getResultValueRange().getMaxValue());
            } else if (typeKey.equals(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_MULTIPLE)) {
                //Get the actual values with a service call
                List<ResultValueInfo> resultValueInfos = getLrcService().getResultValuesByKeys(resultValuesGroupInfo.getResultValueKeys(), contextInfo);
                if (!resultValueInfos.isEmpty()) {
                    //Convert to floats and sort
                    List<Float> creditValuesF = new ArrayList<Float>();
                    for (ResultValueInfo resultValueInfo : resultValueInfos ) {  //convert String to Float for sorting
                        creditValuesF.add(Float.valueOf(resultValueInfo.getValue()));
                    }
                    Collections.sort(creditValuesF); //Do the sort

                    //Convert back to strings and concatenate to one field
                    //FindBugs - it is fine as is
                    for (Float creditF : creditValuesF ){
                        creditCount = creditCount + ", " + trimTrailing0(String.valueOf(creditF));
                    }
                    if(creditCount.length() >=  2)  {
                        creditCount =  creditCount.substring(2);  //trim leading ", "
                    }
                }
            } else {
                //no credit option
                LOG.info("Credit is missing for subject course " + coInfo.getCourseCode());
                creditCount = "N/A";
            }
        }
        return creditCount;
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

    /**
     * Evaluates whether to update the state of a Course Offering (and possibly its Format Offerings) based on
     * the state of its Activity Offerings
     *
     * This is a utility method that combines logic for updating related objects when the state of one or more
     * Activity Offerings is changed.
     *
     * @param coInfo the Course Offering to evaluate
     */
    public static void updateCourseOfferingStateFromActivityOfferingStateChange(CourseOfferingInfo coInfo, ContextInfo context) throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException, DataValidationErrorException, VersionMismatchException, ReadOnlyException {

        CourseOfferingService coService = CourseOfferingResourceLoader.loadCourseOfferingService();

        List<FormatOfferingInfo> formatOfferings = coService.getFormatOfferingsByCourseOffering(coInfo.getId(), context);

        String oldFoState, newFoState;
        // Verify each FO, CO state with AO state consistence

        for (FormatOfferingInfo fo : formatOfferings) {
            oldFoState = fo.getStateKey();
            List<ActivityOfferingInfo> activityOfferings = coService.getActivityOfferingsByFormatOffering(fo.getId(), context);
            newFoState = getNewFoState(activityOfferings);

            if (newFoState != null && !StringUtils.equals(oldFoState, newFoState)) {
                fo.setStateKey(newFoState);
                coService.updateFormatOffering(fo.getId(), fo, context);
            }
        }

        String oldCoState = coInfo.getStateKey();
        String newCoState = getNewCoState(formatOfferings);
        if (newCoState != null && !StringUtils.equals(oldCoState, newCoState)) {
            coInfo.setStateKey(newCoState);
            coService.updateCourseOffering(coInfo.getId(), coInfo, context);
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
}
