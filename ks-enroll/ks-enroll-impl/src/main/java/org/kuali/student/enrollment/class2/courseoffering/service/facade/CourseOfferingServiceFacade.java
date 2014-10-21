package org.kuali.student.enrollment.class2.courseoffering.service.facade;

import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.student.enrollment.class2.courseoffering.service.facade.issue.CourseOfferingAutogenIssue;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingClusterInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.coursewaitlist.dto.CourseWaitListInfo;
import org.kuali.student.r2.common.dto.BulkStatusInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
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
import org.kuali.student.r2.core.acal.dto.TermInfo;

import java.util.HashMap;
import java.util.List;

/**
 * Used to support service calls related to Autogen RGs.
 * Terminology
 *    AO: activity offering
 *    FO: format offering
 *    AOC: activity offering cluster
 *    RG: registration group
 */
public interface CourseOfferingServiceFacade {
    
    /**
     * Useful for when a cluster is created to create the naming.
     * 
     * e.g. CL 1, CL 2, CL 3, ...
     * 
     * @param numberOfExistingClusters used to determine the next name as CL (numberOfExistingClusters+1)
     * @return the default cluster name for the next cluster.
     */
    public String getDefaultClusterName (int numberOfExistingClusters);

    /**
     * Useful for when a cluster is created to create the naming.
     *
     * e.g. CL 1, CL 2, CL 3, ...
     *
     * @param courseOfferingId: determines the CourseOffering that a default cluster will be created within
     * @return the default cluster name for the next cluster.
     */
    public String getDefaultClusterNamePerCO (String courseOfferingId, ContextInfo context);
    
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

    CourseOfferingInfo copyCourseOfferingToTargetTerm(CourseOfferingInfo coInfo, TermInfo targetTerm, List<String> optionKeys, ContextInfo context) throws InvalidParameterException, PermissionDeniedException, DataValidationErrorException, AlreadyExistsException, ReadOnlyException, OperationFailedException, MissingParameterException, DoesNotExistException
            ;

    /**
     * User Story 3: I need the system to automatically create reg groups when I create an AO (via add or copy)
     *               to eliminate the need to manually create them
     * This creates an AO, then adds the created AO to an AOC, generating RGs as needed
     * @param aoInfo The AO to be created and added to the AOC
     * @param aocId The ID of the activity offering cluster
     * @param context
     * @return List of RGs created
     */
    ActivityOfferingResult createActivityOffering(ActivityOfferingInfo aoInfo, String aocId, ContextInfo context)
            throws PermissionDeniedException, DataValidationErrorException, InvalidParameterException, ReadOnlyException,
                   OperationFailedException, MissingParameterException, DoesNotExistException, VersionMismatchException;

    /**
     * A variant on User Story 3 that does not create an AO (relying on copyAO from the
     * @param aoId The ID of an AO to copy
     * @param aocId The AOC cluster ID for the copy of the AO to be associated with
     * @param context
     * @return An AOResult with RGs created
     */
    ActivityOfferingResult copyActivityOfferingToCluster(String aoId, String aocId, ContextInfo context)
            throws PermissionDeniedException, DataValidationErrorException,
            InvalidParameterException, ReadOnlyException, OperationFailedException,
            MissingParameterException, DoesNotExistException, VersionMismatchException;

    /**
     * User Story 4: I need the system to automatically update registration groups as I
     * update an activity offering(s) so reg group states and messaging remain current.
     * @param aoInfo AO to be updated
     * @param context contextInfo
     * @return AOResult of RGs modified?
     */
    ActivityOfferingResult updateActivityOffering(ActivityOfferingInfo aoInfo, ContextInfo context)
            throws PermissionDeniedException, DataValidationErrorException, InvalidParameterException, ReadOnlyException,
            OperationFailedException, MissingParameterException, DoesNotExistException, VersionMismatchException;

