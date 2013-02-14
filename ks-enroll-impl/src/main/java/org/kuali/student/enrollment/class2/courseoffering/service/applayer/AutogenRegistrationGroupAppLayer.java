package org.kuali.student.enrollment.class2.courseoffering.service.applayer;

import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingClusterInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;

import java.util.List;
import java.util.Map;

/**
 * Used to support service calls related to Autogen RGs.
 * Terminology
 *    AO: activity offering
 *    FO: format offering
 *    AOC: activity offering cluster
 *    RG: registration group
 */
public interface AutogenRegistrationGroupAppLayer {
    /**
     * If a Course Offering is brand-new with no AOs, then call this method to create the first default
     * AOC to be used.
     * @param foId The format offering ID that the cluster belongs to
     * @param context Context info
     * @return the default cluster created
     * @throws PermissionDeniedException
     * @throws MissingParameterException
     * @throws InvalidParameterException
     * @throws OperationFailedException
     * @throws DoesNotExistException
     * @throws ReadOnlyException
     * @throws DataValidationErrorException
     */
    ActivityOfferingClusterInfo createDefaultCluster(String foId, ContextInfo context)
            throws PermissionDeniedException, MissingParameterException,
                   InvalidParameterException, OperationFailedException,
                   DoesNotExistException, ReadOnlyException, DataValidationErrorException;

    /**
     * User Story 3: I need the system to automatically create reg groups when I create an AO (via add or copy)
     *               to eliminate the need to manually create them
     * This creates an AO, then adds the created AO to an AOC, generating RGs as needed
     * @param aoInfo The AO to be created and added to the AOC
     * @param aocId The ID of the activity offering cluster
     * @param context
     * @return List of RGs created
     */
    List<RegistrationGroupInfo> createActivityOffering(ActivityOfferingInfo aoInfo, String aocId, ContextInfo context)
            throws PermissionDeniedException, DataValidationErrorException, InvalidParameterException, ReadOnlyException,
                   OperationFailedException, MissingParameterException, DoesNotExistException, VersionMismatchException;

    /**
     * User Story 5: I need the system to automatically delete all associated registration groups when I delete
     *               an AO from an AOC
     * A pass-through to the deleteActivityOfferingCascaded.
     * @param aocId
     * @param context
     * @return
     */
    List<RegistrationGroupInfo> deleteActivityOfferingCascaded(String aoId, String aocId, ContextInfo context)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException,
                   OperationFailedException, DoesNotExistException;

    /**
     * User Story 6: As a user, I need the system to automatically create/delete all associated registration
     *               groups when I move an Activity from one AOC to another
     * Moves an AO from a source AOC to a target AOC.  Assumption is each AO Set in an AOC has a unique AO type,
     * i.e., no two AO sets within an AOC can have the same type.
     * @param aoId The id of the AO to be moved from source AOC to target AOC
     * @param sourceAocId The AOC where aoId is currently (and to be removed)
     * @param targetAocId The AOC where aoId should be placed
     * @param context
     * @return TBD
     * @throws PermissionDeniedException
     * @throws MissingParameterException
     * @throws InvalidParameterException
     * @throws OperationFailedException
     * @throws DoesNotExistException
     */
    List<RegistrationGroupInfo> moveActivityOffering(String aoId, String sourceAocId, String targetAocId, ContextInfo context)
            throws  PermissionDeniedException,
                    DataValidationErrorException,
                    DoesNotExistException,
                    InvalidParameterException,
                    MissingParameterException,
                    OperationFailedException,
                    ReadOnlyException,
                    VersionMismatchException;

    /**
     * User Story 7: As a user, I need the system to automatically delete all AOs when I delete an
     *               AOC so I don’t have to delete all the AOs first
     * This is a pass-through to service call deleteActivityOfferingClusterCascaded
     * @param aocId The ID of the AOC to delete
     *
     */
    void deleteActivityOfferingClusterCascaded(String aocId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
                   MissingParameterException, OperationFailedException,
                   PermissionDeniedException;
}
