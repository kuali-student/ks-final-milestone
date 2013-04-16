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
package org.kuali.student.enrollment.class2.courseoffering.service.adapter;

import org.apache.log4j.Logger;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.enrollment.class2.courseoffering.service.adapter.issue.ActivityOfferingNotInAocSubissue;
import org.kuali.student.enrollment.class2.courseoffering.service.adapter.issue.CourseOfferingAutogenIssue;
import org.kuali.student.enrollment.class2.courseoffering.service.adapter.issue.FormatOfferingAutogenIssue;
import org.kuali.student.enrollment.class2.courseoffering.service.adapter.issue.InvalidRegGroupSubissue;
import org.kuali.student.enrollment.class2.courseoffering.service.adapter.issue.RegGroupNotGeneratedByAocSubissue;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingClusterInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingSetInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseoffering.dto.SeatPoolDefinitionInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseofferingset.dto.SocRolloverResultItemInfo;
import org.kuali.student.r2.common.datadictionary.DataDictionaryValidator;
import org.kuali.student.r2.common.dto.BulkStatusInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DependentObjectsExistException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.permutation.PermutationCounter;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.class1.type.dto.TypeTypeRelationInfo;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.core.constants.TypeServiceConstants;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.kuali.student.r2.core.search.service.SearchService;
import org.kuali.student.r2.lum.course.dto.ActivityInfo;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.dto.FormatInfo;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.kuali.student.r2.lum.util.constants.CourseServiceConstants;

import javax.annotation.Resource;
import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Implementation of the Application Service Layer to provide the functionally specified functionality
 * using several service calls.
 *
 *
 * @author Kuali Student Team
 */
public class AutogenRegGroupServiceAdapterImpl implements AutogenRegGroupServiceAdapter {
    private static final Logger LOGGER = Logger.getLogger(AutogenRegGroupServiceAdapterImpl.class);

    @Resource (name="CourseOfferingService")
    private CourseOfferingService coService;
    
    @Resource (name="SearchService")
    private SearchService searchService;

    private TypeService typeService;

    private CourseService courseService;
    
    /* (non-Javadoc)
     * @see org.kuali.student.enrollment.class2.courseoffering.service.adapter.AutogenRegGroupServiceAdapter#getDefaultClusterName(int)
     */
    @Override
    public String getDefaultClusterName(int numberOfExistingClusters) {

        String clusterName = String.format("CL %d", (numberOfExistingClusters + 1));

        return clusterName;
    }