    /**
     * We need an explicit call to update the registration groups.
     * @param aoInfo AO used and later part of the returned object. This is a pass by reference object so be careful.
     * @param context contextInfo
     * @return AOResult of RGs modified?
     */
    ActivityOfferingResult updateRegistrationGroups(ActivityOfferingInfo aoInfo, ContextInfo context)
            throws PermissionDeniedException, DataValidationErrorException, InvalidParameterException, ReadOnlyException,
            OperationFailedException, MissingParameterException, DoesNotExistException, VersionMismatchException;

    /**
     * User Story 5: I need the system to automatically delete all associated registration groups when I delete
     *               an AO from an AOC
     * A pass-through to the deleteActivityOfferingCascaded.
     * @param aocId
     * @param foId
     * @param context
     * Doesn't return anything for now.  If we need something, can always modify the return type
     */
    void deleteActivityOfferingCascaded(String aoId, ContextInfo context)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException,
            OperationFailedException, DoesNotExistException, DataValidationErrorException, ReadOnlyException, VersionMismatchException;

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
    List<BulkStatusInfo> moveActivityOffering(String aoId, String sourceAocId, String targetAocId, ContextInfo context)
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
    void deleteActivityOfferingCluster(String aocId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException, DependentObjectsExistException;

    /**
     * User Story 8: As a user, I want to view counts of seats so that I can ensure I have the right numbers of seats to support my business needs.
     * 
     * Compute the maximum number of seats for the Activity Offerings within the given Course Offering specified by courseOfferingId.
     * 
     * Note: Out of Scope for M6
     * 
     * @param courseOfferingId
     * @param contextInfo Context information containing the
     *                                  principalId and locale information about
     *                                  the caller of service operation
     * @return the max number of seats in the Course Offering.
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure has occurred
     */
    
    public Integer getSeatCountByCourseOffering (String courseOfferingId, ContextInfo contextInfo) throws OperationFailedException, PermissionDeniedException;
    
    /**
     * User Story 8: As a user, I want to view counts of seats so that I can ensure I have the right numbers of seats to support my business needs.
     * 
     * Compute the maximum number of seats for Activity Offering's within the Activity Offering Cluster specified by aocId.
     * 
     * Note: Out of Scope for M6
     * 
     * @param aocId
     * @param contextInfo Context information containing the
     *                                  principalId and locale information about
     *                                  the caller of service operation
     * @return the max number of seats in the Activity Offering Cluster.
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure has occurred
     */
    public Integer getSeatCountByActivityOfferingCluster (String aocId, ContextInfo contextInfo) throws OperationFailedException, PermissionDeniedException;
    
    /**
     * User Story 8: As a user, I want to view counts of seats so that I can ensure I have the right numbers of seats to support my business needs.
     * 
     * Compute the maximum number of seats for Activity Offerings within the Registration Group specified by registrationGroupId.
     * 
     * Note: Out of Scope for M6
     * 
     * @param registrationGroupId
     * @param contextInfo Context information containing the
     *                                  principalId and locale information about
     *                                  the caller of service operation
     * @return the max number of seats in the Registration Group
     * @throws PermissionDeniedException unable to complete request
     * @throws OperationFailedException an authorization failure has occurred
     */
    public Integer getSeatCountByRegistrationGroup (String registrationGroupId, ContextInfo contextInfo) throws OperationFailedException, PermissionDeniedException;

    public List<CourseOfferingAutogenIssue> findAutogenIssuesByTerm(String termId, ContextInfo context)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException,
                   OperationFailedException, DoesNotExistException;

    public CourseOfferingAutogenIssue findAutogenIssuesByCourseOffering(String courseOfferingId, ContextInfo context)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException,
                   OperationFailedException, DoesNotExistException;

    /**
     * User Story 9: As a user, I want to be able to view AOs by Activity, AOC, or Registration Group so that I can view my Activities in a way to support my specific business needs
     * 
     * Get the counts for the information tool bar at the course offering level.
     * 
     * @param courseOfferingId
     * @param context
     * @return the counts at the course offering level.
     * @throws OperationFailedException 
     * @throws MissingParameterException 
     * @throws InvalidParameterException 
     * @throws DoesNotExistException 
     * @throws PermissionDeniedException 
     */
    public AutogenCount getAutogenCountByCourseOffering(String courseOfferingId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;
    
    /**
     * User Story 9: As a user, I want to be able to view AOs by Activity, AOC, or Registration Group so that I can view my Activities in a way to support my specific business needs
     * 
     * Get the counts for the information toolbar at the format offering level.
     * 
     * @param formatOfferingId
     * @param context
     * @return the counts at the format offering level.
     * 
     */
    public AutogenCount getAutogenCountByFormatOffering(String formatOfferingId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    
    /**
     * User Story 9: As a user, I want to be able to view AOs by Activity, AOC, or Registration Group so that I can view my Activities in a way to support my specific business needs
     * 
     * Get the counts for the information tool bar at the Activity Offering Cluster Level.
     * 
     * Note there will always be zero aoc's reported as it is nonsensical at this level.
     * 
     * @param activiyOfferingClusterId
     * @param context
     * @return the counts at the Activity Offering Cluster Level.
     */
    public AutogenCount getAutogenCountByActivtyOfferingCluster (String activiyOfferingClusterId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;


    /**
     * Returns all ActivityOfferingClusterInfos that map back to a single course offering
     *
     * @param courseOfferingId
     * @return
     */
    public List<ActivityOfferingClusterInfo> getActivityOfferingClusterByCourseOffering(String courseOfferingId);

    /**
     * Returns new CourseWaitListInfo after AO had been un-colocated
     *
     * @param courseWaitListInfo (original WL)
     * @param waitlistType
     * @param hasWaitlist
     * @param limitWaitlistSize
     * @param aoId (activity offering ID)
     * @param foId (format offering ID)
     * @return
     */
    public CourseWaitListInfo createUncolocatedWaitList(CourseWaitListInfo courseWaitListInfo, String waitlistType, boolean hasWaitlist, boolean limitWaitlistSize, String aoId, String foId, ContextInfo context);

    /**
     * Returns new CourseWaitListInfo after AO had been un-colocated
     *
     * @param courseWaitListInfo (original WL)
     * @param waitlistType
     * @param hasWaitlist
     * @param limitWaitlistSize
     * @param isColocatedAO (activity offering colo or not)
     * @param isMaxEnrollmentShared (max enrollment shared checked)
     * @param aoIdfoIdMap (map of ao and fo IDs)
     * @return
     */
    public CourseWaitListInfo createColocatedWaitList(CourseWaitListInfo courseWaitListInfo, String waitlistType, boolean hasWaitlist, boolean limitWaitlistSize, boolean isColocatedAO, boolean isMaxEnrollmentShared,
                                                      HashMap<String, String> aoIdfoIdMap, ContextInfo context);


    /**
     *
     * Returns the number of AO Clusters for a particular FO. If there are no clusters, the count will be 0
     *
     * @param foId    Format Offering Id
     * @param contextInfo Context Info
     * @return Returns the number of AO Clusters for a particular FO. If there are no clusters, the count will be 0
     * @throws MissingParameterException
     * @throws InvalidParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public int getAoClusterCountByFoId(String foId, ContextInfo contextInfo) throws MissingParameterException,
            InvalidParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * This method will give a KeyValue pair AO_ID, AO_TYPE of the Activity Offerings for a given Format offering
     *
     * @param foId   Format offering Id.
     * @param contextInfo   the context info object
     * @return List of KeyValue pair AO_ID, AO_TYPE of the Activity Offerings
     * @throws MissingParameterException
     * @throws InvalidParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public List<KeyValue> getAoIdAndAoTypeByFO(String foId, ContextInfo contextInfo) throws MissingParameterException,
            InvalidParameterException,
            OperationFailedException,
            PermissionDeniedException;
}
