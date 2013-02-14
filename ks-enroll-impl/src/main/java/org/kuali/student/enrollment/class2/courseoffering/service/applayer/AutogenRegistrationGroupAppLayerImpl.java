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
 * Created by Charles on 2/5/13
 */
package org.kuali.student.enrollment.class2.courseoffering.service.applayer;

import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingClusterInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingSetInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
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
import org.kuali.student.r2.common.dto.StatusInfo;

import java.util.List;
import java.util.Map;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class AutogenRegistrationGroupAppLayerImpl implements AutogenRegistrationGroupAppLayer {
    CourseOfferingService coService;

    @Override
    public ActivityOfferingClusterInfo createDefaultCluster(String foId, ContextInfo context)
            throws PermissionDeniedException,
            MissingParameterException,
            InvalidParameterException,
            OperationFailedException,
            DoesNotExistException,
            ReadOnlyException,
            DataValidationErrorException {
        // TODO: Would prefer a count method here
        List<ActivityOfferingClusterInfo> clusters =
                coService.getActivityOfferingClustersByFormatOffering(foId, context);
        if (clusters != null && !clusters.isEmpty()) {
            throw new OperationFailedException("Cluster already exists");
        }
        // TODO: Would prefer a count method here
        List<ActivityOfferingInfo> aoInfos =
                coService.getActivityOfferingsByFormatOffering(foId, context);
        if (aoInfos != null && !aoInfos.isEmpty()) {
            throw new OperationFailedException("Activity offerings already exists");
        }
        // Now we're good...create the AOC
        ActivityOfferingClusterInfo clusterInfo = new ActivityOfferingClusterInfo();
        clusterInfo.setFormatOfferingId(foId);
        clusterInfo.setPrivateName("Default");
        clusterInfo.setName("Default");
        clusterInfo.setStateKey(CourseOfferingServiceConstants.AOC_ACTIVE_STATE_KEY);
        clusterInfo.setTypeKey(CourseOfferingServiceConstants.AOC_ROOT_TYPE_KEY);
        ActivityOfferingClusterInfo aoc =
                coService.createActivityOfferingCluster(foId, CourseOfferingServiceConstants.AOC_ROOT_TYPE_KEY, clusterInfo, context);
        return aoc;
    }

    @Override
    public List<RegistrationGroupInfo> createActivityOffering(ActivityOfferingInfo aoInfo, String aocId, ContextInfo context)
            throws PermissionDeniedException, DataValidationErrorException,
            InvalidParameterException, ReadOnlyException, OperationFailedException,
            MissingParameterException, DoesNotExistException, VersionMismatchException {
        // Fetch cluster first, so if it fails, we don't continue on
        ActivityOfferingClusterInfo cluster =
                coService.getActivityOfferingCluster(aocId, context);
        // Make sure FO IDs match up
        if (!cluster.getFormatOfferingId().equals(aoInfo.getFormatOfferingId())) {
            throw new DataValidationErrorException("Format Offering Ids do not match");
        }
        ActivityOfferingInfo created = coService.createActivityOffering(aoInfo.getFormatOfferingId(), aoInfo.getActivityId(),
                aoInfo.getTypeKey(), aoInfo, context);
        // Now add the AO ID to the correct AOC set
        for (ActivityOfferingSetInfo set: cluster.getActivityOfferingSets()) {
            if (set.getActivityOfferingType().equals(created.getTypeKey())) {
                set.getActivityOfferingIds().add(created.getId()); // Found set, add the ID
                break;
            }
        }
        // Update the AOC
        ActivityOfferingClusterInfo updated =
                coService.updateActivityOfferingCluster(cluster.getFormatOfferingId(), cluster.getId(), cluster, context);
        // Note: this may generate RGs that do NOT include the AO just added
        StatusInfo status =
                coService.generateRegistrationGroupsForCluster(updated.getId(), context);
        return null; // TODO: Fix this
    }

    @Override
    public List<RegistrationGroupInfo> deleteActivityOfferingCascaded(String aoId,
                                                                      String aocId,
                                                                      ContextInfo context)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException,
                   OperationFailedException, DoesNotExistException {
        StatusInfo status = coService.deleteActivityOfferingCascaded(aoId, context);
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<RegistrationGroupInfo> moveActivityOffering(String aoId,
                                                            String sourceAocId,
                                                            String targetAocId,
                                                            ContextInfo context)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException,
            OperationFailedException, DoesNotExistException, ReadOnlyException, DataValidationErrorException,
            VersionMismatchException {
        // Fetch the AOInfo
        ActivityOfferingInfo aoInfo = coService.getActivityOffering(aoId, context);
        // Fetch the source AOC
        ActivityOfferingClusterInfo sourceAoc = coService.getActivityOfferingCluster(sourceAocId, context);
        // Verify that aoId is in this AOC
        ActivityOfferingSetInfo setWithAoId = null;
        for (ActivityOfferingSetInfo set: sourceAoc.getActivityOfferingSets()) {
            if (set.getActivityOfferingType().equals(aoInfo.getTypeKey()) &&
                    set.getActivityOfferingIds().contains(aoId)) {
                setWithAoId = set;
                break;
            }
        }
        if (setWithAoId == null) {
            // Not in source AOC
            throw new InvalidParameterException("aoId, " + aoId + ", does not appear in cluster, " + sourceAocId);
        }
        // Fetch target AOC
        ActivityOfferingClusterInfo targetAoc = coService.getActivityOfferingCluster(sourceAocId, context);
        // Verify the FOs of source/target match up
        if (!sourceAoc.getFormatOfferingId().equals(targetAoc.getFormatOfferingId())) {
            throw new InvalidParameterException("Source/target AOCs do not have matching format offering IDs");
        }
        // Also, check for trivial case of the source/target AOC being identical
        if (sourceAocId.equals(targetAocId)) {
            return null; // exit if same
        }
        setWithAoId.getActivityOfferingIds().remove(aoId); // Delete the AO ID
        // This will delete RGs
        coService.updateActivityOfferingCluster(aoInfo.getFormatOfferingId(), sourceAocId, sourceAoc, context);
        // Now, add the AO ID to the target AOC
        boolean inserted = false;
        for (ActivityOfferingSetInfo set: targetAoc.getActivityOfferingSets()) {
            // Pick first AO set with matching type
            if (set.getActivityOfferingType().equals(aoInfo.getTypeKey())) {
                if (set.getActivityOfferingIds().contains(aoId)) {
                    throw new OperationFailedException("aoId already exists in target AOC--shouldn't happen");
                }
                set.getActivityOfferingIds().add(aoId);
                inserted = true;
                break;
            }
        }
        if (!inserted) {
            // Didn't actually add it
            throw new OperationFailedException("Unable to find AO set in target AOC with correct type: " + aoInfo.getTypeKey());
        }
        // update target AOC
        ActivityOfferingClusterInfo updated =
                coService.updateActivityOfferingCluster(aoInfo.getFormatOfferingId(), targetAocId, targetAoc, context);
        // Generate missing RGs
        StatusInfo statusInfo =
                coService.generateRegistrationGroupsForCluster(updated.getId(), context);
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void deleteActivityOfferingClusterCascaded(String aocId, ContextInfo context)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException,
                   OperationFailedException, DoesNotExistException {
        StatusInfo status = coService.deleteActivityOfferingClusterCascaded(aocId, context);
    }


}
