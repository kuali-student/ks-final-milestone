package org.kuali.student.ap.test.mock;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.enrollment.courseofferingset.dto.SocRolloverResultInfo;
import org.kuali.student.enrollment.courseofferingset.dto.SocRolloverResultItemInfo;
import org.kuali.student.enrollment.courseofferingset.service.CourseOfferingSetService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DependentObjectsExistException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;

import javax.jws.WebParam;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: johglove
 * Date: 1/3/14
 * Time: 1:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class CourseOfferingSetServiceMockTest implements CourseOfferingSetService {
    /**
     * Retrieve information about a Soc
     *
     * @param socId   Unique Id of the Soc
     * @param context Context information containing the principalId and locale
     *                information about the caller of service operation
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          socId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid parameter
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing parameter
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public SocInfo getSoc(@WebParam(name = "socId") String socId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves a list of course offerings by id list.
     *
     * @param socIds  List of unique Ids of Soc
     * @param context Context information containing the principalId and locale
     *                information about the caller of service operation
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          socId in the list not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid socIds
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing socIds
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<SocInfo> getSocsByIds(@WebParam(name = "socIds") List<String> socIds, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieve ids of the Soc for the term
     * <p/>
     * This could return multiple Socs but should always return the default main
     * SOC for that term.
     *
     * @param termId  Unique Id of the term
     * @param context Context information containing the principalId and locale
     *                information about the caller of service operation
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          termId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid parameter
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing parameter
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<String> getSocIdsByTerm(@WebParam(name = "termId") String termId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieve Soc Ids for a given term and subject area.
     * <p/>
     * A Soc will have an official and "other" subject areas, this
     * operation will the course offering ids with either official or other subject area
     * that match.
     * <p/>
     * <p/>
     * THIS IS A PLACEHOLDER for DEPARTMENTAL SOCS
     *
     * @param termId      Unique key of the term in which the course is being offered
     * @param subjectArea subject area
     * @param context     Context information containing the principalId and locale
     *                    information about the caller of service operation
     * @return List of Soc Ids
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          termId or subject area not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid parameter
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing parameter
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<String> getSocIdsByTermAndSubjectArea(@WebParam(name = "termId") String termId, @WebParam(name = "subjectArea") String subjectArea, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieve Soc Ids for a given term and unit content owner
     * <p/>
     * THIS IS A PLACEHOLDER for DEPARTMENTAL SOCS
     *
     * @param termId              Unique key of the term in which the course is being offered
     * @param unitsContentOwnerId Org Id of the Units content owner
     * @param context             Context information containing the principalId and locale
     *                            information about the caller of service operation
     * @return List of Soc Ids
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          termId or unitsContentOwnerid not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid parameter
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing parameter
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<String> getSocIdsByTermAndUnitsContentOwner(@WebParam(name = "termId") String termId, @WebParam(name = "unitsContentOwnerId") String unitsContentOwnerId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieve Soc Ids by type
     *
     * @param typeKey Unique key type of Soc
     * @param context Context information containing the principalId and locale
     *                information about the caller of service operation
     * @return List of Soc Ids
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          typeKey not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid parameter
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing parameter
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<String> getSocIdsByType(@WebParam(name = "typeKey") String typeKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Search for Sets of Courses
     *
     * @param criteria What to search for in SocInfo table
     * @param context  Context information containing the principalId and locale
     *                 information about the caller of service operation
     * @return A list of SocInfo objects matching criteria.  Empty list if none matches
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid parameter
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing parameter
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<SocInfo> searchForSocs(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Search for Set of Course Ids
     *
     * @param criteria What to search for in SocInfo table
     * @param context  Context information containing the principalId and locale
     *                 information about the caller of service operation
     * @return A list of SocInfo object IDs matching criteria.  Empty list if none matches
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid parameter
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing parameter
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<String> searchForSocIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Creates a new Soc for a term
     * <p/>
     * Fields in course offering will be initialized with data from the canonical.
     *
     * @param termId  Unique key of the term in which the course is being offered
     *                course offering
     * @param context Context information containing the principalId and locale
     *                information about the caller of service operation
     * @return newly created SocInfo
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          termId or socTypeKey not found
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *          One or more values invalid for this operation
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          One or more parameters invalid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          One or more parameters missing
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public SocInfo createSoc(@WebParam(name = "termId") String termId, @WebParam(name = "socTypeKey") String socTypeKey, @WebParam(name = "socInfo") SocInfo socInfo, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Updates an existing Soc.
     *
     * @param socId   Id of Soc to be updated
     * @param socInfo Details of updates to the Soc
     * @param context Context information containing the principalId and locale
     *                information about the caller of service operation
     * @return updated Soc
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *          One or more values invalid for this operation
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          the Soc does not exist
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          One or more parameters invalid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          One or more parameters missing
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     * @throws org.kuali.student.r2.common.exceptions.ReadOnlyException
     *          if trying to update the state or type
     * @throws org.kuali.student.r2.common.exceptions.VersionMismatchException
     *          The action was attempted on an out of date version.
     */
    @Override
    public SocInfo updateSoc(@WebParam(name = "socId") String socId, @WebParam(name = "socInfo") SocInfo socInfo, @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Changes the state of an existing Set of CourseOfferings to another state provided that it is valid to do so.
     *
     * @param socId        of the Set of CourseOffering's to be changed.
     * @param nextStateKey The State Key into which the identified Set of CourseOffering's will be placed if the operation succeeds.
     * @param contextInfo  Context information containing the principalId
     *                     and locale information about the caller of
     *                     service operation
     * @return status of the operation (success, failed)
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          the identified Set of CourseOffering's does not exist
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          the contextInfo object is invalid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          One or more parameters missing
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public StatusInfo changeSocState(@WebParam(name = "socId") String socId, @WebParam(name = "nextStateKey") String nextStateKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Deletes an existing Soc.
     * <p/>
     * Deleting the Soc does not automatically delete the course offerings in a soc
     *
     * @param socId   the Id of the ActivityOffering to be deleted
     * @param context Context information containing the principalId and locale
     *                information about the caller of service operation
     * @return status of the operation (success, failed)
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          the soc does not exist
     * @throws org.kuali.student.r2.common.exceptions.DependentObjectsExistException
     *          if course offerings exist and the
     *          implementation has the business rule that a course offering must have a Soc
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          One or more parameters invalid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          One or more parameters missing
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public StatusInfo deleteSoc(@WebParam(name = "socId") String socId, @WebParam(name = "context") ContextInfo context) throws DependentObjectsExistException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Validates a Soc. Depending on the value of validationType,
     * this validation could be limited to tests on just the current object and
     * its directly contained sub-objects or expanded to perform all tests
     * related to this object. If an identifier is present for the academic
     * calendar and a record is found for that identifier, the validation checks
     * if the academic calendar can be shifted to the new values. If a record
     * cannot be found for the identifier, it is assumed that the record does
     * not exist and as such, the checks performed will be much shallower,
     * typically mimicking those performed by setting the validationType to the
     * current object. This is a slightly different pattern from the standard
     * validation as the caller provides the identifier in the create statement
     * instead of the server assigning an identifier.
     *
     * @param validationType Identifier of the extent of validation
     * @param socInfo        the soc information to be tested.
     * @param context        Context information containing the principalId and locale
     *                       information about the caller of service operation
     * @return the results from performing the validation
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          validationTypeKey not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid validationTypeKey, socInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing validationTypeKey, socInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     */
    @Override
    public List<ValidationResultInfo> validateSoc(@WebParam(name = "validationType") String validationType, @WebParam(name = "socInfo") SocInfo socInfo, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieve Soc Ids that contain the specified course offering
     *
     * @param courseOfferingId Unique Id of the course offering
     * @param context          Context information containing the principalId and locale
     *                         information about the caller of service operation
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          courseOfferingId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid parameter
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing parameter
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<String> getSocIdsByCourseOffering(@WebParam(name = "courseOfferingId") String courseOfferingId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieve course offering ids associated with the soc
     *
     * @param socId   Unique Id of the soc
     * @param context Context information containing the principalId and locale
     *                information about the caller of service operation
     * @return list of course offering ids
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          courseId or termId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid courseId or termId
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing courseId or termId
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<String> getCourseOfferingIdsBySoc(@WebParam(name = "socId") String socId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Delete all the course offerings associated with the Soc
     *
     * @param socId   Unique Id of the soc
     * @param context Context information containing the principalId and locale
     *                information about the caller of service operation
     * @return count of the course offerings deleted
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          socId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid parameter
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing parameter
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public Integer deleteCourseOfferingsBySoc(@WebParam(name = "socId") String socId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DataValidationErrorException, VersionMismatchException, ReadOnlyException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Checks if the specified course offering is or is not in the specified soc.
     *
     * @param socId            Unique Id of the soc
     * @param courseOfferingId Unique id of the course offering
     * @param context          Context information containing the principalId and locale
     *                         information about the caller of service operation
     * @return true if the course offering is in the soc else false.
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          either id is not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid parameter
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing parameter
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public Boolean isCourseOfferingInSoc(@WebParam(name = "socId") String socId, @WebParam(name = "courseOfferingId") String courseOfferingId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieve published Course offering ids in this soc
     * <p/>
     * THIS IS A PLACEHOLDER FOR M5 AND NEEDS TO BE REVIEWED
     * Not sure what it means to be "published"
     *
     * @param socId   Unique Id of the soc
     * @param context Context information containing the principalId and locale
     *                information about the caller of service operation
     * @return list of Course offering ids
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          socId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid parameter
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing parameter
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<String> getPublishedCourseOfferingIdsBySoc(@WebParam(name = "socId") String socId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieve unpublished Course offering ids in this soc
     * <p/>
     * THIS IS A PLACEHOLDER FOR M5 AND NEEDS TO BE REVIEWED
     * Not sure what it means to be "unpublished"
     *
     * @param socId   Unique Id of the soc
     * @param context Context information containing the principalId and locale
     *                information about the caller of service operation
     * @return list of Activity offering ids
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          socId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid parameter
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing parameter
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<String> getUnpublishedCourseOfferingIdsBySoc(@WebParam(name = "socId") String socId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieve unpublished Activity offering ids associated with the course
     * offerings in this soc
     * <p/>
     * THIS IS A PLACEHOLDER FOR M5 AND NEEDS TO BE REVIEWED
     * Not sure what it means to be "unpublished"
     *
     * @param socId   Unique Id of the soc
     * @param context Context information containing the principalId and locale
     *                information about the caller of service operation
     * @return list of Activity offering ids
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          socId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid parameter
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing parameter
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<String> getUnpublishedActivityOfferingIdsBySoc(@WebParam(name = "socId") String socId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieve unscheduled Activity offering ids associated with the course
     * offerings in this soc
     * <p/>
     * THIS IS A PLACEHOLDER FOR M5 AND NEEDS TO BE REVIEWED
     * Not sure what it means to be "unscheduled"
     *
     * @param socId   Unique Id of the soc
     * @param context Context information containing the principalId and locale
     *                information about the caller of service operation
     * @return list of Activity offering ids
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          socId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid parameter
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing parameter
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<String> getUnscheduledActivityOfferingIdsBySoc(@WebParam(name = "socId") String socId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Get course offering ids associated with the soc that have an unscheduled final exam
     * <p/>
     * THIS IS A PLACEHOLDER FOR M5 AND NEEDS TO BE REVIEWED
     * Not sure what it means to be "unscheduled final exam"
     *
     * @param socId   Unique Id of the soc
     * @param context Context information containing the principalId and locale
     *                information about the caller of service operation
     * @return list of course offering ids
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          socId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid parameter
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing parameter
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<String> getCourseOfferingIdsWithUnscheduledFinalExamsBySoc(@WebParam(name = "socId") String socId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Requests that the course offerings in this Soc be submitted to the scheduler.
     * <p/>
     * This method starts what is know as the mass scheduling event.
     * <p/>
     * The expectation is that this method starts an asynchronous process to perform the scheduling that will complete or fail eventually independently of this method call.
     * <p/>
     * The <em>optionKeys</em> parameter can be used to alter the default processing behavior.
     *
     * @param socId      Id of the Set of Courses
     * @param optionKeys keys that alter the default processing behavior.
     * @param context    Context information containing the principalId and locale
     *                   information about the caller of service operation
     * @return status of the starting the scheduling process for the activityOffering (success, failed)
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          socId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          contextInfo object is invalid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          one or more missing parameters
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public StatusInfo startScheduleSoc(@WebParam(name = "socId") String socId, @WebParam(name = "optionKeys") List<String> optionKeys, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Creates a new SOC for the target term that corresponds to the
     * source soc and then rolls over all the course offerings in source Soc to
     * the new soc using the supplied options.
     *
     * @param sourceSocId Unique Id of the source Soc
     * @param optionKeys  keys identifying optional processing to occur
     * @param context     Context information containing the principalId and locale
     *                    information about the caller of service operation
     * @return newly created Soc
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          sourceSocId or targetTermId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid parameter
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing parameter
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public SocInfo rolloverSoc(@WebParam(name = "sourceSocId") String sourceSocId, @WebParam(name = "targetTermId") String targetTermId, @WebParam(name = "optionKeys") List<String> optionKeys, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves the result of a rollover.
     *
     * @param rolloverResultId unique Id of the rollover result
     * @param context          Context information containing the principalId and locale
     *                         information about the caller of service operation
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          rolloverResultId in the list not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid parameter
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing parameter
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public SocRolloverResultInfo getSocRolloverResult(@WebParam(name = "rolloverResultId") String rolloverResultId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves a list of rollover results by id list.
     *
     * @param rolloverResultIds List of unique Ids of the rollover result to be fetched
     * @param context           Context information containing the principalId and locale
     *                          information about the caller of service operation
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          rolloverResultId in the list not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid parameter
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing parameter
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<SocRolloverResultInfo> getSocRolloverResultsByIds(@WebParam(name = "rolloverResultIds") List<String> rolloverResultIds, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves a list of rollover results items by id list.
     *
     * @param rolloverResultItemIds List of unique Ids of the rollover result items to be fetched
     * @param context               Context information containing the principalId and locale
     *                              information about the caller of service operation
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          rolloverResultId in the list not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid parameter
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing parameter
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<SocRolloverResultItemInfo> getSocRolloverResultItemsByIds(@WebParam(name = "rolloverResultItemIds") List<String> rolloverResultItemIds, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves a list of rollover result items by result id
     *
     * @param socRolloverResultId Unique Ids of the rollover result for which the items are to be fetched
     * @param context             Context information containing the principalId and locale
     *                            information about the caller of service operation
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          rolloverResultId in the list not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid parameter
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing parameter
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<SocRolloverResultItemInfo> getSocRolloverResultItemsByResultId(@WebParam(name = "socRolloverResultId") String socRolloverResultId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves a list of rollover result items by result id and source course offering id
     *
     * @param socRolloverResultId    Unique Id of the rollover result for which the items are to be fetched
     * @param sourceCourseOfferingId Unique Id of source course offering id
     * @param context                Context information containing the principalId and locale
     *                               information about the caller of service operation
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          rolloverResultId in the list not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid parameter
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing parameter
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<SocRolloverResultItemInfo> getSocRolloverResultItemsByResultIdAndSourceCourseOfferingId(@WebParam(name = "socRolloverResultId") String socRolloverResultId, @WebParam(name = "sourceCourseOfferingId") String sourceCourseOfferingId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves a list of rollover result items by result id and target course offering id
     *
     * @param socRolloverResultId Unique Id of the rollover result for which the items are to be fetched
     * @param context             Context information containing the principalId and locale
     *                            information about the caller of service operation
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          rolloverResultId in the list not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid parameter
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing parameter
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<SocRolloverResultItemInfo> getSocRolloverResultItemsByResultIdAndTargetCourseOfferingId(@WebParam(name = "socRolloverResultId") String socRolloverResultId, @WebParam(name = "targetCourseOfferingId") String targetCourseOfferingId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves the rollover results associated with the specified source and target Soc ids
     *
     * @param sourceSocId source Soc Id
     * @param targetSocId target Soc Id
     * @param context     Context information containing the principalId and locale
     *                    information about the caller of service operation
     * @return List of Rollover Results
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          courseId or termId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid courseId or termId
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing courseId or termId
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<SocRolloverResultInfo> getSocRolloverResultsBySourceAndTargetSocs(@WebParam(name = "sourceSocId") String sourceSocId, @WebParam(name = "targetSocId") String targetSocId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves the rollover results associated with the specified target Soc id
     *
     * @param targetSocId target Soc Id
     * @param context     Context information containing the principalId and locale
     *                    information about the caller of service operation
     * @return List of Rollover Result Ids
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          courseId or termId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid courseId or termId
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing courseId or termId
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<String> getSocRolloverResultIdsByTargetSoc(@WebParam(name = "targetSocId") String targetSocId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves the rollover results associated with the specified source Soc id
     *
     * @param sourceSocId Unique id of the source Soc
     * @param context     Context information containing the principalId and locale
     *                    information about the caller of service operation
     * @return List of Rollover Result Ids
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          courseId or termId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid courseId or termId
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing courseId or termId
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<String> getSocRolloverResultIdsBySourceSoc(@WebParam(name = "targetSocId") String sourceSocId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Requests that the rollover identified by the result be reversed.
     * <p/>
     * This attempts to reverse or clear out the actions applied by the specified rollover result.
     * Depending on whether or not the resulting course offerings have been updated
     * and the optional processing flags a full reversal is not guaranteed.
     *
     * @param rolloverResultId Unique Id of the rollover result
     * @param optionKeys       keys identifying optional processing to happen when clearing the result
     * @param context          Context information containing the principalId and locale
     *                         information about the caller of service operation
     * @return a Rollover Result indicating what reversal actions were successful or not
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          courseId or termId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid courseId or termId
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing courseId or termId
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public SocRolloverResultInfo reverseRollover(@WebParam(name = "rolloverResultId") String rolloverResultId, @WebParam(name = "optionKeys") List<String> optionKeys, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Creates a new SocRolloverResult
     *
     * @param socRolloverResultTypeKey soc rollover result type key
     * @param socRolloverResultInfo    object to be created
     * @param context                  Context information containing the principalId and locale
     *                                 information about the caller of service operation
     * @return newly created SocRolloverResultInfo
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          termId or socRolloverResultTypeKey not found
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *          One or more values invalid for this operation
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          One or more parameters invalid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          One or more parameters missing
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public SocRolloverResultInfo createSocRolloverResult(@WebParam(name = "socRolloverResultTypeKey") String socRolloverResultTypeKey, @WebParam(name = "socRolloverResultInfo") SocRolloverResultInfo socRolloverResultInfo, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Updates an existing SocRolloverResult.
     *
     * @param socRolloverResultId   Id of SocRolloverResult to be updated
     * @param socRolloverResultInfo Details of updates to the SocRolloverResult
     * @param context               Context information containing the principalId and locale
     *                              information about the caller of service operation
     * @return updated SocRolloverResult
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *          One or more values invalid for this operation
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          the SocRolloverResult does not exist
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          One or more parameters invalid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          One or more parameters missing
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     * @throws org.kuali.student.r2.common.exceptions.VersionMismatchException
     *          The action was attempted on an out of date version.
     */
    @Override
    public SocRolloverResultInfo updateSocRolloverResult(@WebParam(name = "socRolloverResultId") String socRolloverResultId, @WebParam(name = "socRolloverResultInfo") SocRolloverResultInfo socRolloverResultInfo, @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Update progress information
     *
     * @param socRolloverResultId Id of SocRolloverResult to be updated
     * @param itemsProcessed      new count of the number of items processed
     * @param context             Context information containing the principalId and locale
     *                            information about the caller of service operation
     * @return StatusInfo indicates the update worked
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *          One or more values invalid for this operation
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          the SocRolloverResult does not exist
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          One or more parameters invalid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          One or more parameters missing
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     * @throws org.kuali.student.r2.common.exceptions.VersionMismatchException
     *          The action was attempted on an out of date version.
     */
    @Override
    public SocRolloverResultInfo updateSocRolloverProgress(@WebParam(name = "socRolloverResultId") String socRolloverResultId, @WebParam(name = "itemsProcessed") Integer itemsProcessed, @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Changes the state of an existing SocRolloverResultState to another state provided that it is valid to do so.
     *
     * @param socRolloverResultStateId of the SocRolloverResultState to be changed.
     * @param nextStateKey             The State Key into which the identified SocRolloverResultState will be placed if the operation succeeds.
     * @param contextInfo              Context information containing the principalId
     *                                 and locale information about the caller of
     *                                 service operation
     * @return status of the operation (success, failed)
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          the identified SocRolloverResultState does not exist
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          the contextInfo object is invalid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          One or more parameters missing
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public StatusInfo changeSocRolloverResultState(@WebParam(name = "socRolloverResultStateId") String socRolloverResultStateId, @WebParam(name = "nextStateKey") String nextStateKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Deletes an existing SocRolloverResult.
     *
     * @param socRolloverResultId the Id of the ActivityOffering to be deleted
     * @param context             Context information containing the principalId and locale
     *                            information about the caller of service operation
     * @return status of the operation (success, failed)
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          the SeatPoolDefinition does not exist
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          One or more parameters invalid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          One or more parameters missing
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public StatusInfo deleteSocRolloverResult(@WebParam(name = "socRolloverResultId") String socRolloverResultId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, DependentObjectsExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Validates a SocRolloverResult. Depending on the value of validationType,
     * this validation could be limited to tests on just the current object and
     * its directly contained sub-objects or expanded to perform all tests
     * related to this object. If an identifier is present for the academic
     * calendar and a record is found for that identifier, the validation checks
     * if the academic calendar can be shifted to the new values. If a record
     * cannot be found for the identifier, it is assumed that the record does
     * not exist and as such, the checks performed will be much shallower,
     * typically mimicking those performed by setting the validationType to the
     * current object. This is a slightly different pattern from the standard
     * validation as the caller provides the identifier in the create statement
     * instead of the server assigning an identifier.
     *
     * @param validationType        Identifier of the extent of validation
     * @param socRolloverResultInfo the socRolloverResult information to be tested.
     * @param context               Context information containing the principalId and locale
     *                              information about the caller of service operation
     * @return the results from performing the validation
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          validationTypeKey not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid validationTypeKey, socRolloverResultInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing validationTypeKey, socRolloverResultInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     */
    @Override
    public List<ValidationResultInfo> validateSocRolloverResult(@WebParam(name = "validationType") String validationType, @WebParam(name = "socRolloverResultInfo") SocRolloverResultInfo socRolloverResultInfo, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieve information about a SocRolloverResultItem
     *
     * @param socRolloverResultItemId Unique Id of the SocRolloverResultItem
     * @param context                 Context information containing the principalId and locale
     *                                information about the caller of service operation
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          socRolloverResultItemId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid parameter
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing parameter
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public SocRolloverResultItemInfo getSocRolloverResultItem(@WebParam(name = "socRolloverResultItemId") String socRolloverResultItemId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Creates a new SocRolloverResultItem
     *
     * @param socRolloverResultId          Id of the corresponding soc rollover result
     * @param socRolloverResultItemTypeKey soc rollover result type key
     * @param socRolloverResultItemInfo    object to be created
     * @param context                      Context information containing the principalId and locale
     *                                     information about the caller of service operation
     * @return newly created SocRolloverResultItemInfo
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          termId or socRolloverResultItemTypeKey not found
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *          One or more values invalid for this operation
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          One or more parameters invalid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          One or more parameters missing
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public SocRolloverResultItemInfo createSocRolloverResultItem(@WebParam(name = "socRolloverResultId") String socRolloverResultId, @WebParam(name = "socRolloverResultItemTypeKey") String socRolloverResultItemTypeKey, @WebParam(name = "socRolloverResultItemInfo") SocRolloverResultItemInfo socRolloverResultItemInfo, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Bulk create of  SocRolloverResultItems
     * <p/>
     * All must be for the same result and type.
     *
     * @param socRolloverResultId          Id of the corresponding soc rollover result
     * @param socRolloverResultItemTypeKey soc rollover result type key
     * @param socRolloverResultItemInfos   objects to be created
     * @param context                      Context information containing the principalId and locale
     *                                     information about the caller of service operation
     * @return count of number of items created
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          termId or socRolloverResultItemTypeKey not found
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *          One or more values invalid for this operation
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          One or more parameters invalid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          One or more parameters missing
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public Integer createSocRolloverResultItems(@WebParam(name = "socRolloverResultId") String socRolloverResultId, @WebParam(name = "socRolloverResultItemTypeKey") String socRolloverResultItemTypeKey, @WebParam(name = "socRolloverResultItemInfos") List<SocRolloverResultItemInfo> socRolloverResultItemInfos, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Updates an existing SocRolloverResultItem.
     *
     * @param socRolloverResultItemId   Id of SocRolloverResultItem to be updated
     * @param socRolloverResultItemInfo Details of updates to the SocRolloverResultItem
     * @param context                   Context information containing the principalId and locale
     *                                  information about the caller of service operation
     * @return updated SocRolloverResultItem
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *          One or more values invalid for this operation
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          the SocRolloverResultItem does not exist
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          One or more parameters invalid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          One or more parameters missing
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     * @throws org.kuali.student.r2.common.exceptions.VersionMismatchException
     *          The action was attempted on an out of date version.
     */
    @Override
    public SocRolloverResultItemInfo updateSocRolloverResultItem(@WebParam(name = "socRolloverResultItemId") String socRolloverResultItemId, @WebParam(name = "socRolloverResultItemInfo") SocRolloverResultItemInfo socRolloverResultItemInfo, @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Changes the state of an existing SocRolloverResultItem to another state provided that it is valid to do so.
     *
     * @param socRolloverResultItemId of the SocRolloverResultItem to be changed.
     * @param nextStateKey            The State Key into which the identified SocRolloverResultItem will be placed if the operation succeeds.
     * @param contextInfo             Context information containing the principalId
     *                                and locale information about the caller of
     *                                service operation
     * @return status of the operation (success, failed)
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          the identified SocRolloverResultItem does not exist
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          the contextInfo object is invalid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          One or more parameters missing
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public StatusInfo changeSocRolloverResultItemState(@WebParam(name = "socRolloverResultItemId") String socRolloverResultItemId, @WebParam(name = "nextStateKey") String nextStateKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Deletes an existing SocRolloverResultItem.
     *
     * @param socRolloverResultItemId the Id of the ActivityOffering to be deleted
     * @param context                 Context information containing the principalId and locale
     *                                information about the caller of service operation
     * @return status of the operation (success, failed)
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          the SeatPoolDefinition does not exist
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          One or more parameters invalid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          One or more parameters missing
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public StatusInfo deleteSocRolloverResultItem(@WebParam(name = "socRolloverResultItemId") String socRolloverResultItemId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Validates a SocRolloverResultItem. Depending on the value of validationType,
     * this validation could be limited to tests on just the current object and
     * its directly contained sub-objects or expanded to perform all tests
     * related to this object. If an identifier is present for the academic
     * calendar and a record is found for that identifier, the validation checks
     * if the academic calendar can be shifted to the new values. If a record
     * cannot be found for the identifier, it is assumed that the record does
     * not exist and as such, the checks performed will be much shallower,
     * typically mimicking those performed by setting the validationType to the
     * current object. This is a slightly different pattern from the standard
     * validation as the caller provides the identifier in the create statement
     * instead of the server assigning an identifier.
     *
     * @param validationType            Identifier of the extent of validation
     * @param socRolloverResultItemInfo the socRolloverResultItem information to be tested.
     * @param context                   Context information containing the principalId and locale
     *                                  information about the caller of service operation
     * @return the results from performing the validation
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          validationTypeKey not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid validationTypeKey, socRolloverResultItemInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing validationTypeKey, socRolloverResultItemInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     */
    @Override
    public List<ValidationResultInfo> validateSocRolloverResultItem(@WebParam(name = "validationType") String validationType, @WebParam(name = "socRolloverResultItemInfo") SocRolloverResultItemInfo socRolloverResultItemInfo, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Searches for soc rollover result ids using a free form search criteria.
     *
     * @param criteria
     * @param context
     * @return
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *
     */
    @Override
    public List<String> searchForSocRolloverResultIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Searches for soc rollover results using a free form search criteria
     *
     * @param criteria
     * @param context
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *
     */
    @Override
    public List<SocRolloverResultInfo> searchForSocRolloverResults(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Searches for soc rollover result item ids using a free form search criteria.
     *
     * @param criteria
     * @param context
     * @return
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *
     */
    @Override
    public List<String> searchForSocRolloverResultItemIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Searches for soc rollover result items using a free form search criteria
     *
     * @param criteria
     * @param context
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *
     */
    @Override
    public List<SocRolloverResultItemInfo> searchForSocRolloverResultItems(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