    @Override
    public String getDefaultClusterNamePerCO(String courseOfferingId, ContextInfo context) {
        List<String> foIds = new ArrayList<String>();
        int prefixNum = 1;
        String defaultClusterNameToCreate = String.format("CL %d", prefixNum);
        Set<String> clusterNames = new HashSet<String>();

        try {
            //retrieve all the FOs associated with the given CO
            List<FormatOfferingInfo> formatOfferingList = coService.getFormatOfferingsByCourseOffering(courseOfferingId, context);
            for(FormatOfferingInfo foInfo:formatOfferingList){
                foIds.add(foInfo.getId());
            }

            if (foIds!=null && !foIds.isEmpty()) {
                QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
                qbcBuilder.setPredicates(PredicateFactory.in("formatOfferingId", foIds.toArray()));
                QueryByCriteria criteria = qbcBuilder.build();

                //retrieve all the clusters associated with the given CO (by fetching clusters for all the FOs in the given CO)
                List<ActivityOfferingClusterInfo> aoClusterList = coService.searchForActivityOfferingClusters(criteria, context);

                for (ActivityOfferingClusterInfo aoCluster : aoClusterList) {
                    clusterNames.add(aoCluster.getPrivateName());
                }

                //assign the default cluster a name that hasn't been used within the given CO
                while (clusterNames.contains(defaultClusterNameToCreate)) {
                    prefixNum++;
                    defaultClusterNameToCreate = String.format("CL %d", prefixNum);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return defaultClusterNameToCreate;
    }

    public void setCourseOfferingService(CourseOfferingService coService) {
        this.coService = coService;
    }

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
        FormatOfferingInfo fo = coService.getFormatOffering(foId, context);
        String coId = fo.getCourseOfferingId();
        CourseOfferingInfo co = coService.getCourseOffering(coId, context);
        CourseInfo course = getCourseService().getCourse(co.getCourseId(), context);
        try {
            _addActivityOfferingTypesToFormatOffering(fo, course, context);
        } catch (VersionMismatchException e) {
            LOGGER.warn("VersionMismatchException thrown in createDefaultCluster, part 1");
            throw new OperationFailedException(e.getMessage());
        }
        // TODO: Would prefer a count method here
        List<ActivityOfferingInfo> aoInfos =
                coService.getActivityOfferingsByFormatOffering(foId, context);
        if (aoInfos != null && !aoInfos.isEmpty()) {
            LOGGER.warn("There are AOs without an AOC for this format (" + foId + ").  Indicates bad ref data.");
        }
        // Now we're good...create the AOC
        ActivityOfferingClusterInfo clusterInfo = new ActivityOfferingClusterInfo();
        clusterInfo.setFormatOfferingId(foId);
        
        String defaultClusterName = getDefaultClusterNamePerCO(coId, context);
        
        clusterInfo.setPrivateName(defaultClusterName);
        clusterInfo.setName(defaultClusterName);
        clusterInfo.setStateKey(CourseOfferingServiceConstants.AOC_ACTIVE_STATE_KEY);
        clusterInfo.setTypeKey(CourseOfferingServiceConstants.AOC_ROOT_TYPE_KEY);
        ActivityOfferingClusterInfo aoc =
                coService.createActivityOfferingCluster(foId, CourseOfferingServiceConstants.AOC_ROOT_TYPE_KEY, clusterInfo, context);
        if (aoInfos != null && !aoInfos.isEmpty()) {
            List<ActivityOfferingSetInfo> sets = aoc.getActivityOfferingSets();
            // Put stray AOs into this cluster (considered a problem)
            LOGGER.warn("Adding stray AOs to this cluster--shouldn't happen");
            for (ActivityOfferingInfo aoInfo: aoInfos) {
                boolean added = false;
                // Iterate over each set to find a matching AO set to put the stray AOs into
                for (ActivityOfferingSetInfo set: sets) {
                    if (set.getActivityOfferingType().equals(aoInfo.getTypeKey())) {
                        // Add AO ID to correct AO set
                        set.getActivityOfferingIds().add(aoInfo.getId());
                        added = true;
                        break;
                    }
                }
                if (!added) {
                    throw new OperationFailedException("Unable to find correct AO set for AO ID (" + aoInfo.getId() + ").  Bad data");
                }
            }
            try {
                coService.updateActivityOfferingCluster(foId, aoc.getId(), aoc, context);
            } catch (VersionMismatchException e) {
                LOGGER.warn("VersionMismatchException thrown in createDefaultCluster");
                throw new OperationFailedException(e.getMessage());
            }
        }
        return aoc;
    }

    private void _addActivityOfferingTypesToFormatOffering(FormatOfferingInfo fo, CourseInfo course, ContextInfo context)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException,
            DoesNotExistException, ReadOnlyException, DataValidationErrorException, VersionMismatchException {
        if (fo.getActivityOfferingTypeKeys() != null && !fo.getActivityOfferingTypeKeys().isEmpty()) {
            // Only bother with this if there are no AO type keys
            return;
        }
        List<FormatInfo> formats = course.getFormats();
        FormatInfo format = null;
        for (FormatInfo f: formats) {
            if (f.getId().equals(fo.getFormatId())) {
                // Find correct format
                format = f;
                break;
            }
        }
        // Get the activity types
        List<String> activityTypes = new ArrayList<String>();
        if (format.getActivities() == null || format.getActivities().isEmpty()) {
            throw new OperationFailedException("Formats must contain non-empty activities.  Error!");
        }
        for (ActivityInfo activityInfo: format.getActivities()) {
            activityTypes.add(activityInfo.getTypeKey());
        }
        // Use type service to find corresponding AO types--assumes 1-1 mapping of Activity types to AO types
        TypeService typeService = getTypeService();
        List<String> aoTypeKeys = new ArrayList<String>();
        for (String activityType: activityTypes) {
            List<TypeTypeRelationInfo> typeTypeRels =
                    typeService.getTypeTypeRelationsByOwnerAndType(activityType,
                            TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY,
                            context);
            if (typeTypeRels.size() != 1) {
                // Ref data currently only has a 1-1 mapping between Activity types (CLU) and AO types (LUI)
                // The UI screens only support this.  Should there be a many-to-1 relation between AO types and Activity
                // types (as they were originally envisioned), then this exception will be thrown.
                throw new UnsupportedOperationException("Can't handle Activity Type -> AO Type that isn't 1-1.  Search for this message in Java code");
            } else {
                String aoType = typeTypeRels.get(0).getRelatedTypeKey();
                aoTypeKeys.add(aoType);
            }
        }
        // Finally, set the ao types for this fo
        fo.setActivityOfferingTypeKeys(aoTypeKeys);

        getCoService().updateFormatOffering(fo.getId(), fo, context);
    }

    /**
     * User Story 2: I need the system to automatically create reg group(s) when I add a CO via Copy to eliminate
     * the need to manually create them
     * Note: this only handles copying an existing CO to a term, not creating from canonical which will
     *       be a separate method (not written as of now).
     * Note: not yet unit tested
     * Note: rollover will handle generation of RGs (not yet implemented)
     */
    @Override
    public CourseOfferingInfo copyCourseOfferingToTargetTerm(CourseOfferingInfo coInfo, TermInfo targetTerm, List<String> optionKeys, ContextInfo context)
            throws InvalidParameterException, PermissionDeniedException, DataValidationErrorException,
                   AlreadyExistsException, ReadOnlyException, OperationFailedException, MissingParameterException,
                   DoesNotExistException {
        // Impl based on CourseOfferingManagementController::copyCourseOfferingCreateCopy
        if (optionKeys == null) {
            optionKeys = new ArrayList<String>();
        }

        optionKeys.add(CourseOfferingServiceConstants.APPEND_COURSE_OFFERING_IN_SUFFIX_OPTION_KEY);

        // Copy CO by using rollover.  Note that rollover will take care of generating RGs
        SocRolloverResultItemInfo item = coService.rolloverCourseOffering(
                coInfo.getId(),
                targetTerm.getId(),
                optionKeys,
                context);
        CourseOfferingInfo targetCo = coService.getCourseOffering(item.getTargetCourseOfferingId(), context);
        return targetCo;
    }

    @Override
    public ActivityOfferingResult createActivityOffering(ActivityOfferingInfo aoInfo, String aocId, ContextInfo context)
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
        return _addActivityOfferingToClusterCommon(created, cluster, context);

    }

    /**
     * Used by both createActivityOffering and copyActivityOfferingToCluster
     * @param aoInfo AO info just created/copied
     * @param cluster The cluster to place it in
     * @param context
     * @return A result with RGs generated
     */
    private ActivityOfferingResult _addActivityOfferingToClusterCommon(ActivityOfferingInfo aoInfo, ActivityOfferingClusterInfo cluster, ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        // Now add the AO ID to the correct AOC set
        for (ActivityOfferingSetInfo set: cluster.getActivityOfferingSets()) {
            if (set.getActivityOfferingType().equals(aoInfo.getTypeKey())) {
                set.getActivityOfferingIds().add(aoInfo.getId()); // Found set, add the ID
                break;
            }
        }
        // Update the AOC
        ActivityOfferingClusterInfo updated =
                coService.updateActivityOfferingCluster(cluster.getFormatOfferingId(), cluster.getId(), cluster, context);
        // Note: this may generate RGs that do NOT include the AO just added
        ActivityOfferingResult aoResult = new ActivityOfferingResult();
        aoResult.setCreatedActivityOffering(aoInfo);
        if (_clusterCanGenerateRgs(updated)) {
            List<BulkStatusInfo> status =
                    coService.generateRegistrationGroupsForCluster(updated.getId(), context);
            aoResult.setGeneratedRegistrationGroups(status);
        } else {
            aoResult.setGeneratedRegistrationGroups(new ArrayList<BulkStatusInfo>());
            aoResult.getClusterstatus().setSuccess(Boolean.FALSE);
            aoResult.getClusterstatus().setMessage("Error: empty AO sets exist--can't generate reg groups");
        }
        return aoResult;
    }

    private boolean _clusterCanGenerateRgs(ActivityOfferingClusterInfo updated) {
        for (ActivityOfferingSetInfo set: updated.getActivityOfferingSets()) {
            if (set.getActivityOfferingIds().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ActivityOfferingResult copyActivityOfferingToCluster(String origAoId, String aocId, ContextInfo context)
            throws PermissionDeniedException, DataValidationErrorException,
            InvalidParameterException, ReadOnlyException, OperationFailedException,
            MissingParameterException, DoesNotExistException, VersionMismatchException {
        // Fetch cluster first, so if it fails, we don't continue on
        ActivityOfferingClusterInfo cluster =
                coService.getActivityOfferingCluster(aocId, context);
        // Make a new copy
        ActivityOfferingInfo copyAoInfo = coService.copyActivityOffering(origAoId, context);
        // Make sure FO IDs match up
        if (!cluster.getFormatOfferingId().equals(copyAoInfo.getFormatOfferingId())) {
            throw new DataValidationErrorException("Format Offering Ids do not match");
        }
        return _addActivityOfferingToClusterCommon(copyAoInfo, cluster, context);
    }

    public  ActivityOfferingResult updateRegistrationGroups(ActivityOfferingInfo activityOfferingInfo, ContextInfo context) throws PermissionDeniedException, DataValidationErrorException, InvalidParameterException, ReadOnlyException, OperationFailedException, MissingParameterException, DoesNotExistException, VersionMismatchException {
        try{
            ActivityOfferingResult aoResult = new ActivityOfferingResult();
            aoResult.setCreatedActivityOffering(activityOfferingInfo);

            // fetch the associated RGs
            String aoId = activityOfferingInfo.getId();
            List<RegistrationGroupInfo> rgs = coService.getRegistrationGroupsByActivityOffering(aoId, context);

            if (rgs != null && !rgs.isEmpty()) {
                //fetch the associated AOC
                ActivityOfferingClusterInfo cluster = coService.getActivityOfferingCluster(rgs.get(0).getActivityOfferingClusterId(), context);

                if (cluster != null) {
                    // Make sure FO IDs match up
                    if (!cluster.getFormatOfferingId().equals(activityOfferingInfo.getFormatOfferingId())) {
                        throw new DataValidationErrorException("Format Offering Ids do not match");
                    }

                    //check the max enroll number of the AOC
                    BulkStatusInfo clusterStatusInfo = new BulkStatusInfo();
                    clusterStatusInfo.setId(cluster.getId());
                    clusterStatusInfo.setSuccess(true);

                    List<ValidationResultInfo> validations = coService.validateActivityOfferingCluster(
                            DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), activityOfferingInfo.getFormatOfferingId(), cluster, context);
                    for (ValidationResultInfo validation : validations) {
                        if (validation.isWarn()) {
                            // If any validation is an error, then make this invalid
                            clusterStatusInfo.setSuccess(false);
                            break;
                        }
                    }
                    aoResult.setClusterstatus(clusterStatusInfo);

                }

                //check time conflict
                List<BulkStatusInfo> rgStatusInfos = new ArrayList<BulkStatusInfo>(rgs.size());

                for (RegistrationGroupInfo rgInfo : rgs) {
                    BulkStatusInfo rgStatusInfo = new BulkStatusInfo();
                    rgStatusInfo.setId(rgInfo.getId());
                    rgStatusInfo.setSuccess(true);

                    StatusInfo statusInfo;
                    if (_isRegistrationGroupValid (rgInfo.getId(), context)) {
                        if (coService.changeRegistrationGroupState(rgInfo.getId(), LuiServiceConstants.REGISTRATION_GROUP_OFFERED_STATE_KEY, context).getIsSuccess()) {
                        } else if (coService.changeRegistrationGroupState(rgInfo.getId(), LuiServiceConstants.REGISTRATION_GROUP_PENDING_STATE_KEY, context).getIsSuccess()) {
                        } else {
                            throw new RuntimeException("State change failed for RG: " + rgInfo.getId() + "From state:" + rgInfo.getStateKey());
                        }
                    } else {
                        coService.changeRegistrationGroupState(rgInfo.getId(), LuiServiceConstants.REGISTRATION_GROUP_INVALID_STATE_KEY, context);
                        rgStatusInfo.setSuccess(false);
                    }

                    rgStatusInfos.add(rgStatusInfo);
                }
                aoResult.setGeneratedRegistrationGroups(rgStatusInfos);

            }
            return aoResult;

        } catch (Exception e) {
        throw new RuntimeException(e);
        }

    }

    @Override
    public ActivityOfferingResult updateActivityOffering(ActivityOfferingInfo aoInfo, ContextInfo context) throws PermissionDeniedException, DataValidationErrorException, InvalidParameterException, ReadOnlyException, OperationFailedException, MissingParameterException, DoesNotExistException, VersionMismatchException {
        try {
            //update AO
            ActivityOfferingInfo activityOfferingInfo = coService.updateActivityOffering(aoInfo.getId(), aoInfo, context);
            return this.updateRegistrationGroups(activityOfferingInfo,context);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private boolean _isRegistrationGroupValid (String rgId, ContextInfo context) {
        try {
            List<ValidationResultInfo> validations = coService.verifyRegistrationGroup(rgId, context);
            for (ValidationResultInfo validation : validations) {
                if (!validation.isOk()) {
                    // If any validation is an error, then make this invalid
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void deleteActivityOfferingCascaded(String aoId,
                                               String aocId,
                                               ContextInfo context)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException,
                   OperationFailedException, DoesNotExistException {
        StatusInfo status = coService.deleteActivityOfferingCascaded(aoId, context);
    }

    @Override
    public List<BulkStatusInfo> moveActivityOffering(String aoId,
                                                            String sourceAocId,
                                                            String targetAocId,
                                                            ContextInfo context)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException,
            OperationFailedException, DoesNotExistException, ReadOnlyException, DataValidationErrorException,
            VersionMismatchException {
        if(coService == null) {
            coService = getCoService();
        }
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
        ActivityOfferingClusterInfo targetAoc = coService.getActivityOfferingCluster(targetAocId, context);
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
        if (_clusterCanGenerateRgs(updated)) {
            List<BulkStatusInfo> created =
                    coService.generateRegistrationGroupsForCluster(updated.getId(), context);
            return created;  //To change body of implemented methods use File | Settings | File Templates.
        } else {
            return new ArrayList<BulkStatusInfo>(); // Unable to generate so return empty list
        }
    }

    @Override
    public void deleteActivityOfferingCluster(String aocId, ContextInfo context)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException,
            OperationFailedException, DoesNotExistException, DependentObjectsExistException {
        List<ActivityOfferingInfo> aoInfos = coService.getActivityOfferingsByCluster(aocId, context);
        StatusInfo status = coService.deleteActivityOfferingClusterCascaded(aocId, context);
        // Delete each AO
        for (ActivityOfferingInfo aoInfo: aoInfos) {
            // get seat pools to delete
            List<SeatPoolDefinitionInfo> seatPools = coService.getSeatPoolDefinitionsForActivityOffering(aoInfo.getId(), context);

            // remove seat pool reference  to AO then delete orphaned seat pool
            for (SeatPoolDefinitionInfo seatPool : seatPools) {
                coService.removeSeatPoolDefinitionFromActivityOffering(seatPool.getId(), aoInfo.getId(), context);
                coService.deleteSeatPoolDefinition(seatPool.getId(), context);
            }

            // delete AO
            coService.deleteActivityOffering(aoInfo.getId(), context);
        }
    }

    /* (non-Javadoc)
     * @see org.kuali.student.enrollment.class2.courseoffering.service.applayer.AutogenRegGroupServiceAdapter#getMaxEnrollmentByCourseOffering(java.lang.String, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public Integer getSeatCountByCourseOffering(String courseOfferingId,
            ContextInfo contextInfo) throws OperationFailedException, PermissionDeniedException {
        
        try {
            CourseOfferingInfo co = coService.getCourseOffering(courseOfferingId, contextInfo);
            
            return co.getMaximumEnrollment();
        } catch (DoesNotExistException e) {
            throw new OperationFailedException("getSeatCountByCourseOffering (courseOfferingId=" + courseOfferingId + "): failed", e);
        } catch (InvalidParameterException e) {
            throw new OperationFailedException("getSeatCountByCourseOffering (courseOfferingId=" + courseOfferingId + "): failed", e);
        } catch (MissingParameterException e) {
            throw new OperationFailedException("getSeatCountByCourseOffering (courseOfferingId=" + courseOfferingId + "): failed", e);
        }
        
//        try {
//            List<ActivityOfferingInfo> aos = coService.getActivityOfferingsByCourseOffering(courseOfferingId, contextInfo);
//            
//            return _computeMaxEnrollment(aos);
//        } catch (DoesNotExistException e) {
//           throw new OperationFailedException("getSeatCountByCourseOffering (courseOfferingId=" + courseOfferingId + "): failed", e);
//           
//        } catch (InvalidParameterException e) {
//            throw new OperationFailedException("getSeatCountByCourseOffering (courseOfferingId=" + courseOfferingId + "): failed", e);
//        } catch (MissingParameterException e) {
//            throw new OperationFailedException("getSeatCountByCourseOffering (courseOfferingId=" + courseOfferingId + "): failed", e);
//        }
    }
    
   
    /* (non-Javadoc)
     * @see org.kuali.student.enrollment.class2.courseoffering.service.applayer.AutogenRegGroupServiceAdapter#getMaxEnrollmentByActivityOfferingCluster(java.lang.String, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public Integer getSeatCountByActivityOfferingCluster(String aocId,
            ContextInfo contextInfo) throws OperationFailedException, PermissionDeniedException {
        
        try {
            List<ActivityOfferingInfo> aos = coService.getActivityOfferingsByCluster(aocId, contextInfo);
            
            Map<String, ActivityOfferingInfo>aoMap = new HashMap<String, ActivityOfferingInfo>();
            
            for (ActivityOfferingInfo activityOfferingInfo : aos) {
                aoMap.put(activityOfferingInfo.getId(), activityOfferingInfo);
            }
            
            ActivityOfferingClusterInfo aoc = coService.getActivityOfferingCluster(aocId, contextInfo);
            List<ActivityOfferingSetInfo> aoSets = aoc.getActivityOfferingSets();
            int maxAOCEnrollment = Integer.MAX_VALUE;
            
            for (ActivityOfferingSetInfo activityOfferingSetInfo : aoSets) {
                int maxActivityTypeEnrollment =
                        _computeMaxEnrollment(activityOfferingSetInfo.getActivityOfferingIds(), aoMap);
                
                if (maxActivityTypeEnrollment < maxAOCEnrollment) {
                    maxAOCEnrollment = maxActivityTypeEnrollment;
                }
            }
            
            FormatOfferingInfo fo = coService.getFormatOffering(aoc.getFormatOfferingId(), contextInfo);
            CourseOfferingInfo co = coService.getCourseOffering(fo.getCourseOfferingId(), contextInfo);
            int maxCOEnrollment = co.getMaximumEnrollment();
            
            // This assumes that the seat count for an aoc is the smallest ao.maxEnrollment number in this cluster.
            List<RegistrationGroupInfo> rgs = coService.getRegistrationGroupsByActivityOfferingCluster(aocId, contextInfo);

            int maxAOEnrollment = _computeMaxEnrollment(aos);
            
            // cap is the smaller of the CO or AO or AOC enrollment limit
            int maxEnrollment = Math.min(Math.min(maxCOEnrollment, maxAOEnrollment), maxAOCEnrollment);
            int minEnrollment = Integer.MAX_VALUE;
            
            for (RegistrationGroupInfo registrationGroupInfo : rgs) {
                List<ActivityOfferingInfo>rgAOList = new ArrayList<ActivityOfferingInfo>();
                
                for (String aoId : registrationGroupInfo.getActivityOfferingIds()) {
                    rgAOList.add(aoMap.get(aoId));
                }
                
                int currentSeats = _computeMaxEnrollment(rgAOList);
                if (minEnrollment > currentSeats) {
                    minEnrollment = currentSeats;
                }
            }

            // actual seats can be smaller but not larger than max enrollment. 
            return Math.min(minEnrollment, maxEnrollment);

        } catch (DoesNotExistException e) {
            throw new OperationFailedException("getSeatCountByActivityOfferingCluster (aocId=" + aocId + "): failed", e);
        } catch (InvalidParameterException e) {
            throw new OperationFailedException("getSeatCountByActivityOfferingCluster (aocId=" + aocId + "): failed", e);
        } catch (MissingParameterException e) {
            throw new OperationFailedException("getSeatCountByActivityOfferingCluster (aocId=" + aocId + "): failed", e);
        }
        
    }


    private Integer _computeMaxEnrollment(List<String> aoIds, Map<String, ActivityOfferingInfo> aoMap) {
        
        List<ActivityOfferingInfo>aoList = new ArrayList<ActivityOfferingInfo>();
        for (String aoId : aoIds) {
            aoList.add(aoMap.get(aoId));
        }
        return _computeMaxEnrollment(aoList);
        
    }
    /*
     * The maxEnrollment of a list of Activity Offering's is the smallest max enrollment number.
     * 
     * This method will extract that number.
     * 
     */
    private Integer _computeMaxEnrollment(List<ActivityOfferingInfo> aos) {
        
        int minEnrollment = Integer.MAX_VALUE;
        
        for (ActivityOfferingInfo activityOfferingInfo : aos) {
            Integer maxEnrollment = activityOfferingInfo.getMaximumEnrollment();
            if (maxEnrollment != null) {
                if (minEnrollment > maxEnrollment) {
                    minEnrollment = maxEnrollment;
                }
            }
        }
        
        if (minEnrollment == Integer.MAX_VALUE) {
            // a data error that none of the AO's have a max enrollment specified
            return null;
        }
        else {
            return minEnrollment;
        }

    }
    /* (non-Javadoc)
     * @see org.kuali.student.enrollment.class2.courseoffering.service.applayer.AutogenRegGroupServiceAdapter#getMaxEnrollmentByRegistrationGroup(java.lang.String, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public Integer getSeatCountByRegistrationGroup(
            String registrationGroupId, ContextInfo contextInfo)
            throws OperationFailedException, PermissionDeniedException {
        
        try {
            RegistrationGroupInfo rg = coService.getRegistrationGroup(registrationGroupId, contextInfo);
            
            List<ActivityOfferingInfo> aos = coService.getActivityOfferingsByIds(rg.getActivityOfferingIds(), contextInfo);

            return _computeMaxEnrollment(aos);
        } catch (DoesNotExistException e) {
            throw new OperationFailedException("getSeatCountByRegistrationGroup (registrationGroupId=" + registrationGroupId + "): failed", e);
        } catch (InvalidParameterException e) {
            throw new OperationFailedException("getSeatCountByRegistrationGroup (registrationGroupId=" + registrationGroupId + "): failed", e);
        } catch (MissingParameterException e) {
            throw new OperationFailedException("getSeatCountByRegistrationGroup (registrationGroupId=" + registrationGroupId + "): failed", e);
        }
    }

    private Set<Set<String>> _convertToSetOfSetOfStrings(Set<List<String>> setOfRgAoIdLists) {
        Set<Set<String>> rgAoIdSets = new HashSet<Set<String>>();
        for (List<String> rgAoIds: setOfRgAoIdLists) {
            rgAoIdSets.add(new HashSet<String>(rgAoIds));
        }
        return rgAoIdSets;
    }

    private Set<Set<String>> _generatePotentialRgAoIdSets(ActivityOfferingClusterInfo cluster) {
        try {
            Set<List<String>> setOfRgAoIdLists =
                    PermutationCounter.computeMissingRegGroupAoIdsInCluster(cluster, new ArrayList<RegistrationGroupInfo>());
            Set<Set<String>> setOfRgAoIdSets =
                    _convertToSetOfSetOfStrings(setOfRgAoIdLists);
            return setOfRgAoIdSets;
        } catch (DataValidationErrorException e) {
            return null;
        }
    }

    @Override
    public List<CourseOfferingAutogenIssue> findAutogenIssuesByTerm(String termId, ContextInfo context)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException,
                   OperationFailedException, DoesNotExistException {
        List<String> coIds = coService.getCourseOfferingIdsByTerm(termId, Boolean.TRUE, context);
        List<CourseOfferingAutogenIssue> termCoIssues = new ArrayList<CourseOfferingAutogenIssue>();
        for (String coId: coIds) {
            CourseOfferingAutogenIssue coIssue = findAutogenIssuesByCourseOffering(coId, context);
            if (coIssue != null) {
                termCoIssues.add(coIssue);
            }
        }
        return termCoIssues;
    }

    @Override
    public CourseOfferingAutogenIssue findAutogenIssuesByCourseOffering(String courseOfferingId, ContextInfo context) throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException, DoesNotExistException {
        List<FormatOfferingInfo> fos = coService.getFormatOfferingsByCourseOffering(courseOfferingId, context);
        CourseOfferingAutogenIssue coIssue = new CourseOfferingAutogenIssue(courseOfferingId);
        for (FormatOfferingInfo fo: fos) {
            String foId = fo.getId();
            FormatOfferingAutogenIssue foIssue = new FormatOfferingAutogenIssue(foId);
            List<ActivityOfferingInfo> aoInfos =
                    coService.getActivityOfferingsByFormatOffering(fo.getId(), context);
            Set<String> aoIdSet = new HashSet<String>();
            for (ActivityOfferingInfo ao: aoInfos) {
                aoIdSet.add(ao.getId());
            }
            // Find AOs without clusters
            List<ActivityOfferingInfo> aosWoClusters =
                    coService.getActivityOfferingsWithoutClusterByFormatOffering(foId, context);
            // Gather only the IDs
            Set<String> aoIdsWoClusters = new HashSet<String>();
            for (ActivityOfferingInfo aoInfo: aosWoClusters) {
                aoIdsWoClusters.add(aoInfo.getId());
            }
            // Then create issues associated with it
            if (!aoIdsWoClusters.isEmpty()) {
                ActivityOfferingNotInAocSubissue aoNotInAoc = new ActivityOfferingNotInAocSubissue(courseOfferingId, fo.getId());
                aoNotInAoc.getActivityOfferingIds().addAll(aoIdsWoClusters);
                foIssue.getSubIssues().add(aoNotInAoc);  // Add the issue
            }
            // --------------------
            // Now verify RGs have correct AOs
            // First create a map
            List<Set<String>> aocAoIdList = new ArrayList<Set<String>>();
            List<ActivityOfferingClusterInfo> clusters =
                    coService.getActivityOfferingClustersByFormatOffering(foId, context);
            Set<Set<String>> possibleRgAOIds = new HashSet<Set<String>>();
            Set<Set<String>> actualRgAOIds = new HashSet<Set<String>>();
            for (ActivityOfferingClusterInfo cluster: clusters) {
                Set<String> clusterAoIds = new HashSet<String>();
                for (ActivityOfferingSetInfo set: cluster.getActivityOfferingSets()) {
                    clusterAoIds.addAll(set.getActivityOfferingIds());
                }
                aocAoIdList.add(clusterAoIds);
                Set<Set<String>> possibleRgAoIdsByAoc =
                        _generatePotentialRgAoIdSets(cluster);
                // Add to list of possible RgAOIds for this format offering
                possibleRgAOIds.addAll(possibleRgAoIdsByAoc);
            }
            // Now go through the RGs to check for invalid ones
            List<RegistrationGroupInfo> rgInfos =
                    coService.getRegistrationGroupsByFormatOffering(fo.getId(), context);
            for (RegistrationGroupInfo rg: rgInfos) {
                boolean found = false;
                List<String> rgAoIds = rg.getActivityOfferingIds();
                for (Set<String> aocAoIds: aocAoIdList) {
                    if (aocAoIds.containsAll(rgAoIds)) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    // No AOC contains this RG
                    InvalidRegGroupSubissue rgIssue = new InvalidRegGroupSubissue(courseOfferingId, foId);
                    foIssue.getSubIssues().add(rgIssue);  // Add the issue
                } else {
                    // Valid RG, store that info
                    Set<String> rgAOIds = new HashSet(rg.getActivityOfferingIds());
                    actualRgAOIds.add(rgAOIds);
                }
            }
            // Now find RGs that should have been created, but weren't
            possibleRgAOIds.removeAll(actualRgAOIds);
            Set<Set<String>> missingRgAOIds // renaming to make it easier to see what's going on
                    = new HashSet<Set<String>>(possibleRgAOIds);
            for (Set<String> rgAoIdSet: missingRgAOIds) {
                // Create an issue
                RegGroupNotGeneratedByAocSubissue subissue =
                        new RegGroupNotGeneratedByAocSubissue(courseOfferingId, foId);
                subissue.getActivityOfferingIds().addAll(rgAoIdSet);
                foIssue.getSubIssues().add(subissue);
            }
            if (!foIssue.getSubIssues().isEmpty()) {
                coIssue.getFormatOfferingIssues().add(foIssue);
            }
        }
        if (coIssue.getFormatOfferingIssues().isEmpty()) {
            return null;
        } else {
            return coIssue;
        }
    }

    /* (non-Javadoc)
     * @see org.kuali.student.enrollment.class2.courseoffering.service.adapter.AutogenRegGroupServiceAdapter#getAutogenCountByCourseOffering(java.lang.String, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public AutogenCount getAutogenCountByCourseOffering(
            String courseOfferingId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        
        SearchRequestInfo request = new SearchRequestInfo(CourseOfferingServiceConstants.AUTOGEN_COUNTS_BY_CO);
        
        request.addParam(CourseOfferingServiceConstants.AUTOGEN_COUNTS_BY_CO_ID_PARAM, courseOfferingId);
        
        return runAutogenCountSearch (request, context); 
    }

    /*
     * Helper to dispatch the search request and accumulate the results into an AutogenCount object.
     */
    private AutogenCount runAutogenCountSearch(SearchRequestInfo request, ContextInfo context) throws MissingParameterException, InvalidParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {

        request.setMaxResults(1);

        SearchResultInfo results = searchService.search(request, context);

        if (results.getRows().size() == 0) {
            // handle the no match case (This probably should never happen but this guards against such a case causing problems)
            throw new DoesNotExistException("No Results");
        }
        // else:
        SearchResultRowInfo row = results.getRows().get(0);

        AutogenCount count = new AutogenCount();

        // setup the defaults
        count.setNumberOfActivityOfferingClusters(0);
        count.setNumberOfActivityOfferings(0);
        count.setNumberOfInvalidRegistrationGroups(0);
        count.setNumberOfRegistrationGroups(0);
        
        List<SearchResultCellInfo> cells = row.getCells();

        for (SearchResultCellInfo cellInfo : cells) {
            String cellKey = cellInfo.getKey();

            if (cellKey
                    .equals(CourseOfferingServiceConstants.AUTOGEN_COUNTS_TOTAL_AOS)) {
                count.setNumberOfActivityOfferings(Integer.parseInt(cellInfo
                        .getValue()));
            } else if (cellKey
                    .equals(CourseOfferingServiceConstants.AUTOGEN_COUNTS_TOTAL_AOCS)) {
                count.setNumberOfActivityOfferingClusters(Integer
                        .parseInt(cellInfo.getValue()));
            } else if (cellKey
                    .equals(CourseOfferingServiceConstants.AUTOGEN_COUNTS_TOTAL_RGS)) {
                count.setNumberOfRegistrationGroups(Integer.parseInt(cellInfo
                        .getValue()));
            } else if (cellKey
                    .equals(CourseOfferingServiceConstants.AUTOGEN_COUNTS_TOTAL_INVALID_RGS)) {
                count.setNumberOfInvalidRegistrationGroups(Integer
                        .parseInt(cellInfo.getValue()));
            }
        }

        return count;
    }
    /* (non-Javadoc)
     * @see org.kuali.student.enrollment.class2.courseoffering.service.adapter.AutogenRegGroupServiceAdapter#getAutogenCountByFormatOffering(java.lang.String, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public AutogenCount getAutogenCountByFormatOffering(
            String formatOfferingId, ContextInfo context) throws MissingParameterException, InvalidParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
       
        SearchRequestInfo request = new SearchRequestInfo(CourseOfferingServiceConstants.AUTOGEN_COUNTS_BY_FO);
        
        request.addParam(CourseOfferingServiceConstants.AUTOGEN_COUNTS_BY_FO_ID_PARAM, formatOfferingId);
        
        return runAutogenCountSearch (request, context); 
    }

    /* (non-Javadoc)
     * @see org.kuali.student.enrollment.class2.courseoffering.service.adapter.AutogenRegGroupServiceAdapter#getAutogenCountByActivtyOfferingCluster(java.lang.String, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public AutogenCount getAutogenCountByActivtyOfferingCluster(
            String activiyOfferingClusterId, ContextInfo context) throws MissingParameterException, InvalidParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
        
        SearchRequestInfo request = new SearchRequestInfo(CourseOfferingServiceConstants.AUTOGEN_COUNTS_BY_AOC);
        
        request.addParam(CourseOfferingServiceConstants.AUTOGEN_COUNTS_BY_AOC_ID_PARAM, activiyOfferingClusterId);
        
        return runAutogenCountSearch (request, context); 
        
    }

    public CourseOfferingService getCoService() {
        if(coService == null) {
//            coService = (CourseOfferingService) GlobalResourceLoader.getService("CourseOfferingService");
            coService = (CourseOfferingService) GlobalResourceLoader.getService(new QName(CourseOfferingServiceConstants.NAMESPACE,
                    CourseOfferingServiceConstants.SERVICE_NAME_LOCAL_PART));
        }

        return coService;
    }

    public void setCoService(CourseOfferingService coService) {
        this.coService = coService;
    }

    public TypeService getTypeService() {
        if (typeService == null) {
            QName qname = new QName(TypeServiceConstants.NAMESPACE,
                                    TypeServiceConstants.SERVICE_NAME_LOCAL_PART);
            typeService = (TypeService) GlobalResourceLoader.getService(qname);
        }
        return typeService;
    }

    public CourseService getCourseService() {
        if (courseService == null) {
            QName qname = new QName(CourseServiceConstants.NAMESPACE,
                    CourseServiceConstants.SERVICE_NAME_LOCAL_PART);
            courseService = (CourseService) GlobalResourceLoader.getService(qname);
        }
        return courseService;
    }
}
