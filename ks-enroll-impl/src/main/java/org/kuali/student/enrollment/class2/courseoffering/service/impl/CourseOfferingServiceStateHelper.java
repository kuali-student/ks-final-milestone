/**
 * Copyright 2011 The Kuali Foundation Licensed under the
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
 */

package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.enrollment.courseofferingset.service.CourseOfferingSetService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;

import java.util.List;

/**
 * This class contains utility code to handle specific state transitions for COs, FOs, and AOs
 *
 * @author andrewlubbers
 */
public class CourseOfferingServiceStateHelper {

    /**
     * This method handles the CO,FO, and AO state changes that may occur when revising the schedule for an individual Activity Offering
     *
     * @param activityOfferingInfo the Activity Offering to inspect
     * @param coService reference to the Course Offering Service
     * @param socService reference to the Course Offering Set Service
     * @param context reference to the context information for service calls
     *
     * @return a reference to the updated Activity Offering
     */
    public static ActivityOfferingInfo updateScheduledActivityOffering(ActivityOfferingInfo activityOfferingInfo, CourseOfferingService coService, CourseOfferingSetService socService, ContextInfo context) throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException, DataValidationErrorException, ReadOnlyException, VersionMismatchException {

        // Keep track of the state before any changes, to avoid extra processing if the AO state does not change
        String oldState = activityOfferingInfo.getStateKey();

        // Find the term-level SOC for this activity offering and find out its state
        List<String> socIds = socService.getSocIdsByTerm(activityOfferingInfo.getTermId(), context);

        // should be only one, if none or more than one is found, throw an exception
        if (socIds == null || socIds.size() != 1) {
            throw new OperationFailedException("Unexpected results from socService.getSocIdsByTerm, expecting exactly one soc id, received: " + socIds);
        }

        SocInfo socInfo = socService.getSoc(socIds.get(0), context);

        // If the SOC is in Final Edits state, and the Scheduled Activity Offering is in Draft state, set the Activity Offering state to Approved
        if (socInfo.getStateKey().equals(CourseOfferingSetServiceConstants.FINALEDITS_SOC_STATE_KEY)) {
            if (LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY.equals(activityOfferingInfo.getStateKey())) {
                activityOfferingInfo.setStateKey(LuiServiceConstants.LUI_AO_STATE_APPROVED_KEY);
            }
        }
        // If the SOC is in Final Edits state, and the Scheduled Activity Offering is in Draft state, AND the Activity Offering is Scheduled, then set the Activity Offering State to Offered
        else if (socInfo.getStateKey().equals(CourseOfferingSetServiceConstants.PUBLISHED_SOC_STATE_KEY)) {
            if (LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY.equals(activityOfferingInfo.getStateKey())) {
                if(LuiServiceConstants.LUI_AO_SCHEDULING_STATE_SCHEDULED_KEY.equals(activityOfferingInfo.getSchedulingStateKey())) {
                    activityOfferingInfo.setStateKey(LuiServiceConstants.LUI_AO_STATE_OFFERED_KEY);
                }
            }
        }

        if (oldState.equals(activityOfferingInfo.getStateKey())) {
            return activityOfferingInfo;
        }
        else {
            coService.updateActivityOffering(activityOfferingInfo.getId(), activityOfferingInfo, context);

            FormatOfferingInfo fo = coService.getFormatOffering(activityOfferingInfo.getFormatOfferingId(), context);
            CourseOfferingInfo co = coService.getCourseOffering(fo.getCourseOfferingId(), context);

            updateCourseOfferingStateFromActivityOfferingStateChange(co, coService, context);

            return coService.getActivityOffering(activityOfferingInfo.getId(), context);
        }
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
    public static void updateCourseOfferingStateFromActivityOfferingStateChange(CourseOfferingInfo coInfo, CourseOfferingService coService, ContextInfo context) throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException, DataValidationErrorException, VersionMismatchException, ReadOnlyException {

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
