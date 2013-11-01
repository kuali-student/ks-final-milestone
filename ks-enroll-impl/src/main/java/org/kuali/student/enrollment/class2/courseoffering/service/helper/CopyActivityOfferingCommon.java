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
 * Created by Charles on 9/9/13
 */
package org.kuali.student.enrollment.class2.courseoffering.service.helper;

import org.apache.log4j.Logger;
import org.kuali.student.enrollment.class2.courseoffering.service.transformer.ActivityOfferingTransformer;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.OfferingInstructorInfo;
import org.kuali.student.enrollment.courseoffering.dto.SeatPoolDefinitionInfo;
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
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.room.service.RoomService;
import org.kuali.student.r2.core.scheduling.constants.SchedulingServiceConstants;
import org.kuali.student.r2.core.scheduling.dto.ScheduleInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestComponentInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestSetInfo;
import org.kuali.student.r2.core.scheduling.service.SchedulingService;
import org.kuali.student.r2.core.scheduling.util.SchedulingServiceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Pulls out common parts of copyActivityOffering so it can be used in certain cases by
 * rolloverCourseOffering
 *
 * @author Kuali Student Team
 */
public class CopyActivityOfferingCommon {
    private static final Logger LOGGER = Logger.getLogger(CopyActivityOfferingCommon.class);

    private static SchedulingService schedulingService;
    private static CourseOfferingService coService;
    private static RoomService roomService;

    private static boolean _hasADLs(ActivityOfferingInfo sourceAO) {
        return sourceAO.getScheduleIds() != null && ! sourceAO.getScheduleIds().isEmpty();
    }

    private static ScheduleRequestSetInfo _buildDefaultSRSInfo(ScheduleRequestSetInfo set, ActivityOfferingInfo targetAO) {
        ScheduleRequestSetInfo newSrs = new ScheduleRequestSetInfo(set);
        newSrs.getRefObjectIds().clear();
        // Add the newly created AO
        newSrs.getRefObjectIds().add(targetAO.getId());
        newSrs.setTypeKey(SchedulingServiceConstants.SCHEDULE_REQUEST_SET_TYPE_SCHEDULE_REQUEST_SET);
        newSrs.setStateKey(SchedulingServiceConstants.SCHEDULE_REQUEST_SET_STATE_CREATED);
        newSrs.setMaxEnrollmentShared(false); // kind of arbitrary, but feel booleans should be set --cclin
        newSrs.setName("Schedule request set for " + targetAO.getCourseOfferingCode() + " - " + targetAO.getActivityCode());
        // Must clear out old ID
        newSrs.setId(null);
        return newSrs;
    }

