/**
 */
package org.kuali.student.enrollment.coursewaitlist.service;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationResponseInfo;
import org.kuali.student.enrollment.coursewaitlist.dto.CourseWaitlistEntryInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.constants.CourseWaitlistServiceConstants;

/**
 * The Course Registration Service is a Class II service supporting the process
 * of registering a student in course(s) for a term. The service provides
 * operations for creating and validating registration requests , registering
 * for a course, waitlist processing, and dropping a course. This service
 * supports the concept of registration cart in the application and all of the
 * transactional requests for registration are made through this service. As
 * part of negotiating the student's registration, operations are provided to
 * manage related exceptions and holds related to registration.
 * 
 * @version 0.0.7
 * @author Kuali Student Team (sambit)
 */

@WebService(name = "CourseWaitlistService", targetNamespace = CourseWaitlistServiceConstants.NAMESPACE)
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface CourseWaitlistService  {

    /**
     * Retrieves the open seat count for a particular course offering. It sums
     * up the open seats for individual registration groups under the same
     * course offering.
     * 
     * @param courseOfferingId
     * @param context
     * @return
     * @throws InvalidParameterException Invalid courseOfferingId in the input
     * @throws MissingParameterException Missing courseOfferingId in the input
     * @throws OperationFailedException Unable to complete request
     * @throws PermissionDeniedException Not authorized to do this operation
     */
    public Integer getAvailableSeatsForCourseOffering(@WebParam(name = "courseOfferingId") String courseOfferingId, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Get available seats for the registration group.
     * 
     * @param regGroupId Identifier of the registration group
     * @param context
     * @return
     * @throws InvalidParameterException Invalid regGroupId in the input
     * @throws MissingParameterException Missing regGroupId in the input
     * @throws OperationFailedException Unable to complete request
     * @throws PermissionDeniedException Not authorized to do this operation
     */
    public Integer getAvailableSeatsForRegGroup(@WebParam(name = "regGroupId") String regGroupId, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Gets the number of seats available for a particular student in a
     * registration group.
     * <p>
     * Implementation notes : Seats available for a student taking seat pool (if
     * any) into consideration.
     * 
     * @param studentId Identifier of the student
     * @param regGroupId Identifier of the registration group
     * @param context
     * @return
     * @throws InvalidParameterException Invalid studentId or regGroupId in the
     *             input
     * @throws MissingParameterException Missing studentId or regGroupId in the
     *             input
     * @throws OperationFailedException Unable to complete request
     * @throws PermissionDeniedException Not authorized to do this operation
     */
    public Integer getAvailableSeatsForStudentInRegGroup(@WebParam(name = "studentId") String studentId, @WebParam(name = "regGroupId") String regGroupId,
            @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Returns the available seats in a particular seat pool. This is an admin
     * support function to check the seat pool usage.
     * 
     * @param studentId Identifier of the student
     * @param seatpoolId Identifier of the seatpool
     * @param context
     * @return
     * @throws InvalidParameterException Invalid seatpool in the input
     * @throws MissingParameterException Missing parameter seatpoolId in the
     *             input
     * @throws OperationFailedException Unable to complete request
     * @throws PermissionDeniedException Not authorized to do this operation
     */
    public Integer getAvailableSeatsInSeatpool(@WebParam(name = "seatpoolId") String seatpoolId, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Gets a course waitlist entry by id.
     * 
     * @param courseWaitlistEntryId Id of the course waitlist entry
     * @param context
     * @return
     * @throws DoesNotExistException No courseWaitlistEntryId exists
     * @throws InvalidParameterException Invalid courseWaitlistEntryId
     * @throws MissingParameterException Missing courseWaitlistEntryId in the
     *             input
     * @throws OperationFailedException Unable to complete request
     * @throws PermissionDeniedException Not authorized to do this operation
     */
    public CourseWaitlistEntryInfo getCourseWaitlistEntry(@WebParam(name = "courseWaitlistEntryId") String courseWaitlistEntryId, @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Updates a course waitlist entry
     * 
     * @param courseWaitlistEntryId Id of the course waitlist entry to be
     *            updated
     * @param courseWaitlistEntryInfo The modified
     *            {@link CourseWaitlistEntryInfo}
     * @param context
     * @return
     * @throws AlreadyExistsException
     * @throws DataValidationErrorException The courseWaitlistEntryInfo is not
     *             valid
     * @throws InvalidParameterException Invalid courseWaitlistEntryId or
     *             courseWaitlistEntryInfo in the input
     * @throws MissingParameterException Missing courseWaitlistEntryId or
     *             courseWaitlistEntryInfo in the input
     * @throws OperationFailedException Unable to complete request
     * @throws PermissionDeniedException Not authorized to do this operation
     */
    public StatusInfo updateCourseWaitlistEntry(@WebParam(name = "courseWaitlistEntryId") String courseWaitlistEntryId,
            @WebParam(name = "courseWaitlistEntryInfo") CourseWaitlistEntryInfo courseWaitlistEntryInfo, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException,
            DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Updates the state of an existing CourseWaitlistEntry to another state
     * provided that it is valid to do so.
     *
     * @param courseWaitlistEntryId        identifier of the CourseWaitlistEntry to be
     *                                      updated
     * @param nextStateKey       The State Key into which the identified
     *                           CourseWaitlistEntry will be placed if the
     *                           operation succeeds.
     * @param contextInfo        Context information containing the principalId
     *                           and locale information about the caller of
     *                           service operation
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException     the identified CourseWaitlistEntry does
     *                                   not exist
     * @throws InvalidParameterException the contextInfo object is invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo changeCourseWaitlistEntryState(@WebParam(name = "courseWaitlistEntryId") String courseWaitlistEntryId, @WebParam(name = "nextStateKey") String nextStateKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Reorder all the entries that are passed in in the input list, i.e.,
     * update each of the entries rank to begin from the top and push the
     * entries not in the list to the ranks after the entries.
     * 
     * @param courseWaitlistEntryId
     * @param position
     * @param context
     * @return
     * @throws DataValidationErrorException
     * @throws InvalidParameterException Invalid courseWaitlistEntryIds in the
     *             input
     * @throws MissingParameterException Missing courseWaitlistEntryIdsin the
     *             input
     * @throws OperationFailedException Unable to complete request
     * @throws PermissionDeniedException Not authorized to do this operation
     */
    public StatusInfo reorderCourseWaitlistEntries(@WebParam(name = "courseWaitlistEntryIds") List<String> courseWaitlistEntryIds, @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Insert a waitlist entry at a particular position in the waitlist. The
     * courseWaitlistEntryId would be moved to the position and all other
     * waitlist entries for that reg group would have adjusted rank
     * 
     * @param courseWaitlistEntryId The id of the course waitlist entry
     * @param position The new rank for the waitlist entry
     * @param context
     * @return
     * @throws DoesNotExistException The courseWaitlistEntryId is not found
     * @throws InvalidParameterException The courseWaitlistEntryId is invalid
     * @throws MissingParameterException Input courseWaitlistEntryId or position
     *             is missing
     * @throws OperationFailedException Unable to complete request
     * @throws PermissionDeniedException Not authorized to do this operation
     */
    public StatusInfo insertCourseWaitlistEntryAtPosition(@WebParam(name = "courseWaitlistEntryId") String courseWaitlistEntryId, @WebParam(name = "position") Integer position,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Remove the {@link CourseWaitlistEntryInfo}, change its state to CANCELLED
     * 
     * @param courseWaitlistEntryId The id of the course waitlist entry
     * @param context
     * @return
     * @throws DoesNotExistException The courseWaitlistEntryId is not found
     * @throws InvalidParameterException The courseWaitlistEntryId is invalid
     * @throws MissingParameterException Input courseWaitlistEntryId or position
     *             is missing
     * @throws OperationFailedException Unable to complete request
     * @throws PermissionDeniedException Not authorized to do this operation
     */
    public StatusInfo removeCourseWaitlistEntry(@WebParam(name = "courseWaitlistEntryId") String courseWaitlistEntryId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Deletes a course waitlist entry
     * 
     * @param courseWaitlistEntryId
     * @param context
     * @return
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public StatusInfo deleteCourseWaitlistEntry(@WebParam(name = "courseWaitlistEntryId") String courseWaitlistEntryId, @WebParam(name = "context") ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Validates a course waitlist entry.
     * 
     * @param validateTypeKey
     * @param courseWaitlistEntryInfo
     * @param context
     * @return
     * @throws DataValidationErrorException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public StatusInfo validateCourseWaitlistEntry(@WebParam(name = "validateTypeKey") String validateTypeKey,
            @WebParam(name = "courseWaitlistEntryInfo") CourseWaitlistEntryInfo courseWaitlistEntryInfo, @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Register a student to a reg group from a waitlist.
     * 
     * @param regRequestInfo
     * @param context
     * @return
     * @throws AlreadyExistsException
     * @throws DataValidationErrorException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public RegistrationResponseInfo registerStudentFromWaitlist(@WebParam(name = "courseWaitlistEntryId") String courseWaitlistEntryId, @WebParam(name = "context") ContextInfo context)
            throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Gets the course waitlist entries for a course offering. Returns all
     * students who are on waitlists for that course offering.
     * 
     * @param courseOfferingId
     * @param context
     * @return
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public List<CourseWaitlistEntryInfo> getCourseWaitlistEntriesForCourseOffering(@WebParam(name = "courseOfferingId") String courseOfferingId, @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Gets the course waitlist entries for a reg group.
     * 
     * @param courseOfferingId
     * @param context
     * @return
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public List<CourseWaitlistEntryInfo> getCourseWaitlistEntriesForRegGroup(@WebParam(name = "regGroupId") String regGroupId, @WebParam(name = "context") ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Gets the waitlist entries for a course offering by student. A student
     * might be listed in multiple reg group waitlists in the same course.
     * 
     * @param courseOfferingId
     * @param context
     * @return
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public List<CourseWaitlistEntryInfo> getCourseWaitlistEntriesForStudentInCourseOffering(@WebParam(name = "courseOfferingId") String courseOfferingId,
            @WebParam(name = "studentId") String studentId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException;

    /**
     * Gets the waitlist for a reg group and student.
     * 
     * @param courseOfferingId
     * @param context
     * @return
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public CourseWaitlistEntryInfo getCourseWaitlistEntryForStudentInRegGroup(@WebParam(name = "regGroupId") String regGroupId, @WebParam(name = "studentId") String studentId,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Get all the waitlist the student is in for a term. Returns
     * CourseWaitlistEntryInfo which is the student-waitlist relation.
     * 
     * @param studentId
     * @param termId
     * @param context
     * @return
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public List<CourseWaitlistEntryInfo> getCourseWaitlistEntriesForStudentByTerm(@WebParam(name = "studentId") String studentId, @WebParam(name = "termId") String termId,
            @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * This method ...
     * 
     * @param criteria
     * @return
     */
    public List<CourseWaitlistEntryInfo> searchForCourseWaitlistEntries(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "context") ContextInfo context)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;;

    /**
     * This method ...
     * 
     * @param criteria
     * @return
     */
    public List<String> searchForCourseWaitlistEntryIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;;

}
