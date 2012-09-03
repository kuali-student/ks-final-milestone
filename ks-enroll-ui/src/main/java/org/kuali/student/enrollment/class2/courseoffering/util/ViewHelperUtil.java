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
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.identity.PersonService;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.kim.impl.KIMPropertyConstants;
import org.kuali.student.enrollment.common.util.ContextBuilder;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.OfferingInstructorInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.dto.ContextInfo;
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
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.lum.course.dto.ActivityInfo;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.dto.FormatInfo;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.kuali.student.r2.lum.course.service.assembler.CourseAssemblerConstants;
import org.kuali.student.r2.lum.lrc.dto.ResultValueInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;
import org.kuali.student.r2.lum.lrc.service.LRCService;
import org.kuali.student.r2.lum.util.constants.LrcServiceConstants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
public class ViewHelperUtil {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(ViewHelperUtil.class);
    public static List<Person> getInstructorByPersonId(String personId){
        Map<String, String> searchCriteria = new HashMap<String, String>();
        searchCriteria.put(KIMPropertyConstants.Person.ENTITY_ID, personId);
        List<Person> lstPerson = getPersonService().findPeople(searchCriteria);
        return lstPerson;
    }

    public static PersonService getPersonService() {
        return KimApiServiceLocator.getPersonService();
    }