    private static void _copyAO_copyWithADLs(List<ScheduleRequestSetInfo> srsList,
                                      ActivityOfferingInfo targetAO,
                                      String sourceAOStateKey,
                                      ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, DataValidationErrorException, ReadOnlyException {

        for (ScheduleRequestSetInfo originalSRSet: srsList) {
            // Should only loop once since exception thrown if multiple SRSes --cclin
            List<String> coloAoIds = originalSRSet.getRefObjectIds(); // Set of AOs associated with the SRS
            boolean isColocated = coloAoIds.size() > 1;

            if (isColocated) {
                // Change state to match target AO, otherwise stays in draft
                // A little hack--the validation forces creation of AOs to be in draft
                coService.changeActivityOfferingState(targetAO.getId(), sourceAOStateKey, context);
            }
            // Convert ADLs to RDLS
            // Step 1: Create the SRS
            ScheduleRequestSetInfo newSrs = _buildDefaultSRSInfo(originalSRSet, targetAO);
            if (isColocated) {
                // Add rest of AO ids if colocated
                newSrs.getRefObjectIds().addAll(originalSRSet.getRefObjectIds());
            }
            String newSrsTypeKey = newSrs.getTypeKey();
            String newRefObjectTypeKey = newSrs.getRefObjectTypeKey();
            ScheduleRequestSetInfo createdSRSet =
                    schedulingService.createScheduleRequestSet(newSrsTypeKey, newRefObjectTypeKey, newSrs, context);

            // Step 2: get the scheduleIds from the schedule requests (instead of using AO schedule IDs)
            List<ScheduleRequestInfo> sourceRequests =
                    schedulingService.getScheduleRequestsByScheduleRequestSet(originalSRSet.getId(), context);
            for (ScheduleRequestInfo request: sourceRequests) {
                // Step 3: for each schedule ID, create a schedule request and attach it to newly created SRS
                String schedId = request.getScheduleId(); // Get the ID of the ADL

                if (schedId == null) {
                    // If the schedule Id is null it means that the RDL has been saved, but hasn't been submitted to the
                    // "scheduler" for processing. So, just move on the the next item.
                    continue;
                }
                ScheduleInfo schedule = schedulingService.getSchedule(schedId, context);
                ScheduleRequestInfo newRequest = SchedulingServiceUtil.scheduleToRequest(schedule, roomService, context);
                newRequest.setScheduleRequestSetId(createdSRSet.getId()); // Attach to SRS
                // Persist to DB
                ScheduleRequestInfo savedRequest =
                        schedulingService.createScheduleRequest(newRequest.getTypeKey(), newRequest, context);
                if (isColocated) {
                    // Delete the original schedule requests since they are being replaced by new schedule requests
                    schedulingService.deleteScheduleRequest(request.getId(), context);
                }
            }

            if (isColocated) {
                // Delete original SRS if colocated
                schedulingService.deleteScheduleRequestSet(originalSRSet.getId(), context);
                // Schedule the AO.
                // (No need to delete schedules since scheduleActivityOffering deletes schedule IDs
                // before rescheduling)
                coService.scheduleActivityOffering(targetAO.getId(), context);
            }
        }
    }

    private static ScheduleRequestInfo _buildDefaultScheduleRequest(ScheduleRequestInfo request) {
        ScheduleRequestInfo copySchedReq = new ScheduleRequestInfo(request);
        copySchedReq.setId(null); // Clear out ID
        for (ScheduleRequestComponentInfo comp: copySchedReq.getScheduleRequestComponents()) {
            // Null out the IDs
            comp.setId(null);
        }
        return copySchedReq;
    }

    private static void _copyAO_copyWithoutADLs(List <ScheduleRequestSetInfo> srsList,
                                         ActivityOfferingInfo targetAO,
                                         ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, DoesNotExistException, DataValidationErrorException, ReadOnlyException {

        for (ScheduleRequestSetInfo set: srsList) {
            // Should only loop once since exception thrown if multiple SRSes --cclin
            List<String> coloAoIds = set.getRefObjectIds(); // Set of AOs associated with the SRS
            boolean isColocated = coloAoIds.size() > 1;
            if (isColocated) {
                // Just add the AO to the colo and update
                set.getRefObjectIds().add(targetAO.getId());
                try {
                    schedulingService.updateScheduleRequestSet(set.getId(), set, context);
                } catch (VersionMismatchException e) {
                    // Re-wrap exception
                    throw new OperationFailedException("Version mismatch: " + e.getMessage());
                }
            } else {
                // Not co-located so need to create a new SRS
                ScheduleRequestSetInfo newSrs = _buildDefaultSRSInfo(set, targetAO);
                String newSrsTypeKey = newSrs.getTypeKey();
                String newRefObjectTypeKey = newSrs.getRefObjectTypeKey();
                ScheduleRequestSetInfo createdSRSet =
                        schedulingService.createScheduleRequestSet(newSrsTypeKey, newRefObjectTypeKey, newSrs, context);

                // Fetch all SRs attached to original SRS. Make copy of each request and attach to new SRS
                List<ScheduleRequestInfo> requests =
                        schedulingService.getScheduleRequestsByScheduleRequestSet(set.getId(), context);
                for (ScheduleRequestInfo request: requests) {
                    // For each request, create a copy, and attach it to new SRS
                    ScheduleRequestInfo copySchedReq = SchedulingServiceUtil.copyScheduleRequest(request, createdSRSet.getId());
                    // Persist to DB
                    schedulingService.createScheduleRequest(copySchedReq.getTypeKey(), copySchedReq, context);
                }
            }
        }
    }

    /**
     * @param aoId ID of the AO to copy
     * @param coServiceParam Course Offering Service
     * @param schedulingServiceParam Scheduling Service
     * @param roomServiceParam Room Service
     * @param activityOfferingTransformer AO Transformer
     * @param targetFo null, if using same FO as aoID, otherwise use this
     * @param targetTermId null, if using same term as aoID, otherwise use this
     * @param context
     * @param optionKeys From rollover
     * @return the copied AO
     */
    public static ActivityOfferingInfo copy(String aoId,
                                            CourseOfferingService coServiceParam,
                                            SchedulingService schedulingServiceParam,
                                            RoomService roomServiceParam,
                                            ActivityOfferingTransformer activityOfferingTransformer,
                                            FormatOfferingInfo targetFo,
                                            String targetTermId,
                                            ContextInfo context,
                                            List<String> optionKeys)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException,
            OperationFailedException, DoesNotExistException, ReadOnlyException, DataValidationErrorException {
        schedulingService = schedulingServiceParam;
        coService = coServiceParam;
        roomService = roomServiceParam;
        ActivityOfferingInfo sourceAO = coService.getActivityOffering(aoId, context);
        ActivityOfferingInfo targetAO = new ActivityOfferingInfo(sourceAO);
        if (targetFo != null) {
            // Override FO if copying CO
            targetAO.setFormatOfferingId(targetFo.getId());
        }
        if (targetTermId != null) {
            targetAO.setTermId(targetTermId);
        }
        targetAO.setStateKey(LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY); // Need to set to draft
        targetAO.setId(null);
        targetAO.getScheduleIds().clear();
        if (targetAO.getInstructors() != null && !targetAO.getInstructors().isEmpty()) {
            for (OfferingInstructorInfo inst : targetAO.getInstructors()) {
                inst.setId(null);
            }
        }
        targetAO.setActivityCode(null);
        targetAO = coService.createActivityOffering(targetAO.getFormatOfferingId(), targetAO.getActivityId(), targetAO.getTypeKey(), targetAO, context);

        // have to copy rules AFTER AO is created because the link is by the AO id
        activityOfferingTransformer.copyRulesFromExistingActivityOffering(sourceAO, targetAO, new ArrayList<String>());

        /**
         * Create ScheduleRequests on the target AO. Use ScheduleComponents (ADL) to create the RDLs if any exist.
         * Otherwise, copy the requests from the source AO.
         */
        List<ScheduleRequestInfo> scheduleRequestInfos = new ArrayList<ScheduleRequestInfo>();
        String sourceAoId = sourceAO.getId();
        // Get all SRSes associated with the AO (typically, just one, but with partial colo, could be many --cclin)
        List<ScheduleRequestSetInfo> srsList =
                schedulingService.getScheduleRequestSetsByRefObject(CourseOfferingServiceConstants.REF_OBJECT_URI_ACTIVITY_OFFERING,
                        sourceAoId, context);
        if (srsList.size() > 1) {
            throw new OperationFailedException("Copy AO: Only one SRS is currently supported");
        }

        if (!optionKeys.contains(CourseOfferingSetServiceConstants.NO_SCHEDULE_OPTION_KEY)) {
            // rolloverCourseOffering has option keys
            if (_hasADLs(sourceAO)) {
                _copyAO_copyWithADLs(srsList, targetAO, sourceAO.getStateKey(), context);
            } else {
                _copyAO_copyWithoutADLs(srsList, targetAO, context);
            }
        }

        try {
            List<SeatPoolDefinitionInfo> sourceSPList = coService.getSeatPoolDefinitionsForActivityOffering(aoId, context);
            if (sourceSPList != null && !sourceSPList.isEmpty()) {
                for (SeatPoolDefinitionInfo sourceSP : sourceSPList) {
                    SeatPoolDefinitionInfo targetSP = new SeatPoolDefinitionInfo(sourceSP);
                    targetSP.setId(null);
                    targetSP.setTypeKey(LuiServiceConstants.SEATPOOL_LUI_CAPACITY_TYPE_KEY);
                    targetSP.setStateKey(LuiServiceConstants.LUI_CAPACITY_ACTIVE_STATE_KEY);
                    SeatPoolDefinitionInfo seatPoolCreated = coService.createSeatPoolDefinition(targetSP, context);
                    coService.addSeatPoolDefinitionToActivityOffering(seatPoolCreated.getId(), targetAO.getId(), context);

                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //Generate Registration Groups based on the copied AO
        //generateRegistrationGroupsForFormatOffering(targetAO.getFormatOfferingId(),context);

        return targetAO;
    }
}