    public static List<KeyValue> collectActivityTypeKeyValues(CourseInfo course, TypeService typeService, ContextInfo contextInfo) {
        List<KeyValue> results = new ArrayList<KeyValue>();

        Set<String> activityTypes = new HashSet<String>();
        for(FormatInfo format : course.getFormats()) {
            for (ActivityInfo activity : format.getActivities()) {
                // if we haven't added a value for this activity type yet
                if(activityTypes.add(activity.getTypeKey())) {
                    try {
                        TypeInfo type = typeService.getType(activity.getTypeKey(), contextInfo);
                        results.add(new ConcreteKeyValue(type.getKey(), type.getName()));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }

        return results;
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
        String creditOptionId = coInfo.getCreditOptionId();
        String creditCount = "";
        if (creditOptionId != null) { //Lookup persisted values
            ResultValuesGroupInfo resultValuesGroupInfo = getLrcService().getResultValuesGroup(creditOptionId, ContextBuilder.loadContextInfo());
            String typeKey = resultValuesGroupInfo.getTypeKey();
            //Get the actual values
            List<ResultValueInfo> resultValueInfos = getLrcService().getResultValuesByKeys(resultValuesGroupInfo.getResultValueKeys(), ContextBuilder.loadContextInfo());
            if (typeKey.equals(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_FIXED)) {                                 //fixed
                if (!resultValueInfos.isEmpty()) {
                    creditCount = trimTrailing0(resultValueInfos.get(0).getValue());
                }
            } else if (typeKey.equals(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_RANGE)) {                          //range
                creditCount = trimTrailing0(resultValuesGroupInfo.getResultValueRange().getMinValue()) + " - " +
                        trimTrailing0(resultValuesGroupInfo.getResultValueRange().getMaxValue());
            } else if (typeKey.equals(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_MULTIPLE)) {                       //range
                if (!resultValueInfos.isEmpty()) {
                    List<Float> creditValuesF = new ArrayList();
                    for (ResultValueInfo resultValueInfo : resultValueInfos ) {  //convert String to Float for sorting
                        creditValuesF.add(Float.valueOf(resultValueInfo.getValue()));
                    }
                    Collections.sort(creditValuesF);
                    for (Float creditF : creditValuesF ){
                        creditCount = creditCount + ", " + trimTrailing0(String.valueOf(creditF));
                    }
                    creditCount =  creditCount.substring(2);  //trim leading ", "
                }
            }
        } else { //Lookup original course values
            if (courseInfo == null) {
                courseInfo = (CourseInfo) getCourseService().getCourse(coInfo.getCourseId(), ContextUtils.getContextInfo());
            }
            String creditOpt = courseInfo.getCreditOptions().get(0).getType();
            if (creditOpt.equalsIgnoreCase(CourseAssemblerConstants.COURSE_RESULT_COMP_TYPE_CREDIT_FIXED) ){              //fixed
                creditCount = trimTrailing0(getLrcService().getResultValue(courseInfo.getCreditOptions().get(0).getResultValueKeys().get(0), ContextUtils.getContextInfo()).getValue());
            } else if (creditOpt.equalsIgnoreCase(CourseAssemblerConstants.COURSE_RESULT_COMP_TYPE_CREDIT_VARIABLE) ){    //range
                //minCreditValue - maxCreditValue
                creditCount = trimTrailing0(courseInfo.getCreditOptions().get(0).getAttributeValue(LrcServiceConstants.R1_DYN_ATTR_CREDIT_OPTION_MIN_CREDITS))
                        +" - "+trimTrailing0(courseInfo.getCreditOptions().get(0).getAttributeValue(LrcServiceConstants.R1_DYN_ATTR_CREDIT_OPTION_MAX_CREDITS));
            } else if (creditOpt.equalsIgnoreCase(CourseAssemblerConstants.COURSE_RESULT_COMP_TYPE_CREDIT_MULTIPLE) ){    //multiple
                List<ResultValueInfo> creditValuesRVI = getLrcService().getResultValuesByKeys(courseInfo.getCreditOptions().get(0).getResultValueKeys(), ContextUtils.getContextInfo());
                List<Float> creditValuesF = new ArrayList();
                for (ResultValueInfo creditRVI : creditValuesRVI ) {  //convert String to Float for sorting
                    creditValuesF.add(Float.valueOf(creditRVI.getValue()));
                }
                Collections.sort(creditValuesF);
                for (Float creditF : creditValuesF ){
                    creditCount = creditCount + ", " + trimTrailing0(String.valueOf(creditF));
                }
                creditCount =  creditCount.substring(2);  //trim leading ", "
            } else {
                //no credit option
                LOG.info("Credit is missing for subject course " + coInfo.getCourseCode());
                return "N/A";
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

        int draftAOCount;
        int draftFOCount = 0;
        String oldFoState, newFoState;
        for (FormatOfferingInfo fo : formatOfferings) {
            draftAOCount = 0;
            oldFoState = fo.getStateKey();
            newFoState = null;
            List<ActivityOfferingInfo> activityOfferings = coService.getActivityOfferingsByFormatOffering(fo.getId(), context);
            for (ActivityOfferingInfo ao : activityOfferings) {
                if (StringUtils.equals(LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY, ao.getStateKey())) {
                    draftAOCount++;
                }
            }

            // if ALL the AOs within this FO are in a draft state (or if there are no AOs), and the current state of the FO is Planned, update the FO state to Draft
            if ((activityOfferings.size() == 0 || draftAOCount == activityOfferings.size()) && StringUtils.equals(LuiServiceConstants.LUI_FO_STATE_PLANNED_KEY, fo.getStateKey())) {
                newFoState =  LuiServiceConstants.LUI_FO_STATE_DRAFT_KEY;
            }
            // otherwise if any AOs are in a non-draft state, and the FO is in a draft state, update the FO to Planned
            else if (draftAOCount < activityOfferings.size() && StringUtils.equals(LuiServiceConstants.LUI_FO_STATE_DRAFT_KEY, fo.getStateKey())) {
                newFoState = LuiServiceConstants.LUI_FO_STATE_PLANNED_KEY;
            }

            if (newFoState != null && !StringUtils.equals(oldFoState, newFoState)) {
                fo.setStateKey(newFoState);
                coService.updateFormatOffering(fo.getId(), fo, context);
            }

            if (StringUtils.equals(fo.getStateKey(), LuiServiceConstants.LUI_FO_STATE_DRAFT_KEY)) {
                draftFOCount++;
            }
        }

        String newCoState = null;
        // if ALL the FOs within the CO are in a draft state (or there are no FOs), and the current state of the CO is Planned, update the CO state to Draft
        if ((draftFOCount == formatOfferings.size() || formatOfferings.size() == 0) && StringUtils.equals(LuiServiceConstants.LUI_CO_STATE_PLANNED_KEY, coInfo.getStateKey())) {
            newCoState = LuiServiceConstants.LUI_CO_STATE_DRAFT_KEY;
        }
        // otherwise if any FOs are in a non-draft state, and the CO is in a draft state, update the CO to Planned
        else if(draftFOCount < formatOfferings.size() && StringUtils.equals(LuiServiceConstants.LUI_CO_STATE_DRAFT_KEY, coInfo.getStateKey())) {
            newCoState = LuiServiceConstants.LUI_CO_STATE_PLANNED_KEY;
        }

        if (newCoState != null) {
            coInfo.setStateKey(newCoState);
            coService.updateCourseOffering(coInfo.getId(), coInfo, context);
        }
    }
}
